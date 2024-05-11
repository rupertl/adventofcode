#include <algorithm>
#include <cassert>
#include <string>
#include <vector>

#include "day13.hpp"
#include "parser.hpp"

auto FirewallRule::was_caught(int time) const -> bool {
    return (time + layer) % ((range - 1) * 2) == 0;
}

Firewall::Firewall(const std::vector<std::string> &lines) {
    for (const auto &line : lines) {
        // Map lines like "0: 3"
        auto words = parse_words(line);
        assert(words.size() == 2);
        rules_.push_back(
            FirewallRule{std::stoi(words[0]), std::stoi(words[1])});
    }
    // Sort so ordered by smallest to largest range
    // This will help Firewall::evade_delay consider layers that are
    // likely to be caught at first.
    std::sort(rules_.begin(), rules_.end(),
              [](const auto &ruleA, const auto &ruleB) {
                  return ruleA.range < ruleB.range;
              });
}

auto Firewall::severity(int start_time) const -> int {
    auto severity = 0;

    for (const auto &rule : rules_) {
        if (rule.was_caught(start_time)) {
            severity += (start_time + rule.layer) * rule.range;
        }
    }

    return severity;
}

// There is probably a better closed form solution here, but this runs
// in around 20ms for my puzzle input.
auto Firewall::evade_delay() const -> int {
    // Set a limit of how many delays to try.
    constexpr auto LIMIT = 10000000;
    // Try each possible delay, giving up on each one when we find the
    // first collision
    for (int delay = 0; delay < LIMIT; ++delay) {
        auto caught = false;
        for (const auto &rule : rules_) {
            if (rule.was_caught(delay)) {
                caught = true;
                break;
            }
        }
        if (! caught) {
            return delay;
        }
    }
    return -1;                  // give up
}

auto Day13::calculate_a() -> std::string {
    return std::to_string(firewall_.severity(0));
}

auto Day13::calculate_b() -> std::string {
    return std::to_string(firewall_.evade_delay());
}
