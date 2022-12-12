package solutions.day12hillclimbingalgorithm.model

import solutions.day12hillclimbingalgorithm.logic.HeatMap

data class Coord(val x : Int, val y : Int, val h : Int) {

    fun walk(direction: WalkDirection, grid: HeatMap) : Coord {
        return when (direction) {
            WalkDirection.UP -> grid.getCoord(x, y-1)
            WalkDirection.DOWN -> grid.getCoord(x, y+1)
            WalkDirection.LEFT -> grid.getCoord(x-1, y)
            WalkDirection.RIGHT -> grid.getCoord(x+1, y)
        }
    }

    fun isAt(x: Int, y: Int) : Boolean {
        return this.x == x && this.y == y
    }

    fun canAccess(coord: Coord) : Boolean {
        return coord.h - this.h in (0..1)
    }

    fun isAt(coord: Coord) : Boolean {
        return this.x == coord.x && this.y == coord.y
    }

}