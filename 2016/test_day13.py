"""Tests Day 13."""
import pytest
from puzzle_data import PuzzleData
from day13 import Day13, Point, CubicleFarm


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day13(PuzzleData("data/sample/13"))


@pytest.mark.parametrize("point, expected",
                         [(Point(0, 0), False),
                          (Point(1, 0), True),
                          (Point(2, 0), False),
                          (Point(0, 3), True),
                          (Point(1, 3), True),
                          (Point(2, 3), True),
                          (Point(3, 3), False),
                          ])
def test_is_wall(point, expected):
    c = CubicleFarm()
    assert c.is_wall(point) == expected


def test_is_open():
    c = CubicleFarm()
    assert not c.is_open(Point(0, 3))


def test_neighbours_at_middle():
    c = CubicleFarm()
    expected = [Point(2, 2), Point(3, 3)]
    assert frozenset(c.neighbours(Point(2, 3))) == frozenset(expected)


def test_neighbours_at_wall():
    c = CubicleFarm()
    expected = [Point(0, 0), Point(1, 1)]
    assert frozenset(c.neighbours(Point(0, 1))) == frozenset(expected)


def test_find_path_7_4(puzzle):
    assert puzzle.solver.find_path_to(Point(7, 4)) == 11


# There is no solution for the sample data to get to (31, 39) and no
# provided answer for part b using sample data.
