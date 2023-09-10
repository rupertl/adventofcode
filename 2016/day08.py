"""2016  Day 8: Two-Factor Authentication"""


# pylint: disable=invalid-name,too-few-public-methods


import re
from puzzle import Puzzle


class Screen():
    """Implements a screen with pizels and set/rotate operations."""
    def __init__(self):
        self.width = 50
        self.height = 6
        # Display is a 2D array of pixels, 0 = off. Origin is top left.
        line = self.width * [0]
        self.display = [line.copy() for _ in range(self.height)]

    def rect(self, a, b):
        """Turn on all pixels in a rectange at the origin with width a
        and height b."""
        for row in range(b):
            for col in range(a):
                self.display[row][col] = 1

    def rotate_row(self, row, distance):
        """Rotate all pixels in the given row by an amount."""
        self.display[row] = (self.display[row][-distance:] +
                             self.display[row][:-distance])

    def rotate_col(self, col, distance):
        """Rotate all pixels in the given column by a distance."""
        # Implemented by transposing the array, rotating the now-row
        # and transposing it back. Not super efficient but OK for this
        # screen size.
        self.transpose_display()
        self.rotate_row(col, distance)
        self.transpose_display()

    def transpose_display(self):
        """Transpose the display array, ie turn rows into columns."""
        # https://stackoverflow.com/questions/4937491/matrix-transpose-in-python
        self.display = list(map(list, zip(*self.display)))

    def execute(self, instruction):
        """Parse a text instruction and execute it."""
        mat = re.match(r'rect (\d+)x(\d+)', instruction)
        if mat:
            self.rect(int(mat.group(1)), int(mat.group(2)))
            return
        mat = re.match(r'rotate column x=(\d+) by (\d+)', instruction)
        if mat:
            self.rotate_col(int(mat.group(1)), int(mat.group(2)))
            return
        mat = re.match(r'rotate row y=(\d+) by (\d+)', instruction)
        if mat:
            self.rotate_row(int(mat.group(1)), int(mat.group(2)))
            return
        raise RuntimeError(f'Invalid instruction "{instruction}"')

    def count_pixels_lit(self):
        """Count how many pixels are turned on."""
        on = 0
        for row in self.display:
            on += sum(row)
        return on

    def __str__(self):
        viz = (' ', '■')        # map 0, 1 pixels to printable values
        return '\n'.join([''.join([viz[pixel] for pixel in row])
                          for row in self.display])


class Day08(Puzzle):
    """2016 Day 8: Two-Factor Authentication"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 8

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.screen = Screen()
        self.instructions = self.puzzle_data.input_as_lines()

    def calculate_a(self):
        """Part A: How many pixels would be lit?"""
        for instruction in self.instructions:
            self.screen.execute(instruction)
        return self.screen.count_pixels_lit()

    def calculate_b(self):
        """Part B. What does the screen display?"""
        print(self.screen)
        return 'n/a'
