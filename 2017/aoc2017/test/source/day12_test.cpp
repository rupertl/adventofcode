#include <catch2/catch_test_macros.hpp>
#include <vector>

#include "day12.hpp"

TEST_CASE("Connections to programs", "[days]") {
    ProgramGroups prg{{
        "0 <-> 2",
        "1 <-> 1",
        "2 <-> 0, 3, 4",
        "3 <-> 2, 4",
        "4 <-> 2, 3, 6",
        "5 <-> 6",
        "6 <-> 4, 5"
    }};

    REQUIRE(prg.count_programs_in_group(0) == 6);
    REQUIRE(prg.count_programs_in_group(1) == 1);

    REQUIRE(prg.count_distinct_groups() == 2);
}
