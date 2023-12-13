"""2023 Day 11: Cosmic Expansion"""


# pylint: disable=invalid-name,too-few-public-methods

import itertools
from collections import namedtuple, defaultdict
from puzzle import Puzzle


Point = namedtuple('Point', 'row col')


def manhattan_distance(p1, p2):
    """Return the Manhattan (grid) dfistance between two points."""
    return abs(p1.row - p2.row) + abs(p1.col - p2.col)


class Cosmos():
    """Represents a cosmos with galaxies at certain points."""
    def __init__(self, lines, expansion=2):
        # Num of rows/cols to add if empty one found
        self.expansion = expansion - 1
        self.galaxies = self.expand(lines)

    def expand(self, lines):
        """Assemble a list of galaxy points by reading input and
        expanding the cosmos size if there are any empty rows or
        columns."""
        galaxies, rowCounter, colCounter = self.read_galaxies(lines)
        numRows, numCols = len(lines), len(lines[0])
        rowShifts = self.calculate_shifts(numRows, rowCounter)
        colShifts = self.calculate_shifts(numCols, colCounter)
        return [Point(g.row + rowShifts[g.row], g.col + colShifts[g.col])
                for g in galaxies]

    def read_galaxies(self, lines):
        """Read lines and return a list of galaxy points, and a
        counter for each row and column."""
        galaxies = []
        rowCounter = defaultdict(lambda: 0)
        colCounter = defaultdict(lambda: 0)
        for row, line in enumerate(lines):
            for col, ch in enumerate(line):
                if ch == '#':
                    galaxies.append(Point(row, col))
                    rowCounter[row] += 1
                    colCounter[col] += 1
        return galaxies, rowCounter, colCounter

    def calculate_shifts(self, limit, counter):
        """Determine for each input point up to limit how much we need
        to shift it by."""
        # We keep a running count of how much to shift by and add the
        # expansion factor to it when we hit an empty row/column.
        shifts = []
        shift = 0
        for n in range(limit):
            shifts.append(shift)
            if counter[n] == 0:
                shift += self.expansion
        return shifts

    def sum_shortest_distance(self):
        """Find the sum of the shortest distances between all galaxies."""
        return sum((manhattan_distance(x, y)
                    for x, y in itertools.combinations(self.galaxies, 2)))


class Day11(Puzzle):
    """2023 Day 11: Cosmic Expansion"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 11

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.lines = self.puzzle_data.input_as_lines()
        self.cosmosA = Cosmos(self.lines)
        self.cosmosB = Cosmos(self.lines, expansion=1_000_000)

    def calculate_a(self):
        """Part A: What is the sum of these lengths?"""
        return self.cosmosA.sum_shortest_distance()

    def calculate_b(self):
        """Part B. What is the sum of these lengths? (with 1000000x
        expansion)"""
        return self.cosmosB.sum_shortest_distance()
