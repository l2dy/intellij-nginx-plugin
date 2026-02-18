package dev.meanmail.directives.catalog.nginx.http

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/http/ngx_http_geo_module.html

val ngx_http_geo_module = NginxModule(
    "ngx_http_geo_module",
    description = "Provides a way to create variables based on client IP addresses"
)

val geo = Directive(
    name = "geo",
    description = "Creates variables with values depending on the client IP address, useful for creating IP-based access controls or conditional configurations",
    syntax = listOf("<b>geo</b> [<i>\$address</i>] <i>\$variable</i> { ... }"),
    parameters = listOf(
        DirectiveParameter(
            name = "address_variable",
            description = "Optional variable for IP address (default: \$remote_addr)",
            valueType = ValueType.STRING,
            required = false
        ),
        DirectiveParameter(
            name = "result_variable",
            description = "Variable to store geolocation result",
            valueType = ValueType.STRING
        )
    ),
    context = listOf(http),
    module = ngx_http_geo_module
)
