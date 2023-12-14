"""2023 Day 12: Hot Springs"""


# pylint: disable=invalid-name,too-few-public-methods
# pylint: disable=too-many-return-statements

import functools
import itertools
import re
from puzzle import Puzzle


@functools.cache
def count_matches_r(row, groups, run):
    """Recursively see if row matches groups and return count. run
    represents the current run of # before calling."""
    # Terminal case: end of line
    if row == "":
        return (len(groups) == 0 or
                (len(groups) == 1 and run == groups[0]))
    # Terminal case: no more groups
    if len(groups) == 0:
        return not any((ch == '#' for ch in row))
    head, tail = row[0], row[1:]
    match head:
        case '.':
            if run == 0:
                # Not in a group, keep going
                return count_matches_r(tail, groups, 0)
            if run == groups[0]:
                # Group matched, remove a group and keep going
                return count_matches_r(tail, groups[1:], 0)
        case '#':
            # Still accumulating a group
            if run < groups[0]:
                return count_matches_r(tail, groups, run + 1)
        case '?':
            # Try the two options
            return (count_matches_r('.' + tail, groups, run) +
                    count_matches_r('#' + tail, groups, run))
    return 0


class SpringRow():
    """Represents a row of springs, some of which may be broken or not,
    and a count of groups of broken springs."""
    def __init__(self, line, folded=False):
        self.row, self.groups = self.parse(line, folded)

    def parse(self, line, folded):
        """Read a line of input and return a row and group count data."""
        # ???.### 1,1,3
        row, end = line.split()
        if folded:
            row = "?".join([row] * 5)
        groups = tuple(int(num) for num in end.split(','))
        if folded:
            groups = groups * 5
        return row, groups

    def count_matches(self):
        """Count the nymber of ways row could match groups."""
        return count_matches_r(self.row, self.groups, run=0)

    # Brute force approach
    def count_matches_brute(self):
        """Find how many combinations match the group count."""
        options = [['.', '#'] if x == '?' else [x] for x in self.row]
        return sum((self.matched_brute("".join(c))
                    for c in itertools.product(*options)))

    def matched_brute(self, candidate):
        """Does the candidate match the groups count>"""
        words = re.findall(r'#+', candidate)
        return tuple(len(word) for word in words) == self.groups


class Day12(Puzzle):
    """2023 Day 12: Hot Springs"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 12

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.lines = self.puzzle_data.input_as_lines()
        self.springRowsA = [SpringRow(line) for line in self.lines]
        self.springRowsB = [SpringRow(line, folded=True) for line
                            in self.lines]

    def calculate_a(self):
        """Part A: What is the sum of those counts?"""
        return sum((sr.count_matches() for sr in self.springRowsA))

    def calculate_b(self):
        """Part B. What is the new sum of possible arrangement counts
        (with folding)?"""
        return sum((sr.count_matches() for sr in self.springRowsB))
