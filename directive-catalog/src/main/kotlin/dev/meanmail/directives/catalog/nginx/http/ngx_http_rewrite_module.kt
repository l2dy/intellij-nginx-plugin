package dev.meanmail.directives.catalog.nginx.http

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/http/ngx_http_rewrite_module.html

val ngx_http_rewrite_module = NginxModule(
    name = "ngx_http_rewrite_module",
    description = "Provides URI transformation, conditional configuration selection, and request processing control using PCRE regular expressions"
)

val locationIf = Directive(
    name = "if",
    description = "Creates a conditional block within a location context, executing directives when the specified condition is true",
    parameters = listOf(
        DirectiveParameter(
            name = "condition",
            description = "Condition to evaluate, supporting variable checks, comparisons, regex matching, and file existence tests",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(location),
    module = ngx_http_rewrite_module
)

val `if` = Directive(
    name = "if",
    description = "Creates a conditional configuration block that executes directives when the specified condition is true",
    parameters = listOf(
        DirectiveParameter(
            name = "condition",
            description = "Condition to evaluate, supporting variable checks, comparisons, regex matching, and file existence tests",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(server, location),
    module = ngx_http_rewrite_module
)

val `break` = Directive(
    name = "break",
    description = "Stops processing the current set of rewrite module directives and continues request processing in the current location",

    context = listOf(server, location, `if`),
    module = ngx_http_rewrite_module
)

val `return` = Directive(
    name = "return",
    description = "Stops request processing and returns a specified HTTP status code or redirects to another URL",
    parameters = listOf(
        DirectiveParameter(
            name = "code",
            description = "HTTP status code (e.g., 200, 301, 404) or URL for redirection",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(server, location, `if`),
    module = ngx_http_rewrite_module
)

val rewrite = Directive(
    name = "rewrite",
    description = "Modifies the request URI using PCRE regular expressions, potentially changing the request processing path",
    parameters = listOf(
        DirectiveParameter(
            name = "regex",
            description = "PCRE regular expression to match and transform the request URI",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "replacement",
            description = "Replacement URI pattern, can include captured regex groups",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "flag",
            description = "Optional processing flag (last, break, redirect, permanent)",
            valueType = ValueType.STRING,
            required = false
        )
    ),
    context = listOf(server, location, `if`),
    module = ngx_http_rewrite_module
)

val rewriteLog = ToggleDirective(
    "rewrite_log",
    "Enables or disables logging of rewrite module directive processing for debugging",
    enabled = false,
    context = listOf(http, server, location, `if`),
    module = ngx_http_rewrite_module
)

val set = Directive(
    name = "set",
    description = "Assigns a value to a specified NGINX variable for dynamic request processing",
    parameters = listOf(
        DirectiveParameter(
            name = "variable",
            valueType = ValueType.STRING,
            description = "Name of the variable to set",
        ),
        DirectiveParameter(
            name = "value",
            valueType = ValueType.STRING,
            description = "Value to assign to the variable",
        )
    ),
    context = listOf(server, location, `if`),
    module = ngx_http_rewrite_module
)

val uninitializedVariableWarn = ToggleDirective(
    "uninitialized_variable_warn",
    "Controls logging of warnings when uninitialized variables are encountered during request processing",
    enabled = true,
    context = listOf(http, server, location, `if`),
    module = ngx_http_rewrite_module
)
