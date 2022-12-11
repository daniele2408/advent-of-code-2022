package solutions.day11monkeyinthemiddle

import retrieveRowsFromFile
import solutions.day11monkeyinthemiddle.logic.Circus
import solutions.day11monkeyinthemiddle.logic.WorryOperation
import solutions.day11monkeyinthemiddle.model.WorryAction

fun levelOfMonkeyBusinessAfterNRounds(rounds : Int) : Int {

    val rows = retrieveRowsFromFile("inputday11.txt")

    val circus = Circus.from(rows, WorryAction(WorryOperation.DIVIDE, 3))

    circus.runRounds(rounds)

    return circus.getMonkeyBusinessLevel().toInt()
}

fun levelOfMonkeyBusinessAfterNRoundsButMoreWorried(rounds : Int) : Int {

    val rows = retrieveRowsFromFile("inputday11.txt")

    val circus = Circus.from(rows, WorryAction(WorryOperation.PLUS, 0))

    circus.runRounds(rounds)

    return circus.getMonkeyBusinessLevel().toInt()
}