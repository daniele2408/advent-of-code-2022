package solutions.day12hillclimbingalgorithm.logic

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test
import kotlin.test.fail

class HeatMapTest {

    val sampleInput: String = """
        aabqponm
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
        heatMap.startWalking()

        assertEquals(31, heatMap.getTotalSteps())
    }
}