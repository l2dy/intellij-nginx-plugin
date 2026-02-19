package dev.meanmail.codeInsight.formatter

import com.intellij.application.options.CodeStyleAbstractConfigurable
import com.intellij.application.options.CodeStyleAbstractPanel
import com.intellij.application.options.SmartIndentOptionsEditor
import com.intellij.application.options.TabbedLanguageCodeStylePanel
import com.intellij.psi.codeStyle.*
import dev.meanmail.NginxLanguage

class NginxLanguageCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {

    override fun getLanguage() = NginxLanguage

    override fun createConfigurable(
        baseSettings: CodeStyleSettings,
        modelSettings: CodeStyleSettings
    ): CodeStyleConfigurable {
        return object : CodeStyleAbstractConfigurable(baseSettings, modelSettings, configurableDisplayName) {
            override fun createPanel(settings: CodeStyleSettings): CodeStyleAbstractPanel =
                object : TabbedLanguageCodeStylePanel(NginxLanguage, currentSettings, settings) {}
        }
    }

    override fun customizeDefaults(
        commonSettings: CommonCodeStyleSettings,
        indentOptions: CommonCodeStyleSettings.IndentOptions
    ) {
        commonSettings.LINE_COMMENT_AT_FIRST_COLUMN = false
        commonSettings.LINE_COMMENT_ADD_SPACE = true
        indentOptions.INDENT_SIZE = 4
        indentOptions.CONTINUATION_INDENT_SIZE = 4
        indentOptions.TAB_SIZE = 4
    }

    override fun customizeSettings(
        consumer: CodeStyleSettingsCustomizable,
        settingsType: SettingsType
    ) {
        if (settingsType == SettingsType.COMMENTER_SETTINGS) {
            consumer.showStandardOptions(
                "LINE_COMMENT_AT_FIRST_COLUMN",
                "LINE_COMMENT_ADD_SPACE",
            )
        }
    }

    override fun getIndentOptionsEditor() = SmartIndentOptionsEditor()

    override fun getCodeSample(settingsType: SettingsType): String = """
        http {
            server {
                listen 80;
                server_name example.com;

                location / {
                    root /var/www/html;
                    index index.html;
                }

                location /api {
                    proxy_pass http://backend;
                    proxy_set_header Host ${'$'}host;
                }
            }
        }
    """.trimIndent()
}
