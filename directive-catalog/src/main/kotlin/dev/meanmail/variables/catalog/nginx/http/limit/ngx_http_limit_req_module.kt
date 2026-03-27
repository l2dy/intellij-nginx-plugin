package dev.meanmail.variables.catalog.nginx.http.limit

import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.http.limit.ngx_http_limit_req_module
import dev.meanmail.variables.catalog.Variable

val limit_req_status = Variable(
    "limit_req_status",
    "keeps the result of limiting the request processing rate (1.17.6): PASSED, DELAYED, REJECTED, DELAYED_DRY_RUN, or REJECTED_DRY_RUN",
    ngx_http_limit_req_module,
    context = http,
)

val ngx_http_limit_req_module_variables: List<Variable> = listOf(
    limit_req_status,
)
