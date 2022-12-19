package solutions.day15beaconexclusionzone.model

import solutions.day15beaconexclusionzone.logic.distance
import kotlin.math.max
import kotlin.math.min

data class SensorBeaconPair(val sensor: Coordinate, val beacon: Coordinate) {

    val d: Int = distance(sensor, beacon)

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

    companion object {

        fun fromPair(pair: Pair<Coordinate, Coordinate>): SensorBeaconPair {
            assert(pair.first.type == CoordinateType.SENSOR && pair.second.type == CoordinateType.BEACON)
            return SensorBeaconPair(pair.first, pair.second)
        }

    }

}