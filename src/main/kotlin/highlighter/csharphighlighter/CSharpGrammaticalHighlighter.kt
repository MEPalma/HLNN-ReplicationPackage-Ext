package highlighter.csharphighlighter

import CSharpLexer
import CSharpParser
import CSharpParserBaseListener
import common.HCode
import common.OHighlight
import highlighter.GrammaticalHighlighter
import isProduction
import isTerminal
import loopingOnChildren
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.TerminalNode

class CSharpGrammaticalHighlighter : GrammaticalHighlighter, CSharpParserBaseListener() {
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
            parserVocab = CSharpParser.ruleNames,
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
            overridingRule = CSharpParser.ruleNames[overridingRuleIndex]
        )

    override fun exitLocal_variable_declarator(ctx: CSharpParser.Local_variable_declaratorContext?) {
        ctx?.thisLoopingOnChildren(
            targetProductionIndex = CSharpParser.RULE_identifier,
            onProduction = { HCode.VARIABLE_DECLARATOR },
            onAddedExit = true,
        )
    }

    override fun exitConstant_declarator(ctx: CSharpParser.Constant_declaratorContext?) {
        ctx?.thisLoopingOnChildren(
            targetProductionIndex = CSharpParser.RULE_identifier,
            onProduction = { HCode.VARIABLE_DECLARATOR },
            onAddedExit = true,
        )
    }

    override fun exitFixed_pointer_declarator(ctx: CSharpParser.Fixed_pointer_declaratorContext?) {
        ctx?.thisLoopingOnChildren(
            targetProductionIndex = CSharpParser.RULE_identifier,
            onProduction = { HCode.VARIABLE_DECLARATOR },
            onAddedExit = true,
        )
    }

    override fun exitLet_clause(ctx: CSharpParser.Let_clauseContext?) {
        ctx?.thisLoopingOnChildren(
            targetProductionIndex = CSharpParser.RULE_identifier,
            onProduction = { HCode.VARIABLE_DECLARATOR },
            onAddedExit = true,
        )
    }

    override fun exitClass_definition(ctx: CSharpParser.Class_definitionContext?) {
        ctx?.thisLoopingOnChildren(
            targetProductionIndex = CSharpParser.RULE_identifier,
            onProduction = { HCode.CLASS_DECLARATOR },
            onAddedExit = true,
        )
    }

    override fun exitStruct_definition(ctx: CSharpParser.Struct_definitionContext?) {
        ctx?.thisLoopingOnChildren(
            targetProductionIndex = CSharpParser.RULE_identifier,
            onProduction = { HCode.CLASS_DECLARATOR },
            onAddedExit = true,
        )
    }

    override fun exitInterface_definition(ctx: CSharpParser.Interface_definitionContext?) {
        ctx?.thisLoopingOnChildren(
            targetProductionIndex = CSharpParser.RULE_identifier,
            onProduction = { HCode.CLASS_DECLARATOR },
            onAddedExit = true,
        )
    }

    override fun exitEnum_definition(ctx: CSharpParser.Enum_definitionContext?) {
        ctx?.thisLoopingOnChildren(
            targetProductionIndex = CSharpParser.RULE_identifier,
            onProduction = { HCode.CLASS_DECLARATOR },
            onAddedExit = true,
        )
    }

    override fun exitDelegate_definition(ctx: CSharpParser.Delegate_definitionContext?) {
        ctx?.thisLoopingOnChildren(
            targetProductionIndex = CSharpParser.RULE_identifier,
            onProduction = { HCode.FUNCTION_DECLARATOR },
            onAddedExit = true,
        )
    }

    override fun exitEvent_declaration(ctx: CSharpParser.Event_declarationContext?) {
        // TODO
    }

    override fun exitConstructor_declaration(ctx: CSharpParser.Constructor_declarationContext?) {
        ctx?.thisLoopingOnChildren(
            targetProductionIndex = CSharpParser.RULE_identifier,
            onProduction = { HCode.FUNCTION_DECLARATOR },
            onAddedExit = true,
        )
    }

    override fun exitDestructor_definition(ctx: CSharpParser.Destructor_definitionContext?) {
        ctx?.thisLoopingOnChildren(
            targetProductionIndex = CSharpParser.RULE_identifier,
            onProduction = { HCode.FUNCTION_DECLARATOR },
            onAddedExit = true,
        )
    }

    override fun exitMethod_member_name(ctx: CSharpParser.Method_member_nameContext?) {
        ctx?.thisLoopingOnChildren(
            targetProductionIndex = CSharpParser.RULE_identifier,
            onProduction = { HCode.FUNCTION_DECLARATOR },
            onAddedExit = true,
        )
    }

    // +-----------+
    // |   TYPES   |
    // +-----------+

    override fun exitNamespace_or_type_name(ctx: CSharpParser.Namespace_or_type_nameContext?) {
        ctx?.thisLoopingOnChildren(
            targetProductionIndex = CSharpParser.RULE_identifier,
            onProduction = { HCode.TYPE_IDENTIFIER },
            onAddedExit = false,
        )
        // TODO: qualified_alias_member
    }

    override fun exitVariant_type_parameter(ctx: CSharpParser.Variant_type_parameterContext?) {
        ctx?.thisLoopingOnChildren(
            targetProductionIndex = CSharpParser.RULE_identifier,
            onProduction = { HCode.TYPE_IDENTIFIER },
            onAddedExit = true,
        )
    }

    override fun exitType_parameter(ctx: CSharpParser.Type_parameterContext?) {
        ctx?.thisLoopingOnChildren(
            targetProductionIndex = CSharpParser.RULE_identifier,
            onProduction = { HCode.TYPE_IDENTIFIER },
            onAddedExit = true,
        )
    }

    // Creation calls (Constructor calls).

    // +---------------+
    // |   FUNCTIONS   |
    // +---------------+

    // +------------+
    // |   FIELDS   |
    // +------------+

    // Field Access (Known).

}
