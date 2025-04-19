/**
 * #61  Permutations
 */

/**
 * #62  Merge Intervals
 */

/**
 * #63  Lowest Common Ancestor of a Binary Tree
 */
fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
    if (root == null) return null

    val left = lowestCommonAncestor(root.left, p, q)
    val right = lowestCommonAncestor(root.right, p, q)
    val found = (root == p || root == q)

    return when {
        found || (left != null && right != null) -> root
        left != null -> left
        right != null -> right
        else -> null
    }
}

/**
 * #64  Time Based Key-Value Store
 */

/**
 * #65  Accounts Merge
 */

/**
 * #66  Sort Colors
 */

/**
 * #67  Word Break
 */

/**
 * #68  Partition Equal Subset Sum
 */

/**
 * #69  String to Integer (atoi)
 */

/**
 * #70  Spiral Matrix
 */

/**
 * #71  Subsets
 */

/**
 * #72  Binary Tree Right Side View
 */

/**
 * #73  Longest Palindromic Substring
 */

/**
 * #74  Unique Paths
 */

/**
 * #75  Construct Binary Tree from Preorder and
 */

/**
 * #76  Container With Most Water
 */

/**
 * #77  Letter Combinations of a Phone Number
 */

/**
 * #78  Word Search
 */

/**
 * #79  Find All Anagrams in a String
 */

/**
 * #80  Minimum Height Trees
 */