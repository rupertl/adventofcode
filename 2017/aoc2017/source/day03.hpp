#include <string>
#include <utility>

#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 3: Spiral Memory

auto find_spiral_memory_distance(int address) -> int;
auto find_spiral_memory_value_larger_than(int target) -> int;

class Day03 : public Puzzle {
public:
    explicit Day03(PuzzleData puzzleData) : Puzzle(3, std::move(puzzleData)) {}

private:
    auto calculate_a() -> std::string override {
        return std::to_string(find_spiral_memory_distance(input_int()));
    }

    auto calculate_b() -> std::string override {
        return std::to_string(find_spiral_memory_value_larger_than(input_int()));
    }
};
