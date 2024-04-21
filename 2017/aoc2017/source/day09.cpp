#include <string>

#include "day09.hpp"

auto collect_garbage(const std::string &input) -> GarbageCollector {
    GarbageCollector gbc;

    bool inGarbage = false;   // are we inside garbage?
    bool cancelNext = false;  // should we ignore the next character?
    for (auto chr : input) {
        if (inGarbage) {
            if (cancelNext) {
                cancelNext = false;
            } else if (chr == '!') {
                cancelNext = true;
            } else if (chr == '>') {
                inGarbage = false;
            } else {
                ++gbc.removed;
            }
        } else if (chr == '<') {
            inGarbage = true;
        } else {
            gbc.cleaned.push_back(chr);
        }
    }

    return gbc;
}

auto remove_garbage(const std::string &input) -> std::string {
    return collect_garbage(input).cleaned;
}

auto group_score(const std::string &input) -> int {
    int total = 0;
    int level = 0;              // Keep track of our level of braces
    for (auto chr : remove_garbage(input)) {
        if (chr == '{') {
            total += ++level;
        } else if (chr == '}') {
            --level;
        }
    }
    return total;
}

auto Day09::calculate_a() -> std::string {
    return std::to_string(group_score(input_string()));
}

auto Day09::calculate_b() -> std::string {
    return std::to_string(collect_garbage(input_string()).removed);
}
