"""Tests Day 15."""
import pytest
from puzzle_data import PuzzleData
from day15 import Day15, Disk


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day15(PuzzleData("data/sample/15"))


@pytest.mark.parametrize("time, expected",
                         [(0, 4), (1, 0), (2, 1)])
def test_position(time, expected):
    assert Disk(5, 4).position(time) == expected


@pytest.mark.parametrize("time, expected",
                         [(0, False), (1, True), (2, False)])
def test_is_open(time, expected):
    assert Disk(5, 4).is_open(time) == expected


@pytest.mark.parametrize("index, expected",
                         [(0, Disk(npos=5, start=4)),
                          (1, Disk(npos=2, start=1))
                          ])
def test_parse(puzzle, index, expected):
    assert puzzle.sculpture.disks[index] == expected


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
