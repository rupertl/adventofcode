"""Tests Day 1."""
import pytest
from puzzle_data import PuzzleData
from day01 import Day01, GridMove, GridPosition


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day01(PuzzleData("data/sample/01"))


def test_gridmove_r3():
    g = GridMove('R3')
    assert g.rotation == 90
    assert g.distance == 3


def test_gridmove_l4():
    g = GridMove('L4')
    assert g.rotation == -90
    assert g.distance == 4


def test_parse(puzzle):
    moves = puzzle.parse_input(puzzle.puzzle_data.input_as_string())
    assert len(moves) == 4
    # R8
    assert moves[0].rotation == 90
    assert moves[0].distance == 8


def test_position_r2_l3():
    gp = GridPosition()
    gp.move(GridMove('R2'))
    gp.move(GridMove('L3'))
    assert gp.pos_x == 2
    assert gp.pos_y == 3


def test_distance_r2_l3():
    gp = GridPosition()
    gp.move(GridMove('R2'))
    gp.move(GridMove('L3'))
    assert gp.distance() == 5


def test_position_r2_r2_r2():
    gp = GridPosition()
    gp.move(GridMove('R2'))
    gp.move(GridMove('R2'))
    gp.move(GridMove('R2'))
    assert gp.pos_x == 0
    assert gp.pos_y == -2


def test_distance_r2_r2_r2():
    gp = GridPosition()
    gp.move(GridMove('R2'))
    gp.move(GridMove('R2'))
    gp.move(GridMove('R2'))
    assert gp.distance() == 2


def test_position_l2_r2_r2_r2():
    # This tests rotating to an angle below 0 and above 360
    gp = GridPosition()
    gp.move(GridMove('L2'))
    gp.move(GridMove('R2'))
    gp.move(GridMove('R2'))
    gp.move(GridMove('R2'))
    assert gp.pos_x == 0
    assert gp.pos_y == 0


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
