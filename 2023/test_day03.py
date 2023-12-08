"""Tests Day 3."""
import pytest
from puzzle_data import PuzzleData
from day03 import Day03, EngineSchematic


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day03(PuzzleData("data/sample/03"))


def test_end_row_case_edge():
    lines = ["...#", ".123"]
    assert EngineSchematic(lines).parts == [123]


def test_end_row_case_not_edge():
    lines = ["...#", ".12."]
    assert EngineSchematic(lines).parts == [12]


def test_find_parts(puzzle):
    assert puzzle.schematic.parts == [467, 35, 633, 617, 592, 755, 664, 598]


def test_gear_ratios(puzzle):
    assert sorted(puzzle.schematic.gearRatios) == [16345, 451490]


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
