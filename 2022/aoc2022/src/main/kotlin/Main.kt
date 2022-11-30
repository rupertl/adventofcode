import java.io.File

// Map day number to a function that will solve the problem.
val problems = mapOf(0 to ::day00)

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
        // Run all problems
        return problems.keys.toList()
    } else if (args.size == 1 && args[0].toIntOrNull() != null) {
        // Run a single problem if it exists
        val day = args[0].toInt()
        if (day in problems) {
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
        val result = problems[day]?.let { it(input) }
        println("$result")
    }
}
