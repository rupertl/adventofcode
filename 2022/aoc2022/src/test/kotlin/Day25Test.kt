import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day25Test {
    private val sampleSnafus = listOf(
        "1=-0-2",
        "12111",
        "2=0=",
        "21",
        "2=01",
        "111",
        "20012",
        "112",
        "1=-1=",
        "1-12",
        "12",
        "1=",
        "122",
    )

    private val sampleLongs = listOf<Long>(
        1747,
        906,
        198,
        11,
        201,
        31,
        1257,
        32,
        353,
        107,
        7,
        3,
        37,
    )

    @Test
    fun `Day 25 sample data convert all snafu to long`() {
        for (index in sampleSnafus.indices) {
            assertEquals(sampleLongs[index], snafuToLong(sampleSnafus[index]))
        }
    }

    @Test
    fun `Day 25 sample data convert all longs to snafus`() {
        for (index in sampleSnafus.indices) {
            assertEquals(sampleSnafus[index], longToSnafu(sampleLongs[index]))
        }
    }

    @Test
    fun `Day 25 sample data fuel numbers sum`() {
        assertEquals("2=-1=0", Bob(sampleSnafus).sum())
    }
}
