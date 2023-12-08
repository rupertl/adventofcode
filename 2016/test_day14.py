"""Tests Day 14."""
import pytest
from puzzle_data import PuzzleData
from day14 import Day14, find_runs


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day14(PuzzleData("data/sample/14"))


@pytest.mark.parametrize("s, expected",
                         [('', (None, set())),
                          ('abc', (None, set())),
                          ('aEbEcEdE', (None, set())),
                          ('abcEEfgh', (None, set())),
                          # Run of 3
                          ('abcdEEEfgh', ('E', set())),
                          # A run of 4 is a run of 3
                          ('abcdEEEEfgh', ('E', set())),
                          # Only first 3 counts
                          ('abcdEEEfGGGh', ('E', set())),
                          # A run of 5 is run of 3 also
                          ('abcdEEEEEfgh', ('E', {'E'})),
                          # Could have more than one run of 5
                          ('abcdEEEEEfGGGGGh', ('E', {'E', 'G'})),
                          # Could have a run of 3 and several runs of 5
                          ('abCCCdEEEEEfGGGGGh', ('C', {'E', 'G'})),
                          # Just a run of 3
                          ('AAA', ('A', set())),
                          # Just a run of 5
                          ('AAAAA', ('A', {'A'})),
                          # Run of 3 at the start
                          ('AAAbcde', ('A', set())),
                          # Run of 5 at the start
                          ('AAAAAbcd', ('A', {'A'})),
                          # Run of 3 at the end
                          ('abcdEEE', ('E', set())),
                          # Run of 5 at the end
                          ('abcdEEEEE', ('E', {'E'})),
                          ])
def test_find_runs(s, expected):
    assert find_runs(s) == expected


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


@pytest.mark.skip(reason="too slow")
def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
