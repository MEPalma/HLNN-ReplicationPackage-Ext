package preprocessor.python3preprocessor

import CSharpLexer
import CSharpParser
import highlighter.csharphighlighter.CSharpGrammaticalHighlighter
import highlighter.csharphighlighter.csharpSemiLexicalHighlighter
import preprocessor.Preprocessor
import utils.toResourcePath

class CSharpPreprocessor(userArgs: Array<String>) : Preprocessor(
    userArgs = userArgs,
    //
    oracleFileSourcesPath = "/python3".toResourcePath(),
    //
    lexerOf = { CSharpLexer(it) },
    parserOf = { CSharpParser(it) },
    startRuleOf = { (it as CSharpParser).compilation_unit() },
    //
    lexicalHighlighter = { csharpSemiLexicalHighlighter(it) },
    grammaticalHighlighter = CSharpGrammaticalHighlighter(),
)

fun main(args: Array<String>) =
    Python3Preprocessor(args).run()
