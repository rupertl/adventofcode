#pragma once

#include <cstdint>
#include <iostream>
#include <string>
#include <unordered_map>
#include <utility>

#include "puzzle_data.hpp"

// Base class for puzzle implementation.
class Puzzle {
public:
    explicit Puzzle(int day, PuzzleData puzzleData)
        : day_(day), puzzleData_(std::move(puzzleData)) {}
    virtual ~Puzzle() = default;
    Puzzle(const Puzzle &) = delete;
    auto operator=(const Puzzle &) -> Puzzle & = delete;
    Puzzle(Puzzle &&) = delete;
    auto operator=(Puzzle &&) -> Puzzle & = delete;

    auto day() const -> int { return day_; }
    friend auto operator<<(std::ostream &out, const Puzzle &puzzle)
        -> std::ostream &;
    void calculate();
    auto report(SolutionPart::Type part) const -> std::string;

    auto input_lines() const -> const std::vector<std::string> & {
        return puzzleData_.input_as_lines();
    }
    auto input_string() const -> std::string {
        return puzzleData_.input_as_string();
    }
    auto input_int() const -> int {
        return puzzleData_.input_as_int();
    }

    void run() {
        calculate();
        std::cout << *this << "\n";
    }

private:
    int day_;
    PuzzleData puzzleData_;
    std::unordered_map<SolutionPart::Type, std::string> results_;
    std::unordered_map<SolutionPart::Type, int64_t> elapsedMs_;
    void calculate_part(SolutionPart::Type part);
    virtual auto calculate_a() -> std::string = 0;
    virtual auto calculate_b() -> std::string = 0;

};
