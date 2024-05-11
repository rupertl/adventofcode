#pragma once

#include <string>
#include <vector>

#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 14: Disk Defragmentation

class Disk {
public:
    // Set disk contents based on knothash of key
    explicit Disk(const std::string &key);
    // How many disk blocks are used?
    auto count_used() -> int;
    // How many contiguous regions are there on the disk?
    auto count_regions() -> int;

private:
    std::vector<bool> storage_;
    void add_bits(char digit);
    void remove_adjacent(int startRow, int startCol);
    auto is_used(int row, int col) -> bool;
    void clear(int row, int col);
};

class Day14 : public Puzzle {
public:
    constexpr static auto DAY = 14;
    explicit Day14(PuzzleData puzzleData)
        : Puzzle(DAY, std::move(puzzleData)),
          disk_(input_string()) {}
    
private:
    Disk disk_;
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
