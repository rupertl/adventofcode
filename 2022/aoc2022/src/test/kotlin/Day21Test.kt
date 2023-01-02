
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day21Test {
    private val sampleData = listOf(
        "root: pppw + sjmn",
        "dbpl: 5",
        "cczh: sllz + lgvd",
        "zczc: 2",
        "ptdq: humn - dvpt",
        "dvpt: 3",
        "lfqf: 4",
        "humn: 5",
        "ljgn: 2",
        "sjmn: drzm * dbpl",
        "sllz: 4",
        "pppw: cczh / lfqf",
        "lgvd: ljgn * ptdq",
        "drzm: hmdt - zczc",
        "hmdt: 32",
    )

    @Test
    fun `Day 21 sample data parse`() {
        val mp = MonkeyPuzzle(sampleData)
        assertEquals(15, mp.monkeys.size)
    }

    @Test
    fun `Day 21 sample data root value`() {
        val mp = MonkeyPuzzle(sampleData)
        assertEquals(152, mp.getRoot())
    }

    @Test
    fun `Day 21 sample data part 2 solve`() {
        val mp = MonkeyPuzzle(sampleData, solveForHuman = true)
        assertEquals(301, mp.solve())
    }
}
