package dev.meanmail.typing.assist

import com.intellij.application.options.CodeStyle
import com.intellij.lang.ASTNode
import com.intellij.lang.SmartEnterProcessorWithFixers
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.util.PsiTreeUtil
import dev.meanmail.directives.catalog.findDirectives
import dev.meanmail.psi.DirectiveStmt
import dev.meanmail.psi.DirectiveStmtElement
import dev.meanmail.psi.Types

class NginxSmartEnterProcessor : SmartEnterProcessorWithFixers() {
    init {
        addFixers(BlockFixer(), SemicolonFixer())
        addEnterProcessors(AfterBlockEnterProcessor(), AfterSemicolonEnterProcessor(), PlainEnterProcessor())
    }

    override fun reformatBeforeEnter(atCaret: PsiElement): Boolean = false

    override fun getStatementAtCaret(editor: Editor?, psiFile: PsiFile?): PsiElement? {
        if (editor == null || psiFile == null) return null
        val offset = editor.caretModel.offset

        for (off in listOf(offset, offset - 1)) {
            if (off < 0) continue
            val element = psiFile.findElementAt(off) ?: continue
            val directive = PsiTreeUtil.getParentOfType(
                element, DirectiveStmtElement::class.java, false
            )
            if (directive != null) return directive
        }

        return psiFile.findElementAt(offset)
    }

    private class BlockFixer : Fixer<NginxSmartEnterProcessor>() {
        override fun apply(editor: Editor, processor: NginxSmartEnterProcessor, element: PsiElement) {
            val directive = element as? DirectiveStmt ?: return

            val ifDirective = directive.ifDirectiveStmt
            if (ifDirective != null) {
                if (ifDirective.blockStmt == null) {
                    editor.document.insertString(ifDirective.textRange.endOffset, " {\n}")
                }
                return
            }

            val locationDirective = directive.locationDirectiveStmt
            if (locationDirective != null) {
                if (locationDirective.blockStmt == null) {
                    editor.document.insertString(locationDirective.textRange.endOffset, " {\n}")
                }
                return
            }

            val mapDirective = directive.mapDirectiveStmt
            if (mapDirective != null) {
                if (mapDirective.mapBlockStmt == null) {
                    editor.document.insertString(mapDirective.textRange.endOffset, " {\n}")
                }
                return
            }

            val geoDirective = directive.geoDirectiveStmt
            if (geoDirective != null) {
                if (geoDirective.geoBlockStmt == null) {
                    editor.document.insertString(geoDirective.textRange.endOffset, " {\n}")
                }
                return
            }

            val typesDirective = directive.typesDirectiveStmt
            if (typesDirective != null) {
                if (typesDirective.typesBlockStmt == null) {
                    editor.document.insertString(typesDirective.textRange.endOffset, " {\n}")
                }
                return
            }

            val regularDirective = directive.regularDirectiveStmt
            if (regularDirective != null) {
                if (regularDirective.blockStmt != null) return
                if (regularDirective.node.findChildByType(Types.SEMICOLON) != null) return
                val name = regularDirective.nameStmt.text
                val directives = findDirectives(name)
                if (directives.any { d -> d.children.any { child -> d in child.context } }) {
                    editor.document.insertString(regularDirective.textRange.endOffset, " {\n}")
                }
                return
            }
        }
    }

    private class SemicolonFixer : Fixer<NginxSmartEnterProcessor>() {
        override fun apply(editor: Editor, processor: NginxSmartEnterProcessor, element: PsiElement) {
            val directive = element as? DirectiveStmt ?: return
            val caretOffset = editor.caretModel.offset
            val doc = editor.document
            val lineEndOffset = doc.getLineEndOffset(doc.getLineNumber(caretOffset))

            val regularDirective = directive.regularDirectiveStmt
            if (regularDirective != null) {
                if (regularDirective.blockStmt != null) return
                val semicolonNode = regularDirective.node.findChildByType(Types.SEMICOLON)
                if (semicolonNode != null && semicolonNode.startOffset <= lineEndOffset) return
                val insertOffset = lastContentOffsetBefore(regularDirective.node, lineEndOffset)
                    .takeIf { it > 0 } ?: lineEndOffset
                editor.document.insertString(insertOffset, ";")
                return
            }

            val varStmt = directive.varStmt
            if (varStmt != null) {
                val semicolonNode = varStmt.node.findChildByType(Types.SEMICOLON)
                if (semicolonNode != null && semicolonNode.startOffset <= lineEndOffset) return
                val insertOffset = lastContentOffsetBefore(varStmt.node, lineEndOffset)
                    .takeIf { it > 0 } ?: lineEndOffset
                editor.document.insertString(insertOffset, ";")
                return
            }
        }

        private fun lastContentOffsetBefore(node: ASTNode, offset: Int): Int {
            if (node.firstChildNode == null) {
                return if (node.elementType != TokenType.WHITE_SPACE && node.startOffset < offset) {
                    node.startOffset + node.textLength
                } else -1
            }
            var best = -1
            var child = node.firstChildNode
            while (child != null && child.startOffset < offset) {
                if (child.elementType != TokenType.WHITE_SPACE) {
                    val end = lastContentOffsetBefore(child, offset)
                    if (end > best) best = end
                }
                child = child.treeNext
            }
            return best
        }
    }

    private class AfterBlockEnterProcessor : FixEnterProcessor() {
        override fun doEnter(
            atCaret: PsiElement,
            file: PsiFile,
            editor: Editor,
            modified: Boolean,
        ): Boolean {
            if (!modified) return false
            val directive = atCaret as? DirectiveStmtElement ?: return false
            if (!directive.text.endsWith("}")) return false

            val doc = editor.document
            val lineNumber = doc.getLineNumber(directive.textRange.startOffset)
            val lineStart = doc.getLineStartOffset(lineNumber)
            val baseIndent = doc.getText(TextRange(lineStart, directive.textRange.startOffset))
            val indentOptions = CodeStyle.getIndentOptions(file)
            val oneIndent = if (indentOptions.USE_TAB_CHARACTER) "\t" else " ".repeat(indentOptions.INDENT_SIZE)
            val innerIndent = baseIndent + oneIndent

            val rbrace = directive.textRange.endOffset - 1
            doc.insertString(rbrace, "$innerIndent\n$baseIndent")
            editor.caretModel.moveToOffset(rbrace + innerIndent.length)
            return true
        }
    }

    private class AfterSemicolonEnterProcessor : FixEnterProcessor() {
        override fun doEnter(
            atCaret: PsiElement,
            file: PsiFile,
            editor: Editor,
            modified: Boolean,
        ): Boolean {
            if (!modified) return false

            val directive = atCaret as? DirectiveStmtElement ?: return false
            editor.caretModel.moveToOffset(directive.textRange.endOffset)
            plainEnter(editor)
            return true
        }
    }

    private class PlainEnterProcessor : FixEnterProcessor() {
        override fun doEnter(
            atCaret: PsiElement,
            file: PsiFile,
            editor: Editor,
            modified: Boolean,
        ): Boolean {
            plainEnter(editor)
            return true
        }
    }
}
