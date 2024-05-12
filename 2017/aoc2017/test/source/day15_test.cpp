#include <catch2/catch_test_macros.hpp>

#include "day15.hpp"

constexpr auto TEST_START_A = 65;
constexpr auto TEST_START_B = 8921;

TEST_CASE("Generators part 1", "[days]") {
    Generator genA;
    genA.value = TEST_START_A;
    genA.factor = Generator::FACTOR_A;

    Generator genB;
    genB.value = TEST_START_B;
    genB.factor = Generator::FACTOR_B;

    SECTION("Single step") {
        REQUIRE(genA.next() == 1092455);
        REQUIRE(genB.next() == 430625591);

        REQUIRE(genA.next() == 1181022009);
        REQUIRE(genB.next() == 1233683848);
    }

    SECTION("Judge") {
        REQUIRE(judge_generators(genA, genB, JUDGE_LOOPS_1) == 588);
    }
}

TEST_CASE("Generators part 2", "[days]") {
    Generator genA;
    genA.value = TEST_START_A;
    genA.factor = Generator::FACTOR_A;
    genA.criteria = Generator::CRITERIA_A;

    Generator genB;
    genB.value = TEST_START_B;
    genB.factor = Generator::FACTOR_B;
    genB.criteria = Generator::CRITERIA_B;

    SECTION("Single step") {
        REQUIRE(genA.next() == 1352636452);
        REQUIRE(genB.next() == 1233683848);

        REQUIRE(genA.next() == 1992081072);
        REQUIRE(genB.next() == 862516352);
    }

    SECTION("Judge") {
        REQUIRE(judge_generators(genA, genB, JUDGE_LOOPS_2) == 309);
    }
}
