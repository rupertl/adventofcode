#include <stdexcept>
#include <string>
#include <vector>

#include "day22.hpp"
#include "point.hpp"

constexpr auto PRINT_INFECTED = '#';
constexpr auto PRINT_CLEAN = '.';

Cluster::Cluster(const std::vector<std::string> &nodes, bool evolved)
    : evolved_(evolved) {
    if (nodes.empty() || nodes.size() != nodes[0].size()) {
        throw std::runtime_error("Assuming map was square");
    }
    if (nodes.size() % 2 != 1) {
        throw std::runtime_error("Assuming odd number of nodes on each side");
    }

    // Parse nodes. The top left square will be {0, 0} and the
    // starting location in the middle.
    auto side = nodes.size();
    auto mid = static_cast<int>((nodes.size() - 1) / 2U);
    location_ = Point{mid, mid};
    for (auto row = 0U; row < side; ++row) {
        for (auto col = 0U; col < side; ++col) {
            if (nodes[row][col] == PRINT_INFECTED) {
                auto point = Point{static_cast<int>(row),
                                   static_cast<int>(col)};
                infected_.insert({point, INFECTED});
            } else if (nodes[row][col] != PRINT_CLEAN) {
                throw std::runtime_error("Unexpected node value");
            }
        }
    }
}

void Cluster::burst() {
    auto &status = infected_[location_]; // default is CLEAN

    switch (status) {
    case INFECTED:
        turn_right();
        status = evolved_ ? FLAGGED : CLEAN;
        break;
    case CLEAN:
        turn_left();
        status = evolved_ ? WEAKENED : INFECTED;
        break;
    case WEAKENED:
        // Don't turn
        status = INFECTED;
        break;
    case FLAGGED:
        reverse();
        status = CLEAN;
        break;
    }

    if (status == INFECTED) {
        ++newly_infected_;
    }

    location_ += direction_;
}

void Cluster::turn_left() {
    if (direction_ == NORTH) {
        direction_ = WEST;
    } else if (direction_ == SOUTH) {
        direction_ = EAST;
    } else if (direction_ == EAST) {
        direction_ = NORTH;
    } else if (direction_ == WEST) {
        direction_ = SOUTH;
    }
    else {
        throw std::runtime_error("Bad direction to turn from");
    }
}

void Cluster::turn_right() {
    if (direction_ == NORTH) {
        direction_ = EAST;
    } else if (direction_ == SOUTH) {
        direction_ = WEST;
    } else if (direction_ == EAST) {
        direction_ = SOUTH;
    } else if (direction_ == WEST) {
        direction_ = NORTH;
    }
    else {
        throw std::runtime_error("Bad direction to turn from");
    }
}

void Cluster::reverse() {
    direction_.row *= -1;
    direction_.col *= -1;
}

auto newly_infected_after(const std::vector<std::string> &nodes,
                          int iterations,
                          bool evolved = false) -> std::string {
    Cluster cluster(nodes, evolved);
    for (auto i = 0; i < iterations; ++i) {
        cluster.burst();
    }
    return std::to_string(cluster.count_newly_infected());
}

auto Day22::calculate_a() -> std::string {
    constexpr auto ITERATIONS = 10000;
    return newly_infected_after(input_lines(), ITERATIONS);
}

auto Day22::calculate_b() -> std::string {
    constexpr auto ITERATIONS = 10000000;
    return newly_infected_after(input_lines(), ITERATIONS, /*evolved*/ true);
}
