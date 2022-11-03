package javascript

import common.HETAMarshaller.Companion.toJSON
import common.JSONSourceMarshaller.Companion.toJSONString
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import preprocessor.javascriptpreprocessor.JavaScriptPreprocessor

import java.io.File

internal class JavaScriptGrammaticalHighlighterTest {

    private fun filepathOf(filename: String): String = "src/test/kotlin/javascript/files/${filename}"
    private fun snapshotFilepathOf(filename: String) = filepathOf("${filename}.json")

    private fun snapshot(filename: String) {
        File(filepathOf(filename)).readText().let { src ->
            JavaScriptPreprocessor(arrayOf()).tryToHetas(src)?.toJSON(snapshotFilepathOf(filename))
        }
    }

    private fun assertSnapshot(filename: String) {
        if (!File(snapshotFilepathOf(filename)).exists()) snapshot(filename)
        val snapshotHetas: String = File(snapshotFilepathOf(filename)).readText()
        val hetas: String? =
            JavaScriptPreprocessor(arrayOf()).tryToHetas(File(filepathOf(filename)).readText())?.toJSONString()
        Assertions.assertEquals(snapshotHetas, hetas)
    }

    @Test
    fun `test JavaScript generation`() {
        assertSnapshot("javascriptInput.js")
    }

}