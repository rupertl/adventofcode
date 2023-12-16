"""2023 Day 13: Point of Incidence"""


# pylint: disable=invalid-name,too-few-public-methods

import itertools
from collections import namedtuple
from puzzle import Puzzle


Reflection = namedtuple('Reflection', 'start size')


class Pattern():
    """Represents a pattern of rocks in a valley that may have a
    mirror inside."""
    def __init__(self, lines):
        self.parse(lines)
        self.rowReflection = self.find_reflection(self.rows)
        self.colReflection = self.find_reflection(self.cols)

    def parse(self, lines):
        """Convert lines of # and . into row/column structures."""
        self.rows = []
        self.cols = [[] for _ in range(len(lines[0]))]
        for line in lines:
            self.rows.append(list(line))
            for col, ch in enumerate(line):
                self.cols[col].append(ch)

    def find_reflection(self, lines):
        """Find a reflection (if any) in the lines and return a
        Reflection object."""
        reflections = self.find_all_reflections(lines)
        if len(reflections) == 0:
            return None
        return reflections[0]

    def find_all_reflections(self, lines):
        """Find all reflectiosn (if any) in the lines and return a
        list of Reflection objects."""
        reflections = []
        for index, _ in enumerate(lines):
            left, right = index - 1, index
            if self.same(lines, left, right):
                while self.same(lines, left - 1, right + 1):
                    left -= 1
                    right += 1
                # Reflection must reach the edge of the pattern
                if left == 0 or right == len(lines) - 1:
                    reflections.append(Reflection(left + 1,  # 1 based index
                                                  1 + right - left))
        return reflections

    def same(self, lines, left, right):
        """Is the line pointed to by left and right on the pattern,
        and is it the same?"""
        return (left >= 0 and right < len(lines) and
                lines[left] == lines[right])

    def reflection(self):
        """Return either the row or col reflection."""
        return (self.rowReflection if self.rowReflection
                else self.colReflection)

    def summary(self):
        """Return the summary score for this pattern."""
        return self.linesBeforeReflection(self.colReflection) + (
            100 * self.linesBeforeReflection(self.rowReflection))

    def linesBeforeReflection(self, reflection):
        """Calculate how many lines there are before the line of
        reflection."""
        return (reflection.start - 1 + (reflection.size // 2)
                if reflection else 0)

    def smudge(self):
        """Try smudging each item until we get a new reflection."""
        for rowIndex, row in enumerate(self.rows):
            for colIndex, _ in enumerate(row):
                if self.test_smudgee(rowIndex, colIndex):
                    return
        raise RuntimeError("Could not find a valid smudge")

    def test_smudgee(self, rowIndex, colIndex):
        """Flip the value at index and if it reveals a new reflection,
        keep it and return True."""
        # This could probably be simplified.
        # Find the number of old row and col reflections
        ors = self.find_all_reflections(self.rows)
        ocs = self.find_all_reflections(self.cols)
        # Swap the value
        old = self.rows[rowIndex][colIndex]
        new = '#' if old == '.' else '.'
        self.rows[rowIndex][colIndex] = new
        self.cols[colIndex][rowIndex] = new
        # Find the number of new row and col reflections
        nrs = self.find_all_reflections(self.rows)
        ncs = self.find_all_reflections(self.cols)
        # See if we found a reflection, possibly a new one
        if len(nrs) + len(ncs) > 0:
            ok = True
            # Valid trnaistions of number of [row, cols] reflections
            # [0, 1] -> [1, 0],
            #           [1, 1] (but remove the old col)
            #           [0, 1] but diff value
            #           [0, 2] (but remove the old col)
            if len(ors) == 0 and len(ocs) == 1:
                if len(nrs) == 1 and len(ncs) == 0:
                    pass
                elif len(nrs) == 1 and len(ncs) == 1:
                    ncs = []
                elif len(nrs) == 0 and len(ncs) == 1:
                    ok = ncs != ocs
                elif len(nrs) == 0 and len(ncs) == 2:
                    ncs.remove(ocs[0])
            # [1, 0] -> [0, 1],
            #           [1, 1] (but remove the old row)
            #           [1, 0] but diff value
            #           [2, 0] (but remove the old row)
            elif len(ors) == 1 and len(ocs) == 0:
                if len(nrs) == 0 and len(ncs) == 1:
                    pass
                elif len(nrs) == 1 and len(ncs) == 1:
                    nrs = []
                elif len(nrs) == 1 and len(ncs) == 0:
                    ok = nrs != ors
                elif len(nrs) == 2 and len(ncs) == 0:
                    nrs.remove(ors[0])
            if ok:
                self.rowReflection = nrs[0] if len(nrs) > 0 else None
                self.colReflection = ncs[0] if len(ncs) > 0 else None
                return True
        # We did not find one, restore old value and continue
        # looking
        self.rows[rowIndex][colIndex] = old
        self.cols[colIndex][rowIndex] = old
        return False


class Valley():
    """Represents an area containing several Patterns."""
    def __init__(self, lines):
        self.patterns = self.parse(lines)

    def parse(self, lines):
        """Break down input into several patterns."""
        groups = [list(v) for k, v in
                  itertools.groupby(lines, lambda x: x != "")
                  if k]
        return [Pattern(group) for group in groups]

    def smudge(self):
        """Fix the smudges in all patterns."""
        for p in self.patterns:
            p.smudge()

    def summary(self):
        """Return the sum of all summaries of patterns."""
        return sum((p.summary() for p in self.patterns))


class Day13(Puzzle):
    """2023 Day 13: Point of Incidence"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 13

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.lines = self.puzzle_data.input_as_lines()
        self.valley = Valley(self.lines)

    def calculate_a(self):
        """Part A: What number do you get after summarizing all of
        your notes?"""
        return self.valley.summary()

    def calculate_b(self):
        """Part B. What number do you get after summarizing the new
        reflection line in each pattern in your notes?"""
        self.valley.smudge()
        return self.valley.summary()
