"""Tests Day 18."""
import pytest
from puzzle_data import PuzzleData
from day18 import Day18


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day18(PuzzleData("data/sample/18"))


def test_parse_a(puzzle):
    assert puzzle.lagoonA.segments[0] == ((0, 0), (6, 0))
    assert puzzle.lagoonA.segments[-1] == ((0, -2), (0, 0))


def test_parse_b(puzzle):
    assert puzzle.lagoonB.segments[0] == ((0, 0), (461937, 0))
    assert puzzle.lagoonB.segments[-1] == ((0, -500254), (0, 0))


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
