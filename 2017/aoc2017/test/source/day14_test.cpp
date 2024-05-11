#include <catch2/catch_test_macros.hpp>

#include "day14.hpp"

TEST_CASE("Day 14", "[days]") {
    Disk disk("flqrgnkx");
    REQUIRE(disk.count_used() == 8108);
    REQUIRE(disk.count_regions() == 1242);
    REQUIRE(disk.count_regions() == 1242); // check non-destructive
}
