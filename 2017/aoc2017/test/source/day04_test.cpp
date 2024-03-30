#include <catch2/catch_test_macros.hpp>

#include "day04.hpp"

TEST_CASE("Passphrase contains no duplicate words", "[days]") {
    REQUIRE(passphrase_has_no_duplicate_words("aa bb cc dd ee", true));
    REQUIRE(! passphrase_has_no_duplicate_words("aa bb cc dd aa", true));
    REQUIRE(passphrase_has_no_duplicate_words("aa bb cc dd aaa", true));
}

TEST_CASE("Passphrase contains no anagrams", "[days]") {
    REQUIRE(passphrase_has_no_duplicate_words("abcde fghij", false));
    REQUIRE(! passphrase_has_no_duplicate_words("abcde xyz ecdab", false));
    REQUIRE(passphrase_has_no_duplicate_words("a ab abc abd abf abj", false));
    REQUIRE(passphrase_has_no_duplicate_words("iiii oiii ooii oooi oooo",
                                              false));
    REQUIRE(! passphrase_has_no_duplicate_words("oiii ioii iioi iiio", false));
}
