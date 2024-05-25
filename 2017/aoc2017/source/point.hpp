#pragma once

// Simple 2D point class

struct Point {
    int row = 0;
    int col = 0;
    void operator+=(const Point &other);
    auto operator==(const Point &other) const -> bool;
};

static constexpr auto NORTH = Point{-1, 0};
static constexpr auto SOUTH = Point{1, 0};
static constexpr auto EAST = Point{0, 1};
static constexpr auto WEST = Point{0, -1};

auto operator+(const Point &pt1, const Point &pt2) -> Point;
