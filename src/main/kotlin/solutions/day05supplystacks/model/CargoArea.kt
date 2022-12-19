package solutions.day05supplystacks.model

import kotlin.RuntimeException

class CargoArea(numberOfSlots : Int) {

    private val stacks: Array<ArrayDeque<Crate>>

    init {
        stacks = Array(numberOfSlots) { ArrayDeque() }
        (0 until numberOfSlots).forEach { addStackToSlot(it) }
    }

    private fun addStackToSlot(slot: Int) {
        stacks[slot] = ArrayDeque()
    }

    fun addCrateToStack(crate: Crate, slot: Int) {
        stacks[slot].addLast(crate)
    }

    private fun removeCrateFromStack(slot: Int) : Crate {
        if (stacks[slot].isEmpty()) throw RuntimeException("Can't remove crate from stack $slot, already empty")
        else return stacks[slot].removeLast()
    }

    private fun moveCrateFromStackToStack(sourceSlot: Int, targetSlot: Int) {
        val crate = removeCrateFromStack(sourceSlot)
        addCrateToStack(crate, targetSlot)
    }

    private fun moveNCrates(numberOfCrates: Int, sourceSlot: Int, targetSlot: Int) {
        IntRange(0, numberOfCrates-1).forEach { _ -> moveCrateFromStackToStack(sourceSlot, targetSlot) }
    }

    private fun addCratesToStack(crates: List<Crate>, slot: Int) {
        crates.forEach { stacks[slot].addLast(it) }
    }

    private fun removeCratesFromStack(slot: Int, numberOfCrates: Int) : List<Crate> {
        if (stacks[slot].size < numberOfCrates) throw RuntimeException("Can't remove $numberOfCrates crates from stack $slot, not enough")
        return (0 until numberOfCrates).map { _ -> stacks[slot].removeLast() }.reversed().toList()
    }

    private fun moveNCratesTogether(numberOfCrates: Int, sourceSlot: Int, targetSlot: Int) {
        val crates = removeCratesFromStack(sourceSlot, numberOfCrates)
        addCratesToStack(crates, targetSlot)
    }

    private fun parseMoveCommand(command: String) : Triple<Int, Int, Int> {
        val (numberOfCrates, sourceSlot, targetSlot) = command.split(" ").filter { it.all { c -> c.isDigit() } }.map { it.toInt() }.take(3)
        return Triple(numberOfCrates, sourceSlot-1, targetSlot-1)
    }

    fun executeCommands(rowsInput: List<String>, craneModel: CraneModel) {
        rowsInput.forEach { row ->
            val (numberOfCrates, sourceSlot, targetSlot) = parseMoveCommand(row)
            when (craneModel) {
                CraneModel.CRATE_MOVER_9000 -> moveNCrates(numberOfCrates, sourceSlot, targetSlot)
                CraneModel.CRATE_MOVER_9001 -> moveNCratesTogether(numberOfCrates, sourceSlot, targetSlot)
            }
        }
    }

    fun getTopCrates() : String {
        return stacks.map { it.last() }.joinToString("")
    }

    private fun printStack(stack: ArrayDeque<Crate>) : String {
        return stack.joinToString(" ") { " [${it}] " }
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stacks.forEachIndexed { idx, stack -> stringBuilder.append("${idx + 1} ${printStack(stack)}\n") }
        return stringBuilder.toString()
    }

    companion object {
        fun fromInput(rowsInput: List<String>) : CargoArea {
            val indexesContent: List<Int> =
                rowsInput.last().mapIndexed { idx, char -> if (char.isDigit()) idx else null }.filterNotNull().toList()
            val cargoArea = CargoArea(indexesContent.size)

            rowsInput.reversed().subList(1, rowsInput.size).map {row ->
                indexesContent.forEachIndexed { pos, outIdx ->
                    val char: Char? = row.filterIndexed { idx, _ -> idx == outIdx }.firstOrNull()
                    if (char?.isLetter() == true) cargoArea.addCrateToStack(Crate.from(char), pos)
                }
            }

            return cargoArea
        }

    }

}