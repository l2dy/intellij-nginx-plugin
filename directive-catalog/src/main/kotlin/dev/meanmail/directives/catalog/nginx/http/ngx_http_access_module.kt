package dev.meanmail.directives.catalog.nginx.http

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/http/ngx_http_access_module.html

val ngx_http_access_module = NginxModule(
    name = "ngx_http_access_module",
    description = "Permits or denies access based on IP addresses or networks"
)

val allow = Directive(
    "allow",
    description = "Allows access for specified IP addresses or networks",
    parameters = listOf(
        DirectiveParameter(
            name = "address",
            description = "IP address, network range, or UNIX-domain socket",
            valueType = ValueType.STRING
        ),
        DirectiveParameter(
            name = "type",
            description = "Optional type of address (CIDR notation, IPv4/IPv6)",
            valueType = ValueType.STRING,
            required = false
        )
    ),
    context = listOf(http, server, location, limitExcept),
    module = ngx_http_access_module
)

val deny = Directive(
    "deny",
    description = "Denies access for specified IP addresses or networks",
    parameters = listOf(
        DirectiveParameter(
            name = "address",
            description = "IP address, network range, or UNIX-domain socket",
            valueType = ValueType.STRING
        ),
        DirectiveParameter(
            name = "type",
            description = "Optional type of address (CIDR notation, IPv4/IPv6)",
            valueType = ValueType.STRING,
            required = false
        )
    ),
    context = listOf(http, server, location, limitExcept),
    module = ngx_http_access_module
)
