import java.util.HashMap
import kotlin.math.max

/**
 * #41  Squares of a Sorted Array
 */
fun sortedSquares(nums: IntArray): IntArray {
    var l = 0
    var r = nums.lastIndex
    val result = IntArray(nums.size)

    for (i in nums.lastIndex downTo 0) {
        val lSquare = nums[l] * nums[l]
        val rSquare = nums[r] * nums[r]
        if (lSquare > rSquare) {
            result[i] = lSquare
            l++
        } else {
            result[i] = rSquare
            r--
        }
    }

    return result
}

/**
 * #42  Maximum Subarray
 */

/**
 * #43  Insert Interval
 */

/**
 * #44  01 Matrix
 */

/**
 * #45  K Closest Points to Origin
 */

/**
 * #46  Longest Substring Without Repeating
 */
fun lengthOfLongestSubstring(s: String): Int {
    var startIndex = 0
    var endIndex = 0
    var ans = 0
    val myMap = HashMap<Char, Int>()

    while (endIndex < s.length) {
        if (s[endIndex] in myMap) {
            startIndex = max(startIndex, myMap[s[endIndex]] ?: 0)
        }

        ans = max(ans, endIndex - startIndex + 1)
        myMap[s[endIndex]] = ++endIndex
    }
    return ans
}

/**
 * #47  3Sum
 */

/**
 * #48  Binary Tree Level Order Traversal
 */

/**
 * #49  Clone Graph
 */

/**
 * #50  Evaluate Reverse Polish Notation
 */

/**
 * #51  Course Schedule
 */

/**
 * #52  Implement Trie (Prefix Tree)
 */

/**
 * #53  Coin Change
 */

/**
 * #54  Product of Array Except Self
 */

/**
 * #55  Min Stack
 */

/**
 * #56  Validate Binary Search Tree
 */

/**
 * #57  Number of Islands
 */
fun numOfIslands(grid: Array<CharArray>): Int {
    val visited = Array(grid.size) { BooleanArray(grid[0].size) }
    val adjacents = arrayOf(intArrayOf(0,1), intArrayOf(1,0), intArrayOf(0,-1), intArrayOf(-1,0))
    var numOfIslands = 0

    fun dfs(row: Int, col: Int) {
        visited[row][col] = true

        for (adj in adjacents) {
            val r = row + adj[0]
            val c = col + adj[1]
            if (r < 0 || r >= grid.size || c < 0 || c >= grid[0].size || visited[r][c]) continue
            if (grid[r][c] == '1') dfs(r,c)
        }
    }

    for (row in grid.indices) {
        for (col in grid[0].indices) {
            if (grid[row][col] == '1' && !visited[row][col]) {
                dfs(row, col)
                numOfIslands++
            }
        }
    }

    return numOfIslands
}

/**
 * #58  Rotting Oranges
 */

/**
 * #59  Search in Rotated Sorted Array
 */

/**
 * #60  Combination Sum
 */