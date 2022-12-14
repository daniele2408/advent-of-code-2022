package solutions.day12hillclimbingalgorithm.model

import solutions.day12hillclimbingalgorithm.logic.HeatMap
import solutions.day12hillclimbingalgorithm.logic.valueCharMap

class Grid(val grid: List<List<Coord>>, val currentPos : Coord) {
    val width = grid[0].size-1
    val heigth = grid.size-1
    val upperLeftCorner : Coord = grid[0][0]
    val lowerRightCorner : Coord = grid[heigth][width]

    fun getAdjStepLessDistantFromTarget(heatMap: HeatMap) : Pair<Coord, Coord>? {
        val candidatesStep = getPerimeter().filter { heatMap.currentPosition.h == it.h }.mapNotNull { it.getNextStepElseNull(heatMap) }.toList()

        if (candidatesStep.isEmpty()) return null

        return candidatesStep.minBy { it.second.distance(heatMap.target) }
    }

    private fun getPerimeter() : List<Coord> {
        return grid. mapIndexed { rowIdx, row ->
            row.filterIndexed { index, _ -> rowIdx == 0 || rowIdx == heigth || index == 0 || index == width }
        }.flatten()
    }

    override fun toString(): String {

        return grid.map {
            it.map { coord -> printPixel(coord) }.joinToString("")
        }.joinToString("\n")

    }

    private fun printPixel(coord: Coord) : String {
        return if (coord.isAt(currentPos)) "O" else valueCharMap[coord.h]?.toString() ?: "#"
    }


}