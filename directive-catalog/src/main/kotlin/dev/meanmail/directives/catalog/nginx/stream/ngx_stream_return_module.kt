package dev.meanmail.directives.catalog.nginx.stream

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/stream/ngx_stream_return_module.html

val ngx_stream_return_module = NginxModule(
    name = "ngx_stream_return_module",
    description = "A module to return the specified value in response to a stream request"
)

val streamReturn = Directive(
    name = "return",
    description = "Sends a specified value to the client and closes the connection",
    syntax = listOf("<b>return</b> <i>value</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "value",
            valueType = ValueType.STRING,
            description = "Value to send to the client. Can contain text, variables, or their combination",
        )
    ),
    context = listOf(streamServer),
    module = ngx_stream_return_module
)
