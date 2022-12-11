package solutions.day11monkeyinthemiddle.logic

import solutions.day11monkeyinthemiddle.model.BagItem
import solutions.day11monkeyinthemiddle.model.Monkey
import solutions.day11monkeyinthemiddle.model.ThrowPolicy
import solutions.day11monkeyinthemiddle.model.WorryAction
import java.math.BigInteger

class Circus(val monkeys : List<Monkey>, private val worryAction: WorryAction) {


    fun runRounds(nRounds : Int) {
        (1..nRounds).forEach { ithRound ->
            monkeys.forEach { monkey ->
                (0 until monkey.items.size).forEach { _ ->
                    val item = monkey.throwItem()
                    if (item != null) {
                        var worryLevel: BigInteger = monkey.makeWorry(item.worryLevel)
                        worryLevel = worryAction.applyTo(worryLevel)
                        monkeys[monkey.addressThrowTo(worryLevel)].addItem(BagItem(worryLevel))
                    }
                }
            }
            if (ithRound in listOf(1, 2, 3, 4, 20, 1000, 2000, 4000, 5000, 6000, 7000, 8000, 9000, 10000))
                println("After Round ${ithRound} circus is like this:\n$this\n\n")
        }
    }

    fun getMonkeyBusinessLevel() : BigInteger {
        return monkeys.sortedBy { it.inspectCount }.reversed().take(2).map { it.inspectCount }.reduce { a, b -> a * b}
    }

    override fun toString(): String {
        return monkeys.joinToString("\n") { it.toString() }
    }


    companion object {

        fun from(inputs : List<String>, myOwnWorryAction: WorryAction) : Circus {
            val trimmedInputs = inputs.map { it.trimIndent() }
            val nMonkeys : Int = trimmedInputs.filter { it.startsWith("Monkey") }.maxOf { Regex("[0-9]+").find(it)?.value.toString().toInt() }
            val listItems : List<List<BigInteger>> = trimmedInputs.filter { it.startsWith("Starting items:") }.map { Regex("[0-9]+").findAll(it).map { e -> e.value.toBigInteger() }.toList() }.toList()
            val listOperations : List<String> = trimmedInputs.filter { it.startsWith("Operation:") }.map { Regex("new = old .*").find(it)?.value.toString().replace("new = ", "") }.toList()
            val listTest : List<BigInteger> = trimmedInputs.filter { it.startsWith("Test:") }.map { Regex("[0-9]+").find(it)?.value.toString().toBigInteger() }.toList()
            val listOutcomeTrue : List<Int> = trimmedInputs.filter { it.startsWith("If true:") }.map { Regex("[0-9]+").find(it)?.value.toString().toInt() }.toList()
            val listOutcomeFalse : List<Int> = trimmedInputs.filter { it.startsWith("If false:") }.map { Regex("[0-9]+").find(it)?.value.toString().toInt() }.toList()

            val monkeys = (0..nMonkeys).map { i ->
                val items = listItems.map { it.map { e -> BagItem(e) }.toMutableList() }[i]
                val worryAction = WorryAction.from(listOperations[i])
                val throwPolicy = ThrowPolicy(
                    listTest[i],
                    listOutcomeTrue[i],
                    listOutcomeFalse[i]
                )
                Monkey(items, worryAction, throwPolicy)
            }.toList()

            return Circus(monkeys, myOwnWorryAction)

        }

    }

}