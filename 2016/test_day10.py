"""Tests Day 10."""
import pytest
from puzzle_data import PuzzleData
from day10 import Day10


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day10(PuzzleData("data/sample/10"))


def test_state_robots(puzzle):
    puzzle.controller.run()
    for index in range(3):
        assert puzzle.controller.robots[index].values == []


@pytest.mark.parametrize("bin_id, contents",
                         [(0, [5]), (1, [2]), (2, [3])])
def test_state_bins(puzzle, bin_id, contents):
    puzzle.controller.run()
    assert puzzle.controller.output[bin_id] == contents


@pytest.mark.parametrize("robot_id, compared",
                         [(0, [3, 5]), (1, [2, 3]), (2, [2, 5])])
def test_locate_comparer(puzzle, robot_id, compared):
    puzzle.controller.run()
    assert puzzle.controller.get_who_compared(frozenset(compared)) == robot_id


# Part 1 sample data is not compatible with the question inputs, so we
# can only run part 2.
def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
