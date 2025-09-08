import java.util.*
import kotlin.math.*

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null

    override fun toString(): String {
        if (left != null && right != null) {
            return "TN={value:${`val`}, left:${left}, right:${right}}"
        }
        if (left != null) {
            return "TN={value:${`val`}, left:${left}}"
        }
        if (right != null) {
            return "TN={value:${`val`}, right:${right}}"
        }
        return "TN={value:${`val`}}"
    }
}

class ListNode(var `val`: Int) {
    var next: ListNode? = null

    override fun toString(): String {
        return "LN={val:${`val`}, next:${next}}"
    }

    fun toIntArray(): IntArray {
        val li = mutableListOf<Int>()
        var current: ListNode? = this
        while (current != null) {
            li.add(current.`val`)
            current = current.next
        }
        return li.toIntArray()
    }
}
data class Node(val `val`: Int, val neighbors: MutableList<Node?> = mutableListOf())
data class TextNode(val `val`: String, val neighbors: MutableList<TextNode> = mutableListOf()) {
    override fun toString(): String {
        return "TN(${`val`})"
//        return toString(mutableSetOf())
    }

    override fun hashCode(): Int {
        return this.`val`[0].code
    }

    private fun toString(visited: MutableSet<TextNode>): String {
        if (this in visited) {
            return "finish"
        }
        visited.add(this)
        var text = "TN(val: ${`val`}, neighbors: ["
        for (neighbor in neighbors) {
            text += "${neighbor.toString(visited)},"
        }
        text += "])"
        return text
    }
}

class TrieNode(val value: String, val adjacents: HashMap<Char, TrieNode>, var isWord: Boolean)

fun main(args: Array<String>) {
    println(args)
//    val mat = Array(5) { IntArray(5) }
//    for (row in mat.indices) {
//        for (col in mat[row].indices) {
//            println("cell={${row}, ${col}}")
//            println(mat[row][col])
//        }
//    }
}

/**
 * 1695. Maximum Erasure Value
 * O(n*n)
 */
fun maximumUniqueSubArrayQuadratic(nums: IntArray): Int {
    var maxScore = 0
    for (i in nums.indices) {
        val num = emptyList<Int>().toMutableList()
        var j = i
        val foundValues = emptySet<Int>().toMutableSet()
        while (j < nums.size) {
            if (foundValues.contains(nums[j])) {
                break
            }
            num.add(nums[j])
            foundValues.add(nums[j])
            j++
        }
        maxScore = max(maxScore, num.sum())
    }
    return maxScore
}

fun maximumUniqueSubArray(nums: IntArray): Int {
    var left = 0
    var maxScore = 0
    val scores = IntArray(nums.size)
    val positions = HashMap<Int, Int>()

    for (right in nums.indices) {
        val n = nums[right]

        left = max(left, positions[n] ?: 0)
        positions[n] = right + 1

        if (right == 0) { scores[right] = n } else { scores[right] = n + scores[right-1] }
        val curScore = if (left > 0) { scores[right] - scores[left-1] } else { scores[right] }

        maxScore = max(maxScore, curScore)
    }

    return maxScore
}

// ---------------------------------------------------------------------------------------------------------------------

private enum class Direction {
    RIGHT,
    DOWN,
    LEFT,
    UP
}

inline fun <reified T: Enum<T>> T.next(): T {
    val values = enumValues<T>()
    val nextOrdinal = (ordinal + 1) % values.size
    return values[nextOrdinal]
}

fun generateMatrix(n: Int): Array<IntArray> {
    val matrix =  Array(n) { IntArray(n) }

    var row = 0
    var col = 0
    var curDir = Direction.RIGHT

    for(i in 1 .. n*n) {
        matrix[row][col] = i
        var nextRow = row
        var nextCol = col
        when(curDir) {
            Direction.RIGHT -> nextCol++
            Direction.DOWN -> nextRow++
            Direction.LEFT -> nextCol--
            Direction.UP -> nextRow--
        }
        if(nextRow < 0 || nextRow >= n || nextCol < 0 || nextCol >= n || matrix[nextRow][nextCol] != 0){ //next cell is not zero
            curDir = curDir.next()
        }

        when(curDir) {
            Direction.RIGHT -> col++
            Direction.DOWN -> row++
            Direction.LEFT -> col--
            Direction.UP -> row--
        }
    }
    return matrix
}

/**
 * check node and children are valid bst
 * check if left subtree is valid and right subtree is valid
 *
 * - nothing -> valid
 * - no children -> valid
 * - only left -> valid if left < node
 * - only right -> valid if right > node
 * - both children -> both conditions met
 */
fun isValidBST(root: TreeNode?): Boolean {
    return isValidBSTMax(root, null, null)
}

/**
 *      3
 *   1      5
 * 0  2   4  6
 */
fun isValidBSTMax(node: TreeNode?, min: Int?, max: Int?): Boolean {
    if (node == null) {
        return true
    }
    if (node.left != null && node.left!!.`val` >= node.`val`) {
        return false
    }
    if (node.right != null && node.right!!.`val` <= node.`val`) {
        return false
    }
    if (max != null && node.`val` >= max) {
        return false
    }
    if (min != null && node.`val` <= min) {
        return false
    }
    return isValidBSTMax(node.left, min, node.`val`) && isValidBSTMax(node.right, node.`val`, max)
}

/**
 * 0 -> 1
 * 1 -> 2, 3
 * 2 -> 3
 * 3 ->
 *
 *
 *     0    1   2   3
 * 0
 * 1
 * 2
 * 3
 */
fun installDependencies(adjList: Array<Array<Int>>, packageToInstall: Int): Array<Any> {
    val installOrder = LinkedHashSet<Int>()

    installDependenciesDFS(adjList, packageToInstall, installOrder)

    return installOrder.toArray()
}

fun installDependenciesDFS(adjList: Array<Array<Int>>, packageToInstall: Int, installOrder: LinkedHashSet<Int>) {
    if (packageToInstall in installOrder) {
        return
    }
    val dependencies = adjList[packageToInstall]

    for (dep in dependencies) {
        installDependenciesDFS(adjList, dep, installOrder)
    }

    installOrder.add(packageToInstall)
}

// ---------------------------------
/**
 * start with every node as a parent of itself
 * for each edge, verify it can be added without creating a cycle
 * we guarantee that if we remove this edge, the graph that we will be left with will be a Tree because we know that
 * when it was originally created, one last edge was added and this edge created a loop. So by removing this, we will
 * just be removing the cycle but the graph will always be connected
 * Complexity: O(n^2)
 */
fun findRedundantConnection(edges: Array<IntArray>): IntArray? {
    val parents = IntArray(edges.size) { i -> i }

    fun findParent(node: Int): Int {
        return when (node == parents[node]) {
            true -> node
            false -> findParent(parents[node])
        }
    }

    for (edge in edges) {
        val p1 = findParent(edge[0] - 1)
        val p2 = findParent(edge[1] - 1)
        when (p1 == p2) {
            true -> return edge
            false -> parents[p1] = p2
        }
    }
    return null
}

fun isTree(edges: Array<IntArray>, numOfNodes: Int): Boolean {
    val graph = Array<ArrayList<Int>>(numOfNodes) { ArrayList() }
    for(edge in edges) {
        graph[edge[0]-1].add(edge[1]-1)
        graph[edge[1]-1].add(edge[0]-1)
    }

    return !hasCycles(graph) && isConnected(graph, numOfNodes)
}

fun isConnected(graph: Array<ArrayList<Int>>, numOfNodes: Int): Boolean {
    val visited = HashSet<Int>()
    dfs(0, graph, visited)

    return visited.size == numOfNodes
}

fun dfs(node: Int, graph: Array<ArrayList<Int>>, visited: HashSet<Int>) {
    if(node in visited) {
        return
    }
    visited.add(node)
    val adjs = graph[node]

    for(adj in adjs) {
        dfs(adj, graph, visited)
    }
}

fun hasCycles(graph: Array<ArrayList<Int>>): Boolean {
    val visited = mutableSetOf<Int>()
    for (node in graph.indices) {
        if (node !in visited) {
            if (cyclesDFS(node, null, graph, visited)) {
                return true
            }
        }
    }
    return false
}

fun cyclesDFS(node: Int, parent: Int?, graph: Array<ArrayList<Int>>, visited: MutableSet<Int>): Boolean {
    visited.add(node)
    for(adj in graph[node]) {
        if (adj !in visited) {
            if(cyclesDFS(adj, node, graph, visited)) {
                return true
            }
        } else if (adj != parent) {
            return true
        }
    }
    return false
}
// ---------------------------------
/**
 * For each node, if it is not visited
 *  Apply dfs(node = adj, parent = node)
 *
 * dfs
 *  visit de node
 *  for each adj
 *      if adj is not visited
 *          dfs(adj, node)
 *      else
 *          if adj != parent -> return true
 * return false
 */
fun isCyclicUndirected(graph: Array<IntArray>): Boolean {
    val visited = HashSet<Int>()

    fun isCyclicDFS(node: Int, parent: Int?): Boolean {
        visited.add(node)
        for (adjNode in graph[node]) {
            if (adjNode !in visited) {
                if (isCyclicDFS(adjNode, node)) {
                    return true
                }
            } else if (adjNode != parent) {
                return true
            }
        }
        return false
    }

    for (node in graph.indices) {
        if (node !in visited) {
            if(isCyclicDFS(node, null)) {
                return true
            }
        }
    }

    return false
}

/**
 * Remove one edge at a time, starting from the last and create a graph each time
 * O(n*n) n = # edges
 * for each graph
 *  check if it !hasCycles && isConnected -> True
 * return false
 *
 * hasCycles
 *  dfs(node, keepTrackOfParent)
 *      if adj != parent && already visited
 *          return true
 *
 * isConnected(numOfNodes)
 *     dfs(anyNode)
 *     if visited.size == numOfNodes
 *      return true
 */
fun findRedundantConnectionCuad(edges: Array<IntArray>): IntArray {
    fun hasCyclesNew(graph: Array<LinkedList<Int>>): Boolean {
        val visited = HashSet<Int>()
        fun dfsHelper(node: Int, parent: Int?): Boolean {
            visited.add(node)
            for (adj in graph[node]) {
                if (adj !in visited) {
                    if(dfsHelper(adj, node)) {
                        return true
                    }
                } else {
                    if (adj != parent) {
                        return true
                    }
                }
            }
            return false
        }

        return dfsHelper(0, null)
    }

    fun isConnectedNew(graph: Array<LinkedList<Int>>): Boolean {
        val visited = HashSet<Int>()
        fun dfsNew2(node: Int) { // O(E^2)
            visited.add(node)
            for(adj in graph[node]) { // O(e)
                if (adj !in visited) { // O(1)
                    dfsNew2(adj) // O(v)
                }
            }
        }

        dfsNew2(0)
        return visited.size == edges.size
    }

    for (i in edges.size-1 downTo 0) {
        val edge = edges[i]
        val edgesOneRemoved = edges.filter { !it.contentEquals(edge) }
        val graph = Array<LinkedList<Int>>(edges.size) { LinkedList() }
        for ((n1,n2) in edgesOneRemoved) {
            graph[n1-1].add(n2-1)
            graph[n2-1].add(n1-1)
        }
        if (isConnectedNew(graph) && !hasCyclesNew(graph)) {
            return edge
        }
    }
    return IntArray(0)
}

fun isCyclicDirected(graph: Array<IntArray>): Boolean {

    fun dfs(node: Int, visited: HashSet<Int>): Boolean {
        if (node in visited) {
            return true
        }
        visited.add(node)
        for (adj in graph[node]) {
            if(dfs(adj, visited)) {
                return true
            }
        }
        return false
    }

    for (node in graph.indices) {
        if (dfs(node, HashSet())) {
            return true
        }
    }

    return false
}

class RankedCell(val distance: Int, val price: Int, val row: Int, val col: Int) {
    override fun toString(): String {
        return "rc={$row, $col}"
    }
}

fun highestRankedKItems(grid: Array<IntArray>, pricing: IntArray, start: IntArray, k: Int): List<List<Int>> {
    val rankedKItems = ArrayList<ArrayList<Int>>()
    val pq = PriorityQueue<RankedCell>(kotlin.Comparator { rc1, rc2 ->
        return@Comparator when {
            rc1.distance != rc2.distance -> rc1.distance.compareTo(rc2.distance)
            rc1.price != rc2.price -> rc1.price.compareTo(rc2.price)
            rc1.row != rc2.row -> rc1.row.compareTo(rc2.row)
            rc1.col != rc2.col -> rc1.col.compareTo(rc2.col)
            else -> 0
        }
    })
    val adjacents = LinkedList<IntArray>()
    val distances = Array(grid.size) { IntArray(grid[0].size) }
    val visited = Array(grid.size) { BooleanArray(grid[0].size) }
    val adjPositions = listOf(intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(0, -1), intArrayOf(-1, 0))

    fun bfs(cell: IntArray) {
        if (visited[cell[0]][cell[1]]) {
            return
        }
        visited[cell[0]][cell[1]] = true
        val row = cell[0]
        val col = cell[1]
        val price = grid[row][col]
        val curDist = distances[row][col]
        if (price in pricing[0]..pricing[1]) { // Verify that is inclusive
            val rc = RankedCell(curDist, price, row, col)
            pq.add(rc)
        }
        for (adjPos in adjPositions) {
            val realAdjPos = intArrayOf(row + adjPos[0], col + adjPos[1])
            if (realAdjPos[0] >= 0 && realAdjPos[0] < grid.size && realAdjPos[1] >= 0 && realAdjPos[1] < grid[0].size) {
                if (!visited[realAdjPos[0]][realAdjPos[1]] && grid[realAdjPos[0]][realAdjPos[1]] != 0) {
                    adjacents.add(realAdjPos)
                    if (distances[realAdjPos[0]][realAdjPos[1]] == 0) {
                        distances[realAdjPos[0]][realAdjPos[1]] = curDist + 1
                    }
                }
            }
        }
        while (adjacents.isNotEmpty()) {
            bfs(adjacents.pollFirst())
        }
    }

    bfs(start)

    while (pq.isNotEmpty() && rankedKItems.size < k) {
        val rc = pq.poll()
        rankedKItems.add(arrayListOf(rc.row, rc.col))
    }

    return rankedKItems
}

//fun minWindow(s: String, t: String): String {
//    var l = 0
//    var r = 0
//    var minWindStr = ""
//    val mapCharCount = HashMap<Char, IntArray>()
//    val mapCount = HashMap<Char, Int>()
//
//    for (c in t) {
//        mapCount[c] = (mapCount[c] ?: 0) + 1
//    }
//
//    for (c in t.toSet()) {
//        if (!mapCharCount.containsKey(c)) {
//            mapCharCount[c] = IntArray(s.length)
//        }
//        for (i in s.indices) {
//            if (s[i] == c) {
//                if (i == 0) {
//                    mapCharCount.getValue(c)[i] += 1
//                } else {
//                    mapCharCount.getValue(c)[i] = mapCharCount.getValue(c)[i-1] + 1
//                }
//            } else {
//                if (i != 0) {
//                    mapCharCount.getValue(c)[i] = mapCharCount.getValue(c)[i-1]
//                }
//            }
//        }
//    }
//
//    fun containsAllChars(): Boolean {
//        for ((c, countArray) in mapCharCount) {
//            val count = if (l-1 >= 0) countArray[r] - countArray[l-1] else countArray[r]
//            if (count < mapCount.getValue(c)) {
//                return false
//            }
//        }
//        return true
//    }
//
//    while (r < s.length && l <= r) {
//        val substr = s.substring(l..r)
//        if (containsAllChars()) {
//            minWindStr = if (minWindStr.isEmpty() || substr.length < minWindStr.length) substr else minWindStr
//            l++
//        } else {
//            r++
//        }
//    }
//
//    return minWindStr
//}

enum class ClassColor {
    WHITE,
    GRAY,
    BLACK
}

/**
    create adj list (install a prior to b like a->b)
    create in-degree array to indicate the in-degree of every node (how many nodes have to be installed before)
    find all nodes that do not have any dependency (degree 0)
    add them to a queue q
    while (q.isNotEmpty())
    node = q.pop()
    for all neighbors
    reduce degree by 1
    if (degree == 0)
    add it to q
    add node to topologicalOrder

    scenarios we return empty array:
    - all the nodes have at least one dependency
    - we have a cyclic dependency i.e. 1 needs 0 and 0 needs 1
 */
fun findOrder(numCourses: Int, prerequisites: Array<IntArray>): IntArray {
    val topologicalOrder = ArrayList<Int>()
    val graph = Array(numCourses) { ArrayList<Int>() }
    val inDegree = IntArray(numCourses)

    for ((a, b) in prerequisites) {
        graph[b].add(a)
        inDegree[a] += 1
    }

    val q = LinkedList<Int>()
    for(i in 0 until numCourses) {
        if (inDegree[i] == 0) {
            q.add(i)
        }
    }
    var coursedCourses = 0
    while(q.isNotEmpty()) {
        coursedCourses++
        val c = q.pollFirst()
        val neighbors = graph[c]
        for (neighbor in neighbors) {
            inDegree[neighbor] -= 1
            if (inDegree[neighbor] == 0) {
                q.add(neighbor)
            }
        }
        topologicalOrder.add(c)
    }
    return if(coursedCourses == numCourses) topologicalOrder.toIntArray() else return IntArray(0)
}

fun findRotation(mat: Array<IntArray>, target: Array<IntArray>): Boolean {
    fun rotate(matrix: Array<IntArray>): Array<IntArray> {
        val tmpMatrix = Array(matrix.size) { IntArray(matrix.size) }
        val size = matrix.size

        for (r in matrix.indices) {
            for (c in matrix.indices) {
                tmpMatrix[c][size-1-r] = matrix[r][c]
            }
        }
        return tmpMatrix
    }

    fun compareMatrix(m1: Array<IntArray>, m2: Array<IntArray>): Boolean {
        if (m1.size != m2.size) {
            return false
        }
        val n = m1.size
        for (r in 0 until n) {
            for (c in 0 until n) {
                if (m1[r][c] != m2[r][c]) {
                    return false
                }
            }
        }
        return true
    }

    var rotatedMat = mat

    for (i in 0 until 4) {
        if (compareMatrix(rotatedMat, target)) {
            return true
        } else {
            rotatedMat = rotate(rotatedMat)
        }
    }
    return false
}

/**
 * Complexity n = total of numbers in array
 * O(n*log(n)) + O(n*n) = O(n^2)
 */
fun threeSumClosest(nums: IntArray, target: Int): Int? {
    nums.sort()
    var minDiffSum: Int? = null
    for (i in 0 until nums.size-2) {
        var j = i+1
        var k = nums.size-1
        while (j < k) {
            val sum = nums[i] + nums[j] + nums[k]
            if (sum == target) {
                return sum
            }
            if (minDiffSum == null || abs(sum-target) < abs(minDiffSum-target)) {
                minDiffSum = sum
            }
            if (sum < target) {
                j++
            } else {
                k--
            }
        }
    }
    return minDiffSum
}

fun levelOrder(root: TreeNode?): List<Any> {
    val firstLevel = if (root != null) LinkedList(listOf(root)) else return emptyList()
    val output = ArrayList<Any>()

    fun bfs(level: LinkedList<TreeNode>) {
        output.add(level.clone())
        val nextLevel = LinkedList<TreeNode>()
        while (level.isNotEmpty()) {
            val node = level.pollFirst()
            val left = node?.left
            if (left != null) nextLevel.add(left)
            val right = node?.right
            if (right != null) nextLevel.add(right)
        }
        if (nextLevel.isNotEmpty())
            bfs(nextLevel)
    }

    bfs(firstLevel)
    return output
}

fun generateParenthesis(n: Int): List<String> {
    val combs = ArrayList<String>()

    fun isValid(current: CharArray): Boolean {
        var balance = 0
        for (c in current) {
            if (c == '(') {
                balance++
            } else {
                balance--
            }
            if (balance < 0) {
                return false
            }
        }
        return balance == 0
    }

    fun generateAll(current: CharArray, pos: Int, result: ArrayList<String>) {
        if (pos == current.size) {
            if (isValid(current)) {
                result.add(current.toString())
            }
        } else {
            current[pos] = '('
            generateAll(current, pos+1, result)
            current[pos] = ')'
            generateAll(current, pos+1, result)
        }
    }

    generateAll(CharArray(2*n), 0, combs)
    return combs
}

fun <T> generatePermutations(li: List<T>): List<List<T>> {
    val perms = ArrayList<ArrayList<T>>()
    //    val n = 6
    //    val initial = CharArray(n) { i -> if(i < n/2) '(' else ')' }

    // add the new element in all the positions each new comb will be 1 element larger

    fun <T> joinPerms(li1: List<T>, newElem: T): ArrayList<ArrayList<T>> {
        val newPerms = ArrayList<ArrayList<T>>()
        if (li1.isEmpty()) {
            newPerms.add(arrayListOf(newElem))
            return newPerms
        }
        for (i in 0..li1.size) {
            val currentPerm = ArrayList<T>()
            currentPerm.addAll(li1)
            currentPerm.add(i, newElem)
            newPerms.add(currentPerm)
        }

        return newPerms
    }

    for (e in li) {
        if (perms.isEmpty()) {
            perms.addAll(joinPerms(arrayListOf(), e))
            continue
        }
        val currentPerms = ArrayList<ArrayList<T>>()
        currentPerms.addAll(perms)
        perms.clear()
        for (permList in currentPerms) {
            perms.addAll(joinPerms(permList, e))
        }
    }

    return perms
}

fun nextPermutation(nums: IntArray): Unit { // 2341 -> 2413
    for (i in nums.size-1 downTo 1) {
        if (nums[i] > nums[i-1]) { // nums[i] = 4 nums[i-1] = 3
            var largerIndex = i
            while (nums[largerIndex] > nums[i-1]) { // find number larger than 3
                largerIndex++
            }
            largerIndex--
            // swap i-1 with largerIndex
            var tmp = nums[i-1]
            nums[i-1] = nums[largerIndex]
            nums[largerIndex] = tmp
            // reverse from largerIndex to last
            var startIndex = i
            var endIndex = nums.size-1

            while (startIndex < endIndex) {
                tmp = nums[startIndex]
                nums[startIndex] = nums[endIndex]
                nums[endIndex] = tmp
                startIndex++
                endIndex--
            }
        }
    }
}

fun floodFillOld(image: Array<IntArray>, sr: Int, sc: Int, newColor: Int): Array<IntArray>? {
    val color = image[sr][sc]

    fun dfs(r: Int, c: Int) {
        if (image[r][c] == color) {
            image[r][c] = newColor
            if (r >= 1) dfs(r - 1, c)
            if (c >= 1) dfs(r, c - 1)
            if (r + 1 < image.size) dfs(r + 1, c)
            if (c + 1 < image[0].size) dfs(r, c + 1)
        }
    }

    if (color != newColor) dfs(sr, sc)
    return image
}

fun maxSubArray(nums: IntArray): Int? {
    val maxVals = IntArray(nums.size)

    for (i in nums.indices) {
        if (i > 0) {
            maxVals[i] = max(nums[i], nums[i] + nums[i-1])
        } else {
            maxVals[i] = nums[i]
        }
    }

    return maxVals.maxOrNull()
}


/**
 * Traverse by level
 * add the last node.val of each level to output array
 */
fun getRightNodesOfTree(root: TreeNode): IntArray {
    val ans = ArrayList<Int>()
    val queue = LinkedList<TreeNode>()

    queue.add(root)

    var currentLevelNodeCount = 1
    var nextLevelNodeCount = 0

    while (queue.isNotEmpty()) {
        val node = queue.pollFirst()
        if (node?.left != null) {
            queue.add(node.left!!)
            nextLevelNodeCount++
        }
        if (node?.right != null) {
            queue.add(node.right!!)
            nextLevelNodeCount++
        }
        currentLevelNodeCount--
        if(currentLevelNodeCount == 0) {
            ans.add(node.`val`)
            currentLevelNodeCount = nextLevelNodeCount
            nextLevelNodeCount = 0
        }
    }

    return ans.toIntArray()
}

fun traverseTreeByLevel(root: TreeNode): IntArray {
    val ans = ArrayList<Int>()
    val queue = LinkedList<TreeNode>()

    queue.add(root)

    while (queue.isNotEmpty()) {
        val node = queue.pollFirst()
        ans.add(node.`val`)
        if (node?.left != null) queue.add(node.left!!)
        if (node?.right != null) queue.add(node.right!!)
    }

    return ans.toIntArray()
}

fun minCostClimbingStairs(cost: IntArray): Int {
    if (cost.isEmpty()) return 0
    if (cost.size < 2) return cost[0]

    val minCost = IntArray(cost.size+1)

    for (i in minCost.indices) {
        val prevCost = if (i == 0) 0 else minCost[i-1]
        val prevCost2 = if (i < 2) 0 else minCost[i-2]
        val curCost = if (i < cost.size) cost[i] else 0
        minCost[i] = min(prevCost + curCost, prevCost2 + curCost)
    }

    return minCost[minCost.size-1]
}

fun replaceWords(dictionary: List<String>, sentence: String): String {
    val out = StringBuilder()
    val trie = TrieNode("", HashMap<Char, TrieNode>(), false)

    for (root in dictionary) {
        var cur = trie
        for (i in root.indices) {
            val c = root[i]
            val word = (i == (root.length - 1))
            if(c in cur.adjacents.keys) {
                cur = cur.adjacents[c]!!
                if (word) cur.isWord = true
            } else {
                val newNode = TrieNode(root.substring(0 .. i), HashMap<Char, TrieNode>(), word)
                cur.adjacents[c] = newNode
                cur = newNode
            }
        }
    }

    for (word in sentence.split(" ")) {
        var cur = trie
        var appended = false
        for (c in word) {
            if (c in cur.adjacents) {
                cur = cur.adjacents[c]!!
                if (cur.isWord) {
                    appended = true
                    out.append(cur.value)
                    break
                }
            } else {
                appended = true
                out.append(word)
                break
            }
        }
        if (!appended) out.append(word)
        out.append(" ")
    }
    return out.toString().trim()
}

fun largestPerfectSetLength(bags: IntArray): Int { // O(n*n*log(n))
    var longestSetLength = -1
    bags.sort()
    val perfectSetsMap = HashMap<Int, Int>()

    for (bag in bags.reversed()) {
        if (bag*bag in perfectSetsMap.keys) {
            perfectSetsMap[bag] = perfectSetsMap[bag*bag]!! + 1
            perfectSetsMap.remove(bag*bag)
            if (perfectSetsMap[bag]!! >= 2) longestSetLength = max(longestSetLength, perfectSetsMap[bag]!!)
        } else {
            perfectSetsMap[bag] = 1
        }
    }

    return longestSetLength
}

/**
 * add all the intervals ending before the new interval starts
 * merge all overlapping intervals to one considering new interval
 * add the merged interval
 * add the rest
 *
 * Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
 * Output: [[1,2],[3,10],[12,16]]
 */
fun insert(intervals: Array<IntArray>, newInterval: IntArray): Array<IntArray> {
    var i = 0
    val tmp = ArrayList<IntArray>()

    while (i < intervals.size && intervals[i][1] < newInterval[0] ) {
        tmp.add(intervals[i])
        i++
    }

    var mergedInterval = newInterval
    while (i < intervals.size &&  intervals[i][0] <= mergedInterval[1]) {
        mergedInterval = intArrayOf(min(intervals[i][0], mergedInterval[0]), max(intervals[i][1], mergedInterval[1]))
        i++
    }
    tmp.add(mergedInterval)

    while (i < intervals.size) {
        tmp.add(intervals[i])
        i++
    }

    return tmp.toTypedArray()
}

fun kClosest(points: Array<IntArray>, k: Int): Array<IntArray> {
    class Point(val distance: Double, val point: IntArray): Comparable<Point> {
        override fun compareTo(other: Point): Int {
            return when {
                this.distance > other.distance -> 1
                this.distance < other.distance -> -1
                else -> 0
            }
        }
    }


    val pq = PriorityQueue<Point>()

    for (point in points) {
        val x = point[0]
        val y = point[1]
        val distance = sqrt(((x*x) + (y*y)).toDouble())
        val newPoint = Point(distance, point)
        pq.add(newPoint)
    }

    val result = Array(k) { IntArray(2) }

    for (i in 0 until k) {
        result[i] = pq.poll().point
    }

    return result
}

fun threeSum(nums: IntArray): List<List<Int>> {
    nums.sort()
    val result = MutableList(0) { MutableList(0) { it } }

    for (i in 0 until nums.size-2) {
        var l = i+1
        var r = nums.size-1
        if (i > 0 && nums[i] == nums[i-1]) continue
        while (l < r) {
            val sum = nums[i] + nums[l] + nums[r]

            if (sum == 0) {
                result.add(mutableListOf(nums[i], nums[l], nums[r]))
            }

            when {
                sum > 0 -> r--
                else -> l++
            }
        }
    }

    return result
}

fun levelOrder2(root: TreeNode?): List<List<Int>> {
    val queue = LinkedList<TreeNode>()
    var curLevelCount = 1
    var nextLevelCount = 0
    val out = MutableList(0) { List(0) { it } }

    if (root != null) {
        queue.add(root)
        out.add(mutableListOf(root.`val`))
    }

    while(queue.isNotEmpty()) {
        val node = queue.pollFirst()
        curLevelCount--
        if (node?.left != null) {
            queue.add(node.left!!)
            nextLevelCount++
        }
        if (node?.right != null) {
            queue.add(node.right!!)
            nextLevelCount++
        }
        if (curLevelCount == 0 && nextLevelCount != 0) {
            out.add(queue.take(nextLevelCount).map { treeNode -> treeNode.`val` })
            curLevelCount = nextLevelCount
            nextLevelCount = 0
        }
    }

    return out
}

fun cloneGraph(node: Node?): Node? {
    val visited = HashSet<Node>()
    val relations = HashMap<Node, Node>()

    fun cloneNeighbors(node: Node?): Node? {
        if (node == null) return null

        if (visited.contains(node)) return relations[node]
        visited.add(node)
        val newNode = Node(node.`val`)
        relations[node] = newNode
        for (neighbor in node.neighbors) {
            newNode.neighbors.add(cloneNeighbors(neighbor))
        }
        return newNode
    }

    return cloneNeighbors(node)
}

fun evalRPN(tokens: Array<String>): Int {
    val operators = setOf("+", "-", "*", "/")

    fun helper(toks: Array<String>): Array<String> {
        if (toks.size == 1) return toks

        var i = 0
        while (toks[i] !in operators) {
            i++
        }
        val operand1 = toks[i-2].toInt()
        val operand2 = toks[i-1].toInt()

        val result = when (toks[i]) {
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "*" -> operand1 * operand2
            "/" -> operand1 / operand2
            else -> throw Exception("Invalid operator")
        }

        val newToks = toks.sliceArray(0 until i-2) + result.toString() + toks.sliceArray(i+1 until toks.size)
        return helper(newToks)
    }

    return helper(tokens)[0].toInt()
}

class Trie() {

    class TrieNode(val text: String, var isWord: Boolean = false) {
        val nextPossibleValues = HashMap<Char, TrieNode>()
    }

    private val root = TrieNode("")

    fun insert(word: String) {
        var cur = root
        for (i in word.indices) {
            val c = word[i]
            if (c !in cur.nextPossibleValues) {
                val newNode = TrieNode(cur.text)
                cur.nextPossibleValues[c] = newNode
            }
            cur = cur.nextPossibleValues[c]!!
            if (i == word.length-1) cur.isWord = true
        }
    }

    fun search(word: String): Boolean {
        var cur = root
        for (c in word) {
            if (c !in cur.nextPossibleValues) return false
            cur = cur.nextPossibleValues[c]!!
        }
        return cur.isWord
    }

    fun startsWith(prefix: String): Boolean {
        var cur = root
        for (c in prefix) {
            if (c !in cur.nextPossibleValues) return false
            cur = cur.nextPossibleValues[c]!!
        }
        return true
    }

}

fun coinChange(coins: IntArray, amount: Int): Int {
    val dp = Array(amount+1) { amount+1 }
    dp[0] = 0

    for (i in dp.indices) {
        for (coin in coins) {
            if (i-coin < 0) continue
            dp[i] = min(dp[i], dp[i-coin]+1)
        }
    }

    return if (dp[amount] != amount+1) dp[amount] else -1
}

fun coinChange2(coins: IntArray, amount: Int): Int {
    val memo = HashMap<Int, Int>()

    fun minCoins(amou: Int, count: Int): Int {
        if (amou == 0) return count
        if (amou < 0) return -1
        if (memo[amou] != null) return memo[amou]!!

        var minCoinsCount = amount+1
        for (coin in coins) {
            val result = minCoins(amou-coin, count)
            if (result == -1) continue
            minCoinsCount = min(minCoinsCount, result+1)
        }
        memo[amou] = if (minCoinsCount == amount+1) -1 else minCoinsCount
        return memo[amou]!!
    }

    return minCoins(amount, 0)
}

fun numOfPaths(labyrinth: Array<IntArray>): Int { // O(n^2) || O(n*m) || once for each cell
    val end = intArrayOf(labyrinth.size-1, labyrinth[0].size-1)
    val memo = Array(labyrinth.size) { IntArray(labyrinth[0].size) }

    fun numOfPaths(row: Int, col: Int): Int {
        if (row >= labyrinth.size || col >= labyrinth[0].size || labyrinth[row][col] == 1) return 0
        if (intArrayOf(row, col).contentEquals(end)) return 1

        if (memo[row][col] == 0) memo[row][col] = numOfPaths(row, col+1) + numOfPaths(row+1, col)
        return memo[row][col]
    }

    return numOfPaths(0, 0)
}

fun fibonacci(n: Int): Int {
    val dp = IntArray(n+2)
    dp[0] = 1
    dp[1] = 1

    for (i in 2 .. n) {
        dp[i] = dp[i-1] + dp[i-2]
    }

    return dp[n]
}

/**
 * n = 5
 * values = [1, 3, 4]
 * result = 6
 */
fun numOfWays(n: Int, values: IntArray): Int {
    val dp = IntArray(n+1)
    dp[0] = 1
    dp[1] = 1
    dp[2] = 1
    dp[3] = 2

    for (i in 4..n) {
        for (v in values) {
            dp[i] += dp[i-v]
        }
    }

    return dp[n]
}

fun productExceptSelf(nums: IntArray): IntArray {
    val results = IntArray(nums.size) { 1 }

    for (i in 1 until nums.size) {
        results[i] = results[i-1] * nums[i-1]
    }

    var right = 1
    for (i in nums.size-1 downTo 0) {
        results[i] *= right
        right *= nums[i]
    }

    return results
}

class MinStack() {
    class Node(val value: Int, var next: Node?, var prev: Node?, var min: Int)
    private var head: Node? = null
    private var tail: Node? = null

    fun push(`val`: Int) {
        if (head == null) {
            head = Node(`val`, null, null, `val`)
            tail = head
        } else {
            tail?.next = Node(`val`, null, tail, min(tail?.min ?: throw Exception("Case not validated"), `val`))
            tail = tail?.next
        }
    }

    fun pop() {
        if (head == tail) {
            head = null
        }
        tail = tail?.prev
    }

    fun top(): Int {
        return tail?.value ?: throw Exception("Case not validated")
    }

    fun getMin(): Int {
        return tail?.min ?: throw Exception("Case not validated")
    }
}

fun orangesRotting(grid: Array<IntArray>): Int {
    var freshOranges = 0
    val rottenOranges = mutableListOf<IntArray>()
    var minutes = 0
    val m = grid.size
    val n = grid[0].size

    for (row in grid.indices) {
        for (col in grid[row].indices) {
            if (grid[row][col] == 1) {
                freshOranges++
            }else if (grid[row][col] == 2) {
                rottenOranges.add(intArrayOf(row, col))
            }
        }
    }

    if (freshOranges > 0 && rottenOranges.size == 0) return -1
    if (freshOranges == 0) return 0
    if (rottenOranges.size == 0) return 0

    val visited = Array(m) { BooleanArray(n) }
    var currentLevelCount = rottenOranges.size
    var nextLevelCount = 0
    val adjacents = arrayOf(intArrayOf(0,1), intArrayOf(1,0), intArrayOf(0,-1), intArrayOf(-1,0))

    while (rottenOranges.isNotEmpty()) {
        val rottenOrange = rottenOranges.removeAt(0)
        visited[rottenOrange[0]][rottenOrange[1]] = true
        currentLevelCount--

        for (adj in adjacents) {
            val adjOrange = intArrayOf(rottenOrange[0]+adj[0], rottenOrange[1]+adj[1])
            val aRow = adjOrange[0]
            val aCol = adjOrange[1]

            if (aRow < 0 || aRow >= m || aCol < 0 || aCol >= n || visited[aRow][aCol]) continue

            if (grid[aRow][aCol] == 1 && freshOranges > 0) {
                freshOranges--
                rottenOranges.add(intArrayOf(aRow, aCol))
                visited[aRow][aCol] = true
                nextLevelCount++
            }
        }

        if (currentLevelCount == 0 && nextLevelCount > 0) {
            currentLevelCount = nextLevelCount
            nextLevelCount = 0
            minutes++
        }
    }

    return if (freshOranges == 0) minutes else -1
}

fun permute2(nums: IntArray): List<List<Int>> {
    val permutations = mutableListOf(mutableListOf(nums[0]))

    for (j in 1 until nums.size) { // n-1
        val num = nums[j]
        val prevPerms = permutations.toMutableList()
        permutations.clear()
        for (perm in prevPerms) { // perms(n) * (n+1)
            for (i in 0..perm.size) { // 1 .. n
                val newPerm = perm.toMutableList()
                newPerm.add(i, num)
                permutations.add(newPerm)
            }
        }
    }

    return permutations
}

fun permute(nums: IntArray): List<List<Int>>? {
    val list: MutableList<List<Int>> = ArrayList()
    // Arrays.sort(nums); // not necessary
    backtrack(list, ArrayList(), nums)
    return list
}

private fun backtrack(permutations: MutableList<List<Int>>, tempList: MutableList<Int>, nums: IntArray) {
    if (tempList.size == nums.size) {
        permutations.add(ArrayList(tempList))
    } else {
        for (i in nums.indices) {
            if (tempList.contains(nums[i])) continue  // element already exists, skip
            tempList.add(nums[i])
            backtrack(permutations, tempList, nums)
            tempList.removeAt(tempList.size - 1)
        }
    }
}

fun tmp(nums: IntArray): Int { // O(n) S(n)
    val n = nums.size
    val lefts = IntArray(n) // S(n)
    val rights = IntArray(n) // S(n)
    var result = -1

    for (i in 0 until n) {
        if (nums[i] == 1) {
            if (i > 0) lefts[i] = lefts[i-1] + 1
            else lefts[i] = 1
        } else {
            if (i > 0) lefts[i-1]--
            lefts[i] = 0
        }
    }

    for (i in n-1 downTo 0) {
        if (nums[i] == 1) {
            if (i < n-1) rights[i] = rights[i+1] + 1
            else rights[i] = 1
        } else {
            if (i < n-1) rights[i]--
            rights[i] = 0
        }
    }

    for (i in 0 until n) {
        result = max(result, min(lefts[i], rights[i]))
    }

    return result
}

fun combinationSum2(candidates: IntArray, target: Int): List<List<Int>> {
    val combinations = mutableListOf<List<Int>>()
    candidates.sort() // O(nlog(n))

    fun dfs(newTarget: Int, path: MutableList<Int>) {
        if (path.size == 6 && path[0] == 3 && path[1] == 3 && path[2] == 3 && path[3] == 3 && path[4] == 3) {
            print("yeah")
        }
        if (newTarget == 0) {
            path.sort() // O(nlog(n))
            var newComb = true
            for (comb in combinations) { //
                if (comb.size != path.size) continue
                var i = 0
                while (i < path.size && path[i] == comb[i]) {
                    i++
                }
                if (i == path.size) newComb = false
            }
            if (newComb) combinations.add(path)
        }

        for (candidate in candidates) { // O(n)
            val rest = newTarget - candidate
            if (rest < 0) break
            val newList = path.toMutableList()
            newList.add(candidate)
            dfs(rest, newList)
        }
    }

    dfs(target, mutableListOf())

    return combinations
}

fun combinationSum(candidates: IntArray, target: Int): List<List<Int>> {
    val combinations = mutableListOf<List<Int>>()
    candidates.sort()

    fun backtrack(temp: MutableList<Int>, bTarget: Int, start: Int) {
        if (bTarget == 0) {
            combinations.add(temp.toList())
            return
        }

        for (i in start until candidates.size) {
            val remain = bTarget-candidates[i]
            if (remain < 0) break
            temp.add(candidates[i])
            backtrack(temp, remain, i)
            temp.removeLast()
        }
    }

    backtrack(mutableListOf(), target, 0)

    return combinations
}

fun merge(intervals: Array<IntArray>): Array<IntArray> { // O(nlogn) S(n)
    intervals.sortBy { ints -> ints[0] } // O(nlogn)
    val mergedIntervals = mutableListOf<IntArray>(intervals[0]) // S(n)

    var prevInterval = intervals[0]

    for (i in 1 until intervals.size) { // O(n)
        var curInterval = intervals[i]
        if (curInterval[0] <= prevInterval[1]) {
            mergedIntervals.removeAt(mergedIntervals.size-1)
            curInterval = intArrayOf(
                min(prevInterval[0], curInterval[0]),
                max(prevInterval[1], curInterval[1])
            )
        }
        mergedIntervals.add(curInterval)
        prevInterval = curInterval
    }

    return mergedIntervals.toTypedArray()
}

fun minimumEffortPath2(heights: Array<IntArray>): Int {
    val rows = heights.size
    val cols = heights[0].size
    val start = intArrayOf(0,0)
    val end = intArrayOf(rows-1, cols-1)
    val visited = Array(rows) { BooleanArray(cols) }
    val efforts = Array(rows) { IntArray(cols) { Int.MAX_VALUE } }
    visited[0][0] = true
    efforts[0][0] = 0

    fun getNeighbors(cell: IntArray): List<IntArray> {
        val row = cell[0]
        val col = cell[1]
        val neighbors = mutableListOf<IntArray>()
        val sides = arrayOf(intArrayOf(0,1), intArrayOf(1,0), intArrayOf(0,-1), intArrayOf(-1,0))

        for (side in sides) {
            val nRow = row + side[0]
            val nCol = col + side[1]
            if (nRow < 0 || nRow >= rows || nCol < 0 || nCol >= cols || visited[nRow][nCol]) continue
            neighbors.add(intArrayOf(nRow, nCol))
        }

        return neighbors
    }

    fun dfs(cell: IntArray) {
        val row = cell[0]
        val col = cell[1]
        visited[row][col] = true

        if (!cell.contentEquals(end)) {
            for (neighbor in getNeighbors(cell)) {
                val nRow = neighbor[0]
                val nCol = neighbor[1]
                val effort = abs(heights[row][col] - heights[nRow][nCol])
                val pathEffort = max(efforts[row][col], effort)
                if (pathEffort > efforts[rows-1][cols-1] || pathEffort >= efforts[nRow][nCol]) continue
                efforts[nRow][nCol] = pathEffort
            }

            for (neighbor in getNeighbors(cell)) {
                dfs(neighbor)
            }
        }

        visited[row][col] = false
    }

    dfs(start)

    return efforts[rows-1][cols-1]
}

fun minimumEffortPath(heights: Array<IntArray>): Int {
    val rows = heights.size
    val cols = heights[0].size
    val neighbors = arrayOf(intArrayOf(0,1), intArrayOf(1,0), intArrayOf(0,-1), intArrayOf(-1,0))
    val efforts = Array(rows) { IntArray(cols) { Int.MAX_VALUE } }
    efforts[0][0] = 0
    val pq = PriorityQueue<IntArray>(Comparator.comparingInt { it[0] }) // effort, row, col
    pq.add(intArrayOf(0,0,0))

    while (pq.isNotEmpty()) {
        val erc = pq.poll()
        val pathEffort = erc[0]; val row = erc[1]; val col = erc[2]
        if (pathEffort > efforts[row][col]) continue
        if (row == rows-1 && col == cols-1) break // is the end cell
        for (neighbor in neighbors) {
            val nRow = row + neighbor[0]; val nCol = col + neighbor[1]
            if (nRow < 0 || nRow >= rows || nCol < 0 || nCol >= cols) continue
            val newEffort = max(pathEffort, abs(heights[row][col] - heights[nRow][nCol]))
            if (newEffort < efforts[nRow][nCol]) {
                efforts[nRow][nCol] = newEffort
                pq.add(intArrayOf(newEffort, nRow, nCol))
            }
        }
    }

    return efforts[rows-1][cols-1]
}

class TimeMap {
    private val map = mutableMapOf<String, MutableMap<Int, String>>()

    fun set(key: String, value: String, timestamp: Int) { // O(1)
        if (map[key] == null) {
            map[key] = mutableMapOf()
        }
        map[key]!![timestamp] = value
    }

    fun get(key: String, timestamp: Int): String { // O(t)
        for (t in timestamp downTo 1) { // O(t)
            if (t in (map[key]?.keys ?: emptySet<Int>())) {
                return map[key]?.get(t) ?: ""
            }
        }
        return ""
    }

}

// n = number of accounts, k = number of emails in an account
/**
 * name not unique
 * only merge if name is equal
 * all emails are unique and can only belong to the right user
 * there could be two users with the same name but different emails
 *
 * [["mario", "e1@com", "e2@com"], ["mario", "e1@com", "e3@com"], ["pedro", "e4@com"], ["mario", "e5@com"]]
 *
 * ["mario", "e1@com", "e2@com"]
 * if name is the same & any of the emails appears on both -> merge
 *
 * name = mario
 *
 * emails = e1@com, e2@com, e3@com
 *
 *
 * e1 - e2
 * e1 - e3
 *
 * adjList
 * e1 - e2, e3
 * e2 - e1
 * e3 - e1
 * ------ below part is not in graph
 * e4
 * e5
 *
 * */
fun accountsMerge(accounts: List<List<String>>): List<List<String>> {
    val mergedAccounts = mutableListOf<MutableList<String>>()
    val graph = mutableMapOf<String, MutableList<String>>()
    val visitedEmails = mutableSetOf<String>()

    fun dfs(mergedAccount: MutableList<String>, email: String) {
        if (email in visitedEmails) return
        visitedEmails.add(email)
        mergedAccount.add(email)
        for (adjEmail in graph[email]!!) {
            dfs(mergedAccount, adjEmail)
        }
    }

    for (account in accounts) {
        val name = account[0]
        val emails = account.subList(1, account.size)
        val firstEmail = emails[0]

        if (emails.size == 1) {
            graph[firstEmail] = mutableListOf()
            continue
        }

        for (email in emails.subList(1, emails.size)) {
            if (graph[firstEmail] == null) graph[firstEmail] = mutableListOf()
            graph[firstEmail]!!.add(email)
            if (graph[email] == null) graph[email] = mutableListOf()
            graph[email]!!.add(firstEmail)
        }
    }

    for (account in accounts) {
        val name = account[0]
        val mergedAccount = mutableListOf<String>()
        val firstEmail = account[1]
        if (firstEmail in visitedEmails) continue

        dfs(mergedAccount, firstEmail)

        mergedAccount.sort()
        mergedAccount.add(0, name)
        mergedAccounts.add(mergedAccount)
    }

    return mergedAccounts
}

fun wordBreak(s: String, wordDict: List<String>): Boolean {
    val memo = mutableMapOf<String, Boolean>()

    fun helper(w: String): Boolean {
        if (w in memo) return memo[w]!!

        val newWords = mutableListOf<String>()
        for (word in wordDict) {
            if (word.length > w.length) continue
            if (word != w.substring(0, word.length)) continue
            val newWord = w.substring(word.length)
            if (newWord == "") return true
            newWords.add(newWord)
        }
        for (newWord in newWords) {
            memo[newWord] = helper(newWord)
            if (memo[newWord]!!) return true
        }
        return false
    }

    return helper(s)
}

fun wordBreakDP(s: String, wordDict: List<String>): Boolean {
    val f = BooleanArray(s.length+1)
    f[0] = true

    // First DP
//    for (i in 1 .. s.length) {
//        for (word in wordDict) {
//            if (word.length > i) continue
//            if (!f[i-word.length]) continue
//            val wordAfterLastCompletedWord = s.substring(i-word.length, i)
//            if (wordAfterLastCompletedWord == word) {
//                f[i] = true
//                break
//            }
//        }
//    }

    // Second DP
    for (i in 1 .. s.length) {
        for (j in 0 until i) {
            val word = s.substring(j, i)
            if (f[j] && wordDict.contains(word)) {
                f[i] = true
                break
            }
        }
    }

    return f[s.length]
}

fun canPartition(nums: IntArray): Boolean {
    var sum = nums.sum() // O(n)
    if (sum % 2 != 0) return false
    sum /= 2

    fun canPart(startIndex: Int, target: Int): Boolean {
        for (i in startIndex until nums.size) { // O(n | n-1 | n-2 | n-3)
            val num = nums[i]
            if (target-num == 0) return true
            if (target-num < 0) continue
            if (canPart(i+1, target-num)) return true // O(m)
        }
        return false
    }

    return canPart(0, sum)
}

fun canPartitionDP(nums: IntArray): Boolean {
    var sum = nums.sum()
    if (sum % 2 != 0) return false
    sum /= 2
    val sums = mutableSetOf(0, nums[0])
    for (i in 1 until nums.size) { // O(n)
        val num = nums[i]
        val tmpSums = mutableSetOf<Int>()
        for (t in sums) { // O(m)
            val target = t+num
            if (target == sum) return true
            tmpSums.add(target)
        }
        sums.addAll(tmpSums)
    }
    return false
}

private val convert = mapOf<Char, Int>(
    '0' to 0,
    '1' to 1,
    '2' to 2,
    '3' to 3,
    '4' to 4,
    '5' to 5,
    '6' to 6,
    '7' to 7,
    '8' to 8,
    '9' to 9
)

fun myAtoi(s: String): Int {
    var number: Double = 0.0

    var i = 0

    while (i < s.length && s[i] == ' ') i++

    val originalString = s.substring(i)
    i = 0
    val isPositive: Boolean

    if (originalString.length <= 1) return 0

    when {
        originalString[0] == '-' -> {
            isPositive = false
            i++
        }
        originalString[0] == '+' -> {
            isPositive = true
            i++
        }
        else -> isPositive = true
    }
    val start = i

    while (i < originalString.length && originalString[i].isDigit()) {
        i++
    }

    val numberString = originalString.substring(start, i)
    var units = 1.0

    for (j in numberString.length-1 downTo 0) {
        number += units * convert[numberString[j]]!!
        units *= 10
    }

    if (!isPositive) number *= -1

    return number.toInt()
}

fun spiralOrder2(matrix: Array<IntArray>): List<Int> {
    val rows = matrix.size
    val cols = matrix[0].size
    val spiral = mutableListOf<Int>(matrix[0][0])

    var row = 0
    var col = 0
    var rowTop = 0
    var colTop = 0
    var rowBot = 0
    var colBot = 0

    while (spiral.size < rows*cols) {
        while (++col < cols-colTop) {
            spiral.add(matrix[row][col])
        }
        col--
        rowBot++
        while (++row < rows-rowTop) {
            spiral.add(matrix[row][col])
        }
        row--
        colTop++
        while (--col >= colBot) {
            spiral.add(matrix[row][col])
        }
        col++
        rowTop++
        while (--row >= rowBot) {
            spiral.add(matrix[row][col])
        }
        row++
        colBot++
    }

    return spiral
}

fun spiralOrder(matrix: Array<IntArray>): List<Int> {
    val spiral = mutableListOf<Int>()

    fun rotate(mat: Array<IntArray>): Array<IntArray> {
        val rows = mat.size; val cols = mat[0].size
        val newMat = Array(cols) { IntArray(rows) }
        for (row in 0 until cols) {
            for (col in 0 until rows) {
                newMat[row][col] = mat[col][cols - 1 - row]
            }
        }
        return newMat
    }

    val m = matrix.size
    val n = matrix[0].size
    var ma = matrix
    while (spiral.size < m*n) {
        spiral.addAll(ma[0].toList())
        if (ma.size > 1) ma = rotate(ma.sliceArray(1 until ma.size))
    }

    return spiral
}

fun subsets(nums: IntArray): List<List<Int>> {
    val substs = mutableListOf(mutableListOf<Int>())

    for (num in  nums) {
        val size = substs.size
        for (i in 0 until size) {
            val subset = substs[i]
            val newSubset = mutableListOf<Int>()
            newSubset.addAll(subset)
            newSubset.add(num)
            substs.add(newSubset)
        }
    }

    return substs
}

fun uniquePathsBFS(m: Int, n: Int): Int {
    val paths = Array(m) { IntArray(n) }
    paths[0][0] = 1
    val visited = Array(m) { BooleanArray(n) }
    val queue = mutableListOf(intArrayOf(0,0))

    while (queue.isNotEmpty()) { // O(v)
        val cell = queue.removeAt(0)
        val row = cell[0]; val col = cell[1]
        if (col+1 < n) {
            paths[row][col+1] += paths[row][col]
            if (!visited[row][col+1]) queue.add(intArrayOf(row, col+1))
            visited[row][col+1] = true
        }
        if (row+1 < m) {
            paths[row+1][col] += paths[row][col]
            if (!visited[row+1][col]) queue.add(intArrayOf(row+1, col))
            visited[row+1][col] = true
        }
    }

    return paths[m-1][n-1]
}

fun uniquePaths(rows: Int, cols: Int): Int {
    val dp = IntArray(cols)
    dp[0] = 1

    for (i in 0 until rows) {
        for (j in 1 until cols) {
            dp[j] += dp[j-1]
        }
    }

    return dp.last()
}

fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {
    var parentIndex = 0

    fun buildSubTree(subTree: IntArray): TreeNode? {
        if (subTree.isEmpty()) return null
        val node = TreeNode(preorder[parentIndex])
        parentIndex++
        var mid = 0
        while (subTree[mid] != node.`val`) mid++

        node.left = buildSubTree(subTree.sliceArray(0 until mid))
        node.right = buildSubTree(subTree.sliceArray(mid+1 .. subTree.lastIndex))

        return node
    }

    return buildSubTree(inorder)
}

fun letterCombinations(digits: String): List<String> {
    val digitsMapping = mapOf(
        '2' to listOf('a','b','c'),
        '3' to listOf('d','e','f'),
        '4' to listOf('g','h','i'),
        '5' to listOf('j','k','l'),
        '6' to listOf('m','n','o'),
        '7' to listOf('p','q','r','s'),
        '8' to listOf('t','u','v'),
        '9' to listOf('w','x','y','z')
    )

    val combinations = digitsMapping[digits[0]]!!.map { m -> m.toString() }.toMutableList()

    for (j in 1 until digits.length) {
        val digit = digits[j] // 2 | 3
        val mappings = digitsMapping[digit]!! // ['a','b','c'] | [def]

        val combinationsSize = combinations.size
        for (i in 0 until combinationsSize) {
            val combination = combinations[i]
            for (mapping in mappings) {
                combinations.add(combination + mapping)
            }
        }
        for (i in 0 until combinationsSize) {
            combinations.removeAt(0)
        }
    }

    return combinations
}

/**
 * s = cbaebabacd
 * p = abc
 *
 * [cba] does this one contains all the characters? Y -> add starting index to out
 * [bae]
 *
 * count = { c:1, b:1, a:1 } start=0 end=2 Y
 * rest 1 from start index and continue from next index
 * count = { b:1, a:1, e:1 } start=1 end=3 N
 *
 * count the characters
 *
 */
fun findAnagrams(s: String, p: String): List<Int> { // O(O(s)*O(p))
    if (p.length > s.length) return emptyList()

    fun getCode(c: Char): Int {
        return c.code-97
    }

    val anagrams = mutableListOf<Int>()
    val anagramSize = p.length
    val count = IntArray(26)

    for (c in p) count[getCode(c)]--
    for (i in 0 until anagramSize-1) count[getCode(s[i])]++

    for (i in 0 .. s.length-anagramSize) {
        count[getCode(s[i+anagramSize-1])]++
        if (count.sumOf { n -> abs(n) } == 0) {
            anagrams.add(i)
        }
        count[getCode(s[i])]--
    }

    return anagrams
}

fun findMinHeightTreesFull(n: Int, edges: Array<IntArray>): List<Int> {
    val tree = mutableMapOf<Int, MutableList<Int>>()
    var minimumTreeHeight = Int.MAX_VALUE
    val minHeightTrees = mutableListOf<Int>()

    if (edges.isEmpty()) return listOf(0)

    for (edge in edges) {
        if (tree[edge[0]] == null) tree[edge[0]] = mutableListOf<Int>()
        tree[edge[0]]!!.add(edge[1])

        if (tree[edge[1]] == null) tree[edge[1]] = mutableListOf<Int>()
        tree[edge[1]]!!.add(edge[0])
    }

    fun getTreeHeight(root: Int, height: Int, visited: MutableSet<Int>): Int {
        var maxHeight = height
        visited.add(root)

        for (neighbor in tree[root]!!) {
            if (neighbor in visited) continue
            maxHeight = max(maxHeight, getTreeHeight(neighbor, height+1, visited))
        }

        return maxHeight
    }

    for ((node, neighbors) in tree) {
        val height = getTreeHeight(node, 0, mutableSetOf<Int>())
        if (height == minimumTreeHeight) {
            minHeightTrees.add(node)
        } else if (height < minimumTreeHeight) {
            minimumTreeHeight = height
            minHeightTrees.clear()
            minHeightTrees.add(node)
        }
    }

    return minHeightTrees
}

fun findMinHeightTrees(n: Int, edges: Array<IntArray>): List<Int> {
    val graph = Array(n) { mutableListOf<Int>() }
    val ranks = IntArray(n)

    if (edges.isEmpty()) return listOf(0)

    for (edge in edges) {
        graph[edge[0]].add(edge[1])
        graph[edge[1]].add(edge[0])
        ranks[edge[0]]++
        ranks[edge[1]]++
    }

    val queue = mutableListOf<Int>()

    for (node in ranks.indices) {
        if (ranks[node] == 1) queue.add(node)
    }

    while (ranks.any { r -> r > 1 } || queue.size > 2) {
        val nodesInLevel = queue.size
        for (i in 0 until nodesInLevel) {
            val node = queue.removeAt(0)
            for (neighbor in graph[node]) {
                ranks[neighbor]--
                if (ranks[neighbor] == 0) continue
                if (ranks[neighbor] == 1) {
                    queue.add(neighbor)
                    continue
                }
            }
        }
    }

    return queue.toList()
}

class LRUCache(private val capacity: Int) {
    private val map = mutableMapOf<Int, LRUNode>()
    private var head: LRUNode = LRUNode(0, 0)
    private var tail: LRUNode = LRUNode(0, 0)

    data class LRUNode(val key: Int, var value: Int) {
        var prev: LRUNode? = null
        var next: LRUNode? = null
    }

    private fun remove(node: LRUNode) {
        val prev = node.prev
        val next = node.next
        if (prev != null) prev.next = next
        if (next != null) next.prev = prev
    }

    private fun add(node: LRUNode) {
        val prev = tail.prev
        if (prev != null) {
            prev.next = node
        }
        tail.prev = node
        node.prev = prev
        node.next = tail
    }

    fun get(key: Int): Int {
        return if (key in map) {
            val node = map[key]!!
            this.remove(node)
            this.add(node)
            node.value
        } else {
            -1
        }
    }

    fun put(key: Int, value: Int) {
        if (key in map) {
            this.remove(map[key]!!)
        }
        var node = LRUNode(key, value)
        this.add(node)
        map[key] = node
        if (map.keys.size > capacity) {
            node = head.next ?: LRUNode(0,0)
            this.remove(node)
            map.remove(node.key)
        }
    }
}

fun verticalTraversal(root: TreeNode): List<List<Int>> {
    val groups = mutableMapOf<Int, MutableList<IntArray>>()
    var minCol = 0
    var maxCol = 0

    fun traversePreOrder(node: TreeNode, row: Int, col: Int) {
        minCol = min(minCol, col)
        maxCol = max(maxCol, col)
        if (groups[col] == null) groups[col] = mutableListOf<IntArray>()
        groups[col]!!.add(intArrayOf(row, node.`val`))

        if (node.left != null) traversePreOrder(node.left!!, row+1, col-1)
        if (node.right != null) traversePreOrder(node.right!!, row+1, col+1)
    }

    traversePreOrder(root, 0, 0)

    val result = mutableListOf<List<Int>>()

    for (i in minCol .. maxCol) {
        groups[i]!!.sortWith(compareBy<IntArray> { it.first() }.thenBy { it.last() })
        result.add(groups[i]!!.map { it.last() })
    }

    return result
}

fun kthSmallest(root: TreeNode?, k: Int): Int {
    var i = 0
    var first = true

    fun smallest(node: TreeNode): Int {
        if (node.left != null) {
            val result = smallest(node.left!!)
            if (result != -1) return result
        }

        if (first) i = 1
        first = false
        if (i == k) return node.`val` else i++

        if (node.right != null) {
            val result = smallest(node.right!!)
            if (result != -1) return result
        }

        return -1
    }

    return smallest(root!!)
}

/**
 * #33 Search in Rotated Sorted Array
 *
 * find pivot
 * do a bsearch on the left
 * do a bsearch on the right
 *
 * return index or -1
 */
fun search(nums: IntArray, target: Int): Int {
    // Returns the index of the pivot or null if the array is not rotated
    fun getPivot(): Int? {
        var start = 0
        var end = nums.size-1
        var mid = 0

        if (nums[start] < nums[end]) return null

        while (start < end) {
            mid = (end-start)/2 + start
            if (nums[start] < nums[mid]) {
                start = mid
            } else {
                end = mid
            }
        }

        return mid
    }

    // returns the target index or -1 if not found
    fun bsearch(start: Int, end: Int): Int {
        val mid = (end-start)/2 + start
        if(mid < 0 || mid >= nums.size) return -1
        if(nums[mid] == target) return mid
        if (start >= end) return -1

        if(nums[mid] > target) {
            val left = bsearch(start, mid)
            if(left != -1) return left
        } else {
            return bsearch(mid + 1, end)
        }

        return  -1
    }

    val start = 0
    val end = nums.size-1
    if (end < 0) return -1
    val pivot = getPivot() ?: return bsearch(start, end)

    val leftResult = bsearch(start, pivot)
    if (leftResult != -1) return leftResult
    return bsearch(pivot+1, end)
}

/**
 * DP
 *  left-right && top-bottom
 *  right-left && bottom-top
 */
fun updateMatrix(mat: Array<IntArray>): Array<IntArray> {
    val rows = mat.size
    val cols = mat[0].size
    val MAX_DISTANCE = rows + cols
    val distances: Array<IntArray> = Array(rows) { row -> IntArray(cols) { col -> if(mat[row][col] == 0) 0 else MAX_DISTANCE } }
    val directions = setOf("left-right", "right-left")

    fun updateDistances(direction: String) {
        val rangeRows:IntProgression
        val rangeCols: IntProgression
        val dir: Int

        when(direction) {
            "left-right" -> { rangeRows = 0 until rows; rangeCols = 0 until cols; dir=1 }
            "right-left" -> { rangeRows = rows-1 downTo 0; rangeCols = cols-1 downTo 0; dir=-1 }
            else -> throw Exception("Invalid direction")
        }

        for (row in rangeRows) {
            for (col in rangeCols) {
                if(mat[row][col] == 0) continue
                if (col+dir in rangeCols) distances[row][col] = min(distances[row][col], distances[row][col+dir]+1)
                if (row+dir in rangeRows) distances[row][col] = min(distances[row][col], distances[row+dir][col]+1)
            }
        }
    }

    for (direction in directions) {
        updateDistances(direction)
    }

    return distances
}

/**
 * start 0
 * end last
 *
 * while start < end
 *
 *   mid = (start + end) / 2
 *
 *   is nums(mid) == target
 *    Y - return mid
 *
 *   left array is sorted?
 *    Y - is target in this range?
 *        update end = mid-1
 *    N - search in the other half
 *   right array is sorted?
 *    Y - is target in this range?
 *        update start = mid+1
 *    N - search in the other half
 *
 *
 * return -1
 */
fun searchInRotatedArray(nums: IntArray, target: Int): Int {
    var start = 0
    var end = nums.lastIndex

    while (start <= end) {
        val mid = (start + end) / 2

        if (nums[mid] == target) return mid

        if (nums[start] < nums[mid]) {
            if (mid-1 >= 0 && target in nums[start] .. nums[mid-1]) {
                end = mid - 1
            } else {
                start = mid + 1
            }
        } else {
            if (mid+1 <= nums.lastIndex && target in nums[mid+1] .. nums[end]) {
                start = mid + 1
            } else {
                end = mid - 1
            }
        }
    }

    return -1
}

enum class Oceans {
    None,
    Pacific,
    Atlantic,
    Both
}

/**
 * do bfs for pacific
 * do bfs for atlantic
 * merge both
 */
fun pacificAtlantic(heights: Array<IntArray>): List<List<Int>> {
    val rows = heights.size
    val cols = heights[0].size
    val oceansTouched = Array(rows) { Array(cols) { Oceans.None } }
    val neighbors = arrayOf(Pair(0,1), Pair(1,0), Pair(0,-1), Pair(-1,0))
    val visited = Array(rows) { Array(cols) { false } }
    val pacific = mutableListOf<Pair<Int, Int>>()
    val atlantic = mutableListOf<Pair<Int, Int>>()

    fun bfs(row: Int, col: Int, ocean: Oceans, queue: MutableList<Pair<Int, Int>>) {
        when (oceansTouched[row][col]) {
            Oceans.Both -> Unit
            Oceans.Pacific -> {
                if (ocean == Oceans.Atlantic) oceansTouched[row][col] = Oceans.Both
            }
            Oceans.Atlantic -> {
                if (ocean == Oceans.Pacific) oceansTouched[row][col] = Oceans.Both
            }
            Oceans.None -> oceansTouched[row][col] = ocean
        }

        for (neighbor in neighbors) {
            val nRow = row + neighbor.first
            val nCol = col + neighbor.second
            if (nRow !in 0 until rows || nCol !in 0 until cols) continue
            if (visited[nRow][nCol]) continue
            if (heights[row][col] > heights[nRow][nCol]) continue
            visited[nRow][nCol] = true
            queue.add(Pair(nRow, nCol))
        }
    }

    for (row in 0 until rows) {
        pacific.add(Pair(row,0))
        visited[row][0] = true
    }
    for (col in 0 until cols) {
        pacific.add(Pair(0, col))
        visited[0][col] = true
    }

    while (pacific.isNotEmpty()) {
        val size = pacific.size
        for (i in 0 until size) {
            val cell = pacific.removeAt(0)
            val row = cell.first
            val col = cell.second
            bfs(row, col, Oceans.Pacific, pacific)
        }
    }

    for (row in 0 until rows) {
        for (col in 0 until cols) {
            visited[row][col] = false
        }
    }

    for (row in 0 until rows) {
        atlantic.add(Pair(row, cols-1))
        visited[row][cols-1] = true
    }
    for (col in 0 until cols) {
        atlantic.add(Pair(rows-1, col))
        visited[rows-1][col] = true
    }

    while (atlantic.isNotEmpty()) {
        val size = atlantic.size
        for (i in 0 until size) {
            val cell = atlantic.removeAt(0)
            val row = cell.first
            val col = cell.second
            bfs(row, col, Oceans.Atlantic, atlantic)
        }
    }

    val result = mutableListOf<List<Int>>()
    for (row in 0 until rows) {
        for (col in 0 until cols) {
            if (oceansTouched[row][col] == Oceans.Both) result.add(listOf(row, col))
        }
    }

    return result
}

fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
    val dummy = ListNode(0)
    dummy.next = head
    var first: ListNode? = dummy
    var second: ListNode? = dummy

    for (i in 0..n) {
        first = first!!.next
    }

    while (first != null) {
        first = first.next
        second = second!!.next
    }

    second!!.next = second.next!!.next
    return dummy.next
}

fun mergeTwoListsIterative(list1: ListNode?, list2: ListNode?): ListNode? {
    var li1 = list1
    var li2 = list2

    val head = ListNode(-1)
    var tail: ListNode? = head

    while (li1 != null && li2 != null) {
        if (li1.`val` <= li2.`val`) {
            tail?.next = li1
            li1 = li1.next
        } else {
            tail?.next = li2
            li2 = li2.next
        }
        tail = tail?.next
    }

    if (li1 == null) {
        tail?.next = li2
    } else {
        tail?.next = li1
    }

    return head.next
}










