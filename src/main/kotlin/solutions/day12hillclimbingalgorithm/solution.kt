package solutions.day12hillclimbingalgorithm

import retrieveRowsFromFile
import solutions.day12hillclimbingalgorithm.logic.HeatMap

fun fewestStepRequiredFromNearestLowestPoint() : Pair<Int, Int> {
    val rows: List<String> = retrieveRowsFromFile("inputday12.txt")

    val heatMap = HeatMap.create(rows)
    val fewestStepsFromStart = heatMap.runDijkstra()

    val fewestStepsFromNearestLowest = heatMap.inverseDijkstra()

    return fewestStepsFromStart to fewestStepsFromNearestLowest
}