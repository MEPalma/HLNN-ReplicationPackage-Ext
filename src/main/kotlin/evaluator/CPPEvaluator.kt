package evaluator

import CPP14Lexer
import CPP14Parser
import highlighter.cpp.CPPGrammaticalHighlighter
import highlighter.cpp.cppLexicalHighlighter
import utils.toResourcePath

class CPPEvaluator(
    userArgs: Array<String>,
) : Evaluator(
    userArgs = userArgs,
    languageName = "cpp",
    oracleFileSourcesPath = "cpp".toResourcePath(),
    logOutputFilePath = "cpp".toResourcePath(),
    lexerOf = { CPP14Lexer(it) },
    parserOf = { CPP14Parser(it) },
    lexicalHighlighter = { cppLexicalHighlighter(it) },
    grammaticalHighlighter = CPPGrammaticalHighlighter(),
    startRuleOf = { (it as CPP14Parser).translationUnit() }
)

fun main(args: Array<String>) =
//    CPPEvaluator(args).run()
    CPPEvaluator(arrayOf("fileToHTMLBrute","testFiles/test.cpp")).run()
//CPPEvaluator(arrayOf("renderTree","testFiles/test.cpp")).run()


