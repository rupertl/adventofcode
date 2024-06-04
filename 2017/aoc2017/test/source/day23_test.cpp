#include <catch2/catch_test_macros.hpp>
#include <string>
#include <vector>

#include "duet.hpp"

TEST_CASE("Coprocessor additional instructions", "[days]") {
    const std::vector<std::string> instructions = {
        "jnz h 2",              // Skip next if h is set externally
        "set h 100",            // Set h to wrong value
        "set a 23",             // Set a to 23
        "mul a h",              // h *= 23
        "sub a 4",              // h -= 4
    };

    auto duet = Duet(instructions);
    duet.set_register('h', 2);
    duet.run();

    constexpr auto ANSWER = 42;
    REQUIRE(duet.get_register('a') == ANSWER);
}
