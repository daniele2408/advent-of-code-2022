package solutions.day14regolithreservoir

import retrieveRowsFromFile
import solutions.day14regolithreservoir.logic.Cave

fun howMuchSandResting() : Int {

    val rows = retrieveRowsFromFile("inputday14.txt")

    val cave = Cave.init(rows)

    cave.startPouring()

    return cave.countGrainsOfSand()
}

fun howMuchSandRestingHavingFloor() : Int {

    val rows = retrieveRowsFromFile("inputday14.txt")

    val cave = Cave.initWithFloor(rows)

    cave.startPouring()

    return cave.countGrainsOfSand()
}