package solutions.day12hillclimbingalgorithm.logic

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

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

        val heatMap = HeatMap.create(rows)

        assertEquals(0, heatMap.getCoord(0, 0).h)

    }

    @Test
    fun testDijkstra() {
        val rows = sampleInput.split("\n")

        val heatMap = HeatMap.create(rows)
        val stepsFromStart = heatMap.runDijkstra()

        assertEquals(31, stepsFromStart)


    }

    @Test
    fun testDijkstraPart2() {
        val rows = sampleInput.split("\n")

        val heatMap = HeatMap.create(rows)
        val stepsFromStart = heatMap.runDijkstra()

        assertEquals(31, stepsFromStart)

        val inversedijkstra = heatMap.inverseDijkstra()
        assertEquals(29, inversedijkstra)

    }

}