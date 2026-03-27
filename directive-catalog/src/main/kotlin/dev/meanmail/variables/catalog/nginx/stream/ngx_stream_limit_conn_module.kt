package dev.meanmail.variables.catalog.nginx.stream

import dev.meanmail.directives.catalog.nginx.stream.stream
import dev.meanmail.directives.catalog.nginx.stream.ngx_stream_limit_conn_module
import dev.meanmail.variables.catalog.Variable

val limit_conn_status = Variable(
    "limit_conn_status",
    "keeps the result of limiting the number of connections (1.17.6): PASSED, REJECTED, or REJECTED_DRY_RUN",
    ngx_stream_limit_conn_module,
    context = stream,
)

val ngx_stream_limit_conn_module_variables: List<Variable> = listOf(
    limit_conn_status,
)
