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

    @Test
    fun rotateTest() {
        val numsOne = intArrayOf(1,2,3,4,5,6,7)
        val expectedOne = intArrayOf(5,6,7,1,2,3,4)
        rotate(numsOne, 3)

        for (i in numsOne.indices) {
            assertEquals(expectedOne[i], numsOne[i])
        }

        val numsTwo = intArrayOf(-1,-100,3,99)
        val expectedTwo = intArrayOf(3,99,-1,-100)
        rotate(numsTwo, 2)

        for (i in numsTwo.indices) {
            assertEquals(expectedTwo[i], numsTwo[i])
        }

        val numsThree = intArrayOf(1,2,3,4,5,6,7)
        val expectedThree = intArrayOf(3,4,5,6,7,1,2)
        rotate(numsThree, 5)

        for (i in numsThree.indices) {
            assertEquals(expectedThree[i], numsThree[i])
        }

        val numsFour = intArrayOf(1,2)
        val expectedFour = intArrayOf(2,1)
        rotate(numsFour, 3)

        for (i in numsFour.indices) {
            assertEquals(expectedFour[i], numsFour[i])
        }

        val numsFive = intArrayOf(1,2,3,4,5,6)
        val expectedFive = intArrayOf(6,1,2,3,4,5)
        rotate(numsFive, 1)

        for (i in numsFive.indices) {
            assertEquals(expectedFive[i], numsFive[i])
        }

        val numsSix = intArrayOf(1,2,3,4,5,6)
        val expectedSix = intArrayOf(5,6,1,2,3,4)
        rotate(numsSix, 2)

        for (i in numsSix.indices) {
            assertEquals(expectedSix[i], numsSix[i])
        }
    }

    @Test
    fun findMaxLengthTest() {
        val numsOne = intArrayOf(0,1)
        assertEquals(2, findMaxLength(numsOne))
        val numsTwo = intArrayOf(0,1,0)
        assertEquals(2, findMaxLength(numsTwo))
        val numsThree = intArrayOf(0,1,1,1,1,1,0,0,0)
        assertEquals(6, findMaxLength(numsThree))
    }

    @Test
    fun widthOfBinaryTreeTest() {
        val root: TreeNode? = TreeNode(1)
        root?.left = TreeNode(3)
        root?.right = TreeNode(2)
        root?.left?.left = TreeNode(5)
        root?.left?.right = TreeNode(3)
        root?.right?.right = TreeNode(9)

        assertEquals(4, widthOfBinaryTree(root))
    }

    @Test
    fun findClosestElementsTest() {
        val arr = intArrayOf(1,2,3,4,5)
        val expected = listOf(1,2,3,4)
        val result = findClosestElements(arr, 4, 3)
        for (i in expected.indices) {
            assertEquals(expected[i], result[i])
        }

        val arr2 = intArrayOf(-2,-1,1,2,3,4,5)
        val expected2 = listOf(-2,-1,1,2,3,4,5)
        val result2 = findClosestElements(arr2, 7, 3)
        for (i in expected2.indices) {
            assertEquals(expected2[i], result2[i])
        }

        val arr3 = intArrayOf(1,1,2,2,2,2,2,3,3)
        val expected3 = listOf(2,3,3)
        val result3 = findClosestElements(arr3, 3, 3)
        for (i in expected3.indices) {
            assertEquals(expected3[i], result3[i])
        }
    }

    @Test
    fun characterReplacementTest() {
        assertEquals(5, characterReplacement("aababba", 2))
    }

    @Test
    fun sortListTest() {
        val head = ListNode(4)
        head.next = ListNode(2)
        head.next?.next = ListNode(1)
        head.next?.next?.next = ListNode(3)
        val expected = intArrayOf(1,2,3,4)
        var cur = sortList(head)

        for (i in expected) {
            assertEquals(i, cur?.`val`)
            cur = cur?.next
        }
    }

    @Test
    fun subarraySumTest() {
        assertEquals(2, subarraySum(intArrayOf(0,1,2,3,4), 1))
    }

}