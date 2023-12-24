"""Tests Day 23."""
import pytest
from puzzle_data import PuzzleData
from day23 import Day23, Point


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day23(PuzzleData("data/sample/23"))


@pytest.mark.parametrize("point, expected",
                         ([[Point(0, 0), '#'],
                           [Point(0, 1), '.'],
                           [Point(1, 0), '#'],
                           [Point(-1, -11), 0],
                           ]))
def test_parse(puzzle, point, expected):
    assert puzzle.islandA.at(point) == expected


@pytest.mark.parametrize("point, expected",
                         ([[Point(3, 3), [Point(3, 4), Point(4, 3)]],
                           [Point(4, 3), [Point(5, 3)]],
                           ]))
def test_valid_moves(puzzle, point, expected):
    assert sorted(puzzle.islandA.valid_moves(point)) == sorted(expected)


@pytest.mark.parametrize("point, expected",
                         ([[Point(3, 3), [Point(3, 4), Point(4, 3)]],
                           [Point(4, 3), [Point(3, 3), Point(5, 3)]],
                           ]))
def test_valid_moves_dry(puzzle, point, expected):
    assert sorted(puzzle.islandB.valid_moves(point)) == sorted(expected)


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
