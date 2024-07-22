package game

import org.example.constants.TREASURE_CHAR
import org.example.models.Adventurer
import org.example.models.BaseData
import org.example.models.Direction
import org.example.models.Game
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class GameTest {

    @Test
    fun `should place element correctly`() {
        // Given
        val baseData = BaseData(
            mapsize = Pair(3, 3),
            mountainsData = mutableSetOf(),
            treasures = mutableMapOf(),
            adventurer = mutableSetOf()
        )
        val game = Game(baseData)
        // When
        game.placeElement("M", 1, 1)
        game.placeElement("A(Lara)", 0, 0)
        game.placeElement("$TREASURE_CHAR(5)", 2, 2)
        // Then
        assertEquals("M", game.gameMap[1][1])
        assertEquals("T(5)", game.gameMap[2][2])
        assertEquals("A(Lara)", game.gameMap[0][0])
    }

    @Test
    fun `should not do anything if element position to set is out of bounds`() {
        // Given
        val baseData = BaseData(
            mapsize = Pair(3, 3),
            mountainsData = mutableSetOf(),
            treasures = mutableMapOf(),
            adventurer = mutableSetOf()
        )
        val game = Game(baseData)
        // When
        game.placeElement("M", 3, 3)
        game.placeElement("A(Lara)", 0, 0)
        game.placeElement("$TREASURE_CHAR(5)", 2, 2)
        // Then
        assertEquals("T(5)", game.gameMap[2][2])
        assertEquals("A(Lara)", game.gameMap[0][0])
    }

    @Test
    fun `should generate map correctly`() {
        // Given
        val mountains = mutableSetOf(Pair(0, 0), Pair(1, 1))
        val treasures = mutableMapOf(Pair(2, 2) to 3)
        val adventurers = mutableSetOf(Adventurer("John", Pair(0, 2), Direction.N, "AADADA".toCharArray()))
        val baseData = BaseData(
            mapsize = Pair(3, 3),
            mountainsData = mountains,
            treasures = treasures,
            adventurer = adventurers
        )
        val game = Game(baseData)
        // When
        game.generateMap()
        // Then
        assertEquals("M", game.gameMap[0][0])
        assertEquals("M", game.gameMap[1][1])
        assertEquals("T(3)", game.gameMap[2][2])
        assertEquals("A(John)", game.gameMap[2][0])
    }
}
