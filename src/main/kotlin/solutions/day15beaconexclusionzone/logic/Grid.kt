package solutions.day15beaconexclusionzone.logic

import solutions.day15beaconexclusionzone.model.Coordinate
import solutions.day15beaconexclusionzone.model.CoordinateType
import solutions.day15beaconexclusionzone.model.GridCanva
import solutions.day15beaconexclusionzone.model.SensorBeaconPair

class Grid(val sbPairs: List<SensorBeaconPair>, val gridCanva: GridCanva, val leftMostX: Int, val rightMostX: Int) {

    fun isCoordinateInSomeSensorRange(coordinate: Coordinate): Boolean {
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
        return (leftMostX..rightMostX).map { x -> Coordinate(x, y, CoordinateType.UNKNOWN) }
            .count { !isCoordinateBeacon(it) && !isCoordinateSensor(it) && isCoordinateInSomeSensorRange(it) }
    }

    fun lookForUncoveredSpot(xStart: Int, xEnd: Int, yStart: Int, yEnd: Int) : Coordinate? {
        return (xStart..xEnd).map {x ->
            (yStart..yEnd).map{ y ->
                Coordinate(x, y, CoordinateType.UNKNOWN)
            }
        }.flatten().firstOrNull { !isCoordinateBeacon(it) && !isCoordinateSensor(it) && !isCoordinateInSomeSensorRange(it) }
    }

    fun findUncoveredSpotInefficient(lowerBound: Int, upperBound: Int): Coordinate {
        return (lowerBound..upperBound).map {y -> (lowerBound..upperBound).map {
                x -> Coordinate(x, y, CoordinateType.UNKNOWN) }
        }.flatten().first { !isCoordinateBeacon(it) && !isCoordinateSensor(it) && !isCoordinateInSomeSensorRange(it) }
    }

    fun  findUncoveredSpot(lowerBound: Int, upperBound: Int): Coordinate {
//        val countMap: Map<Coordinate, Int> = sbPairs.map {
//            it.perimeter.coordinates.filter {elPer ->  elPer.x in (lowerBound..upperBound) && elPer.y in (lowerBound..upperBound) }
//        }.flatten().groupingBy { it }.eachCount()
//
//        return countMap.entries.map { (k, v) -> k to v }.maxByOrNull { it.second }!!.first

        //prima di tramutare in seq i perimetri
//        return sbPairs.sortedBy { it.d }.map { it.perimeter.coordinates.filter { c -> c.x in (lowerBound..upperBound) && c.y in (lowerBound..upperBound) } }.flatten().first { coord ->
//            !isCoordinateInSomeSensorRange(coord)

//        val reduce = sbPairs.sortedBy { it.d }.map {
//            it.perimeter.coordinates()
//                .filter { c -> c.x in (lowerBound..upperBound) && c.y in (lowerBound..upperBound) }
//                .toSet()
//        }.reduce { setA, setB -> setA intersect setB }
//        return reduce.first()

        val associate = sbPairs.associateWith { pair ->
            sbPairs.filter {
                it != pair && distance(it.sensor, pair.sensor) == (it.d + pair.d + 2)
            }
        }.filter { entry -> entry.value.isNotEmpty() }.map { entry -> entry.value.map { v -> entry.key to v } }.flatten()

        val sensorGraph = SensorGraph.init(sbPairs)

        // prendere i node con più edge e indagare in mezzo a questi
        val nodesOrderedByNEdges = sensorGraph.getNodesOrderedByNEdges()


        val res = associate.map { pair ->
            val pairAsList = pair.toList()
            val xStart: Int = pairAsList.minOf { it.sensor.x }
            val xEnd: Int = pairAsList.maxOf { it.sensor.x }
            val yStart: Int = pairAsList.minOf { it.sensor.y }
            val yEnd: Int = pairAsList.maxOf { it.sensor.y }
//            println("Looking for x from $xStart to $xEnd and for y from $yStart to $yEnd (source ${pair.first} + ${pair.second}")
            lookForUncoveredSpot(xStart, xEnd, yStart, yEnd)
        }.firstNotNullOf { it }

        return res

    }

    fun findTuningFrequency(lowerBound: Int, upperBound: Int): Int {
        val uncoveredSpot = findUncoveredSpot(lowerBound, upperBound)
        return 4_000_000 * uncoveredSpot.x + uncoveredSpot.y
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
        val c = Coordinate(x, y, CoordinateType.UNKNOWN)
        return when {
            isCoordinateBeacon(c) -> CoordinateType.BEACON.symbol
            isCoordinateSensor(c) -> CoordinateType.SENSOR.symbol
            isCoordinateInSomeSensorRange(c) -> "#"
            else -> CoordinateType.UNKNOWN.symbol
        }
    }

    fun printAllSensorsOneByOne() : String {
        return sbPairs.map { pair ->
            "\n\n$pair\n" + generateSequence(gridCanva.minY) { if (it < gridCanva.maxY) it + 1 else null }
                .map { j ->
                    "${j.toString().padStart(2)} " + generateSequence(gridCanva.minX-10) { if (it < gridCanva.maxX+10) it + 1 else null }.map { i ->
                        printPixelOneSensor(pair, i, j)
                    }.joinToString("")
                }.joinToString("\n")
        }.joinToString("\n\n")
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