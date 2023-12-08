"""Tests Day 6."""
import pytest
from puzzle_data import PuzzleData
from day06 import Day06


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day06(PuzzleData("data/sample/06"))


@pytest.mark.parametrize("index, time, distance",
                         [[0, 7, 9],
                          [1, 15, 40],
                          [2, 30, 200],
                          ])
def test_parse(puzzle, index, time, distance):
    assert puzzle.racer.races1[index].time == time
    assert puzzle.racer.races1[index].distance == distance


def test_parse_badKerning(puzzle):
    assert puzzle.racer.races2[0].time == 71530
    assert puzzle.racer.races2[0].distance == 940200


@pytest.mark.parametrize("index, expected",
                         [[0, [2, 3, 4, 5]],
                          [1, [4, 5, 6, 7, 8, 9, 10, 11]],
                          [2, [11, 12, 13, 14, 15, 16, 17, 18, 19]],
                          ])
def test_winners(puzzle, index, expected):
    assert puzzle.racer.races1[index].winners() == expected


@pytest.mark.parametrize("index, expected",
                         [[0, 4],
                          [1, 8],
                          [2, 9],
                          ])
def test_winners_count(puzzle, index, expected):
    assert puzzle.racer.races1[index].winners_count() == expected


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
