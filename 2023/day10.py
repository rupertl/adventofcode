"""2023 Day 10: Pipe Maze"""


# pylint: disable=invalid-name,too-few-public-methods

from collections import namedtuple
from puzzle import Puzzle

Point = namedtuple('Point', 'row col')

# Compass points offsets
N = (-1, 0)
S = (1, 0)
E = (0, 1)
W = (0, -1)

# Marker for when we try to access something outside the maze.
OUT_OF_RANGE = '0'

# Given a maze cell contents, what directions can we go?
FROM_TRANSITIONS = {
    '|': {N, S},
    '-': {E, W},
    'L': {N, E},
    'J': {N, W},
    '7': {S, W},
    'F': {S, E},
    'S': {N, S, E, W}
}

# If we are going a certain direction, what destination cell contents
# are acceptable#?
TO_TRANSITIONS = {
    N: {'|', '7', 'F', 'S'},
    S: {'|', 'L', 'J', 'S'},
    E: {'-', 'J', '7', 'S'},
    W: {'-', 'L', 'F', 'S'}
}


class PipeMaze():
    """Represents a maze of pipes where we want to find distances from
    the starting point S."""
    def __init__(self, lines):
        self.maze = lines
        self.start = self.find('S')
        self.maxRow = len(self.maze)
        self.maxCol = len(self.maze[0])
        self.loop = self.find_loop()
        self.remove_junk()

    def find(self, value):
        """Find a point in the maze based on value"""
        for row, line in enumerate(self.maze):
            for col, ch in enumerate(line):
                if ch == value:
                    return Point(row, col)
        return None

    def at(self, point):
        """Return the value of the maze at point, or . if not valid."""
        if (point.row < 0 or point.row >= self.maxRow or
           point.col < 0 or point.col >= self.maxCol):
            return OUT_OF_RANGE
        return self.maze[point.row][point.col]

    def valid_move(self, point):
        """Return a list of valid moves from the given point."""
        moves = set()
        for diff in FROM_TRANSITIONS[self.at(point)]:
            move = Point(point.row + diff[0], point.col + diff[1])
            if self.at(move) in TO_TRANSITIONS[diff]:
                moves.add(move)
        return moves

    def find_loop(self):
        """Find the points on the loop."""
        # Each point on the loop must have exactly two exits. Pick one
        # exit from start and follow it until we reach start again,
        loop = {self.start}
        prev = self.start
        curr = list(self.valid_move(self.start))[0]
        while curr != self.start:
            loop.add(curr)
            choices = self.valid_move(curr)
            choices.remove(prev)
            prev = curr
            curr = list(choices)[0]
        return loop

    def farthest(self):
        """Find the farthest point in the loop from S."""
        # The fathest point must be half of the loop length
        return len(self.loop) // 2

    def remove_junk(self):
        """Remove all non-loop pipes from the maze."""
        newMaze = []
        for row, line in enumerate(self.maze):
            newLine = ""
            for col, ch in enumerate(line):
                if ch not in {' ', '.'} and Point(row, col) not in self.loop:
                    ch = '.'
                newLine += ch
            newMaze.append(newLine)
        self.maze = newMaze

    def bounding_box(self):
        """Find the top left and bottom points that form a square
        containing all loop items."""
        maxRow, maxCol = 0, 0
        minRow, minCol = self.maxRow, self.maxCol
        for point in self.loop:
            minRow = min(minRow, point.row)
            minCol = min(minCol, point.col)
            maxRow = max(maxRow, point.row)
            maxCol = max(maxCol, point.col)
        return (Point(minRow, minCol), Point(maxRow, maxCol))

    def find_internal(self):
        """Return the points which are internal to the loop."""
        bb = self.bounding_box()
        candidates = self.find_inside(bb)
        # For each of these, is there a path to the bounding box?
        internals = set()
        externals = set()
        while candidates:
            point = candidates.pop()
            adjacents = self.find_adjacent(point)
            if any((self.outside(bb, point) for point in adjacents)):
                externals.update(adjacents)
            else:
                internals.update(adjacents)
            candidates.difference_update(adjacents)
        return internals

    def find_inside(self, bb):
        """Find points inside the bounding box bb that are not the loop."""
        candidates = set()
        for row in range(bb[0].row+1, bb[1].row):
            for col in range(bb[0].col+1, bb[1].col):
                point = Point(row, col)
                if point not in self.loop:
                    candidates.add(point)
        return candidates

    def find_adjacent(self, point):
        """Find all points adjacent to point."""
        # This could be optimized further.
        adjacents = {point}
        queue = [point]
        while queue:
            candidate = queue.pop()
            for diff in (N, S, E, W):
                next_candidate = Point(candidate.row + diff[0],
                                       candidate.col + diff[1])
                if (next_candidate not in adjacents and
                    next_candidate not in self.loop and
                   self.at(next_candidate) != OUT_OF_RANGE):
                    adjacents.add(next_candidate)
                    queue.append(next_candidate)
        return adjacents

    def outside(self, bb, point):
        """Returns True if point outside the bounding box bb."""
        return (point.row < bb[0].row or
                point.row > bb[1].row or
                point.col < bb[0].col or
                point.col > bb[1].col)

    def find_internal_count(self):
        """Find the number of internals in the loop."""
        items = self.find_internal()
        return sum((self.at(item) != ' ' for item in items))


# To handle the case of the target 'squeezing between the pipes' we
# blow up the map to reflect these spaces.
#
# Map single maze values to a 3x3 grid. Use blank to fill in the gaps
# to distibguish from real empty squares (.)
BLOW_UP_MAP = {
    '|': [" | ",
          " | ",
          " | "],
    '-': ["   ",
          "---",
          "   "],
    'L': [" | ",
          " L-",
          "   "],
    'J': [" | ",
          "-J ",
          "   "],
    '7': ["   ",
          "-7 ",
          " | "],
    'F': ["   ",
          " F-",
          " | "],
    '.': ["   ",
          " . ",
          "   "]
}


def blow_up(pipeMaze):
    """Make the maze three times its current size to account for
    the escape between pipes issue."""
    output = []
    for row, line in enumerate(pipeMaze.maze):
        l1, l2, l3 = "", "", ""
        for col, ch in enumerate(line):
            if ch == 'S':
                # Special case: find what should be in place and
                # adjust the 3x3 result.
                pasted = {N: ' ', S: ' ', E: ' ', W: ' '}
                for diff, allowed in TO_TRANSITIONS.items():
                    move = Point(row + diff[0], col + diff[1])
                    if pipeMaze.at(move) in allowed:
                        pasted[diff] = ('|' if diff in {N, S}
                                        else '-')
                l1 += f" {pasted[N]} "
                l2 += f"{pasted[W]}S{pasted[E]}"
                l3 += f" {pasted[S]} "
            else:
                l1 += BLOW_UP_MAP[ch][0]
                l2 += BLOW_UP_MAP[ch][1]
                l3 += BLOW_UP_MAP[ch][2]
        output += [l1, l2, l3]
    return PipeMaze(output)


class Day10(Puzzle):
    """2023 Day 10: Pipe Maze"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 10

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        lines = self.puzzle_data.input_as_lines()
        self.pipeMaze = PipeMaze(lines)

    def calculate_a(self):
        """Part A: How many steps along the loop does it take to get
        from the starting position to the point farthest from the
        starting position?"""
        return self.pipeMaze.farthest()

    def calculate_b(self):
        """Part B. How many tiles are enclosed by the loop?"""
        bigPm = blow_up(self.pipeMaze)
        return bigPm.find_internal_count()
