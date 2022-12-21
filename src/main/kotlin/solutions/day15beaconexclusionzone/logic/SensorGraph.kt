package solutions.day15beaconexclusionzone.logic

import solutions.day15beaconexclusionzone.model.Coordinate
import solutions.day15beaconexclusionzone.model.SensorBeaconPair

class SensorGraph(val sensorEdges: Map<Coordinate, Set<Coordinate>>) {

    fun getNodesOrderedByNEdges(): List<Coordinate> {
        return sensorEdges.entries.toList().sortedBy {
            it.value.size
        }.map { it.key }
    }

    companion object {

        fun init(sensorBeaconPairList: List<SensorBeaconPair>): SensorGraph {

            val associate = sensorBeaconPairList.associateWith { pair ->
                sensorBeaconPairList.filter {
                    it != pair && distance(it.sensor, pair.sensor) == (it.d + pair.d + 2)
                }
            }.filter { entry -> entry.value.isNotEmpty() }.map { entry -> entry.value.map { v -> entry.key to v } }.flatten()

            val sensorEdges: Map<Coordinate, Set<Coordinate>> = associate.map { it.first.sensor to it.second.sensor }
                .map { it.toList().sortedBy { sensor -> sensor.x } }.distinct()
                .groupBy {
                    it.first()
                }.mapValues { entry -> entry.value.flatten().toSet() }

//            val sensorEdges: Map<SensorBeaconPair, Set<SensorBeaconPair>> = sensorBeaconPairList.associateWith { pair ->
//                sensorBeaconPairList.filter {
//                    it != pair && distance(it.sensor, pair.sensor) == (it.d + pair.d + 2)
//                }
//            }.filter { entry -> entry.value.isNotEmpty() }.map { entry -> entry.value.map { v -> entry.key to v } }.flatten()
//                .map { el -> el.first.sensor to el.second.sensor }.toMap()
//
            return SensorGraph(sensorEdges)

        }

    }

}