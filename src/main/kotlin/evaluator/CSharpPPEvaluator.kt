package evaluator

import CSharpLexer
import CSharpParser
import CSharpPreprocessorParser
import common.ETAMarshaller
import common.OHighlight
import highlighter.csharphighlighter.CSharpGrammaticalHighlighter
import highlighter.csharphighlighter.CSharpPreprocessorGrammaticalHighlighter
import highlighter.csharphighlighter.csharpPreLexicalHighlighter
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

class CSharpPPEvaluator(
    userArgs: Array<String>,
) : Evaluator(
    userArgs = userArgs,
    languageName = "csharp",
    oracleFileSourcesPath = "csharp".toResourcePath(),
    logOutputFilePath = "csharp".toResourcePath(),
    lexerOf = { CSharpLexer(it) },
    parserOf = { CSharpPreprocessorParser(it) },
    lexicalHighlighter = { csharpPreLexicalHighlighter(it) },
    grammaticalHighlighter = CSharpPreprocessorGrammaticalHighlighter(),
    startRuleOf = { (it as CSharpPreprocessorParser).preprocessor_directives() },
    lexerChannels = arrayOf(Token.HIDDEN_CHANNEL, CSharpLexer.DIRECTIVE),
    parserChannel = CSharpLexer.DIRECTIVE
)

fun main(args: Array<String>) =
    //CSharpEvaluator(args).run()
//    CSharpPPEvaluator(arrayOf("renderTree","testFiles/test.cs")).run()
    CSharpPPEvaluator(arrayOf("fileToHTMLBrute","testFiles/test.cs")).run()
