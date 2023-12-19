"""2023 Day 17: Clumsy Crucible"""


# pylint: disable=invalid-name,too-few-public-methods

from heapq import heappush, heappop
from collections import namedtuple
from puzzle import Puzzle


Point = namedtuple('Point', 'row col')

# Compass points offsets
N = (-1, 0)
S = (1, 0)
E = (0, 1)
W = (0, -1)

# Marker for when we try to access something outside the city.
OUT_OF_RANGE = '0'


# Used to store current state when working out best path
# stepLen is the current number of steps taken in the current direction.
Trajectory = namedtuple('Trajectory', 'position direction stepLen')


class CrucibleCity():
    """Represents a city where we lose a certain amount of heat on
    moving to each block. We want to minimize the heat losee when
    moving from top left to bottom right."""
    def __init__(self, lines):
        self.city = self.parse(lines)
        self.maxRow = len(self.city)
        self.maxCol = len(self.city[0])
        self.start = Point(0, 0)
        self.target = Point(self.maxRow - 1, self.maxCol - 1)

    def parse(self, lines):
        """Convert the grid of numbers into a 2D list of numbers."""
        city = []
        for line in lines:
            city.append([int(ch) for ch in line])
        return city

    def at(self, point):
        """Return the heat loss of entering this point in the city if
        valid."""
        if (point.row < 0 or point.row >= self.maxRow or
           point.col < 0 or point.col >= self.maxCol):
            return OUT_OF_RANGE
        return self.city[point.row][point.col]

    # What directions we can go given our current direction
    TRANSITIONS = {
        N: (N, E, W),
        S: (S, E, W),
        E: (E, N, S),
        W: (W, N, S)
    }

    def valid_moves(self, point, direction):
        """Find what moves we can make given the current point and
        direction."""
        moves = []
        for newDirection in CrucibleCity.TRANSITIONS[direction]:
            newPosition = Point(point.row + newDirection[0],
                                point.col + newDirection[1])
            if self.at(newPosition) != OUT_OF_RANGE:
                moves.append((newPosition, newDirection))
        return moves

    def best_path(self, minStepLen=1, maxStepLen=3):
        """Find the best path to get to the factory given the step
        length conditions applicable for the crucible type."""

        # Form a priority queue containing heat loss and current trajectory
        queue = []
        # Add the two initial moves from the start position
        heappush(queue, (0, Trajectory(self.start, S, 0)))
        heappush(queue, (0, Trajectory(self.start, E, 0)))

        # Create a cache of seen trajectories
        seen = set()

        while queue:
            (loss, curr) = heappop(queue)
            if curr in seen:
                continue
            seen.add(curr)

            for newPosition, newDirection in (
                    self.valid_moves(curr.position, curr.direction)):
                newStepLen = (curr.stepLen + 1
                              if newDirection == curr.direction else 1)
                newLoss = loss + self.at(newPosition)

                if newStepLen > maxStepLen:
                    # Exceeded max steps per direction
                    continue
                if (newDirection != curr.direction and
                   curr.stepLen < minStepLen):
                    # Did not take enough steps on previous direction
                    continue
                if newPosition == self.target and newStepLen >= minStepLen:
                    # Reached target, including min step condition
                    return newLoss

                # Enqueue the next move
                nextTrajectory = Trajectory(newPosition, newDirection,
                                            newStepLen)
                heappush(queue, (newLoss, nextTrajectory))
        # Queue exhausted
        return None


class Day17(Puzzle):
    """2023 Day 17: Clumsy Crucible"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 17

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        lines = self.puzzle_data.input_as_lines()
        self.cc = CrucibleCity(lines)

    def calculate_a(self):
        """Part A: Directing the crucible from the lava pool to the
        machine parts factory, but not moving more than three
        consecutive blocks in the same direction, what is the least
        heat loss it can incur?"""
        return self.cc.best_path(maxStepLen=3)

    def calculate_b(self):
        """Part B. Directing the ultra crucible from the lava pool to
        the machine parts factory, what is the least heat loss it can
        incur?"""
        return self.cc.best_path(minStepLen=4, maxStepLen=10)
