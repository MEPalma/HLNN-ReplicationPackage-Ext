package csharp

import common.HETAMarshaller.Companion.toJSON
import common.JSONSourceMarshaller.Companion.toJSONString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import preprocessor.python3preprocessor.CSharpPreprocessor
import java.io.File

class TestCSharp {

    private fun filepathOf(filename: String): String = "src/test/kotlin/csharp/files/${filename}"
    private fun snapshotFilepathOf(filename: String) = filepathOf("${filename}.json")

    private fun snapshot(filename: String) {
        File(filepathOf(filename)).readText().let { src ->
            CSharpPreprocessor(arrayOf()).tryToHetas(src)?.toJSON(snapshotFilepathOf(filename))
        }
    }

    private fun assertSnapshot(filename: String) {
        val snapshotHetas: String = File(snapshotFilepathOf(filename)).readText()
        val hetas: String? =
            CSharpPreprocessor(arrayOf()).tryToHetas(File(filepathOf(filename)).readText())?.toJSONString()
        assertEquals(snapshotHetas, hetas)
    }

    @Test
    fun `test AllInOneNoPreprocessor_cs`() {
        val filename = "AllInOneNoPreprocessor.cs";
        if (!File(snapshotFilepathOf(filename)).exists()) snapshot(filename)
        assertSnapshot(filename)
    }
}
