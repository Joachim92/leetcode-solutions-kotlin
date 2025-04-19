import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class Grind20Test {
    @Test
    fun twoSumTest() {
        assertContentEquals(intArrayOf(0,1), twoSum(intArrayOf(2,7,11,15), 9))
    }

    @Test
    fun isValidTest() {
        assertTrue(isValid("()[]{}"))
        assertTrue(isValid("{[]}"))
        assertFalse(isValid(")("))
        assertFalse(isValid("()[]{}]["))
        assertFalse(isValid("(("))
        assertFalse(isValid("))"))
    }

    @Test
    fun mergeTwoListsTest() {
        val list1 = ListNode(1)
        list1.next = ListNode(2)
        list1.next?.next = ListNode(4)
        val list2 = ListNode(1)
        list2.next = ListNode(3)
        list2.next?.next = ListNode(4)
        val expectedMergedList = intArrayOf(1,1,2,3,4,4)
        val mergedList = mergeTwoLists(list1, list2)

        assertContentEquals(expectedMergedList, mergedList?.toIntArray())
    }

    @Test
    fun implementQueueUsingStacks() {
        val queue = MyQueue()
        queue.push(1)
        queue.push(2)
        assertEquals(1, queue.peek())
        queue.push(3)
        assertEquals(1, queue.pop())
        queue.push(4)
        queue.push(5)

        val expected = listOf(2,3,4,5)

        for (number in expected) {
            assertEquals(number, queue.pop())
        }

        assertTrue(queue.empty())
    }

    @Test
    fun minimumEffortPathTest() {
        val heights = arrayOf(
            intArrayOf(1,2,2),
            intArrayOf(3,8,2),
            intArrayOf(5,3,5)
        )

        assertEquals(2, minimumEffortPath(heights))

        val heights2 = arrayOf(
            intArrayOf(1,2,1,1,1),
            intArrayOf(1,2,1,2,1),
            intArrayOf(1,2,1,2,1),
            intArrayOf(1,2,1,2,1),
            intArrayOf(1,1,1,2,1)
        )
        assertEquals(0, minimumEffortPath(heights2))
    }

    @Test
    fun lowestCommonAncestorTest() {
        val root = TreeNode(3)
        root.left = TreeNode(5)
        root.right = TreeNode(1)
        root.left?.left = TreeNode(6)
        root.left?.right = TreeNode(2)
        root.left?.right?.left = TreeNode(7)
        root.left?.right?.right = TreeNode(4)
        root.right?.left = TreeNode(0)
        root.right?.right = TreeNode(8)
        assertEquals(root.left, lowestCommonAncestor(root, root.left, root.left?.right?.right))
    }

    @Test
    fun accountMergeTest() {
        val expected = arrayOf(arrayOf("mario", "e1@com", "e2@com", "e3@com"), arrayOf("pedro", "e4@com"), arrayOf("mario", "e5@com"))
        val result = accountsMerge(listOf(listOf("mario", "e1@com", "e2@com"), listOf("mario", "e1@com", "e3@com"), listOf("pedro", "e4@com"), listOf("mario", "e5@com"))).map { it.toTypedArray() }.toTypedArray()

        for (i in expected.indices) {
            for (j in expected[i].indices) {
                assertEquals(expected[i][j], result[i][j])
            }
        }
    }

    @Test
    fun wordBreakTest() {
        assertTrue(wordBreak("wanabana", listOf("wanaban", "wanaba", "na")))
        assertFalse(wordBreak("catsandog", listOf("cats", "dog", "sand", "and", "cat")))
        assertFalse(wordBreak("ccbb", listOf("bc", "cb")))
    }

    @Test
    fun canPartitionTest() {
        assertTrue(canPartitionDP(intArrayOf(1,2,7,8)))
        assertFalse(canPartitionDP(intArrayOf(1,2,3,5)))
        assertTrue(canPartitionDP(intArrayOf(1,5,11,5)))
        assertFalse(canPartitionDP(intArrayOf(100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,99,97)))
    }

    @Test
    fun myAtoiTest() {
        assertEquals(0, myAtoi(""))
        assertEquals(-42, myAtoi("  -42"))
        assertEquals(-2147483648, myAtoi("-91283472332"))
    }

    @Test
    fun spiralOrderTest() {
        val matrix = arrayOf(
            intArrayOf(1,2,3,4,5,6),
            intArrayOf(20,21,22,23,24,7),
            intArrayOf(19,32,33,34,25,8),
            intArrayOf(18,31,36,35,26,9),
            intArrayOf(17,30,29,28,27,10),
            intArrayOf(16,15,14,13,12,11)
        )
        spiralOrder(matrix)
    }

    @Test
    fun longestPalindromeTest() {
        assertEquals("bab".length, longestPalindrome("babad").length)
        assertEquals("aaaaaaba".length, longestPalindrome("baaaaaabaa").length)
        assertEquals("aca".length, longestPalindrome("aacabdkacaa").length)
    }

    @Test
    fun uniquePathsTest() {
        assertEquals(3, uniquePaths(3,2))
        assertEquals(28, uniquePaths(3,7))
    }

    @Test
    fun buildTreeTest() {
        val tree = buildTree(intArrayOf(3,9,20,15,7), intArrayOf(9,3,15,20,7))
        print(tree)
    }

    @Test
    fun findAnagramsTest() {
        findAnagrams("cbaebabacd", "abc")
        findAnagrams("af", "be")
    }

    @Test
    fun findMinHeightTreesTest() {
        assertEquals(listOf(0), findMinHeightTrees(3, arrayOf(intArrayOf(0,1), intArrayOf(0,2))))
        assertEquals(listOf(1), findMinHeightTrees(4, arrayOf(intArrayOf(1,0), intArrayOf(1,2), intArrayOf(1,3))))
        assertEquals(listOf(3,4), findMinHeightTrees(6, arrayOf(intArrayOf(3,0), intArrayOf(3,1), intArrayOf(3,2), intArrayOf(3,4), intArrayOf(5,4))))
        assertEquals(listOf(0), findMinHeightTrees(1, emptyArray()))
        assertEquals(listOf(0,1), findMinHeightTrees(2, arrayOf(intArrayOf(0,1))))
    }

    @Test
    fun lruCacheTest() {
        val cache = LRUCache(2)
        cache.put(1,1)
        cache.put(2,2)
        cache.get(1) // 1
        cache.put(3,3)
        cache.get(2) // -1
    }

    @Test
    fun kSmallestTest() {
        val root = TreeNode(3)
        root.left = TreeNode(1)
        root.right = TreeNode(4)
        root.left?.right = TreeNode(2)
        assertEquals(1, kthSmallest(root, 1))

        val root2 = TreeNode(5)
        root2.left = TreeNode(3)
        root2.right = TreeNode(6)
        root2.left?.left = TreeNode(2)
        root2.left?.right = TreeNode(4)
        root2.left?.left?.left = TreeNode(1)
        assertEquals(3, kthSmallest(root2, 3))
    }

    @Test
    fun searchInRotatedSortedArrayTest() {
        val nums = intArrayOf(0,1,2,3,4,5,6,7,8,9)
        assertEquals(4, search(nums, 4))

        val nums2 = intArrayOf(5,6,7,8,9,0,1,2,3,4)
        assertEquals(4, search(nums2, 9))

        val nums3 = intArrayOf(4,0,1,2,3)
        assertEquals(4, search(nums3, 3))

        val nums4 = intArrayOf()
        assertEquals(-1, search(nums4, 9))

        val nums5 = intArrayOf(5)
        assertEquals(0, search(nums5, 5))

        val nums6 = intArrayOf(1)
        assertEquals(-1, search(nums6, 0))
    }

    @Test
    fun isBalancedTest() {
        val root = TreeNode(1)
        root.left = TreeNode(2)
        root.left?.left = TreeNode(3)
        root.left?.left?.left = TreeNode(4)
        root.right = TreeNode(2)
        root.right?.right = TreeNode(3)
        root.right?.right?.right = TreeNode(4)

        assertFalse(isBalanced(root.left))
    }

    @Test
    fun climbStairsTest() {
        val s = mutableListOf(intArrayOf(1,2))
        s.removeAt(0)

        assertEquals(1, climbStairs(1))
        assertEquals(2, climbStairs(2))
        assertEquals(3, climbStairs(3))
        assertEquals(5, climbStairs(4))
        assertEquals(8, climbStairs(5))
        assertEquals(13, climbStairs(6))
        assertEquals(21, climbStairs(7))
        assertEquals(34, climbStairs(8))
    }

    @Test
    fun addBinary() {
        addBinary("11", "1")
        addBinary("1010", "1011")
        val sums = mutableListOf<MutableList<Int>>()
        val nums = intArrayOf(1,4,3)
        nums.sort()
        intArrayOf(4)
    }

    data class Person(val age: Int, val name: String) // sort by age then name

    @Test
    fun searchInRotatedArrayTest() {
        assertEquals(5, searchInRotatedArray(intArrayOf(5,6,1,2,3,4), 4))
        assertEquals(1, searchInRotatedArray(intArrayOf(3,1), 1))
        assertEquals(1, searchInRotatedArray(intArrayOf(5,6,1,2,3,4), 6))
    }

    @Test
    fun pacificAtlanticTest() {
        val heights = arrayOf(
            intArrayOf(1,2,2,3,5),
            intArrayOf(3,2,3,4,4),
            intArrayOf(2,4,5,3,1),
            intArrayOf(6,7,1,4,5),
            intArrayOf(5,1,1,2,4)
        )
//        val heights = arrayOf(
//            intArrayOf(10,10,10),
//            intArrayOf(10,1,10),
//            intArrayOf(10,10,10)
//        )
//        val heights = arrayOf(
//            intArrayOf(11,3,2,4,14,6,13,18,1,4,12,2,4,1,16),
//            intArrayOf(5,11,18,0,15,14,6,17,2,17,19,15,12,3,14),
//            intArrayOf(10,2,5,13,11,11,13,19,11,17,14,18,14,3,11),
//            intArrayOf(14,2,10,7,5,11,6,11,15,11,6,11,12,3,11),
//            intArrayOf(13,1,16,15,8,2,16,10,9,9,10,14,7,15,13),
//            intArrayOf(17,12,4,17,16,5,0,4,10,15,15,15,14,5,18),
//            intArrayOf(9,13,18,4,14,6,7,8,5,5,6,16,13,7,2),
//            intArrayOf(19,9,16,19,16,6,1,11,7,2,12,10,9,18,19),
//            intArrayOf(19,5,19,10,7,18,6,10,7,12,14,8,4,11,16),
//            intArrayOf(13,3,18,9,16,12,1,0,1,14,2,6,1,16,6),
//            intArrayOf(14,1,12,16,7,15,9,19,14,4,16,6,11,15,7),
//            intArrayOf(6,15,19,13,3,2,13,7,19,11,13,16,0,16,16),
//            intArrayOf(1,5,9,7,12,9,2,18,6,12,1,8,1,10,19),
//            intArrayOf(10,11,10,11,3,5,12,0,0,8,15,7,5,13,19),
//            intArrayOf(8,1,17,18,3,6,8,15,0,9,8,8,12,5,18),
//            intArrayOf(8,3,6,12,18,15,10,10,12,19,16,7,17,17,1),
//            intArrayOf(12,13,6,4,12,18,18,9,4,9,13,11,5,3,14),
//            intArrayOf(8,4,12,11,2,2,10,3,11,17,14,2,17,4,7),
//            intArrayOf(8,0,14,0,13,17,11,0,16,13,15,17,4,8,3),
//            intArrayOf(18,15,8,11,18,3,10,18,3,3,15,9,11,15,15)
//        )
        pacificAtlantic(heights)
    }

    @Test
    fun removeNthFromEndTest() {
        val head = ListNode(1)
        head.next = ListNode(2)
        head.next?.next = ListNode(3)
        head.next?.next?.next = ListNode(4)
        head.next?.next?.next?.next = ListNode(5)

        var result: ListNode? = removeNthFromEnd(head, 2)

        var expected: ListNode? = ListNode(1)
        expected?.next = ListNode(2)
        expected?.next?.next = ListNode(3)
        expected?.next?.next?.next = ListNode(5)

        while (expected != null) {
            assertEquals(expected.`val`, result?.`val`)
            expected = expected.next
            result = result?.next
        }
    }
}