package dev.meanmail.directives.catalog.nginx.stream

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/stream/ngx_stream_core_module.html

val ngx_stream_core_module = NginxModule(
    name = "ngx_stream_core_module",
    description = "Provides core functionality for handling stream connections"
)

val stream = Directive(
    "stream",
    description = "Defines a stream context for handling stream-level configurations",
    syntax = listOf("<b>stream</b> { ... }"),

    module = ngx_stream_core_module,
    context = listOf(main)
)

val streamServer = Directive(
    name = "server",
    description = "Defines a stream server block with configuration for handling stream connections",
    syntax = listOf("<b>server</b> { ... }"),

    module = ngx_stream_core_module,
    parameters = listOf(
        DirectiveParameter(
            name = "address",
            valueType = ValueType.STRING,
            description = "IP address or domain name of the server",
        ),
        DirectiveParameter(
            name = "port",
            valueType = ValueType.NUMBER,
            description = "Port of the server",
            required = false,
        ),
        DirectiveParameter(
            name = "weight",
            valueType = ValueType.NUMBER,
            description = "Weight for server in load balancing",
            required = false,
        ),
        DirectiveParameter(
            name = "max_conns",
            valueType = ValueType.NUMBER,
            description = "Maximum number of concurrent connections",
            required = false,
        ),
        DirectiveParameter(
            name = "max_fails",
            valueType = ValueType.NUMBER,
            description = "Number of failed attempts before marking server unavailable",
            required = false,
        ),
        DirectiveParameter(
            name = "fail_timeout",
            valueType = ValueType.TIME,
            description = "Duration to consider server unavailable after max_fails",
            required = false,
        )
    ),
    context = listOf(stream)
)

val streamListen = Directive(
    name = "listen",
    description = "Configures the address and port or UNIX-domain socket for the server to accept stream connections",
    syntax = listOf("<b>listen</b> <i>address</i>:<i>port</i> [default_server] [ssl] [udp] [proxy_protocol] [setfib=<i>number</i>] [fastopen=<i>number</i>] [backlog=<i>number</i>] [rcvbuf=<i>size</i>] [sndbuf=<i>size</i>] [accept_filter=<i>filter</i>] [deferred] [bind] [ipv6only=on|off] [reuseport] [so_keepalive=on|off|[<i>keepidle</i>]:[<i>keepintvl</i>]:[<i>keepcnt</i>]];"),
    module = ngx_stream_core_module,
    parameters = listOf(
        DirectiveParameter(
            name = "address",
            valueType = ValueType.STRING,
            description = "IP address or UNIX-domain socket path",
        ),
        DirectiveParameter(
            name = "port",
            valueType = ValueType.NUMBER,
            description = "Port number for TCP connections",
        ),
        DirectiveParameter(
            name = "options",
            valueType = ValueType.STRING,
            description = "Additional socket configuration options",
            required = false,
        )
    ),
    context = listOf(streamServer)
)

val streamPrereadBufferSize = Directive(
    name = "preread_buffer_size",
    description = "Sets the buffer size for reading initial data from the proxied server before processing",
    syntax = listOf("<b>preread_buffer_size</b> <i>size</i>;"),
    module = ngx_stream_core_module,
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            valueType = ValueType.SIZE,
            description = "Buffer size in bytes or with size units",
        )
    ),
    context = listOf(stream, streamServer)
)

val streamPrereadTimeout = Directive(
    name = "preread_timeout",
    description = "Sets the timeout for reading initial data from the proxied server",
    syntax = listOf("<b>preread_timeout</b> <i>timeout</i>;"),
    module = ngx_stream_core_module,
    parameters = listOf(
        DirectiveParameter(
            name = "time",
            valueType = ValueType.TIME,
            description = "Timeout duration for preread operation",
        )
    ),
    context = listOf(stream, streamServer)
)

val streamProxyProtocolTimeout = Directive(
    name = "proxy_protocol_timeout",
    description = "Sets the timeout for reading the PROXY protocol header from the proxied server",
    syntax = listOf("<b>proxy_protocol_timeout</b> <i>timeout</i>;"),
    module = ngx_stream_core_module,
    parameters = listOf(
        DirectiveParameter(
            name = "time",
            valueType = ValueType.TIME,
            description = "Timeout duration for PROXY protocol header reading",
        )
    ),
    context = listOf(stream, streamServer)
)

val streamResolver = Directive(
    name = "resolver",
    description = "Configures DNS servers for resolving hostnames to IP addresses",
    syntax = listOf("<b>resolver</b> <i>address</i> ... [valid=<i>time</i>] [ipv4=on|off] [ipv6=on|off] [status_zone=<i>zone</i>];"),
    module = ngx_stream_core_module,
    parameters = listOf(
        DirectiveParameter(
            name = "address",
            valueType = ValueType.STRING,
            description = "IP address of DNS server",
        ),
        DirectiveParameter(
            name = "valid",
            valueType = ValueType.TIME,
            description = "Caching time for DNS records",
            required = false,
        ),
        DirectiveParameter(
            name = "ipv6",
            valueType = ValueType.BOOLEAN,
            description = "Enable IPv6 resolution",
            required = false,
        )
    ),
    context = listOf(stream, streamServer)
)

val streamResolverTimeout = Directive(
    name = "resolver_timeout",
    description = "Sets the timeout for resolving hostnames to IP addresses",
    syntax = listOf("<b>resolver_timeout</b> <i>time</i>;"),
    module = ngx_stream_core_module,
    parameters = listOf(
        DirectiveParameter(
            name = "time",
            valueType = ValueType.TIME,
            description = "Timeout duration for DNS resolution",
        )
    ),
    context = listOf(stream, streamServer)
)

val streamTcpNodelay = ToggleDirective(
    "tcp_nodelay",
    "Enables or disables the TCP_NODELAY socket option to reduce network latency",
    enabled = true,
    module = ngx_stream_core_module,
    context = listOf(stream, streamServer)
)

val streamVariablesHashBucketSize = Directive(
    name = "variables_hash_bucket_size",
    description = "Sets the size of the variables hash bucket for efficient variable lookup",
    syntax = listOf("<b>variables_hash_bucket_size</b> <i>size</i>;"),
    module = ngx_stream_core_module,
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            valueType = ValueType.SIZE,
            description = "Size of variables hash bucket",
            required = false
        )
    ),
    context = listOf(stream)
)

val streamVariablesHashMaxSize = Directive(
    name = "variables_hash_max_size",
    description = "Sets the maximum size of the variables hash table for efficient variable lookup",
    syntax = listOf("<b>variables_hash_max_size</b> <i>size</i>;"),
    module = ngx_stream_core_module,
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            valueType = ValueType.NUMBER,
            description = "Maximum size of variables hash table",
            required = false
        )
    ),
    context = listOf(stream)
)
