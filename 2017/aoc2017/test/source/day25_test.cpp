#include <catch2/catch_test_macros.hpp>
#include <string>
#include <vector>

#include "day25.hpp"

TEST_CASE("Halting Problem", "[days]") {
    const std::vector<std::string> input = {
        "Begin in state A.",
        "Perform a diagnostic checksum after 6 steps.",
        "",
        "In state A:",
        "  If the current value is 0:",
        "    - Write the value 1.",
        "    - Move one slot to the right.",
        "    - Continue with state B.",
        "  If the current value is 1:",
        "    - Write the value 0.",
        "    - Move one slot to the left.",
        "    - Continue with state B.",
        "",
        "In state B:",
        "  If the current value is 0:",
        "    - Write the value 1.",
        "    - Move one slot to the left.",
        "    - Continue with state A.",
        "  If the current value is 1:",
        "    - Write the value 1.",
        "    - Move one slot to the right.",
        "    - Continue with state A.        ",
    };

    Turing machine(input);
    REQUIRE(machine.diagnostic_checksum() == 3);
}
