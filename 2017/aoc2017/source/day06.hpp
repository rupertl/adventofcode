#include <string>
#include <utility>
#include <vector>

#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 6: Memory Reallocation

// Stores at which redistribution cycle an infinite loop was detected,
// and how long the loop is.
struct Loop {
    int detected;
    int length;
};

// Find a loop using the vector of memory banks
auto find_memory_distribution_loop(std::vector<int> banks) -> Loop;

class Day06 : public Puzzle {
public:
    constexpr static auto DAY = 6;
    explicit Day06(PuzzleData puzzleData)
        : Puzzle(DAY, std::move(puzzleData)) {}

private:
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
