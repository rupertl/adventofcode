"""2016  Day 6: Signals and Noise"""


# pylint: disable=invalid-name,too-few-public-methods


from collections import Counter
from puzzle import Puzzle


class Day06(Puzzle):
    """2016 Day 6: Signals and Noise"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 6

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.columns = self.parse(self.puzzle_data.input_as_lines())

    def parse(self, lines):
        """Parse lines of input into columns."""
        columns = [[] for _ in range(len(lines[0]))]
        for line in lines:
            for index, ch in enumerate(line):
                columns[index].append(ch)
        return [''.join(column) for column in columns]

    def calculate_a(self):
        """Part A: What is the error corrected message?"""
        return self.solve(0)

    def calculate_b(self):
        """Part B. What is the error corrected message, using the
        least popular char in each column?"""
        return self.solve(-1)

    def solve(self, pop_rank):
        """Solve the puzzle using the given position in the popularity rank."""
        message = []
        for col in self.columns:
            ctr = Counter(col)
            # We assume only one letter per column has the same popularity
            message.append(ctr.most_common()[pop_rank][0])
        return ''.join(message)
