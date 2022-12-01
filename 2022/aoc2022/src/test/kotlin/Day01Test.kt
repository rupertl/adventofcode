import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day01Test {

    @Test
    fun `Find the elf with the most calories`() {
        val calories = countCalories(
            listOf(
                "10", "20", "",
                "400", "600", "",
                "700",
            ),
        )
        assertEquals(1000, findElfWithMostCalories(calories))
    }

    @Test
    fun `Find the sum of calories for the top 3 elves`() {
        val calories = countCalories(
            listOf(
                "10", "20", "",
                "400", "600", "",
                "70", "",
                "200", "300", "",
                "40"
            ),
        )
        assertEquals(1570, findSumTop3Elves(calories))
    }
}
