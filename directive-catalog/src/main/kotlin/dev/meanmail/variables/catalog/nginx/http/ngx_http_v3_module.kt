package dev.meanmail.variables.catalog.nginx.http

import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.http.ngx_http_v3_module
import dev.meanmail.variables.catalog.Variable

val http3 = Variable(
    "http3",
    "negotiated protocol identifier: “h3” for HTTP/3 connections, “hq” for hq connections, or an empty string otherwise.",
    ngx_http_v3_module,
    context = http,
)

val ngx_http_v3_module_variables: List<Variable> = listOf(
    http3,
)
