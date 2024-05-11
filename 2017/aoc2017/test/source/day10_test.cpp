#include <catch2/catch_test_macros.hpp>

#include "knothash.hpp"

TEST_CASE("Knot hash after twists", "[days]") {
    KnotHash knh{5};            // NOLINT: magic-number
    REQUIRE(knh.check() == 0);

    knh.twist(3);
    REQUIRE(knh.check() == 2);

    knh.twist(4);
    REQUIRE(knh.check() == 12);

    knh.twist(1);
    REQUIRE(knh.check() == 12);

    knh.twist(5);               // NOLINT: magic-number
    REQUIRE(knh.check() == 12);
}

TEST_CASE("Knot hash sparse hex", "[days]") {
    REQUIRE(KnotHash("").sparse_hex() ==
            "a2582a3a0e66e6e86e3812dcb672a272");
    REQUIRE(KnotHash("AoC 2017").sparse_hex() ==
            "33efeb34ea91902bb2f59c9920caa6cd");
    REQUIRE(KnotHash("1,2,3").sparse_hex() ==
            "3efbe78a8d82f29979031a4aa0b16a9d");
    REQUIRE(KnotHash("1,2,4").sparse_hex() ==
            "63960835bcdc130f0b66d7ff4f6a5a8e");
}
