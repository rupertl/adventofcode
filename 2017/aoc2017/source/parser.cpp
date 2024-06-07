#include <algorithm>
#include <iterator>
#include <regex>
#include <stdexcept>
#include <string>
#include <vector>

#include "parser.hpp"

// NOLINTNEXTLINE: bugprone-easily-swappable-parameters
auto parse_regex(const std::string &line, const std::string &regex)
    -> std::vector<std::string> {
    std::vector<std::string> items;
    const std::regex compiled(regex);
    auto matchBegin = std::sregex_iterator(line.begin(), line.end(), compiled);
    auto matchEnd = std::sregex_iterator();

    for (std::sregex_iterator itr = matchBegin; itr != matchEnd; ++itr) {
        items.push_back(itr->str());
    }

    return items;
}

auto parse_words(const std::string &line) -> std::vector<std::string> {
    return parse_regex(line, "\\w+");
}

auto csv_to_tsv(const std::string &line) -> std::string {
    std::string out;
    std::replace_copy(line.begin(), line.end(),
                      std::back_inserter(out),
                      ',', '\t');
    return out;
}

// NOLINTNEXTLINE: bugprone-easily-swappable-parameters
auto find_word_after(const std::string &line, const std::string &search)
    -> std::string {
    const std::regex reg{search + "\\s*(\\w+)"};
    std::smatch parts;
    std::regex_search(line, parts, reg);
    if (parts.size() != 2) {
        throw std::runtime_error("Bad parse results");
    }
    return parts[1];
}
