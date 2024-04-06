#include <algorithm>
#include <numeric>
#include <string>
#include <vector>

#include "day02.hpp"

// The checksum is the sum of the difference of the max and min value
// on each row.
auto spreadsheet_checksum(const std::vector<std::vector<int>> &cells) -> int {
    return std::accumulate(
        cells.begin(), cells.end(), 0, [](int total, const auto &row) {
            const auto [min, max] = std::minmax_element(begin(row), end(row));
            return total + *max - *min;
        });
}

// Find the two numbers in the row that are evenly divisible (ie have
// a remainder of zero) and return the result of dividing them.
auto find_evenly_divisible(const std::vector<int> &row) -> int {
    for (auto i = 0U; i < row.size(); ++i) {
        for (auto j = i + 1; j < row.size(); ++j) {
            auto [denom, numer] = std::minmax(row[i], row[j]);
            if (numer % denom == 0) {
                return numer / denom;
            }
        }
    }
    // The puzzle guarantees there will be two such numbers, but in
    // case return 0 if none found.
    return 0;
}

auto evenly_divisible_sum(const std::vector<std::vector<int>> &cells) -> int {
    return std::accumulate(cells.begin(), cells.end(), 0,
                           [](int total, const auto &row) {
                               return total + find_evenly_divisible(row);
                           });
}

auto Day02::calculate_a() -> std::string {
    return std::to_string(spreadsheet_checksum(cells_));
}

auto Day02::calculate_b() -> std::string {
    return std::to_string(evenly_divisible_sum(cells_));
}
