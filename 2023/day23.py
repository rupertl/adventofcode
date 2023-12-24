"""2023 Day 23: A Long Walk"""


# pylint: disable=invalid-name,too-few-public-methods

from collections import namedtuple
from puzzle import Puzzle

Point = namedtuple('Point', 'row col')

# Compass points offsets and valid hill moves
N = (-1, 0, '^')
S = (1, 0, 'v')
E = (0, 1, '>')
W = (0, -1, '<')

HILLS = ('<', '>', '^', 'v')
ALLOWED = ('.', ) + HILLS

# Marker for when we try to access something outside the grid.
OUT_OF_RANGE = 0


class SnowIsland():
    """Represents a 2D island with paths, forests and slopes."""
    def __init__(self, lines, dryHills=False):
        self.dryHills = dryHills
        self.parse(lines)
        self.movesCache, junctions = self.valid_moves_map()
        self.junctionMap = self.build_junctions_map(junctions)

    def parse(self, lines):
        """Parse lines of puzzle input into the 2D grid."""
        self.maxRow = len(lines)
        self.maxCol = len(lines[0])
        self.grid = lines
        self.start = Point(0, 1)
        self.end = Point(self.maxRow - 1, self.maxCol - 2)
        assert self.at(self.end) == '.'

    def at(self, point):
        """Return the value of the grid at point, or 0 if not valid."""
        if (point.row < 0 or point.row >= self.maxRow or
           point.col < 0 or point.col >= self.maxCol):
            return OUT_OF_RANGE
        return self.grid[point.row][point.col]

    def valid_moves(self, point):
        """Return where we can travel to from point."""
        valid = []
        for rowDiff, colDiff, downhill in (N, S, E, W):
            nextPoint = Point(point.row + rowDiff, point.col + colDiff)
            nextTerrain = self.at(nextPoint)
            if nextTerrain not in ALLOWED:
                continue
            currTerrain = self.at(point)
            if currTerrain in HILLS and not self.dryHills:
                if currTerrain == downhill:
                    # Step downhill from a hill tile
                    valid.append(nextPoint)
            else:
                valid.append(nextPoint)
        return valid

    def valid_moves_map(self):
        """Build a map of points to moves to speed up path checking."""
        cache = {}              # maps point to list of points
        junctions = [self.start, self.end]
        for rowIndex, row in enumerate(self.grid):
            for colIndex, ch in enumerate(row):
                if ch == '#':
                    continue
                point = Point(rowIndex, colIndex)
                validMoves = self.valid_moves(point)
                if len(validMoves) >= 3:
                    junctions.append(point)
                cache[point] = validMoves
        return cache, junctions

    def build_junctions_map(self, junctions):
        """Build a map of junction -> junction paths."""
        junctionMap = {}
        for junction in junctions:
            paths = self.find_junction_paths(junction, junctions)
            junctionMap[junction] = paths
        # Hack: at the last junction before the exit, we must choose
        # the path to the exit.
        if self.dryHills:
            lastJunction, lastJunctionPath = list(junctionMap[self.end])[0]
            junctionMap[lastJunction] = {(self.end, lastJunctionPath)}
        return junctionMap

    def find_junction_paths(self, start, junctions):
        """Find all paths from start to other junctions."""
        validPaths = set()
        queue = [(start, {start})]
        while queue:
            last, visited = queue.pop()
            for move in self.movesCache[last]:
                if move in visited:
                    continue
                newVisited = visited.copy()
                newVisited.add(move)
                if move in junctions and move != start:
                    validPaths.add((move, frozenset(newVisited)))
                    continue
                queue.append((move, newVisited))
        return validPaths

    def longest_path_via_junctions(self):
        """Find the longest path, hopping from one junction to another,"""
        validPaths = set()
        queue = [(self.start, {self.start})]  # last point and points reached
        iterations = 0
        while queue:
            iterations += 1
            if iterations > 3000000:
                # Hack: we converge by this point
                break
            last, visited = queue.pop()
            for junction, pathVisited in self.junctionMap[last]:
                if junction in visited:
                    continue
                newVisited = visited.union(pathVisited)
                if junction == self.end:
                    validPaths.add(len(newVisited) - 1)
                    continue
                queue.append((junction, newVisited))
        return max(validPaths)

    def longest_path_brute(self):
        """Find the longest valid path by enumerating them all."""
        validPaths = set()
        queue = [(self.start, set(self.start))]
        iterations = 0
        while queue:
            last, visited = queue.pop()
            iterations += 1
            for move in self.movesCache[last]:
                if move in visited:
                    continue
                if move == self.end:
                    validPaths.add(len(visited) - 1)
                    continue
                newVisited = visited.copy()
                newVisited.add(move)
                queue.append((move, newVisited))
        return max(validPaths)


class Day23(Puzzle):
    """2023 Day 23: A Long Walk"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 23

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        lines = self.puzzle_data.input_as_lines()
        self.islandA = SnowIsland(lines)
        self.islandB = SnowIsland(lines, dryHills=True)

    def calculate_a(self):
        """Part A: How many steps long is the longest hike?"""
        return self.islandA.longest_path_via_junctions()

    def calculate_b(self):
        """Part B. Assuming slopes are OK, how many steps long is the
        longest hike?"""
        return self.islandB.longest_path_via_junctions()
