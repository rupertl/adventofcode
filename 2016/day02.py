"""2016 Day 1: Bathroom Security"""


# pylint: disable=invalid-name,too-few-public-methods


from collections import namedtuple
from puzzle import Puzzle


# row/column point, top left is 0,0
Point = namedtuple('Point', 'row col')

# Keypad layouts, '' means no key at that position
# Assuming that each row has the same numner of keys
KEYPAD_1_9 = (
    ('1', '2', '3'),
    ('4', '5', '6'),
    ('7', '8', '9')
)

KEYPAD_1_D = (
    ('',   '', '1',  '', ''),
    ('',  '2', '3', '4', ''),
    ('5', '6', '7', '8', '9'),
    ('',  'A', 'B', 'C', ''),
    ('',   '', 'D',  '', ''),
)


class Keypad():
    """Models a keypad to find what key is pointed to after several
    directional moves. Repeating this several times yields a code."""
    def __init__(self, template=KEYPAD_1_9):
        self.template = template
        self.max = len(template[0]) - 1
        self.position = self.find_key('5')
        self.code = ""                # The keypad code after each set of moves

    def find_key(self, val):
        """Find the key in the temmplate and return the position."""
        for row, row_val in enumerate(self.template):
            for col, col_val in enumerate(row_val):
                if col_val == val:
                    return Point(row, col)
        raise RuntimeError(f"Can't find {val} in keypad template")

    def key_at(self, position):
        """Return the number on the keypad being pointed to by position."""
        return self.template[position.row][position.col]

    def current_key(self):
        """Return the number on the keypad being pointed to at present."""
        return self.key_at(self.position)

    def move(self, direction):
        """If valid, move position based on direction."""
        if direction == 'U':
            new_position = Point(self.position.row - 1, self.position.col)
        elif direction == 'D':
            new_position = Point(self.position.row + 1, self.position.col)
        elif direction == 'L':
            new_position = Point(self.position.row, self.position.col - 1)
        elif direction == 'R':
            new_position = Point(self.position.row, self.position.col + 1)
        else:
            raise RuntimeError(f'Bad move directionb {direction}')
        # Update position if it is a valid point
        if self.valid_position(new_position):
            self.position = new_position

    def valid_position(self, position):
        """Returns true if position points at a valid key on the keypad."""
        return (position.row >= 0 and position.row <= self.max and
                position.col >= 0 and position.col <= self.max and
                self.key_at(position) != '')

    def apply_moves(self, moves):
        """Apply a set of moves and store the number we are pointing to."""
        for move in moves:
            self.move(move)
        self.code += str(self.current_key())

    def get_code(self):
        """Get the code revealed after moving."""
        return self.code


class Day02(Puzzle):
    """2016 Day 2: Bathroom Security"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 2

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.moves = self.parse_input(self.puzzle_data.input_as_lines())

    def parse_input(self, lines):
        """Convert lines of moves like UDL into an array of arrays"""
        return [list(line) for line in lines]

    def calculate_a(self):
        """Part A: Keypad code with regular 1-9 keypad"""
        return self.solve(KEYPAD_1_9)

    def calculate_b(self):
        """Part B. Keypad code with strange 1-D keypad"""
        return self.solve(KEYPAD_1_D)

    def solve(self, keypad_layout):
        """Solve for a given keypad layout."""
        k = Keypad(keypad_layout)
        for moves in self.moves:
            k.apply_moves(moves)
        return k.get_code()
