package day01

import readInput

fun main() {
    fun createListOfAmountPerElf(input: List<String>): List<Int> {
        val elvesAmount = mutableListOf(0)
        input.forEach {
            if (it.isNotBlank()) {
                elvesAmount[elvesAmount.lastIndex] += it.toInt()
            } else {
                elvesAmount.add(0)
            }
        }
        return elvesAmount.toList()
    }

    fun part1(input: List<String>): Int {
        val elvesAmount = createListOfAmountPerElf(input)
        return elvesAmount.max()
    }

    fun part2(input: List<String>): Int {
        val elvesAmount = createListOfAmountPerElf(input)
        return elvesAmount.sortedDescending().take(3).sum()
    }

    val input = readInput("day01/Day01")
    println(part1(input))
    println(part2(input))
}
