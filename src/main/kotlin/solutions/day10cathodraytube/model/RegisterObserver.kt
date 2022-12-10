package solutions.day10cathodraytube.model

class RegisterObserver(private val logs : MutableMap<Int, Int>) {

    private fun containsBp(th : Int) : Boolean {
        return logs.contains(th)
    }

    fun addValue(th: Int, value: Int) {
        if (containsBp(th)) logs[th] = value
    }

    fun getSumSignals() : Int {
        return logs.map { it.key * it.value }.sum()
    }

    companion object {
        fun from(breakpoints : List<Int>) : RegisterObserver {
            val logs : MutableMap<Int, Int> = mutableMapOf()
            breakpoints.forEach { logs[it] = -1 }

            return RegisterObserver(logs)
        }
    }

}