import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class MainKtTest {

    @Test
    fun isValidBSTMaxTest() {
        val root = TreeNode(3)
        root.left = TreeNode(1)
        root.right = TreeNode(5)
        root.left?.left = TreeNode(0)
        root.left?.right = TreeNode(2)
        root.right?.left = TreeNode(4)
        root.right?.right = TreeNode(6)
        assertTrue(isValidBSTMax(root, null, null))
    }

    @Test
    fun `maximumUniqueSub-arrayQuadraticTest`() {
        assertEquals(6, maximumUniqueSubArrayQuadratic(intArrayOf(1, 2, 3)))
    }

    @Test
    fun `maximumUniqueSub-arrayTest`() {
        assertEquals(6, maximumUniqueSubArray(intArrayOf(1, 2, 3)))
    }

    @Test
    fun generateMatrixTest() {
        val expectedMatrix = arrayOf(
            intArrayOf(1,2,3,4,5),
            intArrayOf(16,17,18,19,6),
            intArrayOf(15,24,25,20,7),
            intArrayOf(14,23,22,21,8),
            intArrayOf(13,12,11,10,9),
        )
        val generatedMatrix = generateMatrix(5)

        for (row in expectedMatrix.indices) {
            for (col in expectedMatrix[row].indices) {
                assertEquals(expectedMatrix[row][col], generatedMatrix[row][col])
            }
        }
    }

    @Test
    fun lengthOfLongestSubstringTest() {
        assertEquals(5, lengthOfLongestSubstring("truck"))
    }

    @Test
    fun maxDepthTest() {
        val root = TreeNode(3)
        root.left = TreeNode(1)
        root.right = TreeNode(5)
        root.left?.left = TreeNode(0)
        root.left?.right = TreeNode(2)
        root.right?.left = TreeNode(4)
        root.right?.right = TreeNode(6)
        assertEquals(3, maxDepth(root))
    }

    @Test
    fun isValidBSTTest() {
        val root = TreeNode(3)
        root.left = TreeNode(1)
        root.right = TreeNode(5)
        root.left?.left = TreeNode(0)
        root.left?.right = TreeNode(2)
        root.right?.left = TreeNode(4)
        root.right?.right = TreeNode(6)

        assertTrue(isValidBST(root))
    }

    @Test
    fun numOfIslandsTest() {
        val grid = arrayOf(
            charArrayOf('1','1','1','1','0'),
            charArrayOf('1','1','0','1','0'),
            charArrayOf('1','1','0','0','0'),
            charArrayOf('0','0','0','0','0')
        )

        val grid2 = emptyArray<CharArray>()

        val grid3 = arrayOf(
            charArrayOf('1','1','1','1','0'),
            charArrayOf('0','0','0','1','0'),
            charArrayOf('1','1','0','0','1'),
            charArrayOf('0','0','0','0','0')
        )

        assertEquals(1, numOfIslands(grid))
        assertEquals(0, numOfIslands(grid2))
        assertEquals(3, numOfIslands(grid3))
    }

    @Test
    fun installDependenciesTest() {
        val adjList = arrayOf(
            arrayOf(1, 3),
            arrayOf(3),
            arrayOf(4),
            emptyArray(),
            emptyArray(),
            arrayOf(0)
        )
        assertArrayEquals(arrayOf(3, 1, 0), installDependencies(adjList, 0))
        assertArrayEquals(arrayOf(3, 1), installDependencies(adjList, 1))
        assertArrayEquals(arrayOf(4, 2), installDependencies(adjList, 2))
        assertArrayEquals(arrayOf(3), installDependencies(adjList, 3))
        assertArrayEquals(arrayOf(4), installDependencies(adjList, 4))
        assertArrayEquals(arrayOf(3, 1, 0, 5), installDependencies(adjList, 5))
    }

    @Test
    fun findRedundantConnectionTest() {
        val edges = arrayOf(intArrayOf(1,2), intArrayOf(1,3), intArrayOf(2,3))
        assertArrayEquals(intArrayOf(2,3), findRedundantConnection(edges))

        val edges2 = arrayOf(intArrayOf(1,2), intArrayOf(2,3), intArrayOf(3,4), intArrayOf(1,4), intArrayOf(1,5))
        assertArrayEquals(intArrayOf(1,4), findRedundantConnection(edges2))

        val edges3 = arrayOf(intArrayOf(1,3), intArrayOf(3,4), intArrayOf(1,5), intArrayOf(3,5), intArrayOf(2,3))
        assertArrayEquals(intArrayOf(3,5), findRedundantConnection(edges3))
    }

    @Test
    fun isCyclicTest() {
        val graph = arrayOf(intArrayOf(1,3,4),intArrayOf(0,2),intArrayOf(1,3),intArrayOf(0,2),intArrayOf(0))
        assertTrue(isCyclicUndirected(graph))

        val graph2 = arrayOf(intArrayOf(1,3),intArrayOf(0,2),intArrayOf(1,3),intArrayOf(2,0))
        assertTrue(isCyclicUndirected(graph2))

        val graph3 = arrayOf(intArrayOf(1,2),intArrayOf(0,3,4),intArrayOf(0,5,6),intArrayOf(1),intArrayOf(1),
            intArrayOf(2,6), intArrayOf(2,5)
        )
        assertTrue(isCyclicUndirected(graph3))

        val graph4 = arrayOf(intArrayOf(1,4),intArrayOf(0,2),intArrayOf(1,3),intArrayOf(2),intArrayOf(0))
        assertFalse(isCyclicUndirected(graph4))

        val graph5 = arrayOf(intArrayOf(1,2),intArrayOf(0,3,4),intArrayOf(0,5,6),intArrayOf(1),intArrayOf(1),
            intArrayOf(2), intArrayOf(2)
        )
        assertFalse(isCyclicUndirected(graph5))
    }

    @Test
    fun isCyclicDirectedTest() {
        val graph = arrayOf(intArrayOf(0))
        assertTrue(isCyclicDirected(graph))

        val graph2 = arrayOf(intArrayOf(1),intArrayOf(2),intArrayOf(0))
        assertTrue(isCyclicDirected(graph2))

        val graph3 = arrayOf(intArrayOf(1,2),intArrayOf(3),intArrayOf(4),intArrayOf(),intArrayOf())
        assertFalse(isCyclicDirected(graph3))
    }

    @Test
    fun highestRankedKItemsTest() {
        val expected = highestRankedKItems(arrayOf(intArrayOf(1,2,0,1), intArrayOf(1,3,0,1), intArrayOf(0,2,5,1)), intArrayOf(2,5), intArrayOf(0,0), 3)
        assertArrayEquals(arrayOf(intArrayOf(0,1), intArrayOf(1,1), intArrayOf(2,1)), expected.map { it.toIntArray() }.toTypedArray())

        val expected2 = highestRankedKItems(arrayOf(intArrayOf(1,2,0,1), intArrayOf(1,3,3,1), intArrayOf(0,2,5,1)), intArrayOf(2,3), intArrayOf(2,3), 2)
        assertArrayEquals(arrayOf(intArrayOf(2,1), intArrayOf(1,2)), expected2.map { it.toIntArray() }.toTypedArray())

        val expected3 = highestRankedKItems(arrayOf(intArrayOf(1,1,1), intArrayOf(0,0,1), intArrayOf(2,3,4)), intArrayOf(2,3), intArrayOf(0,0), 3)
        assertArrayEquals(arrayOf(intArrayOf(2,1), intArrayOf(2,0)), expected3.map { it.toIntArray() }.toTypedArray())

        val expected4 = highestRankedKItems(arrayOf(intArrayOf(0,2,0)), intArrayOf(2,2), intArrayOf(0,1), 1)
        assertArrayEquals(arrayOf(intArrayOf(0,1)), expected4.map { it.toIntArray() }.toTypedArray())

        val expected5 = highestRankedKItems(arrayOf(intArrayOf(2,0,2), intArrayOf(4,5,3), intArrayOf(2,0,2)), intArrayOf(2,5), intArrayOf(1,1), 9)
        assertArrayEquals(arrayOf(intArrayOf(1,1), intArrayOf(1,2), intArrayOf(1,0), intArrayOf(0,0), intArrayOf(0,2), intArrayOf(2,0), intArrayOf(2,2)), expected5.map { it.toIntArray() }.toTypedArray())
    }

    @Test
    fun minWindowTest() {
        assertEquals("BANC", minWindow("ADOBECODEBANC", "ABC"))
        assertEquals("a", minWindow("a", "a"))
        assertEquals("", minWindow("a", "aa"))
        assertEquals("aa", minWindow("aa", "aa"))
    }

    @Test
    fun isEvenOrOddTest() {
        assertEquals("odd", isEvenOrOdd(listOf(1)))
        assertEquals("even",isEvenOrOdd(listOf(1,2)))
        assertEquals("odd",isEvenOrOdd(listOf(1,2,3)))
        assertEquals("even",isEvenOrOdd(listOf(1,2,3,4)))
        assertEquals("odd",isEvenOrOdd(listOf(1,2,3,4,5)))
    }

    @Test
    fun findRotationTest() {
        val m1 = arrayOf(intArrayOf(0,1), intArrayOf(1,0))
        val m2 = arrayOf(intArrayOf(1,0), intArrayOf(0,1))
        assertTrue(findRotation(m1, m2))
    }

    @Test
    fun generatePermutationsTest() {
        assertEquals(1, generatePermutations(listOf(1)).size)
        assertEquals(2, generatePermutations(listOf(1,2)).size)
        assertEquals(6, generatePermutations(listOf(1,2,3)).size)
        assertEquals(24, generatePermutations(listOf('a','b','c','d')).size)
        assertEquals(120, generatePermutations(listOf('a','b','c','d','e')).size)
        assertEquals(720, generatePermutations("((()))".toList()).size)
    }

    @Test
    fun getRightNodesOfTreeTest() {
        val root = TreeNode(0)
        root.left = TreeNode(1)
        root.right = TreeNode(2)
        root.left?.left = TreeNode(3)
        root.left?.right = TreeNode(4)
        root.right?.left = TreeNode(5)
        root.right?.right = TreeNode(6)
        assertArrayEquals(intArrayOf(0,1,2,3,4,5,6), traverseTreeByLevel(root))
        assertArrayEquals(intArrayOf(0,2,6), getRightNodesOfTree(root))
    }

    @Test
    fun replaceWordsTest() {
        val result = replaceWords(listOf("cat","bat","rat"), "the cattle was rattled by the battery")
        val result2 = replaceWords(listOf("catt","cat","bat","rat"), "the cattle was rattled by the battery")
        val result3 = replaceWords(
            listOf("e","k","c","harqp","h","gsafc","vn","lqp","soy","mr","x","iitgm","sb","oo","spj","gwmly","iu","z","f","ha","vds","v","vpx","fir","t","xo","apifm","tlznm","kkv","nxyud","j","qp","omn","zoxp","mutu","i","nxth","dwuer","sadl","pv","w","mding","mubem","xsmwc","vl","farov","twfmq","ljhmr","q","bbzs","kd","kwc","a","buq","sm","yi","nypa","xwz","si","amqx","iy","eb","qvgt","twy","rf","dc","utt","mxjfu","hm","trz","lzh","lref","qbx","fmemr","gil","go","qggh","uud","trnhf","gels","dfdq","qzkx","qxw"),
            "ikkbp miszkays wqjferqoxjwvbieyk gvcfldkiavww vhokchxz dvypwyb bxahfzcfanteibiltins ueebf lqhflvwxksi dco kddxmckhvqifbuzkhstp wc ytzzlm gximjuhzfdjuamhsu gdkbmhpnvy ifvifheoxqlbosfww mengfdydekwttkhbzenk wjhmmyltmeufqvcpcxg hthcuovils ldipovluo aiprogn nusquzpmnogtjkklfhta klxvvlvyh nxzgnrveghc mpppfhzjkbucv cqcft uwmahhqradjtf iaaasabqqzmbcig zcpvpyypsmodtoiif qjuiqtfhzcpnmtk yzfragcextvx ivnvgkaqs iplazv jurtsyh gzixfeugj rnukjgtjpim hscyhgoru aledyrmzwhsz xbahcwfwm hzd ygelddphxnbh rvjxtlqfnlmwdoezh zawfkko iwhkcddxgpqtdrjrcv bbfj mhs nenrqfkbf spfpazr wrkjiwyf cw dtd cqibzmuuhukwylrnld dtaxhddidfwqs bgnnoxgyynol hg dijhrrpnwjlju muzzrrsypzgwvblf zbugltrnyzbg hktdviastoireyiqf qvufxgcixvhrjqtna ipfzhuvgo daee r nlipyfszvxlwqw yoq dewpgtcrzausqwhh qzsaobsghgm ichlpsjlsrwzhbyfhm ksenb bqprarpgnyemzwifqzz oai pnqottd nygesjtlpala qmxixtooxtbrzyorn gyvukjpc s mxhlkdaycskj uvwmerplaibeknltuvd ocnn frotscysdyclrc ckcttaceuuxzcghw pxbd oklwhcppuziixpvihihp"
        )

        assertEquals("the cat was rat by the bat", result)
        assertEquals("the cat was rat by the bat", result2)
        assertEquals("i miszkays w gvcfldkiavww v dvypwyb bxahfzcfanteibiltins ueebf lqhflvwxksi dc k w ytzzlm gximjuhzfdjuamhsu gdkbmhpnvy i mengfdydekwttkhbzenk w h ldipovluo a nusquzpmnogtjkklfhta k nxzgnrveghc mpppfhzjkbucv c uwmahhqradjtf i z q yzfragcextvx i i j gzixfeugj rnukjgtjpim h a x h ygelddphxnbh rvjxtlqfnlmwdoezh z i bbfj mhs nenrqfkbf spfpazr w c dtd c dtaxhddidfwqs bgnnoxgyynol h dijhrrpnwjlju muzzrrsypzgwvblf z h q i daee r nlipyfszvxlwqw yoq dewpgtcrzausqwhh q i k bqprarpgnyemzwifqzz oai pnqottd nygesjtlpala q gyvukjpc s mxhlkdaycskj uvwmerplaibeknltuvd ocnn f c pxbd oklwhcppuziixpvihihp", result3)
    }

    @Test
    fun diameterOfBinaryTreeTest() {
        val root = TreeNode(1)
        root.left = TreeNode(2)
        root.right = TreeNode(3)
        root.left?.left = TreeNode(4)
        root.left?.right = TreeNode(5)
        assertEquals(3, diameterOfBinaryTree(root))
    }

    @Test
    fun largestPerfectSetLengthTest() {
        assertEquals(-1, largestPerfectSetLength(intArrayOf()))
        assertEquals(2, largestPerfectSetLength(intArrayOf(3,9)))
        assertEquals(-1, largestPerfectSetLength(intArrayOf(3,5,8,1)))
        assertEquals(3, largestPerfectSetLength(intArrayOf(3,9,4,2,16)))
        assertEquals(4, largestPerfectSetLength(intArrayOf(2, 2*2, 5, 9, 5*5, 9*9, 81*81, 25*25, 6561*6561)))
    }

    @Test
    fun insertTest() {
        assertArrayEquals(arrayOf(intArrayOf(1,2), intArrayOf(3,10), intArrayOf(12,16)), insert(arrayOf(intArrayOf(1,2), intArrayOf(3,5), intArrayOf(6,7), intArrayOf(8,10), intArrayOf(12,16)), intArrayOf(4,8)))
        assertArrayEquals(arrayOf(intArrayOf(1,5)), insert(arrayOf(intArrayOf(1,5)), intArrayOf(2,3)))
    }

    @Test
    fun updateMatrixTest() {
        val original = arrayOf(intArrayOf(0,0,0), intArrayOf(0,1,0), intArrayOf(1,1,1))
        val expected = arrayOf(intArrayOf(0,0,0), intArrayOf(0,1,0), intArrayOf(1,2,1))
        val ans = updateMatrix(original)

        for (row in ans.indices) {
            for (col in ans[row].indices) {
                assertEquals(expected[row][col], ans[row][col])
            }
        }
    }

    @Test
    fun kClosestTest() {
        val expected = arrayOf(intArrayOf(3,3), intArrayOf(-2,4))
        val actual = kClosest(arrayOf(intArrayOf(3,3), intArrayOf(5,-1), intArrayOf(-2,4)), 2)

        assertTrue(expected.size == actual.size)
        for (i in expected.indices) {
            assertArrayEquals(expected[i], actual[i])
        }
    }

    @Test
    fun coinChangeTest() {
        assertEquals(3, coinChange(intArrayOf(1,5,2), 11))
        assertEquals(-1, coinChange(intArrayOf(2), 3))
        assertEquals(0, coinChange(intArrayOf(1), 0))
        assertEquals(20, coinChange(intArrayOf(1,2,5), 100))
        assertEquals(20, coinChange(intArrayOf(186,419,83,408), 6249))
    }

    @Test
    fun numOfPathsTest() {
        val labyrinth = arrayOf(
            intArrayOf(0,0,0,0,0,0,0,0),
            intArrayOf(0,0,1,0,0,0,1,0),
            intArrayOf(0,0,0,0,1,0,0,0),
            intArrayOf(1,0,1,0,0,1,0,0),
            intArrayOf(0,0,1,0,0,0,0,0),
            intArrayOf(0,0,0,1,1,0,1,0),
            intArrayOf(0,1,0,0,0,1,0,0),
            intArrayOf(0,0,0,0,0,0,0,0)
        )
        assertEquals(27, numOfPaths(labyrinth))
    }

    @Test
    fun fibonacciTest() {
        assertEquals(1, fibonacci(0))
        assertEquals(1, fibonacci(1))
        assertEquals(2, fibonacci(2))
        assertEquals(3, fibonacci(3))
        assertEquals(5, fibonacci(4))
        assertEquals(8, fibonacci(5))
        assertEquals(13, fibonacci(6))
        assertEquals(21, fibonacci(7))
    }

    @Test
    fun numOfWaysTest() {
        assertEquals(6, numOfWays(5, intArrayOf(1,3,4)))
    }

    @Test
    fun productExceptSelfTet() {
        assertArrayEquals(intArrayOf(24,12,8,6), productExceptSelf(intArrayOf(1,2,3,4)))
    }

    @Test
    fun orangesRottingTest() {
        val grid = arrayOf(
            intArrayOf(2,1,1),
            intArrayOf(1,1,0),
            intArrayOf(0,1,1)
        )
        assertEquals(4, orangesRotting(grid))

        val grid2 = arrayOf(
            intArrayOf(0,1),
            intArrayOf(2,0)
        )
        assertEquals(-1, orangesRotting(grid2))

        val grid3 = arrayOf(
            intArrayOf(2,1,1),
            intArrayOf(1,1,1),
            intArrayOf(0,1,2)
        )
        assertEquals(2, orangesRotting(grid3))
    }

    @Test
    fun permuteTest() {
        permute(intArrayOf(1,2,3))
    }

    @Test
    fun combinationSumTest() {
        combinationSum(intArrayOf(2,3,6,7), 7)
        combinationSum(intArrayOf(7,3,2), 18)
    }
}







































