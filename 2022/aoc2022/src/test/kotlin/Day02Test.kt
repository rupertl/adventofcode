import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day02Test {

    @Test
    fun `Scissors beaten by Rock with score 7`() {
        assertEquals(7, RPSGame(RPSHand.Scissors, RPSHand.Rock).score)
    }

    @Test
    fun `Rock draws with Rock with score 4`() {
        assertEquals(4, RPSGame(RPSHand.Rock, RPSHand.Rock).score)
    }

    @Test
    fun `Paper beats Rock with score 1`() {
        assertEquals(1, RPSGame(RPSHand.Paper, RPSHand.Rock).score)
    }

    @Test
    fun `Paper beaten by Scissors with score 9`() {
        assertEquals(9, RPSGame(RPSHand.Paper, RPSHand.Scissors).score)
    }

    @Test
    fun `A is Rock`() {
        assertEquals(RPSHand.Rock.score, codeToHand('A').score)
    }

    @Test
    fun `Z is Scissors in part 1`() {
        assertEquals(RPSHand.Scissors.score, codeToHand('Z').score)
    }

    private val sampleInput = listOf("A Y", "B X", "C Z")

    @Test
    fun `Day 2 part 1 given test case`() {
        assertEquals(15, calculateScoreWithHands(sampleInput))
    }

    @Test
    fun `Day 2 part 2 given test case`() {
        assertEquals(12, calculateScoreWithResult(sampleInput))
    }
}
