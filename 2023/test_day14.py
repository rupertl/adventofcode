"""Tests Day 14."""
import pytest
from puzzle_data import PuzzleData
from day14 import Day14


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day14(PuzzleData("data/sample/14"))


@pytest.mark.parametrize("row, col, expected",
                         ([[0, 0, 'O'],
                           [0, 1, '.'],
                           [0, 5, '#'],
                           [9, 0, '#'],
                           ]))
def test_parse(puzzle, row, col, expected):
    assert puzzle.dish.at(row, col) == expected


@pytest.mark.parametrize("isRow, index, expected1, expected2",
                         ([[True, 1, "O.OO#....#", "#.#.#.#.#O"],
                           [False, 1, "...OO....O", "##.OO.##.."],
                           ]))
def test_get_set(puzzle, isRow, index, expected1, expected2):
    assert puzzle.dish.get(isRow, index) == list(expected1)
    puzzle.dish.set(isRow, index, expected2)
    assert puzzle.dish.get(isRow, index) == list(expected2)


@pytest.mark.parametrize("line, order, expected",
                         ([['O.O', True, 'OO.'],
                           ['O.O', False, '.OO'],
                           ['###', False, '###'],
                           ['O.O.O.#O', True, 'OOO...#O'],
                           ["O#..OO.##OO#...##O", True, "O#OO...##OO#...##O"],
                           ]))
def test_sort(puzzle, line, order, expected):
    assert ''.join(puzzle.dish.sort(line, order)) == expected


tiltedN = """\
OOOO.#.O..
OO..#....#
OO..O##..O
O..#.OO...
........#.
..#....#.#
..O..#.O.O
..O.......
#....###..
#....#....
"""


def test_tilt(puzzle):
    puzzle.dish.tilt('N')
    assert str(puzzle.dish) == tiltedN


testCycles = ["""\
.....#....
....#...O#
...OO##...
.OO#......
.....OOO#.
.O#...O#.#
....O#....
......OOOO
#...O###..
#..OO#....
""",
              """\
.....#....
....#...O#
.....##...
..O#......
.....OOO#.
.O#...O#.#
....O#...O
.......OOO
#..OO###..
#.OOO#...O
""",
              """\
.....#....
....#...O#
.....##...
..O#......
.....OOO#.
.O#...O#.#
....O#...O
.......OOO
#...O###.O
#.OOO#...O
"""]


@pytest.mark.parametrize("cycles", ([1, 2, 3]))
def test_spin_cycle(puzzle, cycles):
    for _ in range(cycles):
        puzzle.dish.spin_cycle()
    assert str(puzzle.dish) == testCycles[cycles - 1]


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
