# aoc2017

C++ solutions for Advent of Code 2017.

# Technologies used

- C++17
- cmake
- [cmake-init](https://github.com/friendlyanon/cmake-init) to start the project
- [conan](https://conan.io/) for packages
- [catch2](https://github.com/catchorg/Catch2) for tests

In theory this should all be cross-platform, but I have only tested on Linux.

# Building and installing

See the [BUILDING](aoc2017/BUILDING.md) document.

You will need to populate data and solutions from your instance of
Advent of Code. Create directories such as `data/full/NN` where `NN`
is the day (01-26) and save files such as:

- `input` - the provided input to the puzzle
- `solution.a` - solution to part 1
- `solution.b` - solution to part 2

Once data has been provided, simply run the executable `aoc2017` which
will run all puzzles.

# Development and testing

See the [HACKING](aoc2017HACKING.md) document.

## License

Copyright © 2024 Rupert Lane

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
