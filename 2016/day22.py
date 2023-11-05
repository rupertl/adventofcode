"""2016 Day 22: Grid Computing"""


# pylint: disable=invalid-name,too-few-public-methods

import re
from collections import namedtuple
from puzzle import Puzzle


Disk = namedtuple('Disk', 'size used')


class DiskGrid():
    """Represents a grid of Disks where we can move data to adjacent
    disks."""
    def __init__(self, disk_info):
        self.size_x, self.size_y = self.get_extents(disk_info)
        self.num_disks = (self.size_x) * (self.size_y)
        self.disk_sizes = [[0 for _ in range(self.size_y)]
                           for _ in range(self.size_x)]
        self.disk_used = [[0 for _ in range(self.size_y)]
                          for _ in range(self.size_x)]
        self.parse(disk_info)

    def parse(self, disk_info):
        """Parse the df style input to populate fields."""
        for disk in disk_info:
            mat = re.match(r'.*node-x(\d+)-y(\d+)\s+(\d+)T\s+(\d+)T', disk)
            if mat:
                x, y, size, used = [int(g) for g in mat.groups()]
                self.disk_sizes[x][y] = size
                self.disk_used[x][y] = used

    def get_extents(self, disk_info):
        """Find the size of the grid by scanning for max x and y values."""
        xs, ys = [], []
        for disk in disk_info:
            mat = re.match(r'.*node-x(\d+)-y(\d+)', disk)
            if mat:
                x, y = [int(g) for g in mat.groups()]
                xs.append(x)
                ys.append(y)
        # Index 0 so max values are exclusive
        return max(xs) + 1, max(ys) + 1

    def get_viable_pairs(self):
        """Get pairs of disks where A would fit on B."""
        # This is O(n^2) for n nodes so not super efficient, but runs
        # quickly enough for the given puzzle data.
        pairs = []
        for ax in range(self.size_x):
            for ay in range(self.size_y):
                required = self.disk_used[ax][ay]
                if required == 0:
                    continue
                for bx in range(self.size_x):
                    for by in range(self.size_y):
                        if ax == bx and ay == by:
                            continue
                        free = self.disk_sizes[bx][by] - self.disk_used[bx][by]
                        if free >= required:
                            pairs.append(((ax, ay), (bx, by)))
        return pairs

    def __str__(self):
        """Summarise the disk grid."""
        output = ""
        for y in range(self.size_y):
            line = []
            for x in range(self.size_x):
                if x == 0 and y == 0:
                    line.append('T')
                elif x == self.size_x - 1 and y == 0:
                    line.append('G')
                elif self.disk_used[x][y] == 0:
                    line.append('_')
                elif self.unmovable_node(x, y):
                    line.append('#')
                else:
                    line.append('.')
                if x == self.size_x - 1:
                    output += ''.join(line) + '\n'
        return output

    def unmovable_node(self, x, y):
        """Returns true if this node could not be moved as has too
        much disk used."""
        return self.disk_used[x][y] / self.disk_sizes[x][y] > 0.85

    def find_empty_node(self):
        """Find a node with no disk space used (or if more then one,
        the closest to the top left."""
        for x in range(self.size_x):
            for y in range(self.size_y):
                if self.disk_used[x][y] == 0:
                    return x, y
        raise RuntimeError('no empty node found')

    def count_move_top_right_to_left(self):
        """Return how many moves it would take to move data from the
        top right node to the top left node."""
        # This is not a general path finding solution, instead we used
        # a heuristic based on the data. Given something like this
        # T.......................G
        # .........................
        # .........................
        # ..............###########
        # .........................
        # .................._......
        # Count the moves as follows
        # 1. Locate the empty node
        x, y = self.find_empty_node()
        moves = 0
        # 2. Move the empty node to the top row
        # If we hit a unmovable node, go left until clear.
        while y > 0:
            if self.unmovable_node(x, y-1):
                x -= 1
                if x < 0:
                    raise RuntimeError('could not find path')
            else:
                y -= 1
            moves += 1
        # 3. Move the space to be next to the goal
        moves += self.size_x - 2 - x
        x = self.size_x - 2
        # We now look like this
        # T......................_G
        # .........................
        # 4. Swap space and goal, and now treat x as the position of
        # the goal.
        # T......................G_
        # .........................
        moves += 1
        # 5. Move the goal left, which takes 5 moves to reposition the
        # empty space and move the goal. Do this until we reach the
        # top left.
        moves += 5 * x
        return moves


class Day22(Puzzle):
    """2016 Day 22: Grid Computing"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 22

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.disk_grid = DiskGrid(self.puzzle_data.input_as_lines())

    def calculate_a(self):
        """Part A: How many viable pairs of nodes are there?"""
        return len(self.disk_grid.get_viable_pairs())

    def calculate_b(self):
        """Part B. What is the fewest number of steps required to move
        your goal data to node-x0-y0?"""
        return self.disk_grid.count_move_top_right_to_left()
