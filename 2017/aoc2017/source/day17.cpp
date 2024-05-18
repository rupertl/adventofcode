#include <cstddef>
#include <string>
#include <vector>

#include "day17.hpp"

void Spinlock::spin(int times) {
    for (auto i = 0; i < times; ++i) {
        const auto size = static_cast<int>(buffer_.size());
        position_ = ((position_ + jump_size_) % size) + 1;
        buffer_.insert(buffer_.begin() + position_, size);
    }
}

auto Spinlock::next() const -> int {
    return buffer_[static_cast<std::size_t>(position_ + 1) % buffer_.size()];
}

auto Spinlock::after_zero() const -> int {
    if (buffer_.size() <= 1) {
        return 0;
    }
    // By inspection the zero is always the first item
    return buffer_[1];
}

// NOLINTNEXTLINE: bugprone-easily-swappable-parameters
auto predict_spinlock_after_zero(int jumpSize, int times) -> int {
    // Using the insight that the item after zero is always in the
    // first slot of the buffer, we don't actually need to manipulate
    // the buffer at all. Just work out what the position will be each
    // time and if we are at 1, rememver that value.
    auto latestAfterZero = 0;
    auto pos = 0;
    for (auto i = 1; i <= times; ++i) {
        pos = ((pos + jumpSize) % i) + 1;
        if (pos == 1) {
            latestAfterZero = i;
        }
    }
    return latestAfterZero;
}

auto Day17::calculate_a() -> std::string {
    Spinlock spl(steps_);
    spl.spin(Spinlock::TARGET_PART_1);
    return std::to_string(spl.next());
}

auto Day17::calculate_b() -> std::string {
    return std::to_string(
        predict_spinlock_after_zero(steps_, Spinlock::TARGET_PART_2));
}
