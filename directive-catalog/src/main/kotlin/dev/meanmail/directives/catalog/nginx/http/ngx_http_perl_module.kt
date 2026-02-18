package dev.meanmail.directives.catalog.nginx.http

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/http/ngx_http_perl_module.html

val ngx_http_perl_module = NginxModule(
    name = "ngx_http_perl_module",
    description = "Implements location and variable handlers in Perl and allows Perl calls in SSI"
)

val perl = Directive(
    name = "perl",
    description = "Sets a Perl handler for the given location",
    syntax = listOf("<b>perl</b> <i>module</i>::<i>function</i>|'sub { ... }';"),
    parameters = listOf(
        DirectiveParameter(
            name = "handler",
            description = "Perl module::function or anonymous subroutine",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(location, limitExcept),
    module = ngx_http_perl_module
)

val perlModules = Directive(
    name = "perl_modules",
    description = "Sets an additional path for Perl modules",
    syntax = listOf("<b>perl_modules</b> <i>path</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            description = "Directory path for additional Perl modules",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http),
    module = ngx_http_perl_module
)

val perlRequire = Directive(
    name = "perl_require",
    description = "Defines a module to be loaded during each reconfiguration",
    syntax = listOf("<b>perl_require</b> <i>module</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "module",
            description = "Name of the Perl module to require",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http),
    module = ngx_http_perl_module
)

val perlSet = Directive(
    name = "perl_set",
    description = "Installs a Perl handler for the specified variable",
    syntax = listOf("<b>perl_set</b> <i>\$variable</i> <i>module</i>::<i>function</i>|'sub { ... }';"),
    parameters = listOf(
        DirectiveParameter(
            name = "variable",
            description = "NGINX variable to set using Perl",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "handler",
            description = "Perl module::function or anonymous subroutine",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http),
    module = ngx_http_perl_module
)
