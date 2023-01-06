import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day24Test {
    private val sampleData = listOf(
        "#.######",
        "#>>.<^<#",
        "#.<..<<#",
        "#>v.><>#",
        "#<^v^^>#",
        "######.#",
    )

    @Test
    fun `Day 24 sample data parse`() {
        val b = Basin(sampleData)
        val m = b.getMap().toTypedArray()
        assertEquals("#.######", m[0])
        assertEquals("###.####", m[1])
        assertEquals("#.#..###", m[2])
    }

    @Test
    fun `Day 24 sample data basin after 1 turn`() {
        val b = Basin(sampleData)
        b.modelBlizzards(1)
        val m = b.getMap().toTypedArray()
        assertEquals("#.######", m[0])
        assertEquals("#.##.#.#", m[1])
        assertEquals("##..##.#", m[2])
    }

    @Test
    fun `Day 24 sample data basin after 18 turns`() {
        val b = Basin(sampleData)
        b.modelBlizzards(18)
        val m = b.getMap().toTypedArray()
        assertEquals("#.######", m[0])
        assertEquals("###.#.##", m[1])
        assertEquals("#.######", m[2])
    }

    @Test
    fun `Day 24 sample data solve`() {
        assertEquals(18, Basin(sampleData).solve())
    }

    @Test
    fun `Day 24 sample data solve with snacks`() {
        assertEquals(54, Basin(sampleData).solveWithSnacks())
    }
}
