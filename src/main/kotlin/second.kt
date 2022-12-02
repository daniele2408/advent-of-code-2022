import model.MoveEnum
import model.Outcome
import model.neededMove
import model.outcome


fun computeTotalScore() : Int {
    val rows = retrieveRowsFromFile()

    return rows.map { it.split(" ") }.sumOf { (left, right) ->
        val myMove = MoveEnum.fromSymbol(right)
        outcome(
            myMove,
            MoveEnum.fromSymbol(left)
        ).score + myMove.score
    }
}

fun computeTotalScore2() : Int {
    val rows = retrieveRowsFromFile()

    return rows.map { it.split(" ") }.sumOf { (left, right) ->
        val neededOutcome = Outcome.fromSymbol(right)
        val myMove = neededMove(MoveEnum.fromSymbol(left), neededOutcome)
        neededOutcome.score + (myMove?.score ?: 0)
    }
}

private fun retrieveRowsFromFile(): List<String> {
    val contentMatches = getResourceAsText("inputday2.txt")
    val rows = contentMatches?.split("\n") ?: emptyList()
    return rows
}