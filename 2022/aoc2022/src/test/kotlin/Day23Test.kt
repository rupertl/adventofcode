
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day23Test {
    private val sampleData = listOf(
        "..............",
        "..............",
        ".......#......",
        ".....###.#....",
        "...#...#.#....",
        "....#...##....",
        "...#.###......",
        "...##.#.##....",
        "....#..#......",
        "..............",
        "..............",
        "..............",
    )

    private val colSize = sampleData[0].length
    private val rowSize = sampleData.size

    @Test
    fun `Day 23 sample data parse`() {
        val eg = ElfGrove(sampleData)
        assertEquals(sampleData, eg.getMap(rowSize, colSize))
    }

    @Test
    fun `Day 23 sample data run 1`() {
        val eg = ElfGrove(sampleData)
        eg.runTurns(1)
        val expected = listOf(
            "..............",
            ".......#......",
            ".....#...#....",
            "...#..#.#.....",
            ".......#..#...",
            "....#.#.##....",
            "..#..#.#......",
            "..#.#.#.##....",
            "..............",
            "....#..#......",
            "..............",
            "..............",
        )
        val actual = eg.getMap(rowSize, colSize)
        assertEquals(expected, actual)
    }

    @Test
    fun `Day 23 sample data run 2`() {
        val eg = ElfGrove(sampleData)
        eg.runTurns(2)
        val expected = listOf(
            "..............",
            ".......#......",
            "....#.....#...",
            "...#..#.#.....",
            ".......#...#..",
            "...#..#.#.....",
            ".#...#.#.#....",
            "..............",
            "..#.#.#.##....",
            "....#..#......",
            "..............",
            "..............",
        )
        val actual = eg.getMap(rowSize, colSize)
        assertEquals(expected, actual)
    }

    @Test
    fun `Day 23 sample data run 10`() {
        val eg = ElfGrove(sampleData)
        eg.runTurns(10)
        val expected = listOf(
            ".......#......",
            "...........#..",
            "..#.#..#......",
            "......#.......",
            "...#.....#..#.",
            ".#......##....",
            ".....##.......",
            "..#........#..",
            "....#.#..#....",
            "..............",
            "....#..#..#...",
            "..............",
        )
        val actual = eg.getMap(rowSize, colSize)
        assertEquals(expected, actual)
    }

    @Test
    fun `Day 23 sample data empties after 10 turns`() {
        assertEquals(110, ElfGrove(sampleData).emptyAfterTurns())
    }

    @Test
    fun `Day 23 sample data stable after n turns`() {
        assertEquals(20, ElfGrove(sampleData).turnsUntilStable())
    }
}
