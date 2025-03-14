#pragma once

#include <string>
#include <vector>

#include "duet.hpp"
#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 18: Duet

auto count_sent_duet_1(const std::vector<std::string> &source) -> int;

class Day18 : public Puzzle {
public:
    constexpr static auto DAY = 18;
    explicit Day18(PuzzleData puzzleData)
        : Puzzle(DAY, std::move(puzzleData)) {}

private:
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
