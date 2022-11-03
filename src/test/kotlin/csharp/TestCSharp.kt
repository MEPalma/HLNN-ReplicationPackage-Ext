package csharp

import common.JSONSourceMarshaller.Companion.toJSONString
import common.SnapshotTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import preprocessor.python3preprocessor.CSharpPreprocessor
import java.io.File

class TestCSharp : SnapshotTest {

    var language = "csharp"

    private fun assertSnapshot(filename: String) {
        if (!File(snapshotFilepathOf(language, filename)).exists()) snapshot(language, filename)
        val snapshotHetas: String = File(snapshotFilepathOf(language, filename)).readText()
        val hetas: String? =
            CSharpPreprocessor(arrayOf()).tryToHetas(File(filepathOf(language, filename)).readText())?.toJSONString()
        assertEquals(snapshotHetas, hetas)
    }

    @Test
    fun `test AllInOneNoPreprocessor_cs`() {
        assertSnapshot("AllInOneNoPreprocessor.cs")
    }

    @Test
    fun `test TypeInArglistMembAccessError_cs`() {
        assertSnapshot("TypeInArglistMembAccessError.cs")
    }
}
