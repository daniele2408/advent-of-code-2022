package solutions.day12hillclimbingalgorithm.model

import solutions.day12hillclimbingalgorithm.logic.HeatMap
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class Coord(val x : Int, val y : Int, val h : Int) {

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

    fun getNextStepElseNull(grid: HeatMap) : Pair<Coord, Coord>? {
        val directionStep = WalkDirection.values()
            .filter { isDirectionAllowed(it, grid) }
            .find { walk(it, grid).h - this.h == 1 } ?: return null
        return this to walk(directionStep, grid)
    }

    fun isDirectionAllowed(walkDirection: WalkDirection, heatMap: HeatMap) : Boolean {
        return !amIOnMarginFor(walkDirection, heatMap) && this.walk(walkDirection, heatMap).h - this.h <= 1
    }

    fun scanGridSquare(side : Int, heatMap: HeatMap) : List<List<Coord>> {
        val upLeftCorner : Coord = heatMap.getCoord(max(this.x - side, 0), max(this.y - side, 0))
        val bottomRightCorner : Coord = heatMap.getCoord(min(this.x + side, heatMap.width), min(this.y + side, heatMap.height))
        return heatMap.sliceGrid(upLeftCorner, bottomRightCorner)
    }

    fun getRankedOrderRespectTo(coord: Coord, heatMap: HeatMap) : List<WalkDirection> {
        return WalkDirection.values().filter { !amIOnMarginFor(it, heatMap) }
            .sortedWith(compareByDescending<WalkDirection> { getZDistance(walk(it, heatMap)) }.thenBy { walk(it, heatMap).distance(coord) })
    }

    fun getLegalDirection(heatMap: HeatMap) : List<WalkDirection> {
        val lastDirection = heatMap.getLastDirection() ?: throw RuntimeException("Last direction can't be null")
        val legalDirections = WalkDirection.values().filter {
            !amIOnMarginFor(it, heatMap) && it != WalkDirection.getOpposite(lastDirection) && !heatMap.walkingLog.isAlreadySeen(walk(it, heatMap)) && canAccess(walk(it, heatMap))
        }.toList()
        return legalDirections
    }

    fun distance(coord: Coord) : Int {
        return getXDistance(coord) + getYDistance(coord)
    }

    fun canAccess(coord: Coord) : Boolean {
        return (coord.h - this.h) <= 1
    }

    fun isAt(coord: Coord) : Boolean {
        return this.x == coord.x && this.y == coord.y
    }

    fun getXDistance(coord: Coord) : Int {
        return abs(coord.x - this.x)
    }

    fun getYDistance(coord: Coord) : Int {
        return abs(coord.y - this.y)
    }

    fun getZDistance(coord: Coord) : Int {
        return coord.h - this.h
    }

    fun isInRange(coord: Coord, maxDistance : Int) : Boolean {
        return getXDistance(coord) < maxDistance && getYDistance(coord) < maxDistance
    }

}