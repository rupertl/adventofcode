#include <catch2/catch_test_macros.hpp>
#include <string>
#include <utility>
#include "puzzle.hpp"
#include "puzzle_data.hpp"

class TestPuzzle : public Puzzle {
public:
    explicit TestPuzzle(PuzzleData puzzleData, bool forceFail)
        : Puzzle(0, std::move(puzzleData)), _forceFail(forceFail) {}

private:
    bool _forceFail;
    auto calculate_a() -> std::string override {
        if (_forceFail) {
            return "42";
        }
        auto result = 0;
        for (const auto &line : input_lines()) {
            result += std::stoi(line);
        }
        return std::to_string(result);
    }
    auto calculate_b() -> std::string override { return "unimplemented"; }
};

TEST_CASE("TestPuzzle with correct answer", "[library]") {
    auto testPuzzle = TestPuzzle(PuzzleData("data/sample/00"),
                                 /* forceFail */ false);
    testPuzzle.calculate();
    REQUIRE(testPuzzle.report(SolutionPart::Type::A) == "15 ✔");
    REQUIRE(testPuzzle.report(SolutionPart::Type::B) ==
            "unimplemented (unsubmitted)");
}

TEST_CASE("TestPuzzle with wrong answer", "[library]") {
    auto testPuzzle = TestPuzzle(PuzzleData("data/sample/00"),
                                 /* forceFail */ true);
    testPuzzle.calculate();
    REQUIRE(testPuzzle.report(SolutionPart::Type::A) == "42 ✗ (15)");
}
