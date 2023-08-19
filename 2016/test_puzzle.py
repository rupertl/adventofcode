"""Tests Puzzle class."""
import pytest
from puzzle_data import PuzzleData
from day00 import Day00


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle_data")
def fixture_puzzle_data():
    return PuzzleData("data/sample/00")


def test_day_00_normal(puzzle_data):
    p = Day00(puzzle_data)
    p.calculate()
    assert str(p) == "Day 00: xxyy ✔ XY ✔"


def test_day_00_bad_part_a(puzzle_data):
    puzzle_data.solutions['a'] = 'wrong'
    p = Day00(puzzle_data)
    p.calculate()
    assert "✗ (wrong)" in str(p)


def test_day_00_bad_part_b(puzzle_data):
    puzzle_data = PuzzleData("data/sample/00")
    puzzle_data.solutions['b'] = 'worse'
    p = Day00(puzzle_data)
    p.calculate()
    assert "✗ (worse)" in str(p)


def test_day_00_missing_part_a_result(puzzle_data):
    puzzle_data = PuzzleData("data/sample/00")
    p = Day00(puzzle_data)
    assert "(not solved)" in str(p)
