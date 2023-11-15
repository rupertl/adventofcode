"""Assembunny computer used by days 12, 23, 25"""

# pylint: disable=invalid-name,too-few-public-methods


from collections import namedtuple
from enum import Enum


class Operator(Enum):
    """Maps instruction names to a unique value."""
    nop = 0
    cpy = 1
    inc = 2
    dec = 3
    jnz = 4
    tgl = 5
    out = 6


Operand = namedtuple('Operand', 'isRegister value')
Instruction = namedtuple('Instruction', 'operator op1 op2')


# How many operands each operator takes
OPERATOR_OPERANDS = {
    Operator.nop: 0,
    Operator.cpy: 2,
    Operator.inc: 1,
    Operator.dec: 1,
    Operator.jnz: 2,
    Operator.tgl: 1,
    Operator.out: 1,
}


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
        self.output = []

    def assemble(self, code):
        """Assemble the given code into instructions to avoid having
        to parse it each time."""
        ins = []
        for line in code:
            parameters = line.split()
            operator = Operator[parameters[0]]
            op1 = self.assemble_operand(parameters[1])
            if OPERATOR_OPERANDS[operator] == 2:
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

    def run(self, outputBreak=None):
        """Run the computer until it hits max_pc then stop. If
        outputBreak is ser, return after that number of items has been
        outputted."""
        while self.pc < self.max_pc:
            ins = self.instructions[self.pc]
            self.pc += 1
            # match/case needs Python 3.10+
            match ins.operator:
                case Operator.nop:
                    pass
                case Operator.cpy:
                    value = self.get_operand_value(ins.op1)
                    if not ins.op2.isRegister:
                        # Copy to immediate is invalid, skip
                        continue
                    self.set_register_immediate(ins.op2.value, value)
                case Operator.inc:
                    self.add_register_immediate(ins.op1.value, 1)
                case Operator.dec:
                    self.add_register_immediate(ins.op1.value, -1)
                case Operator.jnz:
                    value = self.get_operand_value(ins.op1)
                    displacement = self.get_operand_value(ins.op2)
                    if value != 0:
                        # -1 as we already added 1 above
                        self.pc += displacement - 1
                case Operator.tgl:
                    # -1 as we already added 1 above
                    displacement = self.get_operand_value(ins.op1) - 1
                    self.toggle(self.pc + displacement)
                case Operator.out:
                    value = self.get_operand_value(ins.op1)
                    self.output.append(value)
                    if outputBreak and len(self.output) >= outputBreak:
                        return

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

    def toggle(self, address):
        """Modify the code at address according to Day 23 rules."""
        if address >= self.max_pc:
            return
        old_inst = self.instructions[address]
        new_operator = Operator.nop
        match OPERATOR_OPERANDS[old_inst.operator]:
            case 0:
                pass
            case 1:
                if old_inst.operator == Operator.inc:
                    new_operator = Operator.dec
                else:
                    new_operator = Operator.inc
            case 2:
                if old_inst.operator == Operator.jnz:
                    new_operator = Operator.cpy
                else:
                    new_operator = Operator.jnz
        self.instructions[address] = Instruction(new_operator,
                                                 old_inst.op1,
                                                 old_inst.op2)

    def register_index(self, register):
        """Return the index for a named register."""
        return self.register_names.index(register)

    def get_register(self, register):
        """Convenience function to get value of register."""
        return self.get_operand_value(Operand(True,
                                              self.register_index(register)))

    def set_register(self, register, value):
        """Convenience function to set a register."""
        register_index = self.register_index(register)
        return self.set_register_immediate(register_index, value)
