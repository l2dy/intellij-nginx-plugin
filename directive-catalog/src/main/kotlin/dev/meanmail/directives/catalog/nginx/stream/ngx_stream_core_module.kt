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

    module = ngx_stream_core_module,
    context = listOf(main)
)

val streamServer = Directive(
    name = "server",
    description = "Defines a stream server block with configuration for handling stream connections",

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

val streamTcpNodelay = Directive(
    name = "tcp_nodelay",
    description = "Enables or disables the TCP_NODELAY socket option to reduce network latency",
    module = ngx_stream_core_module,
    parameters = listOf(
        DirectiveParameter(
            name = "state",
            valueType = ValueType.BOOLEAN,
            description = "TCP_NODELAY state",
            allowedValues = listOf("on", "off"),
            required = false
        )
    ),
    context = listOf(stream, streamServer)
)

val streamVariablesHashBucketSize = Directive(
    name = "variables_hash_bucket_size",
    description = "Sets the size of the variables hash bucket for efficient variable lookup",
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
