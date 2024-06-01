#include <algorithm>
#include <cmath>
#include <cstddef>
#include <iterator>
#include <set>
#include <stdexcept>
#include <string>
#include <utility>
#include <vector>

#include "day21.hpp"
#include "parser.hpp"
#include "point.hpp"

constexpr auto PIXEL_ON = '#';
constexpr auto PIXEL_OFF = '.';

void PixelBlock::set_side() {
    side_ = static_cast<std::size_t>(std::sqrt(bits_.size()));
    // Must be square
    if (side_ * side_ != bits_.size()) {
        throw std::runtime_error("Block is not square");
    }
}

PixelBlock::PixelBlock(const std::string &pattern) {
    for (auto chr : pattern) {
        bits_.push_back(chr == PIXEL_ON);
    }
    set_side();
}

PixelBlock::PixelBlock(const std::vector<bool> &bits)
    : bits_(bits) {
    set_side();
 }

PixelBlock::PixelBlock(std::size_t size)
    : bits_(size, false) {
    set_side();
 }

auto PixelBlock::pattern() const -> std::string {
    std::string out;
    std::transform(bits_.begin(), bits_.end(),
                   std::back_inserter(out),
                   [](bool bit){ return bit ? PIXEL_ON : PIXEL_OFF; });
    return out;
}

auto PixelBlock::bits() const -> std::vector<bool> {
    return bits_;
}

auto PixelBlock::operator<(const PixelBlock &other) const -> bool {
    return bits_ < other.bits_;
}

auto PixelBlock::operator==(const PixelBlock &other) const -> bool {
    return bits_ == other.bits_;
}

// Rotate 90°
// This and the next one could probably be simplified into
// common code + lambda.
auto PixelBlock::rotate() const -> PixelBlock {
    std::vector<bool> next(bits_.size(), false);
    for (auto row = 0U; row < side_; ++row) {
        for (auto col = 0U; col < side_; ++col) {
            auto src = (row * side_) + col;
            auto dst = (col * side_) + (side_ - 1 - row);
            next[dst] = bits_[src];
        }
    }

    return PixelBlock{next};
}

// Flip vertically
auto PixelBlock::flip() const -> PixelBlock {
    std::vector<bool> next(bits_.size(), false);
    for (auto row = 0U; row < side_; ++row) {
        for (auto col = 0U; col < side_; ++col) {
            auto src = (row * side_) + col;
            auto dst = ((side_ - 1 - row) * side_) + col;
            next[dst] = bits_[src];
        }
    }

    return PixelBlock{next};
}

auto PixelBlock::variants() const -> std::vector<PixelBlock> {
    std::set<PixelBlock> variants;
    auto next = *this;

    for (auto i = 0U; i < 4; ++i) {
        variants.insert(next);
        auto flipped = next.flip();
        variants.insert(flipped);
        next = next.rotate();
    }

    // Remove duplicates
    std::vector<PixelBlock> out;
    std::copy(variants.begin(), variants.end(), std::back_inserter(out));
    return out;
}

auto block_copy(const std::vector<bool> &src, Point cursor,
                // NOLINTNEXTLINE: bugprone-easily-swappable-parameters
                int blockSide, int wholeSide) -> PixelBlock {
    std::vector<bool> result;
    for (int row = cursor.row * blockSide;
         row < (cursor.row + 1) * blockSide; ++row) {
        for (int col = cursor.col * blockSide;
             col < (cursor.col + 1) * blockSide; ++col) {
            auto loc = (row * wholeSide) + col;
            result.push_back(src[static_cast<std::size_t>(loc)]);
        }
    }
    return PixelBlock{result};
}


void block_paste(const PixelBlock &src, std::vector<bool> &dst, Point cursor,
                 // NOLINTNEXTLINE: bugprone-easily-swappable-parameters
                 int blockSide, int wholeSide) {
    auto bits = src.bits();
    auto itr = bits.begin();
    for (int row = cursor.row * blockSide;
         row < (cursor.row + 1) * blockSide; ++row) {
        for (int col = cursor.col * blockSide;
             col < (cursor.col + 1) * blockSide; ++col) {
            auto loc = (row * wholeSide) + col;
            dst[static_cast<std::size_t>(loc)] = *itr++;
        }
    }
}

constexpr auto START_BLOCK = ".#...####";

PixelFrame::PixelFrame(const std::vector<std::string> &ruleBook) :
    frame_(PixelBlock{START_BLOCK}.bits()) {
    for (const auto &line : ruleBook) {
        parse_rule(line);
    }
}

void PixelFrame::parse_rule(const std::string &line) {
    // Line looks like:
    // ../.# => ##./#../...
    std::string noSlash;
    std::copy_if(line.begin(), line.end(), std::back_inserter(noSlash),
                 [](char chr) { return chr != '/'; });

    auto words = parse_row<std::string>(noSlash);

    if (words.size() != 3) {
        throw std::runtime_error("Error parsing rules");
    }
    auto src = PixelBlock{words[0]};
    auto dst = PixelBlock{words[2]};

    for (const auto &variant : src.variants()) {
        rules_.insert({variant, dst});
    }
}

auto PixelFrame::count_bits_set() const -> int {
    return static_cast<int>(std::count(frame_.begin(), frame_.end(), true));
}

auto PixelFrame::enhance(const PixelBlock &block) const -> PixelBlock {
    auto itr = rules_.find(block);
    if (itr != rules_.end()) {
        return itr->second;
    }
    // Every block must have an enhancement in order to expand to the
    // new size
    throw std::runtime_error("No enhance rule found for " + block.pattern());
}

// Yield source and destination block sides for given frame side
auto block_sides(int frameSide) -> std::pair<int, int> {
    if (frameSide % 2 == 0) {
        return {2, 3};
    }
    if (frameSide % 3 == 0) {
        return {3, 4};
    }
    throw std::runtime_error("Imvalid block size for expansion");
}

void PixelFrame::expand() {
    // Side length of the whole source frame
    auto srcFrameSide = static_cast<int>(std::sqrt(frame_.size()));
    // Block size of source and destination frame
    auto [srcBlockSide, dstBlockSide] = block_sides(srcFrameSide);
    // How many blocks on each side of each frame
    auto blocksPerSide = srcFrameSide / srcBlockSide;
    // Side length of the whole destination frame
    auto dstFrameSide = blocksPerSide * dstBlockSide;
    // Prepare a replacement frame
    auto dstFrameArea = dstFrameSide * dstFrameSide;
    std::vector<bool> dst(static_cast<std::size_t>(dstFrameArea), false);

    // Copy blocks from source, enhance and paste into new frame
    Point cursor{0, 0};
    while (cursor.row < blocksPerSide) {
        while (cursor.col < blocksPerSide) {
            auto src = block_copy(frame_, cursor, srcBlockSide, srcFrameSide);
            auto enhanced = enhance(src);
            block_paste(enhanced, dst, cursor, dstBlockSide, dstFrameSide);
            cursor.col++;
        }
        cursor.col = 0;
        cursor.row++;
    }

    // Replace frame contents
    frame_.swap(dst);
}

auto count_bits_after_expansions(const std::vector<std::string> &ruleBook,
                                 int iterations) -> std::string {
    PixelFrame pfr(ruleBook);
    for (int i = 0; i < iterations; ++i) {
        pfr.expand();
    }
    return std::to_string(pfr.count_bits_set());
}

auto Day21::calculate_a() -> std::string {
    constexpr auto ITERATIONS = 5;
    return count_bits_after_expansions(input_lines(), ITERATIONS);
}

auto Day21::calculate_b() -> std::string {
    constexpr auto ITERATIONS = 18;
    return count_bits_after_expansions(input_lines(), ITERATIONS);
}
