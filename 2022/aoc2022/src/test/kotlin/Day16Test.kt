import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day16Test {
    private val sampleData = listOf(
        "Valve AA has flow rate=0; tunnels lead to valves DD, II, BB",
        "Valve BB has flow rate=13; tunnels lead to valves CC, AA",
        "Valve CC has flow rate=2; tunnels lead to valves DD, BB",
        "Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE",
        "Valve EE has flow rate=3; tunnels lead to valves FF, DD",
        "Valve FF has flow rate=0; tunnels lead to valves EE, GG",
        "Valve GG has flow rate=0; tunnels lead to valves FF, HH",
        "Valve HH has flow rate=22; tunnel leads to valve GG",
        "Valve II has flow rate=0; tunnels lead to valves AA, JJ",
        "Valve JJ has flow rate=21; tunnel leads to valve II",
    )

    @Test
    fun `Day 16 sample data parsing`() {
        val vg = ValveGraph(sampleData)
        assertEquals(2, vg.allNodes["CC"]!!.rate)
        assertEquals(3, vg.allNodes["DD"]!!.connections.size)
    }

    @Test
    fun `Day 16 sample data distances`() {
        val vg = ValveGraph(sampleData)
        assertEquals(2, vg.distances["AA"]!!["CC"])
        assertEquals(1, vg.distances["AA"]!!["DD"])
        assertEquals(7, vg.distances["JJ"]!!["HH"])
    }

    @Test
    fun `Day 16 sample data non zero nodes`() {
        val vg = ValveGraph(sampleData)
        assertEquals(6, vg.nodesToConsider.size)
    }

    @Test
    fun `Day 16 sample data best journey`() {
        val vg = ValveGraph(sampleData)
        assertEquals(1651, vg.findBestScore())
    }

    @Test
    fun `Day 16 sample data best journey with an elephant`() {
        val vg = ValveGraph(sampleData)
        assertEquals(1707, vg.findBestScoreWithElephant())
    }
}
