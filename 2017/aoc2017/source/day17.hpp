#pragma once

#include <cstdint>
#include <string>
#include <vector>

#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 17: Spinlock

class Spinlock {
public:
    static constexpr auto TARGET_PART_1 = 2017;
    static constexpr auto TARGET_PART_2 = 50'000'000;
    explicit Spinlock(int jumpSize) : jump_size_(jumpSize) {
        buffer_.reserve(static_cast<std::size_t>(jumpSize));
    }
    void spin(int times = 1);
    auto next() const -> int;
    auto after_zero() const -> int;

private:
    int jump_size_;
    std::vector<int> buffer_ = {0};
    int position_ = 0;
};

// For part 2, predict what value will be after zero.
auto predict_spinlock_after_zero(int jumpSize, int times) -> int;

class Day17 : public Puzzle {
public:
    constexpr static auto DAY = 17;
    explicit Day17(PuzzleData puzzleData)
        : Puzzle(DAY, std::move(puzzleData)),
          steps_(std::stoi(input_string())) {}

private:
    int steps_;
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
