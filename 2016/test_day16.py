"""Tests Day 16."""
import pytest
from puzzle_data import PuzzleData
from day16 import Day16, generate_dragon, generate_checksum


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day16(PuzzleData("data/sample/16"))


@pytest.mark.parametrize("seed, expected",
                         [('1', '100'),
                          ('0', '001'),
                          ('11111', '11111000000'),
                          ('111100001010', '1111000010100101011110000'),
                          ])
def test_generate_dragon(seed, expected):
    assert generate_dragon(seed, len(expected)) == expected


@pytest.mark.parametrize("data, expected",
                         [('110010110100', '100'),
                          ('10000011110010000111', '01100')
                          ])
def test_generate_checksum(data, expected):
    assert generate_checksum(data) == expected


def test_part1_solurion(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')

# b is kind of the same as a but takes longer, so not tested
