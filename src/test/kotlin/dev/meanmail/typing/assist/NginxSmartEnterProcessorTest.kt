package dev.meanmail.typing.assist

import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class NginxSmartEnterProcessorTest : BasePlatformTestCase() {

    private fun doTest(before: String, after: String) {
        myFixture.configureByText("nginx.conf", before)
        myFixture.performEditorAction(IdeActions.ACTION_EDITOR_COMPLETE_STATEMENT)
        myFixture.checkResult(after)
    }

    fun testAddSemicolon() {
        doTest(
            "listen 80<caret>",
            "listen 80;\n<caret>"
        )
    }

    fun testNoDoubleSemicolon() {
        doTest(
            "listen 80;<caret>",
            "listen 80;\n<caret>"
        )
    }

    fun testAddSemicolonToServerName() {
        doTest(
            "server_name example.com<caret>",
            "server_name example.com;\n<caret>"
        )
    }

    fun testBlockCompletionServer() {
        doTest(
            """
            http {
                server<caret>
            }
            """.trimIndent(),
            """
            http {
                server {
                    <caret>
                }
            }
            """.trimIndent()
        )
    }

    fun testBlockCompletionHttp() {
        doTest(
            "http<caret>",
            "http {\n    <caret>\n}"
        )
    }

    fun testBlockCompletionIf() {
        doTest(
            "if (\$var)<caret>",
            "if (\$var) {\n    <caret>\n}"
        )
    }

    fun testSemicolonNotBlock() {
        doTest(
            "listen 80<caret>",
            "listen 80;\n<caret>"
        )
    }
}
