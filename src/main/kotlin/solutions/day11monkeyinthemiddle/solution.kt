package solutions.day11monkeyinthemiddle

import retrieveRowsFromFile
import solutions.day11monkeyinthemiddle.logic.Circus
import java.math.BigInteger

fun levelOfMonkeyBusinessAfterNRounds(rounds : Int) : BigInteger {

    val rows = retrieveRowsFromFile("inputday11.txt")

    val circus = Circus.from(rows, false)

    circus.runRounds(rounds)

    return circus.getMonkeyBusinessLevel()
}

fun levelOfMonkeyBusinessAfterNRoundsHighStress(rounds : Int) : BigInteger {

    val rows = retrieveRowsFromFile("inputday11.txt")

    val circus = Circus.from(rows, true)

    circus.runRounds(rounds)

    return circus.getMonkeyBusinessLevel()
}