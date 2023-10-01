"""2016 Day 17: Two Steps Forward"""


# pylint: disable=invalid-name,too-few-public-methods

import hashlib
import queue
from collections import namedtuple
from puzzle import Puzzle


Point = namedtuple('Point', 'x y')
Move = namedtuple('Move', 'direction dx dy')


class Rooms():
    """Represents a grid of rooms with doors that open based on a
    passcode."""
    # Dimensions of the grid
    MIN_POS = 0
    MAX_POS = 3
    # The index of MOVES matches the position in the passcode
    MOVES = [Move('U', 0, -1), Move('D', 0, 1),
             Move('L', -1, 0), Move('R', 1, 0)]
    # What chars in the passcode represent an open door
    OPEN_CHARS = frozenset({'b', 'c', 'd', 'e', 'f'})

    def __init__(self, seed, path=(), position=Point(0, 0)):
        self.seed = seed
        self.path = path
        self.position = position

    def adjacent(self):
        """Return copies of this object based on available moves."""
        adjacents = []
        passcode = self.generate_passcode()
        for index, move in enumerate(self.MOVES):
            to = Point(self.position.x + move.dx, self.position.y + move.dy)
            if self.not_at_wall(to) and self.door_open(passcode, index):
                newPath = self.path + (move.direction,)
                newRooms = Rooms(seed=self.seed, position=to, path=newPath)
                adjacents.append(newRooms)
        return adjacents

    def not_at_wall(self, newPosition):
        """Is newPosition at a wall?"""
        return ((self.MIN_POS <= newPosition.x <= self.MAX_POS) and
                (self.MIN_POS <= newPosition.y <= self.MAX_POS))

    def door_open(self, passcode, index):
        """Is the door indicated by the index open?"""
        return passcode[index] in self.OPEN_CHARS

    def generate_passcode(self):
        """Generate the MD5 hash based on the seed and the path so far."""
        to_hash = self.seed + ''.join(self.path)
        return hashlib.md5(to_hash.encode()).hexdigest()

    def path_str(self):
        """Return path to get here as a string."""
        return ''.join(self.path)


class Solver():
    """Find the shortest path through the rooms."""
    TARGET = Point(3, 3)

    def __init__(self, seed):
        self.start = Rooms(seed)

    def find_path(self):
        """Find path of moves to target."""
        q = queue.Queue()
        q.put(self.start)
        while not q.empty():
            turn = q.get()
            moves = turn.adjacent()
            for move in moves:
                if move.position == self.TARGET:
                    return move.path_str()
                q.put(move)
        return ""               # queue exhausted, no solution

    def find_longest_path(self):
        """Find longest valid path of moves to target."""
        maxLen = -1
        q = queue.Queue()
        q.put(self.start)
        while not q.empty():
            turn = q.get()
            moves = turn.adjacent()
            for move in moves:
                if move.position == self.TARGET:
                    maxLen = max(maxLen, len(move.path))
                else:
                    q.put(move)
        return maxLen


class Day17(Puzzle):
    """2016 Day 17: Two Steps Forward"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 17

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.solver = Solver(self.puzzle_data.input_as_string())

    def calculate_a(self):
        """Part A: what is the shortest path (the actual path, not
        just the length) to reach the vault?"""
        return self.solver.find_path()

    def calculate_b(self):
        """Part B. What is the length of the longest path that reaches
        the vault?"""
        return self.solver.find_longest_path()
