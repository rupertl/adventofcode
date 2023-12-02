"""Tests Day 2."""
import pytest
from puzzle_data import PuzzleData
from day02 import Day02, BagGame


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day02(PuzzleData("data/sample/02"))


def test_parse(puzzle):
    # Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    bg = puzzle.games[0]
    assert bg.gid == 1
    assert bg.moves[0] == {'blue': 3, 'red': 4}
    assert bg.moves[1] == {'red': 1, 'green': 2, 'blue': 6}
    assert bg.moves[2] == {'green': 2}


@pytest.mark.parametrize("index, expected",
                         [[0, True],  # index from 0, id from 1
                          [1, True],
                          [2, False],
                          [3, False],
                          [4, True]])
def test_possible(puzzle, index, expected):
    assert puzzle.games[index].possible(puzzle.constraint) == expected


def test_fewest(puzzle):
    fewest = puzzle.games[0].fewest()
    assert fewest == {'red': 4, 'green': 2, 'blue': 6}


@pytest.mark.parametrize("index, expected",
                         [[0, 48],  # index from 0, id from 1
                          [1, 12],
                          [2, 1560],
                          [3, 630],
                          [4, 36]])
def test_power(puzzle, index, expected):
    assert puzzle.games[index].power() == expected


def test_part1_solurion(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solurion(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
