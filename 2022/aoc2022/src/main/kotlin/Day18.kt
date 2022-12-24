import java.util.*

// Advent of Code 2022 - Day 18: Boiling Boulders

// PART 1

// You and the elephants finally reach fresh air. You've emerged near
// the base of a large volcano that seems to be actively erupting!
// Fortunately, the lava seems to be flowing away from you and toward
// the ocean.

// Bits of lava are still being ejected toward you, so you're
// sheltering in the cavern exit a little longer. Outside the cave,
// you can see the lava landing in a pond and hear it loudly hissing
// as it solidifies.

// Depending on the specific compounds in the lava and speed at which
// it cools, it might be forming obsidian! The cooling rate should be
// based on the surface area of the lava droplets, so you take a quick
// scan of a droplet as it flies past you (your puzzle input).

// Because of how quickly the lava is moving, the scan isn't very
// good; its resolution is quite low and, as a result, it approximates
// the shape of the lava droplet with 1x1x1 cubes on a 3D grid, each
// given as its x,y,z position.

// To approximate the surface area, count the number of sides of each
// cube that are not immediately connected to another cube. So, if
// your scan were only two adjacent cubes like 1,1,1 and 2,1,1, each
// cube would have a single side covered and five sides exposed, a
// total surface area of 10 sides.

// Here's a larger example:

// 2,2,2
// 1,2,2
// 3,2,2
// 2,1,2
// 2,3,2
// 2,2,1
// 2,2,3
// 2,2,4
// 2,2,6
// 1,2,5
// 3,2,5
// 2,1,5
// 2,3,5

// In the above example, after counting up all the sides that aren't
// connected to another cube, the total surface area is 64.

// What is the surface area of your scanned lava droplet?

// PART 2

// Something seems off about your calculation. The cooling rate
// depends on exterior surface area, but your calculation also
// included the surface area of air pockets trapped in the lava
// droplet.

// Instead, consider only cube sides that could be reached by the
// water and steam as the lava droplet tumbles into the pond. The
// steam will expand to reach as much as possible, completely
// displacing any air on the outside of the lava droplet but never
// expanding diagonally.

// In the larger example above, exactly one cube of air is trapped
// within the lava droplet (at 2,2,5), so the exterior surface area of
// the lava droplet is 58.

// What is the exterior surface area of your scanned lava droplet?

const val CUBE_SIDES = 6

class LavaScan(val lines: List<String>) {
    enum class Content { AIR, WATER, SOLID }
    private val boundary = 25 // max size of each dimension
    private val shift = 2 // we shift coordinates to keep everything > 0
    private val space = Array(boundary * boundary * boundary) { Content.AIR }

    private fun location(x: Int, y: Int, z: Int) =
        (z * (boundary * boundary)) + (y * boundary) + x
    fun set(x: Int, y: Int, z: Int, c: Content) {
        space[location(x, y, z)] = c
    }
    fun get(x: Int, y: Int, z: Int) = space[location(x, y, z)]

    init {
        parse(lines)
    }

    private fun parse(lines: List<String>) {
        for (line in lines) {
            val (x, y, z) = parseLine(line)
            set(x, y, z, Content.SOLID)
        }
    }

    private fun parseLine(line: String): List<Int> =
        line.split(",").map { it.toInt() + shift }

    fun surfaceArea(): Int {
        var surfaceArea = 0
        for (line in lines) {
            val (x, y, z) = parseLine(line)
            surfaceArea += CUBE_SIDES - countAdjacent(x, y, z)
        }
        return surfaceArea
    }

    private fun countAdjacent(x: Int, y: Int, z: Int): Int {
        var adj = 0
        for (d in listOf(-1, 1)) {
            if (get(x + d, y, z) == Content.SOLID) {
                adj++
            }
            if (get(x, y + d, z) == Content.SOLID) {
                adj++
            }
            if (get(x, y, z + d) == Content.SOLID) {
                adj++
            }
        }
        return adj
    }

    fun externalSurfaceArea(): Int {
        floodFill()
        var surfaceArea = 0
        for (line in lines) {
            val (x, y, z) = parseLine(line)
            surfaceArea += countAdjacentWater(x, y, z)
        }
        return surfaceArea
    }

    private fun floodFill() {
        data class Point3(val x: Int, val y: Int, val z: Int)
        val work = ArrayDeque<Point3>()
        work.push(Point3(0, 0, 0))

        while (!work.isEmpty()) {
            val p = work.pop()
            set(p.x, p.y, p.z, Content.WATER)
            for (d in listOf(-1, 1)) {
                if (p.x + d in 0 until boundary &&
                    get(p.x + d, p.y, p.z) == Content.AIR
                ) {
                    work.push(Point3(p.x + d, p.y, p.z))
                }
                if (p.y + d in 0 until boundary &&
                    get(p.x, p.y + d, p.z) == Content.AIR
                ) {
                    work.push(Point3(p.x, p.y + d, p.z))
                }
                if (p.z + d in 0 until boundary &&
                    get(p.x, p.y, p.z + d) == Content.AIR
                ) {
                    work.push(Point3(p.x, p.y, p.z + d))
                }
            }
        }
    }

    private fun countAdjacentWater(x: Int, y: Int, z: Int): Int {
        var adj = 0
        for (d in listOf(-1, 1)) {
            if (get(x + d, y, z) == Content.WATER) {
                adj++
            }
            if (get(x, y + d, z) == Content.WATER) {
                adj++
            }
            if (get(x, y, z + d) == Content.WATER) {
                adj++
            }
        }
        return adj
    }
}

fun day18(input: String): String {
    val lines = input.trim().lines()
    val ls = LavaScan(lines)

    val part1 = ls.surfaceArea()
    val part2 = ls.externalSurfaceArea()

    return "$part1, $part2"
}
