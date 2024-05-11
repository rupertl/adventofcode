#pragma once

#include <vector>

// Default size for a knot hash - only changed if testing.
constexpr auto KNOTHASH_SIZE = 256;

// Represents a hash where you perform a number of twist operations.
class KnotHash {
public:
    // Initialise an empty hash
    explicit KnotHash(unsigned int size = KNOTHASH_SIZE);
    // Initialise a hash from a string
    explicit KnotHash(const std::string &input);
    void apply_twists(const std::vector<unsigned int> &lengths);
    void twist(unsigned int length);
    // Returns product of first 2 elements
    auto check() -> int;
    auto sparse_hex() -> std::string;

private:
    std::vector<int> list_;
    unsigned int position_{0};
    unsigned int skip_size_{0};
};

