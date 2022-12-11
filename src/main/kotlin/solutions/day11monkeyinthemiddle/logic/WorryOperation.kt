package solutions.day11monkeyinthemiddle.logic

import java.math.BigInteger

enum class WorryOperation(private val f : (BigInteger, BigInteger) -> (BigInteger) ) {
    PLUS({ old, toAdd -> old + toAdd }),
    TIMES({ old, toMultiplyWith -> old * toMultiplyWith }),
    SQUARE({ old, _ -> old * old }),
    DIVIDE({ old, toDivide -> old / toDivide}), // could implement as times 1/n, but I need integers, so it will do
    ;

    fun invoke(a: BigInteger, b: BigInteger) : BigInteger {
        return f.invoke(a, b)
    }

    companion object {

        private val plusPattern: Regex = """old \+ [0-9]+""".toRegex()
        private val timesPattern: Regex = """old \* [0-9]+""".toRegex()
        private val squarePattern: Regex = """old \* old""".toRegex()
        private val dividePattern: Regex = """old / old""".toRegex()

        fun from(s: String) : WorryOperation {
            return when {
                s.matches(plusPattern) -> PLUS
                s.matches(timesPattern) -> TIMES
                s.matches(squarePattern) -> SQUARE
                s.matches(dividePattern) -> DIVIDE
                else -> throw RuntimeException("Couldn't find matching worry operation for string input '$s'")
            }
        }

    }
}