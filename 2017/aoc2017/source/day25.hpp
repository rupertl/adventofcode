#pragma once

#include <cstdint>
#include <string>
#include <utility>
#include <vector>

#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 25: The Halting Problem

// The tape is infinite, but we model as a vector so pick a size.
constexpr auto TAPE_SIZE = 500'000;

// State to decide what the machine should do next.
struct State {
    bool write_value;
    bool move_left;             // false = move right
    std::size_t next_state;
};

class Turing {
public:
    explicit Turing(const std::vector<std::string> &lines);
    auto diagnostic_checksum() -> int;

    void tick(std::size_t times = 1);
    auto checksum() const -> int;

private:
    std::vector<bool> tape_;
    std::size_t cursor_;
    std::size_t current_state_ = 0U;
    std::size_t diagnostic_at_;
    std::vector<State> zero_states_;
    std::vector<State> one_states_;
};

class Day25 : public Puzzle {
public:
    constexpr static auto DAY = 25;
    explicit Day25(PuzzleData puzzleData)
        : Puzzle(DAY, std::move(puzzleData)),
          machine_(input_lines()) {}

private:
    Turing machine_;
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
