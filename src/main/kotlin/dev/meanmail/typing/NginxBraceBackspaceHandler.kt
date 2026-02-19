package dev.meanmail.typing

import com.intellij.codeInsight.editorActions.BackspaceHandlerDelegate
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile
import dev.meanmail.psi.NginxFile

class NginxBraceBackspaceHandler : BackspaceHandlerDelegate() {
    override fun beforeCharDeleted(c: Char, file: PsiFile, editor: Editor) {
    }

    override fun charDeleted(c: Char, file: PsiFile, editor: Editor): Boolean {
        if (file !is NginxFile) return false
        if (c != '{' && c != '(') return false

        val offset = editor.caretModel.offset
        val document = editor.document
        if (offset >= document.textLength) return false

        val matchingClose = if (c == '{') '}' else ')'
        if (document.charsSequence[offset] == matchingClose) {
            document.deleteString(offset, offset + 1)
            return true
        }

        return false
    }
}
