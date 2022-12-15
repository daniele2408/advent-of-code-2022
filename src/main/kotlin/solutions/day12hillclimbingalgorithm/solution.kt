package solutions.day12hillclimbingalgorithm

import retrieveRowsFromFile
import solutions.day12hillclimbingalgorithm.logic.HeatMap

fun fewestStepRequired() : Int {
    val rows: List<String> = retrieveRowsFromFile("inputday12.txt")

    val heatMap = HeatMap.from(rows)

    return heatMap.startDjikstra()
}