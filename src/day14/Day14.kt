package day14


import readInput
import kotlin.math.max
import kotlin.math.min

private data class Point(val x: Int, val y: Int)

private val sandSource = Point(500, 0)
fun main() {

    fun Point.to(other: Point) = if (x == other.x) {
        (min(y, other.y)..max(y, other.y)).map { Point(x, it) }
    } else {
        (min(x, other.x)..max(x, other.x)).map { Point(it, y) }
    }

    fun Point.next(points: MutableMap<Point, Char>): Point = listOf(0, -1, 1).firstNotNullOfOrNull { dx ->
        Point(x + dx, y + 1).let { if (it !in points) it else null }
    } ?: this

    fun List<Point>.toCaveMap() = associate { it to '#' }.toMutableMap()

    fun createPoints(input: List<String>): List<Point> {
        val points = input.flatMap { line ->
            line
                .split(" -> ")
                .map { element ->
                    element
                        .split(",")
                        .map { it.toInt() }
                        .let { Point(it[0], it[1]) }
                }
                .windowed(2)
                .map { it[0].to(it[1]) }
                .flatten()
        }
        return points
    }

    fun dropSand(path: MutableList<Point>, map: MutableMap<Point, Char>, newMax: Int) {
        var proceed = true
        while (proceed) {
            var curr = path.removeLast()
            while (true) {
                val next = curr.next(map)
                when {
                    next.y >= newMax -> {
                        proceed = false
                        break
                    }

                    next == curr -> {
                        map[curr] = 'o'
                        proceed = path.isNotEmpty()
                        break
                    }

                    else -> {
                        path.add(curr)
                        curr = next
                    }
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val points = createPoints(input).toCaveMap()

        val yMax = points.keys.maxOf { it.y }
        val path = mutableListOf(sandSource)

        dropSand(path, points, yMax)
        return points.values.count { it == 'o' }
    }

    fun part2(input: List<String>): Int {
        val points = createPoints(input)

        val yMax = points.maxOf { it.y }
        val floor = Point(sandSource.x - yMax - 3, yMax + 2).to(Point(sandSource.x + yMax + 3, yMax + 2))
        val map = (points + floor).toCaveMap()

        val newMax = map.keys.maxOf { it.y }

        val path = mutableListOf(sandSource)

        dropSand(path, map, newMax)
        return map.values.count { it == 'o' }
    }

    val lines = readInput("day14/Day14")
    println(part1(lines))
    println(part2(lines))
}