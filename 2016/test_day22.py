"""Tests Day 22."""
import pytest
from puzzle_data import PuzzleData
from day22 import Day22


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day22(PuzzleData("data/sample/22"))


def test_parse(puzzle):
    assert puzzle.disk_grid.size_x == 3
    assert puzzle.disk_grid.size_y == 3
    assert puzzle.disk_grid.num_disks == 9
    assert puzzle.disk_grid.disk_sizes[0][1] == 11
    assert puzzle.disk_grid.disk_used[0][1] == 6
    assert puzzle.disk_grid.disk_used[1][1] == 0


def test_unmovable(puzzle):
    assert puzzle.disk_grid.unmovable_node(0, 2)
    assert not puzzle.disk_grid.unmovable_node(2, 0)


def test_find_empty_node(puzzle):
    assert puzzle.disk_grid.find_empty_node() == (1, 1)


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
