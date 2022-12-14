package solutions.day02rockpaperscissor

import solutions.day02rockpaperscissor.model.MoveEnum
import solutions.day02rockpaperscissor.model.Outcome
import solutions.day02rockpaperscissor.logic.neededMove
import solutions.day02rockpaperscissor.logic.outcome
import retrieveRowsFromFile


fun computeTotalScore() : Int {
    val rows = retrieveRowsFromFile("inputday02.txt")

    return rows.map { it.split(" ") }.sumOf { (left, right) ->
        val myMove = MoveEnum.fromSymbol(right)
        outcome(
            myMove,
            MoveEnum.fromSymbol(left)
        ).score + myMove.score
    }
}

fun computeTotalScore2() : Int {
    val rows = retrieveRowsFromFile("inputday02.txt")

    return rows.map { it.split(" ") }.sumOf { (left, right) ->
        val neededOutcome = Outcome.fromSymbol(right)
        val myMove = neededMove(MoveEnum.fromSymbol(left), neededOutcome)
        neededOutcome.score + (myMove?.score ?: 0)
    }
}