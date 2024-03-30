#include <string>
#include <utility>

#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 2: Corruption Checksum

class Spreadsheet {
public:
    explicit Spreadsheet(const std::vector<std::string> &lines);
    auto checksum() -> int;
    auto evenly_divisible_sum() -> int;

private:
    std::vector<std::vector<int>> cells_;
};

class Day02 : public Puzzle {
public:
    explicit Day02(PuzzleData puzzleData)
        : Puzzle(2, std::move(puzzleData)), sheet_(input_lines()) {}

private:
    Spreadsheet sheet_;
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
