package solutions.day10cathodraytube.logic

import retrieveRowsFromFile
import solutions.day10cathodraytube.model.CpuInstruction
import solutions.day10cathodraytube.model.RegisterObserver
import kotlin.test.assertEquals
import kotlin.test.Test

val outputSolution : String = """
##..##..##..##..##..##..##..##..##..##..
###...###...###...###...###...###...###.
####....####....####....####....####....
#####.....#####.....#####.....#####.....
######......######......######......####
#######.......#######.......#######.....
""".trimIndent()

class CpuTest {

    @Test
    fun testOk() {

        val rows: List<String> = retrieveRowsFromFile("inputday10sample.txt")

        val registerObserver = RegisterObserver.from(listOf(20, 60, 100, 140, 180, 220))
        val instructions = rows.map { CpuInstruction.from(it) }.toList()

        val cpu = Cpu(registerObserver, Crt())
        cpu.process(instructions)

        assertEquals(13140, cpu.sumSignalStrengths())
    }

    @Test
    fun testScreen() {

        val rows: List<String> = retrieveRowsFromFile("inputday10sample.txt")

        val registerObserver = RegisterObserver.from(listOf(20, 60, 100, 140, 180, 220))
        val instructions = rows.map { CpuInstruction.from(it) }.toList()

        val crt = Crt()
        val cpu = Cpu(registerObserver, crt)
        cpu.process(instructions)

        assertEquals(outputSolution, crt.toString())

    }

}