"""2016 Day 14: One-Time Pad"""


# pylint: disable=invalid-name,too-few-public-methods

import collections
import hashlib
from puzzle import Puzzle


def find_runs(s):
    """Find runs of 3 and 5 repeated characters in s."""
    last_char = -1
    run = 0
    run3 = None
    run5s = set()
    for ch in s:
        if ch == last_char:
            run += 1
            if run == 3 and not run3:
                run3 = ch
            if run == 5:
                run5s.add(ch)
        else:
            last_char = ch
            run = 1
    return (run3, run5s)


class Keygen:
    """Represents a key generator using MD5."""
    def __init__(self, salt):
        self.salt = salt

    def find_keys(self, num_keys, stretch=0):
        """Find num_keys by using the run-of-digits algorithm
        specififed in the puzzle. Rehash the candidate hash stretch
        times."""
        keys = []
        # Map runs of 3 to a ascending list of the starting keys
        watches = collections.defaultdict(list)
        hasher_base = hashlib.new('md5')
        hasher_base.update(self.salt.encode())
        index = 0
        while True:
            index += 1
            hasher = hasher_base.copy()
            hasher.update(str(index).encode())
            candidate = hasher.hexdigest()
            for _ in range(stretch):
                candidate = hashlib.md5(candidate.encode()).hexdigest()
            run3, run5s = find_runs(candidate)
            if run3:
                watches[run3].append(index)
            # We could in theoru have more than one run of 5
            # characters, but this did not happen, at least in my data
            # set or the sample.
            for run5 in run5s:
                if run5 not in watches:
                    continue
                # Take a copy as we will be removing items from the
                # original.
                starts = watches[run5].copy()
                for start in starts:
                    if index - start <= 1000 and index != start:
                        key = (run5, start, index)
                        keys.append(key)
                        watches[run5].remove(start)
                        if len(keys) == num_keys:
                            return start


class Day14(Puzzle):
    """2016 Day 14: One-Time Pad"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 14

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.keygen = Keygen(self.puzzle_data.input_as_string())

    def calculate_a(self):
        """Part A: What index produces your 64th one-time pad key?"""
        return self.keygen.find_keys(64)

    def calculate_b(self):
        """Part B. With stretching, what index now produces your 64th
        one-time pad key?"""
        return self.keygen.find_keys(64, 2016)
