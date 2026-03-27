package dev.meanmail.variables.catalog

import dev.meanmail.directives.catalog.Directive
import dev.meanmail.directives.catalog.NginxModule
import dev.meanmail.directives.catalog.any
import dev.meanmail.variables.catalog.nginx.*
import dev.meanmail.variables.catalog.nginx.http.*
import dev.meanmail.variables.catalog.nginx.http.gzip.*
import dev.meanmail.variables.catalog.nginx.http.limit.*
import dev.meanmail.variables.catalog.nginx.http.upstream.*
import dev.meanmail.variables.catalog.nginx.stream.*
import dev.meanmail.variables.catalog.nginx.stream.ssl.*
import dev.meanmail.variables.catalog.nginx.stream.upstream.*

class Variable(
    val name: String,
    val description: String,
    val module: NginxModule,
    val parameterized: Boolean = false,
    val context: Directive = any,
) {
    companion object {
        val all: List<Variable> by lazy { initVariables() }
    }
}

fun findVariables(name: String): List<Variable> {
    val exact = Variable.all.filter { it.name == name }
    if (exact.isNotEmpty()) return exact
    return Variable.all.filter { it.parameterized && name.startsWith(it.name) }
}

fun findVariables(name: String, context: Set<Directive>?): List<Variable> {
    val all = findVariables(name)
    if (context == null) return all
    val filtered = all.filter { it.context == any || it.context in context }
    return if (filtered.isNotEmpty()) filtered else all
}

private fun initVariables(): List<Variable> =
    ngx_http_acme_module_variables +
    ngx_http_core_module_variables +
    ngx_http_fastcgi_module_variables +
    ngx_http_gzip_module_variables +
    ngx_http_limit_conn_module_variables +
    ngx_http_limit_req_module_variables +
    ngx_http_memcached_module_variables +
    ngx_http_proxy_module_variables +
    ngx_http_realip_module_variables +
    ngx_http_referer_module_variables +
    ngx_http_secure_link_module_variables +
    ngx_http_slice_module_variables +
    ngx_http_ssi_module_variables +
    ngx_http_ssl_module_variables +
    ngx_http_stub_status_module_variables +
    ngx_http_upstream_module_variables +
    ngx_http_userid_module_variables +
    ngx_http_v2_module_variables +
    ngx_http_v3_module_variables +
    ngx_otel_module_variables +
    ngx_stream_core_module_variables +
    ngx_stream_limit_conn_module_variables +
    ngx_stream_realip_module_variables +
    ngx_stream_ssl_module_variables +
    ngx_stream_ssl_preread_module_variables +
    ngx_stream_upstream_module_variables
