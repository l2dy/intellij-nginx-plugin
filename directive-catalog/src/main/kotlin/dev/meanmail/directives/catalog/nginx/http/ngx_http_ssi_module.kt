package dev.meanmail.directives.catalog.nginx.http

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/http/ngx_http_ssi_module.html

val ngx_http_ssi_module = NginxModule(
    "ngx_http_ssi_module",
    description = "Processes Server Side Includes (SSI) commands in responses. Supports incomplete list of SSI commands."
)

val ssi = ToggleDirective(
    "ssi",
    "Enables or disables processing of SSI (Server Side Includes) commands in responses",
    enabled = false,
    context = listOf(http, server, location, locationIf),
    module = ngx_http_ssi_module
)

val ssiLastModified = ToggleDirective(
    "ssi_last_modified",
    "Preserves the 'Last-Modified' header field during SSI processing to facilitate response caching",
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_ssi_module
)

val ssiMinFileChunk = Directive(
    name = "ssi_min_file_chunk",
    description = "Sets the minimum size for response parts stored on disk to optimize sendfile usage",
    syntax = listOf("<b>ssi_min_file_chunk</b> size;"),
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Minimum file chunk size for sendfile optimization",
            valueType = ValueType.SIZE,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_ssi_module
)

val ssiSilentErrors = ToggleDirective(
    "ssi_silent_errors",
    "Suppresses the error output string if an error occurs during SSI processing",
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_ssi_module
)

val ssiTypes = Directive(
    name = "ssi_types",
    description = "Enables processing of SSI commands in responses with specified MIME types, in addition to 'text/html'",
    syntax = listOf("<b>ssi_types</b> <i>mime-type</i> ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "mime_types",
            description = "MIME types to process with SSI (use '*' to match any MIME type)",
            valueType = ValueType.STRING_LIST,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_ssi_module
)

val ssiValueLength = Directive(
    name = "ssi_value_length",
    description = "Sets the maximum length of parameter values in SSI commands",
    syntax = listOf("<b>ssi_value_length</b> <i>length</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "length",
            description = "Maximum length of SSI command parameter values",
            valueType = ValueType.INTEGER,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_ssi_module
)
