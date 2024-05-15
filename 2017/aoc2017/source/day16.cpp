#include <algorithm>
#include <cassert>
#include <cstddef>
#include <numeric>
#include <string>
#include <utility>
#include <vector>

#include "day16.hpp"

ProgramDance::ProgramDance(std::size_t size)
    : programs_(size, ' ') {
    // Fill with 'a', 'b' etc up to size
    std::iota(programs_.begin(), programs_.end(), 'a');
}

void ProgramDance::dance(const std::vector<std::string> &instructions) {
    std::for_each(instructions.begin(), instructions.end(),
                  [&](const auto &instruction) { step(instruction); });
}

void ProgramDance::step(const std::string &instruction) {
    assert(instruction.size() > 1);
    auto param1 = instruction.substr(1);
    auto param2 = std::string();
    auto slash = instruction.find('/');
    if (slash != std::string::npos) {
        param2 = instruction.substr(slash + 1);
    };

    switch (instruction[0]) {
        case 's':
            spin(std::stoi(param1));
            break;
        case 'x':
            exchange(std::stoul(param1), std::stoul(param2));
            break;
        case 'p':
            partner(param1[0], param2[0]);
            break;
        default:
            assert(false);
            break;
    }
}

void ProgramDance::spin(int amount) {
    std::rotate(programs_.rbegin(),
                programs_.rbegin() + amount,
                programs_.rend());
}

void ProgramDance::exchange(std::size_t pos1, std::size_t pos2) {
    std::swap(programs_[pos1], programs_[pos2]);
}

void ProgramDance::partner(char program1, char program2) {
    exchange(programs_.find(program1), programs_.find(program2));
}

auto Day16::calculate_a() -> std::string {
    ProgramDance prd;
    prd.dance(instructions_);
    return prd.order();
}

auto Day16::calculate_b() -> std::string {
    // Run the dance COLLECT times then extraopolate the result
    constexpr auto COUNT = 1000000000;
    constexpr auto COLLECT = 100;

    ProgramDance prd;
    std::vector<std::string> seen;
    seen.reserve(COLLECT);

    for (int i = 0; i < COLLECT; ++i) {
        seen.push_back(prd.order());
        if (i > 0 && prd.order() == seen[0]) {
            return seen[static_cast<std::size_t>(COUNT % i)];
        }
        prd.dance(instructions_);
    }

    return "unknown";
}
