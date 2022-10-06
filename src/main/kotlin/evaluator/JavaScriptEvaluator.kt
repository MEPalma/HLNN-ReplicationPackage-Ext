package evaluator

import JavaScriptLexer
import JavaScriptParser
import highlighter.javaScriptHighlighter.JavaScriptGrammaticalHighlighter
import highlighter.javaScriptHighlighter.javaScriptSemiLexicalHighlighter
import utils.toResourcePath

class JavaScriptEvaluator(
    userArgs: Array<String>,
) : Evaluator(
    userArgs = userArgs,
    languageName = "javaScript",
    oracleFileSourcesPath = "javaScript".toResourcePath(),
    logOutputFilePath = "javaScript".toResourcePath(),
    lexerOf = { JavaScriptLexer(it) },
    parserOf = { JavaScriptParser(it) },
    lexicalHighlighter = { javaScriptSemiLexicalHighlighter(it) },
    grammaticalHighlighter = JavaScriptGrammaticalHighlighter(),
    startRuleOf = { (it as JavaScriptParser).program() }
)

fun main(args: Array<String>) =
    JavaScriptEvaluator(arrayOf("fileToHTMLBrute", "testFiles/test.js")).run()
//    JavaScriptEvaluator(arrayOf("renderTree","testFiles/test.js")).run()

//renderTree
//    JavaScriptEvaluator(args).run()
