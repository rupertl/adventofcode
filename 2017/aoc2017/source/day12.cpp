#include <cassert>
#include <string>
#include <unordered_map>
#include <unordered_set>
#include <vector>

#include "day12.hpp"
#include "parser.hpp"

ProgramGroups::ProgramGroups(const std::vector<std::string> &lines) {
    // Parse lines like "2 <-> 0, 3, 4" to the connections map.
    for (const auto &line : lines) {
        auto words = parse_words(line);  // eg {2, 0, 3, 4}
        // Assumption: all programs have at least one connection
        assert(words.size() > 1);
        auto fromProgram = std::stoi(words[0]);
        for (const auto &word : words) {
            auto toProgram = std::stoi(word);
            // Connection is bidirectional and includes itself.
            connections_[fromProgram].insert(toProgram);
            connections_[toProgram].insert(fromProgram);
        }
    }
}

auto ProgramGroups::count_programs_in_group(int source) -> int {
    return static_cast<int>(find_programs_in_group(source).size());
}

auto ProgramGroups::find_programs_in_group(int source)
    -> std::unordered_set<int> {
    std::unordered_set<int> seen; // Programs in this group
    std::vector<int> queue;       // Queue of programs to explore
    queue.push_back(source);
    // Explore programs reached from item at the back of the queue
    while (!queue.empty()) {
        auto next = queue.back();
        queue.pop_back();
        seen.insert(next);
        for (auto connected : connections_[next]) {
            // Is this a new program we have not seen?
            if (seen.find(connected) == seen.end()) {
                queue.push_back(connected);
            }
        }
    }

    return seen;
}

auto ProgramGroups::count_distinct_groups() -> int {
    auto groups = 0;
    std::unordered_set<int> seen; // Program IDs seen already in any group
    // Iterate over all program IDs
    for (const auto &item : connections_) {
        auto program = item.first;
        // Is this program not in a group we have seen already?
        if (seen.find(program) == seen.end()) {
            auto connected = find_programs_in_group(program);
            // Merge all connections into seen
            seen.insert(connected.begin(), connected.end());
            groups++;
        }
    }
    return groups;
}

auto Day12::calculate_a() -> std::string {
    return std::to_string(program_groups_.count_programs_in_group(0));
}

auto Day12::calculate_b() -> std::string {
    return std::to_string(program_groups_.count_distinct_groups());
}
