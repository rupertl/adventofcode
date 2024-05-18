#include "day01.hpp"
#include "day02.hpp"
#include "day03.hpp"
#include "day04.hpp"
#include "day05.hpp"
#include "day06.hpp"
#include "day07.hpp"
#include "day08.hpp"
#include "day09.hpp"
#include "day10.hpp"
#include "day11.hpp"
#include "day12.hpp"
#include "day13.hpp"
#include "day14.hpp"
#include "day15.hpp"
#include "day16.hpp"
#include "day17.hpp"
#include "puzzle_data.hpp"

auto main() -> int {
    Day01(PuzzleData("data/full/01")).run();
    Day02(PuzzleData("data/full/02")).run();
    Day03(PuzzleData("data/full/03")).run();
    Day04(PuzzleData("data/full/04")).run();
    Day05(PuzzleData("data/full/05")).run();
    Day06(PuzzleData("data/full/06")).run();
    Day07(PuzzleData("data/full/07")).run();
    Day08(PuzzleData("data/full/08")).run();
    Day09(PuzzleData("data/full/09")).run();
    Day10(PuzzleData("data/full/10")).run();
    Day11(PuzzleData("data/full/11")).run();
    Day12(PuzzleData("data/full/12")).run();
    Day13(PuzzleData("data/full/13")).run();
    Day14(PuzzleData("data/full/14")).run();
    Day15(PuzzleData("data/full/15")).run();
    Day16(PuzzleData("data/full/16")).run();
    Day17(PuzzleData("data/full/17")).run();
    return 0;
}
