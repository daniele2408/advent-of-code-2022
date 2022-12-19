package solutions.day14regolithreservoir.logic

import retrieveRowsFromFile
import solutions.day14regolithreservoir.model.Coordinate
import solutions.day14regolithreservoir.model.RangeCoordinate
import kotlin.test.*


class CaveTest {

    @Test
    fun testRange() {
        val start = Coordinate.from("498,4")
        val end = Coordinate.from("498,6")
        val rangeCoordinate = RangeCoordinate.init(start, end)

        assertTrue(rangeCoordinate.contains(498, 4))
        assertTrue(rangeCoordinate.contains(498, 5))
        assertTrue(rangeCoordinate.contains(498, 6))

        assertFalse(rangeCoordinate.contains(498, 3))
        assertFalse(rangeCoordinate.contains(498, 7))
        assertFalse(rangeCoordinate.contains(497, 5))
        assertFalse(rangeCoordinate.contains(499, 5))
    }

    @Test
    fun testRangeInit() {
        val start = Coordinate.from("498,4")
        val end = Coordinate.from("498,6")
        val rangeCoordinate = RangeCoordinate.init(start, end)
        val rangeCoordinate2 = RangeCoordinate.init(end, start)

        assertEquals(rangeCoordinate, rangeCoordinate)
        assertEquals(rangeCoordinate2, rangeCoordinate2)
        assertEquals(rangeCoordinate, rangeCoordinate2)
    }

    @Test
    fun testPart1() {
        val rows = retrieveRowsFromFile("inputday14sample.txt")

        val cave = Cave.init(rows)

        cave.startPouring()

        assertEquals(24, cave.countGrainsOfSand())
    }

    @Test
    fun testPart2() {
        val rows = retrieveRowsFromFile("inputday14sample.txt")

        val cave = Cave.initWithFloor(rows)

        cave.startPouring()

        assertEquals(93, cave.countGrainsOfSand())
    }

}