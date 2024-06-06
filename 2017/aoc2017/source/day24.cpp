#include <bitset>
#include <cstdint>
#include <stdexcept>
#include <map>
#include <string>
#include <queue>
#include <vector>

#include "day24.hpp"
#include "parser.hpp"

// As we use a bitset in find_best_bridges we have an upper limit
// of number of compinents.
constexpr auto MAX_COMPONENTS = 8 * sizeof(uint64_t);

auto parse_components(const std::vector<std::string> &compNames)
    -> ComponentMap {
    ComponentMap comps;
    uint64_t cid = 1;

    if (compNames.size() > MAX_COMPONENTS) {
        throw std::runtime_error("Too many components");
    }

    for (const auto &name : compNames) {
        auto words = parse_words(name);
        if (words.size() != 2) {
            throw std::runtime_error("Error parsing components");
        }
        auto end1 = std::stoi(words[0]);
        auto end2 = std::stoi(words[1]);
        // Insert the component twice, unless each end is the same.
        auto comp = Component{cid, end1, end2};
        comps.insert({end1, comp});
        if (end1 != end2) {
            comps.insert({end2, comp});
        }
        cid <<= 1U;             // Each cid will be a unique bit
    }

    return comps;
}

// Keep track of bridge candidates.
struct Candidate {
    std::bitset<MAX_COMPONENTS> used; // Each cid has a bit
    int end = 0;                      // Pins at end of bridge
    int strength = 0;                 // Total strength so far
};

// See if a candidate beats the current set of results and update in
// place if so.
void evaluate_candidate(BridgeResults &results, const Candidate &candidate) {
    if (candidate.strength > results.strongest) {
        results.strongest = candidate.strength;
    }

    auto length = candidate.used.count();
    if (length > results.longest_length) {
        results.longest_length = length;
        results.longest_strength = candidate.strength;
    } else if (length == results.longest_length &&
               candidate.strength > results.longest_strength) {
        results.longest_strength = candidate.strength;
    }
}

// Do a BFS on all component combinations that match the pin constraints
// and construct results.
auto find_best_bridges(const ComponentMap &components) -> BridgeResults {
    BridgeResults results;
    std::queue<Candidate> candidates;

    // Find starting components (have a 0-pin port) and add to the queue.
    auto starts = components.equal_range(0);
    for (auto itr = starts.first; itr != starts.second; ++itr) {
        auto comp = itr->second;
        auto candidate =
            Candidate{comp.cid(), comp.otherPort(0), comp.strength()};
        candidates.push(candidate);
    }

    // Process queue until empty
    while (!candidates.empty()) {
        auto candidate = candidates.front();
        candidates.pop();

        // Find next components that could be added to this candidate
        auto nexts = components.equal_range(candidate.end);
        bool atEnd = true;
        for (auto itr = nexts.first; itr != nexts.second; ++itr) {
            auto comp = itr->second;
            auto newUsed =
                candidate.used | std::bitset<MAX_COMPONENTS>(comp.cid());
            if (candidate.used != newUsed) {
                // This component fits and has not been used already
                auto newCandidate =
                    Candidate{newUsed, comp.otherPort(candidate.end),
                              candidate.strength + comp.strength()};
                candidates.push(newCandidate);
                atEnd = false;
            }
        }

        // If components cannot be added further, evaluate this candidate.
        if (atEnd) {
            evaluate_candidate(results, candidate);
        }
    }

    return results;
}

auto Day24::calculate_a() -> std::string {
    return std::to_string(find_best_bridges(components_).strongest);
}

auto Day24::calculate_b() -> std::string {
    return std::to_string(find_best_bridges(components_).longest_strength);
}
