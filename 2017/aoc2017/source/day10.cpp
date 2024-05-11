#include <string>

#include "day10.hpp"
#include "knothash.hpp"
#include "parser.hpp"

auto Day10::calculate_a() -> std::string {
    KnotHash knh;
    // "1,2,3" -> {1, 2, 3}
    const auto lengths = parse_row<unsigned int>(csv_to_tsv(input_string()));
    knh.apply_twists(lengths);
    return std::to_string(knh.check());
}

auto Day10::calculate_b() -> std::string {
    return KnotHash(input_string()).sparse_hex();
}
