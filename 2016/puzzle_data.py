"""Implementation of PuzzleData class, which represents input data and
model solutions for each day's puzzle."""

# There are usually two sub puzzles using the same input. AOC website
# calls them part1 and part2 but we use a and b to distinguihs from
# the puzzle day number.
SOLUTIONS = ['a', 'b']


class PuzzleData:
    """Implements PuzzleData to store puzzle inputs and solutions.
    Some or all of this data may be missing."""
    def __init__(self, path):
        """Takes path to directory containing input and solution files."""
        self.input = []
        self.solutions = {}
        self.read_input(path)
        self.read_solutions(path)

    def read_input(self, path):
        """Read input data from file, ignoring errors."""
        file_name = path + "/input"
        try:
            with open(file_name, encoding='utf-8') as file:
                self.input = file.read().splitlines()
        except FileNotFoundError:
            pass

    def read_solutions(self, path):
        """Read solutions from file, ignoring errors."""
        base_file_name = path + "/solution."
        try:
            for suffix in SOLUTIONS:
                with open(base_file_name + suffix, encoding='utf-8') as file:
                    sol = file.read().splitlines()
                    self.solutions[suffix] = sol[0] if len(sol) == 1 else sol
        except FileNotFoundError:
            pass

    def has_input(self):
        """Return true if input data provided and not empty."""
        return len(self.input) >= 1

    def input_as_lines(self):
        """Return input as an array of strings."""
        return self.input

    def input_as_string(self):
        """Return input as a single string (first line)."""
        return self.input[0]

    def has_solution(self, key):
        """Returns true if a solution for key was provided."""
        assert key in SOLUTIONS
        return key in self.solutions

    def solution(self, key):
        """Returns a given solution."""
        assert key in SOLUTIONS
        return self.solutions[key]
