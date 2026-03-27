package dev.meanmail.variables.catalog.nginx.stream.upstream

import dev.meanmail.directives.catalog.nginx.stream.stream
import dev.meanmail.directives.catalog.nginx.stream.upstream.ngx_stream_upstream_module
import dev.meanmail.variables.catalog.Variable

val upstream_addr = Variable(
    "upstream_addr",
    "keeps the IP address and port, or the path to the UNIX-domain socket of the upstream server (1.11.4). If several servers were contacted during proxying, their addresses are separated by commas, e.g. “192.168.1.1:12345, 192.168.1.2:12345, unix:/tmp/sock”. If a server cannot be selected, the variable keeps the name of the server group.",
    ngx_stream_upstream_module,
    context = stream,
)

val upstream_bytes_received = Variable(
    "upstream_bytes_received",
    "number of bytes received from an upstream server (1.11.4). Values from several connections are separated by commas like addresses in the \$upstream_addr variable.",
    ngx_stream_upstream_module,
    context = stream,
)

val upstream_bytes_sent = Variable(
    "upstream_bytes_sent",
    "number of bytes sent to an upstream server (1.11.4). Values from several connections are separated by commas like addresses in the \$upstream_addr variable.",
    ngx_stream_upstream_module,
    context = stream,
)

val upstream_connect_time = Variable(
    "upstream_connect_time",
    "time to connect to the upstream server (1.11.4); the time is kept in seconds with millisecond resolution. Times of several connections are separated by commas like addresses in the \$upstream_addr variable.",
    ngx_stream_upstream_module,
    context = stream,
)

val upstream_first_byte_time = Variable(
    "upstream_first_byte_time",
    "time to receive the first byte of data (1.11.4); the time is kept in seconds with millisecond resolution. Times of several connections are separated by commas like addresses in the \$upstream_addr variable.",
    ngx_stream_upstream_module,
    context = stream,
)

val upstream_last_addr = Variable(
    "upstream_last_addr",
    """
    keeps the IP address or the path to the UNIX-domain socket of the last selected upstream server (1.29.3).

    This variable is available as part of our commercial subscription.
    """.trimIndent(),
    ngx_stream_upstream_module,
    context = stream,
)

val upstream_session_time = Variable(
    "upstream_session_time",
    "session duration in seconds with millisecond resolution (1.11.4). Times of several connections are separated by commas like addresses in the \$upstream_addr variable.",
    ngx_stream_upstream_module,
    context = stream,
)

val ngx_stream_upstream_module_variables: List<Variable> = listOf(
    upstream_addr,
    upstream_bytes_received,
    upstream_bytes_sent,
    upstream_connect_time,
    upstream_first_byte_time,
    upstream_last_addr,
    upstream_session_time,
)
