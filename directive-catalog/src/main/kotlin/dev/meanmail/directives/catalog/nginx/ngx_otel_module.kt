package dev.meanmail.directives.catalog.nginx

import dev.meanmail.directives.catalog.*
import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.http.location
import dev.meanmail.directives.catalog.nginx.http.server

// https://nginx.org/en/docs/ngx_otel_module.html

val ngx_otel_module = NginxModule(
    name = "ngx_otel_module",
    description = "OpenTelemetry module for distributed tracing and context propagation"
)

val otelExporter = Directive(
    name = "otel_exporter",
    description = "Specifies OTel data export parameters as an OTel exporter configuration block",
    syntax = listOf("<b>otel_exporter</b> { ... }"),
    context = listOf(http),
    module = ngx_otel_module
)

val otelResourceAttr = Directive(
    name = "otel_resource_attr",
    description = "Sets custom OTel resource attributes",
    syntax = listOf("<b>otel_resource_attr</b> <i>name</i> <i>value</i>;"),
    context = listOf(http),
    module = ngx_otel_module
)

val otelServiceName = Directive(
    name = "otel_service_name",
    description = "Sets the service.name attribute for the OTel resource",
    syntax = listOf("<b>otel_service_name</b> <i>name</i>;"),
    context = listOf(http),
    module = ngx_otel_module
)

val otelSpanAttr = Directive(
    name = "otel_span_attr",
    description = "Adds a custom OTel span attribute",
    syntax = listOf("<b>otel_span_attr</b> <i>name</i> <i>value</i>;"),
    context = listOf(http, server, location),
    module = ngx_otel_module
)

val otelSpanName = Directive(
    name = "otel_span_name",
    description = "Defines the span name for OpenTelemetry tracing",
    syntax = listOf("<b>otel_span_name</b> <i>name</i>;"),
    context = listOf(http, server, location),
    module = ngx_otel_module
)

val otelTrace = Directive(
    name = "otel_trace",
    description = "Enables or disables OpenTelemetry tracing",
    syntax = listOf("<b>otel_trace</b> on | off | \$variable;"),
    context = listOf(http, server, location),
    module = ngx_otel_module
)

val otelTraceContext = Directive(
    name = "otel_trace_context",
    description = "Specifies how to propagate traceparent/tracestate headers",
    syntax = listOf("<b>otel_trace_context</b> extract | inject | propagate | ignore;"),
    context = listOf(http, server, location),
    module = ngx_otel_module
)
