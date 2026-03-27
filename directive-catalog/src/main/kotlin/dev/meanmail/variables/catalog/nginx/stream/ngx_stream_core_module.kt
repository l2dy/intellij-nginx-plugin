package dev.meanmail.variables.catalog.nginx.stream

import dev.meanmail.directives.catalog.nginx.stream.stream
import dev.meanmail.directives.catalog.nginx.stream.ngx_stream_core_module
import dev.meanmail.variables.catalog.Variable

val binary_remote_addr = Variable(
    "binary_remote_addr",
    "client address in a binary form, value’s length is always 4 bytes for IPv4 addresses or 16 bytes for IPv6 addresses",
    ngx_stream_core_module,
    context = stream,
)

val bytes_received = Variable(
    "bytes_received",
    "number of bytes received from a client (1.11.4)",
    ngx_stream_core_module,
    context = stream,
)

val bytes_sent = Variable(
    "bytes_sent",
    "number of bytes sent to a client",
    ngx_stream_core_module,
    context = stream,
)

val connection = Variable(
    "connection",
    "connection serial number",
    ngx_stream_core_module,
    context = stream,
)

val hostname = Variable(
    "hostname",
    "host name",
    ngx_stream_core_module,
    context = stream,
)

val msec = Variable(
    "msec",
    "current time in seconds with the milliseconds resolution",
    ngx_stream_core_module,
    context = stream,
)

val nginx_version = Variable(
    "nginx_version",
    "nginx version",
    ngx_stream_core_module,
    context = stream,
)

val pid = Variable(
    "pid",
    "PID of the worker process",
    ngx_stream_core_module,
    context = stream,
)

val protocol = Variable(
    "protocol",
    "protocol used to communicate with the client: TCP or UDP (1.11.4)",
    ngx_stream_core_module,
    context = stream,
)

val proxy_protocol_addr = Variable(
    "proxy_protocol_addr",
    """
    client address from the PROXY protocol header (1.11.4)

    The PROXY protocol must be previously enabled by setting the proxy_protocol parameter in the directive.
    """.trimIndent(),
    ngx_stream_core_module,
    context = stream,
)

val proxy_protocol_port = Variable(
    "proxy_protocol_port",
    """
    client port from the PROXY protocol header (1.11.4)

    The PROXY protocol must be previously enabled by setting the proxy_protocol parameter in the directive.
    """.trimIndent(),
    ngx_stream_core_module,
    context = stream,
)

val proxy_protocol_server_addr = Variable(
    "proxy_protocol_server_addr",
    """
    server address from the PROXY protocol header (1.17.6)

    The PROXY protocol must be previously enabled by setting the proxy_protocol parameter in the directive.
    """.trimIndent(),
    ngx_stream_core_module,
    context = stream,
)

val proxy_protocol_server_port = Variable(
    "proxy_protocol_server_port",
    """
    server port from the PROXY protocol header (1.17.6)

    The PROXY protocol must be previously enabled by setting the proxy_protocol parameter in the directive.
    """.trimIndent(),
    ngx_stream_core_module,
    context = stream,
)

val proxy_protocol_tlv_ = Variable(
    "proxy_protocol_tlv_",
    """
    TLV from the PROXY Protocol header (1.23.2). The name can be a TLV type name or its numeric value. In the latter case, the value is hexadecimal and should be prefixed with 0x:

    <pre><code>${'$'}proxy_protocol_tlv_alpn
${'$'}proxy_protocol_tlv_0x01</code></pre>

    SSL TLVs can also be accessed by TLV type name or its numeric value, both prefixed by ssl_:

    <pre><code>${'$'}proxy_protocol_tlv_ssl_version
${'$'}proxy_protocol_tlv_ssl_0x21</code></pre>

    The following TLV type names are supported: alpn (0x01) - upper layer protocol used over the connection authority (0x02) - host name value passed by the client unique_id (0x05) - unique connection id netns (0x30) - name of the namespace ssl (0x20) - binary SSL TLV structure

    The following SSL TLV type names are supported: ssl_version (0x21) - SSL version used in client connection ssl_cn (0x22) - SSL certificate Common Name ssl_cipher (0x23) - name of the used cipher ssl_sig_alg (0x24) - algorithm used to sign the certificate ssl_key_alg (0x25) - public-key algorithm

    Also, the following special SSL TLV type name is supported: ssl_verify - client SSL certificate verification result, zero if the client presented a certificate and it was successfully verified, and non-zero otherwise

    The PROXY protocol must be previously enabled by setting the proxy_protocol parameter in the directive.
    """.trimIndent(),
    ngx_stream_core_module,
    parameterized = true,
    context = stream,
)

val remote_addr = Variable(
    "remote_addr",
    "client address",
    ngx_stream_core_module,
    context = stream,
)

val remote_port = Variable(
    "remote_port",
    "client port",
    ngx_stream_core_module,
    context = stream,
)

val server_addr = Variable(
    "server_addr",
    """
    an address of the server which accepted a connection

    Computing a value of this variable usually requires one system call. To avoid a system call, the directives must specify addresses and use the bind parameter.
    """.trimIndent(),
    ngx_stream_core_module,
    context = stream,
)

val server_port = Variable(
    "server_port",
    "port of the server which accepted a connection",
    ngx_stream_core_module,
    context = stream,
)

val session_time = Variable(
    "session_time",
    "session duration in seconds with a milliseconds resolution (1.11.4);",
    ngx_stream_core_module,
    context = stream,
)

val status = Variable(
    "status",
    """
    session status (1.11.4), can be one of the following:

    200 session completed successfully 400 client data could not be parsed, for example, the PROXY protocol header 403 access forbidden, for example, when access is limited for certain client addresses 500 internal server error 502 bad gateway, for example, if an upstream server could not be selected or reached. 503 service unavailable, for example, when access is limited by the number of connections
    """.trimIndent(),
    ngx_stream_core_module,
    context = stream,
)

val time_iso8601 = Variable(
    "time_iso8601",
    "local time in the ISO 8601 standard format",
    ngx_stream_core_module,
    context = stream,
)

val time_local = Variable(
    "time_local",
    "local time in the Common Log Format",
    ngx_stream_core_module,
    context = stream,
)

val ngx_stream_core_module_variables: List<Variable> = listOf(
    binary_remote_addr,
    bytes_received,
    bytes_sent,
    connection,
    hostname,
    msec,
    nginx_version,
    pid,
    protocol,
    proxy_protocol_addr,
    proxy_protocol_port,
    proxy_protocol_server_addr,
    proxy_protocol_server_port,
    proxy_protocol_tlv_,
    remote_addr,
    remote_port,
    server_addr,
    server_port,
    session_time,
    status,
    time_iso8601,
    time_local,
)
