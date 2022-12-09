package solutions.day9ropebridge.model

import java.lang.Exception
import kotlin.math.abs

data class Knot(val x: Int, val y: Int) {
    fun moveOneStep(moveType: Move) : Knot {
        return when (moveType) {
            Move.UP-> up()
            Move.DOWN-> down()
            Move.LEFT-> left()
            Move.RIGHT-> right()
        }
    }

    private fun right() = Knot(x + 1, y)

    private fun left() = Knot(x - 1, y)

    private fun down() = Knot(x, y - 1)

    private fun up() = Knot(x, y + 1)

    private fun diff(other: Knot) : Pair<Int, Int> {
        return (other.x - x) to (other.y - y)
    }

    fun planckPull(other: Knot) : Knot {
        val pairDiff = this.diff(other)
        return when (pairDiff) {
            0 to 0, 0 to 1, 0 to -1, 1 to 0, -1 to 0,
            1 to 1, -1 to -1, 1 to -1, -1 to 1 -> this.here()

            0 to 2 -> this.up()
            0 to -2 -> this.down()
            2 to 0 -> this.right()
            -2 to 0 -> this.left()

            1 to 2, 2 to 1, 2 to 2 -> this.up().right()
            2 to -1, 1 to -2, 2 to -2 -> this.down().right()
            -2 to -1, -1 to -2, -2 to -2 -> this.down().left()
            -1 to 2, -2 to 1, -2 to 2 -> this.up().left()

            else -> throw Exception("Couldn't map pull for pair $pairDiff")
        }
    }

    fun here() : Knot {
        return Knot(x, y)
    }

    fun isTouching(other: Knot) : Boolean {
        return abs(other.x - x) <= 1 && abs(other.y - y) <= 1
    }

}
