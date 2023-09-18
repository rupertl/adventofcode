"""Tests Day 11."""
import pytest
from puzzle_data import PuzzleData
from day11 import Day11, Component, Facility


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day11(PuzzleData("data/sample/11"))


def test_component_safe_by_type():
    a = Component("Carbon", True)
    b = Component("Helium", True)
    assert a.safe(b)


def test_component_unsafe_by_type():
    a = Component("Carbon", True)
    b = Component("Helium", False)
    assert not a.safe(b)


def test_component_safe_by_combine():
    a = Component("Carbon", True)
    b = Component("Carbon", False)
    assert a.safe(b)


def test_component_complements():
    a = Component("Carbon", True)
    b = Component("Carbon", False)
    assert a.complements(b)


def test_component_unsafe_by_combine():
    a = Component("Carbon", True)
    b = Component("Helium", False)
    assert not a.safe(b)


def test_facility_finished_yes():
    a = Component("Carbon", True)
    b = Component("Carbon", False)
    f = Facility()
    f.add(3, a)
    f.add(3, b)
    f.elevator_at = 3
    assert f.is_finished()


def test_facility_finished_no():
    a = Component("Carbon", True)
    b = Component("Carbon", False)
    f = Facility()
    f.add(3, a)
    f.add(2, b)
    assert not f.is_finished()


def test_facility_update():
    f1 = Facility()
    moving = Component("Helium", False)
    f1.add(0, Component("Carbon", True))
    f1.add(0, Component("Carbon", False))
    f1.add(0, moving)
    f2 = f1.update(1, {moving})
    assert f1 != f2
    assert len(f1.floors[0]) == 3
    assert len(f1.floors[1]) == 0
    assert len(f2.floors[0]) == 2
    assert len(f2.floors[1]) == 1


@pytest.mark.parametrize("at, choices",
                         [(0, [1]), (1, [2, 0]), (3, [2]),])
def test_facility_next_floors(at, choices):
    f = Facility()
    f.elevator_at = at
    assert f.next_floors() == choices


@pytest.mark.parametrize("index, components",
                         [(0, 2), (1, 1), (2, 1), (3, 0)])
def test_facility_initial(puzzle, index, components):
    assert len(puzzle.solver.facility.floors[index]) == components


def test_facility_initial_1(puzzle):
    floor = puzzle.solver.facility.floors[1]
    assert floor == {Component("hydrogen", True)}


# This cheats slightly by assuming we can travel more than one floor
@pytest.mark.parametrize("to_floor, components, expected",
                         [
                             (1, {Component("hydrogen", False)}, True),
                             (1, {Component("lithium", False)}, False),
                             (1, {Component("hydrogen", False),
                                  Component("lithium", False)}, False),
                             (2, {Component("lithium", False)}, True),
                             (3, {Component("hydrogen", False),
                                  Component("lithium", False)}, True),
                         ])
def test_safe_update(puzzle, to_floor, components, expected):
    assert puzzle.solver.facility.can_update(to_floor, components) == expected


def test_part1_solurion(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solurion(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
