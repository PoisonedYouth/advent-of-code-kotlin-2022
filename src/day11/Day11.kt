package day11

import readInput

data class Monkey(
    val index: Int,
    val items: MutableList<Long>,
    val worryOperation: (Long) -> Long,
    val throwOperation: (Long) -> Int,
    val divisor: Int,
    var inspections: Long = 0
) {
    companion object {
        fun from(input: List<String>): Monkey {
            val index = input[0].substringAfter("Monkey ").first().digitToInt()
            val items = input[1].substringAfter("  Starting items: ").split(", ").map { it.toLong() }.toMutableList()
            val (_, operator, operationNumber) = input[2].substringAfter("  Operation: new = ").split(" ")

            fun worryOperation(value: Long): Long {
                val operationNumberCalculated = operationNumber.toLongOrNull() ?: value
                return when (operator) {
                    "+" -> value + operationNumberCalculated
                    "*" -> value * operationNumberCalculated
                    else -> error("Invalid input!")
                }
            }

            val test = input[3].substringAfter("  Test: divisible by ").toLong()
            val success = input[4].substringAfter("    If true: throw to monkey ").toInt()
            val fail = input[5].substringAfter("    If false: throw to monkey ").toInt()

            fun throwOperation(value: Long): Int {
                return if (value % test == 0L) {
                    success
                } else {
                    fail
                }
            }
            return Monkey(
                index = index,
                items = items,
                worryOperation = ::worryOperation,
                throwOperation = ::throwOperation,
                divisor = test.toInt()
            )
        }
    }
}

fun main() {
    fun calculateInspectionProduct(monkeys: List<Monkey>, iterations: Int, reduceOperation: (Long) -> Long): Long {
        repeat(iterations) {
            monkeys.forEach { monkey ->
                repeat(monkey.items.size) {
                    monkey.inspections++
                    val item = monkey.items.removeFirst()
                    val result = reduceOperation(monkey.worryOperation(item))
                    val destination = monkey.throwOperation(result)
                    monkeys[destination].apply {
                        this.items += result
                    }
                }
            }
        }
        return monkeys.sortedByDescending { it.inspections }.let { it[0].inspections * it[1].inspections }
    }

    fun part1(input: List<String>): Long {
        val monkeys = input.chunked(7).map { Monkey.from(it) }.toMutableList()
        return calculateInspectionProduct(
            monkeys = monkeys,
            iterations = 20,
            reduceOperation = {
                it / 3
            }
        )
    }

    fun part2(input: List<String>): Long {
        val monkeys = input.chunked(7).map { Monkey.from(it) }.toMutableList()
        val reduce = monkeys.map { it.divisor }.reduce { acc, i -> acc * i }
        return calculateInspectionProduct(
            monkeys = monkeys,
            iterations = 10000,
            reduceOperation = {
                it % reduce
            }
        )
    }

    val input = readInput("day11/Day11")
    println(part1(input))
    println(part2(input))
}