package solutions.day12hillclimbingalgorithm.model

enum class WalkDirection(val symbol : String) {
    UP("^"),
    DOWN("v"),
    LEFT("<"),
    RIGHT(">");

    companion object {
        fun getOpposite(opposite : WalkDirection) : WalkDirection {
            return when (opposite) {
                UP -> DOWN
                DOWN -> UP
                LEFT -> RIGHT
                RIGHT -> LEFT
            }
        }
    }

}