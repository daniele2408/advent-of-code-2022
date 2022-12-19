package solutions.day02rockpaperscissor.model

enum class Outcome(val score : Int, val symbol: String) {
    WIN(6, "Z"),
    LOSE(0, "X"),
    DRAW(3, "Y");

    companion object {
        fun fromSymbol(s: String) : Outcome {
            return Outcome.values().first { m -> m.symbol == s }
        }

    }

}