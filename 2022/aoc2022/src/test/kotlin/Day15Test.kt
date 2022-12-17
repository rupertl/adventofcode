
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day15Test {
    private val sampleData = listOf(
        "Sensor at x=2, y=18: closest beacon is at x=-2, y=15",
        "Sensor at x=9, y=16: closest beacon is at x=10, y=16",
        "Sensor at x=13, y=2: closest beacon is at x=15, y=3",
        "Sensor at x=12, y=14: closest beacon is at x=10, y=16",
        "Sensor at x=10, y=20: closest beacon is at x=10, y=16",
        "Sensor at x=14, y=17: closest beacon is at x=10, y=16",
        "Sensor at x=8, y=7: closest beacon is at x=2, y=10",
        "Sensor at x=2, y=0: closest beacon is at x=2, y=10",
        "Sensor at x=0, y=11: closest beacon is at x=2, y=10",
        "Sensor at x=20, y=14: closest beacon is at x=25, y=17",
        "Sensor at x=17, y=20: closest beacon is at x=21, y=22",
        "Sensor at x=16, y=7: closest beacon is at x=15, y=3",
        "Sensor at x=14, y=3: closest beacon is at x=15, y=3",
        "Sensor at x=20, y=1: closest beacon is at x=15, y=3"
    )

    @Test
    fun `Day 15 sample data points`() {
        val r = RescueZone(sampleData)
        val s = r.deployedSensors[0]
        assertEquals(Point(18, 2), s.sensor)
        assertEquals(Point(15, -2), s.beacon)
    }

    //  Sensor at x=8, y=7: closest beacon is at x=2, y=10
    @Test
    fun `Day 15 sample data range`() {
        val r = RescueZone(sampleData)
        val s = r.deployedSensors[6]
        assertEquals(9, s.range)
    }

    @Test
    fun `Day 15 sample data extents`() {
        val r = RescueZone(sampleData)
        assertEquals(-2, r.minCol)
        assertEquals(25, r.maxCol)
        assertEquals(0, r.minRow)
        assertEquals(22, r.maxRow)
    }

    @Test
    fun `Day 15 sample data no beacons at row 10`() {
        assertEquals(26, RescueZone(sampleData).countNoBeacons(10))
    }

    @Test
    fun `Day 15 sample data distress beacon tuning frequency`() {
        assertEquals(
            56000011,
            RescueZone(sampleData).findDistressBeacon(20)
        )
    }
}
