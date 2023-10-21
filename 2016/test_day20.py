"""Tests Day 20."""
import pytest
from puzzle_data import PuzzleData
from day20 import Day20, Range, IPRanges


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day20(PuzzleData("data/sample/20"))


def test_sr_single_parse():
    sr = IPRanges(["1-10"])
    assert sr.ranges == [Range(1, 10)]


def test_sr_insert_subsumed():
    sr = IPRanges(["3-7"])
    sr.insert(Range(4, 5))
    assert sr.ranges == [Range(3, 7)]


def test_sr_insert_subsumes():
    sr = IPRanges(["4-5"])
    sr.insert(Range(3, 7))
    assert sr.ranges == [Range(3, 7)]


def test_sr_insert_left():
    sr = IPRanges(["3-7"])
    sr.insert(Range(2, 5))
    assert sr.ranges == [Range(2, 7)]


def test_sr_insert_right():
    sr = IPRanges(["3-7"])
    sr.insert(Range(5, 9))
    assert sr.ranges == [Range(3, 9)]


def test_sr_insert_start():
    sr = IPRanges(["4-6"])
    sr.insert(Range(1, 2))
    assert sr.ranges == [Range(1, 2), Range(4, 6)]


def test_sr_insert_end():
    sr = IPRanges(["4-6"])
    sr.insert(Range(8, 10))
    assert sr.ranges == [Range(4, 6), Range(8, 10)]


def test_sr_order_3():
    sr = IPRanges(["10-12", "1-2", "4-6"])
    assert sr.ranges == [Range(1, 2), Range(4, 6), Range(10, 12)]


def test_sr_replace_subranges():
    sr = IPRanges(["2-3", "5-6", "7-9", "1-10"])
    assert sr.ranges == [Range(1, 10)]


def test_sr_contiguous_r():
    sr = IPRanges(["1-5", "5-10", "11-20"])
    assert sr.ranges == [Range(1, 20)]


def test_sr_contiguous_l():
    sr = IPRanges(["10-20", "6-10", "1-5"])
    assert sr.ranges == [Range(1, 20)]


def test_part1_solurion(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solurion(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
