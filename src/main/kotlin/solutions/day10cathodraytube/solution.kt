package solutions.day10cathodraytube

import retrieveRowsFromFile
import solutions.day10cathodraytube.logic.Crt
import solutions.day10cathodraytube.logic.Cpu
import solutions.day10cathodraytube.model.CpuInstruction
import solutions.day10cathodraytube.model.RegisterObserver

fun sumSignals(cyclesPos : List<Int>) : Int {

    val rows: List<String> = retrieveRowsFromFile("inputday10.txt")

    val registerObserver = RegisterObserver.from(cyclesPos)
    val instructions = rows.map { CpuInstruction.from(it) }.toList()

    val cpu = Cpu(registerObserver, Crt())
    cpu.process(instructions)

    return cpu.sumSignalStrengths()
}

fun printScreen(cyclesPos : List<Int>) : String {

    val rows: List<String> = retrieveRowsFromFile("inputday10.txt")

    val registerObserver = RegisterObserver.from(cyclesPos)
    val instructions = rows.map { CpuInstruction.from(it) }.toList()

    val crt = Crt()
    val cpu = Cpu(registerObserver, crt)
    cpu.process(instructions)

    return crt.toString()
}