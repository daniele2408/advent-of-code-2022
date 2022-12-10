package solutions.day10cathodraytube.model

enum class InstructionType(val symbol: String) {
    NOOP("noop"),
    ADDX("addx");

    companion object {
        fun fromSymbol(s: String) : InstructionType {
            return InstructionType.values().first { it.symbol == s }
        }
    }

}