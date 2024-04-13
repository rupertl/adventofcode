#pragma once

#include <string>
#include <utility>
#include <unordered_map>
#include <unordered_set>
#include <vector>

#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 7: Recursive Circus

// Nodes in the tower
struct ProgramDisc {
    std::string name;
    int weight = 0;
    bool wasCorrected = false;
    std::string parent;
    std::unordered_set<std::string> children;
};

// Parse puzzle input to a map of names -> ProgramDiscs
auto parse_program_tower(const std::vector<std::string> &lines) ->
    std::unordered_map<std::string, ProgramDisc>;

// Find the ultimate parent of all nodes in the tower
auto find_root_disc(const std::unordered_map<std::string, ProgramDisc> &tower)
    -> std::string;

// Find the incorrect weight in the tower, fix it and return the
// updated value for the node that was wrong.
auto find_corrected_weight(
    std::unordered_map<std::string, ProgramDisc> &tower) -> int;


class Day07 : public Puzzle {
public:
    constexpr static auto DAY = 7;
    explicit Day07(PuzzleData puzzleData)
        : Puzzle(DAY, std::move(puzzleData)),
          tower_(parse_program_tower(input_lines())) {}

private:
    std::unordered_map<std::string, ProgramDisc> tower_;
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
