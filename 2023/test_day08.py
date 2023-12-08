"""Tests Day 8."""
import pytest
from puzzle_data import PuzzleData
from day08 import Day08, Navigator


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day08(PuzzleData("data/sample/08"))


def test_parse(puzzle):
    assert puzzle.navigator.directions == [0, 0, 1]
    assert puzzle.navigator.network['BBB'] == ('AAA', 'ZZZ')


def test_part1_solurion(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


# Part 1 sample input is different from part 2, so supply directly
def test_part2_solurion():
    lines = ["LR", "",
             "11A = (11B, XXX)",
             "11B = (XXX, 11Z)",
             "11Z = (11B, XXX)",
             "22A = (22B, XXX)",
             "22B = (22C, 22C)",
             "22C = (22Z, 22Z)",
             "22Z = (22B, 22B)",
             "XXX = (XXX, XXX)"]
    navigator = Navigator(lines)
    assert navigator.turns_to_all_zs() == 6
