"""Tests Day 4."""
import pytest
from puzzle_data import PuzzleData
from day04 import Day04


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day04(PuzzleData("data/sample/04"))


def test_parse(puzzle):
    assert puzzle.cards[0].cid == 1
    assert puzzle.cards[0].winning == {41, 48, 17, 83, 86}
    assert puzzle.cards[0].drawn == {6, 9, 48, 17, 83, 53, 86, 31}


@pytest.mark.parametrize("index, expected",
                         [[0, 8],  # index from 0, id from 1
                          [1, 2],
                          [2, 2],
                          [3, 1],
                          [4, 0],
                          [5, 0]])
def test_score(puzzle, index, expected):
    assert puzzle.cards[index].score() == expected


@pytest.mark.parametrize("index, expected",
                         [[1, 1],  # index from 1 for counter
                          [2, 2],
                          [3, 4],
                          [4, 8],
                          [5, 14],
                          [6, 1]])
def test_counter(puzzle, index, expected):
    puzzle.counter.count_all()
    assert puzzle.counter.copies[index] == expected


def test_part1_solurion(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solurion(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
