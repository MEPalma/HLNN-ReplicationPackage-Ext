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
    lexerOf = { CPP14Lexer(it).also { lexer -> lexer.removeErrorListeners() } },
    parserOf = { CPP14Parser(it) },
    lexicalHighlighter = { cppLexicalHighlighter(it) },
    grammaticalHighlighter = CPPGrammaticalHighlighter(),
    startRuleOf = { (it as CPP14Parser).translationUnit() })

fun main() = CPPEvaluator(arrayOf("perFileAcc", "../saved_model_losses/cpp_1_28_CNNClassifier1_128embs_147id_128hd_0hl_Falsebid5kernel0.3dropout")).run()

