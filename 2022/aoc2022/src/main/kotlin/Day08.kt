// Advent of Code 2022 - Day 8: Treetop Tree House

// PART 1

// The expedition comes across a peculiar patch of tall trees all
// planted carefully in a grid. The Elves explain that a previous
// expedition planted these trees as a reforestation effort. Now,
// they're curious if this would be a good location for a tree house.

// First, determine whether there is enough tree cover here to keep a
// tree house hidden. To do this, you need to count the number of
// trees that are visible from outside the grid when looking directly
// along a row or column.

// The Elves have already launched a quadcopter to generate a map with
// the height of each tree (your puzzle input). For example:

// 30373
// 25512
// 65332
// 33549
// 35390

// Each tree is represented as a single digit whose value is its
// height, where 0 is the shortest and 9 is the tallest.

// A tree is visible if all of the other trees between it and an edge
// of the grid are shorter than it. Only consider trees in the same
// row or column; that is, only look up, down, left, or right from any
// given tree.

// All of the trees around the edge of the grid are visible - since
// they are already on the edge, there are no trees to block the view.
// In this example, that only leaves the interior nine trees to
// consider:

//     The top-left 5 is visible from the left and top. (It isn't visible from the right or bottom since other trees of height 5 are in the way.)
//     The top-middle 5 is visible from the top and right.
//     The top-right 1 is not visible from any direction; for it to be visible, there would need to only be trees of height 0 between it and an edge.
//     The left-middle 5 is visible, but only from the right.
//     The center 3 is not visible from any direction; for it to be visible, there would need to be only trees of at most height 2 between it and an edge.
//     The right-middle 3 is visible from the right.
//     In the bottom row, the middle 5 is visible, but the 3 and 4 are not.

// With 16 trees visible on the edge and another 5 visible in the
// interior, a total of 21 trees are visible in this arrangement.

// Consider your map; how many trees are visible from outside the grid?

// PART 2

// Content with the amount of tree cover available, the Elves just
// need to know the best spot to build their tree house: they would
// like to be able to see a lot of trees.

// To measure the viewing distance from a given tree, look up, down,
// left, and right from that tree; stop if you reach an edge or at the
// first tree that is the same height or taller than the tree under
// consideration. (If a tree is right on the edge, at least one of its
// viewing distances will be zero.)

// The Elves don't care about distant trees taller than those found by
// the rules above; the proposed tree house has large eaves to keep it
// dry, so they wouldn't be able to see higher than the tree house
// anyway.

// In the example above, consider the middle 5 in the second row:

// 30373
// 25512
// 65332
// 33549
// 35390

//     Looking up, its view is not blocked; it can see 1 tree (of height 3).
//     Looking left, its view is blocked immediately; it can see only 1 tree (of height 5, right next to it).
//     Looking right, its view is not blocked; it can see 2 trees.
//     Looking down, its view is blocked eventually; it can see 2 trees (one of height 3, then the tree of height 5 that blocks its view).

// A tree's scenic score is found by multiplying together its viewing
// distance in each of the four directions. For this tree, this is 4
// (found by multiplying 1 * 1 * 2 * 2).

// However, you can do even better: consider the tree of height 5 in the middle of the fourth row:

// 30373
// 25512
// 65332
// 33549
// 35390

//     Looking up, its view is blocked at 2 trees (by another tree with a height of 5).
//     Looking left, its view is not blocked; it can see 2 trees.
//     Looking down, its view is also not blocked; it can see 1 tree.
//     Looking right, its view is blocked at 2 trees (by a massive tree of height 9).

// This tree's scenic score is 8 (2 * 2 * 1 * 2); this is the ideal
// spot for the tree house.

// Consider each tree on your map. What is the highest scenic score
// possible for any tree?

class TreeGrid(lines: Array<String>) {
    // Access is grid[y][x] with [0][0] at top left
    private val maxX = lines[0].length - 1
    private val maxY = lines.size - 1
    private val grid = lines.map { lineToIntArray(it) }.toTypedArray()

    private fun lineToIntArray(line: String) =
        line.map { it.digitToInt() }.toTypedArray()

    fun heightAt(x: Int, y: Int) = grid[y][x]

    private fun left(x: Int) = x-1 downTo 0
    private fun right(x: Int) = x+1..maxX
    private fun up(y: Int) = y-1 downTo 0
    private fun down(y: Int) = y+1..maxY

    private fun higher(x: Int, y: Int, height: Int) = heightAt(x, y) >= height

    fun treeVisible(x: Int, y: Int): Boolean {
        if (x == 0 || x == maxX || y == 0 || y == maxY) return true

        val height = heightAt(x, y)

        var visibleLeft = true
        for (dx in left(x))
            if (higher(dx, y, height))
                visibleLeft = false

        var visibleRight = true
        for (dx in right(x))
            if (higher(dx, y, height))
                visibleRight = false

        var visibleUp = true
        for (dy in up(y))
            if (higher(x, dy, height))
                visibleUp = false

        var visibleDown = true
        for (dy in down(y))
            if (higher(x, dy, height))
                visibleDown = false

        return visibleLeft || visibleRight || visibleUp || visibleDown
    }

    fun scenicScore(x: Int, y: Int): Int {
        if (x == 0 || x == maxX || y == 0 || y == maxY) return 0

        val height = heightAt(x, y)

        var leftScore = 0
        for (dx in left(x)) {
            leftScore++
            if (higher(dx, y, height))
                break
        }

        var rightScore = 0
        for (dx in right(x)) {
            rightScore++
            if (higher(dx, y, height))
                break
        }

        var upScore = 0
        for (dy in up(y)) {
            upScore++
            if (higher(x, dy, height))
                break
        }

        var downScore = 0
        for (dy in down(y)) {
            downScore++
            if (higher(x, dy, height))
                break
        }

        return leftScore * rightScore * upScore * downScore
    }

    fun numVisible(): Int {
        var count = 0
        for (x in 0..maxX)
            for (y in 0..maxY)
                if (treeVisible(x, y))
                    count++
        return count
    }

    fun mostScenicScore(): Int {
        val scores = mutableListOf<Int>()
        for (x in 0..maxX)
            for (y in 0..maxY)
                scores.add(scenicScore(x, y))
        return scores.max()
    }
}

fun day08(input: String): String {
    val lines = input.trim().lines().toTypedArray()
    val tg = TreeGrid(lines)

    val part1 = tg.numVisible()
    val part2 = tg.mostScenicScore()
    return "$part1, $part2"
}
