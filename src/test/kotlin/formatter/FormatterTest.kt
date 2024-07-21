package formatter

import org.example.models.AdventurerPosition
import org.example.models.Direction
import org.example.service.Formatter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class FormatterTest {

    @Test
    fun `test formatData with valid input`() {
        val lines = listOf(
            "C - 3 - 4",
            "M - 1 - 0",
            "M - 2 - 1",
            "T - 0 - 3 - 2",
            "T - 1 - 3 - 3",
            "A - Lara - 1 - 1 - S - AADADAGGA"
        )
        val formatter = Formatter(lines)
        val baseData = formatter.formatData()

        assertNotNull(baseData)
        baseData?.let {
            assertEquals(Pair(3, 4), it.mapsize)
            assertEquals(setOf(Pair(1, 0), Pair(2, 1)), it.mountainsData)
            assertEquals(mapOf(Pair(0, 3) to 2, Pair(1, 3) to 3), it.treasures)
            assertEquals(1, it.adventurer.size)
            val adventurer = it.adventurer.first()
            assertEquals("Lara", adventurer.name)
            assertEquals(AdventurerPosition(1, 1), adventurer.position)
            assertEquals(Direction.S, adventurer.direction)
            assertArrayEquals(charArrayOf('A', 'A', 'D', 'A', 'D', 'A', 'G', 'G', 'A'), adventurer.steps)
            assertEquals(0, adventurer.treasures)
        }
    }

    @Test
    fun `test formatData with no map size`() {
        val lines = listOf(
            "M - 1 - 0",
            "M - 2 - 1",
            "T - 0 - 3 - 2",
            "T - 1 - 3 - 3",
            "A - Lara - 1 - 1 - S - AADADAGGA"
        )
        val formatter = Formatter(lines)
        val baseData = formatter.formatData()

        assertNull(baseData)
    }

    @Test
    fun `test formatData with malformed input`() {
        val lines = listOf(
            "C - 3 - 4",
            "M - 1 - X", // Invalid mountain coordinates
            "T - 0 - 3 - 2",
            "A - Lara - 1 - 1 - S - AADADAGGA"
        )
        val formatter = Formatter(lines)
        val baseData = formatter.formatData()

        assertEquals(emptySet<Pair<Int, Int>>(), baseData?.mountainsData)
    }
}
