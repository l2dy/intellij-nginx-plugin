package dev.meanmail.variables.catalog.nginx.http

import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.http.ngx_http_ssi_module
import dev.meanmail.variables.catalog.Variable

val date_local = Variable(
    "date_local",
    "current time in the local time zone. The format is set by the config command with the timefmt parameter.",
    ngx_http_ssi_module,
    context = http,
)

val date_gmt = Variable(
    "date_gmt",
    "current time in GMT. The format is set by the config command with the timefmt parameter.",
    ngx_http_ssi_module,
    context = http,
)

val ngx_http_ssi_module_variables: List<Variable> = listOf(
    date_local,
    date_gmt,
)
