package day03

import readInput

fun main() {

    fun calculatePriority(char: Char): Int {
        return if (char.isUpperCase()) (char - 'A' + 27) else (char - 'a' + 1)
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val (part1, part2) = line.chunked(line.length / 2).map { it.toCharArray().toSet() }
            part1.intersect(part2).sumOf { calculatePriority(it) }
        }

    }

    fun part2(input: List<String>): Int {
        return input.chunked(3).sumOf { group ->
            val (elv1, elv2, elv3) = group.map { it.toCharArray().toSet() }
            elv1.intersect(elv2).intersect(elv3).sumOf { calculatePriority(it) }
        }
    }

    val input = readInput("day03/Day03")
    println(part1(input))
    println(part2(input))
}