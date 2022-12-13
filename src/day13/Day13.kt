package day13

import readInput

sealed interface Element : Comparable<Element>

class ElementList(val elements: List<Element>) : Element {
    constructor(element: Element) : this(listOf(element))

    override fun compareTo(other: Element): Int = when (other) {
        is ValueElement -> compareTo(ElementList(other))
        is ElementList -> {
            elements.zip(other.elements).map {
                it.first.compareTo(it.second)
            }.firstOrNull { it != 0 } ?: this.elements.size.compareTo(other.elements.size)
        }
    }
}

class ValueElement(val value: Int) : Element {
    override fun compareTo(other: Element): Int = when (other) {
        is ValueElement -> value.compareTo(other.value)
        is ElementList -> ElementList(this).compareTo(other)
    }
}

private val regex = """(,)|(?=\[)|(?=])|(?<=\[)|(?<=])""".toRegex()

private fun mapToElement(line: String): Element {
    val result = buildList<MutableList<Element>> {
        add(mutableListOf())
        line.split(regex).filter(String::isNotBlank).forEach { t ->
            when (t) {
                "[" -> add(mutableListOf())
                "]" -> removeLast().also { last().add(ElementList(it)) }
                else -> last().add(ValueElement(t.toInt()))
            }
        }
    }
    return result[0][0]
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.asSequence().filter(String::isNotBlank).map(::mapToElement)
            .chunked(2)
            .mapIndexed { index, (a, b) -> if (a < b) index + 1 else 0 }
            .sum()
    }

    fun part2(input: List<String>): Int {
        val parsed = input.filter(String::isNotBlank).map(::mapToElement)
        val packet1 = ElementList(ElementList(ValueElement(2)))
        val packet2 = ElementList(ElementList(ValueElement(6)))
        val list = (parsed + packet1 + packet2).sorted()
        return (1 + list.indexOf(packet1)) * (1 + list.indexOf(packet2))
    }

    val input = readInput("day13/Day13")
    println(part1(input))
    println(part2(input))
}




