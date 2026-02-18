package dev.meanmail.directives.catalog.nginx.http

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/http/ngx_http_realip_module.html

val ngx_http_realip_module = NginxModule(
    "ngx_http_realip_module",
    description = "Provides a way to obtain the client's IP address from a proxy or a load balancer"
)

val setRealIpFrom = Directive(
    name = "set_real_ip_from",
    description = "Defines a network or IP address from which the real client IP should be obtained",
    syntax = listOf("<b>set_real_ip_from</b> <i>address</i> | <i>CIDR</i> | unix:;"),
    parameters = listOf(
        DirectiveParameter(
            name = "address",
            valueType = ValueType.STRING,
            description = "IP address, network range (CIDR), or 'unix:' to trust all UNIX-domain sockets",
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_realip_module
)

val realIpHeader = Directive(
    name = "real_ip_header",
    description = "Specifies the header field used to obtain the real client IP address",
    syntax = listOf("<b>real_ip_header</b> <i>field</i> | X-Real-IP | X-Forwarded-For | proxy_protocol;"),
    parameters = listOf(
        DirectiveParameter(
            name = "header",
            description = "Name of the header containing the real client IP (e.g., X-Forwarded-For, X-Real-IP)",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_realip_module
)

val realIpRecursive = ToggleDirective(
    "real_ip_recursive",
    "Enables recursive search for the real client IP when multiple proxy servers are involved",
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_realip_module
)
