package dev.meanmail.typing

import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler
import dev.meanmail.psi.Types

class NginxQuoteHandler : SimpleTokenSetQuoteHandler(Types.QUOTE, Types.DQUOTE)
