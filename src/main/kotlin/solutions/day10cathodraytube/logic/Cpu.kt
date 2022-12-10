package solutions.day10cathodraytube.logic

import solutions.day10cathodraytube.model.CpuInstruction
import solutions.day10cathodraytube.model.InstructionType
import solutions.day10cathodraytube.model.RegisterObserver


class Cpu(private val observer : RegisterObserver, private val crt: Crt) {

    private var registerX : Int = 1
    private var clock : Int = 0

    private fun tick(cycles: Int) {
        (0 until cycles).forEach { _ ->
            tickOne()
        }
    }

    private fun tickOne() {
        crt.draw(clock, registerX)
        clock += 1
        observer.addValue(clock, registerX)
    }

    private fun processInstruction(instruction: CpuInstruction) {

        when (instruction.type) {
            InstructionType.NOOP -> tick(1)
            InstructionType.ADDX -> {
                tick(2)
                registerX += instruction.argument
            }
        }

    }

    fun process(instructions: List<CpuInstruction>) {
        instructions.forEach {
            processInstruction(it)
        }
    }

    fun sumSignalStrengths(): Int {
        return observer.getSumSignals()
    }


}