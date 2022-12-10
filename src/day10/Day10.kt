package day10

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val steps = listOf(20, 60, 100, 140, 180, 220)
        var x = 1
        var cycle = 1
        var sum = 0

        fun processInstruction() {
            if (cycle in steps) {
                sum += cycle * x
            }
            cycle++
        }

        input.forEach { line ->
            processInstruction()
            if (line.startsWith("addx")) {
                processInstruction()
                x += line.substringAfter(" ").toInt()
            }

        }
        return sum
    }

    fun part2(input: List<String>) {
        val lines = listOf(40, 80, 120, 160, 200, 240)
        var x = 1
        var cycle = 1

        fun processInstruction() {
            val sprite = (cycle - 1) % 40
            if (sprite in (x - 1..x + 1)) {
                print("#")
            } else {
                print(".")
            }
            if (cycle in lines) {
                println()
            }
            cycle++
        }

        input.forEach { line ->
            processInstruction()
            if (line.startsWith("addx")) {
                processInstruction()
                x += line.substringAfter(" ").toInt()
            }

        }
    }

    val input = readInput("day10/Day10")
    println(part1(input))
    part2(input)
}
