package solutions.day9ropebridge.logic

import solutions.day9ropebridge.model.MoveAction

interface INavigator {
    val observer: RopeObserver
    val verbose: Boolean
    fun moveKnots(moveAction: MoveAction)
    fun move()

    fun printInternal(msg : String) {
        if (verbose) println(msg)
    }
}