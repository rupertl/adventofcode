#include <algorithm>
#include <cassert>
#include <cmath>
#include <string>
#include <vector>

#include "day11.hpp"

void HexGrid::apply_steps(const std::vector<std::string> &steps) {
    std::for_each(steps.begin(), steps.end(),
                  [&](const auto &dir) { step(dir); });
}

void HexGrid::step(const std::string &dir) {
    // View the grid as a 2D field, moving from centre point of each hex
    // Going N is moving up the y axis 2 positions
    // Going NE is moving up y 1 and up x 2
    if (dir == "n") {
        y += 2;
    } else if (dir == "s") {
        y -= 2;
    } else if (dir == "ne") {
        x += 2;
        y++;
    } else if (dir == "nw") {
        x -= 2;
        y++;
    } else if (dir == "se") {
        x += 2;
        y--;
    } else if (dir == "sw") {
        x -= 2;
        y--;
    } else {
        assert(false);
    }

    max_distance_ = std::max(max_distance_, distance());
}

auto HexGrid::distance() const -> int {
    auto dist = 0;
    // Reflect to the NE quadrant
    auto absx = std::abs(x);
    auto absy = std::abs(y);
    // Move SW until we hit an axis
    // This could probably be calculated quicker using a closed form solution.
    while (absx > 0 && absy > 0) {
        absx -= 2;
        absy--;
        dist++;
    }
    assert(absx >= 0 && absy >= 0);
    // Move S or W until we reach 0,0
    dist += std::max(absx, absy) / 2;
    return dist;
}

auto Day11::calculate_a() -> std::string {
    return std::to_string(HexGrid(steps_).distance());
}

auto Day11::calculate_b() -> std::string {
    return std::to_string(HexGrid(steps_).max_distance());
}
