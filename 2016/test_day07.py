"""Tests Day 7."""
import pytest
from puzzle_data import PuzzleData
from day07 import Day07, IPAddress, has_abba, find_abas
from day07 import aba_to_bab, find_all_abas


# pylint: disable=invalid-name,missing-function-docstring


# The below will change dir to the directory of this file so pytest
# can be run from any other directory.
@pytest.fixture(autouse=True)
def change_test_dir(request, monkeypatch):
    monkeypatch.chdir(request.fspath.dirname)


@pytest.fixture(name="puzzle")
def fixture_puzzle():
    return Day07(PuzzleData("data/sample/07"))


@pytest.mark.parametrize("index, expected",
                         [(0, ["abba", "qrst"]),
                          (1, ["abcd", "xyyx"]),
                          (2, ["aaaa", "tyui"]),
                          (3, ["ioxxoj", "zxcvbn"])])
def test_parse_ip_address_supernet_segments(puzzle, index, expected):
    assert puzzle.ip_addrs[index].supernet_segments == expected


@pytest.mark.parametrize("index, expected",
                         [(0, ["mnop"]),
                          (1, ["bddb"]),
                          (2, ["qwer"]),
                          (3, ["asdfgh"])])
def test_parse_ip_address_hyperink_segments(puzzle, index, expected):
    assert puzzle.ip_addrs[index].hypernet_segments == expected


def test_parse_ip_address_multi_hyperink_segments():
    ia = IPAddress("abc[def]ghi[jkl]mno")
    assert len(ia.hypernet_segments) == 2 and len(ia.supernet_segments) == 3


def test_abba_yes():
    assert has_abba("abba")


def test_abba_no():
    assert not has_abba("abcd")


def test_abba_no_same_char():
    assert not has_abba("xaaaax")


def test_find_abas_aba():
    assert find_abas('aba') == {'aba'}


def test_find_abas_zazbz():
    assert find_abas('zazbz') == {'zaz', 'zbz'}


def test_find_all_abas():
    assert find_all_abas(["zazbz", "cdczaz"]) == {'zaz', 'zbz', 'cdc'}


def test_aba_to_bab():
    assert aba_to_bab('xyx') == 'yxy'


@pytest.mark.parametrize("index, expected",
                         [(4, True), (5, False), (6, True), (7, True)])
def test_supports_ssl(puzzle, index, expected):
    assert puzzle.ip_addrs[index].supports_ssl() == expected


def test_part1_solution(puzzle):
    puzzle.calculate_part('a')
    assert puzzle.results['a'] == puzzle.puzzle_data.solution('a')


def test_part2_solution(puzzle):
    puzzle.calculate_part('b')
    assert puzzle.results['b'] == puzzle.puzzle_data.solution('b')
