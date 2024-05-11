#include <catch2/catch_test_macros.hpp>
#include <string>
#include <vector>

#include "day13.hpp"

TEST_CASE("Firewall severity", "[days]") {
    const std::vector<std::string> input = {
        "0: 3",
        "1: 2",
        "4: 4",
        "6: 4"
    };
    auto fwall = Firewall(input);
    REQUIRE(fwall.severity(0) == 24);
    REQUIRE(fwall.severity(10) == 0);
    REQUIRE(fwall.evade_delay() == 10);
}
