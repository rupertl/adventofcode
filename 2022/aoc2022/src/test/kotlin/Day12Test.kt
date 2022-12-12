import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day12Test {
    private val sampleData = listOf(
        "Sabqponm",
        "abcryxxl",
        "accszExk",
        "acctuvwj",
        "abdefghi"
    )

    @Test
    fun `Day 12 sample data S at 0,0`() {
        assertEquals('S',
            Mountain(sampleData).at(Point(0,0)))
    }

    @Test
    fun `Day 12 sample data r at 1,3`() {
        assertEquals('r',
            Mountain(sampleData).at(Point(1,3)))
    }

    @Test
    fun `Day 12 sample data 2 valid moves from 0,0`() {
        assertEquals(2,
            Mountain(sampleData).validMoves(Point(0,0)).size)
    }

    @Test
    fun `Day 12 sample data 3 valid moves from 2,2`() {
        assertEquals(3,
            Mountain(sampleData).validMoves(Point(2,2)).size)
    }

    @Test
    fun `Day 12 sample data 3 valid move from 3,5`() {
        assertEquals(3,
            Mountain(sampleData).validMoves(Point(3,5)).size)
    }

    @Test
    fun `Day 12 sample data 4 valid move (E) from 2,4`() {
        assertEquals(4,
            Mountain(sampleData).validMoves(Point(2,4)).size)
    }

    @Test
    fun `Day 12 sample data start and end points`() {
        val mt = Mountain(sampleData)
        assertEquals(Point(0,0), mt.start)
        assertEquals(Point(2,5), mt.end)
    }

    @Test
    fun `Day 12 sample data shortest path is 31`() {
        val mt = Mountain(sampleData)
        assertEquals(31, mt.solve())
    }

    @Test
    fun `Day 12 sample data shortest path for any a is 29`() {
        val mt = Mountain(sampleData)
        assertEquals(29, mt.solveScenic())
    }
}
