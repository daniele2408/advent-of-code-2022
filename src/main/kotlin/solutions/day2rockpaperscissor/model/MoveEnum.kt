package solutions.day2rockpaperscissor.model

enum class MoveEnum(val symbolLeft: String, val symbolRight: String, val score: Int) {
    ROCK("A", "X", 1),
    PAPER("B", "Y", 2),
    SCISSOR("C", "Z", 3);

    companion object {

        fun fromSymbol(s: String) : MoveEnum {
            return values().first { m -> m.symbolRight == s || m.symbolLeft == s }
        }

    }

}