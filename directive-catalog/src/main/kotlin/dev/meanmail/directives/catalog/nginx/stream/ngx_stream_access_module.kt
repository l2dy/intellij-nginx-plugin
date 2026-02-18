package dev.meanmail.directives.catalog.nginx.stream

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/stream/ngx_stream_access_module.html

val ngx_stream_access_module = NginxModule(
    name = "ngx_stream_access_module",
    description = "Comprehensive stream module for fine-grained access control based on client IP addresses, networks, and UNIX-domain sockets"
)

val streamAllow = Directive(
    name = "allow",
    description = "Allows access for specific IP addresses, networks, or UNIX-domain sockets",
    syntax = listOf("<b>allow</b> <i>address</i> | <i>CIDR</i> | unix: | all;"),
    module = ngx_stream_access_module,
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
    context = listOf(stream, streamServer)
)

val streamDeny = Directive(
    name = "deny",
    description = "Denies access for specific IP addresses, networks, or UNIX-domain sockets",
    syntax = listOf("<b>deny</b> <i>address</i> | <i>CIDR</i> | unix: | all;"),
    module = ngx_stream_access_module,
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
    context = listOf(stream, streamServer)
)
