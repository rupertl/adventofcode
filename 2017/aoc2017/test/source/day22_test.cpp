#include <catch2/catch_test_macros.hpp>
#include <map>
#include <set>
#include <string>
#include <vector>

#include "day22.hpp"
#include "point.hpp"

TEST_CASE("Virus 1", "[days]") {
    const std::vector<std::string> nodes = {
        "..#",
        "#..",
        "...",
    };

    auto cluster = Cluster(nodes);
    auto preInfected = std::set<Point>{{0, 2}, {1, 0}};
    auto initial = cluster.infected();
    REQUIRE(initial.size() == 2);
    REQUIRE(initial.find(Point{0, 2}) != initial.end());
    REQUIRE(initial.find(Point{1, 0}) != initial.end());

    constexpr auto MAX_BURST = 10000;
    std::map<int, int> burstsInfected = {
        {7, 5},                 // NOLINT
        {70, 41},               // NOLINT
        {MAX_BURST, 5587},      // NOLINT
    };

    for (int i = 1; i <= MAX_BURST; ++i) {
        cluster.burst();
        auto itr = burstsInfected.find(i);
        if (itr != burstsInfected.end()) {
            REQUIRE(cluster.count_newly_infected() == itr->second);
        }
    }
}

TEST_CASE("Virus 2", "[days]") {
    const std::vector<std::string> nodes = {
        "..#",
        "#..",
        "...",
    };

    auto cluster = Cluster(nodes, /*evolved*/ true);
    constexpr auto MAX_BURST = 100;
    constexpr auto EXPECTED = 26;
    for (int i = 1; i <= MAX_BURST; ++i) {
        cluster.burst();
    }
    REQUIRE(cluster.count_newly_infected() == EXPECTED);
}
