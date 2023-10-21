"""2016 Day 20: Firewall Rules"""


# pylint: disable=invalid-name,too-few-public-methods

import bisect
from puzzle import Puzzle


class Range():
    """Holds a low and high value for a range."""
    def __init__(self, low, high):
        self.low = low
        self.high = high

    def __str__(self):
        return f'({self.low}, {self.high})'

    def __repr__(self):
        return self.__str__()

    def __hash__(self):
        return hash(self.__str__())

    def __eq__(self, other):
        if isinstance(other, Range):
            return self.low == other.low and self.high == other.high
        return False

    # As a range has two points, it is not possible to provide a
    # strict ordering, so this is augmented by code in IPRanges.
    def __lt__(self, other):
        return self.low < other.low


class IPRanges():
    """Represents a series of sorted inclusive ranges."""
    def __init__(self, lines):
        self.ranges = []
        for line in lines:
            self.parse(line)

    def parse(self, line):
        """Convert a text line a-b to a Range and add to ranges."""
        low, high = line.split('-')
        self.insert(Range(int(low), int(high)))

    def insert(self, rng):
        """Insert (or combine) rng into existing ranges."""
        # Find where to insert it with an ordering of rng.low
        place = bisect.bisect_left(self.ranges, rng)
        self.ranges.insert(place, rng)
        # Merge any items on the right or left that overlap or adjoin
        # So (1, 7) (5, 10) (10, 20) (21, 30) -> (1, 30)
        right_i = place + 1
        while right_i < len(self.ranges):
            right = self.ranges[right_i]
            if rng.high + 1 < right.low:
                break
            rng.low = min(rng.low, right.low)
            rng.high = max(rng.high, right.high)
            del self.ranges[right_i]
        left_i = place - 1
        while left_i >= 0:
            left = self.ranges[left_i]
            if rng.low > left.high + 1:
                break
            rng.low = min(rng.low, left.low)
            rng.high = max(rng.high, left.high)
            del self.ranges[left_i]
            left_i -= 1

    def smallest_not_in_ranges(self):
        """Return the first value > 0 not covered by a range."""
        first = self.ranges[0]
        if first.low > 0:
            return 0
        return first.high + 1

    def count_not_in_ranges(self):
        """How many IP addresses are not in any range?"""
        free = 0
        for index, rng in enumerate(self.ranges):
            if index == 0:
                free += rng.low
            elif index == len(self.ranges) - 1:
                free += (2 << 31) - rng.high
            else:
                free += rng.low - self.ranges[index - 1].high - 1
        return free


class Day20(Puzzle):
    """2016 Day 20: Firewall Rules"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 20

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.ip_ranges = IPRanges(self.puzzle_data.input_as_lines())

    def calculate_a(self):
        """Part A: what is the lowest-valued IP that is not blocked?"""
        return self.ip_ranges.smallest_not_in_ranges()

    def calculate_b(self):
        """Part B. How many IPs are allowed by the blacklist?"""
        return self.ip_ranges.count_not_in_ranges()
