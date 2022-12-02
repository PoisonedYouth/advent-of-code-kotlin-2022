package day02

import readInput

fun main() {


    fun part1(input: List<String>): Int {
        val inputPairs = input.map { it.split(" ") }.map { Options.valueOfInput(it[0]) to Options.valueOfInput(it[1]) }

        fun calculateResultRock(options: Options): Int {
            return when (options) {
                Options.ROCK -> 1 + 3
                Options.PAPER -> 2 + 6
                Options.SCISSORS -> 3 + 0
            }
        }

        fun calculateResultPaper(options: Options): Int {
            return when (options) {
                Options.ROCK -> 1 + 0
                Options.PAPER -> 2 + 3
                Options.SCISSORS -> 3 + 6
            }
        }

        fun calculateResultScissors(options: Options): Int {
            return when (options) {
                Options.ROCK -> 1 + 6
                Options.PAPER -> 2 + 0
                Options.SCISSORS -> 3 + 3
            }
        }

        var result = 0
        for (inputPair in inputPairs) {
            result += when (inputPair.first) {
                Options.ROCK -> calculateResultRock(inputPair.second)
                Options.PAPER -> calculateResultPaper(inputPair.second)
                Options.SCISSORS -> calculateResultScissors(inputPair.second)
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        val inputPairs = input.map { it.split(" ") }.map { Options.valueOfInput(it[0]) to GameResult.valueOfInput(it[1]) }
        var result = 0

        fun calculateResultRock(gameResult: GameResult): Int {
            return when (gameResult) {
                GameResult.LOOSE -> 3 + 0
                GameResult.DRAW -> 1 + 3
                GameResult.WIN -> 2 + 6
            }
        }

        fun calculateResultPaper(gameResult: GameResult): Int {
            return when (gameResult) {
                GameResult.LOOSE -> 1 + 0
                GameResult.DRAW -> 2 + 3
                GameResult.WIN -> 3 + 6
            }
        }

        fun calculateResultScissors(gameResult: GameResult): Int {
            return when (gameResult) {
                GameResult.LOOSE -> 2 + 0
                GameResult.DRAW -> 3 + 3
                GameResult.WIN -> 1 + 6
            }
        }

        for (inputPair in inputPairs) {
            result += when (inputPair.first) {
                Options.ROCK -> calculateResultRock(inputPair.second)
                Options.PAPER -> calculateResultPaper(inputPair.second)
                Options.SCISSORS -> calculateResultScissors(inputPair.second)
            }
        }
        return result
    }

    val input = readInput("day02/Day02")
    println(part1(input))
    println(part2(input))
}

enum class Options {
    ROCK, PAPER, SCISSORS;

    companion object {
        fun valueOfInput(input: String): Options {
            return when (input) {
                "X",
                "A" -> ROCK

                "Y",
                "B" -> PAPER

                "Z",
                "C" -> SCISSORS

                else -> error("Invalid input")
            }
        }
    }
}

enum class GameResult {
    LOOSE, DRAW, WIN;

    companion object {
        fun valueOfInput(input: String): GameResult {
            return when (input) {
                "X" -> LOOSE
                "Y" -> DRAW
                "Z" -> WIN
                else -> error("Invalid input!")
            }
        }
    }
}
