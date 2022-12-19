package solutions.day15beaconexclusionzone.model

data class Coordinate(val x: Int, val y: Int, val type: CoordinateType) {

    fun isAt(x: Int, y: Int): Boolean {
        return this.x == x && this.y == y
    }

    fun isAt(coordinate: Coordinate): Boolean {
        return this.x == coordinate.x && this.y == coordinate.y
    }

}