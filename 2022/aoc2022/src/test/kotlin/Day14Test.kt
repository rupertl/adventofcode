import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class Day14Test {
    private val sampleData = listOf(
        "498,4 -> 498,6 -> 496,6",
        "503,4 -> 502,4 -> 502,9 -> 494,9"
    )

    //   4     5  5
    //   9     0  0
    //   4     0  3
    // 0 ......+...
    // 1 ..........
    // 2 ..........
    // 3 ..........
    // 4 ....#...##
    // 5 ....#...#.
    // 6 ..###...#.
    // 7 ........#.
    // 8 ........#.
    // 9 #########.

    @Test
    fun `Day 14 sample data points`() {
        val c = Cave(sampleData)
        assertTrue(c.isAir(Point(0, 500)))
        assertFalse(c.isAir(Point(4, 498)))
        assertTrue(c.isAir(Point(4, 499)))
        assertTrue(c.isAir(Point(4, 500)))
        assertTrue(c.isAir(Point(4, 501)))
        assertFalse(c.isAir(Point(4, 502)))
        assertFalse(c.isAir(Point(4, 503)))
    }

    @Test
    fun `Day 14 sample data settled`() {
        val c = Cave(sampleData)
        c.dropSandUntilSettled()
        assertEquals(24, c.sandSettled)
    }

    @Test
    fun `Day 14 sample data settled with floor`() {
        val c = Cave(sampleData, true)
        c.dropSandUntilSettled()
        assertEquals(93, c.sandSettled)
    }
}
