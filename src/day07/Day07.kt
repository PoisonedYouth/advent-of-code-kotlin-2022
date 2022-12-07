package day07

import day07.Directory.Companion.ROOT
import readInput

private class Directory(val parent: Directory?, val children: MutableList<Directory> = mutableListOf()) {
    var size = 0L

    fun getTotal(): Long = this.size + this.children.sumOf { it.getTotal() }

    companion object {
        val ROOT: Directory = Directory(
            parent = null
        )
    }
}

fun main() {

    fun createFilesystemGraph(input: List<String>): List<Directory> {
        var current = ROOT
        val directories = mutableListOf(ROOT)
        input.forEach { line ->
            when {
                line == "$ cd /" -> {
                    current = ROOT
                }

                line == "$ cd .." -> {
                    current = current.parent ?: current
                }

                line.startsWith("$ cd ") -> {
                    val directory = Directory(parent = current)
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
        return directories.toList()
    }

    fun part1(input: List<String>): Long {
        val directories = createFilesystemGraph(input)
        return directories.map { it.getTotal() }.filter { it <= 100_000 }.sum()
    }

    fun part2(input: List<String>): Long {
        val directories = createFilesystemGraph(input)

        val missingSpace = 30_000_000 - (70_000_000 - directories.first().getTotal())
        return directories.filter { it.getTotal() >= missingSpace }.minOf { it.getTotal() }
    }

    val input = readInput("day07/Day07")
    println(part1(input))
    println(part2(input))
}
