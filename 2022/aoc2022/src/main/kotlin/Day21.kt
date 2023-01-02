// Advent of Code 2022 - Day 21: Monkey Math

// PART 1

// The monkeys are back! You're worried they're going to try to steal
// your stuff again, but it seems like they're just holding their
// ground and making various monkey noises at you.

// Eventually, one of the elephants realizes you don't speak monkey
// and comes over to interpret. As it turns out, they overheard you
// talking about trying to find the grove; they can show you a
// shortcut if you answer their riddle.

// Each monkey is given a job: either to yell a specific number or to
// yell the result of a math operation. All of the number-yelling
// monkeys know their number from the start; however, the math
// operation monkeys need to wait for two other monkeys to yell a
// number, and those two other monkeys might also be waiting on other
// monkeys.

// Your job is to work out the number the monkey named root will yell
// before the monkeys figure it out themselves.

// For example:

// root: pppw + sjmn
// dbpl: 5
// cczh: sllz + lgvd
// zczc: 2
// ptdq: humn - dvpt
// dvpt: 3
// lfqf: 4
// humn: 5
// ljgn: 2
// sjmn: drzm * dbpl
// sllz: 4
// pppw: cczh / lfqf
// lgvd: ljgn * ptdq
// drzm: hmdt - zczc
// hmdt: 32

// Each line contains the name of a monkey, a colon, and then the job
// of that monkey:

// A lone number means the monkey's job is simply to yell that number.
// A job like aaaa + bbbb means the monkey waits for monkeys aaaa
//     and bbbb to yell each of their numbers; the monkey then yells
//     the sum of those two numbers.
// aaaa - bbbb means the monkey yells aaaa's number minus bbbb's number.
// Job aaaa * bbbb will yell aaaa's number multiplied by bbbb's number.
// Job aaaa / bbbb will yell aaaa's number divided by bbbb's number.

// So, in the above example, monkey drzm has to wait for monkeys hmdt
// and zczc to yell their numbers. Fortunately, both hmdt and zczc
// have jobs that involve simply yelling a single number, so they do
// this immediately: 32 and 2. Monkey drzm can then yell its number by
// finding 32 minus 2: 30.

// Then, monkey sjmn has one of its numbers (30, from monkey drzm),
// and already has its other number, 5, from dbpl. This allows it to
// yell its own number by finding 30 multiplied by 5: 150.

// This process continues until root yells a number: 152.

// However, your actual situation involves considerably more monkeys.
// What number will the monkey named root yell?

// PART 2

// Due to some kind of monkey-elephant-human mistranslation, you seem
// to have misunderstood a few key details about the riddle.

// First, you got the wrong job for the monkey named root;
// specifically, you got the wrong math operation. The correct
// operation for monkey root should be =, which means that it still
// listens for two numbers (from the same two monkeys as before), but
// now checks that the two numbers match.

// Second, you got the wrong monkey for the job starting with humn:.
// It isn't a monkey - it's you. Actually, you got the job wrong, too:
// you need to figure out what number you need to yell so that root's
// equality check passes. (The number that appears after humn: in your
// input is now irrelevant.)

// In the above example, the number you need to yell to pass root's
// equality test is 301. (This causes root to get the same number,
// 150, from both of its monkeys.)

// What number do you yell to pass root's equality test?
enum class MonkeyOp {
    INVALID,
    NUMBER,
    PLUS,
    MINUS,
    TIMES,
    DIVIDE,
    EQUALS,
    UNKNOWN,
}

class MonkeyJob(line: String) {
    var name: String = ""
    var op: MonkeyOp = MonkeyOp.INVALID
    var value: Long = 0L
    var arg1: String = ""
    var arg2: String = ""

    init {
        parse(line)
    }

    fun hasArgs(): Boolean = op == MonkeyOp.PLUS || op == MonkeyOp.MINUS ||
        op == MonkeyOp.TIMES ||
        op == MonkeyOp.DIVIDE || op == MonkeyOp.EQUALS

    private fun parse(line: String) {
        val m = Regex("(\\w+): ((\\d+)|((\\w+) ([+*-/]) (\\w+)))")
            .find(line)
        if (m == null) {
            throw RuntimeException("Can't parse $line")
        } else {
            name = m.groupValues[1]
            if (m.groupValues[3].isNotEmpty()) {
                value = m.groupValues[3].toLong()
                op = MonkeyOp.NUMBER
            } else {
                arg1 = m.groupValues[5]
                arg2 = m.groupValues[7]
                op = when (m.groupValues[6]) {
                    "*" -> MonkeyOp.TIMES
                    "/" -> MonkeyOp.DIVIDE
                    "-" -> MonkeyOp.MINUS
                    "+" -> MonkeyOp.PLUS
                    else -> MonkeyOp.INVALID
                }
            }
        }
        if (op == MonkeyOp.INVALID) {
            throw RuntimeException("Can't identify op in $line")
        }
    }

    override fun toString(): String {
        return "Job: $name: $value ($op $arg1 $arg2)"
    }
}

class MonkeyPuzzle(lines: List<String>, val solveForHuman: Boolean = false) {
    private val humanName = "humn"
    private val rootName = "root"
    val monkeys = mutableMapOf<String, MonkeyJob>()
    private val rootMonkey = parse(lines)

    private fun parse(lines: List<String>): MonkeyJob {
        for (line in lines) {
            val m = MonkeyJob(line)
            if (m.name == humanName && solveForHuman) {
                m.op = MonkeyOp.UNKNOWN
            }
            monkeys[m.name] = m
        }

        val mr = monkeys[rootName] ?: throw RuntimeException("No root monkey")
        if (solveForHuman) {
            mr.op = MonkeyOp.EQUALS
        }
        return mr
    }

    // Get the value by evaluating the tree of operations
    fun getRoot(): Long = get(rootMonkey)

    private fun get(m: MonkeyJob): Long {
        if (m.op == MonkeyOp.NUMBER) {
            return m.value
        }
        val arg1 = get(monkeys[m.arg1]!!)
        val arg2 = get(monkeys[m.arg2]!!)
        return eval(m.op, arg1, arg2)
    }

    private fun eval(op: MonkeyOp, arg1: Long, arg2: Long): Long = when (op) {
        MonkeyOp.PLUS -> arg1 + arg2
        MonkeyOp.MINUS -> arg1 - arg2
        MonkeyOp.TIMES -> arg1 * arg2
        MonkeyOp.DIVIDE -> arg1 / arg2
        MonkeyOp.EQUALS -> if (arg1 == arg2) 1 else 0
        else -> throw RuntimeException("Bad operator")
    }

    // Reduce the tree by replacing any operations with numbers
    // when we know the value of each arg
    private fun simplify(m: MonkeyJob = rootMonkey) {
        if (m.hasArgs()) {
            val arg1 = monkeys[m.arg1]!!
            simplify(arg1)
            val arg2 = monkeys[m.arg2]!!
            simplify(arg2)
            if (arg1.op == MonkeyOp.NUMBER && arg2.op == MonkeyOp.NUMBER) {
                m.value = eval(m.op, arg1.value, arg2.value)
                m.op = MonkeyOp.NUMBER
            }
        }
    }

    fun solve(): Long {
        simplify()
        var m = rootMonkey
        var acc: Long
        // Set the accumulator to the solved side of the equality
        // and m to the other side
        if (monkeys[m.arg1]!!.op == MonkeyOp.NUMBER) {
            acc = monkeys[m.arg1]!!.value
            m = monkeys[m.arg2]!!
        } else {
            acc = monkeys[m.arg2]!!.value
            m = monkeys[m.arg1]!!
        }
        // Keep evaluating the operations to update acc until we find human
        while (m.name != humanName) {
            val lhs = monkeys[m.arg1]!!
            val rhs = monkeys[m.arg2]!!
            if (m.op == MonkeyOp.PLUS) {
                if (lhs.op == MonkeyOp.NUMBER) {
                    acc -= lhs.value
                    m = rhs
                } else {
                    acc -= rhs.value
                    m = lhs
                }
            } else if (m.op == MonkeyOp.MINUS) {
                if (lhs.op == MonkeyOp.NUMBER) {
                    acc -= lhs.value
                    acc *= -1
                    m = rhs
                } else {
                    acc += rhs.value
                    m = lhs
                }
            } else if (m.op == MonkeyOp.TIMES) {
                if (lhs.op == MonkeyOp.NUMBER) {
                    acc /= lhs.value
                    m = rhs
                } else {
                    acc /= rhs.value
                    m = lhs
                }
            } else if (m.op == MonkeyOp.DIVIDE) {
                if (lhs.op == MonkeyOp.NUMBER) {
                    acc = lhs.value / acc
                    m = rhs
                } else {
                    acc *= rhs.value
                    m = lhs
                }
            } else {
                throw RuntimeException("Unexpected branch at $m")
            }
        }
        return acc
    }
}

fun day21(input: String): String {
    val lines = input.trim().lines()

    val part1 = MonkeyPuzzle(lines).getRoot()

    val mp2 = MonkeyPuzzle(lines, solveForHuman = true)
    val part2 = mp2.solve()

    return "$part1, $part2"
}
