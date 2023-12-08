"""Tests Day 7."""
import pytest
from puzzle_data import PuzzleData
from day07 import Day07, Hand, HandType


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day07(PuzzleData("data/sample/07"))


@pytest.mark.parametrize("index, cards, bid",
                         ([[0, [3, 2, 10, 3, 13], 765],
                           [1, [10, 5, 5, 11, 5], 684],
                           [2, [13, 13, 6, 7, 7], 28],
                           [3, [13, 10, 11, 11, 10], 220],
                           [4, [12, 12, 12, 11, 14], 483]
                           ]))
def test_parse(puzzle, index, cards, bid):
    assert puzzle.game.hands[index].cards == cards
    assert puzzle.game.hands[index].bid == bid


@pytest.mark.parametrize("index, handType",
                         ([[0, HandType.ONE_PAIR],
                           [1, HandType.THREE_KIND],
                           [2, HandType.TWO_PAIR],
                           [3, HandType.TWO_PAIR],
                           [4, HandType.THREE_KIND]
                           ]))
def test_hand_type(puzzle, index, handType):
    assert puzzle.game.hands[index].handType == handType


@pytest.mark.parametrize("cards, handType",
                         ([[[1, 2, 3, 4, 5], HandType.HIGH_CARD],
                           [[1, 1, 1, 2, 2], HandType.FULL_HOUSE],
                           [[1, 1, 1, 1, 5], HandType.FOUR_KIND],
                           [[10, 10, 10, 10, 10], HandType.FIVE_KIND]
                           ]))
def test_hand_type_others(cards, handType):
    hand = Hand(cards, 0)
    assert hand.handType == handType


@pytest.mark.parametrize("smaller, larger",
                         ([[0, 1], [0, 2], [0, 3], [0, 4],
                           [3, 1], [3, 2], [3, 4],
                           [2, 1], [2, 4],
                           ]))
def test_sorting(puzzle, smaller, larger):
    assert puzzle.game.hands[smaller] < puzzle.game.hands[larger]


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
