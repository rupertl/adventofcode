#include <string>
#include <vector>
#include <catch2/catch_test_macros.hpp>

#include "day08.hpp"

TEST_CASE("Register maximum values]") {
    const std::vector<std::string> input = {
        "b inc 5 if a > 1",
        "a inc 1 if b < 5",
        "c dec -10 if a >= 1",
        "c inc -20 if c == 10",
    };

    SECTION("Find largest register value at end") {
        REQUIRE(find_largest_register_value(input).end == 1);
    }
    SECTION("Find largest register value during processing") {
        REQUIRE(find_largest_register_value(input).running == 10);
    }
    SECTION("Empty instructions test") {
        const std::vector<std::string> empty;
        auto result = find_largest_register_value(empty);
        REQUIRE(result.end == 0);
        REQUIRE(result.running == 0);
    }
}
