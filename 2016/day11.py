"""2016 Day 11: Radioisotope Thermoelectric Generators"""


# pylint: disable=invalid-name,too-few-public-methods


import itertools
import queue
import re
from puzzle import Puzzle


class Component():
    """Represents a microchip or a generator for a specific element."""
    def __init__(self, element, isGenerator):
        self.element = element
        self.isGenerator = isGenerator

    def __str__(self):
        # We assume the first two letters of the element are unique
        return self.element[0:2] + ('G' if self.isGenerator else 'M')

    def __repr__(self):
        return self.__str__()

    def __hash__(self):
        return hash(self.__str__())

    def __eq__(self, other):
        if isinstance(other, Component):
            return self.element == other.element and (self.isGenerator ==
                                                      other.isGenerator)
        return False

    def complements(self, other):
        """Returns true if this and the other are the same element but
        one is a RTG and one is a chip."""
        return (self.element == other.element) and (self.isGenerator !=
                                                    other.isGenerator)

    def safe(self, other):
        """Can this component be on the same floor as the other
        component?"""
        return (self.isGenerator
                == other.isGenerator) or self.complements(other)


# We assume the number of floors is the same for all puzzle inputs.
FACILITY_FLOORS = 4


class Facility():
    """Represents a RTG facility with floors containing Components,
    and an elevator to transfer Components."""
    def __init__(self):
        self.elevator_at = 0    # We use zero based indexing
        self.floors = [set() for _ in range(FACILITY_FLOORS)]
        self.turns = 0

    def next_floors(self):
        """Return a list of what floors we can move to."""
        choices = []
        if self.elevator_at != FACILITY_FLOORS - 1:
            choices.append(self.elevator_at + 1)
        if self.elevator_at != 0:
            choices.append(self.elevator_at - 1)
        return choices

    def add(self, floor, component):
        """Add a component to a floor."""
        self.floors[floor].add(component)

    def remove(self, floor, component):
        """Remove a component from a floor."""
        self.floors[floor].remove(component)

    def update(self, to_floor, components):
        """Make a copy of the facility, moving components from the
        current floor to the to_floor."""
        new = Facility()
        for index, floor in enumerate(self.floors):
            new.floors[index] = floor.copy()
        for component in components:
            new.remove(self.elevator_at, component)
            new.add(to_floor, component)
        new.elevator_at = to_floor
        new.turns += self.turns + 1
        return new

    def can_update(self, to_floor, components):
        """Returns True if it would be safe to move components to the
        to_floor."""
        # Check elevator safety if there are two items
        if len(components) == 2:
            it = iter(components)
            c1 = next(it)
            c2 = next(it)
            if c1.complements(c2):
                # We know this will be safe on whatever floor we land
                # on, so return now.
                return True
            if not c1.safe(c2):
                # Would be fried in the elevator
                return False
        # Now check each elevator component with ones on the new floor.
        for component in components:
            complements = False  # if there is a matching item
            may_fry = False      # if there is a non-matching item
            for existing in self.floors[to_floor]:
                if component.complements(existing):
                    complements = True
                elif not component.safe(existing):
                    may_fry = True
            if may_fry and not complements:
                return False
        return True

    def is_finished(self):
        """True if all items are safely on the top floor."""
        if self.elevator_at != FACILITY_FLOORS - 1:
            return False
        for floor in range(FACILITY_FLOORS - 1):
            if len(self.floors[floor]) != 0:
                return False
        return True

    def __str__(self):
        out = f'Turn {self.turns}\n'
        for floor in reversed(range(FACILITY_FLOORS)):
            el = 'E' if self.elevator_at == floor else ' '
            out += f'F{floor+1} {el}'
            if len(self.floors[floor]) > 0:
                out += f' {self.floors[floor]}'
            out += '\n'
        return out

    def digest(self):
        """Summarise the facility state so we can check for
        equivalence."""
        # The digest includes the floor the elevator is at, plus how
        # many of each type of component are on each floor.
        out = f'E{self.elevator_at}|'
        for floor in reversed(range(FACILITY_FLOORS)):
            numM = 0
            numG = 0
            for component in self.floors[floor]:
                if component.isGenerator:
                    numG += 1
                else:
                    numM += 1
            out += f'F{floor}:{numM},{numG}|'
        return out


class Solver:
    """Solves puzzle by doing a BFS over valid moves."""
    def __init__(self, lines):
        self.facility = Facility()
        self.parse(lines)

    def parse(self, lines):
        """Parse input to work out the starting state of the facility."""
        # We assume lines in ascending order
        for floor, line in enumerate(lines):
            rex = r"(\w+) generator|(\w+)-compatible microchip"
            matches = re.findall(rex, line)
            for match in matches:
                if match[1] == '':
                    self.facility.add(floor, Component(match[0], True))
                else:
                    self.facility.add(floor, Component(match[1], False))

    def solve(self):
        """Solve the puzzle by trying updates."""
        seen = set()
        q = queue.Queue()
        q.put(self.facility)
        while not q.empty():
            f = q.get()
            updates = self.generate_updates(f)
            for update in updates:
                if update.is_finished():
                    return update.turns
                digest = update.digest()
                if digest not in seen:
                    seen.add(digest)
                    q.put(update)
        return -1               # queue exhausted, no solution

    def generate_updates(self, facility):
        """Gemerate valid updates to the facility."""
        updates = []
        components = facility.floors[facility.elevator_at]
        c1 = list(itertools.combinations(components, 1))
        c2 = list(itertools.combinations(components, 2))
        for floor in facility.next_floors():
            for c in c2:
                if facility.can_update(floor, c):
                    updates.append(facility.update(floor, c))
            for c in c1:
                if facility.can_update(floor, c):
                    updates.append(facility.update(floor, c))
        return updates


class Day11(Puzzle):
    """2016 Day 11: Radioisotope Thermoelectric Generators"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 11

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.solver = Solver(self.puzzle_data.input_as_lines())

    def calculate_a(self):
        """Part A: What is the minimum number of steps required to
        bring all of the objects to the fourth floor?"""
        return self.solver.solve()

    def calculate_b(self):
        """Part B. What is the minimum number of steps required, given
        the extra components?"""
        self.solver.facility.add(0, Component("elerium", True))
        self.solver.facility.add(0, Component("elerium", False))
        self.solver.facility.add(0, Component("dilithium", True))
        self.solver.facility.add(0, Component("dilithium", False))
        return self.solver.solve()
