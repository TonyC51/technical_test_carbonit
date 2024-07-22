package org.example

import org.example.models.BaseData
import org.example.models.Game
import org.example.service.Formatter
import org.example.service.Writer
import java.io.File




fun main(args: Array<String>) {

    //  generate proper data and handle any bad format
    val inputFileName = args[0]
    val outputFileName = "output.txt"
    val formatted: BaseData? = Formatter((File(inputFileName).bufferedReader().readLines())).formatData()

    formatted?.let {
        val game = Game(formatted)
        val writer = Writer(game = game, fileName = outputFileName)

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
        /* execute round by round, round priority is respected naturally
         because adventurers are added line by line from start to end of file */
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
