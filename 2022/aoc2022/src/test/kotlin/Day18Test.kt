import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day18Test {
    private val sampleData = listOf(
        "2,2,2",
        "1,2,2",
        "3,2,2",
        "2,1,2",
        "2,3,2",
        "2,2,1",
        "2,2,3",
        "2,2,4",
        "2,2,6",
        "1,2,5",
        "3,2,5",
        "2,1,5",
        "2,3,5",
    )

    @Test
    fun `Day 18 simple case`() {
        val ls = LavaScan(listOf("1,1,1", "2,1,1"))
        assertEquals(10, ls.surfaceArea())
    }

    @Test
    fun `Day 18 sample data part 1`() {
        val ls = LavaScan(sampleData)
        assertEquals(64, ls.surfaceArea())
    }

    @Test
    fun `Day 18 sample data part 2`() {
        val ls = LavaScan(sampleData)
        assertEquals(58, ls.externalSurfaceArea())
        // Check orig surface area still correct.
        assertEquals(64, ls.surfaceArea())
    }
}
