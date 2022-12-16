package solutions.day12hillclimbingalgorithm.logic

import solutions.day12hillclimbingalgorithm.model.Coord

class WalkingLog {
    val alreadySeen : MutableSet<Coord> = mutableSetOf()

    fun setAsSeen(coord: Coord) {
        alreadySeen.add(coord)
    }

    fun isAlreadySeen(coord: Coord) : Boolean {
        return alreadySeen.any { it.isAt(coord) }
    }

}