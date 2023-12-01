"""Tests Day 1."""
import pytest
from puzzle_data import PuzzleData
from day01 import Day01, calibration_value, expand_digits


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day01(PuzzleData("data/sample/01"))


def test_cv_simple():
    assert calibration_value("pqr3stu8vwx") == 38


def test_cv_single_digit():
    assert calibration_value("treb7uchet") == 77


# It does not appear that there are any zeros in the input anyway.    
def test_cv_leading_0():
    assert calibration_value("abc0xyz8ff") == 8


@pytest.mark.parametrize("line, expected",
                         [["two1nine", 29],
                          ["eightwothree", 83],
                          ["abcone2threexyz", 13],
                          ["xtwone3four", 24],
                          ["4nineeightseven2", 42],
                          ["zoneight234", 14],
                          ["7pqrstsixteen", 76],
                          ])
def test_expand_digits(line, expected):
    assert calibration_value(expand_digits(line)) == expected


def test_expand_digits_overlap():
    assert calibration_value(expand_digits('eightwo')) == 82


def test_part1_solurion(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


# Sample data does not change results for part b unfortunately
def test_part2_solurion(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
