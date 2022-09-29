package highlighter.javaScriptHighlighter

import Java8Lexer
import JavaScriptParser.ArgumentsExpressionContext
import JavaScriptParserBaseListener
import common.HCode
import common.OHighlight
import common.OHighlight.Companion.overrideOf
import highlighter.GrammaticalHighlighter
import loopingOnChildren
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.TerminalNode
import java.util.*
import org.antlr.v4.runtime.*;
import utils.println

class JavaScriptGrammaticalHighlighter : GrammaticalHighlighter, JavaScriptParserBaseListener() {
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
            parserVocab = JavaScriptParser.ruleNames,
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
            targetTerminalIndex = JavaScriptParser.Identifier,
            onTerminal = { hcode },
            onAddedExit = true
        )

    // +-----------------+
    // |  DECLARATIONS  |
    //+-----------------+

//    override fun exitFunctionDeclaration(ctx: JavaScriptParser.FunctionDeclarationContext?) =
//        ctx.myLoopingOnChildren(
//            targetProductionIndex = JavaScriptParser.RULE_identifier,
//            onProduction = {HCode.FUNCTION_DECLARATOR}
//        )

    // highlight class name
    override fun exitClassDeclaration(ctx: JavaScriptParser.ClassDeclarationContext?) =
        ctx.myLoopingOnChildren(
            targetProductionIndex = JavaScriptParser.RULE_identifier,
            onProduction = {HCode.FUNCTION_DECLARATOR}
        )

    // direct Function call
    override fun exitMethodDefinition(ctx: JavaScriptParser.MethodDefinitionContext?) =
        ctx.myLoopingOnChildren(
            targetProductionIndex = JavaScriptParser.RULE_propertyName,
            onProduction = { HCode.FUNCTION_IDENTIFIER }
        )

//    override fun exitFunctionExpression(ctx: JavaScriptParser.FunctionExpressionContext?) {
//        ctx.myLoopingOnChildren(
//            targetProductionIndex = JavaScriptParser.RULE_identifier,
//            onProduction = { HCode.FUNCTION_IDENTIFIER },
//            targetTerminalIndex = JavaScriptLexer.Identifier,
//            onTerminal = { HCode.FUNCTION_IDENTIFIER },
//            onAddedExit = true
//        )
//    }

    // function parameters
    override fun exitFormalParameterList(ctx: JavaScriptParser.FormalParameterListContext?) {
        ctx.myLoopingOnChildren(
            targetProductionIndex = JavaScriptParser.RULE_formalParameterArg,
            onProduction = { HCode.FIELD_IDENTIFIER }
        )
    }

    override fun exitImportStatement(ctx: JavaScriptParser.ImportStatementContext?) {
        super.exitImportStatement(ctx)
    }

    override fun exitFunctionExpression(ctx: JavaScriptParser.FunctionExpressionContext?) {
        super.exitFunctionExpression(ctx)
    }

    override fun exitExpressionSequence(ctx: JavaScriptParser.ExpressionSequenceContext?) {
        super.exitExpressionSequence(ctx)
    }

    override fun exitExpressionStatement(ctx: JavaScriptParser.ExpressionStatementContext?) {
        super.exitExpressionStatement(ctx)
    }

    override fun exitMemberDotExpression(ctx: JavaScriptParser.MemberDotExpressionContext?) {
//        if (ctx?.parent?.ruleContext?.equals(JavaScriptParser.ArgumentsExpressionContext) == true){
//
//        }
    }

    override fun exitArgumentsExpression(ctx: ArgumentsExpressionContext?) {
        ctx.myLoopingOnChildren(
            targetProductionIndex = JavaScriptParser.RULE_identifierName,
            onProduction = { HCode.FUNCTION_IDENTIFIER },
            onTerminal = { HCode.TYPE_IDENTIFIER }
        )
    }
//    ctx.myLoopingOnChildren(
//            targetProductionIndex = JavaScriptParser.RULE_identifier,
//            onProduction = {HCode.FUNCTION_DECLARATOR}
//        )

    override fun exitClassExpression(ctx: JavaScriptParser.ClassExpressionContext?) {
        assignOnFirstIdentifier(ctx, HCode.CLASS_DECLARATOR)
    }

    override fun exitClassElement(ctx: JavaScriptParser.ClassElementContext?) {
        assignOnFirstIdentifier(ctx, HCode.CLASS_DECLARATOR)
    }

    override fun exitImportExpression(ctx: JavaScriptParser.ImportExpressionContext?) {
        assignOnFirstIdentifier(ctx, HCode.ANNOTATION_DECLARATOR)
    }

    override fun enterImportExpression(ctx: JavaScriptParser.ImportExpressionContext?) {
        ctx.println()
        assignOnFirstIdentifier(ctx, HCode.ANNOTATION_DECLARATOR)
    }

//    override fun exitClassTail(ctx: JavaScriptParser.ClassTailContext?) {
//        assignOnFirstIdentifier(ctx, HCode.CLASS_DECLARATOR)
//    }


    // +-----------------------+
    // | VARIABLE DECLARATIONS |
    // +-----------------------+

    override fun exitVariableStatement(ctx: JavaScriptParser.VariableStatementContext?) =
        assignOnFirstIdentifier(ctx, HCode.VARIABLE_DECLARATOR)

    override fun exitVariableDeclarationList(ctx: JavaScriptParser.VariableDeclarationListContext?) =
        assignOnFirstIdentifier(ctx, HCode.VARIABLE_DECLARATOR)


    // +----------+
    // |  TYPES  |
    //+----------+
    override fun exitNumericLiteral(ctx: JavaScriptParser.NumericLiteralContext?) =
        assignOnFirstIdentifier(ctx, HCode.TYPE_IDENTIFIER)

    override fun exitBigintLiteral(ctx: JavaScriptParser.BigintLiteralContext?) =
        assignOnFirstIdentifier(ctx, HCode.TYPE_IDENTIFIER)

    override fun exitLiteral(ctx: JavaScriptParser.LiteralContext?) =
        assignOnFirstIdentifier(ctx, HCode.TYPE_IDENTIFIER)

    override fun exitAssignmentOperator(ctx: JavaScriptParser.AssignmentOperatorContext?) =
        assignOnFirstIdentifier(ctx, HCode.TYPE_IDENTIFIER)


    //exitIdentifierExpression

//
//    // Creation calls (Constuctor calls).
//    override fun exitClassInstanceCreationExpression(ctx: JavaScriptParser.ClassInstanceCreationExpressionContext?) =
//        ctx.myLoopingOnChildren(
//            targetTerminalIndex = Java8Lexer.Identifier,
//            onTerminal = { HCode.TYPE_IDENTIFIER },
//            onAddedExit = false
//        )
//
//    override fun exitClassInstanceCreationExpression_lf_primary(ctx: JavaScriptParser.ClassInstanceCreationExpression_lf_primaryContext?) =
//        assignOnFirstIdentifier(ctx, HCode.TYPE_IDENTIFIER)
//
//
//    override fun exitClassInstanceCreationExpression_lfno_primary(ctx: JavaScriptParser.ClassInstanceCreationExpression_lfno_primaryContext?) =
//        ctx.myLoopingOnChildren(
//            targetTerminalIndex = Java8Lexer.Identifier,
//            onTerminal = { HCode.TYPE_IDENTIFIER },
//            onAddedExit = false
//        )
//
//    // +-------------+
//    // |  FUNCTIONS  |
//    //+--------------+
//    override fun exitMethodInvocation(ctx: JavaScriptParser.MethodInvocationContext?) =
//        ctx.myLoopingOnChildren(
//            targetProductionIndex = JavaScriptParser.RULE_methodName,
//            onProduction = { HCode.FUNCTION_IDENTIFIER },
//            targetTerminalIndex = Java8Lexer.Identifier,
//            onTerminal = { HCode.FUNCTION_IDENTIFIER },
//            onAddedExit = true
//        )
//
//    override fun exitMethodInvocation_lf_primary(ctx: JavaScriptParser.MethodInvocation_lf_primaryContext?) =
//        ctx.myLoopingOnChildren(
//            targetTerminalIndex = Java8Lexer.Identifier,
//            onTerminal = { HCode.FUNCTION_IDENTIFIER }
//        )
//
//    override fun exitMethodInvocation_lfno_primary(ctx: JavaScriptParser.MethodInvocation_lfno_primaryContext?) =
//        ctx.myLoopingOnChildren(
//            targetProductionIndex = JavaScriptParser.RULE_methodName,
//            onProduction = { HCode.FUNCTION_IDENTIFIER },
//            targetTerminalIndex = Java8Lexer.Identifier,
//            onTerminal = { HCode.FUNCTION_IDENTIFIER },
//            onAddedExit = true
//        )
//
//    // +----------+
//    // |  FIELDS  |
//    //+-----------+
//
//    // Field Access (Known).
//    override fun exitFieldAccess(ctx: JavaScriptParser.FieldAccessContext?) =
//        assignOnFirstIdentifier(ctx, HCode.FIELD_IDENTIFIER)
//
//    override fun exitFieldAccess_lf_primary(ctx: JavaScriptParser.FieldAccess_lf_primaryContext?) =
//        assignOnFirstIdentifier(ctx, HCode.FIELD_IDENTIFIER)
//
//    override fun exitFieldAccess_lfno_primary(ctx: JavaScriptParser.FieldAccess_lfno_primaryContext?) =
//        assignOnFirstIdentifier(ctx, HCode.FIELD_IDENTIFIER)
//
//    // Expression Name (navigates to type of field, invoked always in this context).
//    override fun exitExpressionName(ctx: JavaScriptParser.ExpressionNameContext?) {
//        val accessSeq = Stack<TerminalNode>()
//        Stack<ParserRuleContext>().let { fringe ->
//            fringe.push(ctx)
//            while (!fringe.isEmpty()) {
//                fringe.pop().myLoopingOnChildren(
//                    targetTerminalIndex = Java8Lexer.Identifier,
//                    onTerminal = { accessSeq.push(it); null },
//                    targetProductionIndex = JavaScriptParser.RULE_ambiguousName,
//                    onProduction = { fringe.push(it); null },
//                    reversed = true
//                )
//            }
//        }
//        accessSeq.removeLastOrNull()
//        accessSeq.forEach {
//            overrideOf(
//                it,
//                HCode.FIELD_IDENTIFIER,
//                JavaScriptParser.RULE_expressionName,
//                JavaScriptParser.ruleNames
//            ).addReplacing()
//        }
//    }
//
//    override fun exitNormalAnnotation(ctx: JavaScriptParser.NormalAnnotationContext?) =
//        ctx.myLoopingOnChildren(
//            targetTerminalIndex = Java8Lexer.AT,
//            onTerminal = { HCode.ANNOTATION_DECLARATOR },
//            onAddedExit = true
//        )
//
//    override fun exitMarkerAnnotation(ctx: JavaScriptParser.MarkerAnnotationContext?) =
//        ctx.myLoopingOnChildren(
//            targetTerminalIndex = Java8Lexer.AT,
//            onTerminal = { HCode.ANNOTATION_DECLARATOR },
//            onAddedExit = true
//        )
//
//    override fun exitSingleElementAnnotation(ctx: JavaScriptParser.SingleElementAnnotationContext?) =
//        ctx.myLoopingOnChildren(
//            targetTerminalIndex = Java8Lexer.AT,
//            onTerminal = { HCode.ANNOTATION_DECLARATOR },
//            onAddedExit = true
//        )
//
//    // Note this is possible thanks to reduced complexity in grammar.
//    // Otherwise: match typeName, discriminate on parent's ruleIndex.
//    override fun exitAnnotationTypeName(ctx: JavaScriptParser.AnnotationTypeNameContext?) =
//        Stack<ParserRuleContext>().let { fringe ->
//            fringe.push(ctx)
//            while (!fringe.isEmpty()) {
//                fringe.pop().myLoopingOnChildren(
//                    targetTerminalIndex = Java8Lexer.Identifier,
//                    onTerminal = { HCode.ANNOTATION_DECLARATOR },
//                    targetProductionIndex = JavaScriptParser.RULE_ambiguousName,
//                    onProduction = { fringe.push(it); null },
//                    reversed = true
//                )
//            }
//        }

}
