#include <catch2/catch_test_macros.hpp>
#include <string>
#include <vector>

#include "day07.hpp"

TEST_CASE("Tower of program discs]") {
    const std::vector<std::string> input = {
        "pbga (66)",
        "xhth (57)",
        "ebii (61)",
        "havc (66)",
        "ktlj (57)",
        "fwft (72) -> ktlj, cntj, xhth",
        "qoyq (66)",
        "padx (45) -> pbga, havc, qoyq",
        "tknk (41) -> ugml, padx, fwft",
        "jptl (61)",
        "ugml (68) -> gyxo, ebii, jptl",
        "gyxo (61)",
        "cntj (57)"
    };
    auto tower = parse_program_tower(input);

    SECTION("parse") {
        REQUIRE(tower.size() == 13);
        const std::string sample = "padx";
        REQUIRE(tower.find(sample) != tower.end());
        REQUIRE(tower[sample].name == sample);
        REQUIRE(tower[sample].weight == 45);
        REQUIRE(tower[sample].children.size() == 3);
        REQUIRE(tower[sample].parent == "tknk");
    }

    const std::string root{"tknk"};
    SECTION("find root") {
        REQUIRE(find_root_disc(tower) == root);
    }

    SECTION("corrected weight") {
        REQUIRE(find_corrected_weight(tower) == 60);
    }
}
