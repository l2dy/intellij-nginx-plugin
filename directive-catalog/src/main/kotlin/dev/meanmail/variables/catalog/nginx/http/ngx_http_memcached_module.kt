package dev.meanmail.variables.catalog.nginx.http

import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.http.ngx_http_memcached_module
import dev.meanmail.variables.catalog.Variable

val memcached_key = Variable(
    "memcached_key",
    "Defines a key for obtaining response from a memcached server.",
    ngx_http_memcached_module,
    context = http,
)

val ngx_http_memcached_module_variables: List<Variable> = listOf(
    memcached_key,
)
