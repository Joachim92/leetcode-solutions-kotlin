import java.lang.Exception
import kotlin.math.abs

/**
 * #101  Path Sum II
 */

/**
 * #102  Longest Consecutive Sequence
 */
fun longestConsecutive(nums: IntArray): Int { // O(n)
    fun isBeginningOfASequence(num: Int, numSet: Set<Int>): Boolean {
        return (num - 1) !in numSet
    }

    if (nums.isEmpty()) return 0

    val numSet = nums.toSet()
    var longestStreak = 0

    for (num in numSet) {
        if (isBeginningOfASequence(num, numSet)) {
            var currentNum = num
            var currentStreak = 1

            while ((currentNum + 1) in numSet) {
                currentNum++
                currentStreak++
            }

            longestStreak = maxOf(longestStreak, currentStreak)
        }
    }

    return longestStreak
}

/**
 * #103  Rotate Array
 */
fun rotate(nums: IntArray, k: Int) {
    fun swap(i1: Int, i2: Int) {
        val tmp = nums[i1]
        nums[i1] = nums[i2]
        nums[i2] = tmp
    }

    fun reverse(i: Int, j: Int) {
        var start = i
        var end = j
        while (start <= end) {
            swap(start, end)
            start++
            end--
        }
    }

    val normalizedK = k % nums.size
    reverse(0, nums.lastIndex)
    reverse(0, normalizedK-1)
    reverse(normalizedK, nums.lastIndex)
}

/**
 * #104  Odd Even Linked List
 */
fun isEvenOrOdd(li: List<Int>): String {
    var ans = "even"
    val iterator = li.stream().iterator()

    while (iterator.hasNext()) {
        iterator.next()
        ans = if (ans == "odd") {
            "even"
        } else {
            "odd"
        }
    }
    return ans
}

/**
 * #105  Decode String
 */

/**
 * #106  Contiguous Array
 */
fun findMaxLength(nums: IntArray): Int {
    val seen = mutableMapOf(0 to -1)
    var maxLength = 0
    var sum = 0

    for ((i, num) in nums.withIndex()) {
        sum += if (num == 0) -1 else 1

        if (sum in seen) {
            maxLength = maxOf(maxLength, i-seen.getValue(sum))
        } else {
            seen[sum] = i
        }
    }

    return maxLength
}

/**
 * #107  Maximum Width of Binary Tree
 *
 * 1
 * 2
 * 4
 * 8
 *
 *                1
 *        1               2
 *    1       2       3       4
 * 1    2   3   4   5   6   7   8
 *
 *
 *                1
 *        2               3
 *    4      5       6       7
 *  8   9 10  11  12  13  14  15
 *
 * I already know the max length of the level
 * Identify the left most
 * Identify the right most
 * (right - left + 1) assuming one index
 *
 *
 *
 */
fun widthOfBinaryTree(root: TreeNode?): Int {
    val levelQueue = mutableListOf(root)
    var level = 1
    var maxWidth = 0
    val nodeMap = mutableMapOf(root to 1)

    while (levelQueue.isNotEmpty()) {
        val levelSize = levelQueue.size
        val start = nodeMap[levelQueue.first()]!!
        val end = nodeMap[levelQueue.last()]!!

        for (i in 0 until levelSize) {
            val node = levelQueue.removeAt(0)

            if (node?.left != null) {
                nodeMap[node.left] = nodeMap[node]?.times(2) ?: 0
                levelQueue.add(node.left)
            }
            if (node?.right != null) {
                nodeMap[node.right] = (nodeMap[node]?.times(2) ?: 0) + 1
                levelQueue.add(node.right)
            }
        }

        val width: Int = (end - start) + 1
        maxWidth = maxOf(maxWidth, width)
        level++
    }

    return maxWidth
}

/**
 * #108  Find K Closest Elements
 */
fun findClosestElements(arr: IntArray, k: Int, x: Int): List<Int> { // O(log(n))
    var left = 0
    var right = arr.size - k

    while (left < right) {
        val mid = (left + right)/2

        if (x - arr[mid] > arr[mid+k] - x) {
            left = mid + 1
        } else {
            right = mid
        }
    }

    return arr.slice(left until left+k)
}

/**
 * #109  Longest Repeating Character Replacement
 */
fun characterReplacement(s: String, k: Int): Int {
    var longest = 0
    val frequency = mutableMapOf<Char, Int>()
    var left = 0

    for ((right,c) in s.withIndex()) {
        frequency[c] = frequency.getOrDefault(c, 0) + 1
        val maxFrequency = frequency.maxOf { (k,v) -> v }
        val length = right - left + 1
        if (length <= maxFrequency + k) {
            longest = maxOf(longest, length)
        } else {
            frequency[s[left]] = frequency.getOrDefault(s[left], 0) - 1
            left++
        }
    }

    return longest
}

/**
 * #110  Inorder Successor in BST
 */

/**
 * #111  Jump Game
 *
 * [2,3,1,1,4]
 *
 * 0-1:
 *  2:
 *  3:
 *    4:
 *  4:
 * 0-2:
 *  3:
 *    4:
 *
 *    [1,0,0,0,0]
 *    [t,f,f,f,f]
 *    2
 *    [1,1,1,0,0]
 *    [t,t,t,f,f]
 *
 *    create a boolean array
 *    traverse numbers
 *      if this index is reachable
 *      for each update the next n indexes to true
 *      return the last index of the boolean array
 *
 * indexes      0, 1, 2, 3, 4
 * array       [2, 3, 1, 1, 4]
 *
 * targetIndex = 4
 *
 * i = 3
 *  nums[i] = 1
 *  i + nums[i] = 4
 *  targetIndex = 3
 * i = 2
 *  nums[i] = 1
 *  i + nums[i] = 3
 * i = 1
 *  nums[i] = 3
 *  i + nums[i] = 4
 * i = 0
 *  nums[i] = 2
 *  i + nums[i] = 2
 */
fun canJump(nums: IntArray): Boolean {
    var goal = nums.lastIndex

    for (i in nums.lastIndex-1 downTo 0) {
        if (i + nums[i] >= goal) {
            goal = i
        }
    }

    return goal == 0
}

/**
 * #112  Add Two Numbers
 */

/**
 * #113  Generate Parentheses
 */

/**
 * #114  Sort List
 * find mid node of list
 * recursively sort each half
 * merge sorted halves
 */
fun sortList(head: ListNode?): ListNode? {
    fun merge(left: ListNode?, right: ListNode?): ListNode? {
        val dummyMergedHead = ListNode(0)
        var cur: ListNode? = dummyMergedHead
        var l = left
        var r = right

        while (l != null && r != null) {
            if (l.`val` < r.`val`) {
                cur?.next = l
                l = l.next
            } else {
                cur?.next = r
                r = r.next
            }
            cur = cur?.next
        }
        if (l != null) cur?.next = l
        if (r != null) cur?.next = r

        return dummyMergedHead.next
    }

    if (head?.next == null) {
        return head
    }

    var slow = head
    var fast = head
    var prev:ListNode? = null

    while (fast != null) {
        prev = slow
        slow = slow?.next
        fast = fast.next?.next
    }
    prev?.next = null

    val left = sortList(head)
    val right = sortList(slow)

    return merge(left, right)
}

/**
 * #115  Number of Connected Components in an
 */

/**
 * #116  Minimum Knight Moves
 */

/**
 * #117  Subarray Sum Equals K
 *
 * A prefix sum is the sum of all the previous values + the current value
 * Allows to calculate the sum of any subarray in constant time.
 *  sum(start .. end) = prefix[end] - prefix[start-1]
 *
 *  if prefix[end] - k == prefix[start-1] then this is a solution
 *
 * The number of earlier prefix sums equal to sum-k tell us how many sub-arrays ending at current index sum to k
 *
 *
 */
fun subarraySum(nums: IntArray, k: Int): Int {
    var solutions = 0
    var sum = 0
    val map = mutableMapOf(0 to 1)

    for (num in nums) {
        sum += num
        solutions += map[sum - k] ?: 0
        map[sum] = (map[sum] ?: 0) + 1
    }

    return solutions
}

/**
 * #118  Asteroid Collision
 */

/**
 * #119  Random Pick with Weight
 */

/**
 * #120  Kth Largest Element in an Array
 */