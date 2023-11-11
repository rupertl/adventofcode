"""2016 Day 23: Safe Cracking"""


# pylint: disable=invalid-name,too-few-public-methods

from assembunny import Assembunny
from puzzle import Puzzle


class Day23(Puzzle):
    """2016 Day 23: Safe Cracking"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 23

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.instructions = self.puzzle_data.input_as_lines()

    def calculate_a(self):
        """Part A: What value should be sent to the safe?"""
        return self.solve(7)

    def calculate_b(self):
        """Part B. What value should actually be sent to the safe?"""
        # This takes about 20 minutes on my PC so did not need to
        # optimise further. If needed, the approach would be to
        # identify the inc X / dec Y loops in the code and change them
        # to add instructions, and nested loops to multuplications.
        # The other way would be to translate it to Python, but the tgl
        # makes that hard.
        return self.solve(12)

    def solve(self, key_input):
        """Run the program for the given input and return value in a."""
        # Create a new assembunny each time as the program modifies
        # the code.
        assembunny = Assembunny(self.instructions)
        assembunny.set_register('a', key_input)
        assembunny.run()
        return assembunny.get_register('a')
