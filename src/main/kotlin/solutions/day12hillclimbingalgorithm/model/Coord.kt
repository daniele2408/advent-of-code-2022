package solutions.day12hillclimbingalgorithm.model

import solutions.day12hillclimbingalgorithm.logic.HeatMap
import kotlin.math.abs

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