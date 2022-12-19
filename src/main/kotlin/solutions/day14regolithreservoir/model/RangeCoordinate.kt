package solutions.day14regolithreservoir.model

class RangeCoordinate private constructor(private val start: Coordinate, private val end: Coordinate) {

    fun getXValues() : List<Int> {
        return listOf(start, end).map { it.x }
    }

    fun getYValues() : List<Int> {
        return listOf(start, end).map { it.y }
    }

    fun contains(coordinate: Coordinate): Boolean {
        return coordinate.x in (start.x .. end.x) && coordinate.y in (start.y .. end.y)
    }

    fun contains(x: Int, y: Int): Boolean {
        return x in (start.x .. end.x) && y in (start.y .. end.y)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RangeCoordinate

        if (start != other.start) return false
        if (end != other.end) return false

        return true
    }

    override fun hashCode(): Int {
        var result = start.hashCode()
        result = 31 * result + end.hashCode()
        return result
    }


    companion object {

        fun init(a: Coordinate, b: Coordinate) : RangeCoordinate {
            val sortedInputs = listOf(a, b).sortedWith(compareBy({ it.x }, { it.y }))
            val start = sortedInputs.first()
            val end = sortedInputs.last()
            return RangeCoordinate(start, end)
        }

    }

}