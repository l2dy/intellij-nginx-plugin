package dev.meanmail.directives.catalog.nginx.http

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/http/ngx_http_userid_module.html

val ngx_http_userid_module = NginxModule(
    name = "ngx_http_userid_module",
    description = """
        The Userid module sets cookies suitable for client identification.
        Received and set cookies can be logged using embedded variables \$\uid_got and \$\uid_set.
        Compatible with mod_uid module for Apache.
    """.trimIndent()
)

val userid = Directive(
    name = "userid",
    description = "Enables or disables setting cookies and logging received cookies",
    syntax = listOf("<b>userid</b> on | v1 | log | off;"),
    parameters = listOf(
        DirectiveParameter(
            name = "mode",
            description = "Cookie mode: on (v2 cookies), v1 (v1 cookies), log (only log), off (disable)",
            valueType = ValueType.STRING,
            required = true,
            defaultValue = "off",
            allowedValues = listOf("on", "v1", "log", "off")
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_userid_module
)

val useridDomain = Directive(
    name = "userid_domain",
    description = "Defines a domain for which the cookie is set",
    syntax = listOf("<b>userid_domain</b> <i>name</i> | none;"),
    parameters = listOf(
        DirectiveParameter(
            name = "domain",
            description = "Domain name or 'none' to disable domain setting",
            valueType = ValueType.STRING,
            required = true,
            defaultValue = "none"
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_userid_module
)

val useridExpires = Directive(
    name = "userid_expires",
    description = "Sets the time during which a browser should keep the cookie",
    syntax = listOf("<b>userid_expires</b> <i>time</i> | max | off;"),
    parameters = listOf(
        DirectiveParameter(
            name = "expiration",
            description = "Cookie expiration: time, 'max' (31 Dec 2037), or 'off' (session end)",
            valueType = ValueType.STRING,
            required = true,
            defaultValue = "off",
            allowedValues = listOf("max", "off")
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_userid_module
)

val useridFlags = Directive(
    name = "userid_flags",
    description = "Defines additional flags for the cookie",
    syntax = listOf("<b>userid_flags</b> off | <i>flag</i> ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "flags",
            description = "Cookie flags: secure, httponly, samesite=strict/lax/none",
            valueType = ValueType.STRING,
            required = true,
            defaultValue = "off",
            allowedValues = listOf(
                "off", "secure", "httponly",
                "samesite=strict", "samesite=lax", "samesite=none"
            )
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_userid_module
)

val useridMark = Directive(
    name = "userid_mark",
    description = "Sets a mark for the user identification cookie",
    syntax = listOf("<b>userid_mark</b> <i>letter</i> | <i>digit</i> | = | off;"),
    parameters = listOf(
        DirectiveParameter(
            name = "mark",
            description = "Unique mark for the cookie",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_userid_module
)

val useridName = Directive(
    name = "userid_name",
    description = "Sets the name of the user identification cookie",
    syntax = listOf("<b>userid_name</b> <i>name</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "name",
            description = "Name of the user identification cookie",
            valueType = ValueType.STRING,
            required = true,
            defaultValue = "uid"
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_userid_module
)

val useridP3P = Directive(
    name = "userid_p3p",
    description = "Sets the P3P privacy policy for the user identification cookie",
    syntax = listOf("<b>userid_p3p</b> <i>string</i> | none;"),
    parameters = listOf(
        DirectiveParameter(
            name = "policy",
            description = "P3P privacy policy string",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_userid_module
)

val useridPath = Directive(
    name = "userid_path",
    description = "Sets the path for the user identification cookie",
    syntax = listOf("<b>userid_path</b> <i>path</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            description = "Path for the cookie",
            valueType = ValueType.STRING,
            required = true,
            defaultValue = "/"
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_userid_module
)

val useridService = Directive(
    name = "userid_service",
    description = "Sets the service identifier for the user identification cookie",
    syntax = listOf("<b>userid_service</b> <i>number</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "service_id",
            description = "Unique service identifier",
            valueType = ValueType.INTEGER,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_userid_module
)
