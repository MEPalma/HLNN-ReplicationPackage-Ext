package highlighter.javaScriptHighlighter

import evaluator.JavaScriptEvaluator
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

internal class JavaScriptGrammaticalHighlighterTest {

    val outputPath = "./out.html"

    @After
    fun tearDown() {
        try {
            Files.deleteIfExists(Paths.get(outputPath))
        } catch (e: IOException) {
            println("Deletion failed.")
        }
    }


    @Test
    fun compareJavaScriptFiles() {
        var expectedFile = File("./src/test/resources/javascript.html")
        var inputFile = "./src/test/resources/javascriptInput.js"
        JavaScriptEvaluator(arrayOf("fileToHTMLBrute", inputFile))
        val generatedOutput = File(outputPath)
        val expected = Files.readString(expectedFile.toPath())
        val actual = Files.readString(generatedOutput.toPath())
        assertTrue(expected.contentEquals(actual))
    }

}