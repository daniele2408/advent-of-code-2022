package solutions.day15beaconexclusionzone.model

import solutions.day15beaconexclusionzone.logic.distance
import kotlin.math.max
import kotlin.math.min

data class SensorBeaconPair(val sensor: Coordinate, val beacon: Coordinate) {

    val d: Int = distance(sensor, beacon)

    val orthogonalPoints : OrthogonalPoints = OrthogonalPoints(
            Coordinate(sensor.x, sensor.y-d-1),
            Coordinate(sensor.x+d+1, sensor.y),
            Coordinate(sensor.x, sensor.y+d+1),
            Coordinate(sensor.x-d-1, sensor.y)
    )



    val perimeter: Perimeter = Perimeter.init(orthogonalPoints)

    fun isInSensorRange(coordinate: Coordinate): Boolean {
        return distance(sensor, coordinate) <= d
    }


    fun getMinX(): Int {
        return min(sensor.x, beacon.x)
    }

    fun getMinY(): Int {
        return min(sensor.y, beacon.y)
    }

    fun getMaxX(): Int {
        return max(sensor.x, beacon.x)
    }

    fun getMaxY(): Int {
        return max(sensor.y, beacon.y)
    }

    override fun toString(): String {
        return "S(${sensor.x.toString().padStart(2)},${sensor.y.toString().padStart(2)})--{$d}-->B(${beacon.x.toString().padStart(2)},${beacon.y.toString().padStart(2)})"
    }

    data class OrthogonalPoints(val n: Coordinate, val e: Coordinate, val s: Coordinate, val w: Coordinate) {

        fun asSet(): Set<Coordinate> {
            return setOf(n, e, s, w)
        }

    }

    class Perimeter(
        val seqN: Sequence<Coordinate>,
        val seqE: Sequence<Coordinate>,
        val seqS: Sequence<Coordinate>,
        val seqW: Sequence<Coordinate>
        ) {

        fun coordinates() : Sequence<Coordinate> {
            return sequenceOf(seqN + seqE + seqS +seqW).flatten()
        }

        companion object {

            fun init(op: OrthogonalPoints) : Perimeter {

                val sideNorthEast = generateSequence(op.n) {
                    val next = Coordinate(it.x + 1, it.y + 1)
                    if (next.isAt(op.e)) null
                    else next
                }

                val sideEastSouth = generateSequence(op.e) {
                    val next = Coordinate(it.x - 1, it.y + 1)
                    if (next.isAt(op.s)) null
                    else next
                }

                val sideSouthWest = generateSequence(op.s) {
                    val next = Coordinate(it.x - 1, it.y - 1)
                    if (next.isAt(op.w)) null
                    else next
                }

                val sideWestNorth = generateSequence(op.w) {
                    val next = Coordinate(it.x + 1, it.y - 1)
                    if (next.isAt(op.n)) null
                    else next
                }

                return Perimeter(sideNorthEast, sideEastSouth , sideSouthWest, sideWestNorth)

            }

        }

    }

    companion object {

        fun fromPair(pair: Pair<Coordinate, Coordinate>): SensorBeaconPair {
            assert(pair.first.type == CoordinateType.SENSOR && pair.second.type == CoordinateType.BEACON)
            return SensorBeaconPair(pair.first, pair.second)
        }

    }

}