import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class Grind169Test {

    @Test
    fun numBusesToDestinationTest() {
        assertEquals(2, numBusesToDestination(arrayOf(intArrayOf(1,2,7), intArrayOf(3,6,7)), 1, 6))
    }

    @Test
    fun maxSlidingWindowTest() {
        val maximums = maxSlidingWindow(intArrayOf(1,3,-1,3,5,3,6,7), 3)
        val expected = intArrayOf(3,3,5,5,6,7)
        for (i in maximums.indices) {
            assertEquals(expected[i], maximums[i])
        }
    }

    @Test
    fun palindromePairsTest() {
        val words = arrayOf("abcd","dcba","lls","s","sssll")
        val expected = arrayOf(intArrayOf(0,1), intArrayOf(1,0), intArrayOf(2,4), intArrayOf(3,2))
        val result = palindromePairs(words)
        assertEquals(4, result.size)
        for ((i, arr) in result.sortedBy { it.first() }.withIndex()) {
            assertEquals(expected[i][0], arr[0])
            assertEquals(expected[i][1], arr[1])
        }
    }

    @Test
    fun reverseKGroupTest() {
        val head = ListNode(1)
        var cur: ListNode? = head
        for (i in 2 .. 5) {
            cur?.next = ListNode(i)
             cur = cur?.next
        }
        val newHead = reverseKGroup(head, 2)
        assertContentEquals(intArrayOf(2,1,4,3,5), newHead?.toIntArray())
    }

    @Test
    fun solveSudokuTest() {
        val board = arrayOf(
            charArrayOf('5','3','.','.','7','.','.','.','.'),
            charArrayOf('6','.','.','1','9','5','.','.','.'),
            charArrayOf('.','9','8','.','.','.','.','6','.'),
            charArrayOf('8','.','.','.','6','.','.','.','3'),
            charArrayOf('4','.','.','8','.','3','.','.','1'),
            charArrayOf('7','.','.','.','2','.','.','.','6'),
            charArrayOf('.','6','.','.','.','.','2','8','.'),
            charArrayOf('.','.','.','4','1','9','.','.','5'),
            charArrayOf('.','.','.','.','8','.','.','7','9')
        )
        solveSudoku(board)
        println()
    }

    @Test
    fun firstMissingPositiveTest() {
        assertEquals(3, firstMissingPositive(intArrayOf(1,2,0)))
        assertEquals(2, firstMissingPositive(intArrayOf(3,4,-1,1)))
        assertEquals(1, firstMissingPositive(intArrayOf(7,8,9,11,12)))
    }

    @Test
    fun solveNQueensTest() {
        assertEquals(listOf(listOf("Q")), solveNQueens(1))
        assertEquals(listOf(listOf(".Q..","...Q","Q...","..Q."), listOf("..Q.","Q...","...Q",".Q..")), solveNQueens(4))
    }

    @Test
    fun smallestRangeTest() {
        assertContentEquals(intArrayOf(20,24), smallestRange(listOf(listOf(4,10,15,24,26), listOf(0,9,12,20), listOf(5,18,22,30))))
//        assertContentEquals(intArrayOf(1,1), smallestRange(listOf(listOf(1,2,3), listOf(1,2,3), listOf(1,2,3))))
    }
}