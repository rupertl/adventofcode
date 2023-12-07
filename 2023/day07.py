"""2023 Day 7: Camel Cards"""


# pylint: disable=invalid-name,too-few-public-methods

from enum import Enum, auto
from collections import Counter
from puzzle import Puzzle


class HandType(Enum):
    """Maps hand type eg two pairs to an ordered value."""
    HIGH_CARD = auto()
    ONE_PAIR = auto()
    TWO_PAIR = auto()
    THREE_KIND = auto()
    FULL_HOUSE = auto()
    FOUR_KIND = auto()
    FIVE_KIND = auto()


# if jokers are present, they will have the value 0
JOKER = 0


class Hand():
    """Represents a hand of cards in the game of Camel Cards."""
    def __init__(self, cards, bid):
        # cards is an array of ints, 14 = A ... 2
        self.cards = cards
        self.bid = bid
        self.handType = self.assign_hand_type()

    def assign_hand_type(self):
        """Work out what type of hand we have."""
        ctr = Counter(self.cards)
        groups = sorted(ctr.values())
        match groups:
            case [5]:
                ht = HandType.FIVE_KIND
            case [1, 4]:
                ht = HandType.FOUR_KIND
            case [2, 3]:
                ht = HandType.FULL_HOUSE
            case [1, 1, 3]:
                ht = HandType.THREE_KIND
            case [1, 2, 2]:
                ht = HandType.TWO_PAIR
            case [1, 1, 1, 2]:
                ht = HandType.ONE_PAIR
            case _:
                ht = HandType.HIGH_CARD
        numJokers = ctr[JOKER]
        if numJokers > 0:
            return self.promote(ht, numJokers)
        return ht

    def promote(self, ht, numJokers):
        """In the prescence of jokers, we promote the hand type to a
        higher value, but keep the joker symbol in cards so we can
        still sort correctly."""
        match ht:
            case HandType.HIGH_CARD:
                ht = HandType.ONE_PAIR
            case HandType.ONE_PAIR:
                ht = HandType.THREE_KIND
            case HandType.TWO_PAIR:
                ht = (HandType.FULL_HOUSE if numJokers == 1
                      else HandType.FOUR_KIND)
            case HandType.THREE_KIND:
                ht = HandType.FOUR_KIND
            case _:
                ht = HandType.FIVE_KIND
        return ht

    def __lt__(self, other):
        if not self.handType.value == other.handType.value:
            return self.handType.value < other.handType.value
        for index, card in enumerate(self.cards):
            if not card == other.cards[index]:
                return card < other.cards[index]
        return False


class Game():
    """Represents a game of Camel Cards"""
    def __init__(self, lines, haveJokers=False):
        self.haveJokers = haveJokers
        self.hands = self.parse(lines)

    def parse(self, lines):
        """Parse each line of puzzle input to a Hand."""
        # eg 32T3K 765
        hands = []
        for line in lines:
            cardText, bidText = line.split()
            cards = [self.card_text_to_card(ch) for ch in cardText]
            bid = int(bidText)
            hands.append(Hand(cards, bid))
        return hands

    def card_text_to_card(self, cardText):
        """Convert a card text representation eg T to a value eg 10."""
        card_map = {'T': 10, 'J': 11, 'Q': 12, 'K': 13, 'A': 14}
        if self.haveJokers and cardText == 'J':
            return JOKER
        return card_map[cardText] if cardText in card_map else int(cardText)

    def winnings(self):
        """Calculate the winnings from sorted hands and bids."""
        winnings = 0
        rankedHands = sorted(self.hands)
        for index, hand in enumerate(rankedHands):
            winnings += (index + 1) * hand.bid
        return winnings


class Day07(Puzzle):
    """2023 Day 7: Camel Cards"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 7

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        lines = self.puzzle_data.input_as_lines()
        self.game = Game(lines)
        self.gameJokers = Game(lines, haveJokers=True)

    def calculate_a(self):
        """Part A: What are the new total winnings?"""
        return self.game.winnings()

    def calculate_b(self):
        """Part B. What are the new total winnings (with J as a
        joker)?"""
        return self.gameJokers.winnings()
