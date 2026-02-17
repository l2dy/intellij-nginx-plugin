package dev.meanmail.directives.catalog.nginx.http

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/http/ngx_http_map_module.html

val ngx_http_map_module = NginxModule(
    "ngx_http_map_module",
    description = "Creates variables whose values depend on values of other variables"
)

val map = Directive(
    name = "map",
    description = "Creates a new variable whose value depends on values of one or more source variables. " +
            "Supports special mapping values like 'default' (catch-all rule) and '' (empty string mapping).",
    parameters = listOf(
        DirectiveParameter(
            name = "source",
            valueType = ValueType.STRING,
            description = "Source variable for mapping. Can contain built-in NGINX variables",
            required = true
        ),
        DirectiveParameter(
            name = "variable",
            valueType = ValueType.STRING,
            description = "Target variable to store the transformed value. Starts with '$' symbol",
            required = true
        ),
        DirectiveParameter(
            name = "mapping",
            valueType = ValueType.STRING,
            description = "Key-value mapping rules for variable transformation. Supports wildcards and regular expressions",
            required = true
        )
    ),
    context = listOf(http),
    module = ngx_http_map_module
)

val mapHashBucketSize = Directive(
    name = "map_hash_bucket_size",
    description = "Sets the bucket size for the map variables hash tables",
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            valueType = ValueType.SIZE,
            description = "Size of the hash table bucket in bytes. Affects search efficiency and memory usage",
            required = false,
            defaultValue = "64",
            minValue = 32,
            maxValue = 128
        )
    ),
    context = listOf(http),
    module = ngx_http_map_module
)

val mapHashMaxSize = Directive(
    name = "map_hash_max_size",
    description = "Sets the maximum size of the map variables hash tables",
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            valueType = ValueType.NUMBER,
            description = "Maximum size of the hash table. Determines the number of elements that can be efficiently processed",
            required = false,
            defaultValue = "2048",
            minValue = 128,
            maxValue = 65536
        )
    ),
    context = listOf(http),
    module = ngx_http_map_module
)
