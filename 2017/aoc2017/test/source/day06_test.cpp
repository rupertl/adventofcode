#include <catch2/catch_test_macros.hpp>

#include "day06.hpp"

TEST_CASE("Find infinite loop in memory distribution", "[days]") {
    auto sampleBanks = {0, 2, 7, 0};  // NOLINT: magic-number
    auto loop = find_memory_distribution_loop(sampleBanks);
    REQUIRE(loop.detected == 5);
    REQUIRE(loop.length == 4);
}
