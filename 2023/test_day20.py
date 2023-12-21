"""Tests Day 20."""
import pytest
from puzzle_data import PuzzleData
from day20 import Day20, Propagator, FlipFlopGate, ConjunctionGate


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day20(PuzzleData("data/sample/20"))


def test_parse(puzzle):
    gates = puzzle.propagatorA.gates
    assert len(gates) == 5
    assert 'broadcaster' in gates
    assert gates['broadcaster'].downstreams == {'a', 'b', 'c'}
    assert 'a' in gates
    assert isinstance(gates['a'], FlipFlopGate)
    assert gates['a'].upstreams == {'broadcaster', 'inv'}
    assert 'inv' in gates
    assert isinstance(gates['inv'], ConjunctionGate)
    assert gates['inv'].upstreams == set('c')


def test_button_simple(puzzle):
    puzzle.propagatorA.press_button()
    assert puzzle.propagatorA.get_pulse_count() == {0: 8, 1: 4}


interestingExample = [
    "broadcaster -> a",
    "%a -> inv, con",
    "&inv -> b",
    "%b -> con",
    "&con -> output",
]


def test_interesting_example():
    p = Propagator(interestingExample)
    p.press_button()
    assert p.gates['output'].received == [1, 0]
    p.press_button()
    assert p.gates['output'].received == [1, 0, 1]
    p.press_button()
    assert p.gates['output'].received == [1, 0, 1, 0, 1]
    p.press_button()
    assert p.gates['output'].received == [1, 0, 1, 0, 1, 1]


def test_button_interesting_1000_product():
    p = Propagator(interestingExample)
    p.press_button(1000)
    assert p.get_pulse_product() == 11687500


def test_pulses_queued():
    # This got me for a long time, thanks to the hint on reddit that
    # unblocked me.
    # https://www.reddit.com/r/adventofcode/\
    # comments/18n6r4q/2023_day_20_part_1_rust_test_passed_but_fails_on/
    # If a gate gets more than one input per cycle, it can produce
    # more than one output.
    text = ["broadcaster -> a, b",
            "%a -> con", "%b -> con",
            "&con -> rx"]
    p = Propagator(text)
    p.press_button()
    assert p.get_pulse_count() == {0: 4, 1: 3}


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')
