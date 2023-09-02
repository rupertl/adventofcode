"""2016  Day 7: Internet Protocol Version 7"""


# pylint: disable=invalid-name,too-few-public-methods


import re
from puzzle import Puzzle


class IPAddress():
    """Represents an IP version 7 address."""
    def __init__(self, address):
        self.parse(address)

    def parse(self, address):
        """Break down an address into segments."""
        # eg for aaa[bbb]ccc we'd have two supernet segments
        # aaa and ccc and one hypernet segment bbb
        self.address = address
        self.supernet_segments = []
        self.hypernet_segments = []
        segment = ""
        in_hypernet = False
        for ch in self.address:
            if ch == '[':
                if in_hypernet:
                    raise RuntimeError("nested segnents not allowed")
                in_hypernet = True
                self.supernet_segments.append(segment)
                segment = ''
            elif ch == ']':
                if not in_hypernet:
                    raise RuntimeError("] without matching [ not allowed")
                in_hypernet = False
                self.hypernet_segments.append(segment)
                segment = ''
            else:
                segment = segment + ch
        # Now at end of string
        if in_hypernet:
            raise RuntimeError("[ without matching ] not allowed")
        self.supernet_segments.append(segment)

    def supports_tls(self):
        """Does this IP address support TLS? Must have at least
            one ABBA in a supernet segment and none in any hypernet
            segment."""
        return (self.count_abbas(self.supernet_segments) >= 1 and
                self.count_abbas(self.hypernet_segments) == 0)

    def count_abbas(self, segments):
        """Count the number of ABBAs in the list of segments."""
        return sum((has_abba(seg) for seg in segments))

    def supports_ssl(self):
        """Does this IP addr support SSL? Must have an aba in a
        supernet segment with a matching bab in a hypernet segment."""
        abas = find_all_abas(self.supernet_segments)
        babs = set((aba_to_bab(aba) for aba in abas))
        intersection = babs.intersection(find_all_abas(self.hypernet_segments))
        return len(intersection) > 0


def has_abba(segment):
    """Does this segment have an ABBA?"""
    mat = re.search(r"(\w)(\w)\2\1", segment)
    if mat and mat.group(1) != mat.group(2):
        return True
    return False


def find_abas(segment):
    """Find all abas (including bab etc) in a segment?"""
    abas = set()
    for index, ch in enumerate(segment):
        if index < 2:
            continue
        if ch == segment[index-2] and ch != segment[index-1]:
            abas.add(segment[index-2:index+1])
    return abas


def aba_to_bab(aba):
    """Convert aba to bab."""
    assert len(aba) == 3
    return aba[1] + aba[0] + aba[1]


def find_all_abas(segments):
    """Find all abas in all given segments and return as one set."""
    abas = (find_abas(segment) for segment in segments)
    return set().union(*abas)  # unpack list of sets to a single set


class Day07(Puzzle):
    """2016 Day 7: Internet Protocol Version 7"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 7

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.ip_addrs = [IPAddress(line) for line in
                         self.puzzle_data.input_as_lines()]

    def calculate_a(self):
        """Part A: How many IP addresses support TLS?"""
        return sum(ip.supports_tls() for ip in self.ip_addrs)

    def calculate_b(self):
        """Part B. How many IP addresses support SSL?"""
        return sum(ip.supports_ssl() for ip in self.ip_addrs)
