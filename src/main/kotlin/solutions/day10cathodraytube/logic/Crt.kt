package solutions.day10cathodraytube.logic

import kotlin.math.abs

class Crt {

    private val width : Int = 40
    private val height : Int = 6
    private val screen : MutableList<MutableList<Char>> = (0 until height).map { _ ->
        (0 until width).map { _ ->
            ' '
        }.toMutableList()
    }.toMutableList()

    private fun isPixelInSprite(xValue: Int, pixelPos: Int): Boolean {
        return abs(xValue-pixelPos) <= 1
    }

    private fun drawPixel(pixelPos: Int, xValue: Int) : Char {
        return if (isPixelInSprite(xValue, pixelPos)) '#' else '.'
    }

    fun draw(tick: Int, xValue: Int) {
        val rowPosition = tick / width
        val pixelPos = tick % width

        val pixel = drawPixel(pixelPos, xValue)
        screen[rowPosition][pixelPos] = pixel

    }

    override fun toString(): String {
        return screen.map { row -> row.joinToString("") }.joinToString("\n")
    }


}