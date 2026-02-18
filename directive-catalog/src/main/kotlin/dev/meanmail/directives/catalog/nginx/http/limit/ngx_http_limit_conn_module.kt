package dev.meanmail.directives.catalog.nginx.http.limit

import dev.meanmail.directives.catalog.*
import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.http.location
import dev.meanmail.directives.catalog.nginx.http.server

// https://nginx.org/en/docs/http/ngx_http_limit_conn_module.html

val ngx_http_limit_conn_module = NginxModule(
    "ngx_http_limit_conn_module",
    description = "Limits the number of connections per defined key, such as connections from a single IP address"
)

val limitConnZone = Directive(
    name = "limit_conn_zone",
    description = "Defines a shared memory zone to store the state of connections",
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
    context = listOf(http),
    module = ngx_http_limit_conn_module
)

val limitConn = Directive(
    name = "limit_conn",
    description = "Sets the maximum allowed number of connections for a given key value",
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
    context = listOf(server, location, http),
    module = ngx_http_limit_conn_module
)

val limitConnDryRun = ToggleDirective(
    "limit_conn_dry_run",
    "Enables dry run mode for connection limiting",
    enabled = false,
    context = listOf(server, location, http),
    module = ngx_http_limit_conn_module
)

val limitConnLogLevel = Directive(
    name = "limit_conn_log_level",
    description = "Sets the logging level for connection limit events",
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
    context = listOf(server, location, http),
    module = ngx_http_limit_conn_module
)

val limitConnStatus = Directive(
    name = "limit_conn_status",
    description = "Sets the HTTP status code returned when the connection limit is exceeded",
    parameters = listOf(
        DirectiveParameter(
            name = "code",
            description = "HTTP status code to return when connection limit is exceeded",
            valueType = ValueType.NUMBER,
            required = true,
            defaultValue = "503"
        )
    ),
    context = listOf(server, location, http),
    module = ngx_http_limit_conn_module
)

val limitZone = Directive(
    name = "limit_zone",
    description = "Sets parameters for a shared memory zone (obsolete since 1.1.8, use limit_conn_zone instead)",
    context = listOf(http),
    module = ngx_http_limit_conn_module
)
