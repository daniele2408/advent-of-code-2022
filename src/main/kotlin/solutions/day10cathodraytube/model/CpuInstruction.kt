package solutions.day10cathodraytube.model

data class CpuInstruction(val type: InstructionType, val argument: Int) {

    companion object {
        fun from(row: String) : CpuInstruction {
            val splitRow = row.split(" ")

            val symbolInstruction = splitRow[0]
            if (splitRow.size == 1) return CpuInstruction(InstructionType.fromSymbol(symbolInstruction), 0)
            return CpuInstruction(InstructionType.fromSymbol(symbolInstruction), splitRow.last().toInt())
        }
    }

}
