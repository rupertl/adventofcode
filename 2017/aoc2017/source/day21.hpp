#pragma once

#include <map>
#include <string>
#include <vector>

#include "puzzle.hpp"
#include "puzzle_data.hpp"
#include "point.hpp"

// Day 21:Fractal Art

// A block of nxn pixels (2 <= n <= 4) which can be repsented
// by a pattern of #/. or a bit vector of bools
class PixelBlock {
public:
    explicit PixelBlock(const std::string &pattern);
    explicit PixelBlock(const std::vector<bool> &bits);
    explicit PixelBlock(std::size_t size); // All pixels off
    auto pattern() const -> std::string;
    auto bits() const -> std::vector<bool>;
    auto operator<(const PixelBlock &other) const -> bool;

    auto operator==(const PixelBlock &other) const -> bool;

    auto rotate() const -> PixelBlock;
    auto flip() const -> PixelBlock;
    // Return all unique variants of block by flipping or rotating
    auto variants() const -> std::vector<PixelBlock>;

private:
    std::vector<bool> bits_;
    std::size_t side_ = 0U;
    void set_side();
};

// Copy a PixelBlock from a larger vector of bits.
// Cursor represents the block position, not the pixel position.
// So with a block side of 2, cursor (1, 0), find the block at pixels
// (2, 0) -> (3, 1)
auto block_copy(const std::vector<bool> &src, Point cursor,
                int blockSide, int wholeSide) -> PixelBlock;

// Paste a PixelBlock into a larger vector of bits. Cursor as above.
void block_paste(const PixelBlock &src, std::vector<bool> &dst, Point cursor,
                 int blockSide, int wholeSide);

// A large frame of pixels which we can repeatedly enlarge by using the rules.
class PixelFrame {
public:
    explicit PixelFrame(const std::vector<std::string> &ruleBook);
    auto count_bits_set() const -> int;
    // Find rule for a single block
    auto enhance(const PixelBlock &block) const -> PixelBlock;
    // Replace all blocks using rules
    void expand();

private:
    std::map<PixelBlock, PixelBlock> rules_;
    std::vector<bool> frame_;

    void parse_rule(const std::string &line);
};

class Day21 : public Puzzle {
public:
    constexpr static auto DAY = 21;
    explicit Day21(PuzzleData puzzleData)
        : Puzzle(DAY, std::move(puzzleData)) {}

private:
    auto calculate_a() -> std::string override;
    auto calculate_b() -> std::string override;
};
