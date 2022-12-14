package javascript

import common.JSONSourceMarshaller.Companion.toJSONString
import common.SnapshotTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import preprocessor.Preprocessor
import preprocessor.javascriptpreprocessor.JavaScriptPreprocessor
import java.io.File

class JavaScriptGrammaticalHighlighterTest : SnapshotTest {
    var language = "javascript"

    private fun assertSnapshot(filename: String) {
        if (!File(snapshotFilepathOf(language, filename)).exists()) snapshot(language, filename)
        val snapshotHetas: String = File(snapshotFilepathOf(language, filename)).readText()
        val hetas: String? =
            preprocessor.tryToHetas(File(filepathOf(language, filename)).readText())
                ?.toJSONString()
        Assertions.assertEquals(snapshotHetas, hetas)
    }

    @Test
    fun `test JavaScript generation`() {
        assertSnapshot("javascriptInput.js")
    }

    override val preprocessor: Preprocessor
        get() = JavaScriptPreprocessor(arrayOf())

}