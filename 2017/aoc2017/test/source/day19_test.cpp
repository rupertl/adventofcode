#include <catch2/catch_test_macros.hpp>
#include <string>
#include <vector>

#include "day19.hpp"
#include "point.hpp"

TEST_CASE("Tubes", "[days]") {
    const std::vector<std::string> diagram = {
        "     |          ",
        "     |  +--+    ",
        "     A  |  C    ",
        " F---|----E|--+ ",
        "     |  |  |  D ",
        "     +B-+  +--+",
    };
    const Network net(diagram);
    REQUIRE(net.start() == Point{0, 5});
    REQUIRE(net.route().path == "ABCDEF");
    REQUIRE(net.route().steps == 38);
}
