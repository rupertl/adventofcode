"""2016  Day 10: Balance Bots"""


# pylint: disable=invalid-name,too-few-public-methods


import re
from collections import namedtuple
from puzzle import Puzzle


MAX_ROBOT_ID = 500
MAX_OUTPUT_BIN = 500


class Destination():
    """Models a type of destination (robot or not) and a number."""
    def __init__(self, destType, number):
        self.isRobot = destType == 'bot'
        self.number = number


Rule = namedtuple('Rule', 'low high')


class Robot():
    """Represents a robot that can hold two chips and has a rule to
    determine what to do with them."""
    # Note that we trigger activity as soon as we get a rule and
    # enough chips. Another way to do this would be to wait for all
    # chips and rules to be loaded before starting, but this would
    # make assumptions about the input data.
    def __init__(self, robot_id, controller):
        self.robot_id = robot_id
        self.controller = controller
        self.values = []
        self.rule = None

    def receive_chip(self, value):
        """Add a chip with a value to the set it owns."""
        self.values.append(value)
        # Should not end up holding more than 2 values
        assert len(self.values) <= 2
        self.trigger()

    def receive_rule(self, rule):
        """Add a rule to the robot."""
        # We assume that rules cannot be replaced and a robot can only
        # have one rule.
        assert not self.rule
        self.rule = rule
        self.trigger()

    def trigger(self):
        """See if we can perform any actions."""
        if not (self.rule and len(self.values) == 2):
            return
        # It's possible the robot will get two chips of the same
        # value: in this case we just give one to each downstream
        # robot.
        lower = min(self.values)
        higher = max(self.values)
        self.controller.give_chip(self.rule.low, lower)
        self.controller.give_chip(self.rule.high, higher)
        self.controller.add_comparer(self.robot_id, frozenset(self.values))
        self.values = []


class Controller():
    """Represents a set of robots and output bins and executes rules."""
    def __init__(self, instructions):
        self.instructions = instructions
        self.robots = [Robot(index, self) for index in range(MAX_ROBOT_ID)]
        self.output = [[] for _ in range(MAX_OUTPUT_BIN)]
        self.audit = {}

    def run(self):
        """Run the instructions through the controller."""
        for instruction in self.instructions:
            self.run_one(instruction)
        # Ensure if we call run() again we don't reprocess the
        # instructions
        self.instructions = []

    def run_one(self, instruction):
        """Parse and run a single instruction."""
        mat = re.match(r'value (\d+) goes to bot (\d+)', instruction)
        if mat:
            self.give_chip_to_robot(int(mat.group(2)), int(mat.group(1)))
            return
        mat = re.match(r'bot (\d+) gives low to (bot|output) (\d+) ' +
                       r'and high to (bot|output) (\d+)', instruction)
        if mat:
            low_dest = Destination(mat.group(2), int(mat.group(3)))
            high_dest = Destination(mat.group(4), int(mat.group(5)))
            rule = Rule(low=low_dest, high=high_dest)
            self.give_rule_to_robot(int(mat.group(1)), rule)
            return
        raise RuntimeError(f'Bad instruction {instruction}')

    def give_chip(self, destination, value):
        """Give a chip to an output bin or another robot."""
        if destination.isRobot:
            self.give_chip_to_robot(destination.number, value)
        else:
            self.store_chip_in_output(destination.number, value)

    def give_chip_to_robot(self, robot_id, value):
        """Give a chip to a robot."""
        self.robots[robot_id].receive_chip(value)

    def store_chip_in_output(self, bin_id, value):
        """Store a chip in an output bin."""
        self.output[bin_id].append(value)

    def give_rule_to_robot(self, robot_id, rule):
        """Set up a new role on a given robot."""
        self.robots[robot_id].receive_rule(rule)

    def add_comparer(self, robot_id, values):
        """Add an audit trail entry of who compared values."""
        self.audit[values] = robot_id

    def get_who_compared(self, values):
        """Find out from the audt trail who compared values."""
        return self.audit[values]


class Day10(Puzzle):
    """2016 Day 10: Balance Bots"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 10

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.controller = Controller(self.puzzle_data.input_as_lines())

    def calculate_a(self):
        """Part A: what is the number of the bot that is responsible
        for comparing value-61 microchips with value-17 microchips?"""
        # Note this is only compatible with the full data set, not the
        # sample one.
        self.controller.run()
        return self.controller.get_who_compared(frozenset([17, 61]))

    def calculate_b(self):
        """Part B. What do you get if you multiply together the values
        of one chip in each of outputs 0, 1, and 2?"""
        self.controller.run()
        result = 1
        for index in range(3):
            result *= self.controller.output[index][0]
        return result
