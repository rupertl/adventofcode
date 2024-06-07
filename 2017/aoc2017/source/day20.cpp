#include <algorithm>
#include <cassert>
#include <cstdint>
#include <cstdlib>
#include <iterator>
#include <map>
#include <set>
#include <string>
#include <tuple>
#include <vector>

#include "day20.hpp"
#include "parser.hpp"

auto ParticleDim::distance_at_time(int time) const -> int64_t {
    // p = vt + ½at² but t² -> t(t+1) due to puzzle rules
    // Integer result for simplicity
    return p_ + (v_ * time) + ((a_ * time * (time + 1)) / 2);
}

auto ParticleDim::tick() -> int64_t {
    v_ += a_;
    p_ += v_;
    return p_;
}

Particle::Particle(int pid, const std::string &line)
    : pid_(pid) {
    // Parse line like
    // p=<1802,2648,674>, v=<262,377,95>, a=<-22,-30,-3>
    // Use a regex to get 9 signed numbers
    const auto * const regex = "[-0-9]+";
    auto words = parse_regex(line, regex);
    assert(words.size() == 9U);

    constexpr std::size_t BASE_P{0U};
    constexpr std::size_t BASE_V{3U};
    constexpr std::size_t BASE_A{6U};

    x_ = ParticleDim{std::stoi(words[BASE_P]),
                     std::stoi(words[BASE_V]),
                     std::stoi(words[BASE_A])};
    y_ = ParticleDim{std::stoi(words[BASE_P + 1U]),
                     std::stoi(words[BASE_V + 1U]),
                     std::stoi(words[BASE_A + 1U])};
    z_ = ParticleDim{std::stoi(words[BASE_P + 2U]),
                     std::stoi(words[BASE_V + 2U]),
                     std::stoi(words[BASE_A + 2U])};
}

auto Particle::manhattan_distance_at_time(int time) const -> int64_t {
    return labs(x_.distance_at_time(time)) +
           labs(y_.distance_at_time(time)) +
           labs(z_.distance_at_time(time));
}

auto Particle::operator<(const Particle &other) const -> bool {
    return pid_ < other.pid();
}

auto Particle::tick() -> std::tuple<int64_t, int64_t, int64_t> {
    return {x_.tick(), y_.tick(),z_.tick()};
}

auto parse_particles(const std::vector<std::string> &lines)
    -> std::vector<Particle> {
    std::vector<Particle> particles;
    auto pid = 0;
    std::transform(lines.begin(), lines.end(), std::back_inserter(particles),
                   [&](const std::string &line) {
                       return Particle{pid++, line};
                   });
    return particles;
}

auto closest_particle_to_origin_long_run(const std::vector<Particle> &particles)
    -> int {
    constexpr auto LONG_RUN = 1000;
    auto closest =
        std::min_element(particles.begin(), particles.end(),
                         [=](const Particle &pa1, const Particle &pa2) {
                             return pa1.manhattan_distance_at_time(LONG_RUN) <
                                    pa2.manhattan_distance_at_time(LONG_RUN);
                         });
    return closest->pid();
}

auto remaining_particles_after_collisions(const std::vector<Particle> &particles) -> int {
    // Copy particles so we can change it
    std::vector<Particle> pars = particles;

    // Number of ticks to look for collisions
    constexpr auto ITERATIONS = 100U;

    for (auto i = 0U; i < ITERATIONS && pars.size() > 1U; ++i) {
        // Map position to particle ID
        std::map<std::tuple<int64_t, int64_t, int64_t>, int> positions;

        // Particles we want to remove
        std::set<int> collisions;

        // Update particle and find all those at the same position
        for (auto &particle : pars) {
            auto pos = particle.tick();

            auto already = positions.find(pos);
            if (already == positions.end()) {
                // First at this position
                positions[pos] = particle.pid();
            } else {
                // Already a particle there. Put existing and this
                // particle on the list to remove. Keep position map
                // in case another particle ends up here.
                collisions.insert(already->second);
                collisions.insert(particle.pid());
            }
        }

        // Keep going if nothing to remove
        if (collisions.empty()) {
            continue;
        }

        // Remove the collided particles
        auto eraseFrom =
            std::remove_if(pars.begin(), pars.end(), [=](const Particle &par) {
                return collisions.find(par.pid()) != collisions.end();
            });
        pars.erase(eraseFrom, pars.end());
    }

    return static_cast<int>(pars.size());
}

auto Day20::calculate_a() -> std::string {
    return std::to_string(closest_particle_to_origin_long_run(particles_));
}

auto Day20::calculate_b() -> std::string {
    return std::to_string(remaining_particles_after_collisions(particles_));
}
