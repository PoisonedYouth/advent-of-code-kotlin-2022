package day05

import readInput
import java.util.*

val craneRegex = Regex("""\[(.)\]""")
val instructionRegex = Regex("""move (\d+) from (\d+) to (\d+)""")

data class Instruction(
    val amount: Int,
    val origin: Int,
    val target: Int
)

fun main() {
    fun initStack(input: List<String>): List<Stack<String?>> {
        val stacks = listOf(
            Stack<String?>(),
            Stack<String?>(),
            Stack<String?>(),
            Stack<String?>(),
            Stack<String?>(),
            Stack<String?>(),
            Stack<String?>(),
            Stack<String?>(),
            Stack<String?>(),
        )
        input.take(8).reversed().map { line ->
            val cranes = craneRegex.findAll(line)
            cranes.forEach {
                stacks[it.range.first / 4].push(it.value.substring(1, 2))
            }
        }
        return stacks
    }

    fun initInstructionList(input: List<String>): List<Instruction> {
        val instructions = input.drop(10).map { line ->
            val instructionInput = instructionRegex.find(line) ?: error("Invalid Input!")
            if (instructionInput.groups.size != 4) error("Invalid Input!")
            val (amount, origin, target) = instructionInput.groups.drop(1).toList()
            Instruction(
                amount = amount!!.value.toInt(),
                origin = origin!!.value.toInt(),
                target = target!!.value.toInt()
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
