package cpp

import common.JSONSourceMarshaller.Companion.toJSONString
import common.SnapshotTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import preprocessor.Preprocessor
import preprocessor.cpp.CPPPreprocessor
import java.io.File

class CPPTest : SnapshotTest {
    var language = "cpp"

    private fun assertSnapshot(filename: String) {
        if (!File(snapshotFilepathOf(language, filename)).exists()) snapshot(language, filename)
        val snapshotHetas: String = File(snapshotFilepathOf(language, filename)).readText()
        val hetas: String? = preprocessor.tryToHetas(File(filepathOf(language, filename)).readText())?.toJSONString()
        Assertions.assertEquals(snapshotHetas, hetas)
    }

    @Test
    fun `test Cpp generation`() {
        assertSnapshot("all.cpp")
    }

    override val preprocessor: Preprocessor
        get() = CPPPreprocessor(arrayOf())

}