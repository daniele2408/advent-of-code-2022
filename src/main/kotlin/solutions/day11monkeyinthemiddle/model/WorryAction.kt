package solutions.day11monkeyinthemiddle.model

import solutions.day11monkeyinthemiddle.logic.WorryOperation
import java.math.BigInteger

class WorryAction(private val worryOperation: WorryOperation, private val argument : BigInteger) {

    constructor(worryOperation: WorryOperation, argument: Int) : this(worryOperation, BigInteger.valueOf(argument.toLong()))

    fun applyTo(input : BigInteger) : BigInteger {
        return worryOperation.invoke(input, argument)
    }

    fun applyTo(input : Int) : BigInteger {
        return worryOperation.invoke(BigInteger.valueOf(input.toLong()), argument)
    }

    companion object {

        fun from(s: String) : WorryAction {
            val worryOperation = WorryOperation.from(s)
            val argument = Regex("[0-9]+").find(s)?.value?.toBigInteger() ?: BigInteger.valueOf(0)
            return WorryAction(worryOperation, argument)
        }
    }

}