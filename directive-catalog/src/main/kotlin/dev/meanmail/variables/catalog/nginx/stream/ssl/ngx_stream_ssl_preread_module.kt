package dev.meanmail.variables.catalog.nginx.stream.ssl

import dev.meanmail.directives.catalog.nginx.stream.stream
import dev.meanmail.directives.catalog.nginx.stream.ssl.ngx_stream_ssl_preread_module
import dev.meanmail.variables.catalog.Variable

val ssl_preread_protocol = Variable(
    "ssl_preread_protocol",
    "the highest SSL protocol version supported by the client (1.15.2)",
    ngx_stream_ssl_preread_module,
    context = stream,
)

val ssl_preread_server_name = Variable(
    "ssl_preread_server_name",
    "server name requested through SNI",
    ngx_stream_ssl_preread_module,
    context = stream,
)

val ssl_preread_alpn_protocols = Variable(
    "ssl_preread_alpn_protocols",
    "list of protocols advertised by the client through ALPN (1.13.10). The values are separated by commas.",
    ngx_stream_ssl_preread_module,
    context = stream,
)

val ngx_stream_ssl_preread_module_variables: List<Variable> = listOf(
    ssl_preread_protocol,
    ssl_preread_server_name,
    ssl_preread_alpn_protocols,
)
