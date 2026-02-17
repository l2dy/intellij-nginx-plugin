package dev.meanmail.directives.catalog.nginx.http

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/http/ngx_http_log_module.html

val ngx_http_log_module = NginxModule(
    name = "ngx_http_log_module",
    description = "Writes request logs in the specified format"
)

val accessLog = Directive(
    name = "access_log",
    description = "Sets the path, format, and configuration for a buffered log write",
    parameters = listOf(
        DirectiveParameter(
            "path",
            "Path to the log file, or 'off' to disable logging. Can be an absolute or relative path",
            valueType = ValueType.STRING,
            required = false,
            defaultValue = "logs/access.log",
            allowedValues = listOf("off")
        ),
        DirectiveParameter(
            "format",
            "Name of the log format previously defined by log_format directive. If not specified, uses the predefined 'combined' format",
            valueType = ValueType.STRING,
            required = false,
            defaultValue = "combined"
        ),
        DirectiveParameter(
            "buffer",
            "Sets the buffer size for writing log entries. Helps improve logging performance by buffering log writes",
            valueType = ValueType.SIZE,
            required = false,
            defaultValue = "64k"
        ),
        DirectiveParameter(
            "gzip",
            "Sets compression level for log files (1-9). Higher levels provide more compression but are more CPU-intensive",
            valueType = ValueType.NUMBER,
            required = false,
            minValue = 1,
            maxValue = 9
        )
    ),
    context = listOf(http, server, location, locationIf, limitExcept),
    module = ngx_http_log_module
)

val logFormat = Directive(
    name = "log_format",
    description = "Specifies log format with optional character escaping",
    parameters = listOf(
        DirectiveParameter(
            "name",
            "Name of the log format. Used as a reference in access_log directive. 'combined' is the default predefined format",
            valueType = ValueType.STRING,
            required = true,
            defaultValue = "combined"
        ),
        DirectiveParameter(
            "format",
            "Format string defining log entries using stream-specific variables. Supports escape sequences and variable interpolation",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http),
    module = ngx_http_log_module
)

val openLogFileCache = Directive(
    name = "open_log_file_cache",
    description = "Configures caching of log file descriptors to improve performance",
    parameters = listOf(
        DirectiveParameter(
            "max",
            "Maximum number of cached log files. Controls memory usage for log file caching",
            valueType = ValueType.NUMBER,
            required = false
        ),
        DirectiveParameter(
            "inactive",
            "Time after which an unused file descriptor is closed. Helps manage resource allocation",
            valueType = ValueType.TIME,
            required = false,
            defaultValue = "10s"
        ),
        DirectiveParameter(
            "min_uses",
            "Minimum number of file uses to keep it open. Prevents caching of rarely used log files",
            valueType = ValueType.NUMBER,
            required = false,
            defaultValue = "1"
        ),
        DirectiveParameter(
            "valid",
            "Time interval to check file existence and metadata. Helps detect file changes",
            valueType = ValueType.TIME,
            required = false,
            defaultValue = "60s"
        ),
        DirectiveParameter(
            "state",
            "Enables or disables the log file descriptor cache",
            valueType = ValueType.BOOLEAN,
            required = false,
            defaultValue = "off"
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_log_module
)
