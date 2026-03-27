package dev.meanmail.variables.catalog.nginx.http

import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.http.ngx_http_proxy_module
import dev.meanmail.variables.catalog.Variable

val proxy_host = Variable(
    "proxy_host",
    "name and port of a proxied server as specified in the directive;",
    ngx_http_proxy_module,
    context = http,
)

val proxy_port = Variable(
    "proxy_port",
    "port of a proxied server as specified in the directive, or the protocol’s default port;",
    ngx_http_proxy_module,
    context = http,
)

val proxy_add_x_forwarded_for = Variable(
    "proxy_add_x_forwarded_for",
    "the X-Forwarded-For client request header field with the \$remote_addr variable appended to it, separated by a comma. If the X-Forwarded-For field is not present in the client request header, the \$proxy_add_x_forwarded_for variable is equal to the \$remote_addr variable.",
    ngx_http_proxy_module,
    context = http,
)

val ngx_http_proxy_module_variables: List<Variable> = listOf(
    proxy_host,
    proxy_port,
    proxy_add_x_forwarded_for,
)
