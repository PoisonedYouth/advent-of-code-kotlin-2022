package day06

import readInput


fun main() {
    fun findMarkerSequenceIndex(input: List<String>, size: Int): List<Int> {
        return input.map { line ->
            line.windowed(size).indexOfFirst { group -> group.groupBy { it }.size == size } + size
        }
    }

    fun part1(input: List<String>): Int {
        return findMarkerSequenceIndex(input, 4).first()
    }

    fun part2(input: List<String>): Int {
        return findMarkerSequenceIndex(input, 14).first()
    }

    val input = readInput("day06/Day06")
    println(part1(input))
    println(part2(input))

}
