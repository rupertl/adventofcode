import java.util.LinkedList
import java.util.Queue

// Advent of Code 2022 - Day 12: Hill Climbing Algorithm

// PART 1

// You try contacting the Elves using your handheld device, but the
// river you're following must be too low to get a decent signal.

// You ask the device for a heightmap of the surrounding area (your
// puzzle input). The heightmap shows the local area from above broken
// into a grid; the elevation of each square of the grid is given by a
// single lowercase letter, where a is the lowest elevation, b is the
// next-lowest, and so on up to the highest elevation, z.

// Also included on the heightmap are marks for your current position
// (S) and the location that should get the best signal (E). Your
// current position (S) has elevation a, and the location that should
// get the best signal (E) has elevation z.

// You'd like to reach E, but to save energy, you should do it in as
// few steps as possible. During each step, you can move exactly one
// square up, down, left, or right. To avoid needing to get out your
// climbing gear, the elevation of the destination square can be at
// most one higher than the elevation of your current square; that is,
// if your current elevation is m, you could step to elevation n, but
// not to elevation o. (This also means that the elevation of the
// destination square can be much lower than the elevation of your
// current square.)

// For example:

// Sabqponm
// abcryxxl
// accszExk
// acctuvwj
// abdefghi

// Here, you start in the top-left corner; your goal is near the
// middle. You could start by moving down or right, but eventually
// you'll need to head toward the e at the bottom. From there, you can
// spiral around to the goal:

// v..v<<<<
// >v.vv<<^
// .>vv>E^^
// ..v>>>^^
// ..>>>>>^

// In the above diagram, the symbols indicate whether the path exits
// each square moving up (^), down (v), left (<), or right (>). The
// location that should get the best signal is still E, and . marks
// unvisited squares.

// This path reaches the goal in 31 steps, the fewest possible.

// What is the fewest steps required to move from your current
// position to the location that should get the best signal?

// PART 2

// As you walk up the hill, you suspect that the Elves will want to
// turn this into a hiking trail. The beginning isn't very scenic,
// though; perhaps you can find a better starting point.

// To maximize exercise while hiking, the trail should start as low as
// possible: elevation a. The goal is still the square marked E.
// However, the trail should still be direct, taking the fewest steps
// to reach its goal. So, you'll need to find the shortest path from
// any square at elevation a to the square marked E.

// Again consider the example from above:

// Sabqponm
// abcryxxl
// accszExk
// acctuvwj
// abdefghi

// Now, there are six choices for starting position (five marked a,
// plus the square marked S that counts as being at elevation a). If
// you start at the bottom-left square, you can reach the goal most
// quickly:

// ...v<<<<
// ...vv<<^
// ...v>E^^
// .>v>>>^^
// >^>>>>>^

// This path reaches the goal in only 29 steps, the fewest possible.

// What is the fewest steps required to move starting from any square
// with elevation a to the location that should get the best signal?

data class Point(val row: Int, val col: Int)

class Mountain(lines: List<String>) {
    private val cols = lines[0].length
    private val rows = lines.size
    private val terrain = lines.joinToString(separator = "")
    private val maxPath = Int.MAX_VALUE

    val start = indexToPoint(terrain.indexOf('S'))
    val end = indexToPoint(terrain.indexOf('E'))

    private fun indexToPoint(index: Int) = Point(index / cols, index % cols)

    fun at(p: Point) = terrain[(p.row * cols) + p.col]

    fun validMoves(from: Point): List<Point> =
        listOf(
            Point(from.row-1, from.col),
            Point(from.row+1, from.col),
            Point(from.row, from.col-1),
            Point(from.row, from.col+1)
        ).filter { isValidPoint(it) }.filter { isValidClimb(from, it) }

    private fun isValidPoint(p: Point): Boolean =
        p.row in 0 until rows && p.col in 0 until cols

    private fun isValidClimb(from: Point, to: Point): Boolean =
        height(to) - height(from) <= 1

    private fun height(p: Point) = when (p) {
        start -> 'a'.code
        end -> 'z'.code
        else -> at(p).code
    }

    fun solve(from: Point = start, to: Point = end): Int {
        val checked = mutableSetOf<Point>()
        val dist = mutableMapOf<Point, Int>()
        val work: Queue<Point> = LinkedList()
        work.add(from)

        while (!work.isEmpty()) {
            val curr = work.remove()
            for (next in validMoves(curr)) {
                if (!checked.contains(next)) {
                    dist[next] = dist.getOrDefault(curr, 0) + 1
                    checked.add(next)
                    work.add(next)
                }
            }
        }

        return dist.getOrDefault(to, maxPath)
    }

    fun solveScenic(): Int =
        terrain
            .indices
            .map { indexToPoint(it) }
            .filter { at(it) == 'a' }
            .minOf { solve(it, end) }
}

fun day12(input: String): String {
    val lines = input.trim().lines()
    val mt = Mountain(lines)
    val part1 = mt.solve()
    val part2 = mt.solveScenic()

    return "$part1, $part2"
}
