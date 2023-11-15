"""2016 Day 25: Clock Signal"""


# pylint: disable=invalid-name,too-few-public-methods

from assembunny_25 import Assembunny
from puzzle import Puzzle


class Day25(Puzzle):
    """2016 Day 25: Clock Signal"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 25

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.instructions = self.puzzle_data.input_as_lines()
        self.assembunny = Assembunny(self.instructions)

    def calculate_a(self):
        """Part A: What is the lowest positive integer that can be
        used to initialize register a and cause the code to output a
        clock signal of 0, 1, 0, 1... repeating forever?"""
        for a in range(1000):
            # Heuristic: try a shprt sequence and if that works check
            # a longer one.
            if self.try_sequence(a, 4) and self.try_sequence(a, 16):
                return a
        return 'not found'

    def try_sequence(self, a, outputBreak):
        """Try running the program with input value a and break after
        outputBreak items have been sent. Return true if it matches
        the expected pattern."""
        target = [0, 1] * (outputBreak // 2)
        self.assembunny.reset()
        self.assembunny.set_register('a', a)
        self.assembunny.run(outputBreak)
        return self.assembunny.output == target

    def calculate_b(self):
        """Part B. Have you solved all other puzzles?"""
        return '*'
