#include <algorithm>
#include <iomanip>
#include <ios>
#include <numeric>
#include <sstream>
#include <string>
#include <vector>

#include "knothash.hpp"

KnotHash::KnotHash(unsigned int size)
    : list_(size) {
    std::iota(list_.begin(), list_.end(), 0); // Fill with 0..size-1
}

KnotHash::KnotHash(const std::string &input)
    : KnotHash(KNOTHASH_SIZE) {
    constexpr auto KNOTHASH_ROUNDS = 64U;
    const auto trailer = std::vector<unsigned int>{17, 31, 73, 47, 23};
    std::vector<unsigned int> lengths;
    for (auto chr : input) {
        lengths.push_back(static_cast<unsigned int>(chr));
    }
    lengths.insert(lengths.end(), trailer.begin(), trailer.end());
    for (auto round = 0U; round < KNOTHASH_ROUNDS; ++round) {
        apply_twists(lengths);
    }
}


void KnotHash::apply_twists(const std::vector<unsigned int> &lengths) {
    std::for_each(lengths.begin(), lengths.end(), [&](const auto length) {
        twist(length);
    });
}

void KnotHash::twist(unsigned int length) {
    auto size = static_cast<unsigned int>(list_.size());
    // Reverse elements, wrapping around
    for (auto index = 0U; index < length / 2U; ++index) {
        auto fromIndex = (position_ + index) % size;
        auto toIndex = (position_ + length - (1 + index)) % size;
        std::swap(list_[fromIndex], list_[toIndex]);
    }

    position_ = (position_ + length + skip_size_++) % size;
}

auto KnotHash::check() -> int {
    return list_[0] * list_[1];
}

auto KnotHash::sparse_hex() -> std::string {
    // Assuming standard KNOTHASH_SIZE.
    // WIDTH * WIDTH = KNOTHASH_SIZE
    constexpr auto WIDTH = 16U;
    std::stringstream stream;
    for (auto i = 0U; i < WIDTH; ++i) {
        int digit = 0;
        for (auto j = 0U; j < WIDTH; ++j) {
            digit ^= list_[i*WIDTH + j]; // NOLINT: hicpp-signed-bitwise
        }
        stream << std::setfill('0') << std::setw(2) << std::hex;
        stream << digit;
    }
    return stream.str();
}

