"""2023 Day 1: Trebuchet?!"""


# pylint: disable=invalid-name,too-few-public-methods


import re
from puzzle import Puzzle


def calibration_value(line):
    """Return the calibration value for a line in the document, which
    is the first and last digit combined."""
    digits = [int(ch) for ch in line if ch.isdigit()]
    assert len(digits) >= 1
    return (10 * digits[0]) + digits[-1]


DIGITS_MAP = {'one': 'o1e', 'two': 't2o', 'three': 'th3ee',
              'four': 'fo4r', 'five': 'fi5e', 'six': 's6x',
              'seven': 'se7en', 'eight': 'ei8ht', 'nine': 'ni9e'}
DIGIT_RE = re.compile('|'.join(DIGITS_MAP.keys()))


def expand_digits(line):
    """Replace words like 'eight' with a word containing digit '8' in
    line."""
    # The tricky thing here is "eightwothree" -> 823 so we need to
    # allow overlaps. Do this by replacing 'eight' with 'ei8ht'
    while True:
        mat = DIGIT_RE.search(line)
        if not mat:
            return line
        word = mat.group(0)
        line = line.replace(word, DIGITS_MAP[word], 1)  # replace only once
    return line


class Day01(Puzzle):
    """2023 Day 1: Trebuchet?!"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 1

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.lines = self.puzzle_data.input_as_lines()

    def calculate_a(self):
        """Part A: What is the sum of all of the calibration values?"""
        return self.solve(self.lines)

    def calculate_b(self):
        """Part B. What is the sum of all of the calibration values,
        including digits spelled out as words?"""
        expanded = [expand_digits(line) for line in self.lines]
        return self.solve(expanded)

    def solve(self, input_lines):
        """Solve the puzzle by summing calibration values for given input."""
        return sum((calibration_value(line) for line in input_lines))
