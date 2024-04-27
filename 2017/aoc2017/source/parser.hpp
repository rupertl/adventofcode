#pragma once

#include <algorithm>
#include <sstream>
#include <string>
#include <utility>
#include <vector>

// Take a vector of strings, each containing one value that can be
// converted to type T, and return as a vector of T.
// eg {"1", "2", "3"} -> {1, 2, 3}
template <typename T>
auto parse_col(const std::vector<std::string> &lines) -> std::vector<T> {
    std::vector<T> results;
    for (auto const &line : lines) {
        std::stringstream lineParser;
        lineParser << line;
        T item{};
        if (lineParser >> item) {
            results.push_back(item);
        }
    }
    return results;
}

// Take a string containing space-separated values and
// return a vector of T
// eg "1 2 3" -> {1, 2, 3}
template <typename T>
auto parse_row(const std::string &line) -> std::vector<T> {
    std::vector<T> row;
    std::stringstream lineParser;
    lineParser << line;
    T cell{};
    while (lineParser >> cell) {
        row.emplace_back(cell);
    }
    return row;
}

// Take a vector of strings containing space-separated values and
// return a 2D vector of T
// eg {"1 2 3", "4 5 6"} -> {{1, 2, 3}, {4, 5, 6}}
template <typename T>
auto parse_matrix(const std::vector<std::string> &lines)
    -> std::vector<std::vector<T>> {
    std::vector<std::vector<T>> mat;
    mat.reserve(lines.size());
    std::transform(lines.begin(), lines.end(), std::back_inserter(mat),
                   parse_row<T>);
    return mat;
}

// Split a string into words, using the regex \w+ to remove punctuation etc
auto parse_words(const std::string &line) -> std::vector<std::string>;

// Make a csv line a tsv line for easier parsing
auto csv_to_tsv(const std::string &line) -> std::string;
