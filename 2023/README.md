# adventofcode 2023

Python solutions to [Advent of Code 2023](http://adventofcode.com/2023).

## Running

The puzzle solutions were written using Python 3.11 and use the
standard library except:

* Day 24 needs [Z3](https://github.com/Z3Prover/z3)
* Day 25 needs [networkx](https://networkx.org/)

These can be installed via:

```
 python3 -m venv venv
 source venv/bin/activate
 pip install -r requirements.txt
```

You will need to provide input data and (if known) solutions to each
puzzle, as this differs for each user. Create files named `input`,
`solution.a` and `solution.b` in the `data/full/NN` directory where
`NN` is the day number. See `data/full/00` for an example.

After this is done, simply run `./aoc.py` from this directory to
execute all puzzles. You can optionally provide a number as a command
line argument to just run one day.

## Tests

Inside the venv, run `pytest`. This uses the sample data provided in
the puzzle text, which has been placed in `data/sample/`.

## License

Copyright © 2023-4 Rupert Lane

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
