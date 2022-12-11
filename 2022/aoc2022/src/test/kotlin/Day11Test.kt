import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day11Test {
    private val sampleData = listOf(
        " Monkey 0:",
        "   Starting items: 79, 98",
        "   Operation: new = old * 19",
        "   Test: divisible by 23",
        "     If true: throw to monkey 2",
        "     If false: throw to monkey 3",
        "",
        " Monkey 1:",
        "   Starting items: 54, 65, 75, 74",
        "   Operation: new = old + 6",
        "   Test: divisible by 19",
        "     If true: throw to monkey 2",
        "     If false: throw to monkey 0",
        "",
        " Monkey 2:",
        "   Starting items: 79, 60, 97",
        "   Operation: new = old * old",
        "   Test: divisible by 13",
        "     If true: throw to monkey 1",
        "     If false: throw to monkey 3",
        "",
        " Monkey 3:",
        "   Starting items: 74",
        "   Operation: new = old + 3",
        "   Test: divisible by 17",
        "     If true: throw to monkey 0",
        "     If false: throw to monkey 1"
    )

    @Test
    fun `Day 11 sample data monkey 1 items`() {
        val mt = MonkeyTrouble()
        mt.parse(sampleData)

        assertEquals(listOf<Long>(54, 65, 75, 74), mt.monkeys[1].items)
    }

    @Test
    fun `Day 11 sample data monkey 0 operations`() {
        val mt = MonkeyTrouble()
        mt.parse(sampleData)

        assertEquals(Operator.TIMES, mt.monkeys[0].operator)
        assertEquals(19, mt.monkeys[0].operand)
    }

    @Test
    fun `Day 11 sample data monkey 3 test`() {
        val mt = MonkeyTrouble()
        mt.parse(sampleData)

        assertEquals(17, mt.monkeys[3].test)
        assertEquals(0, mt.monkeys[3].toTrue)
        assertEquals(1, mt.monkeys[3].toFalse)
    }

    // Monkey 0: 20, 23, 27, 26
    // Monkey 1: 2080, 25, 167, 207, 401, 1046
    // Monkey 2:
    // Monkey 3:
    @Test
    fun `Day 11 sample data after one round`() {
        val mt = MonkeyTrouble()
        mt.parse(sampleData)
        mt.doRound()

        assertEquals(listOf<Long>(20, 23, 27, 26), mt.monkeys[0].items)
        assertEquals(listOf<Long>(2080, 25, 167, 207, 401, 1046), mt.monkeys[1].items)
        assertTrue(mt.monkeys[2].items.size == 0)
        assertTrue(mt.monkeys[3].items.size == 0)
    }

    // Monkey 0 inspected items 101 times.
    // Monkey 1 inspected items 95 times.
    // Monkey 2 inspected items 7 times.
    // Monkey 3 inspected items 105 times.
    @Test
    fun `Day 11 sample data num inspections after 20 round`() {
        val mt = MonkeyTrouble()
        mt.parse(sampleData)
        mt.doRounds(20)

        assertEquals(101, mt.monkeys[0].inspections)
        assertEquals(95, mt.monkeys[1].inspections)
        assertEquals(7, mt.monkeys[2].inspections)
        assertEquals(105, mt.monkeys[3].inspections)
    }

    @Test
    fun `Day 11 sample data trouble score is 10605`() {
        val mt = MonkeyTrouble()
        mt.parse(sampleData)
        mt.doRounds(20)

        assertEquals(10605, mt.troubleScore())
    }

    @Test
    fun `Day 11 sample data trouble score with wd 1 after 1 round is 24`() {
        val mt = MonkeyTrouble(false)
        mt.parse(sampleData)
        mt.doRounds(1)

        assertEquals(24, mt.troubleScore())
    }

    // == After round 1000 ==
    // Monkey 0 inspected items 5204 times.
    // Monkey 1 inspected items 4792 times.
    // Monkey 2 inspected items 199 times.
    // Monkey 3 inspected items 5192 times.
    @Test
    fun `Day 11 sample data wd=1, r=1000 inspections`() {
        val mt = MonkeyTrouble(false)
        mt.parse(sampleData)
        mt.doRounds(1000)

        assertEquals(5204, mt.monkeys[0].inspections)
        assertEquals(4792, mt.monkeys[1].inspections)
        assertEquals(199, mt.monkeys[2].inspections)
        assertEquals(5192, mt.monkeys[3].inspections)
    }

    @Test
    fun `Day 11 sample data trouble score with wd=1 r=10000 is 2713310158`() {
        val mt = MonkeyTrouble(false)
        mt.parse(sampleData)
        mt.doRounds(10000)

        assertEquals(2713310158L, mt.troubleScore())
    }
}
