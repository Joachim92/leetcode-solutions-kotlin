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