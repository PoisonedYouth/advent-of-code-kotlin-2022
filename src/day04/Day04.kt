package day04

import readInput

fun main() {

    fun splitToRange(input: String): IntRange {
        val (start, end) = input.split("-")
        return IntRange(
            start = start.toInt(),
            endInclusive = end.toInt()
        )
    }

    fun IntRange.fullyContains(other: IntRange): Boolean {
        return this.all { other.contains(it) }
    }

    fun IntRange.overlap(other: IntRange): Boolean {
        return this.any { other.contains(it) }
    }

    fun part1(input: List<String>): Int {
        return input.count { line ->
            val (elv1, elv2) = line.split(",")
            val elv1Range = splitToRange(elv1)
            val elv2Range = splitToRange(elv2)
            elv1Range.fullyContains(elv2Range) || elv2Range.fullyContains(elv1Range)
        }
    }

    fun part2(input: List<String>): Int {
        return input.count { line ->
            val (elv1, elv2) = line.split(",")
            val elv1Range = splitToRange(elv1)
            val elv2Range = splitToRange(elv2)
            elv1Range.overlap(elv2Range)
        }
    }

    val input = readInput("day04/Day04")
    println(part1(input))
    println(part2(input))
}