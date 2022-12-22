package solutions.day15beaconexclusionzone.logic

import solutions.day15beaconexclusionzone.model.Coordinate
import solutions.day15beaconexclusionzone.model.CoordinateType
import solutions.day15beaconexclusionzone.model.GridCanva
import solutions.day15beaconexclusionzone.model.SensorBeaconPair
import java.math.BigInteger

class Grid(private val sbPairs: List<SensorBeaconPair>, private val gridCanva: GridCanva, private val leftMostX: Int, private val rightMostX: Int) {

    private fun isCoordinateInSomeSensorRange(coordinate: Coordinate): Boolean {
        return sbPairs.any { distance(it.sensor, coordinate) <= it.d }
    }

    private fun isCoordinateBeacon(coordinate: Coordinate): Boolean {
        return sbPairs.any { it.beacon.isAt(coordinate) }
    }

    private fun isCoordinateSensor(coordinate: Coordinate): Boolean {
        return sbPairs.any { it.sensor.isAt(coordinate) }
    }

    fun checkSurelyEmptyCoordForY(y: Int) : Int {
        return (leftMostX..rightMostX).map { x -> Coordinate(x, y, CoordinateType.UNKNOWN) }
            .count { !isCoordinateBeacon(it) && !isCoordinateSensor(it) && isCoordinateInSomeSensorRange(it) }
    }

    private fun findUncoveredSpot(lowerBound: Int, upperBound: Int): Coordinate {

        val sensorGraph = SensorGraph.init(sbPairs)

        // prendere i node con più edge e indagare in mezzo a questi
        return sensorGraph.emitCoordsToExaminate()
            .map {
                it
                    .filter { coord -> coord.x in (lowerBound..upperBound) && coord.y in (lowerBound..upperBound) }
                    .firstOrNull { coord -> !isCoordinateBeacon(coord) && !isCoordinateSensor(coord) && !isCoordinateInSomeSensorRange(coord) }
            }
            .firstOrNull() ?: throw RuntimeException("Missing beacon not found")

    }

    fun findTuningFrequency(lowerBound: Int, upperBound: Int): BigInteger {
        val uncoveredSpot = findUncoveredSpot(lowerBound, upperBound)
        return BigInteger.valueOf(4_000_000) * BigInteger.valueOf(uncoveredSpot.x.toLong()) + BigInteger.valueOf(uncoveredSpot.y.toLong())
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
    }

    private fun printPixel(x: Int, y: Int) : String {
        val c = Coordinate(x, y, CoordinateType.UNKNOWN)
        return when {
            isCoordinateBeacon(c) -> CoordinateType.BEACON.symbol
            isCoordinateSensor(c) -> CoordinateType.SENSOR.symbol
            isCoordinateInSomeSensorRange(c) -> "#"
            else -> CoordinateType.UNKNOWN.symbol
        }
    }

    fun printAllSensorsOneByOne() : String {
        return sbPairs.joinToString("\n\n") { pair ->
            "\n\n$pair\n" + generateSequence(gridCanva.minY) { if (it < gridCanva.maxY) it + 1 else null }
                .map { j ->
                    "${
                        j.toString().padStart(2)
                    } " + generateSequence(gridCanva.minX - 10) { if (it < gridCanva.maxX + 10) it + 1 else null }.map { i ->
                        printPixelOneSensor(pair, i, j)
                    }.joinToString("")
                }.joinToString("\n")
        }
    }

    private fun printPixelOneSensor(sensorBeaconPair: SensorBeaconPair, x: Int, y: Int) : String {
        val c = Coordinate(x, y, CoordinateType.UNKNOWN)
        return when {
            x == 14 && y == 11 -> "X"
            sensorBeaconPair.beacon.isAt(c) -> CoordinateType.BEACON.symbol
            sensorBeaconPair.sensor.isAt(c) -> CoordinateType.SENSOR.symbol
            distance(sensorBeaconPair.sensor, c) <= sensorBeaconPair.d -> "#"
            else -> CoordinateType.UNKNOWN.symbol
        }
    }

}