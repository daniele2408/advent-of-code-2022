package solutions.day11monkeyinthemiddle.logic

enum class WorryOperation(val symbol: String, private val f : (Long, Long) -> (Long) ) {
    PLUS("+", { old, toAdd -> old + toAdd }),
    TIMES("*", { old, toMultiplyWith -> old * toMultiplyWith }),
    SQUARE("^2", { old, _ -> old * old }),
    DIVIDE("/", { old, toDivide -> old / toDivide})
    ;

    fun invoke(a: Long, b: Long) : Long {
        return f.invoke(a, b)
    }

    companion object {

        private val plusPattern: Regex = """old \+ [0-9]+""".toRegex()
        private val timesPattern: Regex = """old \* [0-9]+""".toRegex()
        private val squarePattern: Regex = """old \* old""".toRegex()
        private val dividePattern: Regex = """old / old""".toRegex()

        fun from(s: String) : WorryOperation {
            return when {
                s.matches(plusPattern) ->    PLUS
                s.matches(timesPattern) -> TIMES
                s.matches(squarePattern) -> SQUARE
                s.matches(dividePattern) -> DIVIDE
                else -> throw RuntimeException("Couldn't find matching worry operation for string input '$s'")
            }
        }

    }
}