package dev.meanmail.directives.catalog.nginx.http

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/http/ngx_http_geoip_module.html

val ngx_http_geoip_module = NginxModule(
    "ngx_http_geoip_module",
    description = "Provides geolocation functionality using MaxMind GeoIP databases for IP-based information retrieval"
)

val geoipCountry = Directive(
    name = "geoip_country",
    description = "Specifies the path to the MaxMind GeoIP country database for determining the country of the client's IP address",
    syntax = listOf("<b>geoip_country</b> <i>file</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "database_file",
            description = "Path to the MaxMind GeoIP database file",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http),
    module = ngx_http_geoip_module
)

val geoipCity = Directive(
    name = "geoip_city",
    description = "Specifies the path to the MaxMind GeoIP city database for determining detailed location information",
    syntax = listOf("<b>geoip_city</b> <i>file</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "database_file",
            description = "Path to the MaxMind GeoIP database file",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http),
    module = ngx_http_geoip_module
)

val geoipOrg = Directive(
    name = "geoip_org",
    description = "Specifies the path to the MaxMind GeoIP organization database for determining the organization associated with an IP address",
    syntax = listOf("<b>geoip_org</b> <i>file</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "database_file",
            description = "Path to the MaxMind GeoIP database file",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http),
    module = ngx_http_geoip_module
)

val geoipProxy = Directive(
    name = "geoip_proxy",
    description = "Defines trusted proxy networks for correctly identifying the original client IP address when behind a proxy",
    syntax = listOf("<b>geoip_proxy</b> <i>address</i> | <i>CIDR</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "network",
            description = "IP address or network range of trusted proxy servers",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http),
    module = ngx_http_geoip_module
)

val geoipProxyRecursive = ToggleDirective(
    "geoip_proxy_recursive",
    "Determines whether to recursively search for the original client IP address through multiple proxy layers",
    enabled = false,
    context = listOf(http),
    module = ngx_http_geoip_module
)
