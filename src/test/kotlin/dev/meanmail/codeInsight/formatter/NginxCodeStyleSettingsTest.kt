package dev.meanmail.codeInsight.formatter

import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class NginxCodeStyleSettingsTest : BasePlatformTestCase() {

    fun testCommentAddsSpace() {
        myFixture.configureByText("nginx.conf", "listen 80;")
        myFixture.performEditorAction(IdeActions.ACTION_COMMENT_LINE)
        myFixture.checkResult("# listen 80;")
    }

    fun testUncommentRemovesSpace() {
        myFixture.configureByText("nginx.conf", "# listen 80;")
        myFixture.performEditorAction(IdeActions.ACTION_COMMENT_LINE)
        myFixture.checkResult("listen 80;")
    }
}
