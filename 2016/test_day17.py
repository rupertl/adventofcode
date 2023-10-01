"""Tests Day 17."""
import pytest
from puzzle_data import PuzzleData
from day17 import Day17, Point, Rooms, Solver


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzl7():
    return Day17(PuzzleData("data/sample/17"))


@pytest.mark.parametrize("point, expected",
                         [(Point(0, 0), True),
                          (Point(1, 0), True),
                          (Point(0, -1), False),
                          (Point(99, 42), False),
                          ])
def test_rooms_walls(point, expected):
    r = Rooms('hijkl')
    assert r.not_at_wall(point) == expected


def test_rooms_adjacents_0():
    r = Rooms('hijkl')
    adj = r.adjacent()
    assert len(adj) == 1
    assert adj[0].position == Point(0, 1)


def test_rooms_adjacents_1():
    r = Rooms('hijkl')
    rr = r.adjacent()[0]
    adj = rr.adjacent()
    assert len(adj) == 2
    assert adj[0].position == Point(0, 0)
    assert adj[1].position == Point(1, 1)


@pytest.mark.parametrize("seed, expected",
                         [('kglvqrro', 'DDUDRLRRUDRD'),
                          ('ulqzkmiv', 'DRURDRUDDLLDLUURRDULRLDUUDDDRR'),
                          ])
def test_shortest_paths(seed, expected):
    s = Solver(seed)
    assert s.find_path() == expected


@pytest.mark.skip(reason="too slow")
@pytest.mark.parametrize("seed, expected",
                         [('kglvqrro', 492),
                          ('ulqzkmiv', 830),
                          ])
def test_paths(seed, expected):
    s = Solver(seed)
    assert s.find_longest_path() == expected


def test_part1_solurion(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')

def test_part2_solurion(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
