import kotlin.random.Random

fun directedGraphHasCycles(graph: List<TextNode>): Boolean {
    fun dfs(node: TextNode, startNode: TextNode, visited: MutableSet<TextNode>): Boolean {
        if (node in visited && node == startNode) return true
        if (node in node.neighbors) return true
        visited.add(node)
        for (neighbor in node.neighbors) {
            if (dfs(neighbor, startNode, visited)) return true
        }
        return false
    }

    for (node in graph) {
        if(dfs(node, node, mutableSetOf())) return true
    }

    return false
}

class RandomizedSet() {

    private val elems = mutableListOf<Int>()
    private val dict = mutableMapOf<Int,Int>() // value, index

    fun insert(`val`: Int): Boolean {
        if (dict.contains(`val`)) {
            return false
        }

        elems.add(`val`)
        dict[`val`] = elems.lastIndex

        return true
    }

    fun remove(`val`: Int): Boolean {
        if (`val` !in dict) {
            return false
        }

        val i = dict[`val`]
        elems[i!!] = elems[elems.lastIndex]
        dict[elems[i]] = i
        elems.removeAt(elems.lastIndex)
        dict.remove(`val`)

        return true
    }

    fun getRandom(): Int {
        return elems[Random.nextInt(elems.size)]
    }

}