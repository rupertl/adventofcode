#include <catch2/catch_test_macros.hpp>

#include "puzzle_data.hpp"

TEST_CASE("PuzzleData with no data", "[library]") {
    const PuzzleData empty("path/does/not/exist");
    REQUIRE(!empty.has_input());
    REQUIRE(!empty.has_solution(SolutionPart::Type::A));
}

TEST_CASE("PuzzleData with data", "[library]") {
    // Note this must be run from the aoc2017 directory so it can find
    // the data.
    // Sample input is 1, 2, 3, 4, 5
    // Sample solution for part A is 15
    const PuzzleData loaded("data/sample/00");
    REQUIRE(loaded.has_input());
    REQUIRE(loaded.has_solution(SolutionPart::Type::A));
    REQUIRE(loaded.input_as_string() == "1");
    REQUIRE(loaded.input_as_int() == 1);
    REQUIRE(loaded.input_as_lines().size() == 5);
    REQUIRE(loaded.input_as_lines()[0] == "1");
    REQUIRE(loaded.input_as_lines()[4] == "5");
    REQUIRE(loaded.has_solution(SolutionPart::Type::A));
    REQUIRE(loaded.solution(SolutionPart::Type::A) == "15");
}
