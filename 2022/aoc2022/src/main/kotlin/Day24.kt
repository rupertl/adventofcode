// Advent of Code 2022 - Day 24: Blizzard Basin

// PART 1

// With everything replanted for next year (and with elephants and
// monkeys to tend the grove), you and the Elves leave for the
// extraction point.

// Partway up the mountain that shields the grove is a flat, open area
// that serves as the extraction point. It's a bit of a climb, but
// nothing the expedition can't handle.

// At least, that would normally be true; now that the mountain is
// covered in snow, things have become more difficult than the Elves
// are used to.

// As the expedition reaches a valley that must be traversed to reach
// the extraction site, you find that strong, turbulent winds are
// pushing small blizzards of snow and sharp ice around the valley.
// It's a good thing everyone packed warm clothes! To make it across
// safely, you'll need to find a way to avoid them.

// Fortunately, it's easy to see all of this from the entrance to the
// valley, so you make a map of the valley and the blizzards (your
// puzzle input). For example:

// #.#####
// #.....#
// #>....#
// #.....#
// #...v.#
// #.....#
// #####.#

// The walls of the valley are drawn as #; everything else is ground.
// Clear ground - where there is currently no blizzard - is drawn as
// .. Otherwise, blizzards are drawn with an arrow indicating their
// direction of motion: up (^), down (v), left (<), or right (>).

// The above map includes two blizzards, one moving right (>) and one
// moving down (v). In one minute, each blizzard moves one position in
// the direction it is pointing:

// #.#####
// #.....#
// #.>...#
// #.....#
// #.....#
// #...v.#
// #####.#

// Due to conservation of blizzard energy, as a blizzard reaches the
// wall of the valley, a new blizzard forms on the opposite side of
// the valley moving in the same direction. After another minute, the
// bottom downward-moving blizzard has been replaced with a new
// downward-moving blizzard at the top of the valley instead:

// #.#####
// #...v.#
// #..>..#
// #.....#
// #.....#
// #.....#
// #####.#

// Because blizzards are made of tiny snowflakes, they pass right
// through each other. After another minute, both blizzards
// temporarily occupy the same position, marked 2:

// #.#####
// #.....#
// #...2.#
// #.....#
// #.....#
// #.....#
// #####.#

// After another minute, the situation resolves itself, giving each
// blizzard back its personal space:

// #.#####
// #.....#
// #....>#
// #...v.#
// #.....#
// #.....#
// #####.#

// Finally, after yet another minute, the rightward-facing blizzard on
// the right is replaced with a new one on the left facing the same
// direction:

// #.#####
// #.....#
// #>....#
// #.....#
// #...v.#
// #.....#
// #####.#

// This process repeats at least as long as you are observing it, but
// probably forever.

// Here is a more complex example:

// #.######
// #>>.<^<#
// #.<..<<#
// #>v.><>#
// #<^v^^>#
// ######.#

// Your expedition begins in the only non-wall position in the top row
// and needs to reach the only non-wall position in the bottom row. On
// each minute, you can move up, down, left, or right, or you can wait
// in place. You and the blizzards act simultaneously, and you cannot
// share a position with a blizzard.

// In the above example, the fastest way to reach your goal requires
// 18 steps. Drawing the position of the expedition as E, one way to
// achieve this is:

// Initial state:
// #E######
// #>>.<^<#
// #.<..<<#
// #>v.><>#
// #<^v^^>#
// ######.#

// Minute 1, move down:
// #.######
// #E>3.<.#
// #<..<<.#
// #>2.22.#
// #>v..^<#
// ######.#

// Minute 2, move down:
// #.######
// #.2>2..#
// #E^22^<#
// #.>2.^>#
// #.>..<.#
// ######.#

// Minute 3, wait:
// #.######
// #<^<22.#
// #E2<.2.#
// #><2>..#
// #..><..#
// ######.#

// Minute 4, move up:
// #.######
// #E<..22#
// #<<.<..#
// #<2.>>.#
// #.^22^.#
// ######.#

// Minute 5, move right:
// #.######
// #2Ev.<>#
// #<.<..<#
// #.^>^22#
// #.2..2.#
// ######.#

// Minute 6, move right:
// #.######
// #>2E<.<#
// #.2v^2<#
// #>..>2>#
// #<....>#
// ######.#

// Minute 7, move down:
// #.######
// #.22^2.#
// #<vE<2.#
// #>>v<>.#
// #>....<#
// ######.#

// Minute 8, move left:
// #.######
// #.<>2^.#
// #.E<<.<#
// #.22..>#
// #.2v^2.#
// ######.#

// Minute 9, move up:
// #.######
// #<E2>>.#
// #.<<.<.#
// #>2>2^.#
// #.v><^.#
// ######.#

// Minute 10, move right:
// #.######
// #.2E.>2#
// #<2v2^.#
// #<>.>2.#
// #..<>..#
// ######.#

// Minute 11, wait:
// #.######
// #2^E^2>#
// #<v<.^<#
// #..2.>2#
// #.<..>.#
// ######.#

// Minute 12, move down:
// #.######
// #>>.<^<#
// #.<E.<<#
// #>v.><>#
// #<^v^^>#
// ######.#

// Minute 13, move down:
// #.######
// #.>3.<.#
// #<..<<.#
// #>2E22.#
// #>v..^<#
// ######.#

// Minute 14, move right:
// #.######
// #.2>2..#
// #.^22^<#
// #.>2E^>#
// #.>..<.#
// ######.#

// Minute 15, move right:
// #.######
// #<^<22.#
// #.2<.2.#
// #><2>E.#
// #..><..#
// ######.#

// Minute 16, move right:
// #.######
// #.<..22#
// #<<.<..#
// #<2.>>E#
// #.^22^.#
// ######.#

// Minute 17, move down:
// #.######
// #2.v.<>#
// #<.<..<#
// #.^>^22#
// #.2..2E#
// ######.#

// Minute 18, move down:
// #.######
// #>2.<.<#
// #.2v^2<#
// #>..>2>#
// #<....>#
// ######E#

// What is the fewest number of minutes required to avoid the
// blizzards and reach the goal?

// PART 2

// As the expedition reaches the far side of the valley, one of the
// Elves looks especially dismayed:

// He forgot his snacks at the entrance to the valley!

// Since you're so good at dodging blizzards, the Elves humbly request
// that you go back for his snacks. From the same initial conditions,
// how quickly can you make it from the start to the goal, then back
// to the start, then back to the goal?

// In the above example, the first trip to the goal takes 18 minutes,
// the trip back to the start takes 23 minutes, and the trip back to
// the goal again takes 13 minutes, for a total time of 54 minutes.

// What is the fewest number of minutes required to reach the goal, go
// back to the start, then reach the goal again?

enum class BlizzardDirection(val code: Char) {
    UP('^'),
    DOWN('v'),
    LEFT('<'),
    RIGHT('>');

    companion object {
        fun from(value: Char): BlizzardDirection =
            BlizzardDirection.values().first { it.code == value }
    }
}

data class Blizzard(val direction: BlizzardDirection, var location: GPoint)

class Basin(lines: List<String>) {
    private val blizzards = mutableListOf<Blizzard>()

    // As well as tracking each blizzard, we render the basin into an array, basins,
    // where points marked as true have either a wall or a blizzard at that time.
    private val numRows = lines.size
    private val numCols = lines.first().length
    // Assume this is true for all maps
    private val elfStart = GPoint(0, 1)
    private val elfTarget = GPoint(numRows - 1, numCols - 2)
    // The empty basin is just the walls
    private val emptyBasin = Array(numRows * numCols) { false }
    // basins is a list of the walls+blizzards for each turn
    private val basins = mutableListOf<Array<Boolean>>()
    private val wallSymbol = '#'
    private val emptySymbol = '.'

    private fun pointToIndex(p: GPoint): Int = (p.row * numCols) + p.col

    fun get(b: Array<Boolean>, p: GPoint): Boolean = b[pointToIndex(p)]
    private fun set(b: Array<Boolean>, p: GPoint) {
        b[pointToIndex(p)] = true
    }

    init {
        parseBasin(lines)
        render()
    }

    private fun parseBasin(lines: List<String>) {
        val p = GPoint(0, 0)
        for (line in lines) {
            for (ch in line) {
                if (ch == wallSymbol) {
                    set(emptyBasin, p)
                } else if (ch != emptySymbol) {
                    val dir = BlizzardDirection.from(ch)
                    blizzards.add(Blizzard(dir, p.copy()))
                }
                p.col++
            }
            p.col = 0
            p.row++
        }
    }

    fun modelBlizzards(turns: Int) {
        for (index in 1..turns) {
            moveBlizzards()
            render()
        }
    }

    private val compass = mapOf(
        BlizzardDirection.UP to GPoint(row = -1, col = 0),
        BlizzardDirection.RIGHT to GPoint(row = 0, col = 1),
        BlizzardDirection.DOWN to GPoint(row = 1, col = 0),
        BlizzardDirection.LEFT to GPoint(row = 0, col = -1),
    )

    private fun wrap(x: Int, xMax: Int) = when (x) {
        0 -> xMax - 2 // top to bottom
        xMax - 1 -> 1 // bottom to top
        else -> x
    }

    private fun moveBlizzards() {
        for (blizzard in blizzards) {
            blizzard.location = blizzard.location + compass[blizzard.direction]!!
            blizzard.location.row = wrap(blizzard.location.row, numRows)
            blizzard.location.col = wrap(blizzard.location.col, numCols)
        }
    }

    private fun render() {
        val basin = emptyBasin.clone()
        for (blizzard in blizzards) {
            set(basin, blizzard.location)
        }
        basins.add(basin)
    }

    fun getMap(b: Array<Boolean> = basins.last()): List<String> {
        val m = mutableListOf<String>()
        val p = GPoint(0, 0)
        for (r in 0 until numRows) {
            val s = mutableListOf<Char>()
            for (c in 0 until numCols) {
                s += if (get(b, p)) wallSymbol else emptySymbol
                p.col++
            }
            m.add(s.joinToString(separator = ""))
            p.col = 0
            p.row++
        }
        return m
    }

    // Set a limit on rendering
    private val maxTurns = 1000

    fun solve(): Int {
        modelBlizzards(maxTurns)
        return solve(elfStart, elfTarget, 1)
    }

    fun solveWithSnacks(): Int {
        modelBlizzards(maxTurns)
        val there = solve(elfStart, elfTarget, 1)
        val back = solve(elfTarget, elfStart, there + 1)
        return solve(elfStart, elfTarget, back + 1)
    }

    private fun solve(start: GPoint, target: GPoint, startTurn: Int): Int {
        var positions = mutableSetOf(start)
        var turn = 0

        // At each turn, we look at the set of previous positions and
        // create a new set of positions based on valid moves. This will bound the
        // number of positions to look at each time, as we treat eg move up and then
        // down as the same as waiting for 2 turns.
        for (basin in basins) {
            if (turn < startTurn) {
                turn++
                continue
            }

            val newPositions = mutableSetOf<GPoint>()
            for (position in positions) {
                if (!get(basin, position)) {
                    // Wait
                    newPositions.add(position)
                }
                for (offset in compass.values) {
                    val newPosition = position + offset
                    if (newPosition == target) {
                        // Found it
                        return turn
                    }
                    if (newPosition.row in 1 until numRows - 1 &&
                        !get(basin, newPosition)
                    ) {
                        newPositions.add(newPosition)
                    }
                }
            }
            positions = newPositions
            turn++
        }
        return maxTurns
    }
}

fun day24(input: String): String {
    val lines = input.trimEnd().lines()

    val part1 = Basin(lines).solve()
    val part2 = Basin(lines).solveWithSnacks()

    return "$part1, $part2"
}
