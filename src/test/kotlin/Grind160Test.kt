import org.junit.jupiter.api.Test
import java.util.PriorityQueue
import kotlin.test.assertEquals

class Grind160Test {
    @Test
    fun eraseOverlapIntervalsTest() {
        val intervals = arrayOf(intArrayOf(1,2), intArrayOf(2,3), intArrayOf(3,4), intArrayOf(1,3))
        assertEquals(1, eraseOverlapIntervals(intervals))

        val intervals2 = arrayOf(intArrayOf(1,2), intArrayOf(2,3), intArrayOf(3,4), intArrayOf(-100,-2), intArrayOf(5,7))
        assertEquals(0, eraseOverlapIntervals(intervals2))
    }

    @Test
    fun minWindowTest() {
        assertEquals("BANC", minWindow("ADOBECODEBANC", "ABC"))
    }

    @Test
    fun serializeTest() {
        assertEquals("", serialize(null))

        val root = TreeNode(1)
        root.left = TreeNode(2)
        root.right = TreeNode(3)
        root.right?.left = TreeNode(4)
        root.right?.right = TreeNode(5)
        assertEquals("1,2,3,_,_,4,5", serialize(root))

        val root2 = TreeNode(1)
        root2.left = TreeNode(2)
        root2.right = TreeNode(3)
        root2.right?.left = TreeNode(4)
        root2.right?.right = TreeNode(5)
        root2.right?.left?.left = TreeNode(6)
        root2.right?.left?.right = TreeNode(7)
        assertEquals("1,2,3,_,_,4,5,_,_,_,_,6,7", serialize(root2))
    }

    @Test
    fun deserializeTest() {
        assertEquals(null, deserialize(""))
        val root = deserialize("1,2,3,_,_,4,5")
        assertEquals("TN={value:1, left:TN={value:2}, right:TN={value:3, left:TN={value:4}, right:TN={value:5}}}", root.toString())

        val root2 = deserialize("1,2,3,_,_,4,5,_,_,_,_,6,7")
        assertEquals("TN={value:1, left:TN={value:2}, right:TN={value:3, left:TN={value:4, left:TN={value:6}, right:TN={value:7}}, right:TN={value:5}}}", root2.toString())
    }

    @Test
    fun trapTest() {
        assertEquals(0, trap(intArrayOf(1)))
        assertEquals(6, trap(intArrayOf(0,1,0,2,1,0,1,3,2,1,2,1)))
        assertEquals(9, trap(intArrayOf(4,2,0,3,2,5)))
        assertEquals(2, trap(intArrayOf(2,0,2)))
        assertEquals(3, trap(intArrayOf(2,1,0,2)))
    }

    @Test
    fun ladderLengthTest() {
        assertEquals(5, ladderLength("hit", "cog", listOf("hot","dot","dog","lot","log","cog")))
    }

    @Test
    fun calculateTest() {
        assertEquals(2147483647, calculate("2147483647"))
        assertEquals(23, calculate("(1+(4+5+2)-3)+(6+8)"))
    }

    @Test
    fun jobSchedulingTest() {
        assertEquals(120, jobScheduling(intArrayOf(1,2,3,3), intArrayOf(3,4,5,6), intArrayOf(50,10,40,70)))
    }

    @Test
    fun mergeKListsTest() {
        val l1 = ListNode(1)
        l1.next = ListNode(4)
        l1.next?.next = ListNode(5)
        val l2 = ListNode(1)
        l2.next = ListNode(3)
        l2.next?.next = ListNode(4)
        val l3 = ListNode(2)
        l3.next = ListNode(6)

        mergeKLists(arrayOf(l1, l2, l3))
    }

    @Test
    fun largestRectangleAreaTest() {
        assertEquals(10, largestRectangleArea(intArrayOf(2,1,5,6,2,3)))
    }

    @Test
    fun maxPathSum() {
        val root = buildTree(intArrayOf(5,4,11,7,2,8,13,4,1), intArrayOf(7,11,2,4,5,13,8,4,1))
        assertEquals(48, maxPathSum(root))

        val root2 = buildTree(intArrayOf(-2,6,0,-6), intArrayOf(0,6,-6,-2))
        assertEquals(6, maxPathSum(root2))
    }

    @Test
    fun frequencyStackTest() {
        val stack = FreqStack()
        stack.push(5)
        stack.push(7)
        stack.push(5)
        stack.push(7)
        stack.push(4)
        stack.push(5)

        assertEquals(5, stack.pop())
        assertEquals(7, stack.pop())
        assertEquals(5, stack.pop())
        assertEquals(4, stack.pop())
    }

    @Test
    fun longestIncreasingPathTest() {
        val matrix = arrayOf(
            intArrayOf(9,9,4),
            intArrayOf(6,6,8),
            intArrayOf(2,1,1)
        )

        assertEquals(4, longestIncreasingPath(matrix))
    }

    @Test
    fun longestValidParetheses() {
        assertEquals(4, longestValidParetheses(")()())"))
    }

    @Test
    fun findWordsTest() {
        val board = arrayOf(
            charArrayOf('o','a','a','n'),
            charArrayOf('e','t','a','e'),
            charArrayOf('i','h','k','r'),
            charArrayOf('i','f','l','v')
        )
        val words = arrayOf("oath", "pea", "eat", "rain")
        assertEquals(2, findWords(board, words).size)
    }
}