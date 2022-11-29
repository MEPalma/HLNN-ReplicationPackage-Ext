package preprocessor.csharppreprocessor

import CSharpLexer
import CSharpParser
import CSharpPreprocessorParser
import highlighter.csharphighlighter.CSharpGrammaticalHighlighter
import highlighter.csharphighlighter.CSharpPreprocessorGrammaticalHighlighter
import highlighter.csharphighlighter.csharpSemiLexicalHighlighter
import org.antlr.v4.runtime.Token
import preprocessor.Preprocessor
import utils.toResourcePath

class CSharpDirectivesPreprocessor(userArgs: Array<String>) : Preprocessor(
    userArgs = userArgs,
    //
    oracleFileSourcesPath = "/csharp".toResourcePath(),
    //
    lexerOf = { CSharpLexer(it) },
    parserOf = { CSharpPreprocessorParser(it) },
    startRuleOf = { (it as CSharpPreprocessorParser).preprocessor_directive() },
    //
    lexicalHighlighter = { csharpSemiLexicalHighlighter(it) },
    grammaticalHighlighter = CSharpPreprocessorGrammaticalHighlighter(),
    lexerChannels = arrayOf(Token.HIDDEN_CHANNEL, CSharpLexer.DIRECTIVE)
)

fun main(args: Array<String>) =
    CSharpDirectivesPreprocessor(args).run()
