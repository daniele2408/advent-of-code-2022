package solutions.day9ropebridge

import retrieveRowsFromFile
import solutions.day9ropebridge.logic.Navigator
import solutions.day9ropebridge.logic.NavigatorMultiKnots

fun totalPositionTailVisited() : Int {
    val rows: List<String> = retrieveRowsFromFile("inputday09.txt")

    val navigator = Navigator.from(rows)

    navigator.move()

    return navigator.totalPositionVisitedByT()
}

fun totalPositionRopeTailVisited() : Int {
    val rows: List<String> = retrieveRowsFromFile("inputday09.txt")

    val navigator = NavigatorMultiKnots.from(10, rows)

    navigator.move()

    return navigator.totalPositionVisitedByLast()
}