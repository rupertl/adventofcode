#include <catch2/catch_test_macros.hpp>
#include <string>
#include "day01.hpp"

TEST_CASE("Solve captchas sequentially", "[days]") {
    REQUIRE(solve_captcha_sequential("1122") == 3);
    REQUIRE(solve_captcha_sequential("1111") == 4);
    REQUIRE(solve_captcha_sequential("1234") == 0);
    REQUIRE(solve_captcha_sequential("91212129") == 9);
}

TEST_CASE("Solve captchas halfway around", "[days]") {
    REQUIRE(solve_captcha_halfway("1212") == 6);
    REQUIRE(solve_captcha_halfway("1221") == 0);
    REQUIRE(solve_captcha_halfway("123425") == 4);
    REQUIRE(solve_captcha_halfway("123123") == 12);
    REQUIRE(solve_captcha_halfway("12131415") == 4);
}
