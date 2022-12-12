package day12

import readInput

private class Point(
    val x: Int,
    val y: Int,
    val code: Char,
    val height: Int,
) {
    var dist: Int = -1
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
        destinationPoint.dist = 0
        val processedPoints = mutableSetOf(destinationPoint)
        while (processedPoints.isNotEmpty()) {
            val point = processedPoints.first().also { processedPoints.remove(it) }
            if (point.code == target) {
                return point.dist - 2
            }
            point.neighbors().filter { it.height + 1 >= point.height }.forEach { neighbour ->
                if (neighbour.dist == -1) {
                    neighbour.dist = point.dist + 1
                    processedPoints.add(neighbour)
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