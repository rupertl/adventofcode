#pragma once

#include <string>
#include <utility>

#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 4: High-Entropy Passphrases

auto passphrase_has_no_duplicate_words(const std::string &passphrase,
                                       bool allowAnagrams) -> bool;

class Day04 : public Puzzle {
public:
    explicit Day04(PuzzleData puzzleData) : Puzzle(4, std::move(puzzleData)) {}

private:
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
