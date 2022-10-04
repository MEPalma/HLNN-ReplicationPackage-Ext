package highlighter.csharphighlighter

import CSharpLexer
import common.ETA
import common.HCode.*
import common.HCode.Companion.hetaOf
import common.HETA

fun csharpLexicalHighlighter(eta: ETA): HETA =
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
            CSharpLexer.LITERAL_ACCESS,
            CSharpLexer.INTEGER_LITERAL,
            CSharpLexer.HEX_INTEGER_LITERAL,
            CSharpLexer.BIN_INTEGER_LITERAL,
            CSharpLexer.REAL_LITERAL,
            CSharpLexer.NULL_,
            CSharpLexer.TRUE,
            CSharpLexer.FALSE,
        ) -> hetaOf(eta, LITERAL)
        in hashSetOf(
            CSharpLexer.CHARACTER_LITERAL,
            CSharpLexer.REGULAR_STRING,
            CSharpLexer.VERBATIUM_STRING,
            CSharpLexer.INTERPOLATED_REGULAR_STRING_START,
            CSharpLexer.INTERPOLATED_VERBATIUM_STRING_START
        ) -> hetaOf(eta, CHAR_STRING_LITERAL)
        in hashSetOf(
            CSharpLexer.SINGLE_LINE_DOC_COMMENT,
            CSharpLexer.EMPTY_DELIMITED_DOC_COMMENT,
            CSharpLexer.DELIMITED_DOC_COMMENT,
            CSharpLexer.SINGLE_LINE_COMMENT,
            CSharpLexer.DELIMITED_COMMENT,
        ) -> hetaOf(eta, COMMENT)
        else -> hetaOf(eta, ANY)
    }
