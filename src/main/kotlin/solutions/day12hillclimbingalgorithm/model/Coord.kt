package solutions.day12hillclimbingalgorithm.model

import solutions.day12hillclimbingalgorithm.logic.HeatMap

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

    private fun getZDistance(coord: Coord) : Int {
        return coord.h - this.h
    }

    private fun canMove(direction: WalkDirection, heatMap: HeatMap) : Boolean {
        return !amIOnMarginFor(direction, heatMap)
    }

    private fun shallIGoThere(direction: WalkDirection, heatMap: HeatMap) : Boolean {
        return canMove(direction, heatMap) && ! heatMap.alreadyBeenThere(this, direction)
    }

    fun getNeighbours(heatMap: HeatMap) : List<Coord> {
        return WalkDirection.values()
            .filter { shallIGoThere(it, heatMap) && getZDistance(walk(it, heatMap)) <= 1 }
            .map { walk(it, heatMap) }
    }

    fun getNeighboursInverse(heatMap: HeatMap) : List<Coord> {
        return WalkDirection.values()
            .filter { shallIGoThere(it, heatMap) && walk(it, heatMap).getZDistance(this) <= 1 }
            .map { walk(it, heatMap) }
    }

}