"""2016 Day 13: A Maze of Twisty Little Cubicles"""


# pylint: disable=invalid-name,too-few-public-methods

import queue
from collections import namedtuple
from puzzle import Puzzle


Point = namedtuple('Point', 'x y')


class CubicleFarm():
    """Represents an infinite farm of cubicles, arranged in a x-y grid
    with origin at top left, where each point can be a wall or open
    space."""

    def __init__(self, seed=10):
        self.seed = seed
        self.cache = {}

    def is_wall(self, point):
        """Is the space at point a wall?"""
        if point not in self.cache:
            self.cache[point] = self.calculate_point(point)
        return self.cache[point]

    def is_open(self, point):
        """Is the space at point open?"""
        return not self.is_wall(point)

    def calculate_point(self, point):
        """Run the algorithm to determine what is at point."""
        value = (point.x * point.x) + (3 * point.x) + (
            2 * point.x * point.y) + point.y + (point.y * point.y) + self.seed
        # If it's odd, it's a wall
        # bit_count on int is Python 3.10+
        return value.bit_count() % 2 != 0

    def neighbours(self, point):
        """Return a set of valid open spaces next to this point."""
        neighbours = []
        for delta in (-1, 1):
            nx = Point(x=point.x + delta, y=point.y)
            if nx.x >= 0 and self.is_open(nx):
                neighbours.append(nx)
            ny = Point(x=point.x, y=point.y + delta)
            if ny.y >= 0 and self.is_open(ny):
                neighbours.append(ny)
        return neighbours


Turn = namedtuple('Turn', 'num_turns at')


class Solver():
    """Finds paths in the cubicle farm."""
    def __init__(self, seed):
        self.cubicles = CubicleFarm(seed)

    def find_path_to(self, destination):
        """Find length of path to a given point."""
        q, visited = self.setup_queue_and_visited()
        while not q.empty():
            turn = q.get()
            moves = self.cubicles.neighbours(turn.at)
            for move in moves:
                num_turns = turn.num_turns + 1
                if move == destination:
                    return num_turns
                if move not in visited:
                    visited.add(move)
                    q.put(Turn(num_turns, move))
        return -1               # queue exhausted, no solution

    def find_visitable_points(self, depth):
        """How many points can we reach in depth turns?"""
        q, visited = self.setup_queue_and_visited()
        while not q.empty():
            turn = q.get()
            moves = self.cubicles.neighbours(turn.at)
            for move in moves:
                num_turns = turn.num_turns + 1
                if num_turns <= depth and move not in visited:
                    visited.add(move)
                    q.put(Turn(num_turns, move))
        return len(visited)

    def setup_queue_and_visited(self):
        """Common work to set up the queue and visited data structures
        for the above methods."""
        visited = set()
        q = queue.Queue()
        start = Turn(num_turns=0, at=Point(1, 1))
        visited.add(start.at)
        q.put(start)
        return (q, visited)


class Day13(Puzzle):
    """2016 Day 13: A Maze of Twisty Little Cubicles"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 13

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        seed = int(self.puzzle_data.input_as_string())
        self.solver = Solver(seed)

    def calculate_a(self):
        """Part A: What is the fewest number of steps required for you
        to reach 31,39?"""
        return self.solver.find_path_to(Point(31, 39))

    def calculate_b(self):
        """Part B. How many locations (distinct x,y coordinates,
        including your starting location) can you reach in at most 50
        steps?"""
        return self.solver.find_visitable_points(50)
