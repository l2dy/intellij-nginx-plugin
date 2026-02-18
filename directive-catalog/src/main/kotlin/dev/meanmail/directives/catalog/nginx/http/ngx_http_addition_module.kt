package dev.meanmail.directives.catalog.nginx.http

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/http/ngx_http_addition_module.html

val ngx_http_addition_module = NginxModule(
    "ngx_http_addition_module",
    description = "Provides content addition capabilities for HTTP responses"
)

val addAfterBody = Directive(
    "add_after_body",
    description = "Adds content after the response body",
    syntax = listOf("<b>add_after_body</b> <i>uri</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the file to be added after the response body",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_addition_module
)

val addBeforeBody = Directive(
    "add_before_body",
    description = "Adds content before the response body",
    syntax = listOf("<b>add_before_body</b> <i>uri</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the file to be added before the response body",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_addition_module
)

val additionTypes = Directive(
    "addition_types",
    description = "Specifies MIME types for which content addition is performed",
    syntax = listOf("<b>addition_types</b> <i>mime-type</i> ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "mime_type",
            description = "MIME type for which content addition is applied",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_addition_module
)
