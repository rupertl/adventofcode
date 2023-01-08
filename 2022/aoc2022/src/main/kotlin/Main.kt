import java.io.File

val problems = arrayOf(
    ::day00, ::day01, ::day02, ::day03, ::day04, ::day05, ::day06,
    ::day07, ::day08, ::day09, ::day10, ::day11, ::day12, ::day13,
    ::day14, ::day15, ::day16, ::day17, ::day18, ::day19, ::day20,
    ::day21, ::day22, ::day23, ::day24, ::day25,
)

fun readFile(fileName: String) = File(fileName).readText()

fun usage() {
    println("usage: problem-number")
    println("if no problem number is supplied, run all problems")
    kotlin.system.exitProcess(1)
}

// Look at the command line args and return a list of day numbers
// for problems to solve.
fun getDaysFromArgs(args: Array<String>) : List<Int> {
    if (args.isEmpty()) {
        // Run all problems except test day 0
        return problems.indices.drop(1)
    } else if (args.size == 1 && args[0].toIntOrNull() != null) {
        // Run a single problem if it exists
        val day = args[0].toInt()
        if (day in 0..25) {
            return listOf(day)
        }
    }
    return listOf()
}

fun main(args: Array<String>) {
    val days = getDaysFromArgs(args)
    if (days.isEmpty()) {
        usage()
    }
    for (day in days) {
        val input = readFile("src/main/resources/day%02d.in".format(day))
        print("Day $day: ")
        val result = problems[day](input)
        println(result)
    }
}
