package evaluator

import Java8Lexer
import Java8Parser
import highlighter.javahighlighter.JavaGrammaticalHighlighter
import highlighter.javahighlighter.javaLexicalHighlighter
import utils.toResourcePath

class JavaEvaluator(
    userArgs: Array<String>,
) : Evaluator(
    userArgs = userArgs,
    languageName = "java",
    oracleFileSourcesPath = "java".toResourcePath(),
    logOutputFilePath = "java".toResourcePath(),
    lexerOf = { Java8Lexer(it) },
    parserOf = { Java8Parser(it) },
    lexicalHighlighter = { javaLexicalHighlighter(it) },
    grammaticalHighlighter = JavaGrammaticalHighlighter(),
    startRuleOf = { (it as Java8Parser).compilationUnit() }
)

fun main(args: Array<String>) =
    JavaEvaluator(arrayOf("fileToHTMLModel","testFiles/test.java","../saved_model_losses/java_1_66_CNNClassifier1_128embs_109id_64hd_2hl_Falsebid.json")).run()
