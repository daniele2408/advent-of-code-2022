package solutions.day08treetoptreehouse.model

class Forest(val trees: List<List<ForestTree>>) {

    val forestSize : Int = trees.size // it's a square, right?

    fun getTree(row : Int, col : Int) : ForestTree {
        return trees[row][col]
    }

    enum class Axis {
        COLUMN,
        ROW
    }

    fun sliceForest(fromIdx: Int, toIdx: Int, axisIdx: Int, axis: Axis) : List<ForestTree> {
        val range = if (fromIdx <= toIdx) fromIdx..toIdx else fromIdx downTo toIdx
        return range.map {
            val forestTree = when (axis) {
                Axis.COLUMN -> trees[it][axisIdx]
                Axis.ROW -> trees[axisIdx][it]
            }
            forestTree
        }.toList()
    }

    fun countVisibleTrees() : Int {
        return trees.flatten().count { it.canSeeOut(this) }
    }

    fun printTreeViz() : String {
        val chunked = trees.flatten().map { if (it.canSeeOut(this)) "O" else "X" }.chunked(forestSize)
        return chunked.joinToString("\n") { it.joinToString("") }
    }

    fun getBestScenicScore(): Int {
        return trees.flatten().maxOf { it.getScenicScore(this) }
    }

    companion object {
        fun fromList(rows: List<String>) : Forest {
            val nTreesBySide = rows.size
            val trees = rows.asSequence().map { it.toList() }.flatten().mapIndexed { idx, height ->
                ForestTree(
                    idx.div(nTreesBySide),
                    idx.rem(nTreesBySide),
                    height.toString().toInt()
                )
            }.toList().chunked(rows.size).toList()
            return Forest(trees)
        }
    }

}