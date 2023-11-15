"""2016 Day 24: Air Duct Spelunking"""


# pylint: disable=invalid-name,too-few-public-methods

import itertools
import queue
from collections import namedtuple
from puzzle import Puzzle


Point = namedtuple('Point', 'x y')


class Ducts():
    """Represents a 2D map of ducts and POIs (points of interest) to
    visit."""
    def __init__(self, lines):
        self.grid = []
        self.pois = []
        self.parse(lines)

    def parse(self, lines):
        """Convert a text map of # (blocked) and . (free) to the grid
        list of lists. Also mark any POIs (numbers) found."""
        poi_map = {}
        for y, line in enumerate(lines):
            row = []
            for x, ch in enumerate(line):
                if ch == '#':
                    row.append(False)
                else:
                    row.append(True)
                    if ch != '.':
                        poi_map[int(ch)] = Point(x, y)
            self.grid.append(row)
        for k in sorted(poi_map.keys()):
            self.pois.append(poi_map[k])

    def is_open(self, point):
        """Is the given point an open position or a wall?"""
        return self.grid[point.y][point.x]

    def poi(self, index):
        """Return the position of the numbered POI."""
        return self.pois[index]

    def open_neightbours(self, point):
        """Return the open neightbours of point."""
        opens = []
        for delta in (-1, 1):
            neighbour_x = Point(point.x + delta, point.y)
            if self.is_open(neighbour_x):
                opens.append(neighbour_x)
            neighbour_y = Point(point.x, point.y + delta)
            if self.is_open(neighbour_y):
                opens.append(neighbour_y)
        return opens

    def find_poi_path(self, poi_from_index, poi_to_index):
        """Find the shortest path between POIs via BFS."""
        poi_from = self.poi(poi_from_index)
        poi_to = self.poi(poi_to_index)
        q = queue.Queue()
        visited = set()
        q.put([poi_from])
        while not q.empty():
            path = q.get()
            moves = self.open_neightbours(path[-1])
            for move in moves:
                if move == poi_to:
                    return len(path)
                if move not in visited:
                    q.put(path + [move])
                    visited.add(move)
        raise RuntimeError(f'No path from {poi_from_index} to {poi_to_index}')

    def find_all_poi_paths(self):
        """Find all shortest paths between POIs and return as a matrix."""
        num_pois = len(self.pois)
        distances = [[-1 for _ in range(num_pois)] for _ in range(num_pois)]
        for poi_from in range(num_pois):
            distances[poi_from][poi_from] = 0
            for poi_to in range(poi_from + 1, num_pois):
                path = self.find_poi_path(poi_from, poi_to)
                distances[poi_from][poi_to] = path
                distances[poi_to][poi_from] = path
        return distances

    def find_shortest_path(self, returnToZero=False):
        """Find the shortest path from 0 that goes to all POIs via
        brute force."""
        distances = self.find_all_poi_paths()
        shortest = 1_000_000
        for path in itertools.permutations(range(1, len(self.pois))):
            if returnToZero:
                path = list(path) + [0]
            path_len = 0
            poi_from = 0
            for poi_to in path:
                path_len += distances[poi_from][poi_to]
                poi_from = poi_to
            shortest = min(path_len, shortest)
        return shortest


class Day24(Puzzle):
    """2016 Day 24: Air Duct Spelunking"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 24

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.ducts = Ducts(self.puzzle_data.input_as_lines())

    def calculate_a(self):
        """Part A: What is the fewest number of steps required to
        visit every non-0 number marked on the map at least once?"""
        return self.ducts.find_shortest_path()

    def calculate_b(self):
        """Part B. What is the fewest number of steps required tostart
        at 0, visit every non-0 number marked on the map at least
        once, and then return to 0?"""
        return self.ducts.find_shortest_path(returnToZero=True)
