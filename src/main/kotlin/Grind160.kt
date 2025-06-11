/**
 * #141  Combination Sum IV
 */
fun combinationSum4(nums: IntArray, target: Int): Int { // DP
    val dp = IntArray(target+1)
    dp[0] = 1

    for (i in 1 .. target) {
        for (num in nums) {
            if (i - num > 0) {
                dp[i] += dp[i-num]
            }
        }
    }

    return dp[dp.lastIndex]
}

//fun combinationSum4(nums: IntArray, target: Int): Int { // Recursion + Memoization
//    val sortedNums = nums.sorted()
//    val memo = mutableMapOf<Int, Int>()
//
//    fun recursive(n: Int): Int {
//        if (n == 0) return 1
//        if (n < sortedNums[0]) return 0
//        if (n in memo) return memo[n]!!
//
//        var count = 0
//
//        for (num in sortedNums) {
//            if (n - num < 0) break
//            count += recursive(n - num)
//        }
//
//        memo[n] = count
//        return count
//    }
//
//    return recursive(target)
//}

/**
 * #142  Insert Delete GetRandom O(1)
 */

/**
 * #143  Non-overlapping Intervals
 */

/**
 * #144  Minimum Window Substring
 */

/**
 * #145  Serialize and Deserialize Binary Tree
 */

/**
 * #146  Trapping Rain Water
 */

/**
 * #147  Find Median from Data Stream
 */

/**
 * #148  Word Ladder
 */

/**
 * #149  Basic Calculator
 */

/**
 * #150  Maximum Profit in Job Scheduling
 */

/**
 * #151  Merge k Sorted Lists
 */

/**
 * #152  Largest Rectangle in Histogram
 */

/**
 * #153  Binary Tree Maximum Path Sum
 */

/**
 * #154  Maximum Frequency Stack
 */

/**
 * #155  Median of Two Sorted Arrays
 */

/**
 * #156  Longest Increasing Path in a Matrix
 */

/**
 * #157  Longest Valid Parentheses
 */

/**
 * #158  Design In-Memory File System
 */

/**
 * #159  Employee Free Time
 */

/**
 * #160  Word Search II
 */