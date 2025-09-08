import java.util.PriorityQueue

/**
 * #161  Alien Dictionary
 */

/**
 * #162  Bus Routes
 *
 * stop -> bus
 * 1 -> 0
 * 2 -> 0
 * 3 -> 1
 * 6 -> 1
 * 7 -> 0,1
 */
fun numBusesToDestination(routes: Array<IntArray>, source: Int, target: Int): Int {
    if (target == source) return 0

    val busesAtStop = mutableMapOf<Int, MutableSet<Int>>() // stop - busesIds
    for ((bus, route) in routes.withIndex()) {
        for (stop in route) {
            if (stop !in busesAtStop) busesAtStop[stop] = mutableSetOf()
            busesAtStop[stop]?.add(bus)
        }
    }

    val visitedStops = mutableSetOf<Int>()
    val visitedBuses = mutableSetOf<Int>()
    val queue = mutableListOf(source)
    visitedStops.add(source)

    var busesTaken = 0

    while (queue.isNotEmpty()) {
        repeat(queue.size) {
            val stop = queue.removeFirst()
            if (stop == target) return busesTaken

            for (bus in busesAtStop[stop] ?: emptySet()) {
                if (bus in visitedBuses) continue
                visitedBuses.add(bus)

                for (nextStop in routes[bus]) {
                    if (nextStop !in visitedStops) {
                        visitedStops.add(nextStop)
                        queue.add(nextStop)
                    }
                }
            }
        }
        busesTaken++
    }

    return -1
}

/**
 * #163  Sliding Window Maximum
 */
fun maxSlidingWindow(nums: IntArray, k: Int): IntArray {
    if (nums.size == 1) return nums

    val maximus = IntArray(nums.size - k + 1)
    val windowDequeue = mutableListOf<Int>() // Stores the max element on the first position

    for (i in nums.indices) {
        // remove elements that are out of the current window
        // i - k is the boundary
        while (windowDequeue.isNotEmpty() && windowDequeue.first() <= i - k) {
            windowDequeue.removeFirst()
        }

        // remove all smaller elements at the back of the queue
        // because they'll never be the max if a bigger comes after them
        while (windowDequeue.isNotEmpty() && nums[windowDequeue.last()] < nums[i]) {
            windowDequeue.removeLast()
        }

        windowDequeue.add(i)
        if (i >= k-1) { // if there are at least 3 elements in window -> add max to result
            maximus[i-k+1] = nums[windowDequeue.first()]
        }
    }

    return maximus
}

/**
 * #164  Palindrome Pairs
 *
 * create a function isPalindrome
 *
 * concatenate all words combinations
 *  check if palindrome
 *  Y - > add array of Indexes to result
 */
fun palindromePairs(words: Array<String>): List<List<Int>> {
    class TrieNode {
        var next = mutableMapOf<Char, TrieNode>()
        var index = -1
        val li = mutableListOf<Int>()
    }

    val result = mutableListOf<List<Int>>()

    fun isPalindrome(word: String): Boolean {
        var left = 0
        var right = word.lastIndex

        while (left <= right) {
            if (word[left++] != word[right--]) return false
        }

        return true
    }

    fun addWord(root: TrieNode, word: String, i: Int) {
        var node = root

        for (j in word.lastIndex downTo 0) {
            if (isPalindrome(word.substring(0, j+1))) {
                node.li.add(i)
            }

            val c = word[j]
            if (c !in node.next) {
                node.next[c] = TrieNode()
            }

            node = node.next.getValue(c)
        }

        node.index = i
        node.li.add(i)
    }

    fun search(words: Array<String>, i: Int, root: TrieNode) {
        var node = root

        for (j in words[i].indices) {
            if (node.index >= 0
                && node.index != i
                && isPalindrome(words[i].substring(j))) {
                result.add(listOf(i, node.index))
            }
            node = node.next[words[i][j]] ?: return
        }

        for (j in node.li) {
            if (i == j) continue
            result.add(listOf(i, j))
        }
    }

    val root = TrieNode()

    var emptyIndex = -1
    for (i in words.indices) {
        if (words[i].isEmpty()) {
            emptyIndex = i
            continue
        }
        addWord(root, words[i], i)
    }

    if (emptyIndex != -1) {
        for (i in words.indices) {
            if (i == emptyIndex) continue
            if (isPalindrome(words[i])) {
                result.add(listOf(emptyIndex, i))
                result.add(listOf(i, emptyIndex))
            }
        }
    }

    for (i in words.indices) {
        if (i == emptyIndex) continue
        search(words, i, root)
    }

    return result
}

/**
 * #165  Reverse Nodes in k-Group
 */
fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
    fun reverse(node: ListNode?, end: ListNode?): ListNode? {
        var cur = node
        var prev: ListNode? = null

        while (cur != end) {
            val next = cur?.next
            cur?.next = prev
            prev = cur
            cur = next
        }

        return prev
    }

    if (head == null) return null

    var tail = head
    for (i in 0 until k) {
        if (tail == null) return head
        tail = tail.next
    }

    val newHead = reverse(head, tail)
    head.next = reverseKGroup(tail, k)
    return newHead
}

/**
 * #166  Sudoku Solver
 *
 * keep track of the possible in each empty cell
 * if only one, place it
 * start again with the rest of the empty cells
 */
fun solveSudoku(board: Array<CharArray>) {
    val rows = Array(9) { IntArray(10) }
    val cols = Array(9) { IntArray(10) }
    val boxes = Array(9) { IntArray(10) }
    var sudokuSolved = false

    lateinit var backtrack: (Int, Int) -> Unit
    lateinit var placeNextNumbers: (Int, Int) -> Unit

    fun couldPlace(d: Int, row: Int, col: Int): Boolean {
        val i = (row / 3) * 3 + col / 3
        return rows[row][d] + cols[col][d] + boxes[i][d] == 0
    }

    fun placeNumber(d: Int, row: Int, col: Int) {
        val i = (row / 3) * 3 + col / 3;
        rows[row][d]++
        cols[col][d]++
        boxes[i][d]++
        board[row][col] = d.digitToChar()
    }

    fun removeNumber(d: Int, row: Int, col: Int) {
        val i = (row / 3) * 3 + col / 3
        rows[row][d]--
        cols[col][d]--
        boxes[i][d]--
        board[row][col] = '.'
    }

    backtrack = { row, col ->
        if (board[row][col] == '.') {
            for (d in 1 .. 9) {
                if (couldPlace(d, row, col)) {
                    placeNumber(d, row, col)
                    placeNextNumbers(row, col)
                    if (!sudokuSolved) removeNumber(d, row, col)
                }
            }
        } else {
            placeNextNumbers(row, col)
        }
    }

    placeNextNumbers = { row, col ->
        if (row == 9 - 1 && col == 9 - 1) {
            sudokuSolved = true
        } else if (col == 9 - 1) {
            backtrack(row + 1, 0)
        } else {
            backtrack(row, col + 1)
        }
    }

    for (row in 0 until 9) {
        for (col in 0 until 9) {
            if (board[row][col] == '.') continue
            placeNumber(board[row][col].digitToInt(), row, col)
        }
    }
    backtrack(0,0)
}

/**
 * #167  First Missing Positive
 */
fun firstMissingPositive(nums: IntArray): Int {
    if (nums.isEmpty()) return 1

    fun swap(i: Int, j: Int) {
        val tmp = nums[i]
        nums[i] = nums[j]
        nums[j] = tmp
    }

    // Place each number in its "correct" position. i.e. 3 ideally goes at index 2
    for (i in nums.indices) {
        while (nums[i] > 0 && nums[i] <= nums.size && nums[i] != nums[nums[i]-1]) {
            swap(i, nums[i] - 1)
        }
    }

    // find the first index where the expected number isn't there
    for (i in nums.indices) {
        if (nums[i] != i+1) {
            return i+1
        }
    }

    return nums.size + 1
}

/**
 * #168  N-Queens
 *
 * recursively calculate row by row
 * place the queen in the row
 * continue to next row until no new queen can be added in row
 * remove queen from row
 * if I have placed a queen in all the rows, this is a solution
 */
fun solveNQueens(n: Int): List<List<String>> {
    val solutions = mutableListOf<List<String>>()
    val queens = IntArray(n) { -1 } // queens[row] = column of queen in that row
    val cols = BooleanArray(n)      // columns already used
    val diag1 = BooleanArray(2 * n) // \ diagonals (row - col + n)
    val diag2 = BooleanArray(2 * n) // / diagonals (row + col)

    fun addSolution() {
        val board = mutableListOf<String>()
        for (r in 0 until n) {
            val row = CharArray(n) { '.' }
            row[queens[r]] = 'Q'
            board.add(String(row))
        }
        solutions.add(board)
    }

    fun backtrack(row: Int) {
        if (row == n) {
            addSolution()
            return
        }

        for (col in 0 until n) {
            val d1 = row - col + n
            val d2 = row + col
            if (cols[col] || diag1[d1] || diag2[d2]) continue

            // place queen
            queens[row] = col
            cols[col] = true
            diag1[d1] = true
            diag2[d2] = true

            backtrack(row + 1)

            // remove queen
            queens[row] = -1
            cols[col] = false
            diag1[d1] = false
            diag2[d2] = false
        }
    }

    backtrack(0)
    return solutions
}

/**
 * #169  Smallest Range Covering Elements from K
 */
fun smallestRange(nums: List<List<Int>>): IntArray {
    val pq = PriorityQueue<Triple<Int, Int, Int>>(compareBy { it.first }) // value, rowIndex, colIndex
    var curMax = Int.MIN_VALUE

    for (i in nums.indices) {
        val value = nums[i][0]
        pq.offer(Triple(value, i, 0))
        curMax = maxOf(curMax, value)
    }

    var rangeStart = 0
    var rangeEnd = Int.MAX_VALUE

    while (pq.size == nums.size) { // until one list is exhausted
        val (minVal, row, col) = pq.poll()

        // Update best range
        if (curMax - minVal < rangeEnd - rangeStart) {
            rangeStart = minVal
            rangeEnd = curMax
        }

        // Push next element from same row, if available
        if (col + 1 < nums[row].size) {
            val nextVal = nums[row][col+1]
            pq.offer(Triple(nextVal, row, col + 1))
            curMax = maxOf(curMax, nextVal)
        }
    }

    return intArrayOf(rangeStart, rangeEnd)
}