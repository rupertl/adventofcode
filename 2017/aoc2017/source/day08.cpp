#include <algorithm>
#include <cassert>
#include <string>
#include <unordered_map>
#include <vector>

#include "day08.hpp"
#include "parser.hpp"

// Instructions tell us how to update registers
struct Instruction {
    std::string dest;           // what register to change
    int increment;              // how much to increase it by if condition met
    std::string source;         // source register to compare
    std::string operation;      // comparison operation
    int comparison;             // value to compare source to
};

// Comvert a line like "b inc 5 if a > 1" to an Instruction
auto parse_instruction(const std::string &line) -> Instruction {
    auto words = parse_row<std::string>(line);
    assert(words.size() == 7);
    Instruction inst{
        words[0],
        std::stoi(words[2]),
        words[4],
        words[5],               // NOLINT: magic-number
        std::stoi(words[6])     // NOLINT: magic-number
    };
    if (words[1] == "dec") {
        inst.increment = - inst.increment;
    }
    return inst;
    // More fancy idea: create a lambda representing the comparison
    // that takes the source register value and returns a bool result.
    // But as we are only running the instructions once this would not
    // be faster.
}

auto find_largest_register_value(const std::vector<std::string> &lines)
    -> MaxRegister {
    std::unordered_map<std::string, int> registers;
    MaxRegister maxes{0, 0};

    // Parse lines into instructions and execute them
    for (const auto & line: lines) {
        auto inst = parse_instruction(line);
        bool result = false;
        if (inst.operation == "<") {
            result = registers[inst.source] < inst.comparison;
        } else if (inst.operation == "<=") {
            result = registers[inst.source] <= inst.comparison;
        } else if (inst.operation == "==") {
            result = registers[inst.source] == inst.comparison;
        } else if (inst.operation == "!=") {
            result = registers[inst.source] != inst.comparison;
        } else if (inst.operation == ">") {
            result = registers[inst.source] > inst.comparison;
        } else if (inst.operation == ">=") {
            result = registers[inst.source] >= inst.comparison;
        } else {
            // Unhandled or missing operator
            assert(false);
        }

        if (result) {
            registers[inst.dest] += inst.increment;
            // See if this is the max value so far
            maxes.running = std::max(maxes.running, registers[inst.dest]);
        }
    }

    // Find the largest final register value
    auto end_max_register = 
        std::max_element(registers.begin(), registers.end(),
                         [](const auto &reg1, const auto &reg2) {
                             return reg1.second < reg2.second;
                         });
    if (end_max_register != registers.end()) {
        maxes.end = end_max_register->second;
    }
    return maxes;
}

auto Day08::calculate_a() -> std::string {
    return std::to_string(find_largest_register_value(input_lines()).end);
}

auto Day08::calculate_b() -> std::string {
    return std::to_string(find_largest_register_value(input_lines()).running);
}
