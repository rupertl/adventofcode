"""Base class for puzzle implementations."""

import time


class Puzzle:
    """Base class for puzzle implementation. Derived classes should
    implement the class method day (to return day number for this
    puzzle), parse input data in the constructor and implement
    calculate_a/b."""
    class NotImplementedError(Exception):
        """Thrown if part of a puzzle is not implemented yet,"""

    def __init__(self, puzzle_data):
        self.puzzle_data = puzzle_data
        self.results = {}
        self.times = {}

    def __str__(self):
        part_a = self.result_check('a')
        part_b = self.result_check('b')
        return f'Day {self.day():02}: {part_a} {part_b}'

    def result_check(self, part):
        """A string showing our result and if it matches the given solution"""
        if part not in self.results:
            return "(not solved)"
        ours = self.results[part]
        if not self.puzzle_data.has_solution(part):
            return f'{ours} (unsubmitted)'
        given = self.puzzle_data.solution(part)
        correct = '✔' if ours == given else f'✗ ({given})'
        output = f'{ours} {correct}'
        if self.times[part] >= 0.01:
            output += f" [{self.times[part]:.2f}s]"
        return output

    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        raise NotImplementedError

    def calculate(self):
        """Calculate results for this puzzle. Main entry point for clients."""
        self.calculate_part('a')
        self.calculate_part('b')

    def calculate_part(self, part):
        """Run a calculation for a part and time the results."""
        start_time = time.process_time()
        if part == 'a':
            self.results[part] = str(self.calculate_a())
        elif part == 'b':
            self.results[part] = str(self.calculate_b())
        else:
            raise NotImplementedError
        stop_time = time.process_time()
        self.times[part] = stop_time - start_time

    def calculate_a(self):
        """Overridden method to calculate the solution for the first part."""
        raise NotImplementedError

    def calculate_b(self):
        """Overridden method to calculate the solution for the second part."""
        raise NotImplementedError
