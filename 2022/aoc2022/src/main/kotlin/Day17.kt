// Advent of Code 2022 - Day 17: Pyroclastic Flow

// PART 1

// Your handheld device has located an alternative exit from the cave
// for you and the elephants. The ground is rumbling almost
// continuously now, but the strange valves bought you some time. It's
// definitely getting warmer in here, though.

// The tunnels eventually open into a very tall, narrow chamber.
// Large, oddly-shaped rocks are falling into the chamber from above,
// presumably due to all the rumbling. If you can't work out where the
// rocks will fall next, you might be crushed!

// The five types of rocks have the following peculiar shapes, where #
// is rock and . is empty space:

// ####

// .#.
// ###
// .#.

// ..#
// ..#
// ###

// #
// #
// #
// #

// ##
// ##

// The rocks fall in the order shown above: first the - shape, then
// the + shape, and so on. Once the end of the list is reached, the
// same order repeats: the - shape falls first, sixth, 11th, 16th,
// etc.

// The rocks don't spin, but they do get pushed around by jets of hot
// gas coming out of the walls themselves. A quick scan reveals the
// effect the jets of hot gas will have on the rocks as they fall
// (your puzzle input).

// For example, suppose this was the jet pattern in your cave:

// >>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>

// In jet patterns, < means a push to the left, while > means a push
// to the right. The pattern above means that the jets will push a
// falling rock right, then right, then right, then left, then left,
// then right, and so on. If the end of the list is reached, it
// repeats.

// The tall, vertical chamber is exactly seven units wide. Each rock
// appears so that its left edge is two units away from the left wall
// and its bottom edge is three units above the highest rock in the
// room (or the floor, if there isn't one).

// After a rock appears, it alternates between being pushed by a jet
// of hot gas one unit (in the direction indicated by the next symbol
// in the jet pattern) and then falling one unit down. If any movement
// would cause any part of the rock to move into the walls, floor, or
// a stopped rock, the movement instead does not occur. If a downward
// movement would have caused a falling rock to move into the floor or
// an already-fallen rock, the falling rock stops where it is (having
// landed on something) and a new rock immediately begins falling.

// Drawing falling rocks with @ and stopped rocks with #, the jet
// pattern in the example above manifests as follows:

// The first rock begins falling:
// |..@@@@.|
// |.......|
// |.......|
// |.......|
// +-------+

// Jet of gas pushes rock right:
// |...@@@@|
// |.......|
// |.......|
// |.......|
// +-------+

// Rock falls 1 unit:
// |...@@@@|
// |.......|
// |.......|
// +-------+

// Jet of gas pushes rock right, but nothing happens:
// |...@@@@|
// |.......|
// |.......|
// +-------+

// Rock falls 1 unit:
// |...@@@@|
// |.......|
// +-------+

// Jet of gas pushes rock right, but nothing happens:
// |...@@@@|
// |.......|
// +-------+

// Rock falls 1 unit:
// |...@@@@|
// +-------+

// Jet of gas pushes rock left:
// |..@@@@.|
// +-------+

// Rock falls 1 unit, causing it to come to rest:
// |..####.|
// +-------+

// A new rock begins falling:
// |...@...|
// |..@@@..|
// |...@...|
// |.......|
// |.......|
// |.......|
// |..####.|
// +-------+

// Jet of gas pushes rock left:
// |..@....|
// |.@@@...|
// |..@....|
// |.......|
// |.......|
// |.......|
// |..####.|
// +-------+

// Rock falls 1 unit:
// |..@....|
// |.@@@...|
// |..@....|
// |.......|
// |.......|
// |..####.|
// +-------+

// Jet of gas pushes rock right:
// |...@...|
// |..@@@..|
// |...@...|
// |.......|
// |.......|
// |..####.|
// +-------+

// Rock falls 1 unit:
// |...@...|
// |..@@@..|
// |...@...|
// |.......|
// |..####.|
// +-------+

// Jet of gas pushes rock left:
// |..@....|
// |.@@@...|
// |..@....|
// |.......|
// |..####.|
// +-------+

// Rock falls 1 unit:
// |..@....|
// |.@@@...|
// |..@....|
// |..####.|
// +-------+

// Jet of gas pushes rock right:
// |...@...|
// |..@@@..|
// |...@...|
// |..####.|
// +-------+

// Rock falls 1 unit, causing it to come to rest:
// |...#...|
// |..###..|
// |...#...|
// |..####.|
// +-------+

// A new rock begins falling:
// |....@..|
// |....@..|
// |..@@@..|
// |.......|
// |.......|
// |.......|
// |...#...|
// |..###..|
// |...#...|
// |..####.|
// +-------+

// The moment each of the next few rocks begins falling, you would see this:

// |..@....|
// |..@....|
// |..@....|
// |..@....|
// |.......|
// |.......|
// |.......|
// |..#....|
// |..#....|
// |####...|
// |..###..|
// |...#...|
// |..####.|
// +-------+

// |..@@...|
// |..@@...|
// |.......|
// |.......|
// |.......|
// |....#..|
// |..#.#..|
// |..#.#..|
// |#####..|
// |..###..|
// |...#...|
// |..####.|
// +-------+

// |..@@@@.|
// |.......|
// |.......|
// |.......|
// |....##.|
// |....##.|
// |....#..|
// |..#.#..|
// |..#.#..|
// |#####..|
// |..###..|
// |...#...|
// |..####.|
// +-------+

// |...@...|
// |..@@@..|
// |...@...|
// |.......|
// |.......|
// |.......|
// |.####..|
// |....##.|
// |....##.|
// |....#..|
// |..#.#..|
// |..#.#..|
// |#####..|
// |..###..|
// |...#...|
// |..####.|
// +-------+

// |....@..|
// |....@..|
// |..@@@..|
// |.......|
// |.......|
// |.......|
// |..#....|
// |.###...|
// |..#....|
// |.####..|
// |....##.|
// |....##.|
// |....#..|
// |..#.#..|
// |..#.#..|
// |#####..|
// |..###..|
// |...#...|
// |..####.|
// +-------+

// |..@....|
// |..@....|
// |..@....|
// |..@....|
// |.......|
// |.......|
// |.......|
// |.....#.|
// |.....#.|
// |..####.|
// |.###...|
// |..#....|
// |.####..|
// |....##.|
// |....##.|
// |....#..|
// |..#.#..|
// |..#.#..|
// |#####..|
// |..###..|
// |...#...|
// |..####.|
// +-------+

// |..@@...|
// |..@@...|
// |.......|
// |.......|
// |.......|
// |....#..|
// |....#..|
// |....##.|
// |....##.|
// |..####.|
// |.###...|
// |..#....|
// |.####..|
// |....##.|
// |....##.|
// |....#..|
// |..#.#..|
// |..#.#..|
// |#####..|
// |..###..|
// |...#...|
// |..####.|
// +-------+

// |..@@@@.|
// |.......|
// |.......|
// |.......|
// |....#..|
// |....#..|
// |....##.|
// |##..##.|
// |######.|
// |.###...|
// |..#....|
// |.####..|
// |....##.|
// |....##.|
// |....#..|
// |..#.#..|
// |..#.#..|
// |#####..|
// |..###..|
// |...#...|
// |..####.|
// +-------+

// To prove to the elephants your simulation is accurate, they want to
// know how tall the tower will get after 2022 rocks have stopped (but
// before the 2023rd rock begins falling). In this example, the tower
// of rocks will be 3068 units tall.

// How many units tall will the tower of rocks be after 2022 rocks
// have stopped falling?

// PART 2

// The elephants are not impressed by your simulation. They demand to
// know how tall the tower will be after 1000000000000 rocks have
// stopped! Only then will they feel confident enough to proceed
// through the cave.

// In the example above, the tower would be 1514285714288 units tall!

// How tall will the tower be after 1000000000000 rocks have stopped?

class RockChamber(line: String) {
    // Gusts of air cam be LEFT (<) or RIGHT (>) and repeat forever.
    private val gusts = line
    private var gustIndex = 0
    enum class Direction { LEFT, RIGHT }
    fun nextGust(): Direction =
        if (gusts[gustIndex++ % gusts.length] == '<')
            Direction.LEFT
        else
            Direction.RIGHT

    // The chamber is modelled by an array of ints, with entry 0 representing
    // the lowest row. Bits 0-7 of each row are 1 if there is a rock there.
    // chamberHeight could need to be adjusted if the cycle in part 2 is large.
    private val chamberHeight = 10_000
    private var chamber = IntArray(chamberHeight) { 0 }

    // The min/max columns for the rock
    private val chamberMin = 0b00000001
    private val chamberMax = 0b01000000
    fun getChamberRow(row: Int) = chamber[row]

    // The rock level is the highest level non-moving rocks can be at present.
    // 0 represents the floor.
    private var rockLevel = 0
    fun getRockLevel() = rockLevel

    // Each rock type, note the lowest part of the rock comes first in the array.
    private val rockTypes = arrayOf(
        intArrayOf(
          // |..@@@@.|
            0b0011110,
        ),
        intArrayOf(
          // |...@...|
          // |..@@@..|
          // |...@...|
            0b0001000,
            0b0011100,
            0b0001000,
        ),
        intArrayOf(
          // |....@..|
          // |....@..|
          // |..@@@..|
            0b0011100,
            0b0000100,
            0b0000100,
        ),
        intArrayOf(
          // |..@....|
          // |..@....|
          // |..@....|
          // |..@....|
            0b0010000,
            0b0010000,
            0b0010000,
            0b0010000,
        ),
        intArrayOf(
          // |..@@...|
          // |..@@...|
            0b0011000,
            0b0011000,
        ),
    )
    private val maxRockHeight = 4
    private var rockTypeIndex = 0
    private fun nextRock() = rockTypes[rockTypeIndex++ % rockTypes.size]

    // Add a rock to the chamber.
    fun addRock() {
        var rock = nextRock()
        var row = rockLevel + 3

        while (true) {
            // Apply a gust, if the placed rock allows it
            val gust = nextGust()
            rock = applyGust(rock, row, gust)

            // Apply a drop down if placed rock allows it
            if (canDrop(rock, row)) {
                row--
            } else {
                // The rock is now settled
                applyDrop(rock, row)
                for (index in rockLevel..rockLevel + maxRockHeight) {
                    if (chamber[index] != 0) {
                        rockLevel = index + 1
                    }
                }
                break
            }
        }
    }

    fun addRocks(n: Int) = repeat(n) { addRock() }

    fun dumpChamber() {
        println("\nChamber with height ${rockLevel}\n")
        for (index in rockLevel downTo 0) {
            val line = chamber[index].toString(2).padStart(7, '0')
            println(line)
        }
    }

    // Predicate to see if cavern row overlaps with a dropping rock
    private fun overlaps(a: Int, b: Int) = (a and b) != 0

    // Merge existing rock with a settled rock
    private fun settle(a: Int, b: Int) = a or b

    private fun applyGust(rock: IntArray, row: Int, gust: Direction): IntArray {
        val newRock = IntArray(rock.size)
        for (index in rock.indices) {
            if ((gust == Direction.LEFT  && overlaps(rock[index], chamberMax)) ||
                (gust == Direction.RIGHT && overlaps(rock[index], chamberMin))
            ) {
                // Would hit wall
                return rock
            }
            val newRockSegment =
                if (gust == Direction.LEFT)
                    rock[index].shl(1)
                else
                    rock[index].shr(1)

            if (overlaps(newRockSegment, chamber[row + index])) {
                // Would collide with placed rock
                return rock
            }
            newRock[index] = newRockSegment
        }

        return newRock
    }

    private fun canDrop(rock: IntArray, row: Int): Boolean {
        if (row <= 0) {
            // On the floor already
            return false
        }
        for (index in rock.indices) {
            if (overlaps(rock[index], chamber[row - 1 + index])) {
                return false
            }
        }
        return true
    }

    private fun applyDrop(rock: IntArray, row: Int) {
        for (index in rock.indices) {
            chamber[row + index] = settle(chamber[row + index], rock[index])
        }
    }

    // For part 2, we need to detect cycles by seeing repeated instances
    // of gust, rock and top placed rock, which is represented by State
    data class State(
        val gustIndex: Int,
        val rockPieceIndex: Int,
        val recentRows: IntArray,
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as State

            if (gustIndex != other.gustIndex) return false
            if (rockPieceIndex != other.rockPieceIndex) return false
            if (!recentRows.contentEquals(other.recentRows)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = gustIndex
            result = 31 * result + rockPieceIndex
            result = 31 * result + recentRows.contentHashCode()
            return result
        }
    }

    // Cycles start at some point cycleStart and repeat after cyclePeriod.
    // These are calculated on demand.
    private var cycleStart = -1
    private var cyclePeriod = -1

    private fun findCycle() {
        // Map state to a list of number of rocks dropped.
        val cycleMap = mutableMapOf<State, MutableList<Int>>()
        for (index in 1..chamberHeight / 2) {
            addRock()

            // We need to keep a number of rows of rock, the number
            // is fairly arbitrary.
            val rockToKeep = 10
            if (index < rockToKeep) {
                continue
            }
            val rcs = State(
                gustIndex % gusts.length,
                rockTypeIndex % rockTypes.size,
                chamber.sliceArray(rockLevel - rockToKeep until rockLevel),
            )
            if (cycleMap[rcs] == null) {
                cycleMap[rcs] = mutableListOf(index)
            } else {
                cycleMap[rcs]!!.add(index)
            }
            if (cycleMap[rcs]!!.size > 2) {
                break
            }
        }
        val c = cycleMap.filter { it.value.size > 2 }.map { it.value }.first()
        cycleStart = c[0]
        cyclePeriod = c[1] - c[0]
        if (cyclePeriod == c[2] - c[1]) {
            // OK, the distances indicate it is a cycle
            return
        }

        throw RuntimeException("Could not detect cycle")
    }

    // Use the cycle to predict the height after n rocks.
    fun findLevelAfterRocks(n: Long): Long {
        if (cyclePeriod < 0) {
            findCycle()
        }
        reset()
        if (n < cycleStart + cyclePeriod) {
            // Just do it the iterative way
            addRocks(n.toInt())
            return getRockLevel().toLong()
        }

        // Add
        // 1. cycleStart rocks
        // 2. ohe period of rocks
        // 3. any left over rocks after a whole number of periods
        addRocks(cycleStart)
        val startLevel = getRockLevel()
        addRocks(cyclePeriod)
        val periodLevel = getRockLevel() - startLevel
        val numPeriods = (n - cycleStart) / cyclePeriod
        val numPeriodsToAdd = numPeriods - 1 // as we've already added 1 period
        val extraRocks = (n - cycleStart) - (numPeriods * cyclePeriod)
        addRocks(extraRocks.toInt())
        return getRockLevel() + (numPeriodsToAdd * periodLevel)
    }

    // Return the chamber to its initial state.
    private fun reset() {
        gustIndex = 0
        rockTypeIndex = 0
        chamber = IntArray(chamberHeight) { 0 }
        rockLevel = 0
    }
}

fun day17(input: String): String {
    val line = input.trim()

    val rc = RockChamber(line)
    rc.addRocks(2022)
    val part1 = rc.getRockLevel()

    val rc2 = RockChamber(line)
    val part2 = rc2.findLevelAfterRocks(1_000_000_000_000L)

    return "$part1, $part2"
}
