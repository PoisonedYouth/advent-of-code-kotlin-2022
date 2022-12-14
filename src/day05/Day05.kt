package day05

import readInput
import java.util.*

val cratesRegex = Regex("""\[(.)\]""")
val instructionRegex = Regex("""move (\d+) from (\d+) to (\d+)""")

data class Instruction(
    val amount: Int,
    val origin: Int,
    val target: Int
)

fun main() {
    fun initStack(input: List<String>): List<Stack<String>> {
        val stacks = List(9) { Stack<String>() }
        input.takeWhile { it.startsWith("[") }.reversed().map { line ->
            val crates = cratesRegex.findAll(line)
            crates.forEach {
                stacks[it.range.first / 4].push(it.groupValues[1])
            }
        }
        return stacks
    }

    fun initInstructionList(input: List<String>): List<Instruction> {
        val instructions = input.dropWhile { !it.startsWith("move") }.map { line ->
            val instructionInput = instructionRegex.find(line) ?: error("Invalid Input!")
            val (amount, origin, target) = instructionInput.groupValues.drop(1).toList()
            Instruction(
                amount = amount.toInt(),
                origin = origin.toInt(),
                target = target.toInt()
            )
        }
        return instructions
    }

    fun part1(input: List<String>): String {
        val stacks = initStack(input)
        val instructions = initInstructionList(input)
        instructions.forEach { instruction ->
            repeat(instruction.amount) {
                val value = stacks[instruction.origin - 1].pop()
                stacks[instruction.target - 1].push(value)
            }
        }
        return stacks.joinToString("") { it.peek() ?: "" }
    }

    fun part2(input: List<String>): String {
        val stacks = initStack(input)
        val instructions = initInstructionList(input)
        instructions.forEach { instruction ->
            val changes = mutableListOf<String?>()
            repeat(instruction.amount) {
                changes.add(stacks[instruction.origin - 1].pop())
            }
            changes.reversed().forEach { stacks[instruction.target - 1].push(it) }
        }
        return stacks.joinToString("") { it.peek() ?: "" }
    }

    val input = readInput("day05/Day05")
    println(part1(input))
    println(part2(input))
}
