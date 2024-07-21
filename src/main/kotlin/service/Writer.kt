package org.example.service

import org.example.constants.ADVENTURER_CHAR
import org.example.constants.MAP_CHAR
import org.example.constants.MOUTAIN_CHAR
import org.example.constants.TREASURE_CHAR
import org.example.models.Game
import java.io.File

data class Writer(val game: Game, val fileName: String) {
    fun writeFile() {
        File(fileName).printWriter().use { writer ->
            writer.println("$MAP_CHAR - ${game.gameMap[0].size} - ${game.gameMap.size}")
            for ((x, y) in game.mountains) {
                writer.println("$MOUTAIN_CHAR - $x - $y")
            }
            for ((coord, num) in game.treasures) {
                if (num > 0) {
                    writer.println("$TREASURE_CHAR - ${coord.first} - ${coord.second} - $num")
                }
            }
            for (adventurer in game.adventurers) {
                writer.println("$ADVENTURER_CHAR - ${adventurer.name} - ${adventurer.position.first} - ${adventurer.position.second} - ${adventurer.direction} - ${adventurer.treasures}")
            }
        }
    }
}