#pragma once

#include <string>
#include <vector>

#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 23: Coprocessor Conflagration

class Day23 : public Puzzle {
public:
    constexpr static auto DAY = 23;
    explicit Day23(PuzzleData puzzleData)
        : Puzzle(DAY, std::move(puzzleData)) {}

private:
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
