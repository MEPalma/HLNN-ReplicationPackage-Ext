package evaluator

import CSharpLexer
import CSharpParser
import CSharpPreprocessorParser
import common.ETAMarshaller
import common.OHighlight
import highlighter.csharphighlighter.CSharpGrammaticalHighlighter
import highlighter.csharphighlighter.CSharpPreprocessorGrammaticalHighlighter
import highlighter.csharphighlighter.csharpSemiLexicalHighlighter
import highlighter.highlightedAs
import highlighter.toHighlightedHTML
import highlighter.tryToETAS
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.RuleContext
import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.tree.ParseTreeWalker
import utils.toResourcePath
import java.io.File

class CSharpEvaluator(
    userArgs: Array<String>,
) : Evaluator(
    userArgs = userArgs,
    languageName = "csharp",
    oracleFileSourcesPath = "csharp".toResourcePath(),
    logOutputFilePath = "csharp".toResourcePath(),
    lexerOf = { CSharpLexer(it) },
    parserOf = { CSharpParser(it) },
    lexicalHighlighter = { csharpSemiLexicalHighlighter(it) },
    grammaticalHighlighter = CSharpGrammaticalHighlighter(),
    startRuleOf = { (it as CSharpParser).compilation_unit() },
    lexerChannels = arrayOf(Token.HIDDEN_CHANNEL, CSharpLexer.DIRECTIVE)
){
    private var ppGramaticalHighlighter = CSharpPreprocessorGrammaticalHighlighter()


    override fun fileToHTMLBrute(filepath: String) {
        File(filepath).readText().let { src ->
            var startRule: RuleContext? = null
            var startRulePP: RuleContext? = null

            src.tryToETAS(
                lexerOf = lexerOf,
                parserOf = { CSharpPreprocessorParser(it) },
                startRuleOf = {  (it as CSharpPreprocessorParser).preprocessor_directive().let { st -> startRulePP = st; st } },
                resolver = ETAMarshaller::tryFromContext,
                lexerChannels = lexerChannels,
                withErrorListeners = false
            )?.let { etas ->
                val hetas = etas.highlightedAs { lexicalHighlighter(it) }
                startRulePP?.let {
                    // Execute preprocessor highlighter
                    ppGramaticalHighlighter.reset()
                    ParseTreeWalker.DEFAULT.walk(ppGramaticalHighlighter, it)
                    OHighlight.applyOverrides(hetas, ppGramaticalHighlighter.getOverrides())
                    ppGramaticalHighlighter.reset()
                } ?: error("No start rule definition.")
                val tmp = toHighlightedHTML(hetas, src)
                println(tmp)
                File("out.html").writeText(tmp)
            } ?: error("Could not derive hetas.")



            src.tryToETAS(
                lexerOf = lexerOf,
                parserOf = parserOf,
                startRuleOf = { startRuleOf(it).let { st -> startRule = st; st } },
                resolver = ETAMarshaller::tryFromContext,
                lexerChannels = lexerChannels,
                withErrorListeners = false
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
                val tmp = toHighlightedHTML(hetas, src)
                println(tmp)
                File("out.html").writeText(tmp)
            } ?: error("Could not derive hetas.")
        }
    }
}

fun main(args: Array<String>) =
    //CSharpEvaluator(args).run()
    CSharpEvaluator(arrayOf("fileToHTMLBrute","testFiles/test.cs")).run()
