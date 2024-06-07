#include <algorithm>
#include <cstddef>
#include <string>
#include <vector>

#include "day25.hpp"
#include "parser.hpp"

auto parse_state(const std::vector<std::string> &lines, std::size_t &index)
    -> State {
    return {find_word_after(lines[index++], "Write the value") == "1",
            find_word_after(lines[index++], "slot to the") == "left",
            static_cast<std::size_t>(
                find_word_after(lines[index++], "with state")[0] - 'A')};
}

Turing::Turing(const std::vector<std::string> &lines)
        : tape_(TAPE_SIZE, false),
          cursor_(TAPE_SIZE / 2) {
    // Note we do little validation of the input lines

    std::size_t index = 1U;  // Ignore first line, we always start in state A
    diagnostic_at_ = std::stoul(find_word_after(lines[index++],
                                                "diagnostic checksum after"));
    index++;                    // Ignore third line

    // Process each state, asuming in order from A.
    while (index < lines.size()) {
        index++;                // Ignore "In state "
        index++;                // Ignore "If the"
        zero_states_.push_back(parse_state(lines, index));
        index++;                // Ignore "If the"
        one_states_.push_back(parse_state(lines, index));
        index++;                // Ignore blank line
    }
}

auto Turing::diagnostic_checksum() -> int {
    tick(diagnostic_at_);
    return checksum();
}

void Turing::tick(std::size_t times) {
    for (auto i = 0U; i < times; ++i) {
        const auto &state = tape_[cursor_] ? one_states_[current_state_]
                                           : zero_states_[current_state_];
        tape_[cursor_] = state.write_value;
        state.move_left ? --cursor_ : ++cursor_;
        current_state_ = state.next_state;
    }
}

auto Turing::checksum() const -> int {
    return static_cast<int>(std::count(tape_.begin(), tape_.end(), true));
}

auto Day25::calculate_a() -> std::string {
    return std::to_string(machine_.diagnostic_checksum());
}

auto Day25::calculate_b() -> std::string {
    return "★";
}
