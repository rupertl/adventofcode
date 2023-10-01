"""Tests Day 18."""
import pytest
from puzzle_data import PuzzleData
from day18 import Day18, TrapRoom


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day18(PuzzleData("data/sample/18"))


def test_parse():
    tr = TrapRoom('..^^.')
    assert tr.rows == [[True, True, False, False, True]]


def test_count_safe():
    tr = TrapRoom('..^^.')
    assert tr.count_safe() == 3


@pytest.mark.parametrize("row, expected",
                         [(1, [True, False, False, False, False]),
                          (2, [False, False, True, True, False])
                          ])
def test_generate_2_row(row, expected):
    tr = TrapRoom('..^^.')
    tr.generate_rows(3)
    assert tr.rows[row] == expected


def test_part1_solurion(puzzle):
    puzzle.target_rows_a = 10
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')
