package solutions.day12hillclimbingalgorithm.logic

import org.junit.jupiter.api.Assertions.*
import retrieveRowsFromFile
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

        val heatMap = HeatMap.from(rows)

        assertEquals(0, heatMap.getCoord(0, 0).h)

    }

    @Test
    fun testDjikstra() {
        val rows = sampleInput.split("\n")

        val heatMap = HeatMap.from(rows)
        val steps = heatMap.startDjikstra()

        assertEquals(31, steps)

    }

    @Test
    fun testDjikstraReal() {
        val rows = retrieveRowsFromFile("inputday12.txt")

        val heatMap = HeatMap.from(rows)
        val steps = heatMap.startDjikstra()

        assertEquals(380, steps)

    }


}