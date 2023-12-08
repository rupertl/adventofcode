"""Tests Day 3."""
import pytest
from puzzle_data import PuzzleData
from day03 import Day03, Shape


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day03(PuzzleData("data/sample/03"))


@pytest.mark.parametrize("index, expected",
                         [(0, [5, 10, 25]), (1, [3, 4, 5]), (2, [4, 6, 22])])
def test_parse_horiz(puzzle, index, expected):
    assert puzzle.shapes_horiz[index].sides == expected


@pytest.mark.parametrize("index, expected",
                         [(0, [5, 3, 4]), (1, [10, 4, 6]), (2, [25, 5, 22])])
def test_parse_vert(puzzle, index, expected):
    assert puzzle.shapes_vert[index].sides == expected


def test_is_triange_yes():
    s = Shape([3, 4, 5])
    assert s.is_triange()


def test_is_triange_no():
    s = Shape([5, 10, 25])
    assert not s.is_triange()


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
