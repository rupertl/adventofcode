#include <cstddef>
#include <string>
#include <vector>

#include "day05.hpp"

// Find how many steps it takes to escape the jump table.
// Adjust each jump based on value of variableOffsets.
// If false (part A) just increment
// If true (part B) decrement if current value >= 3 else increment
auto find_escape_count(std::vector<int> jumps,  // by value as we modify
                       bool variableOffsets) -> int {
    auto jumpCount = 0;
    // location needs to be an signed value, as presumably we could escape
    // the jump table by going to a location < 0.
    for (auto location = 0;
         location >= 0 && location < static_cast<int>(jumps.size());
         jumpCount++) {
        auto &jump = jumps[static_cast<size_t>(location)];
        location += jump;
        jump += (variableOffsets && jump >= 3) ? -1 : 1;
    }
    return jumpCount;
}

auto Day05::calculate_a() -> std::string {
    return std::to_string(find_escape_count(jumps_, /*variableOffsets*/ false));
}

auto Day05::calculate_b() -> std::string {
    return std::to_string(find_escape_count(jumps_, /*variableOffsets*/ true));
}
