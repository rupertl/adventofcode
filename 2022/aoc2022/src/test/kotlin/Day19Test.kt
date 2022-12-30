import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day19Test {
    private val sampleData = listOf(
        "Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.",
        "Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.",
    )

    @Test
    fun `Day 19 sample data blueprints parse`() {
        val rf = RobotFactory(sampleData)
        assertEquals(1, rf.blueprints.first().id)
        assertEquals(2, rf.blueprints.first().clayOreCost)
        assertEquals(3, rf.blueprints.first().obsOreCost)
        assertEquals(14, rf.blueprints.first().obsClayCost)
        assertEquals(2, rf.blueprints.first().geoOreCost)
        assertEquals(7, rf.blueprints.first().geoObsCost)

        assertEquals(3, rf.blueprints.elementAt(1).clayOreCost)
        assertEquals(3, rf.blueprints.elementAt(1).geoOreCost)
        assertEquals(12, rf.blueprints.elementAt(1).geoObsCost)
    }

    // Decreased time a bit to make the test faster.
    @Test
    fun `Day 19 sample data blueprint 1 t=20`() {
        val rf = RobotFactory(sampleData, maxTime = 20)
        assertEquals(2, rf.findMostGeodes(rf.blueprints.first()))
    }
}
