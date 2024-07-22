package org.example.models

import org.example.constants.ADVENTURER_CHAR
import org.example.constants.MOUTAIN_CHAR
import org.example.constants.TREASURE_CHAR


data class Game(val baseData: BaseData) {

        // init
        val gameMap: Array<Array<String>> = Array(baseData.mapsize.second) { Array(baseData.mapsize.first) { "." } }
        val treasures: MutableMap<Pair<Int, Int>, Int> = baseData.treasures
        val mountains: MutableSet<Pair<Int, Int>> = baseData.mountainsData
        val adventurers: MutableSet<Adventurer> = baseData.adventurer

        fun generateMap() {
            baseData.mountainsData.forEach { placeElement(element = MOUTAIN_CHAR, axeX = it.first, axeY = it.second) }
            baseData.treasures.forEach {
                placeElement(
                    element = "$TREASURE_CHAR(${it.value})",
                    axeX = it.key.first,
                    axeY = it.key.second
                )
            }
            baseData.adventurer.forEach {
                placeElement(
                    element = "$ADVENTURER_CHAR(${it.name})",
                    axeX = it.position.first,
                    axeY = it.position.second
                )
            }
        }

        fun placeElement(element: String, axeX: Int, axeY: Int) {
            if (axeX >= 0 && axeX < gameMap[0].size && axeY >= 0 && axeY < gameMap.size )
                gameMap[axeY][axeX] = element
        }

    }

    data class BaseData(
        val mapsize: Pair<Int, Int>,
        val mountainsData: MutableSet<Pair<Int, Int>>,
        val treasures: MutableMap<Pair<Int, Int>, Int>,
        val adventurer: MutableSet<Adventurer>
    )

    enum class Direction {
        N,
        E,
        S,
        W
    }

    data class Adventurer(
        val name: String,
        var position: Pair<Int, Int>,
        var direction: Direction,
        val steps: CharArray,
        var treasures: Int = 0
    ) {

        fun left() {
            direction = when (direction) {
                Direction.N -> Direction.W
                Direction.E -> Direction.N
                Direction.S -> Direction.E
                Direction.W -> Direction.S
            }
        }

        fun right() {
            direction = when (direction) {
                Direction.N -> Direction.E
                Direction.E -> Direction.S
                Direction.S -> Direction.W
                Direction.W -> Direction.N
            }
        }

        fun move(game: Game) {

            val (dx, dy) = when (direction) {
                Direction.N -> 0 to -1
                Direction.E -> 1 to 0
                Direction.S -> 0 to 1
                Direction.W -> -1 to 0
                else -> 0 to 0
            }

            val oldPos = Pair(position.first, position.second)

            val newX = position.first + dx
            val newY = position.second + dy

            val newPos = Pair(newX, newY)

            // check if position is not out of bounds, on a mountain or if an adventurer is not already on the slot
            if (newPos.second in 0 until game.gameMap.size
                && newPos.first in 0 until game.gameMap[0].size
                && newPos !in game.mountains
                && newPos !in game.adventurers.map { it.position }
            ) {
                position = Pair(first = newX, second = newY)

                // check for treasures
                if (newPos in game.treasures && game.treasures[newPos]!! > 0) {
                    treasures++
                    game.treasures[newPos] = game.treasures[newPos]!! - 1
                }

                game.gameMap[newY][newX] = "A(${name})"

                // update map display and print back treasure display with minus one if there's still treasures to find
                if (oldPos in game.treasures && game.treasures[oldPos]!! > 0)
                    game.gameMap[oldPos.second][oldPos.first] = "$TREASURE_CHAR(${game.treasures[oldPos]!!})"
                else game.gameMap[oldPos.second][oldPos.first] = "."
            }
        }

        fun executeMove(game: Game, step: Char) {
            when (step) {
                'A' -> move(game)
                'G' -> left()
                'D' -> right()
            }
        }
    }