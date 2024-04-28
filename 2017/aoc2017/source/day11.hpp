#pragma once

#include <string>
#include <vector>

#include "parser.hpp"
#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 11: Hex Ed

// Represents a hexagonal grid where you can move N,S,NE,NW,SE,SW.
class HexGrid {
public:
    explicit HexGrid(const std::vector<std::string> &steps) {
        apply_steps(steps);
    }
    void apply_steps(const std::vector<std::string> &steps);
    void step(const std::string &dir);
    auto distance() const -> int;
    auto max_distance() const -> int { return max_distance_; }

private:
    int x = 0;
    int y = 0;
    int max_distance_ = 0;
};

class Day11 : public Puzzle {
public:
    constexpr static auto DAY = 11;
    explicit Day11(PuzzleData puzzleData)
        : Puzzle(DAY, std::move(puzzleData)),
          steps_(parse_words(input_string())) {}

private:
    std::vector<std::string> steps_;
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
