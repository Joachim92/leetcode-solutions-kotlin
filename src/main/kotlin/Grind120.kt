/**
 * #101  Path Sum II
 */

/**
 * #102  Longest Consecutive Sequence
 */

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