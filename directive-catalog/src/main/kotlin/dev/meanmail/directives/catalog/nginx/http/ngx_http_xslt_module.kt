package dev.meanmail.directives.catalog.nginx.http

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/http/ngx_http_xslt_module.html

val ngx_http_xslt_module = NginxModule(
    "ngx_http_xslt_module",
    description = "A filter that transforms XML responses using one or more XSLT stylesheets. Requires libxml2 and libxslt libraries. Not built by default, must be enabled with --with-http_xslt_module configuration parameter."
)

val xmlEntities = Directive(
    name = "xml_entities",
    description = "Specifies the DTD file that declares character entities. Compiled at configuration stage. Used instead of external XML subset for technical reasons.",
    syntax = listOf("<b>xml_entities</b> <i>path</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            description = "Path to DTD file declaring character entities",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_xslt_module
)

val xsltLastModified = ToggleDirective(
    "xslt_last_modified",
    "Allows preserving the 'Last-Modified' header during XSLT transformations to facilitate response caching.",
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_xslt_module
)

val xsltParam = Directive(
    name = "xslt_param",
    description = "Defines parameters for XSLT stylesheets. Value is treated as an XPath expression and can contain variables.",
    syntax = listOf("<b>xslt_param</b> <i>parameter</i> <i>value</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "parameter",
            description = "XSLT parameter name",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "value",
            description = "XSLT parameter value as XPath expression",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_xslt_module
)

val xsltStringParam = Directive(
    name = "xslt_string_param",
    description = "Defines string parameters for XSLT stylesheets. XPath expressions in the value are not interpreted.",
    syntax = listOf("<b>xslt_string_param</b> <i>parameter</i> <i>value</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "parameter",
            description = "XSLT string parameter name",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "value",
            description = "XSLT string parameter value",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_xslt_module
)

val xsltStylesheet = Directive(
    name = "xslt_stylesheet",
    description = "Defines XSLT stylesheet and optional parameters. Stylesheet is compiled at configuration stage. Multiple stylesheets can be applied sequentially.",
    syntax = listOf("<b>xslt_stylesheet</b> <i>stylesheet</i> [<i>parameter</i>=<i>value</i> ...];"),
    parameters = listOf(
        DirectiveParameter(
            name = "stylesheet",
            description = "Path to XSLT stylesheet",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "parameters",
            description = "Optional stylesheet parameters. Can use ':' delimiter. Escape ':' as '%3A'.",
            valueType = ValueType.STRING,
            required = false
        )
    ),
    context = listOf(location),
    module = ngx_http_xslt_module
)

val xsltTypes = Directive(
    name = "xslt_types",
    description = "Enables transformations for specified MIME types. Special value '*' matches any MIME type. HTML responses changed to 'text/html'.",
    syntax = listOf("<b>xslt_types</b> <i>mime-type</i> ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "mime_type",
            description = "MIME type for XSLT processing",
            valueType = ValueType.STRING,
            required = true,
            defaultValue = "text/xml"
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_xslt_module
)
