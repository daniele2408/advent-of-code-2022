package solutions.day15beaconexclusionzone.logic

import solutions.day15beaconexclusionzone.model.Coordinate
import solutions.day15beaconexclusionzone.model.CoordinateType
import solutions.day15beaconexclusionzone.model.GridCanva
import solutions.day15beaconexclusionzone.model.SensorBeaconPair

class Grid(val sbPairs: List<SensorBeaconPair>, val gridCanva: GridCanva, val leftMostX: Int, val rightMostX: Int) {

    fun isCoordinateInSensorRange(coordinate: Coordinate): Boolean {
        val res = sbPairs.any { distance(it.sensor, coordinate) <= it.d }
        return res
    }

    fun isCoordinateBeacon(coordinate: Coordinate): Boolean {
        return sbPairs.any { it.beacon.isAt(coordinate) }
    }

    fun isCoordinateSensor(coordinate: Coordinate): Boolean {
        return sbPairs.any { it.sensor.isAt(coordinate) }
    }

    fun checkSurelyEmptyCoordForY(y: Int) : Int {
        return (leftMostX..rightMostX).map { x -> Coordinate(x, y, CoordinateType.VOID) }
            .count { !isCoordinateBeacon(it) && !isCoordinateSensor(it) && isCoordinateInSensorRange(it) }
    }

    companion object {

        fun init(rows: List<String>) : Grid {
            val sbPairs: List<SensorBeaconPair> = rows.map { SensorBeaconPair.fromPair(extractSensorBeacon(it)) }.toList()
            val minX: Int = sbPairs.minOf { it.getMinX() }
            val minY: Int = sbPairs.minOf { it.getMinY() }
            val maxX: Int = sbPairs.maxOf { it.getMaxX() }
            val maxY: Int = sbPairs.maxOf { it.getMaxY() }

            val leftMostPair: SensorBeaconPair = sbPairs.minBy { it.sensor.x }
            val rightMostPair: SensorBeaconPair = sbPairs.maxBy { it.sensor.x }

            val leftEdge: Int = leftMostPair.sensor.x - leftMostPair.d
            val rightEdge: Int = rightMostPair.sensor.x + rightMostPair.d

            return Grid(sbPairs, GridCanva(minX, maxX, minY, maxY), leftEdge, rightEdge)

        }

        fun extractSensorBeacon(s: String): Pair<Coordinate, Coordinate> {

            val (sensorX, beaconX) = Regex("(?<=x=)(-|)[0-9]+").findAll(s).map { it.groupValues }
                .flatten()
                .filter { it != "" && it != "-" }
                .toList()
            val (sensorY, beaconY) = Regex("(?<=y=)(-|)[0-9]+").findAll(s).map { it.groupValues }
                .flatten()
                .filter { it != "" && it != "-" }
                .toList()

            val sensor = Coordinate(sensorX.toInt(), sensorY.toInt(), CoordinateType.SENSOR)
            val beacon = Coordinate(beaconX.toInt(), beaconY.toInt(), CoordinateType.BEACON)
            return sensor to beacon
        }

    }

    override fun toString(): String {

        return generateSequence(gridCanva.minY) { if (it < gridCanva.maxY) it + 1 else null }
            .map { j ->
                "${j.toString().padStart(2)} " + generateSequence(gridCanva.minX-10) { if (it < gridCanva.maxX+10) it + 1 else null }.map { i ->
                    printPixel(i, j)
                }.joinToString("")
            }.joinToString("\n")

//        return grid.mapIndexed { idx, row -> (idx - offSetY).toString().padStart(2) + " " + row.map { it.type.symbol }.joinToString("") }.joinToString("\n")
    }

    private fun printPixel(x: Int, y: Int) : String {
        val c = Coordinate(x, y, CoordinateType.VOID)
        return when {
            isCoordinateBeacon(c) -> CoordinateType.BEACON.symbol
            isCoordinateSensor(c) -> CoordinateType.SENSOR.symbol
            isCoordinateInSensorRange(c) -> "#"
            else -> CoordinateType.VOID.symbol
        }
    }

}