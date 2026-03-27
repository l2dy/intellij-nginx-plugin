package dev.meanmail.variables.catalog.nginx.http

import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.http.ngx_http_stub_status_module
import dev.meanmail.variables.catalog.Variable

val connections_active = Variable(
    "connections_active",
    "same as the Active connections value;",
    ngx_http_stub_status_module,
    context = http,
)

val connections_reading = Variable(
    "connections_reading",
    "same as the Reading value;",
    ngx_http_stub_status_module,
    context = http,
)

val connections_writing = Variable(
    "connections_writing",
    "same as the Writing value;",
    ngx_http_stub_status_module,
    context = http,
)

val connections_waiting = Variable(
    "connections_waiting",
    "same as the Waiting value.",
    ngx_http_stub_status_module,
    context = http,
)

val ngx_http_stub_status_module_variables: List<Variable> = listOf(
    connections_active,
    connections_reading,
    connections_writing,
    connections_waiting,
)
