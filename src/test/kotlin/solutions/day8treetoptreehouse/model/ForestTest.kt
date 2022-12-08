package solutions.day8treetoptreehouse.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ForestTest {
    @Test
    fun testFrom() {

        val input = """
            123
            456
            789
        """.trimIndent()

        val forest = Forest.fromList(input.split('\n'))

        assertEquals(1, forest.getTree(0, 0).height)
        assertEquals(5, forest.getTree(1, 1).height)
        assertEquals(9, forest.getTree(2, 2).height)
    }

    @Test
    fun testVisible() {
        val input = """
            123
            456
            789
        """.trimIndent()

        val forest = Forest.fromList(input.split('\n'))

        val res = forest.trees[1][1].canSeeOut(forest)

        assertTrue(res)
    }

    @Test
    fun testCount1() {
        val input = """
            999
            909
            999
        """.trimIndent()

        val forest = Forest.fromList(input.split('\n'))

        assertEquals(8, forest.countVisibleTrees())
    }

    private val input = """
            30373
            25512
            65332
            33549
            35390
        """.trimIndent()

    @Test
    fun testCount2() {

        val solutionViz = """
            OOOOO
            OOOXO
            OOXOO
            OXOXO
            OOOOO
        """.trimIndent()

        val forest = Forest.fromList(input.split('\n'))

        println(forest.printTreeViz())

        assertEquals(21, forest.countVisibleTrees())
        assertEquals(solutionViz, forest.printTreeViz())

        val expectedNotVisible = listOf(
            ForestTree(1, 3, 1),
            ForestTree(2, 2, 3),
            ForestTree(3, 1, 3),
            ForestTree(3, 3, 4)
        )

        val visibleTrees = forest.trees.flatten().filter { it.canSeeOut(forest) }.toList()
        val notVisibleTrees = forest.trees.flatten().filter { !it.canSeeOut(forest) }.toList()

        assertTrue(visibleTrees.none { it in expectedNotVisible })
        assertTrue(notVisibleTrees.all { it in expectedNotVisible })

    }

    @Test
    fun testGetHighestScenicScore() {
        val forest = Forest.fromList(input.split('\n'))

        val bestScenicScore = forest.getBestScenicScore()

        assertEquals(8, bestScenicScore)
    }
}