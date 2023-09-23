"""2016 Day 12: Leonardo's Monorail"""


# pylint: disable=invalid-name,too-few-public-methods


from collections import namedtuple
from enum import Enum
from puzzle import Puzzle


class Operator(Enum):
    """Maps instruction names to a unique value."""
    cpy = 1
    inc = 2
    dec = 3
    jnz = 4


Operand = namedtuple('Operand', 'isRegister value')
Instruction = namedtuple('Instruction', 'operator op1 op2')


class Assembunny():
    """Represents a computer accepting assembunny code."""
    def __init__(self, code):
        self.max_pc = len(code)
        self.register_names = ('a', 'b', 'c', 'd')
        self.instructions = self.assemble(code)
        self.reset()

    def reset(self):
        """Set computer to starting values."""
        self.pc = 0             # program counter
        self.registers = [0] * len(self.register_names)

    def assemble(self, code):
        """Assemble the given code into instructions to avoid having
        to parse it each time."""
        ins = []
        for line in code:
            parameters = line.split()
            operator = Operator[parameters[0]]
            op1 = self.assemble_operand(parameters[1])
            if operator in (Operator.cpy, Operator.jnz):
                op2 = self.assemble_operand(parameters[2])
            else:
                op2 = None
            ins.append(Instruction(operator, op1, op2))
        return ins

    def assemble_operand(self, operand):
        """Turn operand into a register reference or a value."""
        if operand in self.register_names:
            reg_index = ord(operand) - ord(self.register_names[0])
            return Operand(isRegister=True, value=reg_index)
        return Operand(isRegister=False, value=int(operand))

    def run(self):
        """Run the computer until it hits max_pc then stop."""
        while self.pc < self.max_pc:
            ins = self.instructions[self.pc]
            # match/case needs Python 3.10+
            match ins.operator:
                case Operator.cpy:
                    value = self.get_operand_value(ins.op1)
                    self.set_register_immediate(ins.op2.value, value)
                    self.pc += 1
                case Operator.inc:
                    self.add_register_immediate(ins.op1.value, 1)
                    self.pc += 1
                case Operator.dec:
                    self.add_register_immediate(ins.op1.value, -1)
                    self.pc += 1
                case Operator.jnz:
                    value = self.get_operand_value(ins.op1)
                    displacement = self.get_operand_value(ins.op2)
                    if value != 0:
                        self.pc += displacement
                    else:
                        self.pc += 1

    def get_operand_value(self, operand):
        """Return the value of operand, which can be a register or an
        immediate."""
        if operand.isRegister:
            return self.registers[operand.value]
        return operand.value

    def set_register_immediate(self, register_index, value):
        """Set register to valye."""
        self.registers[register_index] = value

    def add_register_immediate(self, register_index, value):
        """Add value to register."""
        self.registers[register_index] += value

    def get_register_a(self):
        """Convenience function to get value of register a."""
        return self.get_operand_value(Operand(True, 0))

    def set_register_c(self, value):
        """Convenience function to add value to register c."""
        return self.set_register_immediate(2, value)


class Day12(Puzzle):
    """2016 Day 12: Leonardo's Monorail"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 12

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.assembunny = Assembunny(self.puzzle_data.input_as_lines())

    def calculate_a(self):
        """Part A: What value is left in register a?"""
        self.assembunny.reset()
        self.assembunny.run()
        return self.assembunny.get_register_a()

    def calculate_b(self):
        """Part B. If you instead initialize register c to be 1, what
        value is now left in register a?"""
        self.assembunny.reset()
        self.assembunny.set_register_c(1)
        self.assembunny.run()
        return self.assembunny.get_register_a()
