package dev.meanmail.directives.catalog.nginx.http

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/http/ngx_http_random_index_module.html

val ngx_http_random_index_module = NginxModule(
    "ngx_http_random_index_module",
    description = "Processes requests ending with '/' and picks a random file in a directory to serve as an index file"
)

val randomIndex = ToggleDirective(
    "random_index",
    "Enables or disables processing of random index file selection in a location",
    enabled = false,
    context = listOf(location),
    module = ngx_http_random_index_module
)
