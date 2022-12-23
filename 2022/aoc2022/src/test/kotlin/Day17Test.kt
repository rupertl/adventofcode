import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day17Test {
    private val sampleData =
        ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"

    @Test
    fun `Day 16 sample data gusts`() {
        val rc = RockChamber(sampleData)
        assertEquals(RockChamber.Direction.RIGHT, rc.nextGust())
        assertEquals(RockChamber.Direction.RIGHT, rc.nextGust())
        assertEquals(RockChamber.Direction.RIGHT, rc.nextGust())
        assertEquals(RockChamber.Direction.LEFT, rc.nextGust())
    }

    // |..####.|
    // +-------+
    @Test
    fun `Day 16 sample data after 1 rock dropped`() {
        val rc = RockChamber(sampleData)
        rc.addRock()
        assertEquals(0b0011110, rc.getChamberRow(0))
    }

    // |...#...|
    // |..###..|
    // |...#...|
    // |..####.|
    // +-------+
    @Test
    fun `Day 16 sample data after 2 rocks dropped`() {
        val rc = RockChamber(sampleData)
        rc.addRocks(2)
        assertEquals(0b0011110, rc.getChamberRow(0))
        assertEquals(0b0001000, rc.getChamberRow(1))
        assertEquals(0b0011100, rc.getChamberRow(2))
        assertEquals(0b0001000, rc.getChamberRow(3))
        assertEquals(4, rc.getRockLevel())
    }

    @Test
    fun `Day 16 sample data after 2022 rocks dropped`() {
        val rc = RockChamber(sampleData)
        rc.addRocks(2022)
        assertEquals(3068, rc.getRockLevel())
    }

    //
    @Test
    fun `Day 16 sample data after 2022 rocks dropped, the part 2 way`() {
        // Assumes cycle for sample data is < 2022
        val rc = RockChamber(sampleData)
        assertEquals(3068, rc.findLevelAfterRocks(2022))
    }

    //
    @Test
    fun `Day 16 sample data after 1000000000000 rocks dropped`() {
        val rc = RockChamber(sampleData)
        assertEquals(1514285714288, rc.findLevelAfterRocks(1000000000000))
    }
}
