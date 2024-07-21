package org.example

import org.example.models.BaseData
import org.example.models.Game
import org.example.service.Formatter
import org.example.service.Writer
import java.io.File




fun main(args: Array<String>) {

    //  generate proper data and handle any bad format
    val formatted: BaseData? = Formatter((File(args[0]).bufferedReader().readLines())).formatData()

    formatted?.let {
        val game = Game(formatted)
        val writer = Writer(game = game, fileName = "toto.txt")

        game.generateMap()

        println("*** Map initialized *** ")
        printMap(game)

        run(game)

        println("*** Map after game *** ")
        printMap(game)

        writer.writeFile()

    } ?: print("bad input format")
}

private fun printMap(game: Game) {
    game.gameMap.forEach { println(it.joinToString("")) }
}

fun run(game: Game) {
    for (adv in game.adventurers) {
        if (adv.steps.isNotEmpty()) {
            adv.executeMove(game, adv.steps[0])
            // remove done step
            adv.steps.sliceArray(1 until adv.steps.size)
        }

        println()

        for (step in adv.steps) {
            adv.executeMove(game, step)
        }

    }
}
