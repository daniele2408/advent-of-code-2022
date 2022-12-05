package solutions

import model.CargoArea
import model.CraneModel
import retrieveRowsFromFile

fun getCratesOnTop(craneModel: CraneModel): String {
    val rows: List<String> = retrieveRowsFromFile("inputday5.txt")

    val inputCrates = rows.takeWhile { row -> row.isNotEmpty() }
    val inputMoves = rows.dropWhile { row -> !row.startsWith("move") }

    val cargoArea = CargoArea.fromInput(inputCrates)
    cargoArea.executeCommands(inputMoves, craneModel)
    return cargoArea.getTopCrates()

}