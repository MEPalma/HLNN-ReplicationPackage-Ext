package javascript

import common.JSONSourceMarshaller.Companion.toJSONString
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import preprocessor.javascriptpreprocessor.JavaScriptPreprocessor
import common.SnapshotTest

import java.io.File

class JavaScriptGrammaticalHighlighterTest : SnapshotTest {
    var language = "javascript"

    private fun assertSnapshot(filename: String) {
        if (!File(snapshotFilepathOf(language, filename)).exists()) snapshot(language, filename)
        val snapshotHetas: String = File(snapshotFilepathOf(language, filename)).readText()
        val hetas: String? =
            JavaScriptPreprocessor(arrayOf()).tryToHetas(File(filepathOf(language, filename)).readText())
                ?.toJSONString()
        Assertions.assertEquals(snapshotHetas, hetas)
    }

    @Test
    fun `test JavaScript generation`() {
        assertSnapshot("javascriptInput.js")
    }

}