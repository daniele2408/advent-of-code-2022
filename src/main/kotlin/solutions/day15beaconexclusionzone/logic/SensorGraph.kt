package solutions.day15beaconexclusionzone.logic

import solutions.day15beaconexclusionzone.model.Coordinate
import solutions.day15beaconexclusionzone.model.SensorBeaconPair

class SensorGraph(val sensorEdges: Map<Coordinate, Set<Coordinate>>, val sensorBeaconPairList: List<SensorBeaconPair>) {

    private fun getNodesOrderedByDistanceAndNEdges(): List<Coordinate> {
        return sensorEdges.entries.toList().sortedWith(compareBy<Map.Entry<Coordinate, Set<Coordinate>>> { getDistanceSensorBeaconForSensor(it.key) }.thenByDescending  { it.value.size }).map { it.key }
    }

    private fun getDistanceSensorBeaconForSensor(sensor: Coordinate) : Int {
        return sensorBeaconPairList.find { it.sensor == sensor }!!.d
    }

    private fun getExternalPerimeter(sensor: Coordinate) : Set<Coordinate> {
        return sensorBeaconPairList.find { it.sensor == sensor }!!.perimeter.coordinates().toSet()
    }

    fun emitCoordsToExaminate(): Sequence<Set<Coordinate>> {
        return getNodesOrderedByDistanceAndNEdges().map { getExternalPerimeter(it) }.asSequence()
    }

    companion object {

        fun init(sensorBeaconPairList: List<SensorBeaconPair>): SensorGraph {

            val associate: List<Pair<SensorBeaconPair, SensorBeaconPair>> = sensorBeaconPairList.associateWith { pair ->
                sensorBeaconPairList.filter {
                    it != pair && distance(it.sensor, pair.sensor) == (it.d + pair.d + 2)
                }
            }.filter { entry -> entry.value.isNotEmpty() }.map { entry -> entry.value.map { v -> entry.key to v } }.flatten()

            val sensorEdges: Map<Coordinate, Set<Coordinate>> = associate.map { it.first.sensor to it.second.sensor }
                .map { it.toList().sortedBy { sensor -> sensor.x } }.distinct()
                .groupBy {
                    it.first()
                }.mapValues { entry -> entry.value.flatten().filter { it != entry.key }.toSet() }

            return SensorGraph(sensorEdges, sensorBeaconPairList)

        }

    }

}