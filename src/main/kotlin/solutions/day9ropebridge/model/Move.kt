package solutions.day9ropebridge.model

enum class Move(val symbol: String) {
    UP("U"),
    DOWN("D"),
    LEFT("L"),
    RIGHT("R");

    companion object {
        fun fromSymbol(s: String) : Move {
            return values().first { it.symbol == s }
        }
    }
}