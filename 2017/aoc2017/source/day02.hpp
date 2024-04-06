#pragma once

#include <string>
#include <utility>
#include <vector>

#include "parser.hpp"
#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 2: Corruption Checksum

auto spreadsheet_checksum(const std::vector<std::vector<int>> &cells) -> int;
auto evenly_divisible_sum(const std::vector<std::vector<int>> &cells) -> int;

class Day02 : public Puzzle {
public:
    explicit Day02(PuzzleData puzzleData)
        : Puzzle(2, std::move(puzzleData)),
          cells_(parse_matrix<int>(input_lines())) {}

private:
    std::vector<std::vector<int>> cells_;
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
