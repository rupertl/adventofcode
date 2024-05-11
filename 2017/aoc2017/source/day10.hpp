#pragma once

#include <string>
#include <vector>

#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 10: Knot Hash

class Day10 : public Puzzle {
public:
    constexpr static auto DAY = 10;
    explicit Day10(PuzzleData puzzleData)
        : Puzzle(DAY, std::move(puzzleData)) {}

private:
    std::vector<unsigned int> lengths_;
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
