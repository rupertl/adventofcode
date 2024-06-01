#include <catch2/catch_test_macros.hpp>
#include <algorithm>
#include <iterator>
#include <string>
#include <vector>

#include "day21.hpp"
#include "point.hpp"

TEST_CASE("PixelBlocks bits<->patterns", "[days]") {
    std::string pat4 = ".#.#";
    std::vector<bool> bits4{false, true,
                            false, true};
    REQUIRE(PixelBlock(pat4).pattern() == pat4);
    REQUIRE(PixelBlock(pat4).bits() == bits4);
    REQUIRE(PixelBlock(bits4).pattern() == pat4);
    REQUIRE(PixelBlock(bits4).bits() == bits4);

    std::string pat9 = ".#...####";
    std::vector<bool> bits9{false, true, false,
                            false, false, true,
                            true, true, true};
    REQUIRE(PixelBlock(pat9).pattern() == pat9);
    REQUIRE(PixelBlock(pat9).bits() == bits9);
    REQUIRE(PixelBlock(bits9).pattern() == pat9);
    REQUIRE(PixelBlock(bits9).bits() == bits9);
}

TEST_CASE("PixelBlocks rotates", "[days]") {
    const std::string pat4 = ".#.#";
    std::string pat4Rotated1 = "..##";
    auto once = PixelBlock(pat4).rotate();
    REQUIRE(once.pattern() == pat4Rotated1);

    std::string pat4Rotated2 = "#.#.";
    auto twice = once.rotate();
    REQUIRE(twice.pattern() == pat4Rotated2);
}

TEST_CASE("PixelBlocks flip", "[days]") {
    const std::string pat4 =  "##..";
    std::string pat4Flipped = "..##";
    auto once = PixelBlock(pat4).flip();
    REQUIRE(once.pattern() == pat4Flipped);
}

TEST_CASE("PixelBlocks variants", "[days]") {
    const std::string pat9 = ".#...####";
    auto var = PixelBlock(pat9).variants();
    REQUIRE(var.size() == 8);

    std::vector<std::string> expected = {
        "..##.#.##",
        ".#...####",
        ".#.#..###",
        ".###.#..#",
        "#..#.###.",
        "##.#.##..",
        "###..#.#.",
        "####...#."
    };
    std::vector<std::string> actual;
    std::transform(var.begin(), var.end(), std::back_inserter(actual),
                   [](const PixelBlock &pib) { return pib.pattern(); });
    REQUIRE(actual == expected);
}

TEST_CASE("Block copy and paste", "[days]") {
    constexpr auto BLOCK_SIDE = 2;
    constexpr auto FRAME_SIDE = 4;
    constexpr auto FRAME_AREA = FRAME_SIDE * FRAME_SIDE;
    auto original = std::vector<bool>{false, false, true,  true,
                                      true,  true,  false, false,
                                      true,  false, false, false,
                                      false, true,  true,  false};
    auto corner00 = PixelBlock{std::vector<bool>
                                     {false, false,
                                      true,  true}};
    auto corner01 = PixelBlock{std::vector<bool>    {true,  true,
                                                     false, false}};
    auto corner10 = PixelBlock{std::vector<bool>
                                      {true,  false,
                                       false, true}};
    auto corner11 = PixelBlock{std::vector<bool>    {false, false,
                                                     true,  false}};

    REQUIRE(block_copy(original, Point{0, 0},
                       BLOCK_SIDE, FRAME_SIDE) == corner00);
    REQUIRE(block_copy(original, Point{0, 1},
                       BLOCK_SIDE, FRAME_SIDE) == corner01);
    REQUIRE(block_copy(original, Point{1, 0},
                       BLOCK_SIDE, FRAME_SIDE) == corner10);
    REQUIRE(block_copy(original, Point{1, 1},
                       BLOCK_SIDE, FRAME_SIDE) == corner11);

    auto recreated = std::vector<bool>(FRAME_AREA, false);
    block_paste(corner00, recreated, Point{0, 0}, BLOCK_SIDE, FRAME_SIDE);
    block_paste(corner01, recreated, Point{0, 1}, BLOCK_SIDE, FRAME_SIDE);
    block_paste(corner10, recreated, Point{1, 0}, BLOCK_SIDE, FRAME_SIDE);
    block_paste(corner11, recreated, Point{1, 1}, BLOCK_SIDE, FRAME_SIDE);
    REQUIRE(recreated == original);
}

TEST_CASE("PixelFrame rules", "[days]") {
    const std::vector<std::string> rules = {
        "../.# => ##./#../...",
        ".#./..#/### => #..#/..../..../#..#"
    };
    const PixelFrame pfr(rules);

    REQUIRE(pfr.enhance(PixelBlock("...#")).pattern() == "##.#.....");
    REQUIRE(pfr.enhance(PixelBlock("..#.")).pattern() == "##.#.....");
}

TEST_CASE("PixelFrame expand", "[days]") {
    const std::vector<std::string> rules = {
        "../.# => ##./#../...",
        ".#./..#/### => #..#/..../..../#..#"
    };
    PixelFrame pfr(rules);
    REQUIRE(pfr.count_bits_set() == 5);

    pfr.expand();
    REQUIRE(pfr.count_bits_set() == 4);

    pfr.expand();
    REQUIRE(pfr.count_bits_set() == 12);
}
