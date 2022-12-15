package solutions.day12hillclimbingalgorithm.model

import solutions.day12hillclimbingalgorithm.logic.HeatMap
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class Coord(val x : Int, val y : Int, val h : Int, var d : Int = Int.MAX_VALUE) {

    fun walk(direction: WalkDirection, grid: HeatMap) : Coord {
        return when (direction) {
            WalkDirection.UP -> grid.getCoord(x, y-1)
            WalkDirection.DOWN -> grid.getCoord(x, y+1)
            WalkDirection.LEFT -> grid.getCoord(x-1, y)
            WalkDirection.RIGHT -> grid.getCoord(x+1, y)
        }
    }

    private fun amIOnMarginFor(direction: WalkDirection, grid: HeatMap) : Boolean {
        return when (direction) {
            WalkDirection.UP -> this.y == 0
            WalkDirection.DOWN -> this.y == grid.height
            WalkDirection.LEFT -> this.x == 0
            WalkDirection.RIGHT -> this.x == grid.width
        }
    }

    fun isAt(coord: Coord) : Boolean {
        return this.x == coord.x && this.y == coord.y
    }

    fun getZDistance(coord: Coord) : Int {
        return coord.h - this.h
    }

    fun getNeighbours(heatMap: HeatMap) : List<Coord> {
        return WalkDirection.values()
            .filter { !amIOnMarginFor(it, heatMap) && !heatMap.walkingLog.isAlreadySeen(this.walk(it, heatMap)) && this.getZDistance(this.walk(it, heatMap)) <= 1 }
            .map { this.walk(it, heatMap) }
    }

}