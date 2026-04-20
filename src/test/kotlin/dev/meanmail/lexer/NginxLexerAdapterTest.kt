package dev.meanmail.lexer

import com.intellij.psi.TokenType.WHITE_SPACE
import com.intellij.psi.tree.IElementType
import dev.meanmail.NginxLexerAdapter
import org.junit.Assert.assertEquals
import org.junit.Test

class NginxLexerAdapterTest {

    private fun lex(adapter: NginxLexerAdapter, text: String): List<Pair<String, String>> {
        adapter.start(text)
        val tokens = mutableListOf<Pair<String, String>>()
        var type: IElementType? = adapter.tokenType
        while (type != null) {
            if (type != WHITE_SPACE) {
                tokens += type.toString().substringAfter(".") to text.substring(adapter.tokenStart, adapter.tokenEnd)
            }
            adapter.advance()
            type = adapter.tokenType
        }
        return tokens
    }

    @Test
    fun testAdapterClearsStateAcrossReuse() {
        val adapter = NginxLexerAdapter()
        // First lex leaves stack=[DIRECTIVE_STATE] (IDENTIFIER pushes it; no terminator pops).
        // Without clearing, the next lex of "server{}" would have LBRACE's yypop() consume the
        // stale entry, leaving zzLexicalState=DIRECTIVE_STATE for "}", which falls through to
        // the global catch-all rule and emits BAD_CHARACTER instead of RBRACE.
        lex(adapter, "server")
        val tokens = lex(adapter, "server{}")
        assertEquals(
            listOf(
                "IDENTIFIER" to "server",
                "LBRACE" to "{",
                "RBRACE" to "}",
            ),
            tokens,
        )
    }
}
