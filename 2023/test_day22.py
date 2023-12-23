"""Tests Day 22."""
import pytest
from puzzle_data import PuzzleData
from day22 import Day22, Block, Point


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day22(PuzzleData("data/sample/22"))


def test_parse(puzzle):
    assert puzzle.slab.blocks[1] == Block(1, Point(x=1, y=0, z=1),
                                          Point(x=1, y=2, z=1))


# For each block 1-7 (A-G) find what the lowest z level they settled at
@pytest.mark.parametrize("bid, expected",
                         ([[1, 1], [2, 2], [3, 2], [4, 3],
                           [5, 3], [6, 4], [7, 5],
                           ]))
def test_settle(puzzle, bid, expected):
    block = puzzle.slab.blocks[bid]
    assert min(block.start.z, block.end.z) == expected


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
