package dev.meanmail.variables.catalog.nginx.http.gzip

import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.http.gzip.ngx_http_gzip_module
import dev.meanmail.variables.catalog.Variable

val gzip_ratio = Variable(
    "gzip_ratio",
    "achieved compression ratio, computed as the ratio between the original and compressed response sizes.",
    ngx_http_gzip_module,
    context = http,
)

val ngx_http_gzip_module_variables: List<Variable> = listOf(
    gzip_ratio,
)
