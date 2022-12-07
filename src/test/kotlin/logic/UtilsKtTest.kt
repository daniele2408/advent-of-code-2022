package logic

import model.Folder
import org.junit.jupiter.api.Assertions.*
import retrieveRowsFromFile
import kotlin.test.Test

const val TOTAL_SPACE = 70000000
const val NEEDED_SPACE = 30000000

class UtilsKtTest {

    val commands = """
        ${'$'} cd /
        ${'$'} ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        ${'$'} cd a
        ${'$'} ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        ${'$'} cd e
        ${'$'} ls
        584 i
        ${'$'} cd ..
        ${'$'} cd ..
        ${'$'} cd d
        ${'$'} ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent()

    @Test
    fun test1() {
        val rows = commands.split("\n")

        val treeObserver = TreeObserver()
        val tree : Folder = TreeFactory.parseLines(rows, treeObserver)

        assertEquals(48381165, tree.getTotalSize())
        assertEquals(tree.getTotalSize(), treeObserver.getTotalSizeFiles())

        assertEquals(0, treeObserver.getTotalSizeFoldersSmallerThan(583))
        assertEquals(584, treeObserver.getTotalSizeFoldersSmallerThan(584))

        assertEquals(584, treeObserver.getTotalSizeFoldersSmallerThan(94852))
        assertEquals(584 + 94853, treeObserver.getTotalSizeFoldersSmallerThan(94853))

        assertEquals(584 + 94853, treeObserver.getTotalSizeFoldersSmallerThan(24933641))
        assertEquals(584 + 94853 + 24933642, treeObserver.getTotalSizeFoldersSmallerThan(24933642))

        assertEquals(584 + 94853 + 24933642, treeObserver.getTotalSizeFoldersSmallerThan(48381164))
        assertEquals(584 + 94853 + 24933642, treeObserver.getTotalSizeFoldersSmallerThan(48381165))

        assertEquals(95437, treeObserver.getTotalSizeFoldersSmallerThan(100000))

        val spaceAvailable : Int = TOTAL_SPACE - tree.getTotalSize()

        val folderToDeleteSize =
            treeObserver.getSmallestFolderSizeToSaveSpace(NEEDED_SPACE - spaceAvailable)

        assertEquals(24933642, folderToDeleteSize)

    }

    val commands2 = """
        ${'$'} cd /
        ${'$'} ls
        dir a
        1 b.txt
        2 c.dat
        dir e
        ${'$'} cd e
        ${'$'} ls
        10 f.txt
        20 g.txt
        ${'$'} cd /
        ${'$'} cd a
        ${'$'} ls
        1000 h.txt
        2500 i.txt
    """.trimIndent()

    @Test
    fun test2() {
        val rows = commands2.split("\n")

        val treeObserver = TreeObserver()
        val tree : Folder = TreeFactory.parseLines(rows, treeObserver)

        assertEquals(3533, tree.getTotalSize())
        assertEquals(tree.getTotalSize(), treeObserver.getTotalSizeFiles())

    }

    @Test
    fun test3() {
        val rows : List<String> = retrieveRowsFromFile("inputday7.txt")

        val treeObserver = TreeObserver()
        val tree : Folder = TreeFactory.parseLines(rows, treeObserver)

        assertEquals(tree.getTotalSize(), treeObserver.getTotalSizeFiles())

        val res =
            treeObserver.getSmallestFolderSizeToSaveSpace(NEEDED_SPACE - (TOTAL_SPACE - tree.getTotalSize()))

        assertEquals(1498966, res)

    }

}