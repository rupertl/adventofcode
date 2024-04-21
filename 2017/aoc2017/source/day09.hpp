#pragma once

#include <string>

#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 9: Stream Processing

struct GarbageCollector {
    int removed{0};             // Number of non-cancelled garbage removed
    std::string cleaned{};      // String cleaned of garbage
};

// Return a GarbageCollector object representing the clean string and
// the number of garbage items removed after cleaning input.
auto collect_garbage(const std::string &input) -> GarbageCollector;

// Return a copy of input where all garbage has been removed
auto remove_garbage(const std::string &input) -> std::string;

// Find the total score for all groups in the input
auto group_score(const std::string &input) -> int;

class Day09 : public Puzzle {
public:
    constexpr static auto DAY = 9;
    explicit Day09(PuzzleData puzzleData)
        : Puzzle(DAY, std::move(puzzleData)) {}

private:
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
