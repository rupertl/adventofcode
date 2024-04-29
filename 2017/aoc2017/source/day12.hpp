#pragma once

#include <string>
#include <unordered_map>
#include <unordered_set>
#include <vector>

#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 12: Digital Plumber

// Describes a graph of programs (each with an int id) and their connections.
class ProgramGroups {
public:
    // Parse lines to connections
    explicit ProgramGroups(const std::vector<std::string> &lines);

    // Given a connected list of programs in lines, find how many are
    // in the group given by source.
    auto count_programs_in_group(int source) -> int;

    // See how many groups of programs there are that do not connect
    // to other ones.
    auto count_distinct_groups() -> int;

private:
    std::unordered_map<int, std::unordered_set<int>> connections_;
    auto find_programs_in_group(int source) ->     std::unordered_set<int>;
};


class Day12 : public Puzzle {
public:
    constexpr static auto DAY = 12;
    explicit Day12(PuzzleData puzzleData)
        : Puzzle(DAY, std::move(puzzleData)),
          program_groups_(input_lines()) {}

private:
    ProgramGroups program_groups_;
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
