"""2016 Day 4: Security Through Obscurity"""


# pylint: disable=invalid-name,too-few-public-methods


import re
import string
from puzzle import Puzzle


class CountedLetter():
    """Associates a letter with a count, and a sorting rule that looks
    at count first and letter second."""
    def __init__(self, letter):
        self.letter = letter
        self.count = 0

    def __str__(self):
        return self.letter

    def __eq__(self, other):
        return self.letter == other.letter and self.count == other.count

    def __lt__(self, other):
        # Ascending order is by count and then by descending letter
        if self.count < other.count:
            return True
        return self.letter > other.letter

    def inc(self):
        """Increment the count for this letter."""
        self.count += 1
        return self


class Room():
    """Represents a room with a name, sector ID and checksum."""
    def __init__(self, room_id):
        (self.name, self.sector_id,
         self.supplied_checksum) = self.parse_room_id(room_id)
        self.calculated_checksum = self.calculate_checksum()
        self.real = self.supplied_checksum == self.calculated_checksum

    def parse_room_id(self, room_id):
        """Convert a room ID into a name, sector and checksum."""
        mat = re.search(r'([a-z-]+)-([0-9]+)\[([a-z]+)\]', room_id)
        if not mat:
            raise RuntimeError(f'Room ID {room_id} not parsable')
        return (mat.group(1), int(mat.group(2)), mat.group(3))

    def calculate_checksum(self):
        """Calculate what the checksum should be for this room."""
        counts = [CountedLetter(letter) for letter in string.ascii_lowercase]
        for letter in self.name:
            if letter == '-':
                continue
            assert letter in string.ascii_lowercase
            counts[ord(letter) - ord('a')].inc()
        top5 = sorted(counts, reverse=True)[:5]
        return ''.join((str(cl) for cl in top5))

    def decrypt_name(self):
        """Decrypt the room name by rotating letters sector_id times."""
        return ''.join((self.rotate_letter(letter, self.sector_id)
                        for letter in self.name))

    def rotate_letter(self, letter, rotation):
        """Rotate letter by rotation, wrapping around z -> a. Assuming
        letter in range a-z or - already."""
        if letter == '-':
            return ' '
        letter_index_in = ord(letter) - ord('a')
        letter_index_out = (letter_index_in + rotation) % 26
        return chr(ord('a') + letter_index_out)


class Day04(Puzzle):
    """2016 Day 4: Security Through Obscurity"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 4

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        room_ids = self.puzzle_data.input_as_lines()
        self.rooms = [Room(room_id) for room_id in room_ids]

    def calculate_a(self):
        """Part A: What is the sum of sector IDs for valid rooms?"""
        return sum((room.sector_id for room in self.rooms if room.real))

    def calculate_b(self):
        """Part B. What is the sector ID of the room where North Pole
        objects are stored?"""
        # It's not clear from the instructions if this room name will
        # be exactly the same in every puzzle input.
        target = 'northpole object storage'
        for room in self.rooms:
            if room.real and room.decrypt_name() == target:
                return room.sector_id
        raise RuntimeError(f"room '{target}' not found in input")
