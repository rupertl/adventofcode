#pragma once

#include <string>
#include <utility>
#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 1: Inverse Captcha

auto solve_captcha_sequential(const std::string &input) -> int;
auto solve_captcha_halfway(const std::string &input) -> int;

class Day01 : public Puzzle {
public:
    explicit Day01(PuzzleData puzzleData)
        : Puzzle(1, std::move(puzzleData)) {}

private:
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
