"""2016 Day 18: Like a Rogue"""


# pylint: disable=invalid-name,too-few-public-methods

from puzzle import Puzzle


class TrapRoom():
    """Represents a room with traps and safe tiles, where each row can
    be inferred from the previous row."""
    def __init__(self, first_row_str):
        self.rows = [self.parseRowStr(first_row_str)]
        self.row_len = len(self.rows[0])

    def parseRowStr(self, row_str):
        """Parse a string of ^ (trap) and . (safe) to a list of bools
        (trao=False, safe=True)."""
        return [ch == '.' for ch in row_str]

    def count_safe(self):
        """How many tiles in the map are safe>"""
        return sum(sum(row) for row in self.rows)

    def generate_rows(self, target_rows):
        """Keep generating rows until the map size reaches numRows."""
        while len(self.rows) < target_rows:
            self.generate_row()

    def generate_row(self):
        """Generate a row of the map according to the algorithm."""
        # We adapt last row to include the off the edge cases
        last_row = [True] + self.rows[-1:][0] + [True]
        next_row = []
        for index in range(1, self.row_len + 1):
            left = last_row[index - 1]
            right = last_row[index + 1]
            # The following combinations of l/c/r yield trap (False)
            # 001 100 011 110
            # This can be simplified into: not left xor right
            next_row.append(not left ^ right)
        self.rows.append(next_row)


class Day18(Puzzle):
    """2016 Day 18: Like a Rogue"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 18

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.trap_room = TrapRoom(self.puzzle_data.input_as_string())
        self.target_rows_a = 40
        self.target_rows_b = 400000

    def calculate_a(self):
        """Part A: how many safe tiles are there in a room of 40 rows?"""
        self.trap_room.generate_rows(self.target_rows_a)
        return self.trap_room.count_safe()

    def calculate_b(self):
        """Part B. How many safe tiles are there in a total of 400000
        rows?"""
        self.trap_room.generate_rows(self.target_rows_b)
        return self.trap_room.count_safe()
