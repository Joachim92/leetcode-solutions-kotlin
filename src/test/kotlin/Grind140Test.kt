import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Grind140Test {

    @Test
    fun rotateTest() {
        val matrix = arrayOf(
            intArrayOf(1,2,3),
            intArrayOf(4,5,6),
            intArrayOf(7,8,9)
        )

        val expected = arrayOf(
            intArrayOf(7,4,1),
            intArrayOf(8,5,2),
            intArrayOf(9,6,3)
        )

        rotate(matrix)

        for (row in matrix.indices) {
            for (col in matrix.indices) {
                assertEquals(matrix[row][col], expected[row][col])
            }
        }
    }

    @Test
    fun pathSumTest() {
        val root = TreeNode(10)
        root.left = TreeNode(5)
        root.right = TreeNode(-3)
        root.left?.left = TreeNode(3)
        root.left?.right = TreeNode(2)
        root.right?.right = TreeNode(11)
        root.left?.left?.left = TreeNode(3)
        root.left?.left?.right = TreeNode(-2)
        root.left?.right?.right = TreeNode(1)
        assertEquals(3, pathSum(root, 8))

        val root2 = TreeNode(1000000000)
        root2.left = TreeNode(1000000000)
        root2.left?.left = TreeNode(294967296)
        root2.left?.left?.left = TreeNode(1000000000)
        root2.left?.left?.left?.left = TreeNode(1000000000)
        root2.left?.left?.left?.left?.left = TreeNode(1000000000)

        assertEquals(0, pathSum(root2, 0))
    }

    @Test
    fun myPowTest() {
        assertEquals(0.0, myPow(2.0, -2147483648))
    }

    @Test
    fun largestNumberTest() {
        assertEquals("43243432", largestNumber(intArrayOf(432,43243)))
    }

    @Test
    fun numDecodingsTest() {
        assertEquals(3, numDecodings("226"))
        assertEquals(0, numDecodings("603"))
        assertEquals(1, numDecodings("102"))
    }

    @Test
    fun reorderListTest() {
        val head = ListNode(1)
        head.next = ListNode(2)
        head.next?.next = ListNode(3)
        head.next?.next?.next = ListNode(4)
        head.next?.next?.next?.next = ListNode(5)
        reorderList(head)
        println("done")
    }

    @Test
    fun findCheapestPriceTest() {
        val flights = arrayOf(intArrayOf(0,1,100), intArrayOf(1,2,100), intArrayOf(2,0,100), intArrayOf(1,3,600), intArrayOf(2,3,200))
        assertEquals(700, findCheapestPrice(4, flights, 0, 3, 1))

        val flights2 = arrayOf(intArrayOf(0,1,1), intArrayOf(0,2,5), intArrayOf(1,2,1), intArrayOf(2,3,1))
        assertEquals(6, findCheapestPrice(4, flights2, 0, 3, 1))

        val flights3 = arrayOf(intArrayOf(0,1,20),intArrayOf(1,2,20),intArrayOf(2,3,30),intArrayOf(3,4,30),intArrayOf(4,5,30),intArrayOf(5,6,30),intArrayOf(6,7,30),intArrayOf(7,8,30),intArrayOf(8,9,30),intArrayOf(0,2,9999),intArrayOf(2,4,9998),intArrayOf(4,7,9997))
        assertEquals(30054, findCheapestPrice(10, flights3, 0, 9, 4))
    }

    @Test
    fun distanceKTest() {
        val root = TreeNode(3)
        root.left = TreeNode(5)
        root.right = TreeNode(1)
        root.left?.left = TreeNode(6)
        root.left?.right = TreeNode(2)
        root.left?.right?.left = TreeNode(7)
        root.left?.right?.right = TreeNode(4)
        root.right?.left = TreeNode(0)
        root.right?.right = TreeNode(8)

        assertEquals(mutableListOf(1,4,7), distanceK(root, root.left, 2).sorted())
    }

    @Test
    fun findMinTest() {
        val nums = intArrayOf(4,5,6,7,0,1,2)
        assertEquals(0, findMin(nums))

        val nums2 = intArrayOf(3,4,5,1,2)
        assertEquals(1, findMin(nums2))
    }

    @Test
    fun calculate2() {
        assertEquals(1, calculate2(" 3/2 "))
        assertEquals(19, calculate2("4+3*5"))
    }
}