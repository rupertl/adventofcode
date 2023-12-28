"""2023 Day 25: Snowverload"""


# pylint: disable=invalid-name,too-few-public-methods

import math
import networkx
from puzzle import Puzzle


class SnowMachine():
    """Represents a machine with connected components."""
    def __init__(self, lines):
        self.graph = self.parse(lines)

    def parse(self, lines):
        """Turn puzzle input into a networkx graph."""
        # eg "jqt: rhn xhk nvd"
        graph = networkx.Graph()
        for line in lines:
            fromComponent, toComponents = line.split(": ")
            for toComponents in toComponents.split(" "):
                graph.add_edge(fromComponent, toComponents)
        return graph

    def partition_minimum_cut(self):
        """Partition the graph using the minimum edge cut algorithm.
        Return the nodes in each side."""
        # hint from reddit: look at networkx for this algorithm
        nodesRemoved = networkx.minimum_edge_cut(self.graph)
        self.graph.remove_edges_from(nodesRemoved)
        return networkx.connected_components(self.graph)


class Day25(Puzzle):
    """2023 Day 24: Snowverload"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 25

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        lines = self.puzzle_data.input_as_lines()
        self.machine = SnowMachine(lines)

    def calculate_a(self):
        """Part A: What do you get if you multiply the sizes of these
        two groups together?"""
        return math.prod((len(group) for group
                          in self.machine.partition_minimum_cut()))

    def calculate_b(self):
        """Part B. All done!"""
        return '*'
