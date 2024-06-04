#include <cmath>
#include <string>

#include "day23.hpp"
#include "duet.hpp"

auto Day23::calculate_a() -> std::string {
    Duet duet(input_lines());
    duet.run();
    return std::to_string(duet.mul_count());
}

// For part b we need to find an optimisation.

// Taking the code from my puzzle input (I expect constants will be
// different in other inputs) and converting to C.

// set b 79                   L01: b = 79
// set c b                         c = b
// jnz a 2                         if (! debug) goto L02
// jnz 1 5                         goto L08
// mul b 100                  L02: b *= 100
// sub b -100000                   b += 100000
// set c b                         c = b
// sub c -17000                    c += 17000
// set f 1                    L08: f = 1
// set d 2                         d = 2
// set e 2                    L05: e = 2
// set g d                    L03: g = d
// mul g e                         g *= e
// sub g b                         g -= b
// jnz g 2                         if (g != 0) goto L04
// set f 0                         f = 0
// sub e -1                   L04: e += 1
// set g e                         g = e
// sub g b                         g -= b
// jnz g -8                        if (g != 0) goto L03
// sub d -1                        d += 1
// set g d                         g = d
// sub g b                         g -= b
// jnz g -13                       if (g != 0) goto L05
// jnz f 2                         if (f != 0) goto L06
// sub h -1                        h += 1
// set g b                    L06: g = b
// sub g c                         g -= c
// jnz g 2                         if (g != 0) goto L07
// jnz 1 3                         goto END
// sub b -17                  L07: b += 17
// jnz 1 -23                       goto L08
//                            END:

// Then combine artithmetic operations and convert jumps to loops.

// auto coprocessor() -> int {
//     int b = 0, c = 0, d = 0, e = 0,
//         f = 0, g = 0, h = 0;
//     b = 107900;
//     c = b + 17000;
//     do {
//         f = 1;
//         d = 2;
//         do {
//             e = 2;
//             do {
//                 g = (d * e) - b;
//                 if (g == 0) {
//                     f = 0;
//                 }
//                 e += 1;
//                 g = e - b;
//             } while (g != 0);
//             d += 1;
//             g = d - b;
//         } while (g != 0);
//         if (f == 0) {
//             h += 1;
//         }
//         g = b - c;
//         b += 17;
//     } while (g != 0);
//     return h;
// }

// We can see b is looping from 107900 to 107900+17000 in increments of 17.
// On each loop, h gets incremented if f == 0.
// What are the inner two do loops doing to set this?
// It looks like d is being looped from 2 to b, and in the inner loop e
// is being loooped from 2 to b. If d * e == b then f is set to 0.
// Put another way, if b can be factorised (ie is not prime) then set f to 0.
// However, these loops are very inefficient as they try all combinations
// 2..b * 2..b and do not exit early once they have found that b is not prime.

// So this can be replaced by the following, using a basic is_prime check

auto is_prime(int num) -> bool {
    if (num < 2) {
        return false;
    }
    if (num == 2) {
        return true;
    }
    if (num % 2 == 0) {
        return false;
    }
    const auto limit = static_cast<int>(std::sqrt(num));
    for (int i = 3; i <= limit; i += 2) {
        if (num % i == 0) {
            return false;
        }
    }
    return true;
}

auto coprocessor_optimised() -> int {
    constexpr auto START = 107900;
    constexpr auto STEP = 17;
    constexpr auto END = START + (STEP * 1000);

    auto count = 0;
    for (auto num = START; num <= END; num += STEP) {
        if (! is_prime(num)) {
            ++count;
        }
    }
    return count;
}

auto Day23::calculate_b() -> std::string {
    return std::to_string(coprocessor_optimised());
}
