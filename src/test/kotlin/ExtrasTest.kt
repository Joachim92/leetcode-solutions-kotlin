import org.junit.jupiter.api.Assertions.assertFalse
import kotlin.test.Test
import kotlin.test.assertTrue

internal class ExtrasTest {

    @Test
    fun directedGraphHasCyclesFalseTest() {
        val nodeA = TextNode("a")
        val nodeB = TextNode("b")
        val nodeC = TextNode("c")
        val nodeD = TextNode("d")

        nodeA.neighbors.addAll(listOf(nodeB, nodeC, nodeD))
        nodeB.neighbors.add(nodeD)

        val graph = listOf(nodeA, nodeB, nodeC, nodeD)
        assertFalse(directedGraphHasCycles(graph = graph))
    }

    @Test
    fun directedGraphHasCyclesTrueTest() {
        val nodeA = TextNode("a")
        val nodeB = TextNode("b")

        nodeA.neighbors.add(nodeB)
        nodeB.neighbors.add(nodeA)

        val graph = listOf(nodeA, nodeB)
        assertTrue(directedGraphHasCycles(graph = graph))
    }

    @Test
    fun directedGraphHasCyclesSelfTest() {
        val nodeA = TextNode("a")
        val nodeB = TextNode("b")
        val nodeC = TextNode("c")
        val nodeD = TextNode("d")

        nodeA.neighbors.addAll(listOf(nodeB, nodeD))
        nodeB.neighbors.add(nodeB)
        nodeC.neighbors.add(nodeA)

        val graph = listOf(nodeA, nodeB, nodeC, nodeD)
        assertTrue(directedGraphHasCycles(graph = graph))
    }

    @Test
    fun directedGraphHasCyclesFalseNotStartTest() {
        val nodeA = TextNode("a")
        val nodeB = TextNode("b")
        val nodeC = TextNode("c")
        val nodeD = TextNode("d")
        val nodeE = TextNode("e")

        nodeA.neighbors.add(nodeB)
        nodeC.neighbors.addAll(listOf(nodeB, nodeD))
        nodeD.neighbors.add(nodeE)
        nodeE.neighbors.add(nodeC)

        val graph = listOf(nodeA, nodeB, nodeC, nodeD)
        assertTrue(directedGraphHasCycles(graph = graph))
    }
}
