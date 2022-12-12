package solutions.day11monkeyinthemiddle.logic

import solutions.day11monkeyinthemiddle.model.BagItem
import solutions.day11monkeyinthemiddle.model.Monkey
import solutions.day11monkeyinthemiddle.model.ThrowPolicy
import solutions.day11monkeyinthemiddle.model.WorryAction
import java.math.BigInteger

class Circus(val monkeys : List<Monkey>, private val highStress: Boolean, private val verbose : Boolean = false) {

    private val lessStressItemSafe = WorryAction(WorryOperation.DIVIDE, 3)
    private val lessStressItemSafe2 : (Long) -> Long = { a -> a % getN() }

    private fun getN(): Long {
        return monkeys.map { it.throwPolicy.testDivisible }.reduce { acc, l -> acc * l }
    }

    private fun addItemToMonkey(pos : Int, item : BagItem) {
        return monkeys[pos].addItem(item)
    }

    private val roundToPrint = listOf(1, 20, 1000, 2000, 4000, 5000, 6000, 7000, 8000, 9000, 10000)

    private fun printIfRound(ith : Int, msg : String) {

        if (verbose && ith in roundToPrint) {
            print(msg)
        }
    }

    fun runRounds(nRounds : Int) {
        (1..nRounds).forEach { ithRound ->
            printIfRound(ithRound, "\n================ START ROUND $ithRound ================\n")
            monkeys.forEach { monkey ->
                printIfRound(ithRound, "Monkey ${monkey.params()}\n")
                printIfRound(ithRound, monkey.toString()+"\n")
                (0 until monkey.items.size).forEach { _ ->
                    val item = monkey.throwItem()
                    if (item != null) {
                        var worryLevel: Long = item.worryLevel
                        printIfRound(ithRound, "Item as is ${item.worryLevel} ")
                        worryLevel = monkey.makeWorry(worryLevel)
                        printIfRound(ithRound, "Item transformed $worryLevel ")
                        if (!highStress) {
                            worryLevel = lessStressItemSafe.applyTo(worryLevel)
                            printIfRound(ithRound, "Item div/3 less stress $worryLevel")
                        } else {
                            worryLevel = lessStressItemSafe2.invoke(worryLevel)
                        }

                        val nextMonkeyPos : Int = if (monkey.isDivisibleByMe(worryLevel)) {
                            monkey.throwPolicy.outcomeTrue
                        } else {
                            monkey.throwPolicy.outcomeFalse
                        }
                        printIfRound(ithRound, "\nSending item $worryLevel to monkey $nextMonkeyPos\n")
                        addItemToMonkey(nextMonkeyPos, BagItem(worryLevel))
                    }
                }
                printIfRound(ithRound, "\n")
            }
            printIfRound(ithRound, "After Round $ithRound circus is like this:\n$this\n\n\n")
        }
    }

    fun getMonkeyBusinessLevel() : BigInteger {
        val (a, b) = monkeys.sortedBy { it.inspectCount }.reversed().take(2).map { it.inspectCount }.toList()
        return BigInteger.valueOf(a.toLong()) * BigInteger.valueOf(b.toLong())
    }

    override fun toString(): String {
        return monkeys.joinToString("\n") { it.toString() }
    }


    companion object {

        fun from(inputs : List<String>, highStress: Boolean) : Circus {
            val trimmedInputs = inputs.map { it.trimIndent() }
            val nMonkeys : Int = trimmedInputs.filter { it.startsWith("Monkey") }.maxOf { Regex("[0-9]+").find(it)?.value.toString().toInt() }
            val listItems : List<List<Long>> = trimmedInputs.filter { it.startsWith("Starting items:") }.map { Regex("[0-9]+").findAll(it).map { e -> e.value.toLong() }.toList() }.toList()
            val listOperations : List<String> = trimmedInputs.filter { it.startsWith("Operation:") }.map { Regex("new = old .*").find(it)?.value.toString().replace("new = ", "") }.toList()
            val listTest : List<Long> = trimmedInputs.filter { it.startsWith("Test:") }.map { Regex("[0-9]+").find(it)?.value.toString().toLong() }.toList()
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

            return Circus(monkeys, highStress)

        }

    }

}