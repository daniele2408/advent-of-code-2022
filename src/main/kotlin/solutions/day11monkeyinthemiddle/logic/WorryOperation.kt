package solutions.day11monkeyinthemiddle.logic

enum class WorryOperation(val symbol: String, private val f : (Int, Int) -> (Int) ) {
    PLUS("+", { old, toAdd -> old + toAdd }),
    TIMES("*", { old, toMultiplyWith -> old * toMultiplyWith }),
    SQUARE("^2", { old, _ -> old * old }),
    DIVIDE("/", { old, toDivide -> old / toDivide}), // could implement as times 1/n, but I need integers, so it will do
    ;

    fun invoke(a: Int, b: Int) : Int {
        return f.invoke(a, b)
    }

    fun invariantModuleTransform(a : Int, b : Int, testDivisible : Int): Pair<Int, Int> {
        return when (this) {
            TIMES -> (a*b) % testDivisible to 1
            SQUARE -> a to b
            PLUS -> (a+b) % testDivisible to testDivisible
            else -> a to b
        }
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