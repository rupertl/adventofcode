"""Tests PuzzleData class."""
import pytest
from puzzle_data import PuzzleData


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


def test_sample_input_lines():
    pd = PuzzleData("data/sample/00")
    assert pd.input_as_lines() == ['x', 'y']


def test_sample_input_string():
    pd = PuzzleData("data/sample/00")
    assert pd.input_as_string() == "x"


def test_sample_solution_a():
    pd = PuzzleData("data/sample/00")
    assert pd.solution('a') == "xxyy"


def test_sample_solution_b():
    pd = PuzzleData("data/sample/00")
    assert pd.solution('b') == "XY"


def test_sample_has_input():
    pd = PuzzleData("data/sample/00")
    assert pd.has_input()


def test_sample_has_solution():
    pd = PuzzleData("data/sample/00")
    assert pd.has_solution('a')


def test_missing_day_has_no_data():
    pd = PuzzleData("data/sample/99")
    assert not pd.has_input()
    assert not pd.has_solution('a')
