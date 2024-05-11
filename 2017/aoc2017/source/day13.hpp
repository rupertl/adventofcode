#pragma once

#include <string>
#include <unordered_map>
#include <unordered_set>
#include <vector>

#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 13: Packet Scanners

struct FirewallRule {
    int layer;
    int range;

    // With the given start time, was the packet caught?
    auto was_caught(int time) const -> bool;
};

class Firewall {
public:
    // Construct from a list of fules like "0: 3"
    explicit Firewall(const std::vector<std::string> &lines);

    // Return the severity of getting caught if we start at start_time
    auto severity(int start_time) const -> int;

    // Return the amount we need to delay to get out of the firewall
    // with severity = 0
    auto evade_delay() const -> int;

private:
    std::vector<FirewallRule> rules_;
};

class Day13 : public Puzzle {
public:
    constexpr static auto DAY = 13;
    explicit Day13(PuzzleData puzzleData)
        : Puzzle(DAY, std::move(puzzleData)),
          firewall_(Firewall(input_lines())) {}

private:
    Firewall firewall_;

    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
