"""Tests Day 13."""
import pytest
from puzzle_data import PuzzleData
from day13 import Day13, Reflection


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day13(PuzzleData("data/sample/13"))


def test_parse(puzzle):
    assert len(puzzle.valley.patterns) == 2
    p1 = puzzle.valley.patterns[0]
    assert "".join(p1.rows[0]) == "#.##..##."
    assert "".join(p1.cols[0]) == "#.##..#"


@pytest.mark.parametrize("index, reflection",
                         ([[0, Reflection(2, 8)],
                           [1, Reflection(2, 6)],
                           ]))
def test_reflections(puzzle, index, reflection):
    assert puzzle.valley.patterns[index].reflection() == reflection


@pytest.mark.parametrize("index, expected",
                         ([[0, 5], [1, 400], ]))
def test_summary(puzzle, index, expected):
    assert puzzle.valley.patterns[index].summary() == expected


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
