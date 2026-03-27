package dev.meanmail.variables.catalog.nginx.http

import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.http.ngx_http_v2_module
import dev.meanmail.variables.catalog.Variable

val http2 = Variable(
    "http2",
    "negotiated protocol identifier: “h2” for HTTP/2 over TLS, “h2c” for HTTP/2 over cleartext TCP, or an empty string otherwise.",
    ngx_http_v2_module,
    context = http,
)

val ngx_http_v2_module_variables: List<Variable> = listOf(
    http2,
)
