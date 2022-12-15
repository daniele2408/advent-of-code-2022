package solutions.day12hillclimbingalgorithm.logic

import solutions.day12hillclimbingalgorithm.model.*
import kotlin.math.max
import kotlin.math.min

val charValueMap : Map<Char, Int> = ('a'..'z').mapIndexed { index, c -> c to index }.toMap() + ('E' to 26) + ('S' to 0) // OCCHIO CHE CAMBIO PER PRINT
val valueCharMap : Map<Int, Char> = charValueMap.filter { it.key !in setOf('E', 'S') }.map { (k,v) -> v to k }.toMap()

class HeatMap(private val grid: List<List<Coord>>, val target: Coord, startPos: Coord) {

    var currentPosition : Coord = startPos
    val walkingLog : WalkingLog = WalkingLog()
    val width : Int = grid[0].size - 1
    val height : Int = grid.size - 1

    init {
        grid[startPos.y][startPos.x].d = 0
        walkingLog.setAsSeen(grid[startPos.y][startPos.x])
    }

    fun getCoord(x : Int, y : Int) : Coord {
        return grid[y][x]
    }

    fun getCoord(coord: Coord) : Coord {
        return grid[coord.y][coord.x]
    }


    fun djikstra() {
        val neighbours = walkingLog.alreadySeen.map { it.getNeighbours(this) }.flatten()
        if (neighbours.isEmpty()) return
        (neighbours.indices).forEach { i ->
            neighbours[i].d = min(neighbours[i].d, this.getCoord(currentPosition).d+1)
            walkingLog.setAsSeen(neighbours[i])
        }

        currentPosition = neighbours.minBy { it.d }
//        println(this)
//        println("==================================================")
    }


    fun startDjikstra() : Int {
        while (!walkingLog.isAlreadySeen(target)) {
            djikstra()
        }
//        println(printDistanceHeatMap())
//        println("Il target ha una distanza di ${grid[target.y][target.x].d} passi dall'inizio")
        return grid[target.y][target.x].d
    }

    fun printDistanceHeatMap() : String {
        return grid.map {
            it.map { coord -> val padding = 4
                "${if (coord.d != Int.MAX_VALUE) (coord.d).toString().padStart(padding) else "∞".padStart(padding)} " }.joinToString("")
        }.joinToString("\n")
    }

    private fun printPixelMap(coord: Coord) : String {
        return when {
            coord.isAt(currentPosition) -> "§"
            coord.isAt(target) -> "^"
            walkingLog.isCoordInLogAndIsNotLast(coord) -> walkingLog.printCoord(coord)
            walkingLog.isAlreadySeen(coord) -> "."
            else -> valueCharMap[coord.h]?.toString() ?: "#"
        }
    }
    override fun toString(): String {

        return grid.map {
            it.map { coord -> printPixelMap(coord) }.joinToString("")
        }.joinToString("\n")

    }

    companion object {


        fun from(rows : List<String>) : HeatMap {

            val width : Int = rows[0].length
            val height : Int = rows.size

            val flatMapChar: List<Char> = rows.map { row -> row.toCharArray().toList() }.flatten()
            val posE = flatMapChar.mapIndexed { index, c -> index to c }.find { it.second == 'E' }?.first ?: -1
            val posStart = flatMapChar.mapIndexed { index, c -> index to c }.find { it.second == 'S' }?.first ?: -1
            val elevations: List<Int> = flatMapChar.mapNotNull { charValueMap[it] }

            val coords: List<List<Coord>> = (0 until height).map { j ->
                (0 until width).map { i ->
                    Coord(i, j, elevations[width * j + i])
                }.toList()
            }.toList()

            val target = Coord(posE % width, posE / width, charValueMap['z']!!)
            val start = Coord(posStart % width, posStart / width, charValueMap['a']!!)
            return HeatMap(coords, target, start)

        }

    }
}