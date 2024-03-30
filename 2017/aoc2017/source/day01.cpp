#include <cstddef>
#include <string>

#include "day01.hpp"

// Solve the captcha by comparing pairs of numbers in the string. If
// they are equal, sum them up and return the result. Which number to
// compare to is driven by the offset from the current number.
auto solve_captcha(const std::string &input, std::size_t offset) -> int {
    // Not specified by the puzzle rules
    if (input.length() < 2) {
        return 0;
    }

    auto sum = 0;
    auto len = input.length();
    for (auto index = 0U; index < len; ++index) {
        auto left = input[index];
        auto right = input[(index + offset) % len];
        if (left == right) {
            sum += left - '0';
        }
    }

    return sum;
}

// Compare sequential pairs
auto solve_captcha_sequential(const std::string &input) -> int {
    return solve_captcha(input, 1U);
}

// Compare against the value half way around the input
auto solve_captcha_halfway(const std::string &input) -> int {
    return solve_captcha(input, input.length() / 2);
}

auto Day01::calculate_a() -> std::string {
    return std::to_string(solve_captcha_sequential(input_string()));
}

auto Day01::calculate_b() -> std::string {
    return std::to_string(solve_captcha_halfway(input_string()));
}
