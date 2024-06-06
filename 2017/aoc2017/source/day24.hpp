#pragma once

#include <cstdint>
#include <map>
#include <string>
#include <vector>

#include "puzzle.hpp"
#include "puzzle_data.hpp"

// Day 24: Electromagnetic Moat

// A Component has two ports each with a number of pins, and a unique ID.
class Component {
public:
    // NOLINTNEXTLINE: bugprone-easily-swappable-parameters
    explicit Component(uint64_t cid, int port1, int port2)
        : cid_(cid), port1_(port1), port2_(port2) {}
    auto otherPort(int pins) const -> int {
        return port1_ == pins ? port2_ : port1_;
    }
    auto operator==(const Component &other) const -> bool {
        return other.cid_ == cid_;
    }
    auto cid() const -> uint64_t { return cid_; }
    auto strength() const -> int { return port1_ + port2_; }

private:
    uint64_t cid_;
    int port1_;
    int port2_;
};

// Compound results returned by find_best_bridges
struct BridgeResults {
    int strongest = 0;
    std::size_t longest_length = 0U;
    int longest_strength = 0;
};

// Map number of pins to multiple Components.
// The intent here is to add each Component twice, once for each end
// ie Component{id, 4, 10} -> Map{4, component} and Map{10, component}
using ComponentMap = std::multimap<int, Component>;

auto parse_components(const std::vector<std::string> &compNames)
    -> ComponentMap;
auto find_best_bridges(const ComponentMap &components) -> BridgeResults;

class Day24 : public Puzzle {
public:
    constexpr static auto DAY = 24;
    explicit Day24(PuzzleData puzzleData)
        : Puzzle(DAY, std::move(puzzleData)),
          components_(parse_components(input_lines())) {}

private:
    ComponentMap components_;
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
