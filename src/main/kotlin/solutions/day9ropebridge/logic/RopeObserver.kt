package solutions.day9ropebridge.logic

import solutions.day9ropebridge.model.Knot

class RopeObserver {
    val logs : MutableSet<Knot> = mutableSetOf()

    fun addPointToLogs(knot: Knot) {
        logs.add(knot)
    }
}