package dev.meanmail.codeInsight.formatter

import com.intellij.lang.ASTNode
import com.intellij.psi.tree.TokenSet
import dev.meanmail.psi.Types

val BRACE_BLOCKS = TokenSet.create(
    Types.BLOCK_STMT,
    Types.MAP_BLOCK_STMT,
    Types.NUM_MAP_BLOCK_STMT,
    Types.GEO_BLOCK_STMT,
    Types.TYPES_BLOCK_STMT,
    Types.LUA_BLOCK_STMT,
)

val PAREN_BLOCKS = TokenSet.create(
    Types.IF_PAREN_STMT,
    Types.REGEX_GROUP,
)

fun ASTNode.isBlockDelim(): Boolean {
    val type = elementType
    val parentType = treeParent?.elementType ?: return false
    return (type == Types.LBRACE || type == Types.RBRACE) && BRACE_BLOCKS.contains(parentType)
            || (type == Types.LPAREN || type == Types.RPAREN) && PAREN_BLOCKS.contains(parentType)
}
