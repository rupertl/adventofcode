"""2023 Day 19: Aplenty"""


# pylint: disable=invalid-name,too-few-public-methods

import math
import re
from puzzle import Puzzle


class Workflow():
    """A workflow has a name and several rules to apply to a part to
    decide where it should be routed."""
    def __init__(self, name, lines):
        self.name = name
        self.rules = self.parse(lines)

    def parse(self, lines):
        """Parse a text sequence into a list of rules."""
        # like "x>10:one,m<20:two,a>30:R,A"
        rules = []
        for line in lines.split(','):
            mat = re.match(r'(\w)([<>])(\d+):(\w+)', line)
            if mat:
                rule = dict(zip(['category', 'operator',
                                 'value', 'destination'],
                                mat.groups()))
                rule['value'] = int(rule['value'])
            else:
                # Direct route to a destination
                rule = {'destination': line}
            rules.append(rule)
        return rules

    def unconditional(self, rule):
        """Return True if this rule applies to all inputs."""
        return 'operator' not in rule

    def evaluate(self, part):
        """Evaluate this part against the workflow rules and return a
        destination."""
        for rule in self.rules:
            if self.unconditional(rule):
                return rule['destination']
            partVal = part.ratings[rule['category']]
            ruleVal = rule['value']
            decision = (partVal < ruleVal if rule['operator'] == '<'
                        else partVal > ruleVal)
            if decision:
                return rule['destination']
        raise RuntimeError('rules exhausted')

    def probe(self, values):
        """Run the category-to-range values through the rules and
        return the list of wofkflows and subranges."""
        results = []
        for rule in self.rules:
            if self.unconditional(rule):
                # Send all ranges to the destination and process no
                # more rules.
                results.append((rule['destination'], values.copy()))
                break
            partMin, partMax = values[rule['category']]
            ruleVal = rule['value']
            if rule['operator'] == '<':
                yesRange = (partMin, min(partMax, ruleVal - 1))
                noRange = (ruleVal, partMax)
            else:
                yesRange = (max(partMin, ruleVal + 1), partMax)
                noRange = (partMin, ruleVal)
            # Add the yes range to the results
            yesValues = values.copy()
            yesValues[rule['category']] = yesRange
            results.append((rule['destination'], yesValues))
            # Keep processing the no range with the next rule
            noValues = values.copy()
            noValues[rule['category']] = noRange
            values = noValues
        return results


class Part():
    """Defines a part with several categories with numerical ratings."""
    def __init__(self, lines):
        self.ratings = self.parse(lines)

    def parse(self, lines):
        """Break down a text list of category=value."""
        ratings = {}
        for line in lines.split(','):
            category = line[0]
            value = int(line[2:])
            ratings[category] = value
        return ratings


class Ramp():
    """Represents a relentless avalanche of machine parts and rules to
    decide what to do with them."""
    def __init__(self, lines):
        self.workflows = {}
        self.parts = []
        self.parse(lines)
        self.terminals = ('A', 'R')  # Accept and reject
        self.destinations = {k: [] for k in self.terminals}

    def parse(self, lines):
        """Convert puzzle instructions into workflows and parts."""
        for line in lines:
            mat = re.match(r'(\w+){([^}]+)}', line)
            if mat:
                name, rules = mat.groups()
                self.workflows[name] = Workflow(name, rules)
            mat = re.match(r'{(.*)}', line)
            if mat:
                self.parts.append(Part(mat.groups()[0]))

    def evaluate(self):
        """Evaluate the rules to send parts to destinations."""
        for part in self.parts:
            workflowName = 'in'
            while workflowName not in self.terminals:
                workflowName = self.workflows[workflowName].evaluate(part)
            self.destinations[workflowName].append(part)

    def sum_accepted(self):
        """Return the sum of all ratings for accepted items."""
        return sum((sum(part.ratings.values())
                    for part in self.destinations['A']))

    def probe(self):
        """Find how many combinations of part catgeory values the
        system will accept."""
        # value ranges: dict mapping category to (min, max) tuple
        initials = {c: (1, 4000) for c in ['x', 'm', 'a', 's']}
        # queue of (workflow name, value-ranges)
        queue = [('in', initials)]
        # list of value-ranges that got accepted
        # Do we need to merge these? It seems not
        accepted = []
        while queue:
            workflowName, values = queue.pop()
            paths = self.workflows[workflowName].probe(values)
            for path in paths:
                nextWorkflowName, nextValues = path
                if nextWorkflowName == 'A':
                    accepted.append(nextValues)
                elif nextWorkflowName != 'R':
                    queue.append(path)
        return self.sum_combinations(accepted)

    def sum_combinations(self, paths):
        """Sum up all combinations found in each value-range."""
        return sum((math.prod((r[1] - r[0] + 1 for r in path.values()))
                    for path in paths))


class Day19(Puzzle):
    """2023 Day 19: Aplenty"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 19

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        lines = self.puzzle_data.input_as_lines()
        self.ramp = Ramp(lines)
        self.ramp.evaluate()

    def calculate_a(self):
        """Part A: Sort through all of the parts you've been given;
        what do you get if you add together all of the rating numbers
        for all of the parts that ultimately get accepted?"""
        return self.ramp.sum_accepted()

    def calculate_b(self):
        """Part B. How many distinct combinations of ratings will be
        accepted by the Elves' workflows?"""
        return self.ramp.probe()
