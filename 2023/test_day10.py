"""Tests Day 10."""
import pytest
from puzzle_data import PuzzleData
from day10 import Day10, Point, PipeMaze, blow_up


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day10(PuzzleData("data/sample/10"))


def test_find_start(puzzle):
    assert puzzle.pipeMaze.start == Point(2, 0)


def test_moves_from_start(puzzle):
    expected = {Point(2, 1), Point(3, 0)}
    assert puzzle.pipeMaze.valid_move(puzzle.pipeMaze.start) == expected


def test_bounding_box(puzzle):
    expected = (Point(0, 0), Point(4, 4))
    assert puzzle.pipeMaze.bounding_box() == expected


testCase2 = [
    "...........",
    ".S-------7.",
    ".|F-----7|.",
    ".||.....||.",
    ".||.....||.",
    ".|L-7.F-J|.",
    ".|..|.|..|.",
    ".L--J.L--J.",
    "..........."
]


def test_bounding_box_2():
    pm = PipeMaze(testCase2)
    expected = (Point(1, 1), Point(7, 9))
    assert pm.bounding_box() == expected


def test_internal_count_2():
    pm = PipeMaze(testCase2)
    assert pm.find_internal_count() == 4


testCase3 = [
    "..........",
    ".S------7.",
    ".|F----7|.",
    ".||....||.",
    ".||....||.",
    ".|L-7F-J|.",
    ".|..||..|.",
    ".L--JL--J.",
    "..........",
]


def test_internal_count_3():
    pm = blow_up(PipeMaze(testCase3))
    assert pm.find_internal_count() == 4


testCase4 = [
    ".F----7F7F7F7F-7....",
    ".|F--7||||||||FJ....",
    ".||.FJ||||||||L7....",
    "FJL7L7LJLJ||LJ.L-7..",
    "L--J.L7...LJS7F-7L7.",
    "....F-J..F7FJ|L7L7L7",
    "....L7.F7||L7|.L7L7|",
    ".....|FJLJ|FJ|F7|.LJ",
    "....FJL-7.||.||||...",
    "....L---J.LJ.LJLJ...",
]


def test_internal_count_4():
    pm = blow_up(PipeMaze(testCase4))
    assert pm.find_internal_count() == 8


testCase5 = [
    "FF7FSF7F7F7F7F7F---7",
    "L|LJ||||||||||||F--J",
    "FL-7LJLJ||||||LJL-77",
    "F--JF--7||LJLJ7F7FJ-",
    "L---JF-JLJ.||-FJLJJ7",
    "|F|F-JF---7F7-L7L|7|",
    "|FFJF7L7F-JF7|JL---7",
    "7-L-JL7||F7|L7F-7F7|",
    "L.L7LFJ|||||FJL7||LJ",
    "L7JLJL-JLJLJL--JLJ.L",
]


def test_internal_count_5():
    pm = blow_up(PipeMaze(testCase5))
    assert pm.find_internal_count() == 10


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')
