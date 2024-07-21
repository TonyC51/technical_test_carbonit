package org.example.service

import org.example.models.*

data class Formatter(val lines: List<String>) {

    private companion object {
        const val MOUTAIN_CHAR = "M"
        const val MAP_CHAR = "C"
    }


    fun formatData (): BaseData? {
        val treasures: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()
        val mountains: MutableSet<Pair<Int, Int>> = mutableSetOf()
        val adventurers: MutableSet<Adventurer> = mutableSetOf()
        var mapSize: Pair<Int, Int>? = null


        /*
            use of regex to avoid any format error from the input file
            can be replaced by split() method for better performance if we trust the file
         */

        val treasurRegex = Regex("([CMTA]) - ([0-9]) - ([0-9]) - ([0-9])")
        val otherRegex = Regex("([CMTA]) - ([0-9]) - ([0-9])")
        val adventurersRegex = Regex("([A]) - ([A-Za-z]+) - ([0-9]) - ([0-9]) - ([NESW]) - ([AGD]+)")

        lines.map { line ->

            adventurersRegex.find(line)?.let {
                val values = it.groupValues
                adventurers.add(
                    Adventurer(
                        name = values[2],
                        position = Pair(first = values[3].toInt(), second = values[4].toInt()),
                        direction = Direction.valueOf(values[5]),
                        steps = values[6].toCharArray(),
                        treasures = 0
                    ),
                ) } ?: treasurRegex.find(line)?.let {
                val values = it.groupValues
                treasures.put(
                    Pair(
                        first = values[2].toInt(),
                        second = values[3].toInt(),
                    ),
                    values[4].toInt()
                )

            } ?: otherRegex.find(line).let {
                val values = it?.groupValues
                when (values?.get(1)) {
                    MAP_CHAR -> mapSize = Pair(values[2].toInt(), values[3].toInt())
                    MOUTAIN_CHAR -> mountains.add(
                        Pair(
                            first = values[2].toInt(), // x
                            second = values[3].toInt() // y
                        ))
                    else -> {}
                }
            }
        }

        mapSize?.let {
            return BaseData(
                mapsize = it,
                mountainsData = mountains,
                treasures = treasures,
                adventurer = adventurers

            ) } ?: return null
    }
}


