"""2023 Day 22: Sand Slabs"""


# pylint: disable=invalid-name,too-few-public-methods

from collections import namedtuple, defaultdict
import re
from puzzle import Puzzle


Point = namedtuple('Point', 'x y z')


def minmax(a, b):
    """Return a tuple of a.b in order."""
    return (min(a, b), max(a, b))


class Block():
    """Represents a block in 3D space."""
    def __init__(self, bid, start, end):
        self.bid = bid          # block id
        self.start = start
        self.end = end

    def __eq__(self, other):
        return ((self.start == other.start and self.end == other.end) or
                (self.start == other.end and self.end == other.start))

    def __lt__(self, other):
        if self.start.z != other.start.z:
            return self.start.z < other.start.z
        return self

    def z_high(self):
        """Return the highest z coordinate of the block."""
        return max(self.start.z, self.end.z)

    def z_low(self):
        """Return the lowest z coordinate of the block."""
        return min(self.start.z, self.end.z)

    def adjust_z(self, diff):
        """Move the block's z coordinate by diff."""
        self.start = Point(self.start.x, self.start.y, self.start.z + diff)
        self.end = Point(self.end.x, self.end.y, self.end.z + diff)

    def overlapping(self, other):
        """Is this block overlaid with the other block in any dimension?"""
        for dim in (0, 1, 2):
            selfStart, selfEnd = minmax(self.start[dim], self.end[dim])
            otherStart, otherEnd = minmax(other.start[dim], other.end[dim])
            if selfEnd < otherStart or otherEnd < selfStart:
                return False
        return True

    def overlapsAny(self, blocks):
        """Return a set of block ids if this overlaps any in the
        blocks map."""
        supporters = set()
        for block in blocks.values():
            if block.z_high() != self.z_low() or block.bid == self.bid:
                continue
            if self.overlapping(block):
                supporters.add(block.bid)
        return supporters

    def get_drop(self):
        """Calc the new position if we dropped by 1 block."""
        return (Point(self.start.x, self.start.y, self.start.z - 1),
                Point(self.end.x, self.end.y, self.end.z - 1))

    def try_drop(self, blocks, update=True):
        """Try to drop by one block. If OK, update state (if update is
        True) and return True."""
        if self.start.z <= 1 or self.end.z <= 1:
            return set()
        oldStart, oldEnd = self.start, self.end
        self.start, self.end = self.get_drop()
        supporters = self.overlapsAny(blocks)
        if len(supporters) > 0 or not update:
            self.start, self.end = oldStart, oldEnd
        return supporters


class SandSlabs():
    """Represents a 3D grid of sand blocks that fall down to earth."""
    def __init__(self, lines):
        self.z_high = 0         # Current highest z of the slab
        # b c
        # aaa
        # Which blocks support this one? supporters[b] = a
        self.supporters = defaultdict(set)
        # Which blcoks are supported by this one? supported_by[a] = b, c
        self.supported_by = defaultdict(set)
        self.blocks = {}
        self.parse(lines)

    def parse(self, lines):
        """Turn x,y,z~x,y,z lines into blocks in the grid."""
        snapshot = []
        bid = 1
        # Form the snapshot of blocks
        for line in lines:
            mat = re.match(r'(\d+),(\d+),(\d+)~(\d+),(\d+),(\d+)', line)
            nums = [int(n) for n in mat.groups()]
            block = Block(bid, Point(nums[0], nums[1], nums[2]),
                          Point(nums[3], nums[4], nums[5]))
            snapshot.append(block)
            bid += 1
        # Sort by z position and let them fall until settled
        for block in sorted(snapshot):
            self.settle(block)
            self.blocks[block.bid] = block

    # This is kind of slow - if I was doing this again I'd find a
    # better way than dropping one z level at a time, maybe by storing
    # x/y intersections.
    def settle(self, block):
        """Drop a block to the lowest possible z level (1) based on
        position of existing blocks."""
        if block.z_low() > self.z_high + 1:
            # Zoom the block down to just above the others
            block.adjust_z(block.z_low() - (self.z_high + 1))
        while True:
            supporters = block.try_drop(self.blocks)
            if len(supporters) > 0 or block.z_low() == 1:
                # We found blocks this is resting on (supporters) or
                # we are on the floor.
                self.supporters[block.bid] = supporters
                for s in supporters:
                    self.supported_by[s].add(block.bid)
                break
        self.z_high = max(self.z_high, block.z_high())

    def disintegratable(self):
        """Return the block ids that can be removed without letting
        other blocks fall."""
        can = set()
        for bid in self.blocks:
            removable = True
            dependent = self.supported_by[bid]
            for dep in dependent:
                if len(self.supporters[dep]) == 1:
                    # Only block bid supports this so cannot be removed
                    removable = False
                    break
            if removable:
                can.add(bid)
        return can

    def chain_reaction(self):
        """For each brick removed, find out how manu others would
        disintegrate.."""
        results = {}
        for bid in self.blocks:
            removed = {bid}
            queue = [bid]
            while queue:
                block = queue.pop()
                dependent = self.supported_by[block]
                for dep in dependent:
                    if len(self.supporters[dep].difference(removed)) == 0:
                        removed.add(dep)
                        queue.append(dep)
            results[bid] = len(removed) - 1  # -1 for origibal block removed
        return results


class Day22(Puzzle):
    """2023 Day 22: Sand Slabs"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 22

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        lines = self.puzzle_data.input_as_lines()
        self.slab = SandSlabs(lines)

    def calculate_a(self):
        """Part A: Figure how the blocks will settle based on the
        snapshot. Once they've settled, consider disintegrating a
        single brick; how many bricks could be safely chosen as the
        one to get disintegrated?"""
        return len(self.slab.disintegratable())

    def calculate_b(self):
        """Part B. For each brick, determine how many other bricks
        would fall if that brick were disintegrated. What is the sum
        of the number of other bricks that would fall?"""
        return sum(self.slab.chain_reaction().values())
