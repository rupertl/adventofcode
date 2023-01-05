import java.lang.Math.max
import java.lang.Math.min

// Advent of Code 2022 - Day 23: Unstable Diffusion

// PART 1

// You enter a large crater of gray dirt where the grove is supposed
// to be. All around you, plants you imagine were expected to be full
// of fruit are instead withered and broken. A large group of Elves
// has formed in the middle of the grove.

// "...but this volcano has been dormant for months. Without ash, the
// fruit can't grow!"

// You look up to see a massive, snow-capped mountain towering above you.

// "It's not like there are other active volcanoes here; we've looked everywhere."

// "But our scanners show active magma flows; clearly it's going somewhere."

// They finally notice you at the edge of the grove, your pack almost
// overflowing from the random star fruit you've been collecting.
// Behind you, elephants and monkeys explore the grove, looking
// concerned. Then, the Elves recognize the ash cloud slowly spreading
// above your recent detour.

// "Why do you--" "How is--" "Did you just--"

// Before any of them can form a complete question, another Elf speaks
// up: "Okay, new plan. We have almost enough fruit already, and ash
// from the plume should spread here eventually. If we quickly plant
// new seedlings now, we can still make it to the extraction point.
// Spread out!"

// The Elves each reach into their pack and pull out a tiny plant. The
// plants rely on important nutrients from the ash, so they can't be
// planted too close together.

// There isn't enough time to let the Elves figure out where to plant
// the seedlings themselves; you quickly scan the grove (your puzzle
// input) and note their positions.

// For example:

// ....#..
// ..###.#
// #...#.#
// .#...##
// #.###..
// ##.#.##
// .#..#..

// The scan shows Elves # and empty ground .; outside your scan, more
// empty ground extends a long way in every direction. The scan is
// oriented so that north is up; orthogonal directions are written N
// (north), S (south), W (west), and E (east), while diagonal
// directions are written NE, NW, SE, SW.

// The Elves follow a time-consuming process to figure out where they
// should each go; you can speed up this process considerably. The
// process consists of some number of rounds during which Elves
// alternate between considering where to move and actually moving.

// During the first half of each round, each Elf considers the eight
// positions adjacent to themself. If no other Elves are in one of
// those eight positions, the Elf does not do anything during this
// round. Otherwise, the Elf looks in each of four directions in the
// following order and proposes moving one step in the first valid
// direction:

//     If there is no Elf in the N, NE, or NW adjacent positions, the Elf proposes moving north one step.
//     If there is no Elf in the S, SE, or SW adjacent positions, the Elf proposes moving south one step.
//     If there is no Elf in the W, NW, or SW adjacent positions, the Elf proposes moving west one step.
//     If there is no Elf in the E, NE, or SE adjacent positions, the Elf proposes moving east one step.

// After each Elf has had a chance to propose a move, the second half
// of the round can begin. Simultaneously, each Elf moves to their
// proposed destination tile if they were the only Elf to propose
// moving to that position. If two or more Elves propose moving to the
// same position, none of those Elves move.

// Finally, at the end of the round, the first direction the Elves
// considered is moved to the end of the list of directions. For
// example, during the second round, the Elves would try proposing a
// move to the south first, then west, then east, then north. On the
// third round, the Elves would first consider west, then east, then
// north, then south.

// As a smaller example, consider just these five Elves:

// .....
// ..##.
// ..#..
// .....
// ..##.
// .....

// The northernmost two Elves and southernmost two Elves all propose
// moving north, while the middle Elf cannot move north and proposes
// moving south. The middle Elf proposes the same destination as the
// southwest Elf, so neither of them move, but the other three do:

// ..##.
// .....
// ..#..
// ...#.
// ..#..
// .....

// Next, the northernmost two Elves and the southernmost Elf all
// propose moving south. Of the remaining middle two Elves, the west
// one cannot move south and proposes moving west, while the east one
// cannot move south or west and proposes moving east. All five Elves
// succeed in moving to their proposed positions:

// .....
// ..##.
// .#...
// ....#
// .....
// ..#..

// Finally, the southernmost two Elves choose not to move at all. Of
// the remaining three Elves, the west one proposes moving west, the
// east one proposes moving east, and the middle one proposes moving
// north; all three succeed in moving:

// ..#..
// ....#
// #....
// ....#
// .....
// ..#..

// At this point, no Elves need to move, and so the process ends.

// The larger example above proceeds as follows:

// == Initial State ==
// ..............
// ..............
// .......#......
// .....###.#....
// ...#...#.#....
// ....#...##....
// ...#.###......
// ...##.#.##....
// ....#..#......
// ..............
// ..............
// ..............

// == End of Round 1 ==
// ..............
// .......#......
// .....#...#....
// ...#..#.#.....
// .......#..#...
// ....#.#.##....
// ..#..#.#......
// ..#.#.#.##....
// ..............
// ....#..#......
// ..............
// ..............

// == End of Round 2 ==
// ..............
// .......#......
// ....#.....#...
// ...#..#.#.....
// .......#...#..
// ...#..#.#.....
// .#...#.#.#....
// ..............
// ..#.#.#.##....
// ....#..#......
// ..............
// ..............

// == End of Round 3 ==
// ..............
// .......#......
// .....#....#...
// ..#..#...#....
// .......#...#..
// ...#..#.#.....
// .#..#.....#...
// .......##.....
// ..##.#....#...
// ...#..........
// .......#......
// ..............

// == End of Round 4 ==
// ..............
// .......#......
// ......#....#..
// ..#...##......
// ...#.....#.#..
// .........#....
// .#...###..#...
// ..#......#....
// ....##....#...
// ....#.........
// .......#......
// ..............

// == End of Round 5 ==
// .......#......
// ..............
// ..#..#.....#..
// .........#....
// ......##...#..
// .#.#.####.....
// ...........#..
// ....##..#.....
// ..#...........
// ..........#...
// ....#..#......
// ..............

// After a few more rounds...

// == End of Round 10 ==
// .......#......
// ...........#..
// ..#.#..#......
// ......#.......
// ...#.....#..#.
// .#......##....
// .....##.......
// ..#........#..
// ....#.#..#....
// ..............
// ....#..#..#...
// ..............

// To make sure they're on the right track, the Elves like to check
// after round 10 that they're making good progress toward covering
// enough ground. To do this, count the number of empty ground tiles
// contained by the smallest rectangle that contains every Elf. (The
// edges of the rectangle should be aligned to the N/S/E/W directions;
// the Elves do not have the patience to calculate arbitrary
// rectangles.) In the above example, that rectangle is:

// ......#.....
// ..........#.
// .#.#..#.....
// .....#......
// ..#.....#..#
// #......##...
// ....##......
// .#........#.
// ...#.#..#...
// ............
// ...#..#..#..

// In this region, the number of empty ground tiles is 110.

// Simulate the Elves' process and find the smallest rectangle that
// contains the Elves after 10 rounds. How many empty ground tiles
// does that rectangle contain?

// PART 2

// It seems you're on the right track. Finish simulating the process
// and figure out where the Elves need to go. How many rounds did you
// save them?

// In the example above, the first round where no Elf moved was round 20:

// .......#......
// ....#......#..
// ..#.....#.....
// ......#.......
// ...#....#.#..#
// #.............
// ....#.....#...
// ..#.....#.....
// ....#.#....#..
// .........#....
// ....#......#..
// .......#......

// Figure out where the Elves need to go. What is the number of the
// first round where no Elf moves?

data class GPoint(var row: Int, var col: Int) {
    operator fun plus(other: GPoint) =
        GPoint(this.row + other.row, this.col + other.col)
}

class ElfGrove(lines: List<String>) {
    // Model the grove as an array of Booleans, true = elf present
    // We set an size for the grove (in extent) and the origin for
    // their initial placement (offset) that may need to be adjusted
    // based on numbers of elves and turns.
    private val extent = 1000
    private val offset = 300
    private val grove = Array(extent * extent) { false }
    private val elfSymbol = '#'
    private val emptySymbol = '.'

    private fun pointToIndex(p: GPoint): Int = (p.row * extent) + p.col

    fun get(p: GPoint): Boolean = grove[pointToIndex(p)]
    private fun set(p: GPoint, content: Boolean) {
        grove[pointToIndex(p)] = content
    }

    init {
        parseGrove(lines)
    }

    private fun parseGrove(lines: List<String>) {
        val p = GPoint(offset, offset)
        for (line in lines) {
            for (ch in line) {
                if (ch == elfSymbol) {
                    set(p, true)
                }
                p.col++
            }
            p.col = offset
            p.row++
        }
    }

    fun getMap(rowSize: Int, colSize: Int): List<String> {
        val m = mutableListOf<String>()
        val p = GPoint(offset, offset)
        for (r in 0 until rowSize) {
            val s = mutableListOf<Char>()
            for (c in 0 until colSize) {
                s += if (get(p)) elfSymbol else emptySymbol
                p.col++
            }
            m.add(s.joinToString(separator = ""))
            p.col = offset
            p.row++
        }
        return m
    }

    fun runTurns(turns: Int) {
        for (index in 1..turns) {
            runOnce(index)
        }
    }

    private fun runOnce(turn: Int): Int {
        // proposals has key = proposed tile to move to and value
        // as a set of elves propsing to move there.
        val proposals = mutableMapOf<GPoint, MutableSet<GPoint>>()
        var numMoves = 0

        // First part of round: get proposals
        val p = GPoint(offset, offset)
        for (r in 0 until extent) {
            p.row = r
            for (c in 0 until extent) {
                p.col = c
                if (get(p)) {
                    val prop = getProposal(turn, p)
                    if (prop != null) {
                        val fromSet = proposals.getOrDefault(prop, mutableSetOf())
                        fromSet.add(p.copy())
                        proposals[prop] = fromSet
                    }
                }
            }
        }

        // Second part: move if only one proposal per point
        // First remove all elves that have approved proposals
        for (from in proposals.values) {
            if (from.size == 1) {
                set(from.first(), false)
            }
        }

        // Then place them at their new location
        for ((to, from) in proposals.entries) {
            if (from.size == 1) {
                numMoves++
                set(to, true)
            }
        }
        return numMoves
    }

    private val compass = mapOf(
        "N" to GPoint(row = -1, col = 0),
        "NE" to GPoint(row = -1, col = 1),
        "E" to GPoint(row = 0, col = 1),
        "SE" to GPoint(row = 1, col = 1),
        "S" to GPoint(row = 1, col = 0),
        "SW" to GPoint(row = 1, col = -1),
        "W" to GPoint(row = 0, col = -1),
        "NW" to GPoint(row = -1, col = -1),
    )

    private fun getProposal(turn: Int, from: GPoint): GPoint? {
        // Find all neighbours
        val neighbours = mutableSetOf<String>()
        for ((direction, relative) in compass.entries) {
            if (get(from + relative)) {
                neighbours.add(direction)
            }
        }

        if (neighbours.isEmpty()) {
            // No proposal
            return null
        }

        // Try each rule
        for (rule in turn until turn + 4) {
            if (rule % 4 == 1 &&
                !neighbours.contains("N") &&
                !neighbours.contains("NE") && !neighbours.contains("NW")
            ) {
                // No elf in N, NE, NW -> N
                return from + compass["N"]!!
            } else if (rule % 4 == 2 &&
                !neighbours.contains("S") &&
                !neighbours.contains("SE") && !neighbours.contains("SW")
            ) {
                // No elf in S, SE, SW -> S
                return from + compass["S"]!!
            } else if (rule % 4 == 3 &&
                !neighbours.contains("W") &&
                !neighbours.contains("NW") && !neighbours.contains("SW")
            ) {
                // No elf in W, NW, SW -> W
                return from + compass["W"]!!
            } else if (rule % 4 == 0 &&
                !neighbours.contains("E") &&
                !neighbours.contains("NE") && !neighbours.contains("SE")
            ) {
                // No elf in E, SE, NE
                return from + compass["E"]!!
            }
        }

        // No possible move
        return null
    }

    private fun findEmpties(): Int {
        // Find min/max points
        var rmin = extent
        var rmax = 0
        var cmin = extent
        var cmax = 0
        val p = GPoint(offset, offset)
        for (r in 0 until extent) {
            p.row = r
            for (c in 0 until extent) {
                p.col = c
                if (get(p)) {
                    rmin = min(rmin, p.row)
                    rmax = max(rmax, p.row)
                    cmin = min(cmin, p.col)
                    cmax = max(cmax, p.col)
                }
            }
        }

        // Find number of empty spaces inside that box
        var empties = 0
        for (r in rmin..rmax) {
            p.row = r
            for (c in cmin..cmax) {
                p.col = c
                if (!get(p)) {
                    empties++
                }
            }
        }
        return empties
    }

    fun emptyAfterTurns(turns: Int = 10): Int {
        runTurns(turns)
        return findEmpties()
    }

    fun turnsUntilStable(): Int {
        var turn = 1
        while (runOnce(turn) != 0) {
            turn++
        }
        return turn
    }
}

fun day23(input: String): String {
    val lines = input.trimEnd().lines()

    val part1 = ElfGrove(lines).emptyAfterTurns()
    val part2 = ElfGrove(lines).turnsUntilStable()

    return "$part1, $part2"
}
