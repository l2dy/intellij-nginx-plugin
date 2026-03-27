package dev.meanmail.variables.catalog.nginx.http

import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.http.ngx_http_secure_link_module
import dev.meanmail.variables.catalog.Variable

val secure_link = Variable(
    "secure_link",
    "The status of a link check. The specific value depends on the selected operation mode.",
    ngx_http_secure_link_module,
    context = http,
)

val secure_link_expires = Variable(
    "secure_link_expires",
    "The lifetime of a link passed in a request; intended to be used only in the directive.",
    ngx_http_secure_link_module,
    context = http,
)

val ngx_http_secure_link_module_variables: List<Variable> = listOf(
    secure_link,
    secure_link_expires,
)
