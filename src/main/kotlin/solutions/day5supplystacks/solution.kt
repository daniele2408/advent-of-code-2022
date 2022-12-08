package solutions.day5supplystacks

import solutions.day5supplystacks.model.CargoArea
import solutions.day5supplystacks.model.CraneModel
import retrieveRowsFromFile

fun getCratesOnTop(craneModel: CraneModel): String {
    val rows: List<String> = retrieveRowsFromFile("inputday5.txt")

    val inputCrates = rows.takeWhile { row -> row.isNotEmpty() }
    val inputMoves = rows.dropWhile { row -> !row.startsWith("move") }

    val cargoArea = CargoArea.fromInput(inputCrates)
    cargoArea.executeCommands(inputMoves, craneModel)
    return cargoArea.getTopCrates()

}