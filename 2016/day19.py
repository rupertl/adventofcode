"""2016 Day 19: An Elephant Named Joseph"""


# pylint: disable=invalid-name,too-few-public-methods

from puzzle import Puzzle


class Node():
    """A node class for the below linked list."""
    def __init__(self, value):
        self.value = value
        self.next = None
        self.prev = None


class CircularList():
    """A circular linked list which starts with a predefined list of
    numbers and supports only advance and delete operatiops."""
    def __init__(self, size):
        self.size = size
        self.head = self.make_number_ring()

    def make_number_ring(self):
        """Make a ring of numbers up to size."""
        prev_node = None
        for x in range(self.size):
            new_node = Node(x)
            new_node.prev = prev_node
            if x == 0:
                head = new_node
            else:
                prev_node.next = new_node
            prev_node = new_node
        prev_node.next = head
        head.prev = prev_node
        return head

    def advance(self, node, distance):
        """Move forwards distance items in the list."""
        for _ in range(distance):
            node = node.next
        return node

    def delete(self, node):
        """Delete a node from the list."""
        prev_node = node.prev
        next_node = node.next
        prev_node.next = next_node
        next_node.prev = prev_node
        node.next = None
        node.prev = None
        if node == self.head:
            self.head = next_node
        self.size -= 1


class ElfCircle():
    """Represents a ring of elves holding presents. Run a form of the
    Josephius problem to find who gets all the presents."""
    def __init__(self, num_elves):
        self.elves = CircularList(num_elves)

    def runNext(self):
        """Run the simulation until one elf has all the presents, and
        return their numbewr. Take the next elf in line to determine
        who to remove."""
        elf = self.elves.head
        while self.elves.size != 1:
            loser = self.elves.advance(elf, 1)
            self.elves.delete(loser)
            elf = self.elves.advance(elf, 1)
        return self.elves.head.value + 1

    def runAcross(self):
        """Run the simulation until one elf has all the presents, and
        return their numbewr. Use the elf across the circle to
        determine who to remove."""
        elf = self.elves.head
        # We find the first across elf and then keep track of it locally
        across = self.elves.advance(elf, self.elves.size // 2)
        while self.elves.size != 1:
            oddSize = self.elves.size % 2 == 1
            next_across = self.elves.advance(across, 2 if oddSize else 1)
            self.elves.delete(across)
            elf = self.elves.advance(elf, 1)
            across = next_across
        return self.elves.head.value + 1


class Day19(Puzzle):
    """2016 Day 19: An Elephant Named Joseph"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 19

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.num_elves = int(self.puzzle_data.input_as_string())

    def calculate_a(self):
        """Part A: which Elf gets all the presents?"""
        return ElfCircle(self.num_elves).runNext()

    def calculate_b(self):
        """Part B. which Elf gets all the presents, selecting across the
        circle?"""
        return ElfCircle(self.num_elves).runAcross()
