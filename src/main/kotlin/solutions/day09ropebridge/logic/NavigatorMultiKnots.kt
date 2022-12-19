package solutions.day09ropebridge.logic

import solutions.day09ropebridge.model.MoveAction
import solutions.day09ropebridge.model.KnotRope
import kotlin.math.max

class NavigatorMultiKnots(totKnots: Int, private val moves: List<MoveAction>, override val observer: RopeObserver, override var verbose: Boolean) :
    INavigator {

    private val knotRope : KnotRope = KnotRope.from(totKnots)

    init {
        observer.addPointToLogs(knotRope.getLastKnot())
    }
    override fun moveKnots(moveAction: MoveAction) {
        (1..moveAction.steps).forEach { _ ->
            knotRope.pullRope(moveAction.moveType)
            observer.addPointToLogs(knotRope.getLastKnot())
            printInternal("Moved 1 step ${moveAction.moveType}")
            printInternal("$this\n\n")
        }
    }

    override fun move() {

        moves.forEach {
            printInternal("\n== Moving ${it.steps} ${it.moveType.symbol} ==")
            moveKnots(it)
        }
    }

    override fun toString(): String {
        try {
            knotRope
            val height : Int = max((knotRope.getMaxY() * 1.1).toInt() / 2, 20)
            val width : Int = max((knotRope.getMaxX() * 1.1).toInt() / 2, 26)
            val grid = (0..height*2).map { i ->
                (0..width*2).map { j ->
                    "."
                }.toMutableList()
            }.toMutableList()
            grid[width/2][height/2] = "s"
            knotRope.knots.reversed().forEachIndexed { idx, knot ->
                grid[knot.y + width/2][knot.x + height/2] = "${(knotRope.knots.size-1) - idx}"
            }

            return grid.mapIndexed { idx, row -> "${-width/2 + idx} ".padStart(3) + row.joinToString(" ") }.reversed().joinToString("\n")

        } catch (oub: IndexOutOfBoundsException) {
            return "Navigator" // todo make map dynamic fog-of-war like
        }
    }

    fun totalPositionVisitedByLast() : Int {
        return observer.logs.size
    }

    companion object {
        fun from(size: Int, rows: List<String>) : NavigatorMultiKnots {

            val moveList: List<MoveAction> = rows.map { row ->
                val (symbol, steps) = row.split(" ")
                MoveAction.from(symbol, steps)
            }

            return NavigatorMultiKnots(size - 1, moveList, RopeObserver(), false)

        }

        fun from(size: Int, rows: List<String>, verbose : Boolean) : NavigatorMultiKnots {

            val moveList: List<MoveAction> = rows.map { row ->
                val (symbol, steps) = row.split(" ")
                MoveAction.from(symbol, steps)
            }

            return NavigatorMultiKnots(size - 1, moveList, RopeObserver(), verbose)

        }

    }

}