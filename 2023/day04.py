"""2023 Day 4: Scratchcards"""


# pylint: disable=invalid-name,too-few-public-methods


import re
from collections import defaultdict
from puzzle import Puzzle


class Scratchcard():
    """Represents a scratch card where there are a set of winning
    numbers and a set of numbers you have drawn. Each number that
    matches doubles the number of points."""
    def __init__(self, line):
        self.parse(line)

    def parse(self, line):
        """Parse a line of input into a card id, a set of winning
        numbers and a set of drawn numbers."""
        # Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        mat = re.search(r'Card\s+(\d+): ([\d ]+) \| ([\d ]+)', line)
        self.cid = int(mat.group(1))
        self.winning = {int(n) for n in mat.group(2).split()}
        self.drawn = {int(n) for n in mat.group(3).split()}

    def numMatches(self):
        """How many winning numbers did we match?"""
        return len(self.winning.intersection(self.drawn))

    def score(self):
        """Return the winning score, 1 for 1 match, then doubling for
        each other winning item."""
        matched = self.numMatches()
        if matched > 0:
            return 2 ** (matched - 1)
        return 0


class ScratchcardCounter():
    """Counts the number of scratchcards we have after winning copies
    of cards."""
    def __init__(self, cards):
        self.cards = cards
        self.copies = defaultdict(lambda: 1)  # default num copies is 1

    def count_all(self):
        """Calculate the number of copies of cards we get."""
        # Note from puzzle text: Cards will never make you copy a card
        # past the end of the table.
        self.copies.clear()     # reset if we get called again
        for card in self.cards:
            this_cid = card.cid
            matched = card.numMatches()
            num_copies = self.copies[this_cid]
            for next_cid in range(this_cid + 1, this_cid + 1 + matched):
                self.copies[next_cid] += num_copies
        return sum(self.copies.values())


class Day04(Puzzle):
    """2023 Day 4: Scratchcards"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 4

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        lines = self.puzzle_data.input_as_lines()
        self.cards = [Scratchcard(line) for line in lines]
        self.counter = ScratchcardCounter(self.cards)

    def calculate_a(self):
        """Part A: How many points are they worth in total?"""
        return sum((c.score() for c in self.cards))

    def calculate_b(self):
        """Part B. How many total scratchcards do you end up with?"""
        return self.counter.count_all()
