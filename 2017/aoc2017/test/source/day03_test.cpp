#include <catch2/catch_test_macros.hpp>

#include "day03.hpp"

TEST_CASE("Find spiral memory distances", "[days]") {
    REQUIRE(find_spiral_memory_distance(1) == 0);
    REQUIRE(find_spiral_memory_distance(4) == 1);
    REQUIRE(find_spiral_memory_distance(12) == 3);
    REQUIRE(find_spiral_memory_distance(23) == 2);
    REQUIRE(find_spiral_memory_distance(1024) == 31);
}

TEST_CASE("Find spiral memory larger value", "[days]") {
    REQUIRE(find_spiral_memory_value_larger_than(0) == 1);
    REQUIRE(find_spiral_memory_value_larger_than(10) == 11);
    REQUIRE(find_spiral_memory_value_larger_than(20) == 23);
    REQUIRE(find_spiral_memory_value_larger_than(30) == 54);
}
