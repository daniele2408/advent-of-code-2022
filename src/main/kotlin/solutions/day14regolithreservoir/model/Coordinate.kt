package solutions.day14regolithreservoir.model

data class Coordinate(val x : Int, val y : Int, var fill: FillType) {

    constructor(x: Int, y: Int) : this(x, y, FillType.AIR)

    companion object {

        fun from(s: String) : Coordinate {
            val (x, y) = s.split(',').map { it.toInt() }
            return Coordinate(x, y)
        }

    }
}