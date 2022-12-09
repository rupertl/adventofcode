import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day09Test {
    private val sampleData = arrayOf(
        "R 4",
        "U 4",
        "L 3",
        "D 1",
        "R 4",
        "D 1",
        "L 5",
        "R 2"
    )

    @Test
    fun `Day 9 sample data R4`() {
        val r = Rope()
        r.moveHead("R 4")

        assertEquals(4, r.knots[0].x)
        assertEquals(0, r.knots[0].y)
        assertEquals(3, r.knots[1].x)
        assertEquals(0, r.knots[1].y)
    }

    @Test
    fun `Day 9 sample data U3D2`() {
        val r = Rope()
        r.moveHead("U 3")
        r.moveHead("D 2")

        assertEquals(0, r.knots[0].x)
        assertEquals(1, r.knots[0].y)
        assertEquals(0, r.knots[1].x)
        assertEquals(2, r.knots[1].y)
    }

    // ....H.
    // ....T.
    // ......
    // ......
    // s.....
    @Test
    fun `Day 9 sample data R4U4`() {
        val r = Rope()
        r.moveHead(sampleData[0])
        r.moveHead(sampleData[1])

        assertEquals(4, r.knots[0].x)
        assertEquals(4, r.knots[0].y)
        assertEquals(4, r.knots[1].x)
        assertEquals(3, r.knots[1].y)
    }

    // ......
    // ......
    // .TH...
    // ......
    // s.....
    @Test
    fun `Day 9 sample data all moves`() {
        val r = Rope()
        for (line in sampleData) {
            r.moveHead(line)
        }

        assertEquals(2, r.knots[0].x)
        assertEquals(2, r.knots[0].y)
        assertEquals(1, r.knots[1].x)
        assertEquals(2, r.knots[1].y)
    }

    @Test
    fun `Day 9 sample data tail was in 13 places`() {
        val r = Rope()
        for (line in sampleData) {
            r.moveHead(line)
        }

        assertEquals(13, r.numTailPositions())
    }

    @Test
    fun `Day 9 sample data tail with 10 knots was in 1 place`() {
        val r = Rope(10)
        for (line in sampleData) {
            r.moveHead(line)
        }

        assertEquals(1, r.numTailPositions())
    }

    private val sampleData2 = arrayOf(
        "R 5",
        "U 8",
        "L 8",
        "D 3",
        "R 17",
        "D 10",
        "L 25",
        "U 20"
    )

    @Test
    fun `Day 9 sample data 2 with 10 knots was in 36 places`() {
        val r = Rope(10, RopeKnot(11, 5))
        for (line in sampleData2) {
            r.moveHead(line)
        }

        assertEquals(36, r.numTailPositions())
    }
}
