"""2023 Day 8: Haunted Wasteland"""


# pylint: disable=invalid-name,too-few-public-methods

import math
import re
from puzzle import Puzzle


class Navigator():
    """Represents a network of nodes and a set of left/right turns we
    should take, maybe repeatedly, to get to a destination."""
    def __init__(self, lines):
        # turn indices for network
        self.network = {}       # maps node name to tuple of left/right nodes
        self.directions = []    # list of turns, 0 for left, 1 for right
        self.parse(lines)

    def parse(self, lines):
        """Parse lines into directions and turns."""
        for turn in lines[0]:
            direction = int(turn == 'R')
            self.directions.append(direction)
        for line in lines[2:]:
            node, lnode, rnode = re.search(r'(\w+) = \((\w+), (\w+)\)',
                                           line).groups()
            self.network[node] = (lnode, rnode)

    def turn(self, node, turnNumber):
        """Return the node after turning in the direction implied by
        the turn nmber from node."""
        direction = self.directions[turnNumber % len(self.directions)]
        return self.network[node][direction]

    def turns_to_zzz(self):
        """Find how many turns it takes to get to ZZZ."""
        numTurns = 0
        node = 'AAA'
        while node != 'ZZZ':
            node = self.turn(node, numTurns)
            numTurns += 1
        return numTurns

    def turns_to_all_zs(self):
        """How many turns to get from all A-ending nodes
        simultaneously to all Z-ending nodes?"""
        # All paths are circular (this was a lucky guess maybe from
        # lookint the sample input) so for each A node, find how long
        # it takes to get to a Z node, and then find the least common
        # multiplier of all these periods.
        nodes = self.find_nodes_ending('A')
        zNodes = set(self.find_nodes_ending('Z'))
        periods = []
        for node in nodes:
            numTurns = 0
            while node not in zNodes:
                node = self.turn(node, numTurns)
                numTurns += 1
            periods.append(numTurns)
        return math.lcm(*periods)

    def find_nodes_ending(self, ch):
        """Find all nodes ending in character ch."""
        return (node for node in self.network if node[2] == ch)


class Day08(Puzzle):
    """2023 Day 8: Haunted Wasteland"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 8

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        lines = self.puzzle_data.input_as_lines()
        self.navigator = Navigator(lines)

    def calculate_a(self):
        """Part A: How many steps are required to reach ZZZ?"""
        return self.navigator.turns_to_zzz()

    def calculate_b(self):
        """Part B. How many steps does it take before you're only on
        nodes that end with Z?"""
        return self.navigator.turns_to_all_zs()
