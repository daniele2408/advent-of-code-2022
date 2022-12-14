package solutions.day12hillclimbingalgorithm.logic

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test
import kotlin.test.fail

class HeatMapTest {

    val sampleInput: String = """
        Sabqponm
        abcryxxl
        accszExk
        acctuvwj
        abdefghi
    """.trimIndent()

    @Test
    fun testFrom() {
        val rows = sampleInput.split("\n")

        val heatMap = HeatMap.from(rows)

        assertEquals(0, heatMap.getCoord(0, 0).h)

    }

    @Test
    fun testWalk() {
        val rows = sampleInput.split("\n")

        val heatMap = HeatMap.from(rows)
        heatMap.startWalkingIter()

        assertEquals(31, heatMap.getTotalSteps())
    }

    @Test
    fun testScan() {
        val rows = sampleInput.split("\n")

        val heatMap = HeatMap.from(rows)
        print(heatMap)
        while (heatMap.currentPosition.h != heatMap.target.h) {
            val (coord, coordHigher) = heatMap.scanGridSquare()
            heatMap.currentPosition = coordHigher
        }

    }

    @Test
    fun testWalkScan() {
        val rows = sampleInput.split("\n")

        val heatMap = HeatMap.from(rows)
        heatMap.startWalking()

        assertEquals(31, heatMap.getTotalSteps())
    }

}