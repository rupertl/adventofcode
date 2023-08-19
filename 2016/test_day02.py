"""Tests Day 2."""
import pytest
from puzzle_data import PuzzleData
from day02 import Day02, Keypad, KEYPAD_1_D


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day02(PuzzleData("data/sample/02"))


def test_keypad_start_pos_5():
    k = Keypad()
    assert k.current_key() == '5'


def test_keypad_move_up_1():
    k = Keypad()
    k.move('U')
    assert k.current_key() == '2'


def test_keypad_move_down_2():
    k = Keypad()
    k.move('D')
    k.move('D')
    assert k.current_key() == '8'


def test_keypad_code_after_ull():
    k = Keypad()
    k.apply_moves(['U', 'L', 'L'])
    assert k.get_code() == "1"


def test_keypad_code_after_ull_rrddd():
    k = Keypad()
    k.apply_moves(['U', 'L', 'L'])
    k.apply_moves(['R', 'R', 'D', 'D', 'D'])
    assert k.get_code() == "19"


def test_part1_solurion(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_keypad_1d_start_pos_5():
    k = Keypad(KEYPAD_1_D)
    assert k.current_key() == '5'


def test_keypad_1d_move_up_1():
    k = Keypad(KEYPAD_1_D)
    k.move('U')
    assert k.current_key() == '5'


def test_keypad_1d_code_after_ull_rrddd():
    k = Keypad(KEYPAD_1_D)
    k.apply_moves(['U', 'L', 'L'])
    k.apply_moves(['R', 'R', 'D', 'D', 'D'])
    assert k.get_code() == "5D"


def test_part2_solurion(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
