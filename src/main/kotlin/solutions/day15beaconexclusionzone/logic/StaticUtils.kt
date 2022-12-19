package solutions.day15beaconexclusionzone.logic

import solutions.day15beaconexclusionzone.model.Coordinate
import kotlin.math.abs

fun distance(a: Coordinate, b: Coordinate): Int {
    return abs(a.x - b.x) + abs(a.y - b.y)
}