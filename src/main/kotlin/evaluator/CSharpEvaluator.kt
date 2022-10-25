package evaluator

import CSharpLexer
import CSharpParser
import highlighter.csharphighlighter.CSharpGrammaticalHighlighter
import highlighter.csharphighlighter.csharpSemiLexicalHighlighter
import utils.toResourcePath

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
    startRuleOf = { (it as CSharpParser).compilation_unit() }
)

fun main(args: Array<String>) =
    CSharpEvaluator((arrayOf("renderTree"))).run()
//    CSharpEvaluator(arrayOf("fileToHTMLBrute", "src/test/kotlin/csharp/files/AllInOneNoPreprocessor.cs")).run()
//    CSharpEvaluator(arrayOf("fileToHTMLBrute", "test.cs")).run()
