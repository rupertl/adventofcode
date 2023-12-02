"""2023 Day 2: Cube Conundrum"""


# pylint: disable=invalid-name,too-few-public-methods


import math
import re
from puzzle import Puzzle


class BagGame():
    """Represents a game where on each move the elf reveals a certain
    number of coloured cubes."""
    def __init__(self, line):
        self.parse(line)

    def parse(self, line):
        """Parse a line of input into a game id and a set of game
        moves."""
        self.moves = []
        self.gid = int(re.search(r'Game (\d+):', line).group(1))
        plays = line.split(':')[1]  # get text past :
        for play in plays.split(';'):
            move = {}
            for cubes in play.split(','):
                number, colour = re.search(r'\s*(\d+) (\w+)', cubes).groups()
                move[colour] = int(number)
            self.moves.append(move)

    def possible(self, constraint):
        """Return True if game passes the constraint of how many of
        each cubes there could be."""
        for move in self.moves:
            for colour, number in constraint.items():
                if colour in move and move[colour] > number:
                    return False
        return True

    def fewest(self):
        """Return the constraint with the fewest number of cubes that
        would make the game possible."""
        constraint = {'red': 0, 'green': 0, 'blue': 0}
        for move in self.moves:
            for colour, number in move.items():
                constraint[colour] = max(constraint[colour], number)
        return constraint

    def power(self):
        """Return the power number for the fewest set of cubes that is
        possible."""
        return math.prod(self.fewest().values())


class Day02(Puzzle):
    """2023 Day 2: Cube Conundrum"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 2

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.constraint = {'red': 12, 'green': 13, 'blue': 14}
        lines = self.puzzle_data.input_as_lines()
        self.games = [BagGame(line) for line in lines]

    def calculate_a(self):
        """Part A: What is the sum of the IDs of those possible games?"""
        return sum((game.gid for game in self.games
                    if game.possible(self.constraint)))

    def calculate_b(self):
        """Part B. What is the sum of the power of these sets?"""
        return sum(game.power() for game in self.games)
