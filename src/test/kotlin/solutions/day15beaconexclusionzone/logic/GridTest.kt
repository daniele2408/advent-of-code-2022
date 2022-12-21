package solutions.day15beaconexclusionzone.logic

import org.junit.jupiter.api.Assertions.*
import retrieveRowsFromFile
import solutions.day15beaconexclusionzone.model.Coordinate
import solutions.day15beaconexclusionzone.model.CoordinateType
import kotlin.test.Test
import kotlin.test.fail

class GridTest {

    @Test
    fun testParse() {
        val (sensor, beacon) = Grid.extractSensorBeacon("Sensor at x=12, y=14: closest beacon is at x=10, y=16")

        assertEquals(Coordinate(12, 14, CoordinateType.SENSOR), sensor)
        assertEquals(Coordinate(10, 16, CoordinateType.BEACON), beacon)
    }

    @Test
    fun testParse2() {
        val (sensor, beacon) = Grid.extractSensorBeacon("Sensor at x=-12, y=14: closest beacon is at x=10, y=-16")

        assertEquals(Coordinate(-12, 14, CoordinateType.SENSOR), sensor)
        assertEquals(Coordinate(10, -16, CoordinateType.BEACON), beacon)
    }

    @Test
    fun testGrid() {
        val rows = retrieveRowsFromFile("inputday15sample.txt")

        val grid = Grid.init(rows)

        println(grid)

        val res = grid.checkSurelyEmptyCoordForY(10)

        assertEquals(26, res)
    }

    @Test
    fun testGridPart2() {
        val rows = retrieveRowsFromFile("inputday15sample.txt")

        val grid = Grid.init(rows)

        println(grid)

        println(grid.printAllSensorsOneByOne())

        assertEquals(56000011, grid.findTuningFrequency(0, 20))
    }


}