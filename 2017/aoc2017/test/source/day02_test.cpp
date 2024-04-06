#include <catch2/catch_test_macros.hpp>
#include <vector>

#include "day02.hpp"

TEST_CASE("Calculate checksum for sample input", "[days]") {
    const std::vector<std::vector<int>> cells = {
        {5, 1, 9, 5}, {7, 5, 3}, {2, 4, 6, 8}};
    REQUIRE(spreadsheet_checksum(cells) == 18);
}

TEST_CASE("Find evenly divisible sum for sample input", "[days]") {
    const std::vector<std::vector<int>> cells = {
        {5, 9, 2, 8}, {9, 4, 7, 3}, {3, 8, 6}};
    REQUIRE(evenly_divisible_sum(cells) == 9);
}
