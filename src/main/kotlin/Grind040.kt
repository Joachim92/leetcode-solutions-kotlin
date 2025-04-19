import java.util.*
import kotlin.math.max

/**
 * #21  Diameter of Binary Tree
 */
fun diameterOfBinaryTree(root: TreeNode?): Int {
    var ans = 0

    fun getLength(node: TreeNode?): Int {
        if (node == null) return 0
        val leftLength = getLength(node.left)
        val rightLength = getLength(node.right)

        ans = max(ans, leftLength + rightLength)
        return max(leftLength, rightLength) + 1
    }

    getLength(root)
    return ans
}

/**
 * #22  Middle of the Linked List
 */
fun middleNode(head: ListNode?): ListNode? {
    var size = 0
    var cur = head

    while (cur != null) {
        cur = cur.next
        size++
    }

    val middle = size/2
    cur = head

    for (i in 0..middle) {
        cur = cur?.next
    }

    return cur
}

/**
 * #23  Maximum Depth of Binary Tree
 */
fun maxDepth(root: TreeNode?): Int {
    if(root == null) {
        return 0
    }

    val levelNodes = LinkedList<TreeNode>()
    if(root.left != null) {
        levelNodes.add(root.left!!)
    }
    if (root.right != null) {
        levelNodes.add(root.right!!)
    }
    return maxDepthRec(levelNodes, 1)
}

fun  maxDepthRec(levelNodes: LinkedList<TreeNode>, currentLevel: Int): Int {
    if (levelNodes.isEmpty()) {
        return currentLevel
    }
    val nextLevelNodes = LinkedList<TreeNode>()
    while(levelNodes.isNotEmpty()) {
        val node = levelNodes.pollFirst()
        if(node?.left != null) {
            nextLevelNodes.add(node.left!!)
        }
        if (node?.right != null) {
            nextLevelNodes.add(node.right!!)
        }
    }

    return maxDepthRec(nextLevelNodes, currentLevel + 1)
}

/**
 * #24  Contains Duplicate
 */
fun containsDuplicate(nums: IntArray): Boolean {
    // TODO
    return false
}

/**
 * #25  Meeting Rooms
 */
// TODO

/**
 * #26  Roman to Integer
 */
fun romanToInt(s: String): Int {
 // TODO
    return 0
}

/**
 * #27  Backspace String Compare
 */
fun backspaceCompare(s: String, t: String): Boolean {
 // TODO
    return false
}

/**
 * #28  Counting Bits
 */

/**
 * #29  Same Tree
 */

/**
 * #30  Number of 1 Bits
 */

/**
 * #31  Longest Common Prefix
 */

/**
 * #32  Single Number
 */

/**
 * #33  Palindrome Linked List
 */

/**
 * #34  Move Zeroes
 */

/**
 * #35  Symmetric Tree
 */

/**
 * #36  Missing Number
 */

/**
 * #37  Palindrome Number
 */

/**
 * #38  Convert Sorted Array to Binary Search Tree
 */

/**
 * #39  Reverse Bits
 */

/**
 * #40  Subtree of Another Tree
 */
fun isSubtree(root: TreeNode?, subRoot: TreeNode?): Boolean {

    fun helper(root: TreeNode?, subRoot: TreeNode?): Boolean {
        if (root == null && subRoot == null) return true
        if (root == null || subRoot == null) return false
        if (root.`val` != subRoot.`val`) return false

        return helper(root.left, subRoot.left) &&
                helper(root.right, subRoot.right)
    }

    if (root == null) return false
    if (helper(root, subRoot)) return true
    return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot)
}