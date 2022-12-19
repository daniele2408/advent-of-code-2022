package solutions.day14regolithreservoir.logic

import solutions.day14regolithreservoir.model.Coordinate
import solutions.day14regolithreservoir.model.Direction
import solutions.day14regolithreservoir.model.FillType
import solutions.day14regolithreservoir.model.RangeCoordinate
import kotlin.math.max
import kotlin.math.min

class Cave(private val grid : MutableList<MutableList<Coordinate>>) {

    val width: Int = grid[0].size
    val height: Int = grid.size
    private val offsetX: Int = grid.flatten().map { it.x }.min()
    private val outOfBoundCoordinate: Coordinate = Coordinate(-1, -1, FillType.VOID)

    init {
        setCoordinate(sandPouringFrom)
    }

    fun startPouring() {
        // todo va in loop infinito anche perché non si accorge che è arrivato alla fonte, rimedia
        while (outOfBoundCoordinate.fill == FillType.VOID) {
            pour()
        }
        println(this)
    }

    fun pour() {

        setCoordinate(Coordinate(sandPouringFrom.x, sandPouringFrom.y, FillType.SAND))

        var sandGrain = getNextTo(sandPouringFrom, Direction.DOWN)

        sandGrain.fill = FillType.SAND
        var stop = false

        while (!stop) {
            val newPos = fall(sandGrain)
            if (newPos == sandGrain) {
                stop = true
            } else if (newPos == outOfBoundCoordinate) {
                outOfBoundCoordinate.fill = FillType.AIR
                stop = true
            } else {
                sandGrain = newPos
            }
            println(this)

        }
    }

    private fun setCoordinate(coordinate: Coordinate) {
        grid[coordinate.y][coordinate.x-offsetX] = coordinate
    }

    private fun setCoordinateFillType(coordinate: Coordinate, fillType: FillType) {
        grid[coordinate.y][coordinate.x-offsetX].fill = fillType
    }

    private fun getNextTo(coordinate: Coordinate, direction: Direction) : Coordinate {
        return try {
            when (direction) {
                Direction.UP -> grid[coordinate.y-1][coordinate.x-offsetX]
                Direction.DOWN -> grid[coordinate.y+1][coordinate.x-offsetX]
                Direction.LEFT -> grid[coordinate.y][coordinate.x-offsetX-1]
                Direction.RIGHT -> grid[coordinate.y][coordinate.x-offsetX+1]
            }
        } catch (oub: IndexOutOfBoundsException) {
            outOfBoundCoordinate
        }
    }

    fun countGrainsOfSand() : Int {
        return grid.flatten().count { it.fill == FillType.SAND }
    }

    private fun switchFill(a: Coordinate, b: Coordinate) {
        val fillA = a.fill
        val fillB = b.fill
        setCoordinateFillType(a, fillB)
        setCoordinateFillType(b, fillA)
    }
    
    private fun fall(coordinate: Coordinate): Coordinate {
        val downTile = getNextTo(coordinate, Direction.DOWN)
        if (downTile.fill == FillType.AIR) {
            switchFill(coordinate, downTile)
            return downTile
        }
        if (downTile.fill == FillType.VOID) {
            coordinate.fill = FillType.AIR
            return downTile
        }
        if (downTile.fill == FillType.ROCK || downTile.fill == FillType.SAND) {

            val diagonalLeftTile = getNextTo(downTile, Direction.LEFT)
            if (diagonalLeftTile.fill == FillType.AIR) {
                switchFill(coordinate, diagonalLeftTile)
                return diagonalLeftTile
            }
            if (diagonalLeftTile.fill == FillType.VOID) {
                coordinate.fill = FillType.AIR
                return diagonalLeftTile
            }

            val diagonalRightTile = getNextTo(downTile, Direction.RIGHT)
            if (diagonalRightTile.fill == FillType.AIR) {
                switchFill(coordinate, diagonalRightTile)
                return diagonalRightTile
            }
            if (diagonalRightTile.fill == FillType.VOID) {
                coordinate.fill = FillType.AIR
                return diagonalRightTile
            }
        }
        return coordinate

    }

    companion object {
        private val sandPouringFrom = Coordinate(500, 0, FillType.SOURCE)

        fun init(rows: List<String>) : Cave {

            val rangeCoordinates = createListRangeCoordinate(rows)

            val allXValues = rangeCoordinates.map { it.getXValues() }
            val maxX = max(allXValues.flatten().max(), sandPouringFrom.x)
            val minX = min(allXValues.flatten().min(), sandPouringFrom.x)

            val allYValues = rangeCoordinates.map { it.getYValues() }
            val maxY = max(allYValues.flatten().max(), sandPouringFrom.y)
            val minY = min(allYValues.flatten().min(), sandPouringFrom.y)

            val coordinates =
                (minY..maxY).map { y -> (minX..maxX).map { x ->
                    Coordinate(
                        x, y,
                        if (rangeCoordinates.any { it.contains(x, y) }) FillType.ROCK else FillType.AIR
                    )
                }.toMutableList() }.toMutableList()

            val cave = Cave(coordinates)
            return cave

        }

        private fun createListRangeCoordinate(rows: List<String>): List<RangeCoordinate> {
            val rangeCoordinates = rows.map { row ->
                row.split(" -> ")
                    .map { Coordinate.from(it) }
                    .zipWithNext { a, b -> RangeCoordinate.init(a, b) }
            }.flatten()
            return rangeCoordinates
        }

        fun initWithFloor(rows: List<String>) : Cave {

            val rangeCoordinates = createListRangeCoordinate(rows)

            val maxYWithoutFloor = max(rangeCoordinates.map { it.getYValues() }.flatten().max(), sandPouringFrom.y)

            val newRangeCoordinate = createListRangeCoordinate(rows + createStringFloor(maxYWithoutFloor))


            val allXValues = newRangeCoordinate.map { it.getXValues() }
            val maxX = max(allXValues.flatten().max(), sandPouringFrom.x)
            val minX = min(allXValues.flatten().min(), sandPouringFrom.x)

            val allYValues = newRangeCoordinate.map { it.getYValues() }
            val maxY = max(newRangeCoordinate.map { it.getYValues() }.flatten().max(), sandPouringFrom.y)
            val minY = min(allYValues.flatten().min(), sandPouringFrom.y)

            val coordinates =
                (minY..maxY).map { y -> (minX..maxX).map { x ->
                    Coordinate(
                        x, y,
                        if (newRangeCoordinate.any { it.contains(x, y) }) FillType.ROCK else FillType.AIR
                    )
                }.toMutableList() }.toMutableList()

            val cave = Cave(coordinates)
            return cave

        }

        private fun createStringFloor(maxY: Int): String {
            return "${sandPouringFrom.x - (maxY+1)},${maxY + 2} -> ${sandPouringFrom.x + (maxY)},${maxY + 2}"
        }

    }

    override fun toString(): String {
        return grid.map { row -> row.map { it.fill.symbol }.joinToString("") }.joinToString("\n")
    }
}