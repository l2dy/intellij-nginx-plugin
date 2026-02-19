package dev.meanmail.codeInsight.formatter

import com.intellij.formatting.SpacingBuilder
import com.intellij.psi.codeStyle.CodeStyleSettings
import dev.meanmail.NginxLanguage
import dev.meanmail.psi.Types

fun createSpacingBuilder(settings: CodeStyleSettings): SpacingBuilder {
    val common = settings.getCommonSettings(NginxLanguage)
    val keepLineBreaks = common.KEEP_LINE_BREAKS
    val keepBlankLines = common.KEEP_BLANK_LINES_IN_CODE
    return SpacingBuilder(settings, NginxLanguage)
        .afterInside(Types.LBRACE, BRACE_BLOCKS)
        .parentDependentLFSpacing(1, 1, true, keepBlankLines)
        .beforeInside(Types.RBRACE, BRACE_BLOCKS)
        .parentDependentLFSpacing(1, 1, true, keepBlankLines)
        .before(Types.SEMICOLON).spaceIf(false)
        .before(BRACE_BLOCKS).spacing(1, Int.MAX_VALUE, 0, keepLineBreaks, keepBlankLines)
}
