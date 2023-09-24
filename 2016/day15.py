"""2016 Day 15: Timing is Everything"""


# pylint: disable=invalid-name,too-few-public-methods

import re
from puzzle import Puzzle


class Disk():
    """Represents a disk with several slots (npos) that rotates, with
    the starting position given by start."""
    def __init__(self, npos, start):
        self.npos = npos
        self.start = start

    def __eq__(self, other):
        if isinstance(other, Disk):
            return self.npos == other.npos and self.start == other.start
        return False

    def position(self, time):
        """What is the position at the given time?"""
        return (self.start + time) % self.npos

    def is_open(self, time):
        """Returns true if the disk is open (position = 0) at time."""
        return self.position(time) == 0


class Sculpture():
    """Represents a sculpture with rotating disk where we want to find
    a time that will allow a capsule to pass through slots on the disk
    to get to the bottom."""
    def __init__(self, lines):
        self.disks = []
        self.parse(lines)

    def add_disk(self, npos, start):
        """Add a new disk to the sculpture."""
        self.disks.append(Disk(npos, start))

    def parse(self, lines):
        """Parse the incoming list of strings for disks and
        positions."""
        for line in lines:
            # We assume discs are in ascending order
            rex = (r'Disc #\d+ has (\d+) positions; ' +
                   r'at time=0, it is at position (\d+).')
            mat = re.match(rex, line)
            if not mat:
                raise RuntimeError(f'Bad line {line}')
            self.add_disk(int(mat.group(1)), int(mat.group(2)))

    def all_disks_open(self, start_time):
        """At a given start time, and assuming each disk below takes
        one second to get to, are all disks open?"""
        for index, disk in enumerate(self.disks):
            if not disk.is_open(start_time + index + 1):
                return False
        return True

    def get_drop_time(self):
        """Determine the best time to drop the capsule to get through all
        the disks."""
        time = 0
        while True:
            if self.all_disks_open(time):
                return time
            time += 1


class Day15(Puzzle):
    """2016 Day 15: Timing is Everything"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 15

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.sculpture = Sculpture(self.puzzle_data.input_as_lines())

    def calculate_a(self):
        """Part A: What is the first time you can press the button to
        get a capsule?"""
        return self.sculpture.get_drop_time()

    def calculate_b(self):
        """Part B. With this new disc, what is the first time you can
        press the button to get another capsule?"""
        self.sculpture.add_disk(11, 0)
        return self.sculpture.get_drop_time()
