"""2016  Day 9: Explosives in Cyberspace"""


# pylint: disable=invalid-name,too-few-public-methods


import re
from collections import namedtuple
from puzzle import Puzzle

MarkedSection = namedtuple('MarkedSection', 'times offset payload')


class Message():
    """Represents a compressed message where we want to find the
    decomrpessed length. There are two different algorithm versions to
    handle."""
    def __init__(self, cmessage, version=1):
        self.cmessage = cmessage
        assert version in (1, 2)
        self.version = version

    def length(self):
        """What would be the length of the message when decompressed?"""
        length = 0
        remaining = self.cmessage
        # Remove parts of the message (from the front) until none is left.
        while len(remaining) > 0:
            nextBracket = remaining.find('(')
            if nextBracket >= 0:
                # Account for the text before the bracket
                length += nextBracket
                remaining = remaining[nextBracket:]
                # Find details of the marked section and adjust the
                # length based on the algorithm version.
                marked = self.get_marked_section(remaining)
                if self.version == 1:
                    length += marked.times * len(marked.payload)
                else:
                    # Recursively expand this sectio9n
                    length += marked.times * Message(
                        marked.payload, self.version).length()
                # Proceed to the text after the marked section
                remaining = remaining[marked.offset:]
            else:
                # Go to the end
                length += len(remaining)
                remaining = ""
        return length

    def get_marked_section(self, section):
        """Decompose a section like (1x5)B to the number of times to
        repeat, the payload to repeat and the offset to skip past this
        section"""
        mat = re.match(r'\((\d+)x(\d+)\)', section)
        if not mat:
            raise RuntimeError(f'Bad marker starting at {section}')
        marker_len = mat.span()[1]
        payload_len = int(mat.group(1))
        times = int(mat.group(2))
        offset = marker_len + payload_len
        payload = section[marker_len:offset]
        return MarkedSection(times, offset, payload)


class Day09(Puzzle):
    """2016 Day 9: Explosives in Cyberspace"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 9

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.cmessage = self.puzzle_data.input_as_string()

    def calculate_a(self):
        """Part A: What is the decompressed length?"""
        return self.solve(1)

    def calculate_b(self):
        """Part B. What is the decompressed length using algorithm v2?"""
        return self.solve(2)

    def solve(self, version):
        """Solve the puzzle for the given algorithm version."""
        return Message(self.cmessage, version).length()
