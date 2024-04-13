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
