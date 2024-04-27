#include <algorithm>
#include <regex>
#include <string>
#include <vector>

#include "parser.hpp"

auto parse_words(const std::string &line) -> std::vector<std::string> {
    std::vector<std::string> words;
    const std::regex wordsRegex("\\w+");
    auto wordsBegin = std::sregex_iterator(line.begin(), line.end(), wordsRegex);
    auto wordsEnd = std::sregex_iterator();
 
    for (std::sregex_iterator itr = wordsBegin; itr != wordsEnd; ++itr) {
        words.push_back(itr->str());
    }
    
    return words;
}

auto csv_to_tsv(const std::string &line) -> std::string {
    std::string out;
    std::replace_copy(line.begin(), line.end(),
                      std::back_inserter(out),
                      ',', '\t');
    return out;
}
