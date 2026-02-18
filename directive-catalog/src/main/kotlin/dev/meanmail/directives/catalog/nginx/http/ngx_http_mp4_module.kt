package dev.meanmail.directives.catalog.nginx.http

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/http/ngx_http_mp4_module.html

val ngx_http_mp4_module = NginxModule(
    name = "ngx_http_mp4_module",
    description = "Provides pseudo-streaming server-side support for MP4 files (.mp4, .m4v, .m4a)"
)

val mp4 = Directive(
    name = "mp4",
    description = "Turns on module processing in a surrounding location",

    context = listOf(location),
    module = ngx_http_mp4_module
)

val mp4BufferSize = Directive(
    name = "mp4_buffer_size",
    description = "Sets the initial buffer size for processing MP4 files",
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Initial buffer size for MP4 file processing",
            valueType = ValueType.SIZE,
            required = false,
            defaultValue = "512K"
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_mp4_module
)

val mp4MaxBufferSize = Directive(
    name = "mp4_max_buffer_size",
    description = "Sets the maximum buffer size for processing MP4 file metadata",
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Maximum buffer size for MP4 metadata processing",
            valueType = ValueType.SIZE,
            required = false,
            defaultValue = "10M"
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_mp4_module
)

val mp4StartKeyFrame = ToggleDirective(
    "mp4_start_key_frame",
    "Enables prepending the video with a key frame before the start point",
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_mp4_module
)
