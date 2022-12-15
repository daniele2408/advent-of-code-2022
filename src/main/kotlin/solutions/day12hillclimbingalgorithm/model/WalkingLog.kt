package solutions.day12hillclimbingalgorithm.model

class WalkingLog {
    private val logs : MutableList<WalkingLogEntry> = mutableListOf()
    val alreadySeen : MutableSet<Coord> = mutableSetOf()

    fun setAsSeen(coord: Coord) {
        alreadySeen.add(coord)
    }

    fun isAlreadySeen(coord: Coord) : Boolean {
        return alreadySeen.any { it.isAt(coord) }
    }

    fun isCoordInLogAndIsNotLast(coord: Coord) : Boolean {
        return logs.isNotEmpty() && logs.last().coord != coord && logs.find { it.coord.isAt(coord) } != null
    }

    fun printCoord(coord: Coord) : String {
        val pastStep: WalkingLogEntry = logs.find { it.coord.isAt(coord) }!!
        return logs[logs.indexOf(pastStep)+1].direction.symbol

    }

    override fun toString(): String {
        return logs.map { "${it.direction.symbol} ${it.coord}" }.joinToString("\n")
    }

}