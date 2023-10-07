"""Tests Day 19."""
import pytest
from puzzle_data import PuzzleData
from day19 import Day19, CircularList


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day19(PuzzleData("data/sample/19"))


def test_circular_list_advance():
    c = CircularList(5)
    n2 = c.advance(c.head, 2)
    n3 = c.advance(n2, 1)
    assert n3.value == 3


def test_circular_list_advance_wrap():
    c = CircularList(5)
    n3 = c.advance(c.head, 3)
    n6 = c.advance(n3, 3)
    assert n6.value == 1


def test_circular_list_delete_next():
    c = CircularList(5)
    n = c.head
    c.delete(n.next)
    assert n.next.value == 2


def test_circular_list_delete_other():
    c = CircularList(5)
    n = c.head
    n2 = c.advance(n, 2)
    assert n2.value == 2
    c.delete(n2)
    n3 = c.advance(n, 2)
    assert n3.value == 3


def test_circular_list_delete_size():
    c = CircularList(5)
    n2 = c.advance(c.head, 2)
    c.delete(n2)
    n3 = c.advance(c.head, 2)
    c.delete(n3)
    assert c.size == 3


def test_circular_list_preserve_head():
    c = CircularList(2)
    assert c.head.value == 0
    c.delete(c.head)
    assert c.head.value == 1


def test_part1_solurion(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solurion(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
