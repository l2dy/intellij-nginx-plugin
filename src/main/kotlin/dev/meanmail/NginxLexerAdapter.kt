package dev.meanmail

import com.intellij.lexer.FlexAdapter

import java.io.Reader

class NginxLexerAdapter : FlexAdapter(NginxLexer(null as Reader?)) {
    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        (flex as NginxLexer).clearLexerState()
        super.start(buffer, startOffset, endOffset, initialState)
    }
}
