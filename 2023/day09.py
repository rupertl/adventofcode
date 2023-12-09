"""2023 Day 9: Mirage Maintenance"""


# pylint: disable=invalid-name,too-few-public-methods

from puzzle import Puzzle


class Oasis:
    """Represents a sequence of observed values from which we can
    extrapolate a new value."""
    def __init__(self, line):
        self.readings = [[int(word) for word in line.split()]]
        self.generate_subreadings()

    def generate_subreadings(self):
        """Add lines of implied readings."""
        seq = self.readings[0]
        # Keep going until last line is all zero
        while any(seq):
            nextSeq = []
            for index, value in enumerate(seq[1:]):
                nextSeq.append(value - seq[index])
            self.readings.append(nextSeq)
            seq = nextSeq

    def extrapolate_next(self):
        """Extrapolate via the puzzle algorithm and return the next
        value."""
        # Add in the extrapolated values to each line
        for index in range(len(self.readings) - 2, -1, -1):
            ex = self.readings[index][-1] + self.readings[index + 1][-1]
            self.readings[index].append(ex)
        return ex

    def extrapolate_prev(self):
        """Extrapolate via the puzzle algorithm and return the previous
        value."""
        self.readings[-1].insert(0, 0)
        for index in range(len(self.readings) - 2, -1, -1):
            ex = self.readings[index][0] - self.readings[index + 1][0]
            self.readings[index].insert(0, ex)
        return ex


class Day09(Puzzle):
    """2023 Day 9: Mirage Maintenance"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 9

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        lines = self.puzzle_data.input_as_lines()
        self.oases = [Oasis(line) for line in lines]

    def calculate_a(self):
        """Part A: What is the sum of these extrapolated values?"""
        return sum((oasis.extrapolate_next() for oasis in self.oases))

    def calculate_b(self):
        """Part B. What is the sum of these extrapolated values?"""
        return sum((oasis.extrapolate_prev() for oasis in self.oases))
