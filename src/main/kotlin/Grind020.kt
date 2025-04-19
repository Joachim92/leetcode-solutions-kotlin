import java.util.*
import kotlin.collections.HashMap
import kotlin.math.*

/**
 * #1 Two Sum
 * Create a map to track the index and the missing value, so once we find it, we know that we have finished,
 * and we can return it
 */
fun twoSum(nums: IntArray, target: Int): IntArray {
    val map = HashMap<Int, Int>()

    for(i in nums.indices) {
        if (map.contains(nums[i])) {
            return intArrayOf(map[nums[i]]!!, i)
        } else {
            map[target-nums[i]] = i
        }
    }

    return intArrayOf()
}

/**
 * #2 Valid Parenthesis
 * {[]}
 *
 * stack = {[
 */
fun isValid(s: String): Boolean {
    val closingParenthesis = setOf(')', '}', ']')
    val pairs = mapOf(')' to '(', '}' to '{', ']' to '[')

    if (s.length % 2 != 0 || s[0] in closingParenthesis) return false

    val stack: MutableList<Char> = mutableListOf()

    for (c in s) {
        if (c in closingParenthesis) {
            if (stack.isEmpty()) return false
            val top = stack.removeAt(stack.lastIndex)
            if (top != pairs[c]) return false
        } else {
            stack.add(c)
        }
    }

    return stack.isEmpty()
}

/**
 * #3 Merge two sorted lists
 */
fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
    if (list1 == null) return list2
    if (list2 == null) return list1

    return if (list1.`val` <= list2.`val`) {
        list1.next = mergeTwoLists(list1.next, list2)
        list1
    } else {
        list2.next = mergeTwoLists(list2.next, list1)
        list2
    }
}

/**
 * #4 Best Time To Buy And Sell Stock
 */
fun maxProfit(prices: IntArray): Int {
    var maxProfit = 0
    var smallest = Int.MAX_VALUE

    for (i in prices.indices) {
        val profit = prices[i] - smallest
        maxProfit = max(profit, maxProfit)
        smallest = min(smallest, prices[i])
    }

    return maxProfit
}

/**
 * #5 Valid Palindrome
 */
fun isPalindrome(s: String): Boolean {
    val result = StringBuilder()

    for (c in s) {
        if (c.isLetterOrDigit()) {
            result.append(c.lowercaseChar())
        }
    }
    val resultString = result.toString()
    var l = 0
    var r = resultString.length - 1

    while (l < r) {
        if (resultString[l] != resultString[r]) {
            return false
        }
        l++
        r--
    }

    return true
}

/**
 * #6 Invert Binary Tree
 */
fun invertTree(root: TreeNode?): TreeNode? {
    if (root == null) return null

    invertTree(root.left)
    invertTree(root.right)

    val tmp = root.left
    root.left = root.right
    root.right = tmp

    return root
}

/**
 * #7 Valid Anagram
 */
fun isAnagram(s: String, t: String): Boolean {
    if (s.length != t.length) return false

    // create a hashmap to count the occurrences of the chars, first word increments second one decrements

    val count = mutableMapOf<Char, Int>()

    for (i in s.indices) {
        if (count[s[i]] == null) count[s[i]] = 0
        count[s[i]] = count[s[i]]!!.plus(1)

        if (count[t[i]] == null) count[t[i]] = 0
        count[t[i]] = count[t[i]]!!.minus(1)
    }

    count.forEach { (k, v) ->
        if (v != 0) return false
    }

    return true
}

/**
 * #8 Binary Search (Iterative)
 */
fun bsearch(nums: IntArray, target: Int): Int {
    var start = 0
    var end = nums.size-1
    while (start <= end) {
        val mid = start + (end - start)/2
        when {
            nums[mid] == target -> return mid
            nums[mid] > target -> end = mid-1
            else -> start = mid+1
        }
    }
    return -1
}

/**
 * #8 Binary Search (Recursive)
 */
fun bsearchRecursive(nums: IntArray, target: Int, carryover: Int = 0): Int {
    if (nums.isEmpty()) return -1
    val mid = nums.size / 2
    if (nums[mid] == target) return mid + carryover

    return if (target < nums[mid]) {
        bsearchRecursive(nums.sliceArray(0 until mid), target, carryover)
    } else {
        bsearchRecursive(nums.sliceArray(mid+1 .. nums.indices.last), target, carryover + mid+1)
    }
}

/**
 * #9 Flood Fill
 *
 * bfs
 */
fun floodFill(image: Array<IntArray>, sr: Int, sc: Int, color: Int): Array<IntArray>  {
    val originalColor = image[sr][sc]
    val visited = Array(image.size) { BooleanArray(image[0].size) }
    val queue = mutableListOf<IntArray>()
    queue.add(intArrayOf(sr, sc))

    while (queue.isNotEmpty()) {
        val currentPixel = queue.removeAt(0)
        val r = currentPixel[0]
        val c = currentPixel[1]
        image[r][c] = color
        visited[r][c] = true

        val adjacents = arrayOf(intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(0, -1), intArrayOf(-1, 0))

        for (adj in adjacents) {
            val adjR = r + adj[0]
            val adjC = c + adj[1]
            if (adjR < 0 || adjR >= image.size || adjC < 0 || adjC >= image[0].size || visited[adjR][adjC]) continue
            val adjacentPixelColor = image[adjR][adjC]
            if (adjacentPixelColor == originalColor) {
                queue.add(intArrayOf(adjR, adjC))
            }
        }
    }

    return image
}

/**
 * #10 Lowest Common Ancestor of a Binary Search Tree
 */
fun lowestCommonAncestorOld(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
    val smallest = if (p!!.`val` < q!!.`val`) p else q
    val largest = if (p.`val` > q.`val`) p else q

    return when {
        root!!.`val` < smallest.`val` -> lowestCommonAncestorOld(root.right!!, p, q)
        root.`val` > largest.`val` -> lowestCommonAncestorOld(root.left!!, p, q)
        else -> root
    }
}

/**
 * #11 Balanced Binary Tree
 */
fun isBalanced(root: TreeNode?): Boolean {
    fun getDepth(root: TreeNode?, level: Int = 0): Int {
        if (root == null) return level - 1
        return max(getDepth(root.left, level+1), getDepth(root.right, level+1))
    }

    if (root == null) return true

    if (!isBalanced(root.left)) return false
    if (!isBalanced(root.right)) return false

    return abs(getDepth(root.left) - getDepth(root.right)) <= 1
}

/**
 * #12 Linked List Cycle
 */
fun hasCycle(head: ListNode?): Boolean {
    val visited = mutableSetOf<ListNode>()
    var current = head

    while (current != null) {
        if (current in visited) return true
        visited.add(current)
        current = current.next
    }

    return false
}

/**
 * #13 Implement queue using stacks
 */
class MyQueue() {
    private val stackOne = Stack<Int>()
    private val stackTwo = Stack<Int>()
    private var front: Int? = null

    fun push(x: Int) {
        if (stackOne.isEmpty()) {
            front = x
        }
        stackOne.push(x)
    }

    fun pop(): Int {
        if (stackTwo.isEmpty()) {
            while (stackOne.isNotEmpty()) {
                stackTwo.push(stackOne.pop())
            }
        }
        return stackTwo.pop()
    }

    fun peek(): Int? {
        if (stackTwo.isNotEmpty()) {
            return stackTwo.peek()
        }
        return front
    }

    fun empty(): Boolean {
        return stackOne.isEmpty() && stackTwo.isEmpty()
    }
}

/**
 * #14 First bad version
 */
fun firstBadVersion(n: Int) : Int {

    fun isBadVersion(n: Int): Boolean {
        return n >= 4
    }

    var l = 1
    var r = n

    while (l < r) {
        val mid = l + (r-l) / 2
        if (isBadVersion(mid)) {
            r = mid
        } else {
            l = mid + 1
        }
    }
    return l
}

/**
 * #15 Ransom Note
 */
fun canConstruct(ransomNote: String, magazine: String): Boolean {
    val dict = java.util.HashMap<Char, Int>()
    for (c in magazine) {
        dict[c] = dict.getOrDefault(c, 0) + 1
    }
    for (c in ransomNote) {
        if(c !in dict.keys) {
            return false
        } else if (dict[c] == 0) {
            return false
        }

        val prev = dict[c]!!
        dict[c] = prev-1
    }
    return true
}


/**
 * # 16 Climbing Stairs
 */
fun climbStairs(n: Int): Int {
    val memo = IntArray(n+1)

    fun recursive(n: Int): Int {
        if (n == 1) return 1
        if (n == 2) return 2
        if (memo[n] != 0) return memo[n]
        memo[n] = recursive(n-1) + recursive(n-2)
        return memo[n]
    }

    return recursive(n)
}

/**
 * #17 Longest Palindrome
 */
fun longestPalindrome(s: String): String { // O(n^2)
    if (s.length == 1) return s

    var longest = s[0].toString()

    fun expandFromCenter(l: Int, r: Int): String { // O(n)
        var left = l; var right = r
        while (left >= 0 && right <= s.lastIndex && s[left] == s[right]) { // O(n)
            left--; right++
        }
        left++; right--
        return s.substring(left, right+1)
    }

    for (i in s.indices) { // O(n)
        val substrOneCenter = expandFromCenter(i, i)
        val substrTwoCenter = expandFromCenter(i, i+1)
        val biggestSubstr = if (substrOneCenter.length > substrTwoCenter.length) substrOneCenter else substrTwoCenter
        if (biggestSubstr.length > longest.length) longest = biggestSubstr
    }

    return longest
}

/**
 * #18 Reverse Linked List
 */
fun reverseList(head: ListNode?): ListNode? {
    var cur = head
    var prev: ListNode? = null

    while (cur != null) {
        val tmp = cur.next
        cur.next = prev
        prev = cur
        cur = tmp
    }

    return prev
}

fun reverseListRecursively(head: ListNode?): ListNode? {
    fun helper(prev: ListNode?, cur: ListNode?): ListNode? {
        if (cur == null) return prev
        val next = cur.next

        cur.next = prev
        return helper(cur, next)
    }

    return helper(null, head)
}

/**
 * #19 Majority Element
 */
fun majorityElement(nums: IntArray): Int {
    val majority = java.util.HashMap<Int, Int>()

    for (n in nums) {
        majority[n] = majority.getOrDefault(n, 0) + 1
        if (majority[n]!! > nums.size/2) return n
    }

    return -1
}

/**
 * #20 Add Binary
 */
fun addBinary(a: String, b: String): String {
    val sum = StringBuilder()
    var aNormalized = a
    var bNormalized = b
    val indent = "0".repeat(abs(a.length-b.length))

    when {
        a.length < b.length ->  aNormalized = a.prependIndent(indent)
        b.length < a.length -> bNormalized = b.prependIndent(indent)
        else -> Unit
    }

    var carryover = false

    for (i in aNormalized.length-1 downTo  0) {
        if (carryover) {
            when {
                aNormalized[i] == '0' && bNormalized[i] == '0' -> { sum.insert(0, '1'); carryover = false }
                aNormalized[i] == '0' && bNormalized[i] == '1' -> { sum.insert(0, '0'); carryover = true }
                aNormalized[i] == '1' && bNormalized[i] == '0' -> { sum.insert(0, '0'); carryover = true }
                aNormalized[i] == '1' && bNormalized[i] == '1' -> { sum.insert(0,'1'); carryover = true }
            }
        } else {
            when {
                aNormalized[i] == '0' && bNormalized[i] == '0' -> { sum.insert(0, '0'); carryover = false }
                aNormalized[i] == '0' && bNormalized[i] == '1' -> { sum.insert(0, '1'); carryover = false }
                aNormalized[i] == '1' && bNormalized[i] == '0' -> { sum.insert(0, '1'); carryover = false }
                aNormalized[i] == '1' && bNormalized[i] == '1' -> { sum.insert(0,'0'); carryover = true }
            }
        }
    }

    if (carryover) sum.insert(0, '1')

    return sum.toString()
}
