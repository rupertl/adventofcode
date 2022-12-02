import java.lang.RuntimeException

// Advent of Code 2022 - Day 2: Rock Paper Scissors

// PART 1

// The Elves begin to set up camp on the beach. To decide whose tent
// gets to be closest to the snack storage, a giant Rock Paper
// Scissors tournament is already in progress.

// Rock Paper Scissors is a game between two players. Each game
// contains many rounds; in each round, the players each
// simultaneously choose one of Rock, Paper, or Scissors using a hand
// shape. Then, a winner for that round is selected: Rock defeats
// Scissors, Scissors defeats Paper, and Paper defeats Rock. If both
// players choose the same shape, the round instead ends in a draw.

// Appreciative of your help yesterday, one Elf gives you an encrypted
// strategy guide (your puzzle input) that they say will be sure to
// help you win. "The first column is what your opponent is going to
// play: A for Rock, B for Paper, and C for Scissors. The second
// column--" Suddenly, the Elf is called away to help with someone's
// tent.

// The second column, you reason, must be what you should play in
// response: X for Rock, Y for Paper, and Z for Scissors. Winning
// every time would be suspicious, so the responses must have been
// carefully chosen.

// The winner of the whole tournament is the player with the highest
// score. Your total score is the sum of your scores for each round.
// The score for a single round is the score for the shape you
// selected (1 for Rock, 2 for Paper, and 3 for Scissors) plus the
// score for the outcome of the round (0 if you lost, 3 if the round
// was a draw, and 6 if you won).

// Since you can't be sure if the Elf is trying to help you or trick
// you, you should calculate the score you would get if you were to
// follow the strategy guide.

// For example, suppose you were given the following strategy guide:

// A Y
// B X
// C Z

// This strategy guide predicts and recommends the following:

// In the first round, your opponent will choose Rock (A), and you
//     should choose Paper (Y). This ends in a win for you with a
//     score of 8 (2 because you chose Paper + 6 because you won).
// In the second round, your opponent will choose Paper (B), and
//     you should choose Rock (X). This ends in a loss for you with a
//     score of 1 (1 + 0).
// The third round is a draw with both players choosing Scissors,
//     giving you a score of 3 + 3 = 6.

// In this example, if you were to follow the strategy guide, you
// would get a total score of 15 (8 + 1 + 6).

// What would your total score be if everything goes exactly according
// to your strategy guide?

enum class RPSHand(val score: Int) {
    Rock(1),
    Paper(2),
    Scissors(3)
}

fun codeToHand(code: Char) = when (code) {
    'A' -> RPSHand.Rock
    'B' -> RPSHand.Paper
    'C' -> RPSHand.Scissors
    // Below for part 1 only
    'X' -> RPSHand.Rock
    'Y' -> RPSHand.Paper
    'Z' -> RPSHand.Scissors
    else -> throw RuntimeException("bad hand code $code")
}

enum class RPSResult(val score: Int) {
    Lose(0),
    Draw(3),
    Win(6)
}

class RPSGame(theirHand: RPSHand, myHand: RPSHand) {
    private val won = (theirHand == RPSHand.Rock && myHand == RPSHand.Paper) ||
        (theirHand == RPSHand.Paper && myHand == RPSHand.Scissors) ||
        (theirHand == RPSHand.Scissors && myHand == RPSHand.Rock)

    private val draw = theirHand == myHand

    val result = when {
        won -> RPSResult.Win
        draw -> RPSResult.Draw
        else -> RPSResult.Lose
    }

    val score = myHand.score + result.score
}

fun calculateScoreWithHands(plays: List<String>): Int {
    var score = 0
    for (play in plays) {
        score += RPSGame(codeToHand(play[0]), codeToHand(play[2])).score
    }
    return score
}

// PART 2

// The Elf finishes helping with the tent and sneaks back over to you.
// "Anyway, the second column says how the round needs to end: X means
// you need to lose, Y means you need to end the round in a draw, and
// Z means you need to win. Good luck!"

// The total score is still calculated in the same way, but now you
// need to figure out what shape to choose so the round ends as
// indicated. The example above now goes like this:

// In the first round, your opponent will choose Rock (A), and you
//     need the round to end in a draw (Y), so you also choose Rock.
//     This gives you a score of 1 + 3 = 4.
// In the second round, your opponent will choose Paper (B), and
//     you choose Rock so you lose (X) with a score of 1 + 0 = 1.
// In the third round, you will defeat your opponent's Scissors
//     with Rock for a score of 1 + 6 = 7.

// Now that you're correctly decrypting the ultra top secret strategy
// guide, you would get a total score of 12.

// Following the Elf's instructions for the second column, what would
// your total score be if everything goes exactly according to your
// strategy guide?

fun codeToResult(code: Char) = when (code) {
    'X' -> RPSResult.Lose
    'Y' -> RPSResult.Draw
    'Z' -> RPSResult.Win
    else -> throw RuntimeException("bad result code $code")
}

fun calculateScoreWithResult(plays: List<String>): Int {
    var score = 0
    for (play in plays) {
        val theirHand = codeToHand(play[0])
        val expectedResult = codeToResult(play[2])
        // This plays up to 3 games per line to check if the constraints
        // are met. It would be slightly quicker to have a look up table.
        for (myHand in listOf(RPSHand.Rock, RPSHand.Paper, RPSHand.Scissors)) {
            val game = RPSGame(theirHand, myHand)
            if (game.result == expectedResult) {
                score += game.score
                break
            }
        }
    }
    return score
}

fun day02(input: String): String {
    val plays = input.trim().lines()
    val part1Score = calculateScoreWithHands(plays)
    val part2Score = calculateScoreWithResult(plays)
    return "$part1Score, $part2Score"
}
