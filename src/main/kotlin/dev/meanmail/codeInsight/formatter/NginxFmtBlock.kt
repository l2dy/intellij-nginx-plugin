package dev.meanmail.codeInsight.formatter

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.formatter.common.AbstractBlock
import dev.meanmail.psi.Types

class NginxFmtBlock(
    node: ASTNode,
    alignment: Alignment?,
    indent: Indent?,
    wrap: Wrap?,
    val ctx: NginxFmtContext,
) : AbstractBlock(node, wrap, alignment) {

    private val myIndent = indent ?: Indent.getNoneIndent()

    override fun getIndent(): Indent = myIndent

    override fun buildChildren(): List<Block> {
        if (myNode.elementType == Types.LUA_CODE_STMT) {
            return emptyList()
        }

        val blocks = mutableListOf<Block>()
        var child = myNode.firstChildNode
        while (child != null) {
            if (child.elementType != TokenType.WHITE_SPACE) {
                blocks.add(
                    NginxFmtBlock(
                        child,
                        null,
                        computeChildIndent(child),
                        null,
                        ctx,
                    )
                )
            }
            child = child.treeNext
        }
        return blocks
    }

    private fun computeChildIndent(child: ASTNode): Indent {
        val parentType = myNode.elementType
        val childType = child.elementType

        if (BRACE_BLOCKS.contains(parentType)) {
            return if (childType == Types.LBRACE || childType == Types.RBRACE)
                Indent.getNoneIndent()
            else
                Indent.getNormalIndent()
        }

        if (PAREN_BLOCKS.contains(parentType)) {
            return if (childType == Types.LPAREN || childType == Types.RPAREN)
                Indent.getNoneIndent()
            else
                Indent.getNormalIndent()
        }

        return Indent.getNoneIndent()
    }

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        if (myNode.elementType == Types.LUA_BLOCK_STMT) {
            return Spacing.getReadOnlySpacing()
        }

        // Preserve inline comments on the same line as {
        // e.g. "location / { # comment" should not reflow the comment to a new line
        if (child1 != null && BRACE_BLOCKS.contains(myNode.elementType)
            && ASTBlock.getElementType(child1) == Types.LBRACE
            && ASTBlock.getElementType(child2) == Types.COMMENT
        ) {
            val next = ASTBlock.getNode(child1)?.treeNext
            val hasNewline = next?.elementType == TokenType.WHITE_SPACE && next.text.contains('\n')
            if (!hasNewline) {
                return Spacing.getReadOnlySpacing()
            }
        }

        val spacing = ctx.spacingBuilder.getSpacing(this, child1, child2)
        if (spacing != null) return spacing

        val type = myNode.elementType
        if (!BRACE_BLOCKS.contains(type) && !PAREN_BLOCKS.contains(type)) {
            return Spacing.getReadOnlySpacing()
        }

        return null
    }

    override fun getChildAttributes(newChildIndex: Int): ChildAttributes {
        val type = myNode.elementType
        val indent = when {
            BRACE_BLOCKS.contains(type) -> Indent.getNormalIndent()
            PAREN_BLOCKS.contains(type) -> Indent.getNormalIndent()
            else -> Indent.getNoneIndent()
        }
        return ChildAttributes(indent, null)
    }

    override fun isLeaf(): Boolean = myNode.firstChildNode == null
}
