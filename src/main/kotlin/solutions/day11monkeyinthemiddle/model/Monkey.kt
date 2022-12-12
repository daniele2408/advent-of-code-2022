package solutions.day11monkeyinthemiddle.model

data class Monkey(val items : MutableList<BagItem> = mutableListOf(), val worryAction: WorryAction, val throwPolicy: ThrowPolicy) {

    var inspectCount : Int = 0

    fun throwItem() : BagItem? {
        if (items.isEmpty()) return null
        inspectCount++
        return items.removeFirst()
    }

    fun makeWorry(currentWorryLevel: Int, filterStress: Boolean) : Int {
        val (a, b) = if (filterStress) adaptStress(currentWorryLevel, throwPolicy.testDivisible) else currentWorryLevel to worryAction.argument
        val stressLevelConstrained = worryAction.applyTo(a, b)
//        val stressLevelFull = worryAction.applyTo(currentWorryLevel, worryAction.argument)
        if (worryAction.applyTo(a, b) % throwPolicy.testDivisible != (worryAction.applyTo(currentWorryLevel, worryAction.argument) % throwPolicy.testDivisible)) {
            println("$a e $b non sono modulo-invarianti-${throwPolicy.testDivisible} per $currentWorryLevel - ${worryAction.argument} con operazione ${worryAction.worryOperation}")
        }
         return stressLevelConstrained
    }

    private fun adaptStress(input : Int, testDivisible : Int) : Pair<Int, Int> {
        return worryAction.worryOperation.invariantModuleTransform(input, worryAction.argument, testDivisible)
    }

    fun isDivisibleByMe(currentWorryLevel: Int) =
        currentWorryLevel % throwPolicy.testDivisible == 0

    fun addItem(item: BagItem) {
        items.add(item)
    }

    override fun toString(): String {
        return "Monkey(seen: $inspectCount x${items.size}[ ${ items.map { it.worryLevel }.joinToString(" ") } ])"
    }

    fun params() : String {
        return "apply ${worryAction} then ${throwPolicy}"
    }


}