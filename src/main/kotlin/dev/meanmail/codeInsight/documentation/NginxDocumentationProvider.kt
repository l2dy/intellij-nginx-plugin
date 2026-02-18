package dev.meanmail.codeInsight.documentation

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.lang.documentation.DocumentationMarkup.*
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import dev.meanmail.directives.catalog.Directive
import dev.meanmail.directives.catalog.DirectiveParameter
import dev.meanmail.directives.catalog.findDirectives
import dev.meanmail.psi.DirectiveStmt

class NginxDocumentationProvider : AbstractDocumentationProvider() {

    override fun getQuickNavigateInfo(element: PsiElement?, originalElement: PsiElement?): String? {
        val directive = resolveDirective(element, originalElement) ?: return null

        return buildString {
            append("<b>").append(directive.name).append("</b>")
            append(" <i>(").append(directive.module.name).append(")</i> ")
            val description = directive.description
            append(description.substringBefore("\n\n"))
        }
    }

    override fun generateDoc(element: PsiElement?, originalElement: PsiElement?): String? {
        val directive = resolveDirective(element, originalElement) ?: return null

        return buildString {
            // Definition section
            append(DEFINITION_START)
            append(directive.name)
            append(" <span style=\"color:gray\">(${directive.module.name})</span>")
            append(DEFINITION_END)

            // Content section
            if (directive.description.isNotBlank()) {
                append(CONTENT_START)
                append(directive.description)
                append(CONTENT_END)
            }

            // Sections table
            append(SECTIONS_START)

            // Syntax row
            if (directive.syntax.isNotEmpty()) {
                append(SECTION_HEADER_START)
                append("Syntax:")
                append(SECTION_SEPARATOR)
                append(directive.syntax.joinToString("<br>"))
                append(SECTION_END)
            }

            // Context row
            append(SECTION_HEADER_START)
            append("Context:")
            append(SECTION_SEPARATOR)
            val contexts = directive.context
            if (contexts.isEmpty()) {
                append("any")
            } else {
                append(contexts.joinToString(", ") { it.name })
            }
            append(SECTION_END)

            // Parameters row
            val parameters = directive.parameters
            if (parameters.isNotEmpty()) {
                append(SECTION_HEADER_START)
                append("Parameters:")
                append(SECTION_SEPARATOR)
                appendParameterBlock(parameters)
                append(SECTION_END)
            }

            append(SECTIONS_END)
        }
    }

    private fun StringBuilder.appendParameterBlock(parameters: List<DirectiveParameter>) {
        parameters.forEachIndexed { index, parameter ->
            val name = parameter.name ?: "value"

            append("<b>").append(name).append("</b>")
            append(if (parameter.required) " (required)" else " (optional)")
            if (parameter.multiple) {
                append(" (multiple)")
            }
            parameter.defaultValue?.let {
                append(" default: ").append(it)
            }
            append("<br>")

            parameter.description?.let {
                append(it).append("<br>")
            }

            parameter.allowedValues?.takeIf { it.isNotEmpty() }?.let { values ->
                append("Allowed values: ")
                append(values.joinToString(", "))
                append("<br>")
            }

            when {
                parameter.minValue != null && parameter.maxValue != null -> {
                    append("Range: ")
                    append(parameter.minValue)
                    append(" .. ")
                    append(parameter.maxValue)
                    append("<br>")
                }

                parameter.minValue != null -> {
                    append("Minimum: ")
                    append(parameter.minValue)
                    append("<br>")
                }

                parameter.maxValue != null -> {
                    append("Maximum: ")
                    append(parameter.maxValue)
                    append("<br>")
                }
            }

            if (index < parameters.lastIndex) {
                append("<br>")
            }
        }
    }

    private fun resolveDirective(element: PsiElement?, originalElement: PsiElement?): Directive? {
        val directiveStmt = resolveDirectiveStmt(element, originalElement) ?: return null
        val name = directiveStmt.name ?: return null
        val contextPath = directiveStmt.path.dropLast(1)

        return findDirectives(name, contextPath).firstOrNull()
    }

    private fun resolveDirectiveStmt(element: PsiElement?, originalElement: PsiElement?): DirectiveStmt? {
        if (element is DirectiveStmt) return element
        element ?: return null

        val directiveStmt = PsiTreeUtil.getParentOfType(element, DirectiveStmt::class.java)
            ?: return null
        val nameIdentifier = directiveStmt.nameIdentifier ?: return null
        if (!PsiTreeUtil.isAncestor(element, nameIdentifier, false)) return null

        return directiveStmt
    }
}
