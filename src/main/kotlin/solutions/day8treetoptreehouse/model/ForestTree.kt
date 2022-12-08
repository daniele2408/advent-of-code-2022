package solutions.day8treetoptreehouse.model

import takeWhileInclusive

data class ForestTree(val row : Int, val col : Int, val height : Int) {

    private fun canSeeOut(forest: Forest, view: ForestView) : Boolean {
        if (row == 0 || col == 0 || row == forest.forestSize - 1 || col == forest.forestSize - 1) return true

        val sliceTrees = when (view) {
            ForestView.UPWARD -> forest.sliceForest(row-1, 0, col, Forest.Axis.COLUMN)
            ForestView.DOWNWARD -> forest.sliceForest(row+1, forest.forestSize - 1, col, Forest.Axis.COLUMN)
            ForestView.LEFT -> forest.sliceForest(col-1, 0, row, Forest.Axis.ROW)
            ForestView.RIGHT -> forest.sliceForest(col+1, forest.forestSize - 1, row, Forest.Axis.ROW)
        }

        return sliceTrees.isEmpty() || sliceTrees.all { it.height < height }

    }



    private fun countTreesICanSee(forest: Forest, view: ForestView) : Int {
        val sliceTrees = when (view) {
            ForestView.UPWARD -> forest.sliceForest(row, 0, col, Forest.Axis.COLUMN)
            ForestView.DOWNWARD -> forest.sliceForest(row, forest.forestSize - 1, col, Forest.Axis.COLUMN)
            ForestView.LEFT -> forest.sliceForest(col, 0, row, Forest.Axis.ROW)
            ForestView.RIGHT -> forest.sliceForest(col, forest.forestSize - 1, row, Forest.Axis.ROW)
        }

        return sliceTrees.subList(1, sliceTrees.size).takeWhileInclusive { it.height < height }.count()
    }

    fun canSeeOut(forest: Forest) : Boolean {
        return ForestView.values().any { canSeeOut(forest, it) }
    }

    fun getScenicScore(forest: Forest) : Int {
        return ForestView.values().map { countTreesICanSee(forest, it) }.reduce { acc, i -> acc * i }
    }

}
