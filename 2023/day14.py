"""2023 Day 14: Parabolic Reflector Dish"""


# pylint: disable=invalid-name,too-few-public-methods

import itertools
from puzzle import Puzzle


class Dish():
    """Represents a reflector dish with round rocks that can be moved
    around."""
    def __init__(self, lines):
        self.maxRow = len(lines)
        self.maxCol = len(lines[0])
        self.grid = self.parse(lines)

    def parse(self, lines):
        """Parse the puzzle input into the 1D grid list."""
        grid = []
        for row in lines:
            grid += list(row)
        return grid

    def at(self, row, col):
        """Return the element at the given coordinates."""
        return self.grid[(row * self.maxCol) + col]

    def get(self, isRow, index):
        """Get a slice (row/col) of the grid."""
        if isRow:
            return self.grid[index * self.maxCol:(1 + index) * self.maxCol]
        return self.grid[index::self.maxCol]

    def set(self, isRow, index, value):
        """Sets a slice (row/col) of the grid."""
        if isRow:
            self.grid[index * self.maxCol:(1 + index) * self.maxCol] = value
        else:
            self.grid[index::self.maxCol] = value

    def __str__(self):
        s = ""
        for index in range(self.maxRow):
            s += ''.join(self.get(True, index)) + "\n"
        return s

    def sort(self, line, osFirst):
        """Sort the line so areas without cube shaped rocks have
        their round rocks and empty spaces ordered by osFirst."""
        # Group into segments, breaking at each #
        segments = [list(v) for k, v in
                    itertools.groupby(line, lambda ch: ch != "#")]
        # Sort each segment, if osFirst start with O else .
        rearranged = [sorted(segment, reverse=osFirst)
                      for segment in segments]
        # Glue it back together
        return [ch for segment in rearranged for ch in segment]

    def tilt(self, direction):
        """Change the grid by tilting it in a direction."""
        actions = {'N': {'isRow': False, 'osFirst': True},
                   'S': {'isRow': False, 'osFirst': False},
                   'W': {'isRow': True, 'osFirst': True},
                   'E': {'isRow': True, 'osFirst': False}}
        action = actions[direction]
        extent = self.maxRow if action['isRow'] else self.maxCol
        for index in range(extent):
            segment = self.get(action['isRow'], index)
            rearranged = self.sort(segment, action['osFirst'])
            self.set(action['isRow'], index, rearranged)

    def spin_cycle(self):
        """Execute a N-W-S-E tilt."""
        self.tilt('N')
        self.tilt('W')
        self.tilt('S')
        self.tilt('E')

    def load(self):
        """Calculate the load from the S edge."""
        amount = 0
        for rowIndex in range(self.maxRow):
            row = self.get(True, rowIndex)
            amount += sum((ch == 'O' for ch in row)) * (
                self.maxRow - rowIndex)
        return amount

    def extrapolate_load(self, cycles):
        """Work out what the load will be after a number of cycles."""
        # Run it for N cycles to let it converge
        N = 100
        LOOKBACK = 3
        for _ in range(1, N):
            self.spin_cycle()
        # Run it for N more cycles to gather data
        loads = [self.load()]
        for _ in range(1, N):
            self.spin_cycle()
            loads.append(self.load())
        # Loop looking for a repeated cycle of 3 numbers
        for index in range(LOOKBACK, N - LOOKBACK):
            if all((loads[index + offset] == loads[offset]
                    for offset in range(LOOKBACK))):
                period = index
                break
        # Move forwards by a multuple of periods until just before the
        # target cycle numver, then look up the value.
        periodsToAdvance = (cycles - N) // period
        baseCycle = N + (periodsToAdvance * period)
        remaining = cycles - baseCycle + 1
        return loads[remaining]


class Day14(Puzzle):
    """2023 Day 14: Parabolic Reflector Dish"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 14

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.lines = self.puzzle_data.input_as_lines()
        self.cycles = 1000000000
        self.dish = Dish(self.lines)

    def calculate_a(self):
        """Part A: what is the total load on the north support beams?"""
        save = self.dish.grid
        self.dish.tilt('N')
        load = self.dish.load()
        # Undo the grid change so part B starts from the same origin.
        self.dish.grid = save
        return load

    def calculate_b(self):
        """Part B. Run the spin cycle for 1000000000 cycles.
        Afterward, what is the total load on the north support beams?"""
        return self.dish.extrapolate_load(self.cycles)
