"""Tests Day 4."""
import pytest
from puzzle_data import PuzzleData
from day04 import Day04, CountedLetter, Room


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day04(PuzzleData("data/sample/04"))


def test_countedletter_comparison_letter():
    assert CountedLetter('a') > CountedLetter('z')


def test_countedletter_comparison_count():
    assert CountedLetter('z').inc() > CountedLetter('a')


@pytest.mark.parametrize("index, expected",
                         [(0, "aaaaa-bbb-z-y-x"),
                          (1, "a-b-c-d-e-f-g-h"),
                          (2, "not-a-real-room"),
                          (3, "totally-real-room")])
def test_parse_room_name(puzzle, index, expected):
    assert puzzle.rooms[index].name == expected


@pytest.mark.parametrize("index, expected",
                         [(0, 123), (1, 987), (2, 404), (3, 200)])
def test_parse_room_sector_id(puzzle, index, expected):
    assert puzzle.rooms[index].sector_id == expected


@pytest.mark.parametrize("index, expected",
                         [(0, "abxyz"), (1, "abcde"),
                          (2, "oarel"), (3, "decoy")])
def test_parse_room_supplied_checksum(puzzle, index, expected):
    assert puzzle.rooms[index].supplied_checksum == expected


@pytest.mark.parametrize("index, expected",
                         [(0, "abxyz"), (1, "abcde"),
                          (2, "oarel"), (3, "loart")])  # 3 is different
def test_calc_checksum(puzzle, index, expected):
    assert puzzle.rooms[index].calculated_checksum == expected


def test_part1_solurion(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_decrypt():
    r = Room("qzmt-zixmtkozy-ivhz-343[zzzzz]")  # don't care about the checksum
    assert r.decrypt_name() == "very encrypted name"

# It's not possible to calculate part b given the sample input, as it
# relies on a room name that is only present in the full input.
