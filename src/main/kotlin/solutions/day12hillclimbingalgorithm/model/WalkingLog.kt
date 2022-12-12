package solutions.day12hillclimbingalgorithm.model

class WalkingLog {
    private val logs : MutableList<WalkingLogEntry> = mutableListOf()
    private val alreadySeen : MutableSet<Coord> = mutableSetOf()
    fun addLog(logEntry: WalkingLogEntry) {
        logs.add(logEntry)
        setLastEntryAsSeen()
    }

    fun getTotalSteps() : Int {
        return logs.size - 1
    }

    fun removeLog() : WalkingLogEntry {
        return logs.removeLast()
    }

    fun getLastEntry() : WalkingLogEntry {
        return logs[logs.lastIndex]
    }

    fun setLastEntryAsCross() {
        logs[logs.lastIndex].isCross = true
    }

    fun setLastEntryAsSeen() {
        alreadySeen.add(logs[logs.lastIndex].coord)
    }

    fun isAlreadySeen(coord: Coord) : Boolean {
        return coord in alreadySeen
    }

    fun isEmpty(): Boolean {
        return logs.isEmpty()
    }

    fun rollBackToLastCross() {
        val lastCrossIndex = logs.indexOf(logs.findLast { it.isCross })
        logs.subList(lastCrossIndex, logs.lastIndex).forEach { alreadySeen.add(it.coord) }
        ((logs.size - 1) downTo lastCrossIndex).forEach { logs.removeLast() }

    }

    fun isCoordInLog(coord: Coord) : Boolean {
        return logs.find { it.coord.isAt(coord) } != null
    }

    fun printCoord(coord: Coord) : String {
        val pastStep: WalkingLogEntry = logs.find { it.coord.isAt(coord) }!!
        return logs[logs.indexOf(pastStep) + 1].direction.symbol

    }

    override fun toString(): String {
        return logs.map { "${it.direction.symbol} ${it.coord}" }.joinToString("\n")
    }


}