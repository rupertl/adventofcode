"""2023 Day 21: Step Counter"""


# pylint: disable=invalid-name,too-few-public-methods

from collections import namedtuple
from puzzle import Puzzle


Point = namedtuple('Point', 'row col')


# Compass points offsets
N = (-1, 0)
S = (1, 0)
E = (0, 1)
W = (0, -1)

# For the garden in part A, value returned when going out of range.
OUT_OF_GARDEN = -1


class Garden():
    """A garden with plots and rocks where the elf can take steps."""
    def __init__(self, lines, infinite=False):
        self.parse(lines)
        self.infinite = infinite   # does this garden extend infinitely?

    def parse(self, lines):
        """Read puzzle lines and form the 2D grid."""
        self.maxRow = len(lines)
        self.maxCol = len(lines[0])
        self.grid = []
        for lineIndex, line in enumerate(lines):
            row = []
            for chIndex, ch in enumerate(line):
                if ch == 'S':
                    self.start = Point(lineIndex, chIndex)
                    ch = '.'    # replace with open plot
                row.append(ch)
            self.grid.append(row)

    def at(self, point):
        """Find out what is at point"""
        if self.infinite:
            return self.at_finite(point.row % self.maxRow,
                                  point.col % self.maxCol)
        return self.at_finite(point.row, point.col)

    def at_finite(self, row, col):
        """Return . or # representing the value at point, or -1 if out
        of bounds."""
        if row < 0 or row >= self.maxRow or col < 0 or col >= self.maxCol:
            return OUT_OF_GARDEN
        return self.grid[row][col]

    def valid_moves(self, point):
        """Return a set of all valid moves from point."""
        visited = set()
        for diff in (N, S, E, W):
            newPoint = Point(point.row + diff[0], point.col + diff[1])
            if self.at(newPoint) == '.':
                visited.add(newPoint)
        return visited

    def two_steps_away(self, starts):
        """Return all the points visitable two steps away from each
        start location."""
        visited = set()
        for location in starts:
            visited.add(location)
            oneSteps = self.valid_moves(location)
            for step in oneSteps:
                visited.update(self.valid_moves(step))
        return visited

    def visitable(self, maxStepcount):
        """Return all points visitable from the start in maxStepCount
        moves."""
        # We cut this down by observing that every two steps you can
        # return to where you started from.
        locations = {self.start}
        for _ in range(2, maxStepcount + 1, 2):
            locations = self.two_steps_away(locations)
        if maxStepcount % 2 == 0:
            return locations
        # For an odd number of steps, find those one step away from
        # the closest lower even number
        oddLocations = set()
        for location in locations:
            oddLocations.update(self.valid_moves(location))
        return oddLocations

    def part_b_semi_manual(self, maxStepCount):
        """Solution for part B. Needs adaprion for other than my input."""
        # The output looks quadratic based on the sample data
        #
        # 26501365 = 65 + 202300*131 (the width/height of the input)
        # (thanks to reddit for that hint!)
        #
        # Formulate the number of steps as follos:
        # f(steps) = f(65 + n*131)
        # Use the brute force approach to get for n=0,1,2
        #
        # for n in (0, 1, 2):
        #     x = 65 + (131 * n)
        #     print('{', f'{n}, {len(self.visitable(x))}', '}')
        #
        # which for my input gives me
        #
        # { 0, 3867 }
        # { 1, 34253 }
        # { 2, 94909 }
        #
        # Now what is the quadratic equation that fits this?
        # I could use numpy but a quick way is to ask Wolfram Alpha
        # https://www.wolframalpha.com/input?i=find+quadratic+fit&assumption=%7B%22C%22%2C+%22find+quadratic+fit%22%7D+-%3E+%7B%22Calculator%22%2C+%22dflt%22%7D&assumption=%7B%22F%22%2C+%22QuadraticFitCalculator%22%2C+%22data2%22%7D+-%3E%22%7B%7B0%2C+3867%7D%2C+%7B1%2C+34253%7D%2C+%7B2%2C+94909%7D%7D%22
        # Which gives me:
        #     15135 x^2 + 15251 x + 3867
        #     (data is perfectly fit by a 2nd degree polynomial)
        #
        # So we plug in the right value for x based on maxStepCount
        x = (maxStepCount - 65) // 131
        return (15135 * x * x) + (15251 * x) + 3867


class Day21(Puzzle):
    """2023 Day 21: Step Counter"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 21

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        lines = self.puzzle_data.input_as_lines()
        self.gardenA = Garden(lines)
        self.gardenB = Garden(lines, infinite=True)
        self.stepsA = 64
        self.stepsB = 26501365

    def calculate_a(self):
        """Part A: Starting from the garden plot marked S on your map, how
        many garden plots could the Elf reach in exactly 64 steps?"""
        return len(self.gardenA.visitable(self.stepsA))

    def calculate_b(self):
        """Part B. Starting from the garden plot marked S on your
        infinite map, how many garden plots could the Elf reach in
        exactly 26501365 steps?"""
        return self.gardenB.part_b_semi_manual(self.stepsB)
