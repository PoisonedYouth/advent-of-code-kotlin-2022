package day07

import readInput
import org.openjdk.jmh.annotations.*;


fun main() {
    data class Directory(val parent: Directory?, val name: String, val children: MutableList<Directory> = mutableListOf()) {
        var size = 0L

        fun getTotal(): Long = this.size + this.children.sumOf { it.getTotal() }
    }

    fun createFilesystemGraph(input: List<String>): MutableList<Directory> {
        val root = Directory(null, "/")
        var current = root
        val directories = mutableListOf(root)
        input.forEach { line ->
            when {
                line == "$ cd /" -> {
                    current = root
                }

                line == "$ cd .." -> {
                    current = current.parent ?: current
                }

                line.startsWith("$ cd ") -> {
                    val directory = Directory(parent = current, name = line.substringAfter(" "))
                    current.children.add(directory)
                    if (!directories.contains(directory)) {
                        directories.add(directory)
                    }
                    current = directory
                }

                line.first().isDigit() -> {
                    current.size += line.substringBefore(" ").toLong()
                }

                else -> {
                    // Not necessary
                }
            }
        }
        return directories
    }

    fun part1(input: List<String>): Long {
        val directories = createFilesystemGraph(input)
        return directories.map { it.getTotal() }.filter { it <= 100000 }.sum()
    }

    fun part2(input: List<String>): Long {
        val directories = createFilesystemGraph(input)

        val missingSpace = 30000000 - (70000000 - directories.first().getTotal())
        return directories.filter { it.getTotal() >= missingSpace }.minOf { it.getTotal() }
    }

    val input = readInput("day07/Day07")
    println(part1(input))
    println(part2(input))
}
