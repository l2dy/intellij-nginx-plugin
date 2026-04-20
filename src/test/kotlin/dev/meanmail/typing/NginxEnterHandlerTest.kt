package dev.meanmail.typing

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class NginxEnterHandlerTest : BasePlatformTestCase() {

    fun testEnterMidComment() {
        myFixture.configureByText(
            "nginx.conf",
            "# mid<caret>dle of comment"
        )
        myFixture.type('\n')
        myFixture.checkResult(
            "# mid\n# <caret>dle of comment"
        )
    }

    fun testEnterEndOfComment() {
        myFixture.configureByText(
            "nginx.conf",
            "# end of comment<caret>"
        )
        myFixture.type('\n')
        myFixture.checkResult(
            "# end of comment\n<caret>"
        )
    }

    fun testEnterIndentedComment() {
        myFixture.configureByText(
            "nginx.conf",
            """
            http {
                # indented<caret> comment
            }
            """.trimIndent()
        )
        myFixture.type('\n')
        myFixture.checkResult(
            """
            http {
                # indented
                # <caret>comment
            }
            """.trimIndent()
        )
    }

    fun testEnterBetweenBraces() {
        myFixture.configureByText(
            "nginx.conf",
            "server {<caret>}"
        )
        myFixture.type('\n')
        myFixture.checkResult(
            "server {\n    <caret>\n}"
        )
    }

    fun testEnterBetweenBracesLocation() {
        myFixture.configureByText(
            "nginx.conf",
            "location / {<caret>}"
        )
        myFixture.type('\n')
        myFixture.checkResult(
            "location / {\n    <caret>\n}"
        )
    }

    fun testEnterBetweenBracesNested() {
        myFixture.configureByText(
            "nginx.conf",
            """
            http {
                server {<caret>}
            }
            """.trimIndent()
        )
        myFixture.type('\n')
        myFixture.checkResult(
            """
            http {
                server {
                    <caret>
                }
            }
            """.trimIndent()
        )
    }

    fun testBraceAutoCompleteThenEnter() {
        myFixture.configureByText("nginx.conf", "server<caret>")
        myFixture.type('{')
        myFixture.type('\n')
        myFixture.checkResult("server{\n    <caret>\n}")
    }

    fun testBraceAutoCompleteNestedThenEnter() {
        myFixture.configureByText(
            "nginx.conf",
            "http {\n    server<caret>\n}"
        )
        myFixture.type('{')
        myFixture.type('\n')
        myFixture.checkResult("http {\n    server{\n        <caret>\n    }\n}")
    }

    fun testEnterBetweenBracesInsideComment() {
        myFixture.configureByText(
            "nginx.conf",
            "# {<caret>}"
        )
        myFixture.type('\n')
        myFixture.checkResult(
            "# {\n# <caret>}"
        )
    }
}
