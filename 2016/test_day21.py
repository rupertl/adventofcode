"""Tests Day 21."""
import pytest
from puzzle_data import PuzzleData
from day21 import Day21, Scrambler


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day21(PuzzleData("data/sample/21"))


test_cases = [('swap position 4 with position 0', 'abcde', 'ebcda'),
              ('swap letter d with letter b', 'ebcda', 'edcba'),
              ('reverse positions 0 through 4', 'edcba', 'abcde'),
              ('reverse positions 1 through 2', 'abcde', 'acbde'),
              ('rotate left 1 step', 'abcde', 'bcdea'),
              ('move position 1 to position 4', 'bcdea', 'bdeac'),
              ('move position 3 to position 0', 'bdeac', 'abdec'),
              ('rotate based on position of letter b', 'abdec', 'ecabd'),
              ('rotate based on position of letter d', 'ecabd', 'decab')]


@pytest.mark.parametrize("instruction, unscrambled, scrambled", test_cases)
def test_scramble_instruction(instruction, unscrambled, scrambled):
    sc = Scrambler(unscrambled)
    assert sc.scramble([instruction]) == scrambled


@pytest.mark.parametrize("instruction, unscrambled, scrambled", test_cases)
def test_descramble_instruction(instruction, unscrambled, scrambled):
    sc = Scrambler(scrambled)
    assert sc.descramble([instruction]) == unscrambled


@pytest.mark.parametrize("unscrambled, scrambled",
                         [('Abcdefgh', 'hAbcdefg'),  # 0 -> 1
                          ('zAbcdefg', 'fgzAbcde'),  # 1 -> 3
                          ('yzAbcdef', 'defyzAbc'),  # 2 -> 5
                          ('xyzAbcde', 'bcdexyzA'),  # 3 -> 7
                          ('wxyzAbcd', 'yzAbcdwx'),  # 4 -> 10 (2)
                          ('vwxyzAbc', 'wxyzAbcv'),  # 5 -> 12 (4)
                          ('uvwxyzAb', 'uvwxyzAb'),  # 6 -> 14 (6)
                          ('tuvwxyzA', 'Atuvwxyz'),  # 7 -> 16 (8)
                          ])
def test_rotate_position(unscrambled, scrambled):
    sc1 = Scrambler(unscrambled)
    assert sc1.scramble(['rotate based on position of letter A']) == scrambled
    sc2 = Scrambler(scrambled)
    assert sc2.descramble(['rotate based on position of letter A']
                          ) == unscrambled


def test_part1_solurion(puzzle):
    puzzle.to_scramble = 'abcde'
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solurion(puzzle):
    puzzle.to_unscramble = 'decab'
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
