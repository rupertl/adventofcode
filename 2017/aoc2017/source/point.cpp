#include "point.hpp"

auto operator+(const Point &pt1, const Point &pt2) -> Point {
    return Point{pt1.row + pt2.row, pt1.col + pt2.col};
}

void Point::operator+=(const Point &other) {
    row += other.row;
    col += other.col;
}

auto Point::operator==(const Point &other) const -> bool {
    return row == other.row && col == other.col;
}

auto Point::operator<(const Point &other) const -> bool {
    if (row < other.row) {
        return true;
    }
    if (row > other.row) {
        return false;
    }
    return col < other.col;
}
