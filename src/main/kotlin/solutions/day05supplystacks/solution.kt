package solutions.day05supplystacks

import solutions.day05supplystacks.model.CargoArea
import solutions.day05supplystacks.model.CraneModel
import retrieveRowsFromFile

fun getCratesOnTop(craneModel: CraneModel): String {
    val rows: List<String> = retrieveRowsFromFile("inputday05.txt")

    val inputCrates = rows.takeWhile { row -> row.isNotEmpty() }
    val inputMoves = rows.dropWhile { row -> !row.startsWith("move") }

    val cargoArea = CargoArea.fromInput(inputCrates)
    cargoArea.executeCommands(inputMoves, craneModel)
    return cargoArea.getTopCrates()

}