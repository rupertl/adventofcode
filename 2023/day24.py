"""2023 Day 24: Never Tell Me The Odds"""


# pylint: disable=invalid-name,too-few-public-methods
# pylint: disable=unbalanced-tuple-unpacking,too-many-locals

from collections import namedtuple
import itertools
import z3
from puzzle import Puzzle


Point = namedtuple('Point', 'x y z')
Stone = namedtuple('Stone', 'pos vel')


class HailStorm():
    """Represents a 3D space full of hailstones which are moving."""
    def __init__(self, lines, rangeMin, rangeMax):
        self.rangeMin = rangeMin
        self.rangeMax = rangeMax
        self.parse(lines)

    def parse(self, lines):
        """Convert puzzle input into Stones."""
        self.stones = []
        for line in lines:
            pos, vel = line.split('@')
            posPoint = Point(*(int(n) for n in pos.split(',')))
            velPoint = Point(*(int(n) for n in vel.split(',')))
            stone = Stone(posPoint, velPoint)
            self.stones.append(stone)

    def intersects_2d_range(self, s1, s2):
        """Is there an intersection between s1 and s2 (ignoring z) in
        the test range?"""
        # From https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection\
        #    #Given_two_points_on_each_line_segment
        # Extend the line based om velocity
        end = 1000 * (self.rangeMax - self.rangeMin)
        x1, y1 = s1.pos.x, s1.pos.y
        x2, y2 = end * s1.vel.x, end * s1.vel.y
        x3, y3 = s2.pos.x, s2.pos.y
        x4, y4 = end * s2.vel.x, end * s2.vel.y

        d = (((x1 - x2) * (y3 - y4)) - ((y1 - y2) * (x3 - x4)))
        t = (((x1 - x3) * (y3 - y4)) - ((y1 - y3) * (x3 - x4))) / d
        u = (((x1 - x3) * (y1 - y2)) - ((y1 - y3) * (x1 - x2))) / d

        if 0 <= t <= 1 and 0 <= u <= 1:
            ix = x1 + (t * (x2 - x1))
            iy = y1 + (t * (y2 - y1))
            return (self.rangeMin <= ix <= self.rangeMax and
                    self.rangeMin <= iy <= self.rangeMax)
        return False

    def num_intersects_2d(self):
        """Find how many stones intersect with each other in 2D in the
        test range."""
        return sum((self.intersects_2d_range(s1, s2)
                    for s1, s2 in itertools.combinations(self.stones, 2)))

    def find_rock_start(self):
        """Find the rock starting position that will hit all the stones."""
        # Well this stumped me - took a hint from reddit to uze z3 to
        # solve the equation system.

        # For 3 stones (s0,s1,s2)
        assert len(self.stones) >= 3
        s0, s1, s2 = self.stones[0:3]

        # Find the initial position of the rock (px,py,pz) and
        # velocity (vx,vy,vz) for when it intercepts the stones at
        # times (t0,t1,t2) These could be ints according to the puzzle
        # text. This works for the sample input but blows up for the
        # full data set, so use real.
        px, py, pz = z3.Reals("px py pz")
        vx, vy, vz = z3.Reals("vx vy vz")
        t0, t1, t2 = z3.Reals("t0 t1 t2")

        solver = z3.Solver()
        solver.add(px + (t0 * vx) == s0.pos.x + (t0 * s0.vel.x))
        solver.add(py + (t0 * vy) == s0.pos.y + (t0 * s0.vel.y))
        solver.add(pz + (t0 * vz) == s0.pos.z + (t0 * s0.vel.z))
        solver.add(px + (t1 * vx) == s1.pos.x + (t1 * s1.vel.x))
        solver.add(py + (t1 * vy) == s1.pos.y + (t1 * s1.vel.y))
        solver.add(pz + (t1 * vz) == s1.pos.z + (t1 * s1.vel.z))
        solver.add(px + (t2 * vx) == s2.pos.x + (t2 * s2.vel.x))
        solver.add(py + (t2 * vy) == s2.pos.y + (t2 * s2.vel.y))
        solver.add(pz + (t2 * vz) == s2.pos.z + (t2 * s2.vel.z))

        if solver.check() == z3.sat:
            return (solver.model()[p].as_long() for p in (px, py, pz))
        raise RuntimeError('Could not solve')


class Day24(Puzzle):
    """2023 Day 24: Never Tell Me The Odds"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 24

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        lines = self.puzzle_data.input_as_lines()
        self.storm = HailStorm(lines, 200000000000000, 400000000000000)

    def calculate_a(self):
        """Part A: Considering only the X and Y axes, check all pairs
        of hailstones' future paths for intersections. How many of
        these intersections occur within the test area?"""
        return self.storm.num_intersects_2d()

    def calculate_b(self):
        """Part B. Determine the exact position and velocity the rock
        needs to have at time 0 so that it perfectly collides with
        every hailstone. What do you get if you add up the X, Y, and Z
        coordinates of that initial position?"""
        return sum(self.storm.find_rock_start())
