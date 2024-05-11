#include <algorithm>
#include <string>
#include <unordered_map>
#include <utility>
#include <vector>

#include "day14.hpp"
#include "knothash.hpp"

// Disk is of a fixed sized WIDTH * WIDTH
static constexpr auto DISK_WIDTH = 128;

Disk::Disk(const std::string &key) {
    for (auto row = 0; row < DISK_WIDTH; ++row) {
        auto hashInput = key + '-' + std::to_string(row);
        auto hashOutput = KnotHash(hashInput).sparse_hex();
        for (auto digit : hashOutput) {
            add_bits(digit);
        }
    }
}

auto Disk::count_used() -> int{
    return static_cast<int>(std::count(storage_.begin(), storage_.end(), 1));
}

auto Disk::count_regions() -> int {
    // Take a copy as remove_adjacent is destructive
    auto copy = storage_;
    int regions = 0;
    for (auto row = 0; row < DISK_WIDTH; ++row) {
        for (auto col = 0; col < DISK_WIDTH; ++col) {
            if (is_used(row, col)) {
                ++regions;
                remove_adjacent(row, col);
            }
        }
    }
    storage_ = std::move(copy);
    return regions;
}

// Add four binary digits to the end of storage based on the hex digit
// supplied
void Disk::add_bits(char digit) {
    const static std::unordered_map<char, std::vector<int>> HEX_DIGIT_BITS = {
        {'0', {0, 0, 0, 0}},
        {'1', {0, 0, 0, 1}},
        {'2', {0, 0, 1, 0}},
        {'3', {0, 0, 1, 1}},
        {'4', {0, 1, 0, 0}},
        {'5', {0, 1, 0, 1}},
        {'6', {0, 1, 1, 0}},
        {'7', {0, 1, 1, 1}},
        {'8', {1, 0, 0, 0}},
        {'9', {1, 0, 0, 1}},
        {'a', {1, 0, 1, 0}},
        {'b', {1, 0, 1, 1}},
        {'c', {1, 1, 0, 0}},
        {'d', {1, 1, 0, 1}},
        {'e', {1, 1, 1, 0}},
        {'f', {1, 1, 1, 1}}
    };

    const auto &bits = HEX_DIGIT_BITS.at(digit);
    storage_.insert(storage_.end(), bits.begin(), bits.end());
}

void Disk::remove_adjacent(int startRow, int startCol) {
    // Queue of points forming the region
    std::vector<std::pair<int, int>> queue;
    queue.emplace_back(startRow, startCol);
    while (! queue.empty()) {
        auto [row, col] = queue.back();
        queue.pop_back();
        clear(row, col);
        
        // Check N, S, E, W
        for (int delta = -1; delta <= 1; delta += 2) {
            if (is_used(row+delta, col)) {
                queue.emplace_back(row+delta, col);
            }
            if (is_used(row, col+delta)) {
                queue.emplace_back(row, col+delta);
            }
        }
    }
}

auto row_col_to_index(int row, int col) {
    return static_cast<unsigned int>((row * DISK_WIDTH) + col);
}

// Returns true if storage location is set, otherwise false. Allows
// locations outside range which will also return false.
auto Disk::is_used(int row, int col) -> bool {
    if (row >= 0 && row < DISK_WIDTH && col >= 0 && col < DISK_WIDTH) {
        return storage_[row_col_to_index(row, col)];
    }
    return false;
}

void Disk::clear(int row, int col) {
    storage_[row_col_to_index(row, col)] = false;
}

auto Day14::calculate_a() -> std::string {
    return std::to_string(disk_.count_used());
}

auto Day14::calculate_b() -> std::string {
    return std::to_string(disk_.count_regions());
}
