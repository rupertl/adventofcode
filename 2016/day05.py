"""2016 Day 5: How About a Nice Game of Chess?"""


# pylint: disable=invalid-name,too-few-public-methods


import hashlib
from puzzle import Puzzle


class Cracker():
    """Cracks the password based on the room ID. If sequential is
    true, assemble password in order of discovery, if not use hash
    char 6 as index."""
    def __init__(self, room_id, sequential=True):
        self.room_id = room_id
        self.sequential = sequential
        self.password_length = 8
        # number of digits to identify if we want to shorten the
        # process for testing
        self.rounds = 8

    def decode(self):
        """Decodes the password."""
        unknown = '-'
        password = self.password_length * [unknown]
        hasher_base = hashlib.new('md5')
        hasher_base.update(self.room_id.encode())
        index = 0
        rounds = 0
        while rounds < self.rounds:
            hasher_index = hasher_base.copy()
            hasher_index.update(str(index).encode())
            result = hasher_index.hexdigest()
            if result[:5] == "00000":
                if self.sequential:
                    password[rounds] = result[5]
                    rounds += 1
                elif (self.valid_index(result[5]) and
                      password[int(result[5])] == unknown):
                    password[int(result[5])] = result[6]
                    rounds += 1
            index += 1
        return ''.join(password)

    def valid_index(self, index_char):
        """Is index_char a valid index? Note using chars not ints."""
        return ord(index_char) in range(ord('0'), ord('7') + 1)


class Day05(Puzzle):
    """2016 Day 5: How About a Nice Game of Chess?"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 5

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.cracker = Cracker(self.puzzle_data.input_as_string())

    def calculate_a(self):
        """Part A: What is the password for the door?"""
        self.cracker.sequential = True
        return self.cracker.decode()

    def calculate_b(self):
        """Part B. What is the password using the non-sequential method?"""
        self.cracker.sequential = False
        return self.cracker.decode()
