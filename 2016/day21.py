"""2016 Day 21: Scrambled Letters and Hash"""


# pylint: disable=invalid-name,too-few-public-methods

import collections
import re
from puzzle import Puzzle


class Scrambler():
    """Represents a series of instructions to perform on a text to
    produce a scrambled version for use in a password file."""
    def __init__(self, text):
        self.input = text
        self.letters = collections.deque(self.input)
        self.output = ""

    def generate_output(self):
        """Update the output based on the deque, and return it."""
        self.output = ''.join(self.letters)
        return self.output

    def scramble(self, instructions):
        """Scramble text according to instructions and return it."""
        for instruction in instructions:
            self.parse_instruction(instruction, descramble=False)
        return self.generate_output()

    def descramble(self, instructions):
        """Descramble text according to instructions and return it."""
        for instruction in reversed(instructions):
            self.parse_instruction(instruction, descramble=True)
        return self.generate_output()

    def parse_instruction(self, instruction, descramble=False):
        """Parse an individual instruction and update the deque."""
        mat = re.match(r'swap position (\d+) with position (\d+)', instruction)
        if mat:
            x, y = mat.groups()
            self.swap_position(int(x), int(y))
            return
        mat = re.match(r'swap letter (\w) with letter (\w)', instruction)
        if mat:
            x, y = mat.groups()
            self.swap_letter(x, y)
            return
        mat = re.match(r'reverse positions (\w) through (\w)', instruction)
        if mat:
            x, y = mat.groups()
            self.reverse_positions(int(x), int(y))
            return
        mat = re.match(r'rotate (left|right) (\d+) step', instruction)
        if mat:
            direction, steps = mat.groups()
            self.rotate(direction == 'right', int(steps), descramble)
            return
        mat = re.match(r'rotate based on position of letter (\w)', instruction)
        if mat:
            letter = mat.groups()[0]
            self.rotate_based_position(letter, descramble)
            return
        mat = re.match(r'move position (\d+) to position (\d+)', instruction)
        if mat:
            x, y = mat.groups()
            self.move_position(int(x), int(y), descramble)
            return
        raise RuntimeError(f'Unknown instruction {instruction}')

    def swap_position(self, x, y):
        """Swap letters by index x and y."""
        self.letters[x], self.letters[y] = self.letters[y], self.letters[x]

    def swap_letter(self, x, y):
        """Swap letters x and y"""
        self.swap_position(self.letters.index(x), self.letters.index(y))

    def reverse_positions(self, x, y):
        """Reverse letters between x and y in place"""
        # Buult in deque.reverse can only reverse the whole collection.
        while x <= y:
            self.swap_position(x, y)
            x += 1
            y -= 1

    def rotate(self, toRight, x, descramble):
        """Rotate letters to left or right by steps"""
        steps = x if toRight else -x
        if descramble:
            steps = -steps
        self.letters.rotate(steps)

    def rotate_based_position(self, letter, descramble):
        """Rotate based on index of letter"""
        if descramble:
            destination = self.letters.index(letter)
            if destination % 2 == 1:
                source = (destination - 1) // 2
            else:
                # This could probably be simplified. The idea is to
                # undo the rotate right by normalising the destination
                # to somewhere further along to the right.
                if destination == 0:
                    destination += len(self.letters)
                destination += len(self.letters)
                source = (destination - 2) // 2
            # Rotate call will invert the direction as descramble is set
            self.rotate(True, destination - source, descramble)
        else:
            steps = self.letters.index(letter)
            if steps >= 4:
                steps += 1
            steps += 1
            self.rotate(True, steps, descramble)

    def move_position(self, x, y, descramble):
        """Move letter from position x to y"""
        if descramble:
            x, y = y, x
        letter = self.letters[x]
        del self.letters[x]
        self.letters.insert(y, letter)


class Day21(Puzzle):
    """2016 Day 21: Scrambled Letters and Hash"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 21

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.to_scramble = 'abcdefgh'
        self.to_unscramble = 'fbgdceah'
        self.instructions = self.puzzle_data.input_as_lines()

    def calculate_a(self):
        """Part A: what is the result of scrambling abcdefgh?"""
        sc = Scrambler(self.to_scramble)
        return sc.scramble(self.instructions)

    def calculate_b(self):
        """Part B. What is the un-scrambled version of the scrambled
        password fbgdceah?"""
        sc = Scrambler(self.to_unscramble)
        return sc.descramble(self.instructions)
