package dev.meanmail.codeInsight.completion

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class NginxCompletionContributorTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String {
        return "src/test/resources/dev/meanmail/completion"
    }

    private fun doTest(
        before: String,
        vararg completions: String,
        strict: Boolean = false
    ) {
        myFixture.configureByText("nginx.conf", before)
        val variants = myFixture.completeBasic()
        if (variants != null) {
            val strings = variants.map { it.lookupString }
            if (strict) {
                assertEquals(completions.toSet(), strings.toSet())
            } else {
                for (completion in completions) {
                    assertTrue(
                        "Expected completion: $completion not found in ${strings.joinToString()}",
                        strings.contains(completion)
                    )
                }
            }
        } else if (completions.isNotEmpty()) {
            fail("No completions found but ${completions.joinToString()} expected")
        }
    }

    fun testMainContext() {
        // Main directives should be available in the root of the config
        doTest(
            """
            eve<caret>
        """.trimIndent(),
            "events", "gzip_comp_level", "limit_conn_log_level", "limit_req_log_level",
            strict = true
        )
    }

    fun testHttpContext() {
        // Server directives should be available in the http block
        doTest(
            """
            http {
                server<caret>
            }
        """.trimIndent(),
            "grpc_ssl_server_name",
            "proxy_ssl_server_name",
            "server",
            "server_name_in_redirect",
            "server_names_hash_bucket_size",
            "server_names_hash_max_size",
            "server_tokens",
            "ssl_prefer_server_ciphers",
            "uwsgi_ssl_server_name",
            "server_rewrite_by_lua_block",
            "server_rewrite_by_lua_file",
            strict = true
        )
    }

    fun testServerContext() {
        // Server directives should be available in the server block
        doTest(
            """
            http {
                server {
                    limit_con<caret>
                }
            }
        """.trimIndent(),
            "limit_conn", "limit_conn_dry_run", "limit_conn_log_level", "limit_conn_status",
            strict = true
        )
    }

    fun testLocationContext() {
        // Location directives should be available in the location block
        doTest(
            """
            http {
                server {
                    location / {
                        proxy_set<caret>
                    }
                }
            }
        """.trimIndent(),
            "proxy_set_body",
            "proxy_set_header",
            "proxy_send_timeout",
            strict = true
        )
    }

    fun testLocationIfContext() {
        // If directives should be available in the if block inside location
        doTest(
            """
            http {
                server {
                    location / {
                        if ($\request_method = POST) {
                            re<caret>
                        }
                    }
                }
            }
        """.trimIndent(),
            "return",
            "rewrite",
            "rewrite_by_lua",
            "rewrite_by_lua_block",
            "rewrite_by_lua_file",
            "rewrite_log",
            "precontent_by_lua_block",
            "precontent_by_lua_file",
            "lua_need_request_body",
            "lua_upstream_skip_openssl_default_verify",
            "lua_transform_underscores_in_response_headers",
            "break",
            "expires",
            strict = true
        )
    }

    fun testEventsContext() {
        // Events directives should be available in the events block
        doTest(
            """
            events {
                wor<caret>
            }
        """.trimIndent(),
            "worker_aio_requests", "worker_connections",
            strict = true
        )
    }

    fun testUpstreamContext() {
        // Upstream directives should be available in the upstream block
        doTest(
            """
            http {
                upstream backend {
                    ser<caret>
                }
            }
        """.trimIndent(),
            "server", "sticky_cookie_insert",
            strict = true
        )
    }

    fun testLuaHttpContextCompletions() {
        doTest(
            """
            http {
                lua_<caret>
            }
        """.trimIndent(),
            "lua_shared_dict",
            "lua_code_cache",
            "lua_need_request_body"
        )
    }

    fun testLuaServerContextCompletions() {
        doTest(
            """
            http {
                server {
                    location / {
                        con<caret>
                    }
                }
            }
        """.trimIndent(),
            "content_by_lua",
            "content_by_lua_block",
            "content_by_lua_file",
        )
    }
}
