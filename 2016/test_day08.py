"""Tests Day 8."""
import pytest
from puzzle_data import PuzzleData
from day08 import Day08, Screen


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day08(PuzzleData("data/sample/08"))


def test_rect():
    s = Screen()
    s.rect(4, 5)
    assert s.count_pixels_lit() == 4 * 5


def test_rotate_row():
    s = Screen()
    s.rect(4, 4)
    assert s.display[0][0] == 1 and s.display[0][4] == 0
    s.rotate_row(0, 1)
    assert s.display[0][0] == 0 and s.display[0][4] == 1


def test_rotate_col():
    s = Screen()
    s.rect(4, 4)
    assert s.display[0][0] == 1 and s.display[4][0] == 0
    s.rotate_col(0, 1)
    assert s.display[0][0] == 0 and s.display[4][0] == 1


def test_part1_solurion(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


# Part B relies on visual inspection
