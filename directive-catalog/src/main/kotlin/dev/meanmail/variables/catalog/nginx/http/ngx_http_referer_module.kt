package dev.meanmail.variables.catalog.nginx.http

import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.http.ngx_http_referer_module
import dev.meanmail.variables.catalog.Variable

val invalid_referer = Variable(
    "invalid_referer",
    "Empty string, if the Referer request header field value is considered valid, otherwise “1”.",
    ngx_http_referer_module,
    context = http,
)

val ngx_http_referer_module_variables: List<Variable> = listOf(
    invalid_referer,
)
