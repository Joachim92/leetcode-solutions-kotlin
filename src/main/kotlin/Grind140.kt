import kotlin.math.abs

/**
 * #121  Maximal Square
 */

/**
 * #122  Rotate Image
 */
fun rotate(matrix: Array<IntArray>) {

    // vertical
    for (col in matrix.indices) {
        for (row in 0 until matrix.size/2) {
            val tmp = matrix[row][col]
            matrix[row][col] = matrix[matrix.size-1-row][col]
            matrix[matrix.size-1-row][col] = tmp
        }
    }

    // transpose
    for (row in matrix.indices) {
        for (col in row+1 until matrix.size) {
            val tmp = matrix[row][col]
            matrix[row][col] = matrix[col][row]
            matrix[col][row] = tmp
        }
    }

}

/**
 * #123  Binary Tree Zigzag Level Order Traversal
 */
fun zigzagLevelOrder(root: TreeNode?): List<List<Int>> {
    if (root == null) return emptyList()

    val zigzag = mutableListOf<List<Int>>()
    val queue = mutableListOf(root)
    var leftToRight = true

    while (queue.isNotEmpty()) {
        val levelSize = queue.size
        val level = mutableListOf<Int>()

        repeat(levelSize) {
            val node = queue.removeFirst()
            if (leftToRight) level.add(node.`val`) else level.add(0, node.`val`)

            node.left?.let { queue.add(it) }
            node.right?.let { queue.add(it) }
        }

        zigzag.add(level)
        leftToRight = !leftToRight
    }

    return zigzag
}

/**
 * #124  Design Hit Counter
 */

/**
 * #125  Path Sum III
 */
fun pathSum(root: TreeNode?, targetSum: Int): Int {
    val prefixSum = mutableMapOf(0L to 1)

    fun preorder(node: TreeNode?, prevSum: Long): Int {
        if (node == null) return 0

        val currentSum: Long = prevSum + node.`val`
        var count = prefixSum.getOrDefault(currentSum - targetSum, 0)

        prefixSum[currentSum] = prefixSum.getOrDefault(currentSum, 0) + 1
        count += preorder(node.left, currentSum)
        count += preorder(node.right, currentSum)
        prefixSum[currentSum] = prefixSum.getOrDefault(currentSum, 0) - 1

        return count
    }

    return preorder(root, 0)
}

/**
 * #126  Pow(x, n)
 */
fun myPow(x: Double, n: Int): Double {
    val num: Double = if (n < 0) 1/x else x
    var power: Long = abs(n.toLong())
    var product: Double = num
    var res = 1.0

    while (power > 0) {
        if (power % 2 == 1L) {
            res *= product
        }
        power /= 2
        product *= product
    }

    return res
}

/**
 * #127  Search a 2D Matrix
 */
fun searchMatrix(matrix: Array<IntArray>, target: Int): Boolean {
    val rows = matrix.size
    val cols = matrix[0].size

    var row = 0
    var col = cols - 1

    while (row < rows && col >= 0) {
        val num = matrix[row][col]
        if (num == target) return true
        if (target > num) {
            row++
        } else {
            col--
        }
    }

    return false
}

/**
 * #128  Largest Number
 */
fun largestNumber(nums: IntArray): String {
    val numbers = nums.map { it.toString() }
    val sorted = numbers.sortedWith { s1, s2 ->
        (s2+s1).compareTo(s1+s2)
    }
    val result = sorted.joinToString(separator = "")
    return if (result[0] == '0') "0" else result
}

/**
 * #129  Decode Ways
 */
fun numDecodings(s: String): Int {
    val dp = IntArray(s.length + 1)
    dp[s.length] = 1

    for (i in s.lastIndex downTo 0) {
        if (s[i] == '0') continue
        dp[i] = dp[i+1]
        if (i < s.lastIndex && ((s[i].toString() + s[i+1].toString()).toInt() in 1..26)) {
            dp[i] = dp[i] + dp[i+2]
        }
    }

    return dp[0]
}

//fun numDecodings(s: String): Int { // O(2^n) with memo recursive
//    val memo = IntArray(s.length)
//
//    fun recursive(i: Int): Int {
//        if (i == s.length) return 1
//        if (s[i] == '0') return 0
//        if (memo[i] != 0) return memo[i]
//
//        var result = recursive(i+1)
//        if (i < s.lastIndex && ((s[i].toString() + s[i+1].toString()).toInt() in 1..26)) {
//            result += recursive(i+2)
//        }
//
//        memo[i] = result
//        return result
//    }
//
//    return recursive(0)
//}

//fun numDecodings(s: String): Int { // O(n^2)
//    if (s.first() == '0') return 0
//
//    val decodeMap = mapOf(
//        "1" to 'A', "2"   to 'B', "3"  to 'C',
//        "4" to 'D', "5"   to 'E', "6"  to 'F',
//        "7" to 'G', "8"   to 'H', "9"  to 'I',
//        "10" to 'J', "11" to 'K', "12" to 'L',
//        "13" to 'M', "14" to 'N', "15" to 'O',
//        "16" to 'P', "17" to 'Q', "18" to 'R',
//        "19" to 'S', "20" to 'T', "21" to 'U',
//        "22" to 'V', "23" to 'W', "24" to 'X',
//        "25" to 'Y', "26" to 'Z'
//    )
//
//    var decodings = mutableListOf(mutableListOf(s[0].toString()))
//
//    for (i in 1 until s.length) {
//        val numberOfDecodings = decodings.size
//
//        for (j in 0 until numberOfDecodings) {
//            if (decodings[j].last().length == 1) {
//                val newDecoding = decodings[j].toMutableList()
//                newDecoding.removeLast()
//                val nString: String = decodings[j].last() + s[i].toString()
//                if (nString in decodeMap) {
//                    newDecoding.add(nString)
//                    decodings.add(newDecoding)
//                }
//            }
//            val secondDecoding = decodings[j].toMutableList()
//            if (s[i].toString() != "0") {
//                secondDecoding.add(s[i].toString())
//                decodings.add(secondDecoding)
//            }
//        }
//
//        decodings = decodings.slice(numberOfDecodings..decodings.lastIndex).toMutableList()
//    }
//
//    return decodings.size
//}

/**
 * #130  Meeting Rooms II
 */

/**
 * #131  Reverse Integer
 */

/**
 * #132  Set Matrix Zeroes
 */
fun setZeroes(matrix: Array<IntArray>) {
    val rows = matrix.size
    val cols = matrix[0].size

    fun setRowToZeros(row: Int) {
        for (col in 0 until cols) {
            matrix[row][col] = 0
        }
    }

    fun setColToZeros(col: Int) {
        for (row in 0 until rows) {
            matrix[row][col] = 0
        }
    }

    val rowsToZero = mutableSetOf<Int>()
    val colsToZero = mutableSetOf<Int>()

    for (row in 0 until rows) {
        for (col in 0 until cols) {
            if (matrix[row][col] == 0) {
                rowsToZero.add(row)
                colsToZero.add(col)
            }
        }
    }

    for (row in rowsToZero) {
        setRowToZeros(row)
    }

    for (col in colsToZero) {
        setColToZeros(col)
    }
}

/**
 * #133  Reorder List
 *
 *
 * split the list into two
 *
 * reverse the second one
 *
 * add one from left, then one from right
 */
fun reorderList(head: ListNode?) {
    fun reverseList(node: ListNode): ListNode {
        var cur: ListNode? = node
        var prev: ListNode? = null
        var tail = node

        while (cur != null) {
            tail = cur
            val next = cur.next
            cur.next = prev
            prev = cur
            cur = next
        }

        return tail
    }

    var slow = head
    var fast = head
    var prevSlow = slow

    while (fast?.next != null) {
        prevSlow = slow
        slow = slow?.next
        fast = fast.next?.next
    }

    if (slow?.equals(fast) == true) return

    prevSlow?.next = null

    var first: ListNode? = head
    var second: ListNode? = reverseList(slow!!)
    var result: ListNode? = ListNode(0)
    var even = true

    while (first != null && second != null) {
        if (even) {
            result?.next = first
            first = first.next
            result = result?.next
        } else {
            result?.next = second
            second = second.next
            result = result?.next
        }
        even = !even
    }

    if (first != null) {
        result?.next = first
    } else {
        result?.next = second
    }
}


/**
 * #134  Encode and Decode Strings
 */

/**
 * #135  Cheapest Flights Within K Stops
 */
fun findCheapestPrice(n: Int, flights: Array<IntArray>, src: Int, dst: Int, k: Int): Int {
    var prices = IntArray(n) { Int.MAX_VALUE }
    prices[src] = 0

    for (i in 0 .. k) {
        val tripPrices = prices.clone()
        for ((from, to, price) in flights) {
            if (prices[from] == Int.MAX_VALUE) continue
            tripPrices[to] = minOf(tripPrices[to], prices[from] + price)
        }
        prices = tripPrices
    }

    return if (prices[dst] == Int.MAX_VALUE) -1 else prices[dst]
}

/**
 * #136  All Nodes Distance K in Binary Tree
 *
 * traverse tree
 *  identify the target node
 *  create a bidirectional graph
 *      can do bfs k times
 */
fun distanceK(root: TreeNode?, target: TreeNode?, k: Int): List<Int> {
    val graph = mutableMapOf<Int, MutableList<Int>>()

    fun createGraph(node: TreeNode?) {
        if (node == null) return
        if (node.`val` !in graph.keys) graph[node.`val`] = mutableListOf()
        if (node.left != null) {
            if (node.left?.`val` !in graph.keys) {
                graph[node.left!!.`val`] = mutableListOf()
            }
            graph[node.`val`]?.add(node.left!!.`val`)
            graph[node.left!!.`val`]?.add(node.`val`)
        }
        if (node.right != null) {
            if (node.right?.`val` !in graph.keys) {
                graph[node.right!!.`val`] = mutableListOf()
            }
            graph[node.`val`]?.add(node.right!!.`val`)
            graph[node.right!!.`val`]?.add(node.`val`)
        }
        createGraph(node.left)
        createGraph(node.right)
    }

    createGraph(root)

    val queue = mutableListOf(target?.`val`!!)
    var distance = 0
    val visited = mutableSetOf<Int>()

    while (queue.isNotEmpty() && distance < k) {
        val nodesInLevel = queue.size

        for (i in 0 until nodesInLevel) {
            val node = queue.removeFirst()
            visited.add(node)
            val neighbors = graph[node]!!
            for (neighbor in neighbors) {
                if (neighbor !in visited) {
                    queue.add(neighbor)
                }
            }
        }

        distance++
    }

    return queue
}

/**
 * #137  3Sum Closest
 */

/**
 * #138  Rotate List
 *
 * get size
 * divide list into two from 0 until size-k, from size-k until size
 * reverse second list
 * join lists, reversed second + first
 */
fun rotateRight(head: ListNode?, k: Int): ListNode? {
    if (head?.next == null) return head

    var firstHead: ListNode? = head
    var firstTail: ListNode? = head
    var secondHead: ListNode? = null
    var secondTail: ListNode? = null

    var size: Int = 0

    var cur = head
    while (cur != null) {
        secondTail = cur
        cur = cur.next
        size += 1
    }

    val normalizedK = if (k < size) k else  k%size
    if (normalizedK == 0) return head

    for (i in 1 until size-normalizedK) {
        firstTail = firstTail?.next
    }
    secondHead = firstTail?.next

    firstTail?.next = null
    secondTail?.next = firstHead

    return secondHead
}

/**
 * #139  Find Minimum in Rotated Sorted Array
 */

/**
 * #140  Basic Calculator II
 */

























