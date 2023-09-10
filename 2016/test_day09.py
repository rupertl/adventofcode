"""Tests Day 9."""
import pytest
from puzzle_data import PuzzleData
from day09 import Day09, Message


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day09(PuzzleData("data/sample/09"))


@pytest.mark.parametrize("cmessage, length",
                         [('ADVENT', 6),
                          ('A(1x5)BC', 7),
                          ('(3x3)XYZ', 9),
                          ('A(2x2)BCD(2x2)EFG', 11),
                          ('(6x1)(1x3)A', 6),
                          ('X(8x2)(3x3)ABCY', 18)
                          ])
def test_v1_length(cmessage, length):
    assert Message(cmessage).length() == length


@pytest.mark.parametrize("cmessage, length",
                         [('', 0),
                          ('ADVENT', 6),
                          ('(3x3)XYZ', 9),
                          ('X(8x2)(3x3)ABCY', 20),
                          ('(27x12)(20x12)(13x14)(7x10)(1x12)A', 241920),
                          ('(25x3)(3x3)ABC(2x3)XY' +
                           '(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN', 445)
                          ])
def test_v2_length_v2(cmessage, length):
    assert Message(cmessage, 2).length() == length


def test_part1_solurion(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solurion(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
