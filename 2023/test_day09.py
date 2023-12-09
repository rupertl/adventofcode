"""Tests Day 9."""
import pytest
from puzzle_data import PuzzleData
from day09 import Day09


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day09(PuzzleData("data/sample/09"))


def test_parse(puzzle):
    assert puzzle.oases[0].readings[0] == [0, 3, 6, 9, 12, 15]


@pytest.mark.parametrize("index, expected",
                         ([[0, 18], [1, 28], [2, 68]]))
def test_extrapolate_next(puzzle, index, expected):
    assert puzzle.oases[index].extrapolate_next() == expected


@pytest.mark.parametrize("index, expected",
                         ([[0, -3], [1, 0], [2, 5]]))
def test_extrapolate_prev(puzzle, index, expected):
    assert puzzle.oases[index].extrapolate_prev() == expected


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
