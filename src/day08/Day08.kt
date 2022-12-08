package day08

import readInput

fun main() {
    data class Point(
        val x: Int,
        val y: Int,
        val height: Int,
        var matching: Boolean = false,
        var scenicScore: Int = 0
    )

    fun createGrid(input: List<String>): List<List<Point>> {
        val grid = mutableListOf<MutableList<Point>>()
        for ((index, line) in input.withIndex()) {
            grid.add(index, mutableListOf())
            grid[index].addAll(line.mapIndexed { i, c -> Point(x = i, y = index, height = c.digitToInt()) })
        }
        return grid
    }

    fun part1(input: List<String>): Int {
        val grid = createGrid(input)

        grid.forEach { line ->
            line.forEachIndexed { lineIndex, point ->
                when {
                    // The tree is at the edge x coordinate
                    point.x == 0 || point.x == line.lastIndex -> point.matching = true
                    // The tree is at the edge y coordinate
                    point.y == 0 || point.y == line.lastIndex -> point.matching = true
                    // All trees on the left are less tall
                    (0 until point.x).all { i -> line[i].height < point.height } -> point.matching = true
                    // All trees on the right are less tall
                    (point.x + 1..line.lastIndex).all { i -> line[i].height < point.height } -> point.matching = true
                    // All trees above are less tall
                    (0 until point.y).all { i -> grid[i][lineIndex].height < point.height } -> point.matching = true
                    // All trees below are less tall
                    (point.y + 1..grid.lastIndex).all { i -> grid[i][lineIndex].height < point.height } -> point.matching = true
                }
            }
        }
        return grid.sumOf { list -> list.count { it.matching } }
    }

    fun part2(input: List<String>): Int {
        val grid = createGrid(input)

        grid.forEach { line ->
            line.forEachIndexed { lineIndex, point ->
                // Count the trees to the left that are less tall
                var first = (point.x - 1 downTo 0).takeWhile { i -> line[i].height < point.height }.count()
                if (point.x > first) first++
                // Count the trees to the right that are less tall
                var second = (point.x + 1..line.lastIndex).takeWhile { i -> line[i].height < point.height }.count()
                if (line.lastIndex - point.x > second) second++
                // Count the trees above that are less tall
                var third = (point.y - 1 downTo 0).takeWhile { i -> grid[i][lineIndex].height < point.height }.count()
                if (point.y > third) third++
                // Count the trees below that are less tall
                var fourth = (point.y + 1..grid.lastIndex).takeWhile { i -> grid[i][lineIndex].height < point.height }.count()
                if (grid.lastIndex - point.y > fourth) fourth++

                point.scenicScore = first * second * third * fourth
            }
        }
        return grid.maxOf { list -> list.maxOf { it.scenicScore } }
    }

    val input = readInput("day08/Day08")
    println(part1(input))
    println(part2(input))
}
