package highlighter.csharphighlighter

import CSharpParserBaseListener
import Java8Lexer
import Java8Parser
import common.HCode
import common.OHighlight
import highlighter.GrammaticalHighlighter
import loopingOnChildren
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.TerminalNode

class CSharpGrammaticalHighlighter : GrammaticalHighlighter, CSharpParserBaseListener() {
    private val oHighlights = hashMapOf<Int, OHighlight>()

    private fun OHighlight.addReplacing() {
        oHighlights[this.startIndex] = this
    }

    override fun getOverrides(): Collection<OHighlight> =
        this.oHighlights.values

    override fun reset() {
        this.oHighlights.clear()
    }

    private fun ParserRuleContext?.myLoopingOnChildren(
        onTerminal: (TerminalNode) -> HCode? = { _ -> null },
        targetTerminalIndex: Int? = null,
        onProduction: (ParserRuleContext) -> HCode? = { _ -> null },
        targetProductionIndex: Int? = null,
        onAddedExit: Boolean = false,
        reversed: Boolean = false,
    ) =
        this.loopingOnChildren(
            parserVocab = Java8Parser.ruleNames,
            addReplacingFunc = { it.addReplacing() },
            onTerminal = onTerminal,
            targetTerminalIndex = targetTerminalIndex,
            onProduction = onProduction,
            targetProductionIndex = targetProductionIndex,
            onAddedExit = onAddedExit,
            reversed = reversed
        )

    private fun assignOnFirstIdentifier(ctx: ParserRuleContext?, hcode: HCode) =
        ctx.myLoopingOnChildren(
            targetTerminalIndex = Java8Lexer.Identifier,
            onTerminal = { hcode },
            onAddedExit = true
        )

    // +-----------------+
    // |  DECLARATIONS  |
    //+-----------------+

    // +----------+
    // |  TYPES  |
    //+----------+

    // Creation calls (Constuctor calls).

    // +-------------+
    // |  FUNCTIONS  |
    //+--------------+

    // +----------+
    // |  FIELDS  |
    //+-----------+

    // Field Access (Known).

}
