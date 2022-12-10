import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day10Test {
    private val sampleData = arrayOf(
        "addx 15",
        "addx -11",
        "addx 6",
        "addx -3",
        "addx 5",
        "addx -1",
        "addx -8",
        "addx 13",
        "addx 4",
        "noop",
        "addx -1",
        "addx 5",
        "addx -1",
        "addx 5",
        "addx -1",
        "addx 5",
        "addx -1",
        "addx 5",
        "addx -1",
        "addx -35",
        "addx 1",
        "addx 24",
        "addx -19",
        "addx 1",
        "addx 16",
        "addx -11",
        "noop",
        "noop",
        "addx 21",
        "addx -15",
        "noop",
        "noop",
        "addx -3",
        "addx 9",
        "addx 1",
        "addx -3",
        "addx 8",
        "addx 1",
        "addx 5",
        "noop",
        "noop",
        "noop",
        "noop",
        "noop",
        "addx -36",
        "noop",
        "addx 1",
        "addx 7",
        "noop",
        "noop",
        "noop",
        "addx 2",
        "addx 6",
        "noop",
        "noop",
        "noop",
        "noop",
        "noop",
        "addx 1",
        "noop",
        "noop",
        "addx 7",
        "addx 1",
        "noop",
        "addx -13",
        "addx 13",
        "addx 7",
        "noop",
        "addx 1",
        "addx -33",
        "noop",
        "noop",
        "noop",
        "addx 2",
        "noop",
        "noop",
        "noop",
        "addx 8",
        "noop",
        "addx -1",
        "addx 2",
        "addx 1",
        "noop",
        "addx 17",
        "addx -9",
        "addx 1",
        "addx 1",
        "addx -3",
        "addx 11",
        "noop",
        "noop",
        "addx 1",
        "noop",
        "addx 1",
        "noop",
        "noop",
        "addx -13",
        "addx -19",
        "addx 1",
        "addx 3",
        "addx 26",
        "addx -30",
        "addx 12",
        "addx -1",
        "addx 3",
        "addx 1",
        "noop",
        "noop",
        "noop",
        "addx -9",
        "addx 18",
        "addx 1",
        "addx 2",
        "noop",
        "noop",
        "addx 9",
        "noop",
        "noop",
        "noop",
        "addx -1",
        "addx 2",
        "addx -37",
        "addx 1",
        "addx 3",
        "noop",
        "addx 15",
        "addx -21",
        "addx 22",
        "addx -6",
        "addx 1",
        "noop",
        "addx 2",
        "addx 1",
        "noop",
        "addx -10",
        "noop",
        "noop",
        "addx 20",
        "addx 1",
        "addx 2",
        "addx 2",
        "addx -6",
        "addx -11",
        "noop",
        "noop",
        "noop"
    )

    private val expectedSignals = mapOf(
        60 to 1140,
        100 to 1800,
        140 to 2940,
        180 to 2880,
        220 to 3960
    )

    @Test
    fun `Day 10 sample data signal at t=20 is 420`() {
        val cpu = CPU()
        for (line in sampleData) {
            cpu.process(line)
        }

        assertEquals(420, cpu.signalAt(20))
    }

    @Test
    fun `Day 10 sample data signal at other times`() {
        val cpu = CPU()
        for (line in sampleData) {
            cpu.process(line)
        }

        for ((time, signal) in expectedSignals.entries) {
            assertEquals(signal, cpu.signalAt(time))
        }
    }

    @Test
    fun `Day 10 sample data sum of expected signals is 13140`() {
        val cpu = CPU()
        for (line in sampleData) {
            cpu.process(line)
        }
        assertEquals(13140, cpu.signalSum(20, 40))
    }

    private val part2Pattern =
            "##..##..##..##..##..##..##..##..##..##.." +
            "###...###...###...###...###...###...###." +
            "####....####....####....####....####...." +
            "#####.....#####.....#####.....#####....." +
            "######......######......######......####" +
            "#######.......#######.......#######....."

    @Test
    fun `Day 10 sample data part 2 pattern`() {
        val cpu = CPU()
        for (line in sampleData) {
            cpu.process(line)
        }
        assertEquals(part2Pattern, cpu.rawCrt())
    }
}
