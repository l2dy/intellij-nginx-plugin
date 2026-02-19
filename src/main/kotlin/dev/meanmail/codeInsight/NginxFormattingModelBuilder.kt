package dev.meanmail.codeInsight

import com.intellij.formatting.*
import dev.meanmail.codeInsight.formatter.NginxFmtBlock
import dev.meanmail.codeInsight.formatter.NginxFmtContext

class NginxFormattingModelBuilder : FormattingModelBuilder {
    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val file = formattingContext.psiElement.containingFile
        val ctx = NginxFmtContext.create(formattingContext.codeStyleSettings)
        val block = NginxFmtBlock(file.node, null, Indent.getNoneIndent(), null, ctx)
        return FormattingModelProvider.createFormattingModelForPsiFile(
            file, block, formattingContext.codeStyleSettings
        )
    }
}
