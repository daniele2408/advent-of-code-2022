package solutions.day11monkeyinthemiddle.model

import java.math.BigInteger

data class BagItem(var worryLevel : BigInteger) {
    constructor(worryLevel: Int): this(BigInteger.valueOf(worryLevel.toLong()))
}
