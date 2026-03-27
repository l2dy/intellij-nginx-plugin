package dev.meanmail.codeInsight.documentation

import com.intellij.codeInsight.documentation.DocumentationManager
import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class NginxDocumentationProviderTest : BasePlatformTestCase() {

    private val provider = NginxDocumentationProvider()

    private fun configureAndGetDoc(config: String): String {
        myFixture.configureByText("nginx.conf", config)
        val originalElement = myFixture.file.findElementAt(myFixture.caretOffset)!!
        @Suppress("DEPRECATION")
        val targetElement = DocumentationManager.getInstance(project)
            .findTargetElement(myFixture.editor, myFixture.file, originalElement)
        assertNotNull("Failed to resolve target element", targetElement)
        val doc = provider.generateDoc(targetElement!!, originalElement)
        assertNotNull("Expected non-null documentation", doc)
        return doc!!
    }

    private fun configureAndGetQuickNav(config: String): String {
        myFixture.configureByText("nginx.conf", config)
        val originalElement = myFixture.file.findElementAt(myFixture.caretOffset)!!
        @Suppress("DEPRECATION")
        val targetElement = DocumentationManager.getInstance(project)
            .findTargetElement(myFixture.editor, myFixture.file, originalElement)
        assertNotNull("Failed to resolve target element", targetElement)
        val info = provider.getQuickNavigateInfo(targetElement!!, originalElement)
        assertNotNull("Expected non-null quick navigate info", info)
        return info!!
    }

    private fun assertContains(haystack: String, needle: String) {
        if (needle !in haystack) {
            fail("Expected to find \"$needle\" in:\n$haystack")
        }
    }

    private fun assertNotContains(haystack: String, needle: String) {
        if (needle in haystack) {
            fail("Expected NOT to find \"$needle\" in:\n$haystack")
        }
    }

    // ---- generateDoc tests ----

    fun testGenerateDocForListenDirective() {
        val doc = configureAndGetDoc(
            """
            server {
                listen<caret> 80;
            }
            """.trimIndent()
        )

        assertContains(doc, DocumentationMarkup.DEFINITION_START + "listen")
        assertContains(doc, DocumentationMarkup.DEFINITION_END)
        assertContains(doc, "ngx_http_core_module")
        assertContains(doc, DocumentationMarkup.CONTENT_START)
        assertContains(doc, "Configures the IP address and port for server block")
        assertContains(doc, DocumentationMarkup.CONTENT_END)
        assertContains(doc, "Context:" + DocumentationMarkup.SECTION_SEPARATOR + "server")
        assertContains(doc, "Parameters:")
        assertContains(doc, "<b>address</b>")
        assertContains(doc, "(required)")
        assertContains(doc, "<b>port</b>")
        assertContains(doc, "<b>options</b>")
        assertContains(doc, "(optional)")
    }

    fun testGenerateDocForDirectiveWithMultipleContexts() {
        val doc = configureAndGetDoc(
            """
            error_log<caret> /var/log/nginx/error.log;
            """.trimIndent()
        )

        assertContains(doc, DocumentationMarkup.DEFINITION_START + "error_log")
        assertContains(doc, "Configures error logging")
        assertContains(doc, "Context:" + DocumentationMarkup.SECTION_SEPARATOR + "main, http, mail, stream, server, location")
    }

    fun testGenerateDocForDirectiveWithAllowedValues() {
        val doc = configureAndGetDoc(
            """
            error_log<caret> /var/log/nginx/error.log warn;
            """.trimIndent()
        )

        assertContains(doc, "<b>level</b>")
        assertContains(doc, "Allowed values: debug, info, notice, warn, error, crit, alert, emerg")
    }

    fun testGenerateDocForToggleDirective() {
        val doc = configureAndGetDoc(
            """
            http {
                tcp_nodelay<caret> on;
            }
            """.trimIndent()
        )

        assertContains(doc, DocumentationMarkup.DEFINITION_START + "tcp_nodelay")
        assertContains(doc, "Enables or disables the TCP_NODELAY option")
        assertContains(doc, "<b>state</b>")
        assertContains(doc, "Allowed values: on, off")
        assertContains(doc, "default: on")
    }

    fun testGenerateDocForParameterWithoutName() {
        val doc = configureAndGetDoc(
            """
            events {
                worker_connections<caret> 1024;
            }
            """.trimIndent()
        )

        assertContains(doc, "worker_connections")
        assertContains(doc, "Sets the maximum number of connections per worker process")
        assertContains(doc, "<b>value</b>")
    }

    fun testGenerateDocForParameterWithRange() {
        val doc = configureAndGetDoc(
            """
            http {
                map_hash_bucket_size<caret> 64;
            }
            """.trimIndent()
        )

        assertContains(doc, DocumentationMarkup.DEFINITION_START + "map_hash_bucket_size")
        assertContains(doc, "<b>size</b>")
        assertContains(doc, "default: 64")
        assertContains(doc, "Range: 32 .. 128")
    }

    fun testGenerateDocForDirectiveWithNoParameters() {
        val doc = configureAndGetDoc(
            """
            http {
                otel_exporter<caret> {
                }
            }
            """.trimIndent()
        )

        assertContains(doc, "otel_exporter")
        assertNotContains(doc, "Parameters:")
    }

    // ---- no doc on directive parameters ----

    fun testNoDocOnDirectiveParameter() {
        myFixture.configureByText("nginx.conf", """
            server {
                listen <caret>80;
            }
        """.trimIndent())
        val originalElement = myFixture.file.findElementAt(myFixture.caretOffset)!!
        @Suppress("DEPRECATION")
        val targetElement = DocumentationManager.getInstance(project)
            .findTargetElement(myFixture.editor, myFixture.file, originalElement)
        val doc = provider.generateDoc(targetElement ?: originalElement, originalElement)
        assertNull("Expected no documentation on directive parameter", doc)
    }

    fun testNoDocOnSemicolon() {
        myFixture.configureByText("nginx.conf", """
            events {
                worker_connections 1024<caret>;
            }
        """.trimIndent())
        val originalElement = myFixture.file.findElementAt(myFixture.caretOffset)!!
        @Suppress("DEPRECATION")
        val targetElement = DocumentationManager.getInstance(project)
            .findTargetElement(myFixture.editor, myFixture.file, originalElement)
        val doc = provider.generateDoc(targetElement ?: originalElement, originalElement)
        assertNull("Expected no documentation on semicolon", doc)
    }

    // ---- getUrlFor tests ----

    fun testGetUrlForReturnsOfficialDocUrl() {
        myFixture.configureByText("nginx.conf", """
            server {
                location / {
                    proxy_pass<caret> http://backend;
                }
            }
        """.trimIndent())
        val originalElement = myFixture.file.findElementAt(myFixture.caretOffset)!!
        @Suppress("DEPRECATION")
        val targetElement = DocumentationManager.getInstance(project)
            .findTargetElement(myFixture.editor, myFixture.file, originalElement)!!
        val urls = provider.getUrlFor(targetElement, originalElement)
        assertNotNull(urls)
        assertEquals(1, urls!!.size)
        assertEquals("https://nginx.org/en/docs/http/ngx_http_proxy_module.html#proxy_pass", urls[0])
    }

    // ---- getQuickNavigateInfo tests ----

    fun testQuickNavForListenDirective() {
        val info = configureAndGetQuickNav(
            """
            server {
                listen<caret> 80;
            }
            """.trimIndent()
        )

        assertTrue("Expected quick nav to start with '<b>listen</b>', got: $info",
            info.startsWith("<b>listen</b>"))
        assertContains(info, "ngx_http_core_module")
        assertContains(info, "Configures the IP address and port for server block")
        assertNotContains(info, "Parameters")
        assertNotContains(info, "Context:")
    }

    fun testQuickNavForToggleDirective() {
        val info = configureAndGetQuickNav(
            """
            http {
                tcp_nodelay<caret> on;
            }
            """.trimIndent()
        )

        assertTrue("Expected quick nav to start with '<b>tcp_nodelay</b>', got: $info",
            info.startsWith("<b>tcp_nodelay</b>"))
        assertContains(info, "ngx_http_core_module")
        assertContains(info, "Enables or disables the TCP_NODELAY option")
    }

    // ---- Variable documentation tests ----

    fun testGenerateDocForVariable() {
        val doc = configureAndGetDoc(
            """
            server {
                return 200 ${'$'}arg<caret>s;
            }
            """.trimIndent()
        )

        assertContains(doc, DocumentationMarkup.DEFINITION_START + "\$args")
        assertNotContains(doc, "\$args*")
        assertContains(doc, "ngx_http_core_module")
        assertContains(doc, "arguments in the request line")
    }

    fun testGenerateDocForParameterizedVariable() {
        val doc = configureAndGetDoc(
            """
            server {
                return 200 ${'$'}arg_foo<caret>;
            }
            """.trimIndent()
        )

        assertContains(doc, DocumentationMarkup.DEFINITION_START + "\$arg_foo*")
        assertContains(doc, "ngx_http_core_module")
        assertContains(doc, "argument 'foo' in the request line")
    }

    fun testQuickNavForVariable() {
        val info = configureAndGetQuickNav(
            """
            server {
                return 200 ${'$'}remote_addr<caret>;
            }
            """.trimIndent()
        )

        assertTrue("Expected quick nav to start with '<b>\$remote_addr</b>', got: $info",
            info.startsWith("<b>\$remote_addr</b>"))
        assertContains(info, "ngx_http_core_module")
        assertContains(info, "client address")
    }

    fun testGenerateDocForVariableInConcatenation() {
        val doc = configureAndGetDoc(
            """
            server {
                return 301 https://${'$'}hos<caret>t;
            }
            """.trimIndent()
        )

        assertContains(doc, DocumentationMarkup.DEFINITION_START + "\$host")
        assertContains(doc, "ngx_http_core_module")
    }

    fun testGetUrlForVariable() {
        myFixture.configureByText("nginx.conf", """
            server {
                return 200 ${'$'}remote_addr<caret>;
            }
        """.trimIndent())
        val originalElement = myFixture.file.findElementAt(myFixture.caretOffset)!!
        @Suppress("DEPRECATION")
        val targetElement = DocumentationManager.getInstance(project)
            .findTargetElement(myFixture.editor, myFixture.file, originalElement)!!
        val urls = provider.getUrlFor(targetElement, originalElement)
        assertNotNull(urls)
        assertEquals(1, urls!!.size)
        assertEquals("https://nginx.org/en/docs/http/ngx_http_core_module.html#var_remote_addr", urls[0])
    }

}
