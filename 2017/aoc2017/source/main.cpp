#include <string>

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
#include "day18.hpp"
#include "day19.hpp"
#include "day20.hpp"
#include "day21.hpp"
#include "day22.hpp"
#include "day23.hpp"
#include "day24.hpp"
#include "day25.hpp"
#include "puzzle_data.hpp"

auto get_data(int day) -> PuzzleData {
    const std::string dir = (day < 10) ? "data/full/0" : "data/full/";
    return PuzzleData(dir + std::to_string(day));
}

auto main() -> int {
    int day = 1;
    Day01(get_data(day++)).run();
    Day02(get_data(day++)).run();
    Day03(get_data(day++)).run();
    Day04(get_data(day++)).run();
    Day05(get_data(day++)).run();
    Day06(get_data(day++)).run();
    Day07(get_data(day++)).run();
    Day08(get_data(day++)).run();
    Day09(get_data(day++)).run();
    Day10(get_data(day++)).run();
    Day11(get_data(day++)).run();
    Day12(get_data(day++)).run();
    Day13(get_data(day++)).run();
    Day14(get_data(day++)).run();
    Day15(get_data(day++)).run();
    Day16(get_data(day++)).run();
    Day17(get_data(day++)).run();
    Day18(get_data(day++)).run();
    Day19(get_data(day++)).run();
    Day20(get_data(day++)).run();
    Day21(get_data(day++)).run();
    Day22(get_data(day++)).run();
    Day23(get_data(day++)).run();
    Day24(get_data(day++)).run();
    Day25(get_data(day++)).run();
    return 0;
}
