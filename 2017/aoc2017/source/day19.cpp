#include <cctype>
#include <cstddef>
#include <stdexcept>
#include <string>
#include <vector>

#include "day19.hpp"
#include "point.hpp"

Network::Network(const std::vector<std::string> &diagram)
    : diagram_(diagram),
      max_row_(static_cast<int>(diagram.size())),
      max_col_(static_cast<int>(diagram[0].size())) {
    // Find the starting point, the only | in the first row.
    for (Point start = {0, 0}; start.col < max_col_; ++start.col) {
        if (get(start) == '|') {
            start_ = start;
            return;
        }
    }
    throw std::runtime_error("No start found");
}

auto Network::route() const -> NetworkRoute {
    NetworkRoute route;
    Point location = start_;
    Point direction = SOUTH;
    while (true) {
        location += direction;
        if (! valid(location)) {
            // We have come to the end of the route
            return route;
        }
        ++route.steps;
        auto here = get(location);
        if (isalpha(here) != 0) {
            // We passed a letter
            route.path += here;
        } else if (here == '+') {
            // We need to change direction
            direction = corner(location, direction);
        }
    }
}

auto Network::valid(const Point &pnt) const -> bool {
    return pnt.row >= 0 && pnt.row < max_row_ &&
           pnt.col >= 0 && pnt.col < max_col_ &&
           get(pnt) != ' ';
}

auto Network::get(const Point &pnt) const -> char {
    return diagram_[static_cast<std::size_t>(pnt.row)]
                   [static_cast<std::size_t>(pnt.col)];
}


// NOLINTNEXTLINE: bugprone-easily-swappable-parameters
auto Network::corner(const Point &location, const Point &direction) const
    -> Point {
    static constexpr auto N_S = {NORTH, SOUTH};
    static constexpr auto E_W = {EAST, WEST};
    // If we were going E/W (row is not changing), we can turn either
    // N or S, and vice versa
    const auto &choices = (direction.row == 0) ? N_S : E_W;
    for (auto newDirection : choices) {
        if (valid(location + newDirection)) {
            return newDirection;
        }
    }
    throw std::runtime_error("No valid route from corner found");
}

auto Day19::calculate_a() -> std::string {
    const Network net(input_lines());
    return net.route().path;
}

auto Day19::calculate_b() -> std::string {
    const Network net(input_lines());
    return std::to_string(net.route().steps);
}
