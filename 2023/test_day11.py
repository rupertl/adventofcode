"""Tests Day 11."""
import pytest
from puzzle_data import PuzzleData
from day11 import Day11, Point, manhattan_distance, Cosmos


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day11(PuzzleData("data/sample/11"))

#           111
# 0123456789012
# ....1........  0
# .........2...  1
# 3............  2
# .............  3
# .............  4
# ........4....  5
# .5...........  6
# ............6  7
# .............  8
# .............  9
# .........7...  10
# 8....9.......  11


def test_expansion(puzzle):
    assert set(puzzle.cosmosA.galaxies) == {
        Point(0, 4),
        Point(1, 9),
        Point(2, 0),
        Point(5, 8),
        Point(6, 1),
        Point(7, 12),
        Point(10, 9),
        Point(11, 0),
        Point(11, 5)
        }


def test_manhattan():
    assert manhattan_distance(Point(6, 1), Point(11, 5)) == 9


@pytest.mark.parametrize("expansion, expected",
                         ([[10, 1030], [100, 8410]]))
def test_variable_expansion(puzzle, expansion, expected):
    cosmos = Cosmos(puzzle.lines, expansion)
    assert cosmos.sum_shortest_distance() == expected


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
