package dev.meanmail.directives.catalog.nginx.google

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/ngx_google_perftools_module.html

val ngx_google_perftools_module = NginxModule(
    name = "ngx_google_perftools_module",
    description = "Enables Google Perftools profiling for Nginx stream module"
)

val streamGooglePerftoolsProfiles = Directive(
    "google_perftools_profiles",
    description = "Configures Google Perftools profiling for Nginx stream module",
    syntax = listOf("<b>google_perftools_profiles</b> <i>file</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            description = "Path to the directory for storing profiling files",
            valueType = ValueType.PATH,
            required = true,
            defaultValue = "/tmp/nginx_profiles"
        )
    ),
    context = listOf(main),
    module = ngx_google_perftools_module
)
