"""2023 Day 3: Gear Ratios"""


# pylint: disable=invalid-name,too-few-public-methods


import math
from collections import namedtuple, defaultdict
from puzzle import Puzzle


Point = namedtuple('Point', 'row col')


class EngineSchematic:
    """Represents an engine schematic with a number of parts in it."""
    def __init__(self, lines):
        self.lines = lines
        self.maxRow = len(self.lines)
        self.maxCol = len(self.lines[0])
        self.parts = []
        self.gears = defaultdict(set)         # maps * points to parts
        self.gearRatios = []
        self.scan()
        self.calculateGearRatios()

    def scan(self):
        """Scan the schematic for numbers and pass any that are found
        to checkNumber to see if they are parts or gears.."""
        number = None           # string containing digits found so far
        start, end = None, None
        for row in range(self.maxRow):
            for col in range(self.maxCol):
                point = Point(row, col)
                if not number and self.isNumber(point):
                    # We've found the start of a number
                    number = self.at(point)
                    start = point
                    end = point
                elif number:
                    # We were scanning a number.
                    if self.isNumber(point):
                        # We are still in a number
                        number += self.at(point)
                        end = point
                        if col != self.maxCol - 1:
                            # Keep going, unless we are in the last column
                            continue
                    # The number has finished. Check if it is a part
                    # and reset number ready for the next scan.
                    self.checkNumber(start, end, int(number))
                    number = None

    def checkNumber(self, start, end, number):
        """Given a number and its location, check if it is a part
        number and if it may be a gear."""
        symbols = self.findSymbols(start, end)
        if symbols:
            self.parts.append(number)
            for symbol in symbols:
                if self.at(symbol) == '*':
                    self.gears[symbol].add(number)

    def findSymbols(self, start, end):
        """See if number bounded by start and end have any symbols in
        them."""
        # We need to check all points marked with #
        # ...#####...
        # ...#123#..
        # ...#####...
        # But to simplify we check all the points in the box
        # including the numbers.
        symbols = []
        for row in range(start.row - 1, start.row + 2):
            for col in range(start.col - 1, end.col + 2):
                point = Point(row, col)
                if self.isValidPoint(point) and self.isSymbol(point):
                    symbols.append(point)
        return symbols

    def at(self, point):
        """Return contents of the schematic at point."""
        return self.lines[point.row][point.col]

    def isValidPoint(self, point):
        """Is point on the schematic>"""
        return (point.row >= 0 and point.row < self.maxRow and
                point.col >= 0 and point.col < self.maxCol)

    def isNumber(self, point):
        """Is the thing on the schematic at point a number?"""
        return self.at(point).isdigit()

    def isSymbol(self, point):
        """Is the thing on the schematic at point a symbol?"""
        return (not self.isNumber(point)) and self.at(point) != '.'

    def calculateGearRatios(self):
        """Ensure that only gears touching two numbers are saved."""
        for gearNumbers in self.gears.values():
            if len(gearNumbers) == 2:
                self.gearRatios.append(math.prod(list(gearNumbers)))


class Day03(Puzzle):
    """2023 Day 3: Gear Ratios"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 3

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        lines = self.puzzle_data.input_as_lines()
        self.schematic = EngineSchematic(lines)

    def calculate_a(self):
        """Part A: What is the sum of all of the part numbers in the
        engine schematic?"""
        return sum(self.schematic.parts)

    def calculate_b(self):
        """Part B. What is the sum of all of the gear ratios in your
        engine schematic?"""
        return sum(self.schematic.gearRatios)
