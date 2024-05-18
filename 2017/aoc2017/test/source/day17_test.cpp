#include <catch2/catch_test_macros.hpp>

#include "day17.hpp"

TEST_CASE("Spinlock single step", "[days]") {
    Spinlock spl(3);
    REQUIRE(spl.next() == 0);

    spl.spin(1);                // Insert 1
    REQUIRE(spl.next() == 0);
    spl.spin(1);                // 2
    REQUIRE(spl.next() == 1);
    spl.spin(1);                // 3
    REQUIRE(spl.next() == 1);
    spl.spin(1);                // 4
    REQUIRE(spl.next() == 3);
    spl.spin(1);                // 5
    REQUIRE(spl.next() == 2);

    spl.spin(4);                // 6-9
    REQUIRE(spl.next() == 5);

    REQUIRE(spl.after_zero() == 9);
}

TEST_CASE("Spinlock 2017", "[days]") {
    Spinlock spl(3);
    spl.spin(Spinlock::TARGET_PART_1);
    REQUIRE(spl.next() == 638);
    REQUIRE(spl.after_zero() == 1226);
}

TEST_CASE("Spinlock 2017 predicted value", "[days]") {
    REQUIRE(predict_spinlock_after_zero(3, Spinlock::TARGET_PART_1) == 1226);
}
