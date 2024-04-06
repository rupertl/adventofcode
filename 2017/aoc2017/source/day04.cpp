#include <algorithm>
#include <string>
#include <unordered_set>
#include <vector>

#include "day04.hpp"
#include "parser.hpp"

auto passphrase_has_no_duplicate_words(const std::string &passphrase,
                                       bool allowAnagrams) -> bool {
    // Parse line to words, add each to a set and return false if we
    // see the same word more than once.
    std::unordered_set<std::string> wordsSeen;
    for (auto &word : parse_row<std::string>(passphrase)) {
        if (! allowAnagrams) {
            // Sort word to spot anagrams (updating word in place)
            std::sort(word.begin(), word.end());
        }
        if (wordsSeen.find(word) != wordsSeen.end()) {
            // Duplicate found, can return early
            return false;
        }
        wordsSeen.insert(word);
    }
    return true;
}

auto count_valid(const std::vector<std::string> &lines, bool allowAnagrams) {
    return std::count_if(lines.begin(), lines.end(), [=](const auto &line) {
        return passphrase_has_no_duplicate_words(line, allowAnagrams);
    });
}

auto Day04::calculate_a() -> std::string {
    return std::to_string(count_valid(input_lines(), /*allowAnagrams*/ true));
}

auto Day04::calculate_b() -> std::string {
    return std::to_string(count_valid(input_lines(), /*allowAnagrams*/ false));
}
