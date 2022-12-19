package solutions.day09ropebridge.logic

import solutions.day09ropebridge.model.MoveAction

interface INavigator {
    val observer: RopeObserver
    val verbose: Boolean
    fun moveKnots(moveAction: MoveAction)
    fun move()

    fun printInternal(msg : String) {
        if (verbose) println(msg)
    }
}