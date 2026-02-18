package dev.meanmail.directives.catalog.nginx.http

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/http/ngx_http_scgi_module.html

val ngx_http_scgi_module = NginxModule(
    name = "ngx_http_scgi_module",
    description = """
        Allows passing requests to an SCGI server.
        
        Features:
        - Proxies requests to SCGI servers
        - Supports response buffering and caching
        - Provides connection management and failover
        - Allows customizing request parameters and headers
    """.trimIndent()
)

val scgiBind = Directive(
    name = "scgi_bind",
    description = "Specifies the local IP address and optional port for outgoing connections to the SCGI server",
    syntax = listOf("<b>scgi_bind</b> <i>address</i> [transparent] | off;"),
    parameters = listOf(
        DirectiveParameter(
            name = "address",
            description = "Local IP address to bind SCGI connections, can include variables",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiBufferSize = Directive(
    name = "scgi_buffer_size",
    description = "Sets the buffer size for reading the first part of the response from the SCGI server",
    syntax = listOf("<b>scgi_buffer_size</b> <i>size</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Size of the buffer for the initial server response (default: 4k or 8k)",
            valueType = ValueType.SIZE,
            required = true,
            defaultValue = "4k|8k"
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiBuffering = ToggleDirective(
    "scgi_buffering",
    """
        Enables or disables buffering of responses from the SCGI server.
        
        When buffering is enabled, nginx receives response as soon as possible, saving it 
        into buffers set by scgi_buffer_size and scgi_buffers directives.
        
        When disabled, response is passed to client synchronously, as it is received.
    """.trimIndent(),
    enabled = true,
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiBuffers = Directive(
    name = "scgi_buffers",
    description = "Configures the number and size of buffers for reading SCGI server responses",
    syntax = listOf("<b>scgi_buffers</b> <i>number</i> <i>size</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "number",
            description = "Number of buffers for response",
            valueType = ValueType.NUMBER,
            required = true,
            defaultValue = "8"
        ),
        DirectiveParameter(
            name = "size",
            description = "Size of each buffer (default: 4k or 8k)",
            valueType = ValueType.SIZE,
            required = true,
            defaultValue = "4k|8k"
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiBusyBuffersSize = Directive(
    name = "scgi_busy_buffers_size",
    description = "Limits the total size of buffers that can be busy sending a response while the full response is not yet read",
    syntax = listOf("<b>scgi_busy_buffers_size</b> <i>size</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Maximum size of busy buffers",
            valueType = ValueType.SIZE,
            required = true,
            defaultValue = "8k|16k"
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiCache = Directive(
    name = "scgi_cache",
    description = """
        Defines a shared memory zone used for caching. 
        Same zone can be used in several places.
        The 'off' parameter disables caching inherited from previous level.
    """.trimIndent(),
    syntax = listOf("<b>scgi_cache</b> <i>zone</i> | off;"),
    parameters = listOf(
        DirectiveParameter(
            name = "zone",
            description = "Name of shared memory zone for caching, or 'off' to disable caching",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiCacheBackgroundUpdate = ToggleDirective(
    "scgi_cache_background_update",
    """
        Allows starting a background subrequest to update an expired cache item,
        while a stale cached response is returned to the client.
        
        Note that it is necessary to allow usage of a stale cached response
        when it is being updated.
    """.trimIndent(),
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiCacheBypass = Directive(
    name = "scgi_cache_bypass",
    description = "Defines conditions for bypassing the cache and directly requesting from the SCGI server",
    syntax = listOf("<b>scgi_cache_bypass</b> <i>string</i> ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "condition",
            description = "Condition for skipping cache, can use variables",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiCacheKey = Directive(
    name = "scgi_cache_key",
    description = "Defines the key for caching SCGI server responses",
    syntax = listOf("<b>scgi_cache_key</b> <i>string</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "key",
            description = "Cache key, can include variables",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiCacheLock = ToggleDirective(
    "scgi_cache_lock",
    """
        When enabled, only one request at a time will be allowed to populate
        a new cache element identified according to the scgi_cache_key directive.
        
        Other requests of the same cache element will either wait for:
        - Response to appear in the cache
        - Cache lock to be released (up to the scgi_cache_lock_timeout)
    """.trimIndent(),
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiCacheLockAge = Directive(
    name = "scgi_cache_lock_age",
    description = "Sets the maximum age of a cache item to be considered for locking",
    syntax = listOf("<b>scgi_cache_lock_age</b> <i>time</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "age",
            description = "Maximum age of cache item",
            valueType = ValueType.TIME,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiCacheLockTimeout = Directive(
    name = "scgi_cache_lock_timeout",
    description = "Sets the timeout for locking cache items",
    syntax = listOf("<b>scgi_cache_lock_timeout</b> <i>time</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "timeout",
            description = "Lock timeout",
            valueType = ValueType.TIME,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiCacheMaxRangeOffset = Directive(
    name = "scgi_cache_max_range_offset",
    description = "Sets the maximum range offset for caching SCGI server responses",
    syntax = listOf("<b>scgi_cache_max_range_offset</b> <i>number</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "offset",
            description = "Maximum range offset",
            valueType = ValueType.OFFSET,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiCacheMethods = Directive(
    name = "scgi_cache_methods",
    description = "Defines the HTTP methods for which caching is enabled",
    syntax = listOf("<b>scgi_cache_methods</b> GET | HEAD | POST ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "methods",
            description = "HTTP methods for caching",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiCacheMinUses = Directive(
    name = "scgi_cache_min_uses",
    description = "Sets the minimum number of uses for caching SCGI server responses",
    syntax = listOf("<b>scgi_cache_min_uses</b> <i>number</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "uses",
            description = "Minimum number of uses",
            valueType = ValueType.NUMBER,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiCachePath = Directive(
    name = "scgi_cache_path",
    description = "Defines the path for caching SCGI server responses",
    syntax = listOf("<b>scgi_cache_path</b> <i>path</i> [levels=<i>levels</i>] [use_temp_path=on|off] keys_zone=<i>name</i>:<i>size</i> [inactive=<i>time</i>] [max_size=<i>size</i>] [min_free=<i>size</i>] [manager_files=<i>number</i>] [manager_sleep=<i>time</i>] [manager_threshold=<i>time</i>] [loader_files=<i>number</i>] [loader_sleep=<i>time</i>] [loader_threshold=<i>time</i>] [purger=on|off] [purger_files=<i>number</i>] [purger_sleep=<i>time</i>] [purger_threshold=<i>time</i>];"),
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            description = "Cache path, can include variables",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http),
    module = ngx_http_scgi_module
)

val scgiCachePurge = Directive(
    name = "scgi_cache_purge",
    description = "Defines the conditions under which cached responses are purged",
    syntax = listOf("<b>scgi_cache_purge</b> string ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "condition",
            description = "Condition for purging cache, can use variables",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiCacheRevalidate = ToggleDirective(
    "scgi_cache_revalidate",
    """
        Enables revalidation of expired cache items using conditional requests with 
        the "If-Modified-Since" and "If-None-Match" header fields.
    """.trimIndent(),
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiCacheUseStale = Directive(
    name = "scgi_cache_use_stale",
    description = "Defines the conditions under which stale cached responses are used",
    syntax = listOf("<b>scgi_cache_use_stale</b> error | timeout | invalid_header | updating | http_500 | http_503 | http_403 | http_404 | http_429 | off ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "condition",
            description = "Condition for using stale cache, can use variables",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiCacheValid = Directive(
    name = "scgi_cache_valid",
    description = "Defines the validity period for cached SCGI server responses",
    syntax = listOf("<b>scgi_cache_valid</b> [<i>code</i> ...] <i>time</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "validity",
            description = "Validity period",
            valueType = ValueType.TIME,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiConnectTimeout = Directive(
    name = "scgi_connect_timeout",
    description = "Sets the timeout for connecting to the SCGI server",
    syntax = listOf("<b>scgi_connect_timeout</b> <i>time</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "timeout",
            description = "Connect timeout",
            valueType = ValueType.TIME,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiForceRanges = ToggleDirective(
    "scgi_force_ranges",
    """
        Enables byte-range support for both cached and uncached responses from the 
        SCGI server regardless of the "Accept-Ranges" field in these responses.
    """.trimIndent(),
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiHideHeader = Directive(
    name = "scgi_hide_header",
    description = "Hides the specified header from the SCGI server response",
    syntax = listOf("<b>scgi_hide_header</b> <i>field</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "header",
            description = "Header to hide",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiIgnoreClientAbort = ToggleDirective(
    "scgi_ignore_client_abort",
    """
        Determines whether the connection with an SCGI server should be closed 
        when a client closes the connection without waiting for a response.
    """.trimIndent(),
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiIgnoreHeaders = Directive(
    name = "scgi_ignore_headers",
    description = "Ignores the specified headers from the SCGI server response",
    syntax = listOf("<b>scgi_ignore_headers</b> <i>field</i> ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "headers",
            description = "Headers to ignore",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiInterceptErrors = ToggleDirective(
    "scgi_intercept_errors",
    """
        Determines whether SCGI server responses with codes greater than or equal 
        to 300 should be passed to a client or be intercepted and redirected to 
        nginx for processing with the error_page directive.
    """.trimIndent(),
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiLimitRate = Directive(
    name = "scgi_limit_rate",
    description = "Sets the rate limit for the SCGI server response",
    syntax = listOf("<b>scgi_limit_rate</b> <i>rate</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "rate",
            description = "Rate limit",
            valueType = ValueType.RATE,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiMaxTempFileSize = Directive(
    name = "scgi_max_temp_file_size",
    description = "Sets the maximum size of temporary files for the SCGI server response",
    syntax = listOf("<b>scgi_max_temp_file_size</b> <i>size</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Maximum size of temporary files",
            valueType = ValueType.SIZE,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiNextUpstream = Directive(
    name = "scgi_next_upstream",
    description = "Defines the conditions under which the request is passed to the next upstream server",
    syntax = listOf("<b>scgi_next_upstream</b> error | timeout | denied | invalid_header | http_500 | http_503 | http_403 | http_404 | http_429 | non_idempotent | off ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "condition",
            description = "Condition for passing to next upstream server, can use variables",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiNextUpstreamTimeout = Directive(
    name = "scgi_next_upstream_timeout",
    description = "Sets the timeout for passing the request to the next upstream server",
    syntax = listOf("<b>scgi_next_upstream_timeout</b> <i>time</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "timeout",
            description = "Timeout for passing to next upstream server",
            valueType = ValueType.TIME,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiNextUpstreamTries = Directive(
    name = "scgi_next_upstream_tries",
    description = "Sets the number of tries for passing the request to the next upstream server",
    syntax = listOf("<b>scgi_next_upstream_tries</b> <i>number</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "tries",
            description = "Number of tries",
            valueType = ValueType.NUMBER,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiNoCache = Directive(
    name = "scgi_no_cache",
    description = "Disables caching of SCGI server responses",
    syntax = listOf("<b>scgi_no_cache</b> <i>string</i> ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "condition",
            description = "Condition for disabling cache, can use variables",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiParam = Directive(
    name = "scgi_param",
    description = """
        Sets a parameter that should be passed to the SCGI server.
        The value can contain text, variables, and their combinations.
        
        These parameters are passed as SCGI protocol headers.
    """.trimIndent(),
    syntax = listOf("<b>scgi_param</b> <i>parameter</i> <i>value</i> [if_not_empty];"),
    parameters = listOf(
        DirectiveParameter(
            name = "parameter",
            description = "Parameter name to pass to SCGI server",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "value",
            description = "Parameter value, can contain text and variables",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "if_not_empty",
            description = "If specified, parameter will be passed only if its value is not empty",
            valueType = ValueType.BOOLEAN,
            required = false
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiPass = Directive(
    name = "scgi_pass",
    description = """
        Sets the address of an SCGI server. The address can be specified as a domain name 
        or IP address, and a port:
        
        - scgi_pass localhost:9000;
        - scgi_pass unix:/tmp/scgi.socket;
        
        Can also specify a server group defined in the upstream block.
    """.trimIndent(),
    syntax = listOf("<b>scgi_pass</b> <i>address</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "address",
            description = "Address of SCGI server (domain:port, IP:port, unix:path, or upstream name)",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(location, locationIf),
    module = ngx_http_scgi_module
)

val scgiPassHeader = Directive(
    name = "scgi_pass_header",
    description = "Passes the specified header from the SCGI server response",
    syntax = listOf("<b>scgi_pass_header</b> <i>field</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "header",
            description = "Header to pass",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiPassRequestBody = ToggleDirective(
    "scgi_pass_request_body",
    "Enables passing of the request body to the SCGI server",
    enabled = true,
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiPassRequestHeaders = ToggleDirective(
    "scgi_pass_request_headers",
    "Enables passing of request headers to the SCGI server",
    enabled = true,
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiReadTimeout = Directive(
    name = "scgi_read_timeout",
    description = "Sets the timeout for reading the SCGI server response",
    syntax = listOf("<b>scgi_read_timeout</b> <i>time</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "timeout",
            description = "Read timeout",
            valueType = ValueType.TIME,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiRequestBuffering = ToggleDirective(
    "scgi_request_buffering",
    """
        Enables or disables buffering of a client request body.
        
        When enabled, the entire request body is read from the client before sending 
        the request to an SCGI server.
        
        When disabled, the request body is sent to the SCGI server immediately as 
        it is received.
    """.trimIndent(),
    enabled = true,
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiSendTimeout = Directive(
    name = "scgi_send_timeout",
    description = "Sets the timeout for sending the request to the SCGI server",
    syntax = listOf("<b>scgi_send_timeout</b> <i>time</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "timeout",
            description = "Send timeout",
            valueType = ValueType.TIME,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiSocketKeepalive = ToggleDirective(
    "scgi_socket_keepalive",
    """
        Configures the "TCP keepalive" behavior for outgoing connections to an 
        SCGI server. When enabled, the SO_KEEPALIVE socket option is turned on 
        for the socket.
    """.trimIndent(),
    enabled = false,
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiStore = Directive(
    name = "scgi_store",
    description = "Enables saving of the SCGI server response to a file",
    syntax = listOf("<b>scgi_store</b> on | off | <i>string</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            description = "File path where the response will be stored, can include variables",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiStoreAccess = Directive(
    name = "scgi_store_access",
    description = "Sets the access permissions for files created when saving SCGI server responses",
    syntax = listOf("<b>scgi_store_access</b> <i>users</i>:<i>permissions</i> ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "permissions",
            description = "Access permissions in the format user:group, e.g., 'user=rw group=r'",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiTempFileWriteSize = Directive(
    name = "scgi_temp_file_write_size",
    description = "Sets the size of temporary files when buffering responses from the SCGI server",
    syntax = listOf("<b>scgi_temp_file_write_size</b> <i>size</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Maximum size of temporary files used for buffering",
            valueType = ValueType.SIZE,
            required = true,
            defaultValue = "8k|16k"
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)

val scgiTempPath = Directive(
    name = "scgi_temp_path",
    description = "Defines a directory for storing temporary files when buffering SCGI server responses",
    syntax = listOf("<b>scgi_temp_path</b> <i>path</i> [<i>level1</i> [<i>level2</i> [<i>level3</i>]]];"),
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            description = "Path to the directory for temporary files, can include variables",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "level1",
            description = "First level of directory hierarchy (optional)",
            valueType = ValueType.NUMBER,
            required = false
        ),
        DirectiveParameter(
            name = "level2",
            description = "Second level of directory hierarchy (optional)",
            valueType = ValueType.NUMBER,
            required = false
        ),
        DirectiveParameter(
            name = "level3",
            description = "Third level of directory hierarchy (optional)",
            valueType = ValueType.NUMBER,
            required = false
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_scgi_module
)
