#pragma once

#include <array>
#include <string>
#include <unordered_map>
#include <vector>

// There are usually two sub puzzles using the same input. AOC website
// calls them part1 and part2 but we use a and b to distinguihs from
// the puzzle day number.
namespace SolutionPart {
enum Type { A = 'a', B = 'b' };
static const std::array<Type, 2> All = {A, B};
}  // namespace SolutionPart

// Read puzzle inputs and solutions from a file.
// There is a single input (in path/input)
// and a solution for each SolutionPart (in path/solution.[ab])
// Some or all of this data may be missing.
class PuzzleData {
public:
    explicit PuzzleData(const std::string &path);
    auto has_input() const -> bool;
    auto input_as_lines() const -> const std::vector<std::string> &;
    auto input_as_string() const -> const std::string &;
    auto input_as_int() const -> int;
    auto has_solution(SolutionPart::Type part) const -> bool;
    auto solution(SolutionPart::Type part) const -> std::string;

private:
    using InputVector = std::vector<std::string>;
    InputVector input_;
    using SolutionMap = std::unordered_map<SolutionPart::Type, std::string>;
    SolutionMap solutions_;
    static auto read_input(const std::string &path) -> InputVector;
    static auto read_solutions(const std::string &path) -> SolutionMap;
};
