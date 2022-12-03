package day03

import readInput

fun main() {

    fun Char.calculatePriority(): Int {
        return if (this.isUpperCase()) (this - 'A' + 27) else (this - 'a' + 1)
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val (part1, part2) = line.chunked(line.length / 2).map { it.toSet() }
            part1.intersect(part2).first().calculatePriority()
        }
    }


    fun part2(input: List<String>): Int {
        return input.chunked(3).sumOf { group ->
            val (elv1, elv2, elv3) = group.map { it.toSet() }
            elv1.intersect(elv2).intersect(elv3).first().calculatePriority()
        }
    }

    val input = readInput("day03/Day03")
    println(part1(input))
    println(part2(input))
}