#include <algorithm>
#include <cassert>
#include <string>
#include <unordered_map>
#include <unordered_set>
#include <vector>

#include "day07.hpp"
#include "parser.hpp"

using ProgramTower = std::unordered_map<std::string, ProgramDisc>;

auto parse_program_tower(const std::vector<std::string> &lines)
    -> ProgramTower {
    ProgramTower tower;

    // Go through the input and set up discs and any children
    // eg "fwft (72) -> ktlj, cntj, xhth"
    // word[0] is name, word[1] is weight, any remaining words are children
    for (const auto &line : lines) {
        const auto words = parse_words(line);
        assert(words.size() >= 2);
        ProgramDisc disc;
        disc.name = words[0];
        disc.weight = std::stoi(words[1]);

        for (auto index = 2U; index < words.size(); ++index) {
            disc.children.insert(words[index]);
        }

        tower[disc.name] = disc;
    }

    // Set parent of any children nodes
    for (auto &[parentName, parentDisc] : tower) {
        for (const auto &child : parentDisc.children) {
            tower[child].parent = parentName;
        }
    }

    return tower;
}

auto find_root_disc(const ProgramTower &tower) -> std::string {
    // The root will be the node with no parent.
    const auto root = std::find_if(
        tower.begin(), tower.end(),
        [](const auto &item) { return item.second.parent.empty(); });
    if (root != tower.end()) {
        return root->second.name;
    }
    return "(no root found)";
}

auto find_and_correct_weight(   // NOLINT: recursion
    const std::string &name, ProgramTower &tower) -> int {
    auto &node = tower[name];
    if (node.children.empty()) {
        // Termibal case
        return node.weight;
    }

    // Get the subweights for each child. Keep track of items we have
    // seen once and those more than once, so we can decide if one
    // needs to be corrected.
    std::unordered_map<int, std::string> seenOnce; // weight -> name
    std::unordered_set<int> seenMoreThanOnce;      // weight
    for (const auto &childName : node.children) {
        // Get the weight for the child recursively
        auto subWeight = find_and_correct_weight(childName, tower);
        // If we have not seen this more than once
        if (seenMoreThanOnce.find(subWeight) == seenMoreThanOnce.end()) {
            // And if we have not seen this just once
            if (seenOnce.find(subWeight) == seenOnce.end()) {
                // Save it as seen once
                seenOnce[subWeight] = childName;
            } else {
                // Save it as seen more than once
                seenOnce.erase(subWeight);
                seenMoreThanOnce.insert(subWeight);
            }
        }
    }

    // The implicit assumption here is that number of children is not
    // 1, and if there were two children there must be no mismatch, as
    // we would not know which one was correct.
    assert(seenMoreThanOnce.size() == 1);
    const int target = *(seenMoreThanOnce.begin());

    // There was a mismatch, correct it
    if (seenOnce.size() == 1) {
        const auto & [childWeight, childName] = *(seenOnce.begin());
        tower[childName].weight += target - childWeight;
        tower[childName].wasCorrected = true;
    }

    // Return the total weight of this node and all its children.
    return (static_cast<int>(node.children.size()) * target) + node.weight;
}

auto find_corrected_weight(std::unordered_map<std::string,
                           ProgramDisc> &tower) -> int {
    find_and_correct_weight(find_root_disc(tower), tower);
    const auto corrected =
        std::find_if(tower.begin(), tower.end(),
                     [](const auto &item) { return item.second.wasCorrected; });
    if (corrected != tower.end()) {
        return corrected->second.weight;
    }
    return 0;
}

auto Day07::calculate_a() -> std::string {
    return find_root_disc(tower_);
}

auto Day07::calculate_b() -> std::string {
    return std::to_string(find_corrected_weight(tower_));
}
