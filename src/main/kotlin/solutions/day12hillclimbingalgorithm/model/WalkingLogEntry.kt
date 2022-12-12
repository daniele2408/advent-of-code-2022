package solutions.day12hillclimbingalgorithm.model

class WalkingLogEntry(val direction: WalkDirection, val coord: Coord, var isCross: Boolean = false) {

    override fun toString(): String {
        return "${direction.symbol} [$coord] ${if (isCross) "x" else "|"}"
    }
}