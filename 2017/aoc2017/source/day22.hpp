#pragma once

#include <map>
#include <string>
#include <unordered_map>
#include <vector>

#include "puzzle.hpp"
#include "puzzle_data.hpp"
#include "point.hpp"

// Day 22: Sporifica Virus

// Infection status of a cluster node.
enum NodeStatus {
    CLEAN,
    WEAKENED,
    INFECTED,
    FLAGGED,
};

// An infinite 2D grid of compute nodes where there is a virus carrier
// which has a location and direction, and each node can be in a
// different state.
// Using an unordered_map is about 3x quicker than a map here.
class Cluster {
public:
    explicit Cluster(const std::vector<std::string> &nodes,
                     bool evolved = false);
    void burst();
    auto count_newly_infected() const -> int { return newly_infected_; }
    auto infected() const -> std::unordered_map<Point, NodeStatus> {
        return infected_;
    }

private:
    bool evolved_ = false;
    std::unordered_map<Point, NodeStatus> infected_;
    Point location_{0, 0};
    Point direction_{NORTH};
    int newly_infected_ = 0;

    void turn_left();
    void turn_right();
    void reverse();
};

class Day22 : public Puzzle {
public:
    constexpr static auto DAY = 22;
    explicit Day22(PuzzleData puzzleData)
        : Puzzle(DAY, std::move(puzzleData)) {}

private:
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
