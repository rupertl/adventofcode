"""A test of the puzzle framework."""


from puzzle import Puzzle


class Day00(Puzzle):
    """Sample puzzle implementation."""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 0

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.input = self.puzzle_data.input_as_lines()

    def calculate_a(self):
        """Part A returns two copies of each line."""
        output = ''
        for line in self.input:
            output += line * 2
        return output

    def calculate_b(self):
        """Part B upper cases the input."""
        output = ''
        for line in self.input:
            output += line.upper()
        return output
