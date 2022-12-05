import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day05Test {
    //                        [Z]            [D]
    //     [D]                [N]            [N]
    // [N] [C]     ->         [D] or         [Z]
    // [Z] [M] [P]    [C] [M] [P]    [M] [C] [P]
    //  1   2   3      1   2   3      1   2   3
    //                 model 9000     model 9001
    private val sampleData = listOf(
        "ZN,MCD,P",
        "move 1 from 2 to 1",
        "move 3 from 1 to 3",
        "move 2 from 2 to 1",
        "move 1 from 1 to 2"
    )

    @Test
    fun `Day 5 sample data initial top of stacks is NDP`() {
        val ss = ShipStacks(sampleData.first())
        assertEquals("NDP", ss.getTops())
    }

    @Test
    fun `Day 5 sample data top of stacks after moves is CMZ`() {
        val ss = ShipStacks(sampleData.first())
        for (line in sampleData.drop(1)) {
            ss.move(line)
        }
        assertEquals("CMZ", ss.getTops())
    }

    @Test
    fun `Day 5 sample data top of stacks after multi-moves is MCD`() {
        val ss = ShipStacks(sampleData.first(), model = 9001)
        for (line in sampleData.drop(1)) {
            ss.move(line)
        }
        assertEquals("MCD", ss.getTops())
    }
}
