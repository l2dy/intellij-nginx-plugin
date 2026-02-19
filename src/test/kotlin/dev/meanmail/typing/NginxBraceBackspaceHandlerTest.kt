package dev.meanmail.typing

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class NginxBraceBackspaceHandlerTest : BasePlatformTestCase() {

    fun testDeleteMatchingBrace() {
        myFixture.configureByText("nginx.conf", "server {<caret>}")
        myFixture.type('\b')
        myFixture.checkResult("server <caret>")
    }

    fun testDeleteMatchingParen() {
        myFixture.configureByText("nginx.conf", "if (<caret>)")
        myFixture.type('\b')
        myFixture.checkResult("if <caret>")
    }

    fun testNoDeleteWhenContentBetween() {
        myFixture.configureByText("nginx.conf", "server {<caret> listen 80; }")
        myFixture.type('\b')
        myFixture.checkResult("server <caret> listen 80; }")
    }
}
