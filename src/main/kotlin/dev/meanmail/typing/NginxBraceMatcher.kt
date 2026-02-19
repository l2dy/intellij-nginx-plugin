package dev.meanmail.typing

import com.intellij.codeInsight.highlighting.PairedBraceMatcherAdapter
import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import dev.meanmail.NginxLanguage
import dev.meanmail.psi.Types

private class NginxBaseBraceMatcher : PairedBraceMatcher {
    override fun getPairs(): Array<BracePair> = arrayOf(
        BracePair(Types.LBRACE, Types.RBRACE, true),
        BracePair(Types.LPAREN, Types.RPAREN, false),
    )

    private val allowedBeforeTypes = TokenSet.orSet(
        TokenSet.WHITE_SPACE,
        TokenSet.create(
            Types.SEMICOLON,
            Types.RBRACE,
            Types.RPAREN,
            Types.COMMENT,
        )
    )

    override fun isPairedBracesAllowedBeforeType(
        lbraceType: IElementType,
        contextType: IElementType?,
    ): Boolean {
        return contextType == null || contextType in allowedBeforeTypes
    }

    override fun getCodeConstructStart(
        file: PsiFile?,
        openingBraceOffset: Int,
    ): Int = openingBraceOffset
}

class NginxBraceMatcher : PairedBraceMatcherAdapter(NginxBaseBraceMatcher(), NginxLanguage)
