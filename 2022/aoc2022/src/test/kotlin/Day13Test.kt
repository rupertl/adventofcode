import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day13Test {
    private val sampleData = listOf(
        "[1,1,3,1,1]",
        "[1,1,5,1,1]",
        "",
        "[[1],[2,3,4]]",
        "[[1],4]",
        "",
        "[9]",
        "[[8,7,6]]",
        "",
        "[[4,4],4,4]",
        "[[4,4],4,4,4]",
        "",
        "[7,7,7,7]",
        "[7,7,7]",
        "",
        "[]",
        "[3]",
        "",
        "[[[]]]",
        "[[]]",
        "",
        "[1,[2,[3,[4,[5,6,7]]]],8,9]",
        "[1,[2,[3,[4,[5,6,0]]]],8,9]",
        ""
    )

    private val expectedResults =
        listOf(true, true, false, true, false, true, false, false)

    @Test
    fun `Day 13 sample data 0`() {
        val n = 0
        assertEquals(
            expectedResults[0],
            Packet(sampleData[n * 3])
                .isCorrectOrder(Packet(sampleData[(n * 3) + 1]))
        )
    }

    @Test
    fun `Day 13 sample data rest`() {
        for (n in 1..7) {
            assertEquals(
                expectedResults[n],
                Packet(sampleData[n * 3])
                    .isCorrectOrder(Packet(sampleData[(n * 3) + 1]))
            )
        }
    }

    @Test
    fun `Day 13 sample data part 1 sum of correct orders`() {
        assertEquals(13, sumCorrectPacketOrderings(sampleData))
    }

    @Test
    fun `Day 13 sample data part 2`() {
        assertEquals(140, findDecoderPacketsProduct(sampleData))
    }
}
