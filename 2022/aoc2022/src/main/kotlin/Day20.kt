// Advent of Code 2022 - Day 20: Grove Positioning System

// PART 1

// It's finally time to meet back up with the Elves. When you try to
// contact them, however, you get no reply. Perhaps you're out of
// range?

// You know they're headed to the grove where the star fruit grows, so
// if you can figure out where that is, you should be able to meet
// back up with them.

// Fortunately, your handheld device has a file (your puzzle input)
// that contains the grove's coordinates! Unfortunately, the file is
// encrypted - just in case the device were to fall into the wrong
// hands.

// Maybe you can decrypt it?

// When you were still back at the camp, you overheard some Elves
// talking about coordinate file encryption. The main operation
// involved in decrypting the file is called mixing.

// The encrypted file is a list of numbers. To mix the file, move each
// number forward or backward in the file a number of positions equal
// to the value of the number being moved. The list is circular, so
// moving a number off one end of the list wraps back around to the
// other end as if the ends were connected.

// For example, to move the 1 in a sequence like 4, 5, 6, 1, 7, 8, 9,
// the 1 moves one position forward: 4, 5, 6, 7, 1, 8, 9. To move the
// -2 in a sequence like 4, -2, 5, 6, 7, 8, 9, the -2 moves two
// positions backward, wrapping around: 4, 5, 6, 7, 8, -2, 9.

// The numbers should be moved in the order they originally appear in
// the encrypted file. Numbers moving around during the mixing process
// do not change the order in which the numbers are moved.

// Consider this encrypted file:

// 1
// 2
// -3
// 3
// -2
// 0
// 4

// Mixing this file proceeds as follows:

// Initial arrangement:
// 1, 2, -3, 3, -2, 0, 4

// 1 moves between 2 and -3:
// 2, 1, -3, 3, -2, 0, 4

// 2 moves between -3 and 3:
// 1, -3, 2, 3, -2, 0, 4

// -3 moves between -2 and 0:
// 1, 2, 3, -2, -3, 0, 4

// 3 moves between 0 and 4:
// 1, 2, -2, -3, 0, 3, 4

// -2 moves between 4 and 1:
// 1, 2, -3, 0, 3, 4, -2

// 0 does not move:
// 1, 2, -3, 0, 3, 4, -2

// 4 moves between -3 and 0:
// 1, 2, -3, 4, 0, 3, -2

// Then, the grove coordinates can be found by looking at the 1000th,
// 2000th, and 3000th numbers after the value 0, wrapping around the
// list as necessary. In the above example, the 1000th number after 0
// is 4, the 2000th is -3, and the 3000th is 2; adding these together
// produces 3.

// Mix your encrypted file exactly once. What is the sum of the three
// numbers that form the grove coordinates?

// PART 2

// The grove coordinate values seem nonsensical. While you ponder the
// mysteries of Elf encryption, you suddenly remember the rest of the
// decryption routine you overheard back at camp.

// First, you need to apply the decryption key, 811589153. Multiply
// each number by the decryption key before you begin; this will
// produce the actual list of numbers to mix.

// Second, you need to mix the list of numbers ten times. The order in
// which the numbers are mixed does not change during mixing; the
// numbers are still moved in the order they appeared in the original,
// pre-mixed list. (So, if -3 appears fourth in the original list of
// numbers to mix, -3 will be the fourth number to move during each
// round of mixing.)

// Using the same example as above:

// Initial arrangement:
// 811589153, 1623178306, -2434767459, 2434767459, -1623178306, 0, 3246356612

// After 1 round of mixing:
// 0, -2434767459, 3246356612, -1623178306, 2434767459, 1623178306, 811589153

// After 2 rounds of mixing:
// 0, 2434767459, 1623178306, 3246356612, -2434767459, -1623178306, 811589153

// After 3 rounds of mixing:
// 0, 811589153, 2434767459, 3246356612, 1623178306, -1623178306, -2434767459

// After 4 rounds of mixing:
// 0, 1623178306, -2434767459, 811589153, 2434767459, 3246356612, -1623178306

// After 5 rounds of mixing:
// 0, 811589153, -1623178306, 1623178306, -2434767459, 3246356612, 2434767459

// After 6 rounds of mixing:
// 0, 811589153, -1623178306, 3246356612, -2434767459, 1623178306, 2434767459

// After 7 rounds of mixing:
// 0, -2434767459, 2434767459, 1623178306, -1623178306, 811589153, 3246356612

// After 8 rounds of mixing:
// 0, 1623178306, 3246356612, 811589153, -2434767459, 2434767459, -1623178306

// After 9 rounds of mixing:
// 0, 811589153, 1623178306, -2434767459, 3246356612, 2434767459, -1623178306

// After 10 rounds of mixing:
// 0, -2434767459, 1623178306, 3246356612, -1623178306, 2434767459, 811589153

// The grove coordinates can still be found in the same way. Here, the
// 1000th number after 0 is 811589153, the 2000th is 2434767459, and
// the 3000th is -1623178306; adding these together produces
// 1623178306.

// Apply the decryption key and mix your encrypted file ten times.
// What is the sum of the three numbers that form the grove
// coordinates?

class CoordinateNode(
    val value: Long,
    var next: CoordinateNode? = null,
    var prev: CoordinateNode? = null,
)

// Doubly linked circular list also holding in 'nodes' the original order of items
class CoordinateFile(lines: List<String>, key: Long = 1) {
    private val nodes = lines.map { CoordinateNode(it.toLong() * key) }
    private val zeroNode = linkAndFindZeroNode()

    private fun linkAndFindZeroNode(): CoordinateNode {
        var z: CoordinateNode? = null
        var previous = nodes.first()
        for (node in nodes) {
            if (node.value == 0L) {
                z = node
            }
            if (node == previous) {
                continue
            }
            previous.next = node
            node.prev = previous
            previous = node
        }
        previous.next = nodes.first()
        nodes.first().prev = previous
        if (z != null) {
            return z
        }
        throw RuntimeException("No zero node")
    }

    // Treating 0 as the first item in the list, return all the values in order.
    fun nodesFromZero(): List<Long> {
        val result = mutableListOf<Long>()
        var node = zeroNode
        do {
            result.add(node.value)
            node = node.next!!
        } while (node != zeroNode)
        return result
    }

    fun mix() {
        for (node in nodes) {
            val dist = node.value % (nodes.size - 1)
            val new = find(node, dist)
            when {
                dist > 0 -> spliceAfter(node, new)
                dist < 0 -> spliceBefore(node, new)
            }
        }
    }

    private fun find(node: CoordinateNode, dist: Long): CoordinateNode {
        var new = node
        if (dist > 0) {
            for (index in 0 until dist) {
                new = new.next!!
            }
        } else if (dist < 0) {
            for (index in dist until 0) {
                new = new.prev!!
            }
        }
        return new
    }

    private fun unlink(node: CoordinateNode) {
        // Relink the nodes before and after to remove 'node'
        node.prev!!.next = node.next
        node.next!!.prev = node.prev
    }

    private fun spliceAfter(node: CoordinateNode, after: CoordinateNode) {
        // Add 'node' as the successor to 'after'
        unlink(node)
        node.prev = after
        node.next = after.next
        node.next!!.prev = node
        after.next = node
    }

    private fun spliceBefore(node: CoordinateNode, before: CoordinateNode) {
        unlink(node)
        // Add 'node' as the predecessor of 'before'
        node.next = before
        node.prev = before.prev
        node.prev!!.next = node
        before.prev = node
    }

    fun decrypt(numMixes: Int = 1): Long {
        repeat(numMixes) { mix() }
        var result = 0L
        val dist = 1000L
        var node = zeroNode
        for (i in 1..3) {
            node = find(node, dist)
            result += node.value
        }
        return result
    }
}

const val KEY_20_2 = 811589153L

fun day20(input: String): String {
    val lines = input.trim().lines()

    val part1 = CoordinateFile(lines).decrypt()
    val part2 = CoordinateFile(lines, key = KEY_20_2).decrypt(numMixes = 10)

    return "$part1, $part2"
}
