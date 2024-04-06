#include <algorithm>
#include <cstdint>
#include <iterator>
#include <map>
#include <string>
#include <vector>

#include "day06.hpp"

// Given a vector of memory banks, redistribute by finding the one
// with the most contents and moving that equally across successive
// banks, wrapping around at zero.
auto redistribute_memory(std::vector<int> &banks) -> void {
    auto max = std::max_element(banks.begin(), banks.end());
    auto maxIndex = static_cast<uint64_t>(std::distance(banks.begin(), max));
    auto toDistribute = static_cast<uint64_t>(*max);
    *max = 0;  // remove what we are about to distribute

    for (auto offset = 1U; offset <= toDistribute; ++offset) {
        banks[(maxIndex + offset) % banks.size()]++;
    }
    // A way to go faster would be to distribute toDistribute / banks.size()
    // to all banks in one go and then do the
    // incrementing loop for toDistribute % banks.size() only.
    // However, for our puzzle input this is fast enough.
}

// Find the loop in the given vector of banks
auto find_memory_distribution_loop(std::vector<int> banks) -> Loop {
    // Map the state of the banks to the loop count where it was seen.
    // unrodered_map could be faster, but map allows for easier key comparison.
    std::map<std::vector<int>, int> states;
    auto loopCount = 0;
    while (states.find(banks) == states.end()) {
        states[banks] = loopCount;
        redistribute_memory(banks);
        loopCount++;
    }
    return Loop{/*detected*/ loopCount, /*length*/ loopCount - states[banks]};
}

auto Day06::calculate_a() -> std::string {
    auto loop = find_memory_distribution_loop(banks_);
    return std::to_string(loop.detected);
}

auto Day06::calculate_b() -> std::string {
    auto loop = find_memory_distribution_loop(banks_);
    return std::to_string(loop.length);
}
