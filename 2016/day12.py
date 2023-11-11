"""2016 Day 12: Leonardo's Monorail"""


# pylint: disable=invalid-name,too-few-public-methods


from assembunny import Assembunny
from puzzle import Puzzle


class Day12(Puzzle):
    """2016 Day 12: Leonardo's Monorail"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 12

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.assembunny = Assembunny(self.puzzle_data.input_as_lines())

    def calculate_a(self):
        """Part A: What value is left in register a?"""
        self.assembunny.reset()
        self.assembunny.run()
        return self.assembunny.get_register('a')

    def calculate_b(self):
        """Part B. If you instead initialize register c to be 1, what
        value is now left in register a?"""
        self.assembunny.reset()
        self.assembunny.set_register('c', 1)
        self.assembunny.run()
        return self.assembunny.get_register('a')
