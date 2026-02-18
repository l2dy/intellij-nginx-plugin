package dev.meanmail.directives.catalog.nginx.http.limit

import dev.meanmail.directives.catalog.*
import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.http.location
import dev.meanmail.directives.catalog.nginx.http.server

// https://nginx.org/en/docs/http/ngx_http_limit_req_module.html

val ngx_http_limit_req_module = NginxModule(
    "ngx_http_limit_req_module",
    description = "Limits the request processing rate per a defined key, using the 'leaky bucket' method"
)

val limitReqZone = Directive(
    name = "limit_req_zone",
    description = "Defines a shared memory zone for tracking request rate limits",
    syntax = listOf("<b>limit_req_zone</b> <i>key</i> zone=<i>name</i>:<i>size</i> rate=<i>rate</i> [sync];"),
    parameters = listOf(
        DirectiveParameter(
            name = "key",
            description = "Defines the key for tracking request rate (e.g., \$binary_remote_addr)",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "zone_name",
            description = "Name and size of the shared memory zone for storing request rate counters",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "rate",
            description = "Maximum request rate allowed (requests per second)",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http),
    module = ngx_http_limit_req_module
)

val limitReq = Directive(
    name = "limit_req",
    description = "Sets the shared memory zone and maximum burst size of requests",
    syntax = listOf("<b>limit_req</b> zone=<i>name</i> [burst=<i>number</i>] [nodelay | delay=<i>number</i>];"),
    parameters = listOf(
        DirectiveParameter(
            name = "zone_name",
            description = "Name of the request rate limit zone",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "burst",
            description = "Maximum burst size of requests",
            valueType = ValueType.NUMBER,
            required = false,
            defaultValue = "0"
        ),
        DirectiveParameter(
            name = "nodelay",
            description = "Enables immediate processing of requests instead of delaying",
            valueType = ValueType.BOOLEAN,
            required = false
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_limit_req_module
)

val limitReqDryRun = ToggleDirective(
    "limit_req_dry_run",
    "Enables dry run mode for request rate limiting without actual blocking",
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_limit_req_module
)

val limitReqLogLevel = Directive(
    name = "limit_req_log_level",
    description = "Sets the logging level for request rate limit events",
    syntax = listOf("<b>limit_req_log_level</b> info | notice | warn | error;"),
    parameters = listOf(
        DirectiveParameter(
            name = "log_level",
            description = "Logging level for request rate limit events",
            valueType = ValueType.ENUM,
            allowedValues = listOf("info", "notice", "warn", "error"),
            required = true,
            defaultValue = "error"
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_limit_req_module
)

val limitReqStatus = Directive(
    name = "limit_req_status",
    description = "Defines the HTTP status code returned when request rate limit is exceeded",
    syntax = listOf("<b>limit_req_status</b> <i>code</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "status_code",
            description = "HTTP status code to return when request rate limit is reached",
            valueType = ValueType.NUMBER,
            required = true,
            defaultValue = "503"
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_limit_req_module
)
