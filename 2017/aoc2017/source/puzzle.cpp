#include <chrono>
#include <cstdint>
#include <iomanip>
#include <iostream>
#include <string>

#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Display running time if it takes more than thus
constexpr auto TIME_BUDGET_MS = 10;

auto operator<<(std::ostream &out, const Puzzle &puzzle) -> std::ostream & {
    out << "Day " << std::setfill('0') << std::setw(2) << puzzle.day() << ": ";
    out << puzzle.report(SolutionPart::Type::A);
    out << " ";
    out << puzzle.report(SolutionPart::Type::B);
    return out;
}

void Puzzle::calculate() {
    for (auto part : SolutionPart::All) {
        calculate_part(part);
    }
}

auto format_time(int64_t elapsedMs) -> std::string {
    constexpr auto ONE_SECOND = 1000;
    if (elapsedMs < ONE_SECOND) {
        return std::to_string(elapsedMs) + "ms";
    }
    return std::to_string(elapsedMs / ONE_SECOND) + "s";
}

//  Return a string for the given part showing our result, whether it
//  matched the target solution and how long it took.
auto Puzzle::report(SolutionPart::Type part) const -> std::string {
    if (results_.find(part) == results_.end()) {
        return "(not solved)";
    }
    const auto &ours = results_.at(part);

    if (!puzzleData_.has_solution(part)) {
        return ours + " (unsubmitted)";
    }
    auto theirs = puzzleData_.solution(part);

    auto correctness = (ours == theirs) ? " ✔" : " ✗ (" + theirs + ")";

    std::string timing;
    auto itr = elapsedMs_.find(part);
    if (itr != elapsedMs_.end()) {
        auto elapsedMs = itr->second;
        if (elapsedMs > TIME_BUDGET_MS) {
            timing = " [" + format_time(elapsedMs) + "]";
        }
    }

    return ours + correctness + timing;
}

void Puzzle::calculate_part(SolutionPart::Type part) {
    auto start = std::chrono::high_resolution_clock::now();

    if (part == SolutionPart::Type::A) {
        results_[part] = calculate_a();
    } else if (part == SolutionPart::Type::B) {
        results_[part] = calculate_b();
    }

    auto stop = std::chrono::high_resolution_clock::now();
    elapsedMs_[part] =
        std::chrono::duration_cast<std::chrono::milliseconds>
        (stop - start).count();
}
