package day12

import readInput

private data class Point(
    val x: Int,
    val y: Int,
    val code: Char,
    val height: Int,
)

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
        fun getOnGridOrNull(x: Int, y: Int): Point? {
            return when {
                x < 0 || x > grid[0].lastIndex || y < 0 || y > grid.lastIndex -> null
                else -> grid[y][x]
            }
        }

        fun Point.neighbors(): List<Point> {
            val neighbourList = mutableListOf<Point?>()
            neighbourList.add(getOnGridOrNull(x, y + 1))
            neighbourList.add(getOnGridOrNull(x, y - 1))
            neighbourList.add(getOnGridOrNull(x + 1, y))
            neighbourList.add(getOnGridOrNull(x - 1, y))
            return neighbourList.filterNotNull()
        }

        val destinationPoint = grid.flatten().single { it.code == 'E' }
        val processedPoints = mutableListOf(destinationPoint to 0)
        val visited = mutableSetOf(destinationPoint)
        while (processedPoints.isNotEmpty()) {
            val (point, dist) = processedPoints.removeFirst()
            println("Point:$point, dist:$dist")
            if(point.code == target ) {
                return dist
            }
            point.neighbors().filter { it.height + 1 >= point.height  }.forEach {
                if (visited.add(it)) processedPoints.add(it to dist + 1)
            }
        }
        return -1
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