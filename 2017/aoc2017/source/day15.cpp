#include <cstdint>
#include <string>
#include <vector>

#include "day15.hpp"
#include "parser.hpp"

constexpr auto MOD = uint64_t{2147483647};
constexpr auto LOWER_16 = uint64_t{0xffff};

auto Generator::next() -> uint64_t {
    while (true) {
        value = (value * factor) % MOD;
        if (criteria == 0 || (value % criteria) == 0) {
            return value;
        }
    }
}

auto judge_generators(Generator &genA, Generator &genB, int loops) -> int {
    int matches = 0;
    for (int loop = 0; loop < loops; ++loop) {
        if ((genA.next() & LOWER_16) == (genB.next() & LOWER_16)) {
            ++matches;
        }
    }
    return matches;
}

auto get_generators(const std::vector<std::string> &lines)
    -> std::vector<Generator> {
    std::vector<Generator> gens;
    bool isA = true;
    for (const auto &line : lines) {
        auto words = parse_words(line);
        Generator gen;
        gen.value = std::stoul(words[4]);
        gen.factor = isA ? Generator::FACTOR_A : Generator::FACTOR_B;
        isA = false;
        gens.push_back(gen);
    }
    return gens;
}

auto Day15::calculate_a() -> std::string {
    auto gens = get_generators(input_lines());
    return std::to_string(judge_generators(gens[0], gens[1], JUDGE_LOOPS_1));
}

auto Day15::calculate_b() -> std::string {
    auto gens = get_generators(input_lines());
    gens[0].criteria = Generator::CRITERIA_A;
    gens[1].criteria = Generator::CRITERIA_B;
    return std::to_string(judge_generators(gens[0], gens[1], JUDGE_LOOPS_2));
}
