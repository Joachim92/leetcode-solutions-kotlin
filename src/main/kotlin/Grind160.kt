import java.lang.Exception
import java.util.PriorityQueue
import java.util.Stack
import kotlin.math.sign

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
 *
 * sort by end date
 * remove the one with later end date
 *
 */
fun eraseOverlapIntervals(intervals: Array<IntArray>): Int {
    intervals.sortBy { it.last() }
    var current = Int.MIN_VALUE
    var removals = 0

    for (interval in intervals) {
        if (interval.first() < current) {
            removals += 1
        } else {
            current = interval.last()
        }
    }

    return removals
}

/**
 * #144  Minimum Window Substring
 */
fun minWindow(s: String, t: String): String {
    if (s.isEmpty() || t.isEmpty()) return ""

    val frequencyCount = mutableMapOf<Char, Int>()
    for (c in t) {
        frequencyCount[c] = frequencyCount.getOrDefault(c, 0) + 1
    }

    var charsFoundInWindow = 0
    val charsNeededInWindow = frequencyCount.size
    val windowCount = mutableMapOf<Char, Int>()
    var start = 0
    var resultLength = Int.MAX_VALUE
    var result = Pair(-1, -1)

    for ((i, c) in s.withIndex()) {
        windowCount[c] = windowCount.getOrDefault(c, 0) + 1

        if (c in frequencyCount && windowCount[c] == frequencyCount[c]) {
            charsFoundInWindow++
        }

        while (charsFoundInWindow == charsNeededInWindow) {
            if ((i - start + 1) < resultLength) {
                result = Pair(start, i)
                resultLength = i - start + 1
            }
            val startChar = s[start]
            windowCount[startChar] = windowCount[startChar]!! - 1
            if (startChar in frequencyCount && windowCount[startChar]!! < frequencyCount[startChar]!!) {
                charsFoundInWindow--
            }
            start++
        }
    }

    return if (resultLength == Int.MAX_VALUE) "" else s.substring(result.first, result.second + 1)
}

/**
 * #145  Serialize and Deserialize Binary Tree
 */
fun serialize(root: TreeNode?): String {
    if (root == null) return ""

    val sb = StringBuilder()

    val level = mutableListOf<TreeNode?>()
    level.add(root)

    while (level.isNotEmpty()) {
        var allNull = true
        val nodesInLevel = level.size

        for (i in 0 until nodesInLevel) {
            val n = level.removeFirst()

            if (n == null) {
                sb.append("_,")
            } else {
                sb.append(n.`val`).append(',')
                allNull = false
            }
            level.add(n?.left)
            level.add(n?.right)
        }

        if (allNull) break
    }

    while (sb.last() == ',' || sb.last() == '_') {
        sb.deleteCharAt(sb.lastIndex)
    }

    return sb.toString()
}

fun deserialize(data: String): TreeNode? {
    if (data.isEmpty()) return null

    val nodeValues = data.split(',')

    val nodes = nodeValues.map {
        if (it.length == 1 && it[0] == '_') {
            null
        } else {
            TreeNode(it.toInt())
        }
    }

    val root: TreeNode? = nodes[0]

    for ((i, node) in nodes.withIndex()) {
        val index = i*2
        if (index+1 < nodes.size) node?.left = nodes[index+1]
        if (index+2 < nodes.size) node?.right = nodes[index+2]
    }

    return root
}

/**
 * #146  Trapping Rain Water
 */
fun trap(height: IntArray): Int {
    var left = 0
    var right = height.size - 1
    var leftMax = 0
    var rightMax = 0
    var water = 0

    while (left < right) {
        if (height[left] < height[right]) {
            if (height[left] >= leftMax) {
                leftMax = height[left]
            } else {
                water += leftMax - height[left]
            }
            left++
        } else {
            if (height[right] >= rightMax) {
                rightMax = height[right]
            } else {
                water += rightMax - height[right]
            }
            right--
        }
    }
    return water
}

/**
 * #147  Find Median from Data Stream
 */
fun medianFromDataStream() {
    val left = PriorityQueue<Int>(compareByDescending { it })
    val right = PriorityQueue<Int>()

    fun addNum(num: Int) {
        left.offer(num)
        right.offer(left.poll())

        if (left.size < right.size) {
            left.offer(right.poll())
        }
    }

    fun findMedian(): Double {
        return if (left.size > right.size) {
            left.peek().toDouble()
        } else {
            (left.peek() + right.peek()) / 2.0
        }
    }
}

/**
 * #148  Word Ladder
 */
fun ladderLength(beginWord: String, endWord: String, wordList: List<String>): Int {
    fun changesByOne(word: String, other: String): Boolean {
        var diff = 0
        for (i in word.indices) if (word[i] != other[i]) diff++
        return diff == 1
    }

    val graph = mutableMapOf<String, MutableSet<String>>()
    var length = 1

    for (word in wordList) graph[word] = mutableSetOf()
    if (graph[beginWord] == null) graph[beginWord] = mutableSetOf()

    for ((word, neighbors) in graph) {
        for (w in graph.keys) {
            if (changesByOne(word, w)) neighbors.add(w)
        }
    }

    val queue = mutableListOf(beginWord)
    val visited = mutableSetOf<String>()

    while (queue.isNotEmpty()) {
        val nodes = queue.size
        for (i in 0 until nodes) {
            val current = queue.removeAt(0)
            visited.add(current)
            if (current == endWord) return length
            val neighbors = graph.getOrDefault(current, mutableSetOf())
            for (adj in neighbors) {
                if (adj in visited) continue
                queue.add(adj)
            }
        }
        length++
    }

    return 0
}
// This was line 285
/**
 * #149  Basic Calculator
 *
 * Remove spaces
 * use stack to split operations in parentheses
 * solve the operation
 *  if first char is '-' grab the number as negative
 *      split the rest by operator +,-
 *      perform the operation recursively
 */
fun calculate(s: String): Int {
    var number = 0
    var result = 0
    var sign = 1
    val signStack = Stack<Int>()
    val resultStack = Stack<Int>()

    for (c in s) {
        when {
            c.isDigit() -> {
                number = number * 10 + c.digitToInt()
            }
            c == '+' -> {
                result += sign * number
                number = 0
                sign = 1
            }
            c == '-' -> {
                result += sign * number
                number = 0
                sign = -1
            }
            c == '(' -> {
                resultStack.push(result)
                signStack.push(sign)
                number = 0
                sign = 1
                result = 0
            }
            c == ')' -> {
                result += sign * number
                result = resultStack.pop() + (signStack.pop() * result)
                number = 0
            }
        }
    }

    if (number != 0) result += sign * number

    return result
}


/**
 * #150  Maximum Profit in Job Scheduling
 *
 * Create a class to hold the jobs data together
 * Create a sorted list of the jobs by endTime
 * Get a list that includes all the start and end times
 *  use each value as a trigger to get a list of the jobs that end at that specific time (use bsearch to get that part of the list)
 *  update the maxProfit so that it is the highest between previous and current
 *  for the jobs that end at this time (or have already ended)
 *      update the maxProfit between current and the maxProfit before the job started + the profit of the current job
 */
fun jobScheduling(startTime: IntArray, endTime: IntArray, profit: IntArray): Int {
    data class Job(val end: Int, val start: Int, val profit: Int)
    val jobs = mutableListOf<Job>()
    val dp = mutableMapOf(0 to 0) // endTime, maxProfit

    fun bsearch(time: Int, start: Int, end: Int): List<Job> {
        if (start > end) return emptyList()

        val mid = (start + end) / 2
        val t = jobs[mid].end
        return if (t == time) {
            val jobsThatEndAtTime = mutableListOf<Job>()
            var l = mid
            var r = mid
            while (l > 1 && jobs[l-1].end == time) l--
            while (r < jobs.lastIndex && jobs[r+1].end == time) r++
            jobsThatEndAtTime.addAll(jobs.slice(l .. r))
            jobsThatEndAtTime
        } else if (t < time) {
            bsearch(time, mid+1, end)
        } else {
            bsearch(time, start, mid-1)
        }
    }

    val times = (startTime.toMutableList() + endTime.toMutableList()).toSet().toList().sorted()
    times.forEach { t -> dp[t] = 0 }

    for (i in profit.indices) jobs.add(Job(endTime[i], startTime[i], profit[i]))
    jobs.sortBy { it.end }

    for (i in 1 until times.size) {
        val time = times[i]
        val jobsThatEndAtTime = bsearch(time, 0, jobs.lastIndex)
        dp[time] = maxOf(dp[times[i-1]]!!, dp[time]!!)
        for (job in jobsThatEndAtTime) {
            dp[time] = maxOf(dp[time]!!, dp[job.start]!!+job.profit)
        }
    }

    return dp[jobs.last().end] ?: -1
}

/**
 * #151  Merge k Sorted Lists
 */
fun mergeKLists(lists: Array<ListNode?>): ListNode? {
    val pq = PriorityQueue<ListNode?>(compareBy { it.`val` })

    for (listNode in lists) {
        if (listNode != null) pq.add(listNode)
    }

    val first: ListNode? = pq.poll()
    var cur = first
    if (first?.next != null) pq.add(first.next)

    while (cur != null && pq.isNotEmpty()) {
        val min = pq.poll()
        cur.next = min
        cur = cur.next
        if (min?.next != null) {
            pq.add(min.next)
        }
    }

    return first
}

/**
 * #152  Largest Rectangle in Histogram
 *
 * i = 2
 *            0 1 2 3 4 5
 * heights = [2,1,5,6,2,3]
 * nextSmallLeft = [-1, -1, 1, -1, -1, -1]
 * nextSmallRight = [1, 6, 6, 6, 6, 6]
 * stack = [1,2]
 *
 * nextSmallLeft = [-1, -1, 1, 2, 1, 4]
 * nextSmallRight = [1, 6, 4, 4, 6, 6]
 *
 * create leftSmall & rightSmall
 *
 * if current <= prev
 *  area can increase
 * else
 *  area cannot increase
 *
 *
 * leftMinHeight[-1,1,1,5,2,2]
 * rightMinHeight[1,1,5,2,2,-1]
 *
 *
 */
fun largestRectangleArea(heights: IntArray): Int {
    var maxArea = 0
    val stack = mutableListOf<Int>()
    val allHeights = heights.toMutableList()
    allHeights.add(0)

    for (i in allHeights.indices) {

        while (stack.isNotEmpty() && allHeights[i] < heights[stack.last()]) {
            val mid = stack.removeLast()
            val left = if (stack.isNotEmpty()) stack.last() else i
            val area = if (stack.isEmpty()) {
                i * heights[mid]
            } else {
                (i - left - 1) * heights[mid]
            }
            maxArea = maxOf(maxArea, area)
        }
        stack.add(i)
    }

    return maxArea
}

/**
 * #153  Binary Tree Maximum Path Sum
 */
fun maxPathSum(root: TreeNode?): Int {
    var maxSum = Int.MIN_VALUE

    fun helper(node: TreeNode?): Int {
        if (node == null) return 0

        val leftMax = helper(node.left)
        val rightMax = helper(node.right)

        val maxOfLeftAndRight = maxOf(leftMax, rightMax)
        val maxOneNodeRoot = maxOf(node.`val`, node.`val` + maxOfLeftAndRight)
        val maxAll = maxOf(maxOneNodeRoot, leftMax + rightMax + node.`val`)

        maxSum = maxOf(maxSum, maxAll)

        return maxOneNodeRoot
    }

    helper(root)

    return maxSum
}


/**
 * #154  Maximum Frequency Stack
 *
 *          5,7,5,7,4,5
 *
 *          5 -> 3
 *          7 -> 2
 *          4 -> 1
 *
 *          5,7,5,4
 */
class FreqStack() {

    private val stack = mutableMapOf<Int, MutableList<Int>>() // count, values
    private val countMap = mutableMapOf<Int, Int>() // value, count
    private var maxFrequency = 0

    fun push(`val`: Int) {
        var curFrequency = countMap.getOrDefault(`val`, 0)
        curFrequency += 1
        countMap[`val`] = curFrequency

        if (!stack.contains(curFrequency)) {
            stack[curFrequency] = mutableListOf()
        }

        stack.getValue(curFrequency).add(`val`)
        maxFrequency = maxOf(maxFrequency, curFrequency)
    }

    fun pop(): Int {
        val ans = stack.getValue(maxFrequency).removeLast()

        var curFrequency = countMap.getValue(ans)
        curFrequency -= 1
        countMap[ans] = curFrequency
        if (stack.getValue(maxFrequency).isEmpty()) {
            maxFrequency -= 1
        }

        return ans
    }

}

/**
 * #155  Median of Two Sorted Arrays
 *
 * get the position of the median
 * merge arrrays until median
 * return median
 */
fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
    val n = nums1.size + nums2.size

    val medianIndex = n / 2
    var oneIndex = 0
    var twoIndex = 0
    var prev = 0
    var cur = 0

    for (i in 0 .. medianIndex) {
        prev = cur
        if (oneIndex >= nums1.size) {
            cur = nums2[twoIndex]
            twoIndex += 1
        } else if (twoIndex >= nums2.size) {
            cur = nums1[oneIndex]
            oneIndex += 1
        } else {
            if (nums1[oneIndex] <= nums2[twoIndex]) {
                cur = nums1[oneIndex]
                oneIndex += 1
            } else {
                cur = nums2[twoIndex]
                twoIndex += 1
            }
        }

    }

    return if (n % 2 == 0) {
        (prev + cur) / 2.0
    } else {
        cur.toDouble()
    }
}

/**
 * #156  Longest Increasing Path in a Matrix
 */
fun longestIncreasingPath(matrix: Array<IntArray>): Int {
    val rows = matrix.size
    val cols = matrix[0].size
    val dp = Array(rows) { IntArray(cols) }
    val neighbors = arrayOf(intArrayOf(0,1), intArrayOf(1,0), intArrayOf(0,-1), intArrayOf(-1,0))

    fun dfs(row: Int, col: Int): Int {
        if (dp[row][col] != 0) return dp[row][col]

        var maxLen = 1
        for ((dr, dc) in neighbors) {
            val nRow = row + dr
            val nCol = col + dc
            if (nRow in 0 until rows
                && nCol in 0 until cols
                && matrix[nRow][nCol] > matrix[row][col]) {
                maxLen = maxOf(maxLen, 1 + dfs(nRow, nCol))
            }
        }
        dp[row][col] = maxLen
        return maxLen
    }

    var longest = 0
    for (r in matrix.indices) {
        for (c in matrix[0].indices) {
            longest = maxOf(longest, dfs(r, c))
        }
    }
    return longest
}

/**
 * #157  Longest Valid Parentheses
 *
 * stack
 *
 * (()
 *
 *
 * )()())
 *
 * i  = 4
 * st = 3
 *
 * I need a flag to tell me where the valid substring is starting
 *  I can use a start pointer
 *  The end pointer is current index
 *
 * Would it be a good idea to keep track of the index of the opening parenthesis?
 *  List<Int> index of the opening parenthesis
 *
 *  0,1
 *  length = index - stackIndex + 1
 *  check if it is max length
 *
 * Open parenthesis found
 *  Add it to stack
 *
 * I've found a closing parenthesis, do I have an opening parenthesis in the top of the stack?
 *  Y -> This is part of a valid
 *  N -> This is not valid. Continue to next index
 *
 *  0 1 2 3 4 5
 *  ) ( ) ( ) )
 *
 *  start = 1
 *  stack = 3
 *
 *  0 1 2 3 4
 *  ( ( ) ( )
 *
 *  start = 0
 *  stack =
 */
fun longestValidParetheses(s: String): Int {
    if (s.isEmpty()) return 0

    var longest = 0
    // This stores the indexes of the opening parenthesis that are not yet closed
    val stack = mutableListOf(-1)

    for ((i, c) in s.withIndex()) {
        if (c == '(') {
            stack.add(i)
        } else { // c == ')'
            stack.removeLast()
            if (stack.isEmpty()) {
                // this is not valid. Move start to the next char
                stack.add(i)
            } else {
                longest = maxOf(longest, i - stack.last())
            }
        }
    }

    return longest
}

/**
 * #158  Design In-Memory File System
 */

/**
 * #159  Employee Free Time
 */

/**
 * #160  Word Search II
 *
 * create a Trie using the words
 * do dfs on each cell of the matrix
 */
fun findWords(board: Array<CharArray>, words: Array<String>): List<String> {
    class TrieNode(val value: String, val neighbors: MutableMap<Char, TrieNode>, var isWord: Boolean)
    val wordsSet = words.toSet()

    fun createTrie(): TrieNode {
        val root = TrieNode("", mutableMapOf(), false)

        for (word in words) {
            var node = root
            for (c in word) {
                if (c !in node.neighbors) {
                    val text = node.value + c
                    val isWord = text in wordsSet
                    node.neighbors[c] = TrieNode(text, mutableMapOf(), isWord)
                }
                node = node.neighbors.getValue(c)
            }
        }

        return root
    }

    val found = mutableListOf<String>()
    val rows = board.size
    val cols = board[0].size
    val root = createTrie()
    val cellNeighbors = arrayOf(intArrayOf(0,1), intArrayOf(1,0), intArrayOf(0,-1), intArrayOf(-1,0))

    fun dfs(row: Int, col: Int, node: TrieNode, visited: Array<BooleanArray>) {
        val boardChar = board[row][col]
        val curNode = node.neighbors[boardChar] ?: return
        if (curNode.isWord) {
            found.add(curNode.value)
        }
        visited[row][col] = true

        for (neighbor in cellNeighbors) {
            val nRow = row + neighbor[0]
            val nCol = col + neighbor[1]

            if (nRow !in 0 until rows
                || nCol !in 0 until cols
                || visited[nRow][nCol]
                || board[nRow][nCol] !in curNode.neighbors) {
                continue
            }

            dfs(nRow, nCol, curNode, visited)

        }
        visited[row][col] = false
    }

    for (row in 0 until rows) {
        for (col in 0 until cols) {
            dfs(row, col, root, Array(rows) { BooleanArray(cols) }) // M*n * 4^maxWordLength
        }
    }

    return found.distinct()
}