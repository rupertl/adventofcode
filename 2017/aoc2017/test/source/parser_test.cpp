#include <catch2/catch_test_macros.hpp>
#include <string>
#include <vector>

#include "parser.hpp"

TEST_CASE("Parse column of ints", "[library]") {
    const std::vector<std::string> input{"1", "2", "3", "42"};
    const std::vector<int> expected{1, 2, 3, 42};
        
    REQUIRE(parse_col<int>(input) == expected);
}

TEST_CASE("Parse grids of ints", "[library]") {
    const std::vector<std::string> input{"1 2 3", "4\t5", "6 7 88"};
    const std::vector<std::vector<int>> expected{{1, 2, 3}, {4, 5}, {6, 7, 88}};
    REQUIRE(parse_matrix<int>(input) == expected);
}

TEST_CASE("Parse grids of strings", "[library]") {
    const std::vector<std::string> input{"abc def", "ghi", "foo bar baz"};
    const std::vector<std::vector<std::string>> expected{
        {"abc", "def"},
        {"ghi"},
        {"foo", "bar", "baz"}
    };
    REQUIRE(parse_matrix<std::string>(input) == expected);
}
