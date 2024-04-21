cmake_minimum_required(VERSION 3.14)

include(cmake/prelude.cmake)

project(
    aoc2017
    VERSION 0.1.0
    DESCRIPTION "Advent of Code solutions for 2017"
    HOMEPAGE_URL "https://github.com/rupertl/adventofcode"
    LANGUAGES CXX
)

include(cmake/project-is-top-level.cmake)
include(cmake/variables.cmake)

# ---- Declare library ----

add_library(
    aoc2017_lib OBJECT
    source/parser.cpp
    source/puzzle.cpp
    source/puzzle_data.cpp
    source/day01.cpp
    source/day02.cpp
    source/day03.cpp
    source/day04.cpp
    source/day05.cpp
    source/day06.cpp
    source/day07.cpp
    source/day08.cpp
    source/day09.cpp
)

target_include_directories(
    aoc2017_lib ${warning_guard}
    PUBLIC
    "\$<BUILD_INTERFACE:${PROJECT_SOURCE_DIR}/source>"
)

target_compile_features(aoc2017_lib PUBLIC cxx_std_17)

find_package(fmt REQUIRED)
target_link_libraries(aoc2017_lib PRIVATE fmt::fmt)

# ---- Declare executable ----

add_executable(aoc2017_exe source/main.cpp)
add_executable(aoc2017::exe ALIAS aoc2017_exe)

set_property(TARGET aoc2017_exe PROPERTY OUTPUT_NAME aoc2017)

target_compile_features(aoc2017_exe PRIVATE cxx_std_17)

target_link_libraries(aoc2017_exe PRIVATE aoc2017_lib)

# ---- Install rules ----

if(NOT CMAKE_SKIP_INSTALL_RULES)
  include(cmake/install-rules.cmake)
endif()

# ---- Developer mode ----

if(NOT aoc2017_DEVELOPER_MODE)
  return()
elseif(NOT PROJECT_IS_TOP_LEVEL)
  message(
      AUTHOR_WARNING
      "Developer mode is intended for developers of aoc2017"
  )
endif()

include(cmake/dev-mode.cmake)
