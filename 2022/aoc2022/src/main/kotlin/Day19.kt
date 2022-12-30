import java.lang.Math.max

// Advent of Code 2022 - Day 19: Not Enough Minerals

// PART 1

// Your scans show that the lava did indeed form obsidian!

// The wind has changed direction enough to stop sending lava droplets
// toward you, so you and the elephants exit the cave. As you do, you
// notice a collection of geodes around the pond. Perhaps you could
// use the obsidian to create some geode-cracking robots and break
// them open?

// To collect the obsidian from the bottom of the pond, you'll need
// waterproof obsidian-collecting robots. Fortunately, there is an
// abundant amount of clay nearby that you can use to make them
// waterproof.

// In order to harvest the clay, you'll need special-purpose
// clay-collecting robots. To make any type of robot, you'll need ore,
// which is also plentiful but in the opposite direction from the
// clay.

// Collecting ore requires ore-collecting robots with big drills.
// Fortunately, you have exactly one ore-collecting robot in your pack
// that you can use to kickstart the whole operation.

// Each robot can collect 1 of its resource type per minute. It also
// takes one minute for the robot factory (also conveniently from your
// pack) to construct any type of robot, although it consumes the
// necessary resources available when construction begins.

// The robot factory has many blueprints (your puzzle input) you can
// choose from, but once you've configured it with a blueprint, you
// can't change it. You'll need to work out which blueprint is best.

// For example:

// Blueprint 1:
//   Each ore robot costs 4 ore.
//   Each clay robot costs 2 ore.
//   Each obsidian robot costs 3 ore and 14 clay.
//   Each geode robot costs 2 ore and 7 obsidian.

// Blueprint 2:
//   Each ore robot costs 2 ore.
//   Each clay robot costs 3 ore.
//   Each obsidian robot costs 3 ore and 8 clay.
//   Each geode robot costs 3 ore and 12 obsidian.

// (Blueprints have been line-wrapped here for legibility. The robot
// factory's actual assortment of blueprints are provided one
// blueprint per line.)

// The elephants are starting to look hungry, so you shouldn't take
// too long; you need to figure out which blueprint would maximize the
// number of opened geodes after 24 minutes by figuring out which
// robots to build and when to build them.

// Using blueprint 1 in the example above, the largest number of
// geodes you could open in 24 minutes is 9. One way to achieve that
// is:

// == Minute 1 ==
// 1 ore-collecting robot collects 1 ore; you now have 1 ore.

// == Minute 2 ==
// 1 ore-collecting robot collects 1 ore; you now have 2 ore.

// == Minute 3 ==
// Spend 2 ore to start building a clay-collecting robot.
// 1 ore-collecting robot collects 1 ore; you now have 1 ore.
// The new clay-collecting robot is ready; you now have 1 of them.

// == Minute 4 ==
// 1 ore-collecting robot collects 1 ore; you now have 2 ore.
// 1 clay-collecting robot collects 1 clay; you now have 1 clay.

// == Minute 5 ==
// Spend 2 ore to start building a clay-collecting robot.
// 1 ore-collecting robot collects 1 ore; you now have 1 ore.
// 1 clay-collecting robot collects 1 clay; you now have 2 clay.
// The new clay-collecting robot is ready; you now have 2 of them.

// == Minute 6 ==
// 1 ore-collecting robot collects 1 ore; you now have 2 ore.
// 2 clay-collecting robots collect 2 clay; you now have 4 clay.

// == Minute 7 ==
// Spend 2 ore to start building a clay-collecting robot.
// 1 ore-collecting robot collects 1 ore; you now have 1 ore.
// 2 clay-collecting robots collect 2 clay; you now have 6 clay.
// The new clay-collecting robot is ready; you now have 3 of them.

// == Minute 8 ==
// 1 ore-collecting robot collects 1 ore; you now have 2 ore.
// 3 clay-collecting robots collect 3 clay; you now have 9 clay.

// == Minute 9 ==
// 1 ore-collecting robot collects 1 ore; you now have 3 ore.
// 3 clay-collecting robots collect 3 clay; you now have 12 clay.

// == Minute 10 ==
// 1 ore-collecting robot collects 1 ore; you now have 4 ore.
// 3 clay-collecting robots collect 3 clay; you now have 15 clay.

// == Minute 11 ==
// Spend 3 ore and 14 clay to start building an obsidian-collecting robot.
// 1 ore-collecting robot collects 1 ore; you now have 2 ore.
// 3 clay-collecting robots collect 3 clay; you now have 4 clay.
// The new obsidian-collecting robot is ready; you now have 1 of them.

// == Minute 12 ==
// Spend 2 ore to start building a clay-collecting robot.
// 1 ore-collecting robot collects 1 ore; you now have 1 ore.
// 3 clay-collecting robots collect 3 clay; you now have 7 clay.
// 1 obsidian-collecting robot collects 1 obsidian; you now have 1 obsidian.
// The new clay-collecting robot is ready; you now have 4 of them.

// == Minute 13 ==
// 1 ore-collecting robot collects 1 ore; you now have 2 ore.
// 4 clay-collecting robots collect 4 clay; you now have 11 clay.
// 1 obsidian-collecting robot collects 1 obsidian; you now have 2 obsidian.

// == Minute 14 ==
// 1 ore-collecting robot collects 1 ore; you now have 3 ore.
// 4 clay-collecting robots collect 4 clay; you now have 15 clay.
// 1 obsidian-collecting robot collects 1 obsidian; you now have 3 obsidian.

// == Minute 15 ==
// Spend 3 ore and 14 clay to start building an obsidian-collecting robot.
// 1 ore-collecting robot collects 1 ore; you now have 1 ore.
// 4 clay-collecting robots collect 4 clay; you now have 5 clay.
// 1 obsidian-collecting robot collects 1 obsidian; you now have 4 obsidian.
// The new obsidian-collecting robot is ready; you now have 2 of them.

// == Minute 16 ==
// 1 ore-collecting robot collects 1 ore; you now have 2 ore.
// 4 clay-collecting robots collect 4 clay; you now have 9 clay.
// 2 obsidian-collecting robots collect 2 obsidian; you now have 6 obsidian.

// == Minute 17 ==
// 1 ore-collecting robot collects 1 ore; you now have 3 ore.
// 4 clay-collecting robots collect 4 clay; you now have 13 clay.
// 2 obsidian-collecting robots collect 2 obsidian; you now have 8 obsidian.

// == Minute 18 ==
// Spend 2 ore and 7 obsidian to start building a geode-cracking robot.
// 1 ore-collecting robot collects 1 ore; you now have 2 ore.
// 4 clay-collecting robots collect 4 clay; you now have 17 clay.
// 2 obsidian-collecting robots collect 2 obsidian; you now have 3 obsidian.
// The new geode-cracking robot is ready; you now have 1 of them.

// == Minute 19 ==
// 1 ore-collecting robot collects 1 ore; you now have 3 ore.
// 4 clay-collecting robots collect 4 clay; you now have 21 clay.
// 2 obsidian-collecting robots collect 2 obsidian; you now have 5 obsidian.
// 1 geode-cracking robot cracks 1 geode; you now have 1 open geode.

// == Minute 20 ==
// 1 ore-collecting robot collects 1 ore; you now have 4 ore.
// 4 clay-collecting robots collect 4 clay; you now have 25 clay.
// 2 obsidian-collecting robots collect 2 obsidian; you now have 7 obsidian.
// 1 geode-cracking robot cracks 1 geode; you now have 2 open geodes.

// == Minute 21 ==
// Spend 2 ore and 7 obsidian to start building a geode-cracking robot.
// 1 ore-collecting robot collects 1 ore; you now have 3 ore.
// 4 clay-collecting robots collect 4 clay; you now have 29 clay.
// 2 obsidian-collecting robots collect 2 obsidian; you now have 2 obsidian.
// 1 geode-cracking robot cracks 1 geode; you now have 3 open geodes.
// The new geode-cracking robot is ready; you now have 2 of them.

// == Minute 22 ==
// 1 ore-collecting robot collects 1 ore; you now have 4 ore.
// 4 clay-collecting robots collect 4 clay; you now have 33 clay.
// 2 obsidian-collecting robots collect 2 obsidian; you now have 4 obsidian.
// 2 geode-cracking robots crack 2 geodes; you now have 5 open geodes.

// == Minute 23 ==
// 1 ore-collecting robot collects 1 ore; you now have 5 ore.
// 4 clay-collecting robots collect 4 clay; you now have 37 clay.
// 2 obsidian-collecting robots collect 2 obsidian; you now have 6 obsidian.
// 2 geode-cracking robots crack 2 geodes; you now have 7 open geodes.

// == Minute 24 ==
// 1 ore-collecting robot collects 1 ore; you now have 6 ore.
// 4 clay-collecting robots collect 4 clay; you now have 41 clay.
// 2 obsidian-collecting robots collect 2 obsidian; you now have 8 obsidian.
// 2 geode-cracking robots crack 2 geodes; you now have 9 open geodes.

// However, by using blueprint 2 in the example above, you could do
// even better: the largest number of geodes you could open in 24
// minutes is 12.

// Determine the quality level of each blueprint by multiplying that
// blueprint's ID number with the largest number of geodes that can be
// opened in 24 minutes using that blueprint. In this example, the
// first blueprint has ID 1 and can open 9 geodes, so its quality
// level is 9. The second blueprint has ID 2 and can open 12 geodes,
// so its quality level is 24. Finally, if you add up the quality
// levels of all of the blueprints in the list, you get 33.

// Determine the quality level of each blueprint using the largest
// number of geodes it could produce in 24 minutes. What do you get if
// you add up the quality level of all of the blueprints in your list?

// PART 2

// While you were choosing the best blueprint, the elephants found
// some food on their own, so you're not in as much of a hurry; you
// figure you probably have 32 minutes before the wind changes
// direction again and you'll need to get out of range of the erupting
// volcano.

// Unfortunately, one of the elephants ate most of your blueprint
// list! Now, only the first three blueprints in your list are intact.

// In 32 minutes, the largest number of geodes blueprint 1 (from the
// example above) can open is 56. One way to achieve that is:

// ...

// However, blueprint 2 from the example above is still better; using
// it, the largest number of geodes you could open in 32 minutes is
// 62.

// You no longer have enough blueprints to worry about quality levels.
// Instead, for each of the first three blueprints, determine the
// largest number of geodes you could open; then, multiply these three
// values together.

// Don't worry about quality levels; instead, just determine the
// largest number of geodes you could open using each of the first
// three blueprints. What do you get if you multiply these numbers
// together?

// Holds the constants for each blueprint.
// We just look for numbers in the text.
class Blueprint(line: String) {
    private val numberRegex = Regex("[0-9]+")
    private val numbers = numberRegex
        .findAll(line)
        .map { it.value }
        .map { it.toInt() }

    var index = 0
    val id = numbers.elementAt(index++)
    val oreCost = numbers.elementAt(index++)
    val clayOreCost = numbers.elementAt(index++)
    val obsOreCost = numbers.elementAt(index++)
    val obsClayCost = numbers.elementAt(index++)
    val geoOreCost = numbers.elementAt(index++)
    val geoObsCost = numbers.elementAt(index++)
}

// A collection of robots and their products.
data class Robots(
    val blueprint: Blueprint,
    var time: Int = 1,
    var oreRobots: Int = 1,
    var clayRobots: Int = 0,
    var obsRobots: Int = 0,
    var geoRobots: Int = 0,
    var ore: Int = 0,
    var clay: Int = 0,
    var obs: Int = 0,
    var geo: Int = 0,
) {
    fun tick() {
        ore += oreRobots
        clay += clayRobots
        obs += obsRobots
        geo += geoRobots
        time++
    }
}

// Solve the problem by trying to create different combinations
// of robots.
// Note: part 2 takes several hours to run - needs more pruning!
class RobotFactory(lines: List<String>, val maxTime: Int = 24) {
    val blueprints = lines.map { Blueprint(it) }

    fun findMostGeodes(blueprint: Blueprint): Int = findMostGeodes(Robots(blueprint))

    private fun findMostGeodes(r: Robots): Int {
        if (outOfTime(r) || notBuyingRobotsFastEnough(r)) {
            return finalise(r)
        }

        var best = 0

        // Buy a geo robot
        if (r.obs >= r.blueprint.geoObsCost && r.ore >= r.blueprint.geoOreCost) {
            val next = r.copy()
            next.obs -= next.blueprint.geoObsCost
            next.ore -= next.blueprint.geoOreCost
            next.tick()
            next.geoRobots++
            best = max(best, findMostGeodes(next))
        }

        // Buy an obs robot
        if (r.clay >= r.blueprint.obsClayCost && r.ore >= r.blueprint.obsOreCost) {
            val next = r.copy()
            next.clay -= next.blueprint.obsClayCost
            next.ore -= next.blueprint.obsOreCost
            next.tick()
            next.obsRobots++
            best = max(best, findMostGeodes(next))
        }

        // Buy a clay robot
        if (r.ore >= r.blueprint.clayOreCost) {
            val next = r.copy()
            next.ore -= next.blueprint.clayOreCost
            next.tick()
            next.clayRobots++
            best = max(best, findMostGeodes(next))
        }

        // Buy an ore robot
        if (r.ore >= r.blueprint.oreCost) {
            val next = r.copy()
            next.ore -= next.blueprint.oreCost
            next.tick()
            next.oreRobots++
            best = max(best, findMostGeodes(next))
        }

        // Do nothing
        r.tick()
        best = max(best, findMostGeodes(r))

        return best
    }

    private fun outOfTime(r: Robots): Boolean = r.time > maxTime

    // Idea here is to prune paths where we hang around and don't
    // buy anything even if we have enough material.
    private fun notBuyingRobotsFastEnough(r: Robots) =
        (r.clayRobots < 1 && r.ore > 2 + r.blueprint.clayOreCost) ||
        (r.obsRobots < 1 && r.clay > 2 + r.blueprint.obsClayCost) ||
        (r.geoRobots < 1 && r.obs > 2 + r.blueprint.geoObsCost)

    private fun finalise(r: Robots): Int {
        // Run out the clock and return the number of geos
        while (r.time <= maxTime) {
            r.tick()
        }
        return r.geo
    }

    fun sumQualityScores() = blueprints.sumOf { findMostGeodes(it) * it.id }

    fun findProduct(n: Int): Long {
        var prod = 1L
        for (bp in blueprints.take(n)) {
            prod *= findMostGeodes(bp)
        }
        return prod
    }
}

fun day19(input: String): String {
    val lines = input.trim().lines()

    val rf1 = RobotFactory(lines)
    val part1 = rf1.sumQualityScores()

    val rf2 = RobotFactory(lines, maxTime = 32)
    val part2 = "(skipped)" // rf2.findProduct(3)

    return "$part1, $part2"
}
