package dev.meanmail.variables.catalog.nginx.http

import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.http.ngx_http_realip_module
import dev.meanmail.variables.catalog.Variable

val realip_remote_addr = Variable(
    "realip_remote_addr",
    "keeps the original client address (1.9.7)",
    ngx_http_realip_module,
    context = http,
)

val realip_remote_port = Variable(
    "realip_remote_port",
    "keeps the original client port (1.11.0)",
    ngx_http_realip_module,
    context = http,
)

val ngx_http_realip_module_variables: List<Variable> = listOf(
    realip_remote_addr,
    realip_remote_port,
)
