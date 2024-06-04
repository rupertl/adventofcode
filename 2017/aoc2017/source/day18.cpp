#include <string>
#include <vector>

#include "day18.hpp"
#include "duet.hpp"

auto count_sent_duet_1(const std::vector<std::string> &source) -> int {
    DuetChannel dch;
    Duet duet0(source, 0, &dch);
    Duet duet1(source, 1, &dch);

    // Run until both programs are terminated or deadlocked
    while (true) {
        duet0.run();
        duet1.run();
        if (! dch.can_any_receive() ||
            (duet0.terminated() && duet1.terminated())) {
            return duet1.count_sent();
        }
    }
}

auto Day18::calculate_a() -> std::string {
    Duet crd(input_lines());
    crd.run();
    return std::to_string(crd.recovered_sound());
}

auto Day18::calculate_b() -> std::string {
    return std::to_string(count_sent_duet_1(input_lines()));
}
