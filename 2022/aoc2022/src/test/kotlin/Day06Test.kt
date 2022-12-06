import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day06Test {
    private val sampleData = mapOf(
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb"    to 7,
        "bvwbjplbgvbhsrlpgdmjqwftvncz"      to 5,
        "nppdvjthqldpwncqszvftbrmjlhg"      to 6,
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 10,
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"  to 11,
    )

    @Test
    fun `Day 6 sample data packet start`() {
        for ((key, value) in sampleData.entries) {
            assertEquals(value, findStartOfData(key))
        }
    }

    @Test
    fun `Day 6 sample data message start`() {
        assertEquals(19, findStartOfData("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 14))
    }
}
