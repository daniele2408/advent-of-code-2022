package solutions.day12hillclimbingalgorithm.logic

import solutions.day12hillclimbingalgorithm.model.*
import kotlin.math.min

val charValueMap : Map<Char, Int> = ('a'..'z').mapIndexed { index, c -> c to index }.toMap() + ('E' to 26) + ('S' to 0) // OCCHIO CHE CAMBIO PER PRINT
val valueCharMap : Map<Int, Char> = charValueMap.filter { it.key !in setOf('E', 'S') }.map { (k,v) -> v to k }.toMap()

class HeatMap(private val grid: List<List<Coord>>, val target: Coord, startPos: Coord) {

    private var currentPosition : Coord = startPos
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

    private fun getCoord(coord: Coord) : Coord {
        return grid[coord.y][coord.x]
    }

    fun runDijkstra() : Int {
        while (!walkingLog.isAlreadySeen(target)) {
            dijkstra()
        }
        return grid[target.y][target.x].d
    }

    private fun dijkstra() {
        val neighbours = walkingLog.alreadySeen.map { it.getNeighbours(this) }.flatten()
        if (neighbours.isEmpty()) {
            return
        }
        currentPosition = chooseNext(neighbours)
    }

    private fun chooseNext(neighbours : List<Coord>) : Coord {
        (neighbours.indices).forEach { i ->
            neighbours[i].d = min(neighbours[i].d, this.getCoord(currentPosition).d+1)
            walkingLog.setAsSeen(neighbours[i])
        }

        return neighbours.minBy { it.d }
    }

    fun inverseDijkstra() : Int {

        grid.flatten().forEach { it.d = Int.MAX_VALUE }
        grid[target.y][target.x].d = 0
        currentPosition = grid[target.y][target.x]

        walkingLog.alreadySeen.clear()
        walkingLog.setAsSeen(grid[currentPosition.y][currentPosition.x])

        var goOn = true
        while (goOn) {
            goOn = dijkstraInverse()
        }

        val lowestNearestSquareFromTarget = grid.flatten().filter { it.h == 0 }.minByOrNull { it.d }!!

        return lowestNearestSquareFromTarget.d
    }

    private fun dijkstraInverse() : Boolean {
        val neighbours = walkingLog.alreadySeen.map { it.getNeighboursInverse(this) }.flatten()
        if (neighbours.isEmpty()) {
            return false
        }

        currentPosition = chooseNextInverse(neighbours)

        return true
    }

    private fun chooseNextInverse(neighbours : List<Coord>) : Coord {
        (neighbours.indices).forEach { i ->
            neighbours[i].d = min(neighbours[i].d, this.getCoord(currentPosition).d+1)
            walkingLog.setAsSeen(neighbours[i])
        }

        return neighbours.minBy { it.d }
    }

    fun alreadyBeenThere(coord: Coord, direction: WalkDirection) : Boolean {
        return walkingLog.isAlreadySeen(coord.walk(direction, this))
    }

    private fun printPixelMap(coord: Coord) : String {
        return when {
            coord.isAt(currentPosition) -> "§"
            coord.isAt(target) -> "^"
            walkingLog.isAlreadySeen(coord) -> "."
            else -> valueCharMap[coord.h]?.toString() ?: "#"
        }
    }
    override fun toString(): String {

        return grid.map {
            it.joinToString("") { coord -> printPixelMap(coord) }
        }.joinToString("\n")

    }

    companion object {

        fun create(rows : List<String>) : HeatMap {

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

            val start = Coord(posStart % width, posStart / width, charValueMap['a']!!)
            val target = Coord(posE % width, posE / width, charValueMap['z']!!)
            return HeatMap(coords, target, start)

        }

    }
}