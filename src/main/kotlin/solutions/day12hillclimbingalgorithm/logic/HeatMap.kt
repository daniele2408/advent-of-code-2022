package solutions.day12hillclimbingalgorithm.logic

import solutions.day12hillclimbingalgorithm.model.Coord
import solutions.day12hillclimbingalgorithm.model.WalkDirection.*
import solutions.day12hillclimbingalgorithm.model.WalkDirection
import solutions.day12hillclimbingalgorithm.model.WalkingLog
import solutions.day12hillclimbingalgorithm.model.WalkingLogEntry
import kotlin.math.abs

val charValueMap : Map<Char, Int> = ('a'..'z').mapIndexed { index, c -> c to index }.toMap() + ('E' to 25) + ('S' to 0) // OCCHIO CHE CAMBIO PER PRINT
val valueCharMap : Map<Int, Char> = charValueMap.filter { it.key !in setOf('E', 'S') }.map { (k,v) -> v to k }.toMap()

class HeatMap(private val grid: List<List<Coord>>, val target: Coord, startPos: Coord) {

    var currentPosition : Coord = startPos
    val walkingLog : WalkingLog = WalkingLog()
    val width : Int = grid[0].size
    val height : Int = grid.size

    init {
        walkingLog.addLog(WalkingLogEntry(WalkDirection.DOWN, currentPosition, true))
    }

    fun getCoord(x : Int, y : Int) : Coord {
        return grid[y][x]
    }

    fun getLegalDirection() : List<WalkDirection> {
        val lastDirection = getLastDirection() ?: throw RuntimeException("Last direction can't be null")
        val legalDirections = WalkDirection.values().filter {
            !amIOnMarginFor(it) && it != WalkDirection.getOpposite(lastDirection) && !walkingLog.isAlreadySeen(currentPosition.walk(it, this)) && currentPosition.canAccess(currentPosition.walk(it, this))
        }.toList()
        return legalDirections
    }

    fun getLastDirection() : WalkDirection? {
        if (walkingLog.isEmpty()) return null
        return walkingLog.getLastEntry().direction
    }

    /*
    idee: fare simulazioni ricorsivamente concatenando walk e scegliere quello più corto
    oppure farlo tornare a un certo incrocio e non l'ultimo
     */

    fun getRankedOrder() : List<WalkDirection> {
//        return WalkDirection.values().filter { !amIOnMarginFor(it) }.sortedBy { currentPosition.walk(it, this).distance(target) }
        return WalkDirection.values().filter { !amIOnMarginFor(it) }
//            .sortedWith(compareBy<WalkDirection> { currentPosition.walk(it, this).distance(target) }.thenByDescending { currentPosition.getZDistance(currentPosition.walk(it, this)) })
            .sortedWith(compareByDescending<WalkDirection> { currentPosition.getZDistance(currentPosition.walk(it, this)) }.thenBy { currentPosition.walk(it, this).distance(target) })
//        return when {
//            abs(getDeltaX().toLong()) >= abs(getDeltaY().toLong()) -> if (getDeltaX() > 0) listOf(LEFT, RIGHT, UP, DOWN) else listOf(RIGHT, LEFT, UP, DOWN)
//            abs(getDeltaX().toLong()) < abs(getDeltaY().toLong()) -> if (getDeltaY() > 0) listOf(UP, DOWN, LEFT, RIGHT) else listOf(DOWN, UP, LEFT, RIGHT)
//            else -> throw RuntimeException("Caso non coperto di DeltaX e DeltaY")
//        }
    }

    private fun getDeltaY() : Int = currentPosition.y - target.y

    private fun getDeltaX() : Int = currentPosition.x - target.x

    private fun amIOnMarginFor(direction: WalkDirection) : Boolean {
        return when (direction) {
            WalkDirection.UP -> currentPosition.y == 0
            WalkDirection.DOWN -> currentPosition.y == height - 1
            WalkDirection.LEFT -> currentPosition.x == 0
            WalkDirection.RIGHT -> currentPosition.x == width - 1
        }
    }

    var c : Int = 0

    fun addC() {
        c++
    }

    fun pickDirection() {
        addC()
        println(this)

        println("=================================================== MOSSA $c =====================================================")
        if (currentPosition.isAt(target)) return
        val possibleDirections: List<WalkDirection> = getLegalDirection()

        if (possibleDirections.isEmpty()) {
            println("Vicolo cieco, torno indietro")

            walkingLog.rollBackToLastCross()
            currentPosition = walkingLog.getLastEntry().coord
            pickDirection()
            return
        }
        if (possibleDirections.size > 1) walkingLog.setLastEntryAsCross()

        val rankedOrder = getRankedOrder()
        println("Al momento mi trovo su ${currentPosition}")
        println("Le mie preferenze sono $rankedOrder")
        println("Ho ${possibleDirections.size} possibili direzioni ($possibleDirections)")

        val nextPos = rankedOrder.first { it in possibleDirections }

//        val nextPos = possibleDirections.map { currentPosition.walk(it, this) to it }.sortedBy { it.first.h }.reversed()
//            .first { it.first.h - currentPosition.h in (0..1) }.second
        println("Cammino verso $nextPos, sento sia la strada migliore")
        doOneStep(nextPos)
        pickDirection()

    }

    fun doOneStep(pickedDirection: WalkDirection) {
        currentPosition = currentPosition.walk(pickedDirection, this)
        walkingLog.addLog(WalkingLogEntry(pickedDirection, currentPosition))
    }

    fun startWalking() {
        pickDirection()
    }

    fun getTotalSteps() : Int {
        return walkingLog.getTotalSteps()
    }

    private fun printPixelMap(coord: Coord) : String {
        return when {
            coord.isAt(currentPosition) -> "H"
            coord.isAt(target) -> "T"
            walkingLog.isCoordInLog(coord) -> walkingLog.printCoord(coord)
            currentPosition.isInRange(coord, 5) -> valueCharMap[coord.h]?.toString() ?: "#"
            else -> "."
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