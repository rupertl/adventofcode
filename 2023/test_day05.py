"""Tests Day 5."""
import pytest
from puzzle_data import PuzzleData
from day05 import Day05, RangeMap


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day05(PuzzleData("data/sample/05"))


@pytest.mark.parametrize("number, expected1, expected2",
                         [[9, 9, 34],
                          [10, 20, 20],
                          [11, 21, 21],
                          [12, 12, 12],
                          ])
def test_range_map(number, expected1, expected2):
    rm = RangeMap()
    rm.add(20, 10, 2)
    assert rm.find(number) == expected1
    rm.add(30, 5, 5)
    assert rm.find(number) == expected2


@pytest.mark.parametrize("number, next_break",
                         [[0, 10],
                          [8, 2],
                          [10, 5],
                          [11, 4],
                          [15, 5],
                          [16, 4],
                          [20, 2],
                          [21, 1],
                          [22, 8]
                          ])
def test_range_break(number, next_break):
    rm = RangeMap()
    # 0-10 empty
    rm.add(100, 10, 5)
    rm.add(200, 15, 5)
    # 20 empty
    rm.add(300, 22, 8)
    assert rm.next_break(number) == next_break


def test_parse(puzzle):
    assert puzzle.almanac.seeds == [79, 14, 55, 13]
    assert len(puzzle.almanac.maps) == 7


def test_locations(puzzle):
    assert puzzle.almanac.seed_locations() == {79: 82,
                                               14: 43,
                                               55: 86,
                                               13: 35}


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
