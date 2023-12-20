"""Tests Day 19."""
import pytest
from puzzle_data import PuzzleData
from day19 import Day19


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day19(PuzzleData("data/sample/19"))


def test_parse(puzzle):
    assert len(puzzle.ramp.workflows) == 11
    assert len(puzzle.ramp.workflows['px'].rules) == 3
    assert puzzle.ramp.workflows['px'].rules[0] == {'category': 'a',
                                                    'operator': '<',
                                                    'value': 2006,
                                                    'destination': 'qkq'}
    assert len(puzzle.ramp.parts) == 5
    assert puzzle.ramp.parts[0].ratings == {'x': 787, 'm': 2655,
                                            'a': 1222, 's': 2876}


def test_evaluate(puzzle):
    assert len(puzzle.ramp.destinations['A']) == 3
    assert len(puzzle.ramp.destinations['R']) == 2


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
