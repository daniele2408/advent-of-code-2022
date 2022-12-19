package solutions.day09ropebridge.logic

import solutions.day09ropebridge.model.Knot

class RopeObserver {
    val logs : MutableSet<Knot> = mutableSetOf()

    fun addPointToLogs(knot: Knot) {
        logs.add(knot)
    }
}