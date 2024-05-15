#include <catch2/catch_test_macros.hpp>

#include "day16.hpp"

TEST_CASE("Dancing programs", "[days]") {
    constexpr auto TEST_SIZE = 5;
    ProgramDance prd(TEST_SIZE);
    REQUIRE(prd.order() == "abcde");

    prd.step("s1");
    REQUIRE(prd.order() == "eabcd");

    prd.step("x3/4");
    REQUIRE(prd.order() == "eabdc");

    prd.step("pe/b");
    REQUIRE(prd.order() == "baedc");

    // Part 2

    prd.step("s1");
    REQUIRE(prd.order() == "cbaed");

    prd.step("x3/4");
    REQUIRE(prd.order() == "cbade");

    prd.step("pe/b");
    REQUIRE(prd.order() == "ceadb");
}
