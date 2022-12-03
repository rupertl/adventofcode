import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day03Test {

    @Test
    fun `Priority of p is 16`() {
        assertEquals(16, itemToPriority('p'))
    }

    @Test
    fun `Priority of L is 38`() {
        assertEquals(38, itemToPriority('L'))
    }

    private val sampleInput = listOf(
        "vJrwpWtwJgWrhcsFMMfFFhFp",
        "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
        "PmmdzqPrVvPwwTWBwg",
        "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
        "ttgJtRGJQctTZtZT",
        "CrZsJsPPZsGzwwsLwLmpwMDw"
    )

    @Test
    fun `Day 3 sample input 0 common item`() {
        assertEquals('p', commonLuggageItem(sampleInput[0]))
    }

    @Test
    fun `Day 3 sample input 2 common item`() {
        assertEquals('P', commonLuggageItem(sampleInput[2]))
    }

    @Test
    fun `Day 3 sample input sum of priorities of common items`() {
        assertEquals(157, findSumOfPrioritiesOfCommonItems(sampleInput))
    }

    @Test
    fun `Day 3 sample input sum of priorities of group badges`() {
        assertEquals(70, findSumOfPrioritiesOfBadges(sampleInput))
    }
}
