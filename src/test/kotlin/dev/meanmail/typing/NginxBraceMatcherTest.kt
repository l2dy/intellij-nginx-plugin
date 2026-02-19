package dev.meanmail.typing

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class NginxBraceMatcherTest : BasePlatformTestCase() {

    fun testAutoCloseBrace() {
        myFixture.configureByText("nginx.conf", "server <caret>")
        myFixture.type('{')
        myFixture.checkResult("server {<caret>}")
    }

    fun testAutoCloseBraceNoDoubleInsert() {
        myFixture.configureByText("nginx.conf", "server <caret>}")
        myFixture.type('{')
        myFixture.checkResult("server {<caret>}")
    }

    fun testAutoCloseParen() {
        myFixture.configureByText(
            "nginx.conf",
            """
            http {
                server {
                    if <caret> {
                    }
                }
            }
            """.trimIndent()
        )
        myFixture.type('(')
        myFixture.checkResult(
            """
            http {
                server {
                    if (<caret>) {
                    }
                }
            }
            """.trimIndent()
        )
    }

    fun testAutoCloseBraceAfterDirective() {
        myFixture.configureByText("nginx.conf", "events <caret>")
        myFixture.type('{')
        myFixture.checkResult("events {<caret>}")
    }
}
