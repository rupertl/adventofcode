"""2023 Day 18: Lavaduct Lagoon"""


# pylint: disable=invalid-name,too-few-public-methods

from puzzle import Puzzle


class LavaLagoon:
    """Holds a series of pits dug that form a lagoon."""
    def __init__(self, lines, swapped=False):
        self.segments = []      # list of (from-x-y, to-x-y) segments
        self.perimeter = 0      # total length of line being dug
        self.parse(lines, swapped)

    # DIg direction to x/y diff
    compass = {'U': (0, 1), 'D': (0, -1), 'R': (1, 0), 'L': (-1, 0)}

    def parse(self, lines, swapped):
        """Parse lines of puzzle text into a series of line segments."""
        x, y = 0, 0
        for line in lines:
            direction, length, colour = line.split()
            if swapped:
                direction, length = self.swap(colour)
            else:
                length = int(length)
            nextX = x + (LavaLagoon.compass[direction][0] * length)
            nextY = y + (LavaLagoon.compass[direction][1] * length)
            self.perimeter += length
            self.segments.append(((x, y), (nextX, nextY)))
            x, y = nextX, nextY

    def pit_size(self):
        """Count the number of squares dug in the pit."""
        # Using the Shoelace algorithm as implemented at
        # https://stackoverflow.com/questions/451426/\
        #    how-do-i-calculate-the-area-of-a-2d-polygon/717367#717367
        # plus the perimeter to find the dug area
        return 1 + (self.perimeter +
                    abs(sum(x0 * y1 - x1 * y0
                            for ((x0, y0), (x1, y1)) in self.segments))) // 2

    # Map digit to direction if swapped
    encoded_direction = ['R', 'D', 'L', 'U']

    def swap(self, colour):
        """Transform the 6 hex digit colour code to a direction and
        length."""
        # Input like "(#70c710)"
        # First five hex digits are length
        # Last hex digit is encoded direction
        length = int(colour[2:-2], 16)
        direction = LavaLagoon.encoded_direction[int(colour[-2])]
        return (direction, length)


class Day18(Puzzle):
    """2023 Day 18: Lavaduct Lagoon"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 18

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        lines = self.puzzle_data.input_as_lines()
        self.lagoonA = LavaLagoon(lines)
        self.lagoonB = LavaLagoon(lines, swapped=True)

    def calculate_a(self):
        """Part A: The Elves are concerned the lagoon won't be large
        enough; if they follow their dig plan, how many cubic meters
        of lava could it hold?"""
        return self.lagoonA.pit_size()

    def calculate_b(self):
        """Part B. Convert the hexadecimal color codes into the
        correct instructions; if the Elves follow this new dig plan,
        how many cubic meters of lava could the lagoon hold?"""
        return self.lagoonB.pit_size()
