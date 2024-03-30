#include <algorithm>
#include <cassert>
#include <cmath>
#include <map>
#include <utility>

#include "day03.hpp"

// Represents a spiral memory structure with 2D location and address
// eg
// 17  16  15  14  13
// 18   5   4   3  12
// 19   6   1   2  11
// 20   7   8   9  10
// 21  22  23---> ...
// So address 1, the starting position, is at x=y=0

class SpiralMemory {
public:
    // Jump to a location, setting x, y and address
    void go_to(int toAddress) {
        // Find the bottom right corner that is larger than toAddress
        go_to_larger_corner(toAddress);

        // Move left, up, right, down until we find the address
        go_straight(std::min(ring_ - 1, address_ - toAddress), -1, 0);
        go_straight(std::min(ring_ - 1, address_ - toAddress), 0, 1);
        go_straight(std::min(ring_ - 1, address_ - toAddress), 1, 0);
        go_straight(std::min(ring_ - 1, address_ - toAddress), 0, -1);
        assert(address_ == toAddress);
    }

    // Find the distance from the start location
    auto distance() const { return std::abs(x_) + std::abs(y_); }

    // Set values based on part B. Find the largest value greater than target
    auto stress_fill_until(int target) {
        // Store (x, y) -> value at that address
        std::map<std::pair<int, int>, int> values;
        reset();
        int value = 1;
        while (value <= target) {
            values.insert({{x_, y_}, value});
            // go_to is not optimal to find the next point, but is fast
            // enough for given input
            go_to(address_ + 1);
            value = 0;
            // Add up values at all points surrounding this
            for (auto xOffset : {-1, 0, 1}) {
                for (auto yOffset : {-1, 0, 1}) {
                    if (xOffset == 0 && yOffset == 0) {
                        continue;
                    }
                    const auto itr = values.find({x_ + xOffset, y_ + yOffset});
                    value += itr == values.cend() ? 0 : itr->second;
                }
            }
        }
        return value;
    }

private:
    int x_{0};
    int y_{0};
    int address_{1};
    int ring_{1};  // central ring is 1, then 3, 5, etc

    // Reset to starting position
    void reset() {
        x_ = 0;
        y_ = 0;
        address_ = 1;
        ring_ = 1;
    }

    // Jump to the first address of a bottom right corner larger than target.
    // We can see that the corners have addresses 1*1, 3*3, 5*5 etc
    void go_to_larger_corner(int target) {
        reset();
        while (address_ < target) {
            ring_ += 2;
            x_++;
            y_--;
            address_ = ring_ * ring_;
        }
    }

    // Move length units in the direction given by offsets
    void go_straight(int length, int xBias, int yBias) {
        if (length > 0) {
            address_ -= length;
            x_ += xBias * length;
            y_ += yBias * length;
        }
    }
};

auto find_spiral_memory_distance(int address) -> int {
    SpiralMemory spm;
    spm.go_to(address);
    return spm.distance();
}

auto find_spiral_memory_value_larger_than(int target) -> int {
    return SpiralMemory{}.stress_fill_until(target);
}
