"""Tests Day 12."""
import pytest
from puzzle_data import PuzzleData
from day12 import Day12, SpringRow


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day12(PuzzleData("data/sample/12"))


def test_parse():
    sr = SpringRow("???.### 1,1,3")
    assert sr.row == "???.###"
    assert sr.groups == (1, 1, 3)


def test_parse_folded():
    sr = SpringRow("?.# 1,3", folded=True)
    assert sr.row == "?.#??.#??.#??.#??.#"
    assert sr.groups == (1,  3) * 5


@pytest.mark.parametrize("candidate, expected",
                         ([["#.#.###", True],
                           ["##..###", False],
                           ]))
def test_matched(candidate, expected):
    sr = SpringRow("???.### 1,1,3")
    assert sr.matched_brute(candidate) == expected


@pytest.mark.parametrize("line, expected",
                         ([["### 3", 1],
                           ["###.### 3,3", 1],
                           [".###..###. 3,3", 1],
                           [".#?#..#?#. 3,3", 1],
                           ["?? 1", 2],
                           ["??? 1", 3],
                           ["##... 2", 1],
                           ["...## 2", 1],
                           ["??.?? 2", 2],
                           ["??.?? 1", 4],
                           ["??.?? 1,1", 4],
                           ["??.?? 2,1", 2],
                           ]))
def test_count_matches(line, expected):
    assert SpringRow(line).count_matches() == expected


@pytest.mark.parametrize("index, expected",
                         ([[0, 1],
                           [1, 4],
                           [2, 1],
                           [3, 1],
                           [4, 4],
                           [5, 10],
                           ]))
def test_count_matches_puzzle(puzzle, index, expected):
    assert puzzle.springRowsA[index].count_matches() == expected


@pytest.mark.parametrize("index, expected",
                         ([[0, 1],
                           [1, 16384],
                           [2, 1],
                           [3, 16],
                           [4, 2500],
                           [5, 506250],
                           ]))
def test_count_matches_folded(puzzle, index, expected):
    assert puzzle.springRowsB[index].count_matches() == expected


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
