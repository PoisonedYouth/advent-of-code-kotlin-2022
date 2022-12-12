package day12

import readInput
import java.util.*

private class Point(
    val x: Int,
    val y: Int,
    val code: Char,
    val height: Int,
) : Comparable<Point> {
    var dist: Int = -1

    override fun compareTo(other: Point): Int {
        return this.dist.compareTo(other.dist)
    }

}

fun main() {

    fun setupGrid(input: List<String>): List<List<Point>> {
        val grid = input.mapIndexed { yIndex, s ->
            s.mapIndexed { xIndex, c ->
                Point(
                    x = xIndex,
                    y = yIndex,
                    code = c,
                    height = when (c) {
                        'S' -> 0
                        'E' -> 26
                        else -> c - 'a'
                    }
                )
            }
        }
        return grid
    }

    fun processGrid(grid: List<List<Point>>, target: Char): Int {
        fun getPointOrNull(x: Int, y: Int): Point? {
            return when {
                x < 0 || x > grid[0].lastIndex || y < 0 || y > grid.lastIndex -> null
                else -> grid[y][x]
            }
        }

        fun Point.neighbors(): List<Point> {
            val neighbourList = mutableListOf<Point?>()
            neighbourList.add(getPointOrNull(x, y + 1))
            neighbourList.add(getPointOrNull(x, y - 1))
            neighbourList.add(getPointOrNull(x + 1, y))
            neighbourList.add(getPointOrNull(x - 1, y))
            return neighbourList.filterNotNull()
        }

        val destinationPoint = grid.flatten().single { it.code == 'E' }
        val processedPoints = mutableSetOf<Point>()
        val priorityQueue = PriorityQueue<Point>().apply { add(destinationPoint) }
        while (priorityQueue.isNotEmpty()) {
            val point = priorityQueue.poll()
            point.neighbors().filter { it.height + 1 >= point.height }.forEach { neighbour ->
                if (neighbour.code == target) {
                    return point.dist
                }
                if (neighbour !in processedPoints) {
                    neighbour.dist = point.dist + 1
                    processedPoints.add(neighbour)
                    priorityQueue.add(neighbour)
                }
            }
        }
        error("target '$target' not found in grid!")
    }

    fun part1(input: List<String>): Int {
        val grid = setupGrid(input)

        return processGrid(grid, 'S')
    }

    fun part2(input: List<String>): Int {
        val grid = setupGrid(input)

        return processGrid(grid, 'a')
    }

    val input = readInput("day12/Day12")
    println(part1(input))
    println(part2(input))
}