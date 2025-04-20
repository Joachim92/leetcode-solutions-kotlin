/**
 * #101  Path Sum II
 */

/**
 * #102  Longest Consecutive Sequence
 *
 * Example 1:
 *
 * Input: nums = [100,4,200,1,3,2]
 * Output: 4
 * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
 * Example 2:
 *
 * Input: nums = [0,3,7,2,5,8,4,6,0,1]
 * Output: 9
 * Example 3:
 *
 * Input: nums = [1,0,1,2]
 * Output: 3
 *
 *
 * Constraints:
 *
 * 0 <= nums.length <= 105
 * -109 <= nums[i] <= 109
 *
 * [] size 109 * 2 + 1
 *
 * min = null | 0
 * max = null | 2
 * {1,0,2}
 *
 * set = {1,0,2}
 *
 * traverse set
 *  if there is no number exactly one position prior to this one, this is the start of a sequence
 *      length = 1
 *  else
 *      length += 1
 *
 * [1,0,2]
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

/**
 * #107  Maximum Width of Binary Tree
 */

/**
 * #108  Find K Closest Elements
 */

/**
 * #109  Longest Repeating Character Replacement
 */

/**
 * #110  Inorder Successor in BST
 */

/**
 * #111  Jump Game
 */

/**
 * #112  Add Two Numbers
 */

/**
 * #113  Generate Parentheses
 */

/**
 * #114  Sort List
 */

/**
 * #115  Number of Connected Components in an
 */

/**
 * #116  Minimum Knight Moves
 */

/**
 * #117  Subarray Sum Equals K
 */

/**
 * #118  Asteroid Collision
 */

/**
 * #119  Random Pick with Weight
 */

/**
 * #120  Kth Largest Element in an Array
 */