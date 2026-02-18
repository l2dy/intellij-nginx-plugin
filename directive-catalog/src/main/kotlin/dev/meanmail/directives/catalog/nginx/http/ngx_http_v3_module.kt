package dev.meanmail.directives.catalog.nginx.http

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/http/ngx_http_v3_module.html

val ngx_http_v3_module = NginxModule(
    "ngx_http_v3_module",
    description = "Provides support for HTTP/3 protocol over QUIC"
)

val http3 = ToggleDirective(
    "http3",
    "Enables HTTP/3 protocol negotiation",
    enabled = true,
    context = listOf(http, server),
    module = ngx_http_v3_module
)

val http3Hq = ToggleDirective(
    "http3_hq",
    "Enables HTTP/0.9 protocol negotiation used in QUIC interoperability tests",
    enabled = false,
    context = listOf(http, server),
    module = ngx_http_v3_module
)

val http3MaxConcurrentStreams = Directive(
    name = "http3_max_concurrent_streams",
    description = "Sets the maximum number of concurrent HTTP/3 streams in a connection",
    context = listOf(http, server),
    module = ngx_http_v3_module
)

val http3StreamBufferSize = Directive(
    name = "http3_stream_buffer_size",
    description = "Sets the size of the buffer used for reading and writing of the QUIC streams",
    context = listOf(http, server),
    module = ngx_http_v3_module
)

val quicActiveConnectionIdLimit = Directive(
    name = "quic_active_connection_id_limit",
    description = "Sets the QUIC active_connection_id_limit transport parameter value",
    context = listOf(http, server),
    module = ngx_http_v3_module
)

val quicBpf = ToggleDirective(
    "quic_bpf",
    "Enables routing of QUIC packets using eBPF (Linux 5.7+)",
    enabled = false,
    context = listOf(main),
    module = ngx_http_v3_module
)

val quicGso = ToggleDirective(
    "quic_gso",
    "Enables sending in optimized batch mode using segmentation offloading",
    enabled = false,
    context = listOf(http, server),
    module = ngx_http_v3_module
)

val quicHostKey = Directive(
    name = "quic_host_key",
    description = "Sets a file with the secret key used to encrypt stateless reset and address validation tokens",
    context = listOf(http, server),
    module = ngx_http_v3_module
)

val quicRetry = ToggleDirective(
    "quic_retry",
    "Enables the QUIC Address Validation feature",
    enabled = false,
    context = listOf(http, server),
    module = ngx_http_v3_module
)
