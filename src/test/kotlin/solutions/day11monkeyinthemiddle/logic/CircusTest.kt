package solutions.day11monkeyinthemiddle.logic

import org.junit.jupiter.api.Assertions.*
import solutions.day11monkeyinthemiddle.model.BagItem
import java.math.BigInteger
import kotlin.test.Test

const val sampleInputDay11 = """Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1"""
class CircusTest {

    @Test
    fun testStaticFactoryMethod() {
        val circus = Circus.from(sampleInputDay11.split('\n'), false)

        assertEquals(4, circus.monkeys.size)
        assertEquals(2, circus.monkeys[0].items.size)
        assertEquals(BagItem(75), circus.monkeys[1].items[2])
        assertEquals(9, circus.monkeys[2].worryAction.applyTo(3))
        assertEquals(17, circus.monkeys[3].throwPolicy.testDivisible)
        assertEquals(0, circus.monkeys[3].throwPolicy.outcomeTrue)
        assertEquals(1, circus.monkeys[3].throwPolicy.outcomeFalse)

    }

    @Test
    fun test20NoWorry() {
        val circus = Circus.from(
            sampleInputDay11.split('\n'),
            false
        )

        circus.runRounds(20)

        assertEquals( BigInteger.valueOf(10605), circus.getMonkeyBusinessLevel())
    }

    @Test
    fun test10000YesWorry() {
        val circus = Circus.from(
            sampleInputDay11.split('\n'),
            true
        )

        circus.runRounds(10000)

        assertEquals(2713310158, circus.getMonkeyBusinessLevel().toLong())
    }

}