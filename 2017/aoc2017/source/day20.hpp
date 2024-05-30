#pragma once

#include <cstdint>
#include <set>
#include <string>
#include <vector>

#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 20: Particle Swarm

// Position/velocity/aacceleration info for one dimension of a particle
class ParticleDim {
public:
    ParticleDim() = default;
    // NOLINTNEXTLINE: bugprone-easily-swappable-parameters
    explicit ParticleDim(int p, int v, int a)
        : p_(p), v_(v), a_(a) {}

    // Find directly where it would be at a certain time
    auto distance_at_time(int time) const -> int64_t;
    // Move the particle according to v/a for one tick
    auto tick() -> int64_t;

private:
    int64_t p_ = 0;
    int64_t v_ = 0;
    int64_t a_ = 0;
};

// Model a particle moving in three dimensions
class Particle {
public:
    explicit Particle(int pid, const std::string &line);

    auto pid() const -> int { return pid_; }
    auto manhattan_distance_at_time(int time) const -> int64_t;
    auto operator<(const Particle &other) const -> bool;

    // Move the particle and return its x/y/z position
    auto tick() -> std::tuple<int64_t, int64_t, int64_t>;

private:
    int pid_;
    ParticleDim x_;
    ParticleDim y_;
    ParticleDim z_;
};

auto parse_particles(const std::vector<std::string> &lines)
    -> std::vector<Particle>;
auto closest_particle_to_origin_long_run(const std::vector<Particle> &particles)
    -> int;
auto remaining_particles_after_collisions(
    const std::vector<Particle> &particles) -> int;

class Day20 : public Puzzle {
public:
    constexpr static auto DAY = 20;
    explicit Day20(PuzzleData puzzleData)
        : Puzzle(DAY, std::move(puzzleData)),
          particles_(parse_particles(input_lines())) {}

private:
    std::vector<Particle> particles_;
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
