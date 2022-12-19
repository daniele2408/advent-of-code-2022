package solutions.day02rockpaperscissor.logic

import solutions.day02rockpaperscissor.model.MoveEnum
import solutions.day02rockpaperscissor.model.Outcome

private val rules = mapOf(
    MoveEnum.ROCK to MoveEnum.SCISSOR,
    MoveEnum.SCISSOR to MoveEnum.PAPER,
    MoveEnum.PAPER to MoveEnum.ROCK
)

fun outcome(moveA: MoveEnum, moveB: MoveEnum) : Outcome {
    val res = when {
        moveA == moveB -> Outcome.DRAW
        rules[moveA] == moveB -> Outcome.WIN
        else -> Outcome.LOSE
    }
    return res
}

fun neededMove(moveA: MoveEnum, outcome: Outcome) : MoveEnum? {
    val res = when (outcome) {
        Outcome.DRAW -> moveA
        Outcome.LOSE -> rules[moveA]
        else -> rules.map { it.value to it.key }.toMap()[moveA]
    }
    return res
}
