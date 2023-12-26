"""Tests Day 24."""
import pytest
from puzzle_data import PuzzleData
from day24 import Day24, Point, Stone


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    puzzle = Day24(PuzzleData("data/sample/24"))
    puzzle.storm.rangeMin = 7
    puzzle.storm.rangeMax = 27
    return puzzle


def test_parse(puzzle):
    assert puzzle.storm.stones[0] == Stone(Point(19, 13, 30),
                                           Point(-2, 1, -2))


# 19, 13, 30 @ -2,  1, -2    0
# 18, 19, 22 @ -1, -1, -2    1
# 20, 25, 34 @ -2, -2, -4    2
# 12, 31, 28 @ -1, -2, -1    3
# 20, 19, 15 @  1, -5, -3    4
@pytest.mark.parametrize("index1, index2, expected",
                         ([[0, 1, True],
                           [0, 2, True],
                           [0, 3, False],
                           [0, 4, False],
                           [1, 2, False],
                           [1, 3, False],
                           [1, 4, False],
                           [2, 3, False],
                           [2, 4, False],
                           [3, 4, False],
                           ]))
def test_intersects_2d(puzzle, index1, index2, expected):
    s1 = puzzle.storm.stones[index1]
    s2 = puzzle.storm.stones[index2]
    assert puzzle.storm.intersects_2d_range(s1, s2) == expected


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
