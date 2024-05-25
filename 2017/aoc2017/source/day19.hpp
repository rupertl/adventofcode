#pragma once

#include <cstdint>
#include <deque>
#include <string>
#include <vector>

#include "point.hpp"
#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 19: A Series of Tubes

// Returns data from Network::route for puzzle parts 1 and 2.
struct NetworkRoute {
    std::string path;
    int steps = 1;
};

class Network {
public:
    explicit Network(const std::vector<std::string> &diagram);
    auto start() const -> Point { return start_; }
    auto route() const -> NetworkRoute;

private:
    std::vector<std::string> diagram_;
    int max_row_ = 0;
    int max_col_ = 0;
    Point start_;

    auto valid(const Point &pnt) const -> bool;
    auto get(const Point &pnt) const -> char;
    auto corner(const Point &location, const Point &direction) const -> Point;
};

class Day19 : public Puzzle {
public:
    constexpr static auto DAY = 19;
    explicit Day19(PuzzleData puzzleData)
        : Puzzle(DAY, std::move(puzzleData)) {}

private:
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
