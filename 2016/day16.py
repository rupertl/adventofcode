"""2016 Day 16: Dragon Checksum"""


# pylint: disable=invalid-name,too-few-public-methods

from puzzle import Puzzle


# It's about 25% quikcer if we operate on lists rather than strings.
def bin_string_to_list(s):
    """Comvert a string like '010' to [0, 1, 0]."""
    return [1 if x == '1' else 0 for x in s]


def list_to_bin_string(ls):
    """Converts a list like [0, 1, 0] to a string '010'."""
    return ''.join([str(x) for x in ls])


def generate_dragon(seed, maxLength):
    """Expand input using a fractal style algorithm."""
    out = bin_string_to_list(seed)
    while len(out) < maxLength:
        b = out.copy()
        b.reverse()
        b = [x ^ 1 for x in b]
        out += [0] + b
    return list_to_bin_string(out)[:maxLength]


def generate_checksum(data):
    """Generate a checksum for data."""
    cs = bin_string_to_list(data)
    while True:
        nextCs = []
        for index in range(0, len(cs), 2):
            nextCs.append(1 if cs[index] == cs[index + 1] else 0)
        cs = nextCs
        if len(cs) % 2 != 0:
            return list_to_bin_string(cs)


class Day16(Puzzle):
    """2016 Day 16: Dragon Checksum"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 16

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.seed = self.puzzle_data.input_as_string()

    def calculate_a(self):
        """Part A: What is the correct checksum?"""
        return generate_checksum(generate_dragon(self.seed, 272))

    def calculate_b(self):
        """Part B. What is the correct checksumwith length 35651584?"""
        return generate_checksum(generate_dragon(self.seed, 35651584))
