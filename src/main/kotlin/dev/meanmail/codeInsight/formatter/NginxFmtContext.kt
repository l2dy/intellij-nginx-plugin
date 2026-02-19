package dev.meanmail.codeInsight.formatter

import com.intellij.formatting.SpacingBuilder
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CommonCodeStyleSettings
import dev.meanmail.NginxLanguage

data class NginxFmtContext(
    val commonSettings: CommonCodeStyleSettings,
    val spacingBuilder: SpacingBuilder,
) {
    companion object {
        fun create(settings: CodeStyleSettings): NginxFmtContext {
            val common = settings.getCommonSettings(NginxLanguage)
            return NginxFmtContext(common, createSpacingBuilder(settings))
        }
    }
}
