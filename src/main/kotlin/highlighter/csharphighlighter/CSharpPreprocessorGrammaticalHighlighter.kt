package highlighter.csharphighlighter

import CSharpParser
import CSharpParserBaseListener
import CSharpPreprocessorParserBase
import CSharpPreprocessorParserBaseListener
import allSubsTo
import common.HCode
import common.OHighlight
import highlighter.GrammaticalHighlighter
import isProduction
import loopingOnChildren
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.atn.ATN
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.TerminalNode
import preprocessor.csharppreprocessor.CSharpPreprocessor
import java.util.*

class CSharpPreprocessorGrammaticalHighlighter : GrammaticalHighlighter, CSharpPreprocessorParserBaseListener() {
    private val oHighlights = hashMapOf<Int, OHighlight>()

    private fun OHighlight.addReplacing() {
        oHighlights[this.startIndex - 2] = this
    }

    override fun getOverrides(): Collection<OHighlight> =
        this.oHighlights.values

    override fun reset() {
        this.oHighlights.clear()
    }

    private fun ParserRuleContext?.thisLoopingOnChildren(
        onTerminal: (TerminalNode) -> HCode? = { _ -> null },
        targetTerminalIndex: Int? = null,
        onProduction: (ParserRuleContext) -> HCode? = { _ -> null },
        targetProductionIndex: Int? = null,
        onAddedExit: Boolean = false,
        reversed: Boolean = false,
    ) =
        this.loopingOnChildren(
            parserVocab = CSharpPreprocessorParser.ruleNames,
            addReplacingFunc = { it.addReplacing() },
            onTerminal = onTerminal,
            targetTerminalIndex = targetTerminalIndex,
            onProduction = onProduction,
            targetProductionIndex = targetProductionIndex,
            onAddedExit = onAddedExit,
            reversed = reversed
        )

    // +------------------+
    // |   DECLARATIONS   |
    // +------------------+

    private fun overrideOf(prc: ParserRuleContext, hCode: HCode, overridingRuleIndex: Int): OHighlight =
        OHighlight(
            startIndex = prc.start.startIndex,
            stopIndex = prc.stop.stopIndex,
            highlightCode = hCode.ordinal,
            highlightColor = hCode.colorCode,
            overridingRule = CSharpPreprocessorParser.ruleNames[overridingRuleIndex]
        )


    // +------------+
    // | DIRECTIVES  |
    // +------------+
    override fun exitPreprocessorDeclaration(ctx: CSharpPreprocessorParser.PreprocessorDeclarationContext?) {
        ctx.thisLoopingOnChildren(
            targetTerminalIndex = CSharpLexer.CONDITIONAL_SYMBOL,
            onTerminal = {HCode.VARIABLE_DECLARATOR},
            reversed = true,
            onAddedExit = true
        )

    }

    override fun exitPreprocessor_directives(ctx: CSharpPreprocessorParser.Preprocessor_directivesContext?) {
        super.exitPreprocessor_directives(ctx)
    }

    override fun exitPreprocessor_expression(ctx: CSharpPreprocessorParser.Preprocessor_expressionContext?) {
        ctx.thisLoopingOnChildren(
            targetTerminalIndex = CSharpLexer.IDENTIFIER,
            onTerminal = {HCode.FIELD_IDENTIFIER},
            onAddedExit = true
        )
    }
    override fun exitPreprocessorPragma(ctx: CSharpPreprocessorParser.PreprocessorPragmaContext?) {
        ctx.thisLoopingOnChildren(
            targetTerminalIndex = CSharpLexer.TEXT,
            onTerminal = {HCode.LITERAL},
            reversed = true,
            onAddedExit = true
        )
    }

    override fun exitPreprocessorConditional(ctx: CSharpPreprocessorParser.PreprocessorConditionalContext?) {
        ctx.thisLoopingOnChildren(
            onProduction = {HCode.FIELD_IDENTIFIER},
            targetProductionIndex = CSharpPreprocessorParser.RULE_preprocessor_expression
        )
    }

    override fun exitPreprocessorRegion(ctx: CSharpPreprocessorParser.PreprocessorRegionContext?) {
        ctx.thisLoopingOnChildren(
            targetTerminalIndex = CSharpLexer.DIRECTIVE_TEXT,
            onTerminal = {HCode.LITERAL},
            reversed = true,
            onAddedExit = true
        )
    }



    override fun exitPreprocessorDiagnostic(ctx: CSharpPreprocessorParser.PreprocessorDiagnosticContext?) {
        super.exitPreprocessorDiagnostic(ctx)
    }

    override fun exitEveryRule(ctx: ParserRuleContext?) {
        super.exitEveryRule(ctx)
    }

}
