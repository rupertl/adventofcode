#include "day01.hpp"
#include "day02.hpp"
#include "day03.hpp"
#include "day04.hpp"
#include "day05.hpp"
#include "day06.hpp"
#include "puzzle_data.hpp"

auto main() -> int {
    Day01(PuzzleData("data/full/01")).run();
    Day02(PuzzleData("data/full/02")).run();
    Day03(PuzzleData("data/full/03")).run();
    Day04(PuzzleData("data/full/04")).run();
    Day05(PuzzleData("data/full/05")).run();
    Day06(PuzzleData("data/full/06")).run();
    return 0;
}
