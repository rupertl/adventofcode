"""2023 Day 15: Lens Library"""


# pylint: disable=invalid-name,too-few-public-methods

from collections import namedtuple
from puzzle import Puzzle


Box = namedtuple('Box', 'order lenses')


def HASH(message):
    """Calculates the hash of the message."""
    val = 0
    for ch in message:
        val += ord(ch)
        val *= 17
        val %= 256
    return val


def HASH_words(words):
    """Hash a sequence of comma separated words."""
    return [HASH(word) for word in words.split(',')]


def sum_HASH_words(words):
    """Return the sum of the HASH value for each word in words."""
    return sum(HASH_words(words))


class HASHMAP():
    """A set of boxes containing lenses arranged in a way suspiciously
    like a well known data structure."""
    def __init__(self, line):
        self.boxes = [Box(order=[], lenses={}) for _ in range(256)]
        for operation in line.split(','):
            self.apply(operation)

    def apply(self, operation):
        """Apply the add/remove operation specified."""
        if operation[-1] == '-':
            self.remove(operation[:-1])
        else:
            label, focal = operation.split('=')
            self.add(label, int(focal))

    def remove(self, label):
        """Remove the lens with the given label."""
        box = self.boxes[HASH(label)]
        if label in box.lenses:
            del box.lenses[label]
            box.order.remove(label)

    def add(self, label, focal):
        """Add the lens with given focal length to the box."""
        box = self.boxes[HASH(label)]
        if label not in box.lenses:
            box.order.append(label)
        box.lenses[label] = focal

    def focusing_power(self):
        """Calculate the focusing power of the lenses."""
        return sum(((boxIndex + 1) * (lensIndex + 1) * box.lenses[label]
                    for boxIndex, box in enumerate(self.boxes)
                    for lensIndex, label in enumerate(box.order)))


class Day15(Puzzle):
    """2023 Day 15: Lens Library"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 15

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.line = self.puzzle_data.input_as_string()

    def calculate_a(self):
        """Part A: Run the HASH algorithm on each step in the
        initialization sequence. What is the sum of the results?"""
        return sum_HASH_words(self.line)

    def calculate_b(self):
        """Part B. What is the focusing power of the resulting lens
        configuration?"""
        hashmap = HASHMAP(self.line)
        return hashmap.focusing_power()
