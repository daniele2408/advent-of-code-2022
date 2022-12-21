package solutions.day15beaconexclusionzone.model

data class Coordinate(val x: Int, val y: Int, val type: CoordinateType) {

    constructor(x: Int, y: Int) : this(x, y, CoordinateType.EMPTY)

    fun isAt(coordinate: Coordinate): Boolean {
        return this.x == coordinate.x && this.y == coordinate.y
    }

}