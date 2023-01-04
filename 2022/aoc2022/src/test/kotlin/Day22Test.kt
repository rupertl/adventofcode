import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class Day22Test {
    private val sampleData = listOf(
        "        ...#    ",
        "        .#..    ",
        "        #...    ",
        "        ....    ",
        "...#.......#    ",
        "........#...    ",
        "..#....#....    ",
        "..........#.    ",
        "        ...#....",
        "        .....#..",
        "        .#......",
        "        ......#.",
        "",
        "10R5L5R10L4R5L5",
    )

    @Test
    fun `Day 22 facings`() {
        val f = Facing()
        assertEquals(1, f.dx())
        assertEquals(0, f.dy())
        assertEquals(0, f.password())
        f.turn("L")
        assertEquals(0, f.dx())
        assertEquals(-1, f.dy())
        assertEquals(3, f.password())
    }

    @Test
    fun `Day 22 sample data parse`() {
        val b = Board(sampleData)
        assertEquals(Tile.EMPTY, b.get(RCPoint(1, 1)))
        assertEquals(Tile.OPEN, b.get(RCPoint(1, 9)))
        assertEquals(Tile.WALL, b.get(RCPoint(2, 10)))
        assertEquals(Tile.OPEN, b.get(RCPoint(12, 16)))
        assertEquals(Tile.EMPTY, b.get(RCPoint(12, 17)))
        assertEquals(Tile.EMPTY, b.get(RCPoint(13, 16)))
        var index = 0
        assertEquals("10", b.moves[index++])
        assertEquals("R", b.moves[index++])
        assertEquals("5", b.moves[index++])
        assertEquals("L", b.moves[index])
    }

    @Test
    fun `Day 22 sample data starting point`() {
        val b = Board(sampleData)
        assertEquals(RCPoint(1, 9), b.position)
        assertEquals(0, b.facing.password())
    }

    @Test
    fun `Day 22 sample data test moves`() {
        val b = Board(sampleData)
        assertTrue(b.moveOnce())
        assertEquals(RCPoint(1, 10), b.position)
        assertTrue(b.moveOnce())
        assertEquals(RCPoint(1, 11), b.position)
        assertFalse(b.moveOnce())
        assertEquals(RCPoint(1, 11), b.position)
        b.facing.turn("L")
        b.moveOnce() // wrap around top to bottom
        assertEquals(RCPoint(12, 11), b.position)
        b.facing.turn("L")
        assertTrue(b.moveOnce())
        assertEquals(RCPoint(12, 10), b.position)
        assertTrue(b.moveOnce())
        assertTrue(b.moveOnce())
        assertFalse(b.moveOnce())
        assertEquals(RCPoint(12, 16), b.position)
    }

    @Test
    fun `Day 22 sample data password 2D`() {
        assertEquals(6032, Board(sampleData).run())
    }
}
