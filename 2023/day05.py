"""2023 Day 5: If You Give A Seed A Fertilizer"""


# pylint: disable=invalid-name,too-few-public-methods

from collections import namedtuple
from puzzle import Puzzle


RangeItem = namedtuple('RangeItem', 'dest source length')


class RangeMap():
    """Maps a number in a source range to a number in a destination
    range, or returns the number if not found in the range."""
    def __init__(self):
        self.items = []         # list of RangeItems

    def add(self, dest, source, length):
        """Add a RangeItem to the map."""
        self.items.append(RangeItem(dest, source, length))

    def find(self, number):
        """Maps the number based on the range."""
        for item in self.items:
            if item.source <= number < item.source + item.length:
                return item.dest + number - item.source
        return number

    def next_break(self, number):
        """Find the next discontinuity in ranges after number and
        return the offset."""
        # eg for ranges 10-20, 21-30 if the input is 15 the output
        # would be 6 to get to the range starting at 21.
        next_sources = [float('Inf')]  # Default value to find min
        for item in self.items:
            if item.source <= number < item.source + item.length:
                return item.source + item.length - number
            if item.source > number:
                next_sources.append(item.source - number)
        # If not in a range, find the start of the next one
        return min(next_sources)


class Almanac():
    """Represents a set of seed and range maps to find the location."""
    def __init__(self, lines):
        self.seeds = []
        self.maps = []
        self.parse(lines)

    def parse(self, lines):
        """Parse puzzle input into seeds and a sequence of maps."""
        # seeds: 79 14 55 13
        self.seeds = [int(n) for n in lines[0][7:].split()]
        next_map = RangeMap()
        for line in lines[2:]:
            if line == "":
                # Map ended
                self.maps.append(next_map)
                next_map = RangeMap()
            elif line[0].isalpha():
                # Ignore map name
                continue
            else:
                next_map.add(*[int(n) for n in line.split()])
        # Last one
        self.maps.append(next_map)

    def find_location(self, seed):
        """For a given seed, find the location by going through the
        maps."""
        number = seed
        for rm in self.maps:
            number = rm.find(number)
        return number

    def seed_locations(self):
        """Find the locations for each seed and return as a map."""
        locations = {}
        for seed in self.seeds:
            locations[seed] = self.find_location(seed)
        return locations

    def find_min_break(self, seed):
        """For a given seed, find the smallest break in all the mspa."""
        number = seed
        breaks = []
        for rm in self.maps:
            breaks.append(rm.next_break(number))
            number = rm.find(number)
        return min(breaks)

    def lowest_seed_location_multi(self):
        """Treat seeds as pairs of numbers indicating a range, and
        find the smallest location."""
        locations = []
        for index in range(len(self.seeds) // 2):
            seed = self.seeds[2 * index]
            end = seed + self.seeds[2 * index + 1]
            while seed < end:
                next_location = self.find_location(seed)
                locations.append(next_location)
                # Skip ahead by the smallesyt break in all the maps
                seed += self.find_min_break(seed)
        return min(locations)


class Day05(Puzzle):
    """2023 Day 5: If You Give A Seed A Fertilizer"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 5

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        lines = self.puzzle_data.input_as_lines()
        self.almanac = Almanac(lines)

    def calculate_a(self):
        """Part A: What is the lowest location number that corresponds
        to any of the initial seed numbers?"""
        return min(self.almanac.seed_locations().values())

    def calculate_b(self):
        """Part B. What is the lowest location number that corresponds
        to any of the initial seed numbers?"""
        return self.almanac.lowest_seed_location_multi()
