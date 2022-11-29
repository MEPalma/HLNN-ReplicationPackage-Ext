package preprocessor.csharppreprocessor

import CSharpLexer
import CSharpParser
import common.ETAMarshaller
import common.JSONAnnotatedSource
import common.JSONHighlightedSource
import common.JSONSourceMarshaller.Companion.toJSON
import common.JSONSourceMarshaller.Companion.tryJSONSourcesFromJSON
import common.OHighlight
import highlighter.csharphighlighter.CSharpGrammaticalHighlighter
import highlighter.csharphighlighter.CSharpPreprocessorGrammaticalHighlighter
import highlighter.csharphighlighter.csharpSemiLexicalHighlighter
import highlighter.highlightedAs
import highlighter.tryToETAS
import org.antlr.v4.runtime.RuleContext
import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.tree.ParseTreeWalker
import preprocessor.Preprocessor
import utils.toResourcePath
import java.io.File

class CSharpPreprocessor(userArgs: Array<String>) : Preprocessor(
    userArgs = userArgs,
    //
    oracleFileSourcesPath = "/csharp".toResourcePath(),
    //
    lexerOf = { CSharpLexer(it) },
    parserOf = { CSharpParser(it) },
    startRuleOf = { (it as CSharpParser).compilation_unit() },
    //
    lexicalHighlighter = { csharpSemiLexicalHighlighter(it) },
    grammaticalHighlighter = CSharpGrammaticalHighlighter(),
    lexerChannels = arrayOf(Token.HIDDEN_CHANNEL, CSharpLexer.DIRECTIVE),

){

    private var ppGramaticalHighlighter = CSharpPreprocessorGrammaticalHighlighter()


    override fun generateOracle() {
        val jetasFile = File("$oracleFileSourcesPath/oracle/jetas.json")
        val jhetasFile = File("$oracleFileSourcesPath/oracle/jhetas.json")
        //
        jetasFile.writeText("[\n")
        jhetasFile.writeText("[\n")
        //
        var startRule: RuleContext? = null
        File("$oracleFileSourcesPath/raw/file_sources.json").readText().tryJSONSourcesFromJSON()
            ?.forEachIndexed { i, jsonSource ->
                print("\rOn file number: ${i + 1}")
                try {
                    jsonSource.source.tryToETAS(
                        lexerOf = lexerOf,
                        parserOf = parserOf,
                        startRuleOf = { startRuleOf(it).let { sr -> startRule = sr; sr } },
                        resolver = ETAMarshaller::tryFromContext,
                        lexerChannels = lexerChannels,
                        parserChannel = CSharpLexer.DIRECTIVE
                    )?.let { etas ->
                        val hetas = etas.highlightedAs { lexicalHighlighter(it) }
                        startRule?.let {
                            // Execute preprocessor highlighter
                            ppGramaticalHighlighter.reset()
                            ParseTreeWalker.DEFAULT.walk(ppGramaticalHighlighter, it)
                            OHighlight.applyOverrides(hetas, ppGramaticalHighlighter.getOverrides())
                            ppGramaticalHighlighter.reset()

                            grammaticalHighlighter.reset() // Redundant.
                            ParseTreeWalker.DEFAULT.walk(grammaticalHighlighter, it)
                            OHighlight.applyOverrides(hetas, grammaticalHighlighter.getOverrides())
                            grammaticalHighlighter.reset()
                        } ?: error("No start rule definition.")
                        //
                        if (i > 0) {
                            jetasFile.appendText("\n,\n")
                            jhetasFile.appendText("\n,\n")
                        }
                        //
                        jetasFile.appendText(JSONAnnotatedSource(jsonSource, etas).toJSON())
                        jhetasFile.appendText(JSONHighlightedSource(jsonSource, hetas).toJSON())
                        //
                    } ?: error("Parser error generating ETAs")
                } catch (e: Exception) {
                    try {
                        System.err.println("\rERROR: ${jsonSource.file.repo} at ${jsonSource.file.path} -> ${e.message}")
                    } catch (e1: Exception) {
                        System.err.println("\rERRORS: " + e.message + " " + e1.message)
                    }
                }
            }
        //
        jetasFile.appendText("\n]\n")
        jhetasFile.appendText("\n]\n")
    }
}


fun main(args: Array<String>) =
    CSharpPreprocessor(args).run()
