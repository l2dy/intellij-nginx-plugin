package dev.meanmail.variables.catalog.nginx.http.upstream

import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.http.upstream.ngx_http_upstream_module
import dev.meanmail.variables.catalog.Variable

val upstream_addr = Variable(
    "upstream_addr",
    "keeps the IP address and port, or the path to the UNIX-domain socket of the upstream server. If several servers were contacted during request processing, their addresses are separated by commas, e.g. “192.168.1.1:80, 192.168.1.2:80, unix:/tmp/sock”. If an internal redirect from one server group to another happens, initiated by X-Accel-Redirect or , then the server addresses from different groups are separated by colons, e.g. “192.168.1.1:80, 192.168.1.2:80, unix:/tmp/sock : 192.168.10.1:80, 192.168.10.2:80”. If a server cannot be selected, the variable keeps the name of the server group.",
    ngx_http_upstream_module,
    context = http,
)

val upstream_bytes_received = Variable(
    "upstream_bytes_received",
    "number of bytes received from an upstream server (1.11.4). Values from several connections are separated by commas and colons like addresses in the \$upstream_addr variable.",
    ngx_http_upstream_module,
    context = http,
)

val upstream_bytes_sent = Variable(
    "upstream_bytes_sent",
    "number of bytes sent to an upstream server (1.15.8). Values from several connections are separated by commas and colons like addresses in the \$upstream_addr variable.",
    ngx_http_upstream_module,
    context = http,
)

val upstream_cache_status = Variable(
    "upstream_cache_status",
    "keeps the status of accessing a response cache (0.8.3). The status can be either “MISS”, “BYPASS”, “EXPIRED”, “STALE”, “UPDATING”, “REVALIDATED”, or “HIT”.",
    ngx_http_upstream_module,
    context = http,
)

val upstream_connect_time = Variable(
    "upstream_connect_time",
    "keeps time spent on establishing a connection with the upstream server (1.9.1); the time is kept in seconds with millisecond resolution. In case of SSL, includes time spent on handshake. Times of several connections are separated by commas and colons like addresses in the \$upstream_addr variable.",
    ngx_http_upstream_module,
    context = http,
)

val upstream_cookie_ = Variable(
    "upstream_cookie_",
    "cookie with the specified {} sent by the upstream server in the Set-Cookie response header field (1.7.1). Only the cookies from the response of the last server are saved.",
    ngx_http_upstream_module,
    parameterized = true,
    context = http,
)

val upstream_header_time = Variable(
    "upstream_header_time",
    "keeps time spent on receiving the response header from the upstream server (1.7.10); the time is kept in seconds with millisecond resolution. Times of several responses are separated by commas and colons like addresses in the \$upstream_addr variable.",
    ngx_http_upstream_module,
    context = http,
)

val upstream_http_ = Variable(
    "upstream_http_",
    "keep server response header fields. For example, the Server response header field is available through the \$upstream_http_server variable. The rules of converting header field names to variable names are the same as for the variables that start with the “\$http_” prefix. Only the header fields from the response of the last server are saved.",
    ngx_http_upstream_module,
    parameterized = true,
    context = http,
)

val upstream_last_addr = Variable(
    "upstream_last_addr",
    """
    keeps the IP address or the path to the UNIX-domain socket of the last selected upstream server (1.29.3).

    This variable is available as part of our commercial subscription.
    """.trimIndent(),
    ngx_http_upstream_module,
    context = http,
)

val upstream_last_server_name = Variable(
    "upstream_last_server_name",
    """
    keeps the name of last selected upstream server (1.25.3); allows passing it through SNI:

    <pre><code>proxy_ssl_server_name on;
proxy_ssl_name        ${'$'}upstream_last_server_name;</code></pre>

    This variable is available as part of our commercial subscription.
    """.trimIndent(),
    ngx_http_upstream_module,
    context = http,
)

val upstream_queue_time = Variable(
    "upstream_queue_time",
    "keeps time the request spent in the upstream queue (1.13.9); the time is kept in seconds with millisecond resolution. Times of several responses are separated by commas and colons like addresses in the \$upstream_addr variable.",
    ngx_http_upstream_module,
    context = http,
)

val upstream_response_length = Variable(
    "upstream_response_length",
    "keeps the length of the response obtained from the upstream server (0.7.27); the length is kept in bytes. Lengths of several responses are separated by commas and colons like addresses in the \$upstream_addr variable.",
    ngx_http_upstream_module,
    context = http,
)

val upstream_response_time = Variable(
    "upstream_response_time",
    "keeps time spent on receiving the response from the upstream server; the time is kept in seconds with millisecond resolution. Times of several responses are separated by commas and colons like addresses in the \$upstream_addr variable.",
    ngx_http_upstream_module,
    context = http,
)

val upstream_status = Variable(
    "upstream_status",
    "keeps status code of the response obtained from the upstream server. Status codes of several responses are separated by commas and colons like addresses in the \$upstream_addr variable. If a server cannot be selected, the variable keeps the status code.",
    ngx_http_upstream_module,
    context = http,
)

val upstream_trailer_ = Variable(
    "upstream_trailer_",
    "keeps fields from the end of the response obtained from the upstream server (1.13.10).",
    ngx_http_upstream_module,
    parameterized = true,
    context = http,
)

val ngx_http_upstream_module_variables: List<Variable> = listOf(
    upstream_addr,
    upstream_bytes_received,
    upstream_bytes_sent,
    upstream_cache_status,
    upstream_connect_time,
    upstream_cookie_,
    upstream_header_time,
    upstream_http_,
    upstream_last_addr,
    upstream_last_server_name,
    upstream_queue_time,
    upstream_response_length,
    upstream_response_time,
    upstream_status,
    upstream_trailer_,
)
