#include <catch2/catch_test_macros.hpp>
#include <string>
#include <vector>

#include "day18.hpp"
#include "duet.hpp"

TEST_CASE("Duet channel", "[days]") {
    DuetChannel dch;
    REQUIRE(! dch.can_any_receive());

    dch.send(0, 123);           // NOLINT: magic-number
    dch.send(0, 456);           // NOLINT: magic-number
    REQUIRE(! dch.can_receive(0));
    REQUIRE(dch.can_receive(1));
    REQUIRE(dch.receive(1) == 123);

    dch.send(1, 789);           // NOLINT: magic-number
    REQUIRE(dch.can_receive(0));
    REQUIRE(dch.can_receive(1));
    REQUIRE(dch.receive(1) == 456);
    REQUIRE(dch.receive(0) == 789);

    REQUIRE(! dch.can_any_receive());
}

TEST_CASE("Duet as a sound card", "[days]") {
    const std::vector<std::string> instructions = {
        "set a 1",
        "add a 2",
        "mul a a",
        "mod a 5",
        "snd a",
        "set a 0",
        "rcv a",
        "jgz a -1",
        "set a 1",
        "jgz a -2",
    };

    auto scd = Duet(instructions);

    SECTION("parse") {
        REQUIRE(scd.num_instructions() == 10);
    }

    SECTION("recovery") {
        scd.run();
        REQUIRE(scd.recovered_sound() == 4);
    }
}

TEST_CASE("Duet multi-cpu", "[days]") {
    const std::vector<std::string> instructions = {
        "snd 1",
        "snd 2",
        "snd p",
        "rcv a",
        "rcv b",
        "rcv c",
        "rcv d",
    };

    REQUIRE(count_sent_duet_1(instructions) == 3);
}
