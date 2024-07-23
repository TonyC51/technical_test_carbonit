import org.example.models.Adventurer
import org.example.models.BaseData
import org.example.models.Direction
import org.example.models.Game
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AdventurerTest {

    private lateinit var baseData: BaseData
    private lateinit var game: Game
    private lateinit var adventurer: Adventurer

    @BeforeEach
    fun setUp() {
        baseData = BaseData(
            mapsize = Pair(3, 3),
            mountainsData = mutableSetOf(Pair(1, 1)),
            treasures = mutableMapOf(Pair(2, 2) to 1),
            adventurer = mutableSetOf()
        )
        game = Game(baseData)
        adventurer = Adventurer(name = "John", position = Pair(0, 0), direction = Direction.N, steps = charArrayOf('A', 'G', 'D'))
        baseData.adventurer.add(adventurer)
        game.generateMap()
    }

    @Test
    fun testLeft() {
        adventurer.left()
        assertEquals(Direction.W, adventurer.direction)
        adventurer.left()
        assertEquals(Direction.S, adventurer.direction)
        adventurer.left()
        assertEquals(Direction.E, adventurer.direction)
        adventurer.left()
        assertEquals(Direction.N, adventurer.direction)
    }

    @Test
    fun testRight() {
        adventurer.right()
        assertEquals(Direction.E, adventurer.direction)
        adventurer.right()
        assertEquals(Direction.S, adventurer.direction)
        adventurer.right()
        assertEquals(Direction.W, adventurer.direction)
        adventurer.right()
        assertEquals(Direction.N, adventurer.direction)
    }

    @Test
    fun testMove() {
        // Test moving north when at the top of the map
        adventurer.move(game)
        assertEquals(Pair(0, 0), adventurer.position)

        // Change direction to east and move
        adventurer.right()
        adventurer.move(game)
        assertEquals(Pair(1, 0), adventurer.position)

        // Change direction to south and move
        adventurer.right()
        adventurer.move(game)
        assertNotEquals(Pair(1, 1), adventurer.position) // Should not move into a mountain
        assertEquals(Pair(1, 0), adventurer.position) // Position should remain the same

        // Move east to (2,0)
        adventurer.left() // Face east
        adventurer.move(game)
        assertEquals(Pair(2, 0), adventurer.position)

        // Move south to (2,1)
        adventurer.right() // Face south
        adventurer.move(game)
        assertEquals(Pair(2, 1), adventurer.position)

        // Move south to (2,2) and collect treasure
        adventurer.move(game)
        assertEquals(Pair(2, 2), adventurer.position)
        assertEquals(1, adventurer.treasures)
        assertEquals(0, game.treasures[Pair(2, 2)])
    }

    @Test
    fun testExecuteMove() {
        // Execute move forward
        adventurer.executeMove(game, 'A')
        assertEquals(Pair(0, 0), adventurer.position) // Should not move since it faces north and is at the top

        // Execute turn left
        adventurer.executeMove(game, 'G')
        assertEquals(Direction.W, adventurer.direction)

        // Execute turn right
        adventurer.executeMove(game, 'D')
        assertEquals(Direction.N, adventurer.direction)
    }
}
