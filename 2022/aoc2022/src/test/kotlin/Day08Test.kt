import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day08Test {
    private val sampleData = arrayOf(
        "30373",
        "25512",
        "65332",
        "33549",
        "35390"
    )

    @Test
    fun `Day 8 sample data tree at 1,3 is 3`() {
        val tg = TreeGrid(sampleData)
        assertEquals(3, tg.heightAt(1, 3))
    }

    @Test
    fun `Day 8 sample data tree at 3,1 is 1`() {
        val tg = TreeGrid(sampleData)
        assertEquals(1, tg.heightAt(3, 1))
    }

    @Test
    fun `Day 8 sample data tree at 1,1 is visible`() {
        val tg = TreeGrid(sampleData)
        assertTrue(tg.treeVisible(1, 1))
    }

    @Test
    fun `Day 8 sample data tree at 2,1 is visible`() {
        val tg = TreeGrid(sampleData)
        assertTrue(tg.treeVisible(2, 1))
    }

    @Test
    fun `Day 8 sample data tree at 3,1 is not visible`() {
        val tg = TreeGrid(sampleData)
        assertFalse(tg.treeVisible(3, 1))
    }

    @Test
    fun `Day 8 sample data tree at 2,2 is not visible`() {
        val tg = TreeGrid(sampleData)
        assertFalse(tg.treeVisible(2, 2))
    }

    @Test
    fun `Day 8 sample data tree at 3,2 is visible`() {
        val tg = TreeGrid(sampleData)
        assertTrue(tg.treeVisible(3, 2))
    }

    @Test
    fun `Day 8 sample data bottom row, the middle 5 is visible, others are not`() {
        val tg = TreeGrid(sampleData)
        assertFalse(tg.treeVisible(1, 3))
        assertTrue(tg.treeVisible(2, 3))
        assertFalse(tg.treeVisible(3, 3))
    }

    @Test
    fun `Day 8 sample data has 21 visible trees`() {
        val tg = TreeGrid(sampleData)
        assertEquals(21, tg.numVisible())
    }

    @Test
    fun `Day 8 sample data tree 2,1 has scenic score of 4`() {
        val tg = TreeGrid(sampleData)
        assertEquals(4, tg.scenicScore(2,1))
    }

    @Test
    fun `Day 8 sample data tree 2,3 has scenic score of 8`() {
        val tg = TreeGrid(sampleData)
        assertEquals(8, tg.scenicScore(2,3))
    }

    @Test
    fun `Day 8 sample data most scenic score is 8`() {
        val tg = TreeGrid(sampleData)
        assertEquals(8, tg.mostScenicScore())
    }
}
