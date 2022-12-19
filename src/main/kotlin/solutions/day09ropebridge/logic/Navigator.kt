package solutions.day09ropebridge.logic

import solutions.day09ropebridge.model.Knot
import solutions.day09ropebridge.model.MoveAction
import kotlin.math.max

// used it for first part, I could replace it with generic implementation with size = 2
class Navigator(private val moves: List<MoveAction>, override val observer: RopeObserver, override var verbose: Boolean) :
    INavigator {

    var positionH : Knot = Knot(0, 0)
    var positionT : Knot = Knot(0, 0)

    constructor(moves: List<MoveAction>, observer: RopeObserver) : this(moves, observer, false)

    init {
        observer.addPointToLogs(positionT)
    }

    override fun moveKnots(moveAction: MoveAction) {
        (1..moveAction.steps).forEach {

            val nextPos : Knot = positionH.moveOneStep(moveAction.moveType)
            if (!positionT.isTouching(nextPos)) {
                positionT = positionH.here()
                observer.addPointToLogs(positionT.here())
            }
            positionH = nextPos
            printInternal("\nMoved 1 step ${moveAction.moveType}\n")
            printInternal("$this\n\n")
        }
    }

    override fun move() {
        moves.forEach {
            printInternal("\n##############\nMoving ${it.steps} steps ${it.moveType}")
            moveKnots(it)
        }
    }

    override fun toString(): String {
        try {
            val height : Int = max((max(positionH.y, positionT.y) * 1.1).toInt(), 4)
            val width : Int = max((max(positionH.x, positionT.x) * 1.1).toInt(), 5)
            val grid = (0..height).map { i ->
                (0..width).map { j ->
                    "."
                }.toMutableList()
            }.toMutableList()
            grid[0][0] = "s"
            grid[positionT.y][positionT.x] = "T"
            grid[positionH.y][positionH.x] = "H"

            return grid.mapIndexed { idx, row -> "$idx " + row.joinToString(" ") }.reversed().joinToString("\n") + "\n* " + (0..width).map { "$it" }.joinToString(" ")

        } catch (oub: IndexOutOfBoundsException) {
            return "Navigator" // todo make map dynamic fog-of-war like
        }
    }

    fun totalPositionVisitedByT() : Int {
        return observer.logs.size
    }

    companion object {

        fun from(rows : List<String>) : Navigator {

            val moveList: List<MoveAction> = rows.map { row ->
                val (symbol, steps) = row.split(" ")
                MoveAction.from(symbol, steps)
            }

            return Navigator(moveList, RopeObserver())

        }

        fun from(rows : List<String>, verbose : Boolean) : Navigator {

            val moveList: List<MoveAction> = rows.map { row ->
                val (symbol, steps) = row.split(" ")
                MoveAction.from(symbol, steps)
            }

            return Navigator(moveList, RopeObserver(), verbose)

        }

    }

}


