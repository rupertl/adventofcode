#include <algorithm>
#include <iterator>
#include <regex>
#include <string>
#include <vector>

#include "parser.hpp"

// NOLINTNEXTLINE: bugprone-easily-swappable-parameters
auto parse_regex(const std::string &regex, const std::string &line) -> std::vector<std::string> {
    std::vector<std::string> items;
    const std::regex compiled(regex);
    auto matchBegin = std::sregex_iterator(line.begin(), line.end(),
                                           compiled);
    auto matchEnd = std::sregex_iterator();
 
    for (std::sregex_iterator itr = matchBegin; itr != matchEnd; ++itr) {
        items.push_back(itr->str());
    }
    
    return items;
}

auto parse_words(const std::string &line) -> std::vector<std::string> {
    return parse_regex("\\w+", line);
}

auto csv_to_tsv(const std::string &line) -> std::string {
    std::string out;
    std::replace_copy(line.begin(), line.end(),
                      std::back_inserter(out),
                      ',', '\t');
    return out;
}
