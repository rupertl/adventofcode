#include <catch2/catch_test_macros.hpp>
#include <string>

#include "day09.hpp"

TEST_CASE("Remove garbage", "[days]") {
    REQUIRE(remove_garbage("{<>}") == "{}");
    REQUIRE(remove_garbage("{<fhsdjhf:>}") == "{}");
    REQUIRE(remove_garbage("{<<<<>}") == "{}");
    REQUIRE(remove_garbage("{<{!>}>}") == "{}");
    REQUIRE(remove_garbage("{<!!>}") == "{}");
    REQUIRE(remove_garbage("{<!!!>>}") == "{}");
    REQUIRE(remove_garbage("{<{o\"i!a,<{i<a>}") == "{}");
    REQUIRE(remove_garbage("{{<a>},{<a>},{<a>},{<a>}}") == "{{},{},{},{}}");
    REQUIRE(remove_garbage("{{<!>},{<!>},{<!>},{<a>}}") == "{{}}");
}

TEST_CASE("Group score", "[days]") {
    REQUIRE(group_score("{}") == 1);
    REQUIRE(group_score("{{{}}}") == 6);
    REQUIRE(group_score("{{},{}}") == 5);
    REQUIRE(group_score("{{{},{},{{}}}}") == 16);
    REQUIRE(group_score("{<a>,<a>,<a>,<a>}") == 1);
    REQUIRE(group_score("{{<ab>},{<ab>},{<ab>},{<ab>}}") == 9);
    REQUIRE(group_score("{{<!!>},{<!!>},{<!!>},{<!!>}}") == 9);
    REQUIRE(group_score("{{<a!>},{<a!>},{<a!>},{<ab>}}") == 3);
}

TEST_CASE("Count garbage removed", "[days]") {
    REQUIRE(collect_garbage("<>").removed == 0);
    REQUIRE(collect_garbage("<random characters>").removed == 17);
    REQUIRE(collect_garbage("<<<<>").removed == 3);
    REQUIRE(collect_garbage("<{!>}>").removed == 2);
    REQUIRE(collect_garbage("<!!>").removed == 0);
    REQUIRE(collect_garbage("<!!!>>").removed == 0);
    REQUIRE(collect_garbage("<{o\"i!a,<{i<a>").removed == 10);
}
