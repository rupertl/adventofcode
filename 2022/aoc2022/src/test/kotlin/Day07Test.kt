import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day07Test {
    private val sampleData = listOf(
        "$ cd /",
        "$ ls",
        "dir a",
        "14848514 b.txt",
        "8504156 c.dat",
        "dir d",
        "$ cd a",
        "$ ls",
        "dir e",
        "29116 f",
        "2557 g",
        "62596 h.lst",
        "$ cd e",
        "$ ls",
        "584 i",
        "$ cd ..",
        "$ cd ..",
        "$ cd d",
        "$ ls",
        "4060174 j",
        "8033020 d.log",
        "5626152 d.ext",
        "7214296 k"
    )

    @Test
    fun `Day 6 sample data root has a and d dirs`() {
        val root = parseFileActivity(sampleData)
        assertNotNull(root.findDir("a"))
        assertNotNull(root.findDir("d"))
    }

    @Test
    fun `Day 6 sample data d dir size is 24933642`() {
        val root = parseFileActivity(sampleData)
        assertEquals(24933642, root.findDir("d")!!.dirSize())
    }

    @Test
    fun `Day 6 sample data a dir size is 94853`() {
        val root = parseFileActivity(sampleData)
        assertEquals(94853, root.findDir("a")!!.dirSize())
    }

    @Test
    fun `Day 6 sample data root dir size is 48381165`() {
        val root = parseFileActivity(sampleData)
        assertEquals(48381165, root.dirSize())
    }

    @Test
    fun `Day 6 sample data part 1 criteria`() {
        val root = parseFileActivity(sampleData)
        assertEquals(95437, findSumAllDirectoriesBelowSize(root))
    }

    @Test
    fun `Day 6 sample data part 2 criteria`() {
        val root = parseFileActivity(sampleData)
        assertEquals(24933642, findSmallestCandidateDirectory(root))
    }
}
