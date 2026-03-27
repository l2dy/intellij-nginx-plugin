package dev.meanmail.variables.catalog.nginx.http

import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.http.ngx_http_core_module
import dev.meanmail.variables.catalog.Variable

val arg_ = Variable(
    "arg_",
    "argument {} in the request line",
    ngx_http_core_module,
    parameterized = true,
    context = http,
)

val args = Variable(
    "args",
    "arguments in the request line",
    ngx_http_core_module,
    context = http,
)

val binary_remote_addr = Variable(
    "binary_remote_addr",
    "client address in a binary form, value’s length is always 4 bytes for IPv4 addresses or 16 bytes for IPv6 addresses",
    ngx_http_core_module,
    context = http,
)

val body_bytes_sent = Variable(
    "body_bytes_sent",
    "number of bytes sent to a client, not counting the response header; this variable is compatible with the “%B” parameter of the mod_log_config Apache module",
    ngx_http_core_module,
    context = http,
)

val bytes_sent = Variable(
    "bytes_sent",
    "number of bytes sent to a client (1.3.8, 1.2.5)",
    ngx_http_core_module,
    context = http,
)

val connection = Variable(
    "connection",
    "connection serial number (1.3.8, 1.2.5)",
    ngx_http_core_module,
    context = http,
)

val connection_requests = Variable(
    "connection_requests",
    "current number of requests made through a connection (1.3.8, 1.2.5)",
    ngx_http_core_module,
    context = http,
)

val connection_time = Variable(
    "connection_time",
    "connection time in seconds with a milliseconds resolution (1.19.10)",
    ngx_http_core_module,
    context = http,
)

val content_length = Variable(
    "content_length",
    "Content-Length request header field",
    ngx_http_core_module,
    context = http,
)

val content_type = Variable(
    "content_type",
    "Content-Type request header field",
    ngx_http_core_module,
    context = http,
)

val cookie_ = Variable(
    "cookie_",
    "the {} cookie",
    ngx_http_core_module,
    parameterized = true,
    context = http,
)

val document_root = Variable(
    "document_root",
    "or directive’s value for the current request",
    ngx_http_core_module,
    context = http,
)

val document_uri = Variable(
    "document_uri",
    "same as \$uri",
    ngx_http_core_module,
    context = http,
)

val host = Variable(
    "host",
    "in this order of precedence: host name from the request line, or host name from the Host request header field, or the server name matching a request",
    ngx_http_core_module,
    context = http,
)

val hostname = Variable(
    "hostname",
    "host name",
    ngx_http_core_module,
    context = http,
)

val http_ = Variable(
    "http_",
    "arbitrary request header field; the last part of a variable name is the field name converted to lower case with dashes replaced by underscores",
    ngx_http_core_module,
    parameterized = true,
    context = http,
)

val https = Variable(
    "https",
    "“on” if connection operates in SSL mode, or an empty string otherwise",
    ngx_http_core_module,
    context = http,
)

val is_args = Variable(
    "is_args",
    "“?” if a request line has arguments, or an empty string otherwise",
    ngx_http_core_module,
    context = http,
)

val is_request_port = Variable(
    "is_request_port",
    "“:” if \$request_port is non-empty, or an empty string otherwise (1.29.3)",
    ngx_http_core_module,
    context = http,
)

val limit_rate = Variable(
    "limit_rate",
    "setting this variable enables response rate limiting; see",
    ngx_http_core_module,
    context = http,
)

val msec = Variable(
    "msec",
    "current time in seconds with the milliseconds resolution (1.3.9, 1.2.6)",
    ngx_http_core_module,
    context = http,
)

val nginx_version = Variable(
    "nginx_version",
    "nginx version",
    ngx_http_core_module,
    context = http,
)

val pid = Variable(
    "pid",
    "PID of the worker process",
    ngx_http_core_module,
    context = http,
)

val pipe = Variable(
    "pipe",
    "“p” if request was pipelined, “.” otherwise (1.3.12, 1.2.7)",
    ngx_http_core_module,
    context = http,
)

val proxy_protocol_addr = Variable(
    "proxy_protocol_addr",
    """
    client address from the PROXY protocol header (1.5.12)

    The PROXY protocol must be previously enabled by setting the proxy_protocol parameter in the directive.
    """.trimIndent(),
    ngx_http_core_module,
    context = http,
)

val proxy_protocol_port = Variable(
    "proxy_protocol_port",
    """
    client port from the PROXY protocol header (1.11.0)

    The PROXY protocol must be previously enabled by setting the proxy_protocol parameter in the directive.
    """.trimIndent(),
    ngx_http_core_module,
    context = http,
)

val proxy_protocol_server_addr = Variable(
    "proxy_protocol_server_addr",
    """
    server address from the PROXY protocol header (1.17.6)

    The PROXY protocol must be previously enabled by setting the proxy_protocol parameter in the directive.
    """.trimIndent(),
    ngx_http_core_module,
    context = http,
)

val proxy_protocol_server_port = Variable(
    "proxy_protocol_server_port",
    """
    server port from the PROXY protocol header (1.17.6)

    The PROXY protocol must be previously enabled by setting the proxy_protocol parameter in the directive.
    """.trimIndent(),
    ngx_http_core_module,
    context = http,
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

    Also, the following special SSL TLV type name is supported: ssl_verify - client SSL certificate verification result, 0 if the client presented a certificate and it was successfully verified, non-zero otherwise.

    The PROXY protocol must be previously enabled by setting the proxy_protocol parameter in the directive.
    """.trimIndent(),
    ngx_http_core_module,
    parameterized = true,
    context = http,
)

val query_string = Variable(
    "query_string",
    "same as \$args",
    ngx_http_core_module,
    context = http,
)

val realpath_root = Variable(
    "realpath_root",
    "an absolute pathname corresponding to the or directive’s value for the current request, with all symbolic links resolved to real paths",
    ngx_http_core_module,
    context = http,
)

val remote_addr = Variable(
    "remote_addr",
    "client address",
    ngx_http_core_module,
    context = http,
)

val remote_port = Variable(
    "remote_port",
    "client port",
    ngx_http_core_module,
    context = http,
)

val remote_user = Variable(
    "remote_user",
    "user name supplied with the Basic authentication",
    ngx_http_core_module,
    context = http,
)

val request = Variable(
    "request",
    "full original request line",
    ngx_http_core_module,
    context = http,
)

val request_body = Variable(
    "request_body",
    """
    request body

    The variable’s value is made available in locations processed by the , , , and directives when the request body was read to a memory buffer.
    """.trimIndent(),
    ngx_http_core_module,
    context = http,
)

val request_body_file = Variable(
    "request_body_file",
    """
    name of a temporary file with the request body

    At the end of processing, the file needs to be removed. To always write the request body to a file, needs to be enabled. When the name of a temporary file is passed in a proxied request or in a request to a FastCGI/uwsgi/SCGI server, passing the request body should be disabled by the proxy_pass_request_body off, fastcgi_pass_request_body off, uwsgi_pass_request_body off, or scgi_pass_request_body off directives, respectively.
    """.trimIndent(),
    ngx_http_core_module,
    context = http,
)

val request_completion = Variable(
    "request_completion",
    "“OK” if a request has completed, or an empty string otherwise",
    ngx_http_core_module,
    context = http,
)

val request_filename = Variable(
    "request_filename",
    "file path for the current request, based on the or directives, and the request URI",
    ngx_http_core_module,
    context = http,
)

val request_id = Variable(
    "request_id",
    "unique request identifier generated from 16 random bytes, in hexadecimal (1.11.0)",
    ngx_http_core_module,
    context = http,
)

val request_length = Variable(
    "request_length",
    "request length (including request line, header, and request body) (1.3.12, 1.2.7)",
    ngx_http_core_module,
    context = http,
)

val request_method = Variable(
    "request_method",
    "request method, usually “GET” or “POST”",
    ngx_http_core_module,
    context = http,
)

val request_port = Variable(
    "request_port",
    "in this order of precedence: port number from the URI authority component, or port number from the Host request header field (1.29.3)",
    ngx_http_core_module,
    context = http,
)

val request_time = Variable(
    "request_time",
    "request processing time in seconds with a milliseconds resolution (1.3.9, 1.2.6); time elapsed since the first bytes were read from the client",
    ngx_http_core_module,
    context = http,
)

val request_uri = Variable(
    "request_uri",
    "full original request URI (with arguments)",
    ngx_http_core_module,
    context = http,
)

val scheme = Variable(
    "scheme",
    "request scheme, “http” or “https”",
    ngx_http_core_module,
    context = http,
)

val sent_http_ = Variable(
    "sent_http_",
    "arbitrary response header field; the last part of a variable name is the field name converted to lower case with dashes replaced by underscores",
    ngx_http_core_module,
    parameterized = true,
    context = http,
)

val sent_trailer_ = Variable(
    "sent_trailer_",
    "arbitrary field sent at the end of the response (1.13.2); the last part of a variable name is the field name converted to lower case with dashes replaced by underscores",
    ngx_http_core_module,
    parameterized = true,
    context = http,
)

val server_addr = Variable(
    "server_addr",
    """
    an address of the server which accepted a request

    Computing a value of this variable usually requires one system call. To avoid a system call, the directives must specify addresses and use the bind parameter.
    """.trimIndent(),
    ngx_http_core_module,
    context = http,
)

val server_name = Variable(
    "server_name",
    "name of the server which accepted a request",
    ngx_http_core_module,
    context = http,
)

val server_port = Variable(
    "server_port",
    "port of the server which accepted a request",
    ngx_http_core_module,
    context = http,
)

val server_protocol = Variable(
    "server_protocol",
    "request protocol, usually “HTTP/1.0”, “HTTP/1.1”, “HTTP/2.0”, or “HTTP/3.0”",
    ngx_http_core_module,
    context = http,
)

val status = Variable(
    "status",
    "response status (1.3.2, 1.2.2)",
    ngx_http_core_module,
    context = http,
)

val tcpinfo_rtt = Variable(
    "tcpinfo_rtt",
    "information about the client TCP connection; available on systems that support the TCP_INFO socket option",
    ngx_http_core_module,
    context = http,
)

val time_iso8601 = Variable(
    "time_iso8601",
    "local time in the ISO 8601 standard format (1.3.12, 1.2.7)",
    ngx_http_core_module,
    context = http,
)

val time_local = Variable(
    "time_local",
    "local time in the Common Log Format (1.3.12, 1.2.7)",
    ngx_http_core_module,
    context = http,
)

val uri = Variable(
    "uri",
    """
    current URI in request, normalized

    The value of ${'$'}uri may change during request processing, e.g. when doing internal redirects, or when using index files.
    """.trimIndent(),
    ngx_http_core_module,
    context = http,
)

val ngx_http_core_module_variables: List<Variable> = listOf(
    arg_,
    args,
    binary_remote_addr,
    body_bytes_sent,
    bytes_sent,
    connection,
    connection_requests,
    connection_time,
    content_length,
    content_type,
    cookie_,
    document_root,
    document_uri,
    host,
    hostname,
    http_,
    https,
    is_args,
    is_request_port,
    limit_rate,
    msec,
    nginx_version,
    pid,
    pipe,
    proxy_protocol_addr,
    proxy_protocol_port,
    proxy_protocol_server_addr,
    proxy_protocol_server_port,
    proxy_protocol_tlv_,
    query_string,
    realpath_root,
    remote_addr,
    remote_port,
    remote_user,
    request,
    request_body,
    request_body_file,
    request_completion,
    request_filename,
    request_id,
    request_length,
    request_method,
    request_port,
    request_time,
    request_uri,
    scheme,
    sent_http_,
    sent_trailer_,
    server_addr,
    server_name,
    server_port,
    server_protocol,
    status,
    tcpinfo_rtt,
    time_iso8601,
    time_local,
    uri,
)
