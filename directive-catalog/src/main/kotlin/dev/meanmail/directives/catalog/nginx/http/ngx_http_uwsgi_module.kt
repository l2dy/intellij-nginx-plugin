package dev.meanmail.directives.catalog.nginx.http

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/http/ngx_http_uwsgi_module.html

val ngx_http_uwsgi_module = NginxModule(
    "ngx_http_uwsgi_module",
    description = "Provides support for proxying requests to uWSGI servers"
)

val uwsgiBind = Directive(
    name = "uwsgi_bind",
    description = "Makes outgoing connections to a uwsgi server originate from the specified local IP address",
    syntax = listOf("<b>uwsgi_bind</b> <i>address</i> [transparent] | off;"),
    parameters = listOf(
        DirectiveParameter(
            name = "address",
            description = "Local IP address with optional port or 'off'",
            valueType = ValueType.STRING,
            required = false
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiBuffering = ToggleDirective(
    "uwsgi_buffering",
    "Enables or disables buffering of responses from the uwsgi server",
    enabled = true,
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiBufferSize = Directive(
    name = "uwsgi_buffer_size",
    description = "Sets the size of the buffer used for reading the first part of the response",
    syntax = listOf("<b>uwsgi_buffer_size</b> <i>size</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Buffer size for the first part of the response",
            valueType = ValueType.SIZE,
            defaultValue = "4k|8k",
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiBuffers = Directive(
    name = "uwsgi_buffers",
    description = "Sets the number and size of buffers for reading a response from the uwsgi server",
    syntax = listOf("<b>uwsgi_buffers</b> <i>number</i> <i>size</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "number",
            description = "Number of buffers",
            valueType = ValueType.INTEGER,
            required = true,
            defaultValue = "8"
        ),
        DirectiveParameter(
            name = "size",
            description = "Size of each buffer",
            valueType = ValueType.SIZE,
            defaultValue = "4k|8k",
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiBusyBuffersSize = Directive(
    name = "uwsgi_busy_buffers_size",
    description = "Limits the total size of buffers that can be busy sending a response to the client",
    syntax = listOf("<b>uwsgi_busy_buffers_size</b> <i>size</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Maximum size of busy buffers",
            valueType = ValueType.SIZE,
            defaultValue = "8k|16k",
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiCache = Directive(
    name = "uwsgi_cache",
    description = "Defines a shared memory zone used for caching",
    syntax = listOf("<b>uwsgi_cache</b> <i>zone</i> | off;"),
    parameters = listOf(
        DirectiveParameter(
            name = "zone",
            description = "Name of the cache zone or 'off'",
            valueType = ValueType.STRING,
            defaultValue = "off",
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiCacheBackgroundUpdate = ToggleDirective(
    "uwsgi_cache_background_update",
    "Allows starting a background subrequest to update an expired cache item",
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiCacheBypass = Directive(
    name = "uwsgi_cache_bypass",
    description = "Defines conditions under which the response will not be taken from a cache",
    syntax = listOf("<b>uwsgi_cache_bypass</b> <i>string</i> ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "condition",
            description = "Conditions for bypassing cache",
            valueType = ValueType.STRING,
            required = false
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiCacheKey = Directive(
    name = "uwsgi_cache_key",
    description = "Defines a key for caching",
    syntax = listOf("<b>uwsgi_cache_key</b> <i>string</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "key",
            description = "Unique key for caching",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiCacheLock = ToggleDirective(
    "uwsgi_cache_lock",
    "Enables or disables cache lock for new cache elements",
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiCacheLockAge = Directive(
    name = "uwsgi_cache_lock_age",
    description = "Sets the time for additional request to the uwsgi server",
    syntax = listOf("<b>uwsgi_cache_lock_age</b> <i>time</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "time",
            description = "Maximum time for additional request",
            valueType = ValueType.TIME,
            defaultValue = "5s",
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiCacheLockTimeout = Directive(
    name = "uwsgi_cache_lock_timeout",
    description = "Sets a timeout for uwsgi_cache_lock",
    syntax = listOf("<b>uwsgi_cache_lock_timeout</b> <i>time</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "time",
            description = "Timeout for cache lock",
            valueType = ValueType.TIME,
            defaultValue = "5s",
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiCacheMaxRangeOffset = Directive(
    name = "uwsgi_cache_max_range_offset",
    description = "Sets an offset in bytes for byte-range requests",
    syntax = listOf("<b>uwsgi_cache_max_range_offset</b> <i>number</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "number",
            description = "Maximum offset for byte-range requests",
            valueType = ValueType.INTEGER,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiCacheMethods = Directive(
    name = "uwsgi_cache_methods",
    description = "Specifies HTTP methods for which responses will be cached",
    syntax = listOf("<b>uwsgi_cache_methods</b> GET | HEAD | POST ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "method",
            description = "HTTP methods to cache (GET, HEAD, POST)",
            valueType = ValueType.STRING,
            defaultValue = "GET HEAD",
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiCacheMinUses = Directive(
    name = "uwsgi_cache_min_uses",
    description = "Sets the number of requests after which the response will be cached",
    syntax = listOf("<b>uwsgi_cache_min_uses</b> <i>number</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "number",
            description = "Minimum number of requests to cache",
            valueType = ValueType.INTEGER,
            defaultValue = "1",
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiCachePath = Directive(
    name = "uwsgi_cache_path",
    description = "Sets the path and other parameters of a cache",
    syntax = listOf("<b>uwsgi_cache_path</b> <i>path</i> [levels=<i>levels</i>] [use_temp_path=on|off] keys_zone=<i>name</i>:<i>size</i> [inactive=<i>time</i>] [max_size=<i>size</i>] [min_free=<i>size</i>] [manager_files=<i>number</i>] [manager_sleep=<i>time</i>] [manager_threshold=<i>time</i>] [loader_files=<i>number</i>] [loader_sleep=<i>time</i>] [loader_threshold=<i>time</i>] [purger=on|off] [purger_files=<i>number</i>] [purger_sleep=<i>time</i>] [purger_threshold=<i>time</i>];"),
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            description = "Path to the cache directory",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "keys_zone",
            description = "Name and size of shared memory zone for cache keys",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http),
    module = ngx_http_uwsgi_module
)

val uwsgiCachePurge = Directive(
    name = "uwsgi_cache_purge",
    description = "Allows purging specific cache entries",
    syntax = listOf("<b>uwsgi_cache_purge</b> string ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "key",
            description = "Cache key to purge",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiCacheRevalidate = ToggleDirective(
    "uwsgi_cache_revalidate",
    "Allows revalidating expired cache items",
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiCacheUseStale = Directive(
    name = "uwsgi_cache_use_stale",
    description = "Allows using stale cached responses in specific situations",
    syntax = listOf("<b>uwsgi_cache_use_stale</b> error | timeout | invalid_header | updating | http_500 | http_503 | http_403 | http_404 | http_429 | off ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "type",
            description = "Conditions for using stale cache",
            valueType = ValueType.STRING,
            required = false
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiCacheValid = Directive(
    name = "uwsgi_cache_valid",
    description = "Sets caching time for different response codes",
    syntax = listOf("<b>uwsgi_cache_valid</b> [<i>code</i> ...] <i>time</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "code",
            description = "HTTP response code",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "time",
            description = "Caching duration",
            valueType = ValueType.TIME,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiConnectTimeout = Directive(
    name = "uwsgi_connect_timeout",
    description = "Sets a timeout for establishing a connection with the uwsgi server",
    syntax = listOf("<b>uwsgi_connect_timeout</b> <i>time</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "time",
            description = "Connection timeout",
            valueType = ValueType.TIME,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiForceRanges = ToggleDirective(
    "uwsgi_force_ranges",
    "Enables byte-range support for cached responses",
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiHideHeader = Directive(
    name = "uwsgi_hide_header",
    description = "Prevents passing specified headers from the uwsgi server response",
    syntax = listOf("<b>uwsgi_hide_header</b> <i>field</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "header",
            description = "Header to hide",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiIgnoreClientAbort = ToggleDirective(
    "uwsgi_ignore_client_abort",
    "Determines whether to close the connection with the uwsgi server when a client closes the connection",
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiIgnoreHeaders = Directive(
    name = "uwsgi_ignore_headers",
    description = "Disables processing of specified headers from the uwsgi server response",
    syntax = listOf("<b>uwsgi_ignore_headers</b> <i>field</i> ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "header",
            description = "Headers to ignore",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiIntcpBuffer = ToggleDirective(
    "uwsgi_intercept_errors",
    "Determines whether to intercept HTTP errors from the uwsgi server",
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiInterceptErrors = ToggleDirective(
    "uwsgi_intercept_errors",
    "Determines whether to intercept HTTP error responses from the uwsgi server",
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiKeepConn = ToggleDirective(
    "uwsgi_keep_conn",
    "Keeps the connection with the uwsgi server open",
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiLimitRate = Directive(
    name = "uwsgi_limit_rate",
    description = "Limits the transfer rate of a response from the uwsgi server to the client",
    syntax = listOf("<b>uwsgi_limit_rate</b> <i>rate</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "rate",
            description = "Transfer rate limit in bytes per second",
            valueType = ValueType.SIZE,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiMaxTempFileSize = Directive(
    name = "uwsgi_max_temp_file_size",
    description = "Sets the maximum size of temporary files used for storing uwsgi server responses",
    syntax = listOf("<b>uwsgi_max_temp_file_size</b> <i>size</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Maximum size of temporary files",
            valueType = ValueType.SIZE,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiModifier1 = Directive(
    name = "uwsgi_modifier1",
    description = "Sets the first modifier byte for the uWSGI protocol request",
    syntax = listOf("<b>uwsgi_modifier1</b> <i>number</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "value",
            description = "First modifier byte value (0-255)",
            valueType = ValueType.INTEGER,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiModifier2 = Directive(
    name = "uwsgi_modifier2",
    description = "Sets the second modifier byte for the uWSGI protocol request",
    syntax = listOf("<b>uwsgi_modifier2</b> <i>number</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "value",
            description = "Second modifier byte value (0-255)",
            valueType = ValueType.INTEGER,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiNextUpstream = Directive(
    name = "uwsgi_next_upstream",
    description = "Specifies conditions for passing a request to the next uwsgi server if the current server fails",
    syntax = listOf("<b>uwsgi_next_upstream</b> error | timeout | denied | invalid_header | http_500 | http_503 | http_403 | http_404 | http_429 | non_idempotent | off ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "condition",
            description = "Conditions for trying the next server (error, timeout, invalid_header, http_500, etc.)",
            valueType = ValueType.STRING,
            required = false
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiNextUpstreamTimeout = Directive(
    name = "uwsgi_next_upstream_timeout",
    description = "Sets the maximum time for passing a request to the next server",
    syntax = listOf("<b>uwsgi_next_upstream_timeout</b> <i>time</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "time",
            description = "Maximum time for passing to the next server",
            valueType = ValueType.TIME,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiNextUpstreamTries = Directive(
    name = "uwsgi_next_upstream_tries",
    description = "Sets the maximum number of possible tries for passing a request to the next server",
    syntax = listOf("<b>uwsgi_next_upstream_tries</b> <i>number</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "number",
            description = "Maximum number of server tries",
            valueType = ValueType.INTEGER,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiNoCache = Directive(
    name = "uwsgi_no_cache",
    description = "Defines conditions for not caching a response",
    syntax = listOf("<b>uwsgi_no_cache</b> <i>string</i> ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "condition",
            description = "Condition for not caching",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiParam = Directive(
    name = "uwsgi_param",
    description = "Sets a parameter to be passed to the uwsgi server",
    syntax = listOf("<b>uwsgi_param</b> <i>parameter</i> <i>value</i> [if_not_empty];"),
    parameters = listOf(
        DirectiveParameter(
            name = "parameter",
            description = "Name of the parameter",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "value",
            description = "Value of the parameter",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiPass = Directive(
    name = "uwsgi_pass",
    description = "Passes requests to a uwsgi server",
    syntax = listOf("<b>uwsgi_pass</b> [<i>protocol</i>://]<i>address</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "server",
            description = "Address of the uwsgi server",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(location, locationIf),
    module = ngx_http_uwsgi_module
)

val uwsgiPassHeader = Directive(
    name = "uwsgi_pass_header",
    description = "Allows passing specified headers from the uwsgi server response",
    syntax = listOf("<b>uwsgi_pass_header</b> <i>field</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "header",
            description = "Header to pass",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiPassRequestBody = ToggleDirective(
    "uwsgi_pass_request_body",
    "Determines whether to pass the client request body to the uwsgi server",
    enabled = true,
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiPassRequestHeaders = ToggleDirective(
    "uwsgi_pass_request_headers",
    "Determines whether to pass client request headers to the uwsgi server",
    enabled = true,
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiReadTimeout = Directive(
    name = "uwsgi_read_timeout",
    description = "Sets a timeout for reading a response from the uwsgi server",
    syntax = listOf("<b>uwsgi_read_timeout</b> <i>time</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "time",
            description = "Timeout for reading response",
            valueType = ValueType.TIME,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiRequestBuffering = ToggleDirective(
    "uwsgi_request_buffering",
    "Enables or disables buffering of client request body",
    enabled = true,
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiSendTimeout = Directive(
    name = "uwsgi_send_timeout",
    description = "Sets a timeout for transmitting a request to the uwsgi server",
    syntax = listOf("<b>uwsgi_send_timeout</b> <i>time</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "time",
            description = "Timeout for sending request",
            valueType = ValueType.TIME,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiSocketKeepAlive = ToggleDirective(
    "uwsgi_socket_keepalive",
    "Enables or disables the TCP keepalive behavior for connections to uwsgi servers",
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiSslCertificate = Directive(
    name = "uwsgi_ssl_certificate",
    description = "Specifies the path to the SSL certificate for secure connections to uwsgi servers",
    syntax = listOf("<b>uwsgi_ssl_certificate</b> <i>file</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            description = "Path to the SSL certificate file",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiSslCertificateCache = Directive(
    name = "uwsgi_ssl_certificate_cache",
    description = "Enables caching of SSL certificates for secure connections to uwsgi servers",
    syntax = listOf(
        "<b>uwsgi_ssl_certificate_cache</b> off;",
        "<b>uwsgi_ssl_certificate_cache</b> max=<i>N</i> [inactive=<i>time</i>] [valid=<i>time</i>];"
    ),
    parameters = listOf(
        DirectiveParameter(
            name = "enabled",
            description = "Enable or disable SSL certificate caching",
            valueType = ValueType.BOOLEAN,
            defaultValue = "off",
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiSslCertificateKey = Directive(
    name = "uwsgi_ssl_certificate_key",
    description = "Specifies the path to the private key for the SSL certificate",
    syntax = listOf("<b>uwsgi_ssl_certificate_key</b> <i>file</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            description = "Path to the SSL certificate private key file",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiSslCiphers = Directive(
    name = "uwsgi_ssl_ciphers",
    description = "Specifies the ciphers for secure connections to uwsgi servers",
    syntax = listOf("<b>uwsgi_ssl_ciphers</b> <i>ciphers</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "ciphers",
            description = "List of ciphers to use",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiSslConfCommand = Directive(
    name = "uwsgi_ssl_conf_command",
    description = "Sets custom OpenSSL configuration commands for secure connections to uwsgi servers",
    syntax = listOf("<b>uwsgi_ssl_conf_command</b> <i>name</i> <i>value</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "command",
            description = "OpenSSL configuration command name",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "value",
            description = "Value for the OpenSSL configuration command",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiSslCrl = Directive(
    name = "uwsgi_ssl_crl",
    description = "Specifies a file with revoked certificates (Certificate Revocation List) for verifying uwsgi server certificates",
    syntax = listOf("<b>uwsgi_ssl_crl</b> <i>file</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            description = "Path to the Certificate Revocation List file",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiSslName = Directive(
    name = "uwsgi_ssl_name",
    description = "Sets the server name for SSL/TLS server name indication (SNI) extension",
    syntax = listOf("<b>uwsgi_ssl_name</b> <i>name</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "name",
            description = "Server name for SNI",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiSslPasswordFile = Directive(
    name = "uwsgi_ssl_password_file",
    description = "Specifies a file with passwords for SSL private keys",
    syntax = listOf("<b>uwsgi_ssl_password_file</b> <i>file</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            description = "Path to the password file",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiSslProtocols = Directive(
    name = "uwsgi_ssl_protocols",
    description = "Enables protocols for secure connections to uwsgi servers",
    syntax = listOf("<b>uwsgi_ssl_protocols</b> [SSLv2] [SSLv3] [TLSv1] [TLSv1.1] [TLSv1.2] [TLSv1.3];"),
    parameters = listOf(
        DirectiveParameter(
            name = "protocols",
            description = "SSL/TLS protocols to enable",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiSslServerName = ToggleDirective(
    "uwsgi_ssl_server_name",
    "Enables or disables passing of the server name through SNI",
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiSslSessionReuse = ToggleDirective(
    "uwsgi_ssl_session_reuse",
    "Enables or disables reuse of SSL sessions",
    enabled = true,
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiSslTrustedCertificate = Directive(
    name = "uwsgi_ssl_trusted_certificate",
    description = "Specifies a file with trusted CA certificates for secure connections to uwsgi servers",
    syntax = listOf("<b>uwsgi_ssl_trusted_certificate</b> <i>file</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            description = "Path to the trusted CA certificates file",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiSslVerify = ToggleDirective(
    "uwsgi_ssl_verify",
    "Enables or disables verification of the uwsgi server certificate",
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiSslVerifyDepth = Directive(
    name = "uwsgi_ssl_verify_depth",
    description = "Sets the maximum depth of the uwsgi server certificate verification chain",
    syntax = listOf("<b>uwsgi_ssl_verify_depth</b> <i>number</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "depth",
            description = "Maximum verification chain depth",
            valueType = ValueType.INTEGER,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiStore = Directive(
    name = "uwsgi_store",
    description = "Enables storing responses from uwsgi servers to files",
    syntax = listOf("<b>uwsgi_store</b> on | off | <i>string</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            description = "Path to store the uwsgi server response",
            valueType = ValueType.STRING,
            required = false
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiStoreAccess = Directive(
    name = "uwsgi_store_access",
    description = "Sets access permissions for files created with uwsgi_store",
    syntax = listOf("<b>uwsgi_store_access</b> <i>users</i>:<i>permissions</i> ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "permissions",
            description = "Access permissions for stored files",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiStoreBuffering = ToggleDirective(
    "uwsgi_store_buffering",
    "Enables or disables buffering of responses from uwsgi servers before storing",
    enabled = true,
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiStoreBufferSize = Directive(
    name = "uwsgi_store_buffer_size",
    description = "Sets the size of the buffer used for storing uwsgi server responses",
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Buffer size for storing responses",
            valueType = ValueType.SIZE,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiStoreMaxSize = Directive(
    name = "uwsgi_store_max_size",
    description = "Sets the maximum size of files created with uwsgi_store",
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Maximum file size for stored responses",
            valueType = ValueType.SIZE,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiStoreNoUpdateError = ToggleDirective(
    "uwsgi_store_no_update_error",
    "Determines whether to return an error if the file cannot be updated",
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiTempFileWriteSize = Directive(
    name = "uwsgi_temp_file_write_size",
    description = "Sets the size of temporary files used for storing responses from uwsgi servers",
    syntax = listOf("<b>uwsgi_temp_file_write_size</b> <i>size</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Size of temporary files",
            valueType = ValueType.SIZE,
            required = true,
            defaultValue = "8k|16k"
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)

val uwsgiTempPath = Directive(
    name = "uwsgi_temp_path",
    description = "Defines a directory for storing temporary files with responses from uwsgi servers",
    syntax = listOf("<b>uwsgi_temp_path</b> <i>path</i> [<i>level1</i> [<i>level2</i> [<i>level3</i>]]];"),
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            description = "Path to the temporary files directory",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_uwsgi_module
)
