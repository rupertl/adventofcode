#pragma once

#include <bitset>

// Simple 2D point class.
// Note this uses a row-column addressing system where the origin is
// in the top left and the vertical axis increases going down. This
// matches must puzzle inputs but is not the same as a x-y system
// where the vertical axis increases going up.

struct Point {
    int row = 0;
    int col = 0;
    void operator+=(const Point &other);
    auto operator==(const Point &other) const -> bool;
    auto operator<(const Point &other) const -> bool;
};

static constexpr auto NORTH = Point{-1, 0};
static constexpr auto SOUTH = Point{1, 0};
static constexpr auto EAST = Point{0, 1};
static constexpr auto WEST = Point{0, -1};

auto operator+(const Point &pt1, const Point &pt2) -> Point;

// Custom hash for use in unordered_map etc.
// Points in AOC have coordinates in range (-100'000 - +100'000)
// so combine to provide hopefully a unique number.
template <>
struct std::hash<Point>
{
    auto operator()(const Point& point) const noexcept -> std::size_t {
        constexpr auto BIAS = 100'000;
        constexpr auto SHIFT = 16;
        // NOLINTNEXTLINE: hicpp-signed-bitwise
        auto rowBiased = (BIAS + point.row) << SHIFT;
        auto colBiased = point.col;
        return std::hash<int>{}(rowBiased + colBiased);
    }
};
