import org.example.main
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

class MainKtTest {

    @Test
    fun `test full program`() {
        // Given
        val finalResult = "[C - 3 - 4, M - 1 - 0, M - 2 - 1, T - 1 - 3 - 2, A - Lara - 0 - 3 - S - 3]"
        // When
        main(args = arrayOf("input.txt"))
        // Then
        assertEquals(
            File("output.txt").readLines().toString(),
            finalResult
        )
    }

}