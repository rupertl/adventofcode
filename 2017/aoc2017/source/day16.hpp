#pragma once

#include <cstdint>
#include <string>
#include <vector>

#include "parser.hpp"
#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 16: Permutation Promenade

// Represents a number of programs (identified by letters) which can
// be re-ordered based on dance steps.
class ProgramDance {
public:
    static constexpr auto STD_SIZE = 16;
    explicit ProgramDance(std::size_t size = STD_SIZE);
    auto order() const -> std::string  { return programs_; }
    void dance(const std::vector<std::string> &instructions);
    void step(const std::string &instruction);

private:
    std::string programs_;
    void spin(int amount);
    void exchange(std::size_t pos1, std::size_t pos2);
    void partner(char program1, char program2);
};

class Day16 : public Puzzle {
public:
    constexpr static auto DAY = 16;
    explicit Day16(PuzzleData puzzleData)
        : Puzzle(DAY, std::move(puzzleData)),
          instructions_(parse_row<std::string>(csv_to_tsv(input_string()))) {}

private:
    std::vector<std::string> instructions_;
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
