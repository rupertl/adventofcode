"""Tests Day 21."""
import pytest
from puzzle_data import PuzzleData
from day21 import Day21, Point


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day21(PuzzleData("data/sample/21"))


@pytest.mark.parametrize("row, col, expected",
                         ([[0, 0, '.'],
                           [1, 2, '.'],
                           [2, 1, '#'],
                           [-1, -1, -1],
                           ]))
def test_parse(puzzle, row, col, expected):
    assert puzzle.gardenA.at(Point(row, col)) == expected


@pytest.mark.parametrize("steps, expected",
                         ([[0, 1],
                           [1, 2],
                           [2, 4],
                           [3, 6],
                           [6, 16],
                           [10, 33],
                           ]))
def test_steps(puzzle, steps, expected):
    assert len(puzzle.gardenA.visitable(steps)) == expected


@pytest.mark.parametrize("steps, expected",
                         ([[0, 1],
                           [1, 2],
                           [2, 4],
                           [3, 6],
                           [6, 16],
                           [10, 50],     # too slow after that
                           ]))
def test_infinite_garden(puzzle, steps, expected):
    assert len(puzzle.gardenB.visitable(steps)) == expected


def test_part1_solution(puzzle):
    puzzle.stepsA = 6           # override for sample data
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')
