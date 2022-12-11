package solutions.day11monkeyinthemiddle.model

import java.math.BigInteger

data class Monkey(val items : MutableList<BagItem> = mutableListOf(), val worryAction: WorryAction, val throwPolicy: ThrowPolicy) {

    var inspectCount : BigInteger = BigInteger.valueOf(0L)

    fun throwItem() : BagItem? {
        if (items.isEmpty()) return null
        inspectCount++
        return items.removeFirst()
    }

    fun makeWorry(currentWorryLevel: BigInteger) : BigInteger {
        return worryAction.applyTo(currentWorryLevel)
    }

    fun addressThrowTo(currentWorryLevel : BigInteger) : Int {
        if (currentWorryLevel % throwPolicy.testDivisible == BigInteger.valueOf(0L)) return throwPolicy.outcomeTrue
        return throwPolicy.outcomeFalse
    }

    fun addItem(item: BagItem) {
        items.add(item)
    }

    override fun toString(): String {
        return "Monkey(seen: $inspectCount [ ${ items.map { it.worryLevel }.joinToString(" ") } ])"
    }


}