package dev.meanmail.variables.catalog.nginx.http

import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.http.ngx_http_slice_module
import dev.meanmail.variables.catalog.Variable

val slice_range = Variable(
    "slice_range",
    "the current slice range in HTTP byte range format, for example, bytes=0-1048575.",
    ngx_http_slice_module,
    context = http,
)

val ngx_http_slice_module_variables: List<Variable> = listOf(
    slice_range,
)
