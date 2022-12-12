package solutions.day11monkeyinthemiddle.model

import solutions.day11monkeyinthemiddle.logic.WorryOperation

class WorryAction(val worryOperation: WorryOperation, val argument : Long) {

    fun applyTo(input: Long) : Long {
        return worryOperation.invoke(input, argument)
    }

    fun applyTo(input: Long, newArgument : Long) : Long {
        return worryOperation.invoke(input, newArgument)
    }

    override fun toString(): String {
        return "Action(${worryOperation.symbol} ${if (worryOperation == WorryOperation.SQUARE) "" else argument})"
    }

    companion object {

        fun from(s: String) : WorryAction {
            val worryOperation = WorryOperation.from(s)
            val argument = Regex("[0-9]+").find(s)?.value?.toLong() ?: 0
            return WorryAction(worryOperation, argument)
        }
    }

}