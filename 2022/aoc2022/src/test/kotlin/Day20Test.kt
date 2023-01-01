import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day20Test {
    private val sampleData = listOf(
        "1",
        "2",
        "-3",
        "3",
        "-2",
        "0",
        "4",
    )

    @Test
    fun `Day 20 sample data at start`() {
        val cf = CoordinateFile(sampleData)
        assertEquals(listOf(0L, 4L, 1L, 2L, -3L, 3L, -2L), cf.nodesFromZero())
    }

    @Test
    fun `Day 20 simple case`() {
        val cf = CoordinateFile(listOf("0", "1", "2"))
        cf.mix()
        assertEquals(listOf(0L, 2L, 1L), cf.nodesFromZero())
    }

    @Test
    fun `Day 20 move -2 to be after -2`() {
        val cf = CoordinateFile(listOf("0", "1", "-2"))
        cf.mix()
        assertEquals(listOf(0L, -2L, 1L), cf.nodesFromZero())
    }

    @Test
    fun `Day 20 -ve case`() {
        val cf = CoordinateFile(listOf("0", "-2", "-1", "-4"))
        cf.mix()
        assertEquals(listOf(0L, -4L, -2L, -1), cf.nodesFromZero())
    }

    @Test
    fun `Day 20 sample data after mix`() {
        val cf = CoordinateFile(sampleData)
        cf.mix()
        assertEquals(listOf(0L, 3L, -2L, 1L, 2L, -3L, 4L), cf.nodesFromZero())
    }

    @Test
    fun `Day 20 sample data decrypt`() {
        assertEquals(3L, CoordinateFile(sampleData).decrypt())
    }

    @Test
    fun `Day 20 sample data decrypt with key`() {
        val result = CoordinateFile(sampleData, key = KEY_20_2).decrypt(10)
        assertEquals(1623178306L, result)
    }
}
