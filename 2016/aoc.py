#!/usr/bin/env python

"""Main entry point to Advent of Code runner. See README.md for details."""


import pkgutil
import sys

from puzzle_data import PuzzleData
from puzzle import Puzzle


def load_all_puzzle_modules():
    """Load all puzzle modules (dayXX) from the current directory."""
    max_day = 0                 # How many days we have implemented.
    for importer, package_name, _ in pkgutil.iter_modules(["."]):
        if "day" in package_name and 'test' not in package_name:
            importer.find_module(package_name).load_module(package_name)
            day_number = int(package_name[-2:])
            max_day = max(max_day, day_number)
    return max_day


def find_puzzle(day, puzzle_data):
    """Find the class for the given puzzle day."""
    for cls in Puzzle.__subclasses__():
        if cls.day() == day:
            return cls(puzzle_data)
    raise RuntimeError(f'day {day} not implemented')


def select_days(args, max_day):
    """Select which days to run puzzles for. By default run all except
    if a single day is provided as an argument."""
    if len(args) == 1 and args[0].isdigit():
        return [int(args[0])]
    return range(1, max_day + 1)


def main():
    """Main entry point"""
    max_day = load_all_puzzle_modules()
    days = select_days(sys.argv[1:], max_day)
    for day in days:
        data_source = f"data/full/{day:02}"
        puzzle_data = PuzzleData(data_source)
        if not puzzle_data.has_input():
            raise RuntimeError(f"Could not find puzzle data in {data_source}")
        puzzle = find_puzzle(day, puzzle_data)
        puzzle.calculate()
        print(puzzle)


if __name__ == '__main__':
    main()
