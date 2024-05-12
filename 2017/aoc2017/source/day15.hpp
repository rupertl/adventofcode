#pragma once

#include <cstdint>
#include <string>
#include <vector>

#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 15: Dueling Generators

class Generator {
public:
    uint64_t value = 0;
    uint64_t factor = 0;
    uint64_t criteria = 0;

    /// Find next value in the sequence from the generator
    auto next() -> uint64_t;

    static constexpr auto FACTOR_A = 16807;
    static constexpr auto FACTOR_B = 48271;
    static constexpr auto CRITERIA_A = 4;
    static constexpr auto CRITERIA_B = 8;
};

constexpr auto JUDGE_LOOPS_1 = 40000000;
constexpr auto JUDGE_LOOPS_2 = 5000000;

// Run generator loop times and find number of times the bottom 16 bits match
auto judge_generators(Generator &genA, Generator &genB, int loops) -> int;

class Day15 : public Puzzle {
public:
    constexpr static auto DAY = 15;
    explicit Day15(PuzzleData puzzleData)
        : Puzzle(DAY, std::move(puzzleData)) {}

private:
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
