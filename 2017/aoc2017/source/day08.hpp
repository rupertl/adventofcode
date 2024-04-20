#pragma once

#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 8: I Heard You Like Registers

// Different types of max register values to return
struct MaxRegister {
    int end;                    // largest value after all instructions
    int running;                // largest value during processing
};

auto find_largest_register_value(const std::vector<std::string> &lines)
    -> MaxRegister;

class Day08 : public Puzzle {
public:
    constexpr static auto DAY = 8;
    explicit Day08(PuzzleData puzzleData)
        : Puzzle(DAY, std::move(puzzleData)) {}

private:
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
