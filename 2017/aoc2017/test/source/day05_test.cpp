#include <catch2/catch_test_macros.hpp>

#include "day05.hpp"

TEST_CASE("Find escape count", "[days]") {
    auto sampleData = {0, 3, 0, 1, -3};
    REQUIRE(find_escape_count(sampleData, false) == 5);
    REQUIRE(find_escape_count(sampleData, true) == 10);
}
