package solutions.day12hillclimbingalgorithm.logic

import solutions.day12hillclimbingalgorithm.model.*
import solutions.day12hillclimbingalgorithm.model.WalkDirection.*
import kotlin.math.abs
import kotlin.math.max

val charValueMap : Map<Char, Int> = ('a'..'z').mapIndexed { index, c -> c to index }.toMap() + ('E' to 26) + ('S' to 0) // OCCHIO CHE CAMBIO PER PRINT
val valueCharMap : Map<Int, Char> = charValueMap.filter { it.key !in setOf('E', 'S') }.map { (k,v) -> v to k }.toMap()

class HeatMap(private val grid: List<List<Coord>>, val target: Coord, startPos: Coord) {

    var currentPosition : Coord = startPos
    var currentTarget : Coord = target
    val listTargets: MutableSet<Coord> = mutableSetOf()
    val walkingLog : WalkingLog = WalkingLog()
    val width : Int = grid[0].size - 1
    val height : Int = grid.size - 1

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
    idee: scansionare l'area progressivamente e trovareil punto più vicino dove l'h +1 e +2 si toccano
    vedere se raggiungibile
    raggiungerlo
     */

    fun scanGridSquare(): Pair<Coord, Coord> {
        var sideSlice = 0
        var coupleStep : Pair<Coord, Coord>? = null
        while (coupleStep == null) {
            val scanGridSquare = currentPosition.scanGridSquare(sideSlice, this)
            val subGrid = Grid(scanGridSquare, currentPosition)
            println("Slicing grid ${subGrid.width}x${subGrid.heigth}:\n\n$subGrid\n\n")
            coupleStep = subGrid.getAdjStepLessDistantFromTarget(this)
            sideSlice++
            if (sideSlice > max(width, height)) {
                throw RuntimeException("sideSlice greater than map limits, it's looping forever")
            }
        }
        return coupleStep
    }

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
            WalkDirection.DOWN -> currentPosition.y == height
            WalkDirection.LEFT -> currentPosition.x == 0
            WalkDirection.RIGHT -> currentPosition.x == width
        }
    }

    var c : Int = 0

    fun addC() {
        c++
    }

    fun pickDirectionToward(coord: Coord) {
        addC()
        println(this)

        println("=================================================== MOSSA $c =====================================================")
        if (currentPosition.isAt(coord)) {
            println("Arrivato al target $coord")
            return
        }
        val possibleDirections: List<WalkDirection> = getLegalDirection()

        if (possibleDirections.isEmpty()) {
            println("Vicolo cieco, torno indietro")

            walkingLog.rollBackToLastCross()
            currentPosition = walkingLog.getLastEntry().coord
            return
        }
        if (possibleDirections.size > 1) walkingLog.setLastEntryAsCross()

        val rankedOrder = currentPosition.getRankedOrderRespectTo(coord, this)
        println("Al momento mi trovo su ${currentPosition} e vorrei arrivare a $coord")
        println("Le mie preferenze sono $rankedOrder")
        println("Ho ${possibleDirections.size} possibili direzioni ($possibleDirections)")

        val nextPos = rankedOrder.first { it in possibleDirections }

        println("Cammino verso $nextPos, sento sia la strada migliore per $coord")
        doOneStep(nextPos)
    }

    fun pickDirectionIter() {
        addC()
        println(this)

        println("=================================================== MOSSA $c =====================================================")
        if (currentPosition.isAt(target)) return
        val possibleDirections: List<WalkDirection> = getLegalDirection()

        if (possibleDirections.isEmpty()) {
            println("Vicolo cieco, torno indietro")

            walkingLog.rollBackToLastCross()
            currentPosition = walkingLog.getLastEntry().coord
            return
        }
        if (possibleDirections.size > 1) walkingLog.setLastEntryAsCross()

        val rankedOrder = getRankedOrder()
        println("Al momento mi trovo su ${currentPosition}")
        println("Le mie preferenze sono $rankedOrder")
        println("Ho ${possibleDirections.size} possibili direzioni ($possibleDirections)")

        val nextPos = rankedOrder.first { it in possibleDirections }

        println("Cammino verso $nextPos, sento sia la strada migliore")
        doOneStep(nextPos)
    }

    fun doOneStep(pickedDirection: WalkDirection) {
        currentPosition = currentPosition.walk(pickedDirection, this)
        walkingLog.addLog(WalkingLogEntry(pickedDirection, currentPosition))
    }

    fun startWalkingIter() {
        while (!currentPosition.isAt(target)) {
            pickDirectionIter()
        }
    }

    fun startWalking() {
        while (!currentPosition.isAt(target)) {
            val (_, coordHigher) = scanGridSquare()
            currentTarget = coordHigher
            while (!currentPosition.isAt(coordHigher)) {
                pickDirectionToward(coordHigher)
            }
            listTargets.add(coordHigher)
        }
    }

    fun getTotalSteps() : Int {
        return walkingLog.getTotalSteps()
    }

    private fun printPixelMap(coord: Coord) : String {
        return when {
            coord.isAt(currentPosition) -> "O"
            coord.isAt(target) -> "X"
            coord in listTargets -> valueCharMap[coord.h]?.uppercase() ?: "."
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

    fun sliceGrid(upLeftCorner: Coord, bottomRightCorner: Coord): List<List<Coord>> {
        return (upLeftCorner.y..bottomRightCorner.y).map { j ->
            (upLeftCorner.x..bottomRightCorner.x).map { i ->
                grid[j][i]
            }.toList()
        }.toList()
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