#include <catch2/catch_test_macros.hpp>
#include <cstdint>
#include <string>
#include <tuple>
#include <vector>

#include "day20.hpp"

TEST_CASE("Particles near zero", "[days]") {
    const std::vector<std::string> input = {
        "p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>",
        "p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>",
    };
    auto particles = parse_particles(input);
    REQUIRE(closest_particle_to_origin_long_run(particles) == 0);
}

TEST_CASE("Particle movement", "[days]") {
    const std::string input = "p=<-6,0,0>, v=< 3,0,0>, a=< 0,0,0>";

    Particle part(1, input);
    part.tick();
    auto pos = part.tick();
    auto expected = std::tuple<int64_t, int64_t, int64_t>{0L, 0L, 0L};
    REQUIRE(pos == expected);
}

TEST_CASE("Particle collision", "[days]") {
    const std::vector<std::string> input = {
        "p=<-6,0,0>, v=< 3,0,0>, a=< 0,0,0>",
        "p=<-4,0,0>, v=< 2,0,0>, a=< 0,0,0>",
        "p=<-2,0,0>, v=< 1,0,0>, a=< 0,0,0>",
        "p=< 3,0,0>, v=<-1,0,0>, a=< 0,0,0>",
    };

    auto particles = parse_particles(input);
    REQUIRE(remaining_particles_after_collisions(particles) == 1);
}
