#include <catch2/catch_test_macros.hpp>
#include "day02.hpp"

TEST_CASE("Calculate checksum for sample input", "[days]") {
    auto sheet = Spreadsheet({"5 1 9 5", "7 5 3", "2 4 6 8"});
    REQUIRE(sheet.checksum() == 18);
}

TEST_CASE("Find evenly divisible sum for sample input", "[days]") {
    auto sheet = Spreadsheet({"5 9 2 8", "9 4 7 3", "3 8 6 5"});
    REQUIRE(sheet.evenly_divisible_sum() == 9);
}
