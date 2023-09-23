"""Tests Day 12."""
import pytest
from puzzle_data import PuzzleData
from day12 import Day12, Assembunny


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day12(PuzzleData("data/sample/12"))


def test_register_register_copy():
    a = Assembunny(('cpy 18 d', 'cpy d a'))
    a.run()
    assert a.get_register_a() == 18


def test_jnz_no_branch():
    a = Assembunny(('cpy 0 a', 'jnz a 2', 'inc a', 'inc a'))
    a.run()
    assert a.get_register_a() == 2


def test_part1_solurion(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solurion(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
