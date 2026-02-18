package dev.meanmail.directives.catalog.nginx.stream

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/stream/ngx_stream_limit_conn_module.html

val ngx_stream_limit_conn_module = NginxModule(
    "ngx_stream_limit_conn_module",
    description = "Limits the number of concurrent connections per defined key in stream context"
)

val streamLimitConn = Directive(
    "limit_conn",
    description = "Limits the number of concurrent connections per defined key",
    syntax = listOf("<b>limit_conn</b> <i>zone</i> <i>number</i>;"),
    parameters = listOf(
        DirectiveParameter(
            "zone",
            "Name of the zone defining the limit",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            "number",
            "Maximum number of concurrent connections",
            valueType = ValueType.NUMBER,
            required = true
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_limit_conn_module
)

val streamLimitConnDryRun = ToggleDirective(
    "limit_conn_dry_run",
    "Enables the dry run mode for limit_conn module without actually limiting connections",
    enabled = false,
    context = listOf(stream, streamServer),
    module = ngx_stream_limit_conn_module
)

val streamLimitConnLogLevel = Directive(
    "limit_conn_log_level",
    description = "Sets the logging level for connection limit events",
    syntax = listOf("<b>limit_conn_log_level</b> info | notice | warn | error;"),
    parameters = listOf(
        DirectiveParameter(
            "log_level",
            "Logging level for connection limit events",
            valueType = ValueType.STRING,
            required = false,
            allowedValues = listOf("info", "notice", "warn", "error"),
            defaultValue = "error"
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_limit_conn_module
)

val streamLimitConnZone = Directive(
    "limit_conn_zone",
    description = "Defines a shared memory zone for connection limiting",
    syntax = listOf("<b>limit_conn_zone</b> <i>key</i> zone=<i>name</i>:<i>size</i>;"),
    parameters = listOf(
        DirectiveParameter(
            "key",
            "Defines the key for connection limiting (e.g., \$remote_addr)",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            "zone",
            "Name and size of the shared memory zone (name:size)",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(stream),
    module = ngx_stream_limit_conn_module
)
