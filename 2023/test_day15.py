"""Tests Day 15."""
import pytest
from puzzle_data import PuzzleData
from day15 import Day15, HASH, HASH_words, Box, HASHMAP


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day15(PuzzleData("data/sample/15"))


def test_hash():
    assert HASH("HASH") == 52


def test_hash_words(puzzle):
    assert HASH_words(puzzle.line) == [30, 253, 97, 47, 14, 180,
                                       9, 197, 48, 214, 231]


def test_hashmap(puzzle):
    hm = HASHMAP(puzzle.line)
    assert hm.boxes[0] == Box(order=['rn', 'cm'],
                              lenses={'rn': 1, 'cm': 2})
    assert hm.boxes[3] == Box(order=['ot', 'ab', 'pc'],
                              lenses={'ot': 7, 'ab': 5, 'pc': 6})


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
