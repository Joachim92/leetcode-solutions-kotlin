import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Grind100Test {

    @Test
    fun lengthOfLISTest() {
        val increasingValues = intArrayOf(1,2,3,4,5)
        assertEquals(5, lengthOfLIS(increasingValues))

        val sameValues = intArrayOf(1,1,1,1)
        assertEquals(1, lengthOfLIS(sameValues))

        val extendedValues = intArrayOf(8,1,2,7,6,3,4,5,6,1,1,0,3)
        assertEquals(6, lengthOfLIS(extendedValues))
    }
}