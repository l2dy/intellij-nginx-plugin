package dev.meanmail.typing

import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegate
import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegateAdapter
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile
import dev.meanmail.psi.NginxFile

class NginxEnterInLineCommentHandler : EnterHandlerDelegateAdapter() {

    override fun postProcessEnter(
        file: PsiFile,
        editor: Editor,
        dataContext: DataContext,
    ): EnterHandlerDelegate.Result {
        if (file !is NginxFile) return EnterHandlerDelegate.Result.Continue

        val caretModel = editor.caretModel
        val document = editor.document
        val caretOffset = caretModel.offset
        val caretLine = document.getLineNumber(caretOffset)
        if (caretLine == 0) return EnterHandlerDelegate.Result.Continue

        // Check the previous line for a comment
        val prevLineNumber = caretLine - 1
        val prevLineStart = document.getLineStartOffset(prevLineNumber)
        val prevLineEnd = document.getLineEndOffset(prevLineNumber)
        val prevLineText = document.charsSequence.subSequence(prevLineStart, prevLineEnd)

        val trimmed = prevLineText.trimStart()
        if (!trimmed.startsWith("#")) return EnterHandlerDelegate.Result.Continue

        // Check if there's non-whitespace content after the caret on the current line
        // (meaning we split a comment, not just pressed Enter at the end)
        val currentLineEnd = document.getLineEndOffset(caretLine)
        val textAfterCaret = document.charsSequence.subSequence(caretOffset, currentLineEnd)
        if (textAfterCaret.isBlank()) return EnterHandlerDelegate.Result.Continue

        // Compute indentation from the previous comment line
        val indent = prevLineText.takeWhile { it == ' ' || it == '\t' }
        val prefix = "$indent# "

        // Replace any auto-indentation with the correct indent + # prefix
        val currentLineStart = document.getLineStartOffset(caretLine)
        document.replaceString(currentLineStart, caretOffset, prefix)
        caretModel.moveToOffset(currentLineStart + prefix.length)

        return EnterHandlerDelegate.Result.Continue
    }
}
