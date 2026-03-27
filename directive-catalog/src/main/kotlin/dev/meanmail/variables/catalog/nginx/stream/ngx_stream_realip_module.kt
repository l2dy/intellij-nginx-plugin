package dev.meanmail.variables.catalog.nginx.stream

import dev.meanmail.directives.catalog.nginx.stream.stream
import dev.meanmail.directives.catalog.nginx.stream.ngx_stream_realip_module
import dev.meanmail.variables.catalog.Variable

val realip_remote_addr = Variable(
    "realip_remote_addr",
    "keeps the original client address",
    ngx_stream_realip_module,
    context = stream,
)

val realip_remote_port = Variable(
    "realip_remote_port",
    "keeps the original client port",
    ngx_stream_realip_module,
    context = stream,
)

val ngx_stream_realip_module_variables: List<Variable> = listOf(
    realip_remote_addr,
    realip_remote_port,
)
