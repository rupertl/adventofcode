"""2023 Day 20: Pulse Propagation"""


# pylint: disable=invalid-name,too-few-public-methods

import math
from collections import defaultdict
from puzzle import Puzzle


class Gate():
    """Base class for modules in the propagator."""
    def __init__(self, name, downstreams):
        self.name = name
        self.downstreams = downstreams
        self.inp = []
        self.out = []
        self.pulseCount = {0: 0, 1: 0}
        self.upstreams = set()

    def set_upstreams(self, upstreams):
        """Set up what feeds into this gate."""
        self.upstreams = upstreams

    def receive(self, fromGate, inp):
        """Receive inputs."""
        self.inp.append((fromGate, inp))
        self.pulseCount[inp] += 1

    def tick(self):
        """Act on inputs."""
        self.inp.clear()

    def send(self, gates):
        """Send outputs and return how many pulses we sent."""
        sent = 0
        for pulse in self.out:
            for downstream in self.downstreams:
                gates[downstream].receive(self.name, pulse)
                sent += 1
        self.out.clear()
        return sent


class FlipFlopGate(Gate):
    """A gate that toggles between on or off."""
    def __init__(self, name, downstreams):
        super().__init__(name, downstreams)
        self.state = False          # off

    def tick(self):
        """Act on inputs."""
        for _, pulse in self.inp:
            if pulse == 0:
                self.state = not self.state
                self.out.append(1 if self.state else 0)
        super().tick()


class ConjunctionGate(Gate):
    """A gate that remembers its inputs."""
    def __init__(self, name, downstreams):
        super().__init__(name, downstreams)
        self.state = None

    def set_upstreams(self, upstreams):
        """Set up what feeds into this gate."""
        self.state = {upstream: 0 for upstream in upstreams}
        super().set_upstreams(upstreams)

    def tick(self):
        """Act on inputs."""
        for fromGate, pulse in self.inp:
            self.state[fromGate] = pulse
            value = (0 if all((inp == 1 for inp in self.state.values()))
                     else 1)
            self.out.append(value)
        super().tick()


class TerminalGate(Gate):
    """A gate that just sends input to output."""
    def __init__(self, name, downstreams):
        super().__init__(name, downstreams)
        self.received = []      # Log all outputs received

    def tick(self):
        """Act on inputs."""
        for _, pulse in self.inp:
            self.received.append(pulse)
            self.out.append(pulse)
        super().tick()


class Propagator():
    """Represents a machine with flip-flip and conjunction modules
    that it sends pulses to."""
    def __init__(self, lines):
        self.gates = self.parse(lines)

    def parse(self, lines):
        """Parse lines of gate definitions."""
        gates = {}
        allDownstreams = set()
        upstreams = defaultdict(set)
        for line in lines:
            name, downstreams = line.split(' -> ')
            downstreams = frozenset(downstreams.split(', '))
            if name[0] == '&':
                name = name[1:]
                gate = ConjunctionGate(name, downstreams)
            elif name[0] == '%':
                name = name[1:]
                gate = FlipFlopGate(name, downstreams)
            else:
                gate = TerminalGate(name, downstreams)
            gates[name] = gate
            for downstream in downstreams:
                upstreams[downstream].add(name)
            allDownstreams.update(downstreams)
        for gateName in allDownstreams.difference(gates.keys()):
            # Add any gates that receive output but aren't further
            # connected
            gates[gateName] = TerminalGate(gateName, [])
        for gateName, gatesUpstreams in upstreams.items():
            # Hook up each gate with upstreams we discovered
            gates[gateName].set_upstreams(gatesUpstreams)
        return gates

    def press_button_once(self):
        """Run the system by sending a low pulse to the broadcaster.
        Return the names of all gates that went high at some point."""
        highGates = set()
        self.gates['broadcaster'].receive('button', 0)
        totalSent = 1
        while totalSent > 0:
            for gate in self.gates.values():
                gate.tick()
                if 1 in gate.out:
                    highGates.add(gate.name)
            totalSent = 0
            for gate in self.gates.values():
                totalSent += gate.send(self.gates)
        return highGates

    def press_button(self, N=1):
        """Press the button one or more times."""
        for _ in range(N):
            self.press_button_once()

    def get_pulse_count(self):
        """Get the number of high and low pulses sent in the system."""
        total = {0: 0, 1: 0}
        for gate in self.gates.values():
            total[0] += gate.pulseCount[0]
            total[1] += gate.pulseCount[1]
        return total

    def get_pulse_product(self):
        """Get count of low pulses * count of high pulese."""
        return math.prod(self.get_pulse_count().values())

    def watch_high(self, watchGates):
        """Press the button continually and see if the output for any
        of the watch gates goes high. When all have gone high at some
        point, return the first cycle each went high."""
        highAt = {watchGate: None for watchGate in watchGates}
        n = 1
        while None in highAt.values():
            cycleHighs = self.press_button_once()
            for gate in cycleHighs:
                if gate in highAt and highAt[gate] is None:
                    highAt[gate] = n
            n += 1
        return highAt


class Day20(Puzzle):
    """2023 Day 20: Pulse Propagation"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 20

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        lines = self.puzzle_data.input_as_lines()
        self.propagatorA = Propagator(lines)
        self.propagatorB = Propagator(lines)

    def calculate_a(self):
        """Part A: What do you get if you multiply the total number of
        low pulses sent by the total number of high pulses sent?"""
        self.propagatorA.press_button(1000)
        return self.propagatorA.get_pulse_product()

    def calculate_b(self):
        """Part B. What is the fewest number of button presses
        required to deliver a single low pulse to the module named rx?"""
        # Goal is to find when all the gates feeding into the
        # comparator in front of rx, go high. Then the result will be
        # the lcm of these.
        # This assumes all puzzle inputs have the same form but
        # different gate names, of course.
        rxUpstreams = self.propagatorB.gates['rx'].upstreams
        comp = list(rxUpstreams)[0]
        watches = self.propagatorB.gates[comp].upstreams
        results = self.propagatorB.watch_high(watches)
        return math.lcm(*results.values())
