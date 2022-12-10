package day09

import readInput
import kotlin.math.sign

private data class Point(
    val x: Int,
    val y: Int,
) {
    private fun move(x: Int = 0, y: Int = 0): Point {
        return this.copy(
            x = this.x + x,
            y = this.y + y
        )
    }

    fun isNotTouching(other: Point): Boolean {
        return this !in setOf(
            other,
            other.move(x = 1),
            other.move(y = 1),
            other.move(x = -1),
            other.move(y = -1),
            other.move(x = 1, y = 1),
            other.move(x = -1, y = 1),
            other.move(x = 1, y = -1),
            other.move(x = -1, y = -1),
        )
    }

    fun moveDirection(direction: String): Point {
        return when (direction) {
            "U" -> move(y = 1)
            "L" -> move(x = -1)
            "R" -> move(x = 1)
            "D" -> move(y = -1)
            else -> error("Invalid input!")
        }
    }

    companion object {
        val START = Point(x = 0, y = 0)
    }

}

fun main() {

    fun part1(input: List<String>): Int {
        var head = Point.START
        var tail = head
        val visited = mutableSetOf(tail)
        for (line in input) {
            val (direction, steps) = line.split(" ")
            repeat(steps.toInt()) {
                head = head.moveDirection(direction)
                if (tail.isNotTouching(head)) {
                    tail = Point(tail.x + (head.x - tail.x).sign, tail.y + (head.y - tail.y).sign)
                    visited += tail
                }
            }
        }
        return visited.size
    }

    fun part2(input: List<String>): Int {
        val rope = MutableList(10) { Point.START }
        val visited = mutableSetOf(Point.START)
        for (line in input) {
            val (direction, steps) = line.split(" ")
            repeat(steps.toInt()) {
                rope[0] = rope[0].moveDirection(direction)
                rope.drop(1).indices.forEach { index ->
                    val head = rope[index]
                    var tail = rope[index + 1]
                    if (tail.isNotTouching(head)) {
                        tail = Point(tail.x + (head.x - tail.x).sign, tail.y + (head.y - tail.y).sign)
                        visited += rope.last()
                    }
                    rope[index + 1] = tail
                }
            }
        }
        return visited.size
    }

    val input = readInput("day09/Day09")
    println(part1(input))
    println(part2(input))
}
