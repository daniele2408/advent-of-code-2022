package solutions.day9ropebridge.model

class KnotRope(val knots: MutableList<Knot>) {

    fun pullRope(move: Move) {

        knots[0] = knots[0].moveOneStep(move)
        if (!knots[1].isTouching(knots[0])) {
            moveKnot(1, knots[1].planckPull(knots[0]))
        }
    }

    private fun moveKnot(rank: Int, newPos: Knot) {
        knots[rank] = newPos
        if (isLastPos(rank)) return
        if (!knots[rank+1].isTouching(knots[rank])) {
            moveKnot(rank+1, knots[rank+1].planckPull(knots[rank]))
        }
    }

    private fun isNotLastPos(rank: Int) : Boolean = rank < knots.size - 1
    private fun isLastPos(rank: Int) : Boolean = !isNotLastPos(rank)

    fun getLastKnot() : Knot {
        return knots[knots.size-1].here()
    }

    fun getMaxX() : Int {
        return knots.maxBy { it.x }.x
    }

    fun getMaxY() : Int {
        return knots.maxBy { it.y }.y
    }

    companion object {
        fun from(size : Int) : KnotRope {
            val knots = (0..size).map {
                Knot(0, 0)
            }.toMutableList()

            return KnotRope(knots)
        }
    }

}