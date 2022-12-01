fun main() {
    fun createListOfAmountPerElve(input: List<String>): MutableList<Int> {
        val elvesAmount = mutableListOf(0)
        input.forEach {
            if (it.isNotBlank()) {
                elvesAmount[elvesAmount.lastIndex] += it.toInt()
            } else {
                elvesAmount.add(0)
            }
        }
        return elvesAmount
    }

    fun part1(input: List<String>): Int {
        val elvesAmount = createListOfAmountPerElve(input)
        return elvesAmount.max()
    }

    fun part2(input: List<String>): Int {
        val elvesAmount = createListOfAmountPerElve(input)
        return elvesAmount.sortedDescending().take(3).sum()
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
