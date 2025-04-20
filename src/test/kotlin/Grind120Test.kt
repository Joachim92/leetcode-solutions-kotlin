import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Grind120Test {

    @Test
    fun longestConsecutiveTest() {
        val inputOne = intArrayOf(100,4,200,1,3,2)
        val inputTwo = intArrayOf(0,3,7,2,5,8,4,6,0,1)
        val inputThree = intArrayOf(1,0,1,2)

        assertEquals(4, longestConsecutive(inputOne))
        assertEquals(9, longestConsecutive(inputTwo))
        assertEquals(3, longestConsecutive(inputThree))
    }

}