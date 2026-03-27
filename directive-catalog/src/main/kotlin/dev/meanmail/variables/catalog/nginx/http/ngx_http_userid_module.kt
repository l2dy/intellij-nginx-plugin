package dev.meanmail.variables.catalog.nginx.http

import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.http.ngx_http_userid_module
import dev.meanmail.variables.catalog.Variable

val uid_got = Variable(
    "uid_got",
    "The cookie name and received client identifier.",
    ngx_http_userid_module,
    context = http,
)

val uid_reset = Variable(
    "uid_reset",
    "If the variable is set to a non-empty string that is not “0”, the client identifiers are reset. The special value “log” additionally leads to the output of messages about the reset identifiers to the .",
    ngx_http_userid_module,
    context = http,
)

val uid_set = Variable(
    "uid_set",
    "The cookie name and sent client identifier.",
    ngx_http_userid_module,
    context = http,
)

val ngx_http_userid_module_variables: List<Variable> = listOf(
    uid_got,
    uid_reset,
    uid_set,
)
