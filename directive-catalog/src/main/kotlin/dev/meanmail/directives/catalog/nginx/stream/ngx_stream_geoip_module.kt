package dev.meanmail.directives.catalog.nginx.stream

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/stream/ngx_stream_geoip_module.html

val ngx_stream_geoip_module = NginxModule(
    name = "ngx_stream_geoip_module",
    description = "The GeoIP module for NGINX"
)

val streamGeoipCountry = Directive(
    name = "geoip_country",
    description = "Specifies the path to the GeoIP country database",
    syntax = listOf("<b>geoip_country</b> <i>file</i>;"),
    module = ngx_stream_geoip_module,
    parameters = listOf(
        DirectiveParameter(
            name = "database_file",
            description = "Path to the MaxMind GeoIP database file",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(stream)
)

val streamGeoipCity = Directive(
    name = "geoip_city",
    description = "Specifies the path to the GeoIP city database",
    syntax = listOf("<b>geoip_city</b> <i>file</i>;"),
    module = ngx_stream_geoip_module,
    parameters = listOf(
        DirectiveParameter(
            name = "database_file",
            description = "Path to the MaxMind GeoIP database file",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(stream)
)

val streamGeoipOrg = Directive(
    name = "geoip_org",
    description = "Specifies the path to the GeoIP organization database",
    syntax = listOf("<b>geoip_org</b> <i>file</i>;"),
    module = ngx_stream_geoip_module,
    parameters = listOf(
        DirectiveParameter(
            name = "database_file",
            description = "Path to the MaxMind GeoIP database file",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(stream)
)
