#include <catch2/catch_test_macros.hpp>

#include "day11.hpp"

TEST_CASE("Distance from centre of hex grid", "[days]") {
    REQUIRE(HexGrid{{"ne", "ne", "ne"}}.distance() == 3);
    REQUIRE(HexGrid{{"ne", "ne", "sw", "sw"}}.distance() == 0);
    REQUIRE(HexGrid{{"ne", "ne", "s", "s"}}.distance() == 2);
    REQUIRE(HexGrid{{"se", "sw", "se", "sw", "sw"}}.distance() == 3);

    REQUIRE(HexGrid{{"s", "s", "s"}}.distance() == 3);
    REQUIRE(HexGrid{{"sw", "nw", }}.distance() == 2);
    REQUIRE(HexGrid{{"n", "n", "nw", "nw"}}.distance() == 4);

    REQUIRE(HexGrid{{"n", "n", "n" }}.max_distance() == 3);
    REQUIRE(HexGrid{{"n", "n", "s" }}.max_distance() == 2);
}
