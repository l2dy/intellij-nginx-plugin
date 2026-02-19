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
}
