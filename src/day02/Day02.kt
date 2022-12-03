package day02

import readInput

fun main() {

    fun part1(input: List<String>): Int {
        return input.sumOf {
            when (it) {
                "A X" -> 1 + 3
                "A Y" -> 2 + 6
                "A Z" -> 3 + 0
                "B X" -> 1 + 0
                "B Y" -> 2 + 3
                "B Z" -> 3 + 6
                "C X" -> 1 + 6
                "C Y" -> 2 + 0
                "C Z" -> 3 + 3
                else -> error("Invalid input!")
            }.toInt()
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            when (it) {
                "A X" -> 3 + 0
                "A Y" -> 1 + 3
                "A Z" -> 2 + 6
                "B X" -> 1 + 0
                "B Y" -> 2 + 3
                "B Z" -> 3 + 6
                "C X" -> 2 + 0
                "C Y" -> 3 + 3
                "C Z" -> 1 + 6
                else -> error("Invalid input!")
            }.toInt()
        }
    }

    val input = readInput("day02/Day02")
    println(part1(input))
    println(part2(input))
}