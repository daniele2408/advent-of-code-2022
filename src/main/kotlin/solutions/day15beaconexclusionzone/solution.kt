package solutions.day15beaconexclusionzone

import retrieveRowsFromFile
import solutions.day15beaconexclusionzone.logic.Grid

fun positionExcludingBeacon(y: Int): Int {
    val rows = retrieveRowsFromFile("inputday15.txt")

    val grid = Grid.init(rows)

    return grid.checkSurelyEmptyCoordForY(y)

}