package dev.meanmail.lang.injection

import com.intellij.lang.Language
import com.intellij.lang.injection.MultiHostInjector
import com.intellij.lang.injection.MultiHostRegistrar
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import dev.meanmail.psi.LuaCodeStmt
import kotlin.math.min

class NginxLuaInjector : MultiHostInjector {

    override fun getLanguagesToInject(registrar: MultiHostRegistrar, context: PsiElement) {
        val luaLanguage = Language.findLanguageByID("Lua") ?: return
        if (context !is LuaCodeStmt) return

        val text = context.text
        val lines = text.lines()

        val firstContentLine = lines.indexOfFirst { it.isNotBlank() }
        val lastContentLine = lines.indexOfLast { it.isNotBlank() }

        if (firstContentLine == -1 || lastContentLine == -1) return

        val startOffset = lines
            .take(firstContentLine)
            .sumOf { it.length + 1 } // include newline

        val endOffsetInclusive = lines
            .take(lastContentLine + 1)
            .sumOf { it.length + 1 }

        val range = TextRange(
            startOffset,
            min(endOffsetInclusive, text.length)
        )

        registrar
            .startInjecting(luaLanguage)
            .addPlace(null, null, context, range)
            .doneInjecting()
    }

    override fun elementsToInjectIn(): MutableList<out Class<out PsiElement>> {
        return mutableListOf(LuaCodeStmt::class.java)
    }
}
