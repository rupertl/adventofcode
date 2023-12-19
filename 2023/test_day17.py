"""Tests Day 17."""
import pytest
from puzzle_data import PuzzleData
from day17 import Day17, Point, S, E, CrucibleCity


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day17(PuzzleData("data/sample/17"))


def test_parsr(puzzle):
    assert puzzle.cc.at(Point(0, 0)) == 2
    assert puzzle.cc.at(Point(1, 0)) == 3
    assert puzzle.cc.at(Point(0, 1)) == 4


def test_moves(puzzle):
    moves = puzzle.cc.valid_moves(Point(0, 0), S)
    assert len(moves) == 2
    assert (Point(0, 1), E) in moves
    assert (Point(1, 0), S) in moves


extremeTestCase = [
    "111111111111",
    "999999999991",
    "999999999991",
    "999999999991",
    "999999999991",
]


def test_extreme_case():
    cc = CrucibleCity(extremeTestCase)
    assert cc.best_path(minStepLen=4, maxStepLen=10) == 71


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
