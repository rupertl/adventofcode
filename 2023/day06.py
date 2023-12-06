"""2023 Day 6: Wait For It"""


# pylint: disable=invalid-name,too-few-public-methods

import math
from puzzle import Puzzle


class Race():
    """Represents a boat race with a target time and a distance."""
    def __init__(self, time, distance):
        self.time = time
        self.distance = distance

    # Brute force solution
    def winners(self):
        """Find the combinations of holding the button for a certain
        number of ms would win the race."""
        wins = []
        for hold in range(self.time):  # ignore holding for full time
            result = (self.time - hold) * hold
            if result > self.distance:
                wins.append(hold)
        return wins

    def winners_count(self):
        """An algebraic solution to count the number of winners."""
        # For t (race time), d (distance) solve for h (hold time)
        # -h^2 + ht - d = 0
        # h = (-t +/- sqrt(t^2 - 4d)) / -2
        t = self.time
        d = self.distance
        hmin = (-t + math.sqrt(t*t - 4*d)) / -2
        hmax = (-t - math.sqrt(t*t - 4*d)) / -2
        # Find the next integer after hmin and before hmax
        hmin = math.floor(hmin + 1)
        hmax = math.ceil(hmax - 1)
        return 1 + hmax - hmin


class BoatRacer():
    """Represents a set of boat races with time and target distance to
    beat."""
    def __init__(self, lines):
        self.races1 = self.parse(lines, badKerning=False)
        self.races2 = self.parse(lines, badKerning=True)

    def parse(self, lines, badKerning):
        """Parse puzzle input into boats."""
        # Time:      7  15   30
        # Distance:  9  40  200
        times = self.parseLine(lines[0], badKerning)
        distances = self.parseLine(lines[1], badKerning)
        races = []
        for index, time in enumerate(times):
            races.append(Race(time, distances[index]))
        return races

    def parseLine(self, line, badKerning):
        """Parae a line of input. Join the numbers into one if
        badKerning is true."""
        line = line[9:]         # Remove leading word
        if badKerning:
            line = line.replace(' ', '')
        return [int(word) for word in line.split()]

    def winning_product(self, badKerning=False):
        """Return the product of the number of winning options for
        each race."""
        races = self.races2 if badKerning else self.races1
        return math.prod((race.winners_count() for race in races))


class Day06(Puzzle):
    """2023 Day 6: Wait For It"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 6

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        lines = self.puzzle_data.input_as_lines()
        self.racer = BoatRacer(lines)

    def calculate_a(self):
        """Part A: What do you get if you multiply these numbers together?"""
        return self.racer.winning_product(badKerning=False)

    def calculate_b(self):
        """Part B. How many ways can you beat the record in this one
        much longer race?"""
        return self.racer.winning_product(badKerning=True)
