package solutions.day11monkeyinthemiddle.model

data class ThrowPolicy(val testDivisible : Int, val outcomeTrue : Int, val outcomeFalse : Int) {
    override fun toString(): String {
        return "if % $testDivisible == 0 send to $outcomeTrue else to $outcomeFalse"
    }
}
