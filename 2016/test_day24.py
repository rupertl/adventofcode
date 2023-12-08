"""Tests Day 24."""
import pytest
from puzzle_data import PuzzleData
from day24 import Day24, Point


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day24(PuzzleData("data/sample/24"))


@pytest.mark.parametrize("point, expected",
                         [(Point(0, 0), False),
                          (Point(0, 1), False),
                          (Point(2, 1), True),
                          (Point(2, 2), False),
                          ])
def test_parse_grid(puzzle, point, expected):
    assert puzzle.ducts.is_open(point) == expected


@pytest.mark.parametrize("index, expected",
                         [(0, Point(1, 1)),
                          (1, Point(3, 1)),
                          (4, Point(1, 3)),
                          ])
def test_parse_pou(puzzle, index, expected):
    assert puzzle.ducts.poi(index) == expected


@pytest.mark.parametrize("where, expected",
                         [(Point(1, 1), [Point(1, 2), Point(2, 1)]),
                          (Point(1, 2), [Point(1, 1), Point(1, 3)]),
                          (Point(4, 3), [Point(3, 3), Point(5, 3)]),
                          ])
def test_open_neightbours(puzzle, where, expected):
    assert set(puzzle.ducts.open_neightbours(where)) == set(expected)


@pytest.mark.parametrize("poi_from, poi_to, expected",
                         [(0, 4, 2),
                          (4, 1, 4),
                          (1, 2, 6),
                          (2, 3, 2),
                          (0, 1, 2),
                          (0, 2, 8),
                          (0, 3, 10),
                          ])
def test_poi_path(puzzle, poi_from, poi_to, expected):
    assert puzzle.ducts.find_poi_path(poi_from, poi_to) == expected


def test_find_all_poi_paths(puzzle):
    distances = puzzle.ducts.find_all_poi_paths()
    expected = [[0,  2,  8, 10,  2],
                [2,  0,  6,  8,  4],
                [8,  6,  0,  2, 10],
                [10, 8,  2,  0,  8],
                [2,  4, 10,  8,  0]]
    assert distances == expected


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
