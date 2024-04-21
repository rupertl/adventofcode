# Parent project does not export its library target, so this CML implicitly
# depends on being added from it, i.e. the testing is done only from the build
# tree and is not feasible from an install location

project(aoc2017Tests LANGUAGES CXX)

# ---- Dependencies ----

find_package(Catch2 REQUIRED)
include(Catch)

# ---- Tests ----

add_executable(aoc2017_test
    source/parser_test.cpp
    source/puzzle_data_test.cpp
    source/puzzle_test.cpp
    source/day01_test.cpp
    source/day02_test.cpp
    source/day03_test.cpp
    source/day04_test.cpp
    source/day05_test.cpp
    source/day06_test.cpp
    source/day07_test.cpp
    source/day08_test.cpp
    source/day09_test.cpp
)
target_link_libraries(
    aoc2017_test PRIVATE
    aoc2017_lib
    Catch2::Catch2WithMain
)
target_compile_features(aoc2017_test PRIVATE cxx_std_17)

catch_discover_tests(aoc2017_test)

# ---- End-of-file commands ----

add_folders(Test)
