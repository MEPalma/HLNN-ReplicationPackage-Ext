package highlighter.csharphighlighter

import CSharpLexer
import common.ETA
import common.HCode.*
import common.HCode.Companion.hetaOf
import common.HETA

fun javaLexicalHighlighter(eta: ETA): HETA =
    when (eta.tokenRule) {
        in hashSetOf(
            CSharpLexer.ABSTRACT,
            CSharpLexer.AS,
            CSharpLexer.BASE,
            CSharpLexer.BOOL,
            CSharpLexer.BREAK,
            CSharpLexer.BYTE,
            CSharpLexer.CASE,
            CSharpLexer.CATCH,
            CSharpLexer.CHAR,
            CSharpLexer.CHECKED,
            CSharpLexer.CLASS,
            CSharpLexer.CONST,
            CSharpLexer.CONTINUE,
            CSharpLexer.DECIMAL,
            CSharpLexer.DEFAULT,
            CSharpLexer.DELEGATE,
            CSharpLexer.DO,
            CSharpLexer.DOUBLE,
            CSharpLexer.ELSE,
            CSharpLexer.ENUM,
            CSharpLexer.EVENT,
            CSharpLexer.EXPLICIT,
            CSharpLexer.EXTERN,
            CSharpLexer.FALSE,
            CSharpLexer.FINALLY,
            CSharpLexer.FIXED,
            CSharpLexer.FLOAT,
            CSharpLexer.FOR,
            CSharpLexer.FOREACH,
            CSharpLexer.GOTO,
            CSharpLexer.IF,
            CSharpLexer.IMPLICIT,
            CSharpLexer.IN,
            CSharpLexer.INT,
            CSharpLexer.INTERFACE,
            CSharpLexer.INTERNAL,
            CSharpLexer.IS,
            CSharpLexer.LOCK,
            CSharpLexer.LONG,
            CSharpLexer.NAMESPACE,
            CSharpLexer.NEW,
            CSharpLexer.NULL_,
            CSharpLexer.OBJECT,
            CSharpLexer.OPERATOR,
            CSharpLexer.OUT,
            CSharpLexer.OVERRIDE,
            CSharpLexer.PARAMS,
            CSharpLexer.PRIVATE,
            CSharpLexer.PROTECTED,
            CSharpLexer.PUBLIC,
            CSharpLexer.READONLY,
            CSharpLexer.REF,
            CSharpLexer.RETURN,
            CSharpLexer.SBYTE,
            CSharpLexer.SEALED,
            CSharpLexer.SHORT,
            CSharpLexer.SIZEOF,
            CSharpLexer.STACKALLOC,
            CSharpLexer.STATIC,
            CSharpLexer.STRING,
            CSharpLexer.STRUCT,
            CSharpLexer.SWITCH,
            CSharpLexer.THIS,
            CSharpLexer.THROW,
            CSharpLexer.TRUE,
            CSharpLexer.TRY,
            CSharpLexer.TYPEOF,
            CSharpLexer.UINT,
            CSharpLexer.ULONG,
            CSharpLexer.UNCHECKED,
            CSharpLexer.UNSAFE,
            CSharpLexer.USHORT,
            CSharpLexer.USING,
            CSharpLexer.VIRTUAL,
            CSharpLexer.VOID,
            CSharpLexer.VOLATILE,
            CSharpLexer.WHILE
        ) -> hetaOf(eta, KEYWORD)
        in hashSetOf(
            Java8Lexer.IntegerLiteral,
            Java8Lexer.BooleanLiteral,
            Java8Lexer.BooleanLiteral,
            Java8Lexer.FloatingPointLiteral,
            Java8Lexer.NullLiteral
        ) -> hetaOf(eta, LITERAL)
        in hashSetOf(
            Java8Lexer.StringLiteral, Java8Lexer.CharacterLiteral
        ) -> hetaOf(eta, CHAR_STRING_LITERAL)
        in hashSetOf(
            Java8Lexer.COMMENT, Java8Lexer.LINE_COMMENT
        ) -> hetaOf(eta, COMMENT)
        else -> hetaOf(eta, ANY)
    }
