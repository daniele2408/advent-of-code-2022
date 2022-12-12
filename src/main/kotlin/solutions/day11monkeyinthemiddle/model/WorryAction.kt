package solutions.day11monkeyinthemiddle.model

import solutions.day11monkeyinthemiddle.logic.WorryOperation

class WorryAction(val worryOperation: WorryOperation, val argument : Int) {

    fun applyTo(input: Int) : Int {
        return worryOperation.invoke(input, argument)
    }

    fun applyTo(input: Int, newArgument : Int) : Int {
        return worryOperation.invoke(input, newArgument)
    }

    override fun toString(): String {
        return "Action(${worryOperation.symbol} ${if (worryOperation == WorryOperation.SQUARE) "" else argument})"
    }

    companion object {

        fun from(s: String) : WorryAction {
            val worryOperation = WorryOperation.from(s)
            val argument = Regex("[0-9]+").find(s)?.value?.toInt() ?: 0
            return WorryAction(worryOperation, argument)
        }
    }

}