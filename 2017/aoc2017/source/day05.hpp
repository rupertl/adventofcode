#pragma once

#include <string>
#include <utility>
#include <vector>

#include "parser.hpp"
#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 5: A Maze of Twisty Trampolines, All Alike

auto find_escape_count(std::vector<int> jumps, bool variableOffsets) -> int;

class Day05 : public Puzzle {
public:
    constexpr static auto DAY = 5;
    explicit Day05(PuzzleData puzzleData) :
        Puzzle(DAY, std::move(puzzleData)),
        jumps_(parse_col<int>(input_lines())) {}

private:
    std::vector<int> jumps_;
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
