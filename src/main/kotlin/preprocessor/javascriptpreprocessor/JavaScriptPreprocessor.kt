package preprocessor.javascriptpreprocessor

import JavaScriptLexer
import JavaScriptParser
import highlighter.javaScriptHighlighter.javaScriptLexicalHighlighter
import highlighter.javaScriptHighlighter.JavaScriptGrammaticalHighlighter
import highlighter.javahighlighter.javaLexicalHighlighter
import preprocessor.Preprocessor
import utils.toResourcePath

class JavaScriptPreprocessor(userArgs: Array<String>) : Preprocessor(
    userArgs = userArgs,
    //
    oracleFileSourcesPath = "java".toResourcePath(),
    //
    lexerOf = { JavaScriptLexer(it) },
    parserOf = { JavaScriptParser(it) },
    startRuleOf = { (it as JavaScriptParser).program() },
    //
    lexicalHighlighter = { javaScriptLexicalHighlighter(it) },
    grammaticalHighlighter = JavaScriptGrammaticalHighlighter(),
)

fun main(args: Array<String>) =
    JavaScriptPreprocessor(arrayOf("debug", "test.js")).run()
//    JavaPreprocessor(args).run()
