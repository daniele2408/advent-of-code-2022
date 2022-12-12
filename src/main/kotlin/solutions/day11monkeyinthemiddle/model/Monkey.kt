package solutions.day11monkeyinthemiddle.model

data class Monkey(val items : MutableList<BagItem> = mutableListOf(), val worryAction: WorryAction, val throwPolicy: ThrowPolicy) {

    var inspectCount : Int = 0

    fun throwItem() : BagItem? {
        if (items.isEmpty()) return null
        inspectCount++
        return items.removeFirst()
    }

    fun makeWorry(currentWorryLevel: Long) : Long {
        val stressLevelConstrained = worryAction.applyTo(currentWorryLevel, worryAction.argument)
         return stressLevelConstrained
    }

    fun isDivisibleByMe(currentWorryLevel: Long) =
        currentWorryLevel % throwPolicy.testDivisible == 0L

    fun addItem(item: BagItem) {
        items.add(item)
    }

    override fun toString(): String {
        return "Monkey(seen: $inspectCount x${items.size}[ ${ items.map { it.worryLevel.toInt() }.joinToString(" ") } ])"
    }

    fun params() : String {
        return "apply ${worryAction} then ${throwPolicy}"
    }


}