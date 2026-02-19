package dev.meanmail.typing

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class NginxQuoteHandlerTest : BasePlatformTestCase() {

    fun testAutoCloseDoubleQuote() {
        myFixture.configureByText(
            "nginx.conf",
            """
            http {
                server {
                    server_name <caret>;
                }
            }
            """.trimIndent()
        )
        myFixture.type('"')
        myFixture.checkResult(
            """
            http {
                server {
                    server_name "<caret>";
                }
            }
            """.trimIndent()
        )
    }

    fun testAutoCloseSingleQuote() {
        myFixture.configureByText(
            "nginx.conf",
            """
            http {
                server {
                    server_name <caret>;
                }
            }
            """.trimIndent()
        )
        myFixture.type('\'')
        myFixture.checkResult(
            """
            http {
                server {
                    server_name '<caret>';
                }
            }
            """.trimIndent()
        )
    }

    fun testSkipOverClosingDoubleQuote() {
        myFixture.configureByText(
            "nginx.conf",
            """
            http {
                server {
                    server_name "example.com<caret>";
                }
            }
            """.trimIndent()
        )
        myFixture.type('"')
        myFixture.checkResult(
            """
            http {
                server {
                    server_name "example.com"<caret>;
                }
            }
            """.trimIndent()
        )
    }

    fun testSkipOverClosingSingleQuote() {
        myFixture.configureByText(
            "nginx.conf",
            """
            http {
                server {
                    server_name 'example.com<caret>';
                }
            }
            """.trimIndent()
        )
        myFixture.type('\'')
        myFixture.checkResult(
            """
            http {
                server {
                    server_name 'example.com'<caret>;
                }
            }
            """.trimIndent()
        )
    }
}
