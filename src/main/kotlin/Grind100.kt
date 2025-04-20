import kotlin.math.abs
import kotlin.math.max

/**
 * #81  Task Scheduler
 */

/**
 * #82  LRU Cache
 */

/**
 * #83  Kth Smallest Element in a BST
 */

/**
 * #84  Daily Temperatures
 */

/**
 * #85  House Robber
 */

/**
 * #86  Gas Station
 */

/**
 * #87  Next Permutation
 */

/**
 * #88  Valid Sudoku
 */

/**
 * #89  Group Anagrams
 */

/**
 * #90  Maximum Product Subarray
 */

/**
 * #91  Design Add and Search Words Data Structure
 */

/**
 * #92  Pacific Atlantic Water Flow
 */

/**
 * #93  Remove Nth Node From End of List
 */

/**
 * #94  Shortest Path to Get Food
 */

/**
 * #95  Find the Duplicate Number
 */

/**
 * #96  Top K Frequent Words
 */

/**
 * #97  Longest Increasing Subsequence
 */
fun lengthOfLIS(nums: IntArray): Int {
    val sub = mutableListOf<Int>()

    sub.add(nums[0])

    for (i in 1 until nums.size) {
        if (sub.last() < nums[i]) {
            sub.add(nums[i])
        } else {
            val searchResult = sub.binarySearch(nums[i])
            val index = if (searchResult >= 0) searchResult else -searchResult - 1
            sub[index] = nums[i]
        }
    }

    return sub.size
}

fun lengthOfLISDP(nums: IntArray): Int { // O(n^2)
    val dp = IntArray(nums.size) { 1 }

    for (i in 1 until nums.size) {
        for (j in i-1 downTo 0) {
            if (nums[i] > nums[j]) {
                dp[i] = max(dp[i], dp[j] + 1)
            }
        }
    }

    return dp.maxOrNull() ?: 1
}

/**
 * #98  Graph Valid Tree
 */

/**
 * #99  Course Schedule II
 */

/**
 * #100  Swap Nodes in Pairs
 */