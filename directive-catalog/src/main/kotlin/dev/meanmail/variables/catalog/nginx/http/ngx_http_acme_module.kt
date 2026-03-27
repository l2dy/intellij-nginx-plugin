package dev.meanmail.variables.catalog.nginx.http

import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.http.ngx_http_acme_module
import dev.meanmail.variables.catalog.Variable

val acme_certificate = Variable(
    "acme_certificate",
    "SSL certificate that can be passed to the",
    ngx_http_acme_module,
    context = http,
)

val acme_certificate_key = Variable(
    "acme_certificate_key",
    "SSL certificate private key that can be passed to",
    ngx_http_acme_module,
    context = http,
)

val ngx_http_acme_module_variables: List<Variable> = listOf(
    acme_certificate,
    acme_certificate_key,
)
