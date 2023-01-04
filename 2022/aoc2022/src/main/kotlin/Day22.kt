// Advent of Code 2022 - Day 22: Monkey Map

// PART 1

// The monkeys take you on a surprisingly easy trail through the
// jungle. They're even going in roughly the right direction according
// to your handheld device's Grove Positioning System.

// As you walk, the monkeys explain that the grove is protected by a
// force field. To pass through the force field, you have to enter a
// password; doing so involves tracing a specific path on a
// strangely-shaped board.

// At least, you're pretty sure that's what you have to do; the
// elephants aren't exactly fluent in monkey.

// The monkeys give you notes that they took when they last saw the
// password entered (your puzzle input).

// For example:

//         ...#
//         .#..
//         #...
//         ....
// ...#.......#
// ........#...
// ..#....#....
// ..........#.
//         ...#....
//         .....#..
//         .#......
//         ......#.

// 10R5L5R10L4R5L5

// The first half of the monkeys' notes is a map of the board. It is
// comprised of a set of open tiles (on which you can move, drawn .)
// and solid walls (tiles which you cannot enter, drawn #).

// The second half is a description of the path you must follow. It
// consists of alternating numbers and letters:

// A number indicates the number of tiles to move in the direction
//     you are facing. If you run into a wall, you stop moving forward
//     and continue with the next instruction.
// A letter indicates whether to turn 90 degrees clockwise (R) or
//     counterclockwise (L). Turning happens in-place; it does not
//     change your current tile.

// So, a path like 10R5 means "go forward 10 tiles, then turn
// clockwise 90 degrees, then go forward 5 tiles".

// You begin the path in the leftmost open tile of the top row of
// tiles. Initially, you are facing to the right (from the perspective
// of how the map is drawn).

// If a movement instruction would take you off of the map, you wrap
// around to the other side of the board. In other words, if your next
// tile is off of the board, you should instead look in the direction
// opposite of your current facing as far as you can until you find
// the opposite edge of the board, then reappear there.

// For example, if you are at A and facing to the right, the tile in
// front of you is marked B; if you are at C and facing down, the tile
// in front of you is marked D:

//         ...#
//         .#..
//         #...
//         ....
// ...#.D.....#
// ........#...
// B.#....#...A
// .....C....#.
//         ...#....
//         .....#..
//         .#......
//         ......#.

// It is possible for the next tile (after wrapping around) to be a
// wall; this still counts as there being a wall in front of you, and
// so movement stops before you actually wrap to the other side of the
// board.

// By drawing the last facing you had with an arrow on each tile you
// visit, the full path taken by the above example looks like this:

//         >>v#    
//         .#v.    
//         #.v.    
//         ..v.    
// ...#...v..v#    
// >>>v...>#.>>    
// ..#v...#....    
// ...>>>>v..#.    
//         ...#....
//         .....#..
//         .#......
//         ......#.

// To finish providing the password to this strange input device, you
// need to determine numbers for your final row, column, and facing as
// your final position appears from the perspective of the original
// map. Rows start from 1 at the top and count downward; columns start
// from 1 at the left and count rightward. (In the above example, row
// 1, column 1 refers to the empty space with no tile on it in the
// top-left corner.) Facing is 0 for right (>), 1 for down (v), 2 for
// left (<), and 3 for up (^). The final password is the sum of 1000
// times the row, 4 times the column, and the facing.

// In the above example, the final row is 6, the final column is 8,
// and the final facing is 0. So, the final password is 1000 * 6 + 4 *
// 8 + 0: 6032.

// Follow the path given in the monkeys' notes. What is the final
// password?

// PART 2

// As you reach the force field, you think you hear some Elves in the
// distance. Perhaps they've already arrived?

// You approach the strange input device, but it isn't quite what the
// monkeys drew in their notes. Instead, you are met with a large
// cube; each of its six faces is a square of 50x50 tiles.

// To be fair, the monkeys' map does have six 50x50 regions on it. If
// you were to carefully fold the map, you should be able to shape it
// into a cube!

// In the example above, the six (smaller, 4x4) faces of the cube are:

//         1111
//         1111
//         1111
//         1111
// 222233334444
// 222233334444
// 222233334444
// 222233334444
//         55556666
//         55556666
//         55556666
//         55556666

// You still start in the same position and with the same facing as
// before, but the wrapping rules are different. Now, if you would
// walk off the board, you instead proceed around the cube. From the
// perspective of the map, this can look a little strange. In the
// above example, if you are at A and move to the right, you would
// arrive at B facing down; if you are at C and move down, you would
// arrive at D facing up:

//         ...#
//         .#..
//         #...
//         ....
// ...#.......#
// ........#..A
// ..#....#....
// .D........#.
//         ...#..B.
//         .....#..
//         .#......
//         ..C...#.

// Walls still block your path, even if they are on a different face
// of the cube. If you are at E facing up, your movement is blocked by
// the wall marked by the arrow:

//         ...#
//         .#..
//      -->#...
//         ....
// ...#..E....#
// ........#...
// ..#....#....
// ..........#.
//         ...#....
//         .....#..
//         .#......
//         ......#.

// Using the same method of drawing the last facing you had with an
// arrow on each tile you visit, the full path taken by the above
// example now looks like this:

//         >>v#    
//         .#v.    
//         #.v.    
//         ..v.    
// ...#..^...v#    
// .>>>>>^.#.>>    
// .^#....#....    
// .^........#.    
//         ...#..v.
//         .....#v.
//         .#v<<<<.
//         ..v...#.

// The final password is still calculated from your final position and
// facing from the perspective of the map. In this example, the final
// row is 5, the final column is 7, and the final facing is 3, so the
// final password is 1000 * 5 + 4 * 7 + 3 = 5031.

// Fold the map into a cube, then follow the path given in the
// monkeys' notes. What is the final password?

class Facing {
    private var heading = 90 // degrees, 0 = north

    fun turn(direction: String) {
        heading += parseDirection(direction)
        if (heading == 360) {
            heading = 0
        } else if (heading == -90) {
            heading = 270
        }
    }

    private fun parseDirection(direction: String): Int = when (direction) {
        "R" -> 90
        "L" -> -90
        else -> throw RuntimeException("Bad turn direction $direction")
    }

    fun dx(): Int = when (heading) {
        0, 180 -> 0
        90 -> 1
        270 -> -1
        else -> throw RuntimeException("Bad heading $heading for dx")
    }

    fun dy(): Int = when (heading) {
        90, 270 -> 0
        0 -> -1
        180 -> 1
        else -> throw RuntimeException("Bad heading $heading for dy")
    }

    fun password(): Int = when (heading) {
        0 -> 3
        else -> (heading / 90) - 1
    }
}

enum class Tile {
    EMPTY,
    OPEN,
    WALL,
}

data class RCPoint(var row: Int, var col: Int)

class Board(lines: List<String>, val is3D: Boolean = false) {
    private val extent = 250
    private val board = Array(extent * extent) { Tile.EMPTY }
    init {
        parseMap(lines)
    }
    val moves = parseMoves(lines)

    var facing = Facing()
    var position = getStartPosition()

    private fun getStartPosition(): RCPoint {
        val p = RCPoint(1, 1)
        while (get(p) != Tile.OPEN) {
            p.col++
        }
        return p
    }

    private fun pointToIndex(p: RCPoint): Int = (p.row * extent) + p.col
    fun get(p: RCPoint): Tile = board[pointToIndex(p)]
    private fun set(p: RCPoint, content: Tile) {
        board[pointToIndex(p)] = content
    }

    private fun parseMap(lines: List<String>) {
        val p = RCPoint(1, 1)
        for (line in lines) {
            if (line == "") {
                break
            }
            for (ch in line) {
                val content = when (ch) {
                    ' ' -> Tile.EMPTY
                    '.' -> Tile.OPEN
                    '#' -> Tile.WALL
                    else -> throw RuntimeException("Bad tile $ch")
                }
                set(p, content)
                p.col++
            }
            p.col = 1
            p.row++
        }
    }

    private fun parseMoves(lines: List<String>): List<String> {
        val line = lines.last()
        val m = mutableListOf<String>()
        var number = ""
        for (ch in line) {
            if (ch == 'L' || ch == 'R') {
                if (number != "") {
                    m.add(number)
                }
                m.add(ch.toString())
                number = ""
            } else {
                number += ch
            }
        }
        return m
    }

    fun moveOnce(): Boolean {
        var newPosition = position.copy()
        newPosition.col += facing.dx()
        newPosition.row += facing.dy()
        if (newPosition.row < 1 || newPosition.col < 1 ||
            get(newPosition) == Tile.EMPTY
        ) {
            newPosition = if (is3D) wrap3D() else wrap2D()
        }
        if (get(newPosition) == Tile.WALL) {
            // Stop
            return false
        }
        position = newPosition
        return true
    }

    private fun wrap2D(): RCPoint {
        val newPosition = position.copy()
        var lastPosition = newPosition.copy()
        val dx = facing.dx() * -1
        val dy = facing.dy() * -1
        while (get(newPosition) != Tile.EMPTY) {
            lastPosition = newPosition.copy()
            newPosition.col += dx
            newPosition.row += dy
        }
        return lastPosition
    }

    private fun wrap3D(): RCPoint {
        TODO("not done")
    }

    fun run(): Int {
        for (move in moves) {
            val dist = move.toIntOrNull()
            if (dist == null) {
                facing.turn(move)
            } else {
                for (index in 0 until dist) {
                    if (!moveOnce()) {
                        break
                    }
                }
            }
        }
        return password()
    }

    private fun password(): Int =
        (1000 * position.row) + (4 * position.col) + facing.password()
}

fun day22(input: String): String {
    val lines = input.trimEnd().lines()

    val part1 = Board(lines).run()
    val part2 = "(not done)" // Board(lines, is3D = true).run()

    return "$part1, $part2"
}
