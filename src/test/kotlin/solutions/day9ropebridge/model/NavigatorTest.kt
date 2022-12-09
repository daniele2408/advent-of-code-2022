package solutions.day9ropebridge.model

import org.junit.jupiter.api.Assertions.*
import solutions.day9ropebridge.logic.Navigator
import solutions.day9ropebridge.logic.NavigatorMultiKnots
import kotlin.test.Test

const val simpleInput: String = """R 4
U 2"""

const val sampleInputPart1: String = """R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2"""

const val sampleInputPart2: String = """R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20"""

class NavigatorTest {

    @Test
    fun testSimple() {
        val rows = simpleInput.split('\n')

        val navigator = Navigator.from(rows)

        navigator.move()

        assertEquals(Knot(4,1), navigator.positionT)
        assertEquals(Knot(4,2), navigator.positionH)

        assertEquals(5, navigator.totalPositionVisitedByT())

    }

    @Test
    fun testOk() {
        val rows = sampleInputPart1.split('\n')

        val navigator = Navigator.from(rows, true)

        navigator.move()

        assertEquals(Knot(1,2), navigator.positionT)
        assertEquals(Knot(2,2), navigator.positionH)

        assertEquals(13, navigator.totalPositionVisitedByT())
    }

    @Test
    fun testOkPart2() {
        val rows = sampleInputPart2.split('\n')

        val navigatorMultiKnots = NavigatorMultiKnots.from(10, rows, true)

        navigatorMultiKnots.move()

        assertEquals(36, navigatorMultiKnots.totalPositionVisitedByLast())

    }

    @Test
    fun testOkPart2Input1() {
        val rows = sampleInputPart1.split('\n')

        val navigatorMultiKnots = NavigatorMultiKnots.from(10, rows, true)

        navigatorMultiKnots.move()

        assertTrue(true)

    }

}