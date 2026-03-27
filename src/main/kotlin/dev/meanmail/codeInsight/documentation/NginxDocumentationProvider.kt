package dev.meanmail.codeInsight.documentation

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.lang.documentation.DocumentationMarkup.*
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.util.PsiTreeUtil
import dev.meanmail.directives.catalog.Directive
import dev.meanmail.directives.catalog.DirectiveParameter
import dev.meanmail.directives.catalog.findDirectives
import dev.meanmail.psi.DirectiveStmt
import dev.meanmail.psi.VariableStmt
import dev.meanmail.variables.catalog.Variable
import dev.meanmail.variables.catalog.findVariables

class NginxDocumentationProvider : AbstractDocumentationProvider() {

    override fun getCustomDocumentationElement(
        editor: Editor,
        file: PsiFile,
        contextElement: PsiElement?,
        targetOffset: Int,
    ): PsiElement? {
        contextElement ?: return null

        PsiTreeUtil.getParentOfType(contextElement, VariableStmt::class.java)
            ?.let { return it }

        val directiveStmt = PsiTreeUtil.getParentOfType(contextElement, DirectiveStmt::class.java)
            ?: return null
        val nameIdentifier = directiveStmt.nameIdentifier ?: return null
        if (PsiTreeUtil.isAncestor(nameIdentifier, contextElement, false)) {
            return directiveStmt
        }

        return null
    }

    override fun getUrlFor(element: PsiElement?, originalElement: PsiElement?): List<String>? {
        val directive = resolveDirective(element, originalElement)
        if (directive != null) {
            val moduleUrl = directive.module.url ?: return null
            return listOf("$moduleUrl#${directive.name}")
        }

        val (variable, _) = resolveVariable(element, originalElement) ?: return null
        val moduleUrl = variable.module.url ?: return null
        return listOf("$moduleUrl#var_${variable.name}")
    }

    override fun getQuickNavigateInfo(element: PsiElement?, originalElement: PsiElement?): String? {
        val directive = resolveDirective(element, originalElement)
        if (directive != null) {
            return buildString {
                append("<b>").append(directive.name).append("</b>")
                append(" <i>(").append(directive.module.name).append(")</i> ")
                append(directive.description.substringBefore("\n\n"))
            }
        }

        val (variable, _) = resolveVariable(element, originalElement) ?: return null
        return buildString {
            append("<b>\$").append(variable.name).append("</b>")
            append(" <i>(").append(variable.module.name).append(")</i> ")
            append(variable.description.substringBefore("\n\n"))
        }
    }

    override fun generateDoc(element: PsiElement?, originalElement: PsiElement?): String? {
        val directive = resolveDirective(element, originalElement)
        if (directive != null) {
            return generateDirectiveDoc(directive)
        }

        val (variable, usedName) = resolveVariable(element, originalElement) ?: return null
        return generateVariableDoc(variable, usedName)
    }

    private fun generateDirectiveDoc(directive: Directive): String {
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

    private fun generateVariableDoc(variable: Variable, usedName: String): String {
        return buildString {
            // Definition section
            append(DEFINITION_START)
            append("\$").append(usedName)
            if (variable.parameterized) {
                append("*")
            }
            append(" <span style=\"color:gray\">(${variable.module.name})</span>")
            append(DEFINITION_END)

            // Content section — substitute parameter suffix into {} placeholder
            if (variable.description.isNotBlank()) {
                val description = if (variable.parameterized) {
                    val suffix = usedName.removePrefix(variable.name)
                    if (suffix.isNotEmpty()) {
                        variable.description.replace("{}", "'$suffix'")
                    } else {
                        variable.description.replace("{}", "…")
                    }
                } else {
                    variable.description
                }
                append(CONTENT_START)
                append(description)
                append(CONTENT_END)
            }

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

    private fun resolveVariable(element: PsiElement?, originalElement: PsiElement?): Pair<Variable, String>? {
        val stmt = if (element is VariableStmt) element
            else PsiTreeUtil.getParentOfType(originalElement, VariableStmt::class.java)
        val name = (stmt as? PsiNamedElement)?.name ?: return null
        val variable = findVariables(name).firstOrNull() ?: return null
        return variable to name
    }
}
