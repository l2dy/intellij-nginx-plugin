package dev.meanmail.variables.catalog.nginx

import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.ngx_otel_module
import dev.meanmail.variables.catalog.Variable

val otel_trace_id = Variable(
    "otel_trace_id",
    "the identifier of the trace the current span belongs to, for example, 56552bc4daa3bf39c08362527e1dd6c4",
    ngx_otel_module,
    context = http,
)

val otel_span_id = Variable(
    "otel_span_id",
    "the identifier of the current span, for example, 4c0b8531ec38ca59",
    ngx_otel_module,
    context = http,
)

val otel_parent_id = Variable(
    "otel_parent_id",
    "the identifier of the parent span, for example, dc94d281b0f884ea",
    ngx_otel_module,
    context = http,
)

val otel_parent_sampled = Variable(
    "otel_parent_sampled",
    "the “sampled” flag of the parent span, can be “1” or “0”",
    ngx_otel_module,
    context = http,
)

val ngx_otel_module_variables: List<Variable> = listOf(
    otel_trace_id,
    otel_span_id,
    otel_parent_id,
    otel_parent_sampled,
)
