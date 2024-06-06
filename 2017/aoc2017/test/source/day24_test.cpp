#include <catch2/catch_test_macros.hpp>
#include <string>
#include <vector>

#include "day24.hpp"

TEST_CASE("Electromagnetic Moat", "[days]") {
    const std::vector<std::string> input = {
        "0/2",
        "2/2",
        "2/3",
        "3/4",
        "3/5",
        "0/1",
        "10/1",
        "9/10",
    };

    auto comps = parse_components(input);
    REQUIRE(comps.find(0) != comps.end());
    REQUIRE(comps.count(3) == 3); // 2/3, 3/4, 3/5

    auto results = find_best_bridges(comps);
    REQUIRE(results.strongest == 31);
    REQUIRE(results.longest_strength == 19);
}
