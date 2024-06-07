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

TEST_CASE("Parse line into words", "[library]") {
    const std::string input{"\tgame, set  & match."};
    const std::vector<std::string> expected{"game", "set", "match"};
    REQUIRE(parse_words(input) == expected);
}

TEST_CASE("Convert csv to tsv", "[library]") {
    const std::string input{"42,3,0,1"};
    REQUIRE(csv_to_tsv(input) == "42\t3\t0\t1");
}

TEST_CASE("Parse a regex", "[library]") {
    const std::string input{"Readings are 3, 12 and 42."};
    const std::vector<std::string> expected{"3", "12", "42"};
    REQUIRE(parse_regex(input, "\\d+") == expected);
}

TEST_CASE("Get word after", "[library]") {
    const std::string input{"Move to the left, please."};
    REQUIRE(find_word_after(input, "to the") == "left");
}
