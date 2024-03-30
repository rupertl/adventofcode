#include <fstream>
#include <string>
#include <vector>

#include "puzzle_data.hpp"

PuzzleData::PuzzleData(const std::string &path)
    : input_{read_input(path)}, solutions_{read_solutions(path)} {}

auto PuzzleData::has_input() const -> bool { return !input_.empty(); }

auto PuzzleData::input_as_lines() const -> const std::vector<std::string> & {
    return input_;
}

auto PuzzleData::input_as_string() const -> const std::string & {
    // Treat the input as a single line
    return input_[0];
}

auto PuzzleData::input_as_int() const -> int {
    // Treat the input as a single number
    return std::stoi(input_[0]);
}

auto PuzzleData::has_solution(SolutionPart::Type part) const -> bool {
    return solutions_.find(part) != solutions_.end();
}

auto PuzzleData::solution(SolutionPart::Type part) const -> std::string {
    return solutions_.at(part);
}

auto PuzzleData::read_input(const std::string &path) -> InputVector {
    PuzzleData::InputVector lines;
    std::ifstream file(path + "/input");
    if (file) {
        std::string line;
        while (std::getline(file, line)) {
            lines.push_back(line);
        }
        file.close();
    }
    return lines;
}

auto PuzzleData::read_solutions(const std::string &path) -> SolutionMap {
    PuzzleData::SolutionMap solutions;
    for (auto suffix : SolutionPart::All) {
        std::ifstream file(path + "/solution." + static_cast<char>(suffix));
        if (file) {
            std::string line;
            if (file >> line) {
                solutions[suffix] = line;
            }
            file.close();
        }
    }
    return solutions;
}
