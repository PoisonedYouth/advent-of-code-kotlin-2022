package day11

import readInput

data class Monkey(
    val index: Int,
    val items: MutableList<Long>,
    val operationSign: Char,
    val operationNumber: Int?,
    val destination: (Long) -> Int,
    val divisor: Int,
    var inspections: Long = 0
) {
    companion object {
        fun from(input: List<String>): Monkey {
            val index = input[0].substringAfter("Monkey ").first().digitToInt()
            val items = input[1].substringAfter("  Starting items: ").split(", ").map { it.toLong() }.toMutableList()
            val (_, operator, operationNumber) = input[2].substringAfter("  Operation: new = ").split(" ")

            val test = input[3].substringAfter("  Test: divisible by ").toLong()
            val success = input[4].substringAfter("    If true: throw to monkey ").toInt()
            val fail = input[5].substringAfter("    If false: throw to monkey ").toInt()

            fun throwTo(value: Long): Int {
                return if (value % test == 0L) {
                    success
                } else {
                    fail
                }
            }
            return Monkey(
                index = index,
                items = items,
                operationSign = operator[0],
                operationNumber = operationNumber.toIntOrNull(),
                destination = ::throwTo,
                divisor = test.toInt()
            )
        }
    }
}

fun main() {
    fun part1(input: List<String>): Long {
        val monkeys = input.chunked(7).map { Monkey.from(it) }.toMutableList()
        repeat(20) {
            monkeys.forEach { monkey ->
                repeat(monkey.items.size) {
                    monkey.inspections++
                    val item = monkey.items.removeFirst()
                    val oppNumber = monkey.operationNumber?.toLong() ?: item
                    val result = when (monkey.operationSign) {
                        '+' -> item + oppNumber
                        '*' -> item * oppNumber
                        else -> error("Invalid input!")

                    } / 3
                    val destination = monkey.destination(result)
                    monkeys[destination].apply {
                        this.items += result
                    }
                }
            }
        }
        return monkeys.sortedByDescending { it.inspections }.let { it[0].inspections * it[1].inspections }
    }

    fun part2(input: List<String>): Long {
        val monkeys = input.chunked(7).map { Monkey.from(it) }.toMutableList()
        val product = monkeys.map { it.divisor }.fold(1L) { acc, v -> acc * v }
        repeat(10000) {
            monkeys.forEach { monkey ->
                repeat(monkey.items.size) {
                    monkey.inspections++
                    val item = monkey.items.removeFirst()
                    val oppNumber = monkey.operationNumber?.toLong() ?: item
                    val result = when (monkey.operationSign) {
                        '+' -> item + oppNumber
                        '*' -> item * oppNumber
                        else -> error("Invalid input!")

                    } % product
                    val destination = monkey.destination(result)
                    monkeys[destination].apply {
                        this.items += result
                    }
                }
            }
        }
        return monkeys.sortedByDescending { it.inspections }.let { it[0].inspections * it[1].inspections }
    }

    val input = readInput("day11/Day11")
    println(part1(input))
    println(part2(input))
}