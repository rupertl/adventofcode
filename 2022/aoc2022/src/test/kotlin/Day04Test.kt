import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day04Test {

    private val sampleData = listOf(
        "2-4,6-8",
        "2-3,4-5",
        "5-7,7-9",
        "2-8,3-7",
        "6-6,4-6",
        "2-6,4-8"
    )

    @Test
    fun `Make an ElfCLeaningPair from a line of sample data`() {
        val ecp = makeElfCleaningPair(sampleData[0])
        assertEquals(2, ecp.elfA.first)
        assertEquals(4, ecp.elfA.last)
        assertEquals(6, ecp.elfB.first)
        assertEquals(8, ecp.elfB.last)
    }

    @Test
    fun `Day 4 sample data line 3 has contained sections`() {
        val ecp = makeElfCleaningPair(sampleData[3])
        assertTrue(ecp.hasContainedSections)
    }

    @Test
    fun `Day 4 sample data line 4 has contained sections`() {
        val ecp = makeElfCleaningPair(sampleData[4])
        assertTrue(ecp.hasContainedSections)
    }

    @Test
    fun `Day 4 sample data line 5 does not has contained sections`() {
        val ecp = makeElfCleaningPair(sampleData[5])
        assertFalse(ecp.hasContainedSections)
    }

    @Test
    fun `Day 4 sample data has 2 with contained sections`() {
        assertEquals(2, sumPairsWithContainedSections(sampleData))
    }

    @Test
    fun `Day 4 sample data line 5 has overlapped sections`() {
        val ecp = makeElfCleaningPair(sampleData[5])
        assertTrue(ecp.hasOverlappedSections)
    }

    @Test
    fun `Day 4 sample data line 0 does not has overlapped sections`() {
        val ecp = makeElfCleaningPair(sampleData[0])
        assertFalse(ecp.hasOverlappedSections)
    }

    @Test
    fun `Day 4 sample data has 4 with overlapped sections`() {
        assertEquals(4, sumPairsWithOverlappedSections(sampleData))
    }
}
