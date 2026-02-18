package dev.meanmail.directives.catalog.nginx.http

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/http/ngx_http_js_module.html

val ngx_http_js_module = NginxModule(
    "ngx_http_js_module",
    description = "Provides JavaScript integration for dynamic request processing and configuration"
)

val jsImport = Directive(
    name = "js_import",
    description = "Imports JavaScript modules for use in Nginx configuration",
    syntax = listOf("<b>js_import</b> <i>module.js</i> | <i>export_name from module.js</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "module",
            description = "Path to the JavaScript module or module name",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_js_module
)

val jsInclude = Directive(
    name = "js_include",
    description = "Includes JavaScript files for use in Nginx configuration",
    syntax = listOf("<b>js_include</b> <i>file</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the JavaScript file to include",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http),
    module = ngx_http_js_module
)

val jsPath = Directive(
    name = "js_path",
    description = "Sets the search path for JavaScript modules used in Nginx configuration",
    syntax = listOf("<b>js_path</b> <i>path</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "directory",
            description = "Path to the directory containing JavaScript modules",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_js_module
)

val jsPeriodic = Directive(
    name = "js_periodic",
    description = "Specifies a content handler to run at regular interval. The handler receives a session object as its first argument, it also has access to global objects such as ngx.",
    syntax = listOf("<b>js_periodic</b> <i>module.function</i> [interval=<i>time</i>] [jitter=<i>number</i>] [worker_affinity=<i>mask</i>];"),
    parameters = listOf(
        DirectiveParameter(
            name = "function",
            description = "JavaScript function to be executed periodically",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "interval",
            description = "Interval between periodic executions",
            valueType = ValueType.TIME,
            required = true
        )
    ),
    context = listOf(location),
    module = ngx_http_js_module
)

val jsPreloadObject = Directive(
    name = "js_preload_object",
    description = "Preloads a JavaScript object for use in Nginx configuration",
    syntax = listOf("<b>js_preload_object</b> <i>name.json</i> | <i>name</i> from <i>file.json</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "object_name",
            description = "Name of the object to preload",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "path",
            description = "Path to the file containing the object",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_js_module
)

val jsBodyFilter = Directive(
    name = "js_body_filter",
    description = "Applies a JavaScript function to modify the response body before sending to the client",
    syntax = listOf("<b>js_body_filter</b> <i>module.function</i> [<i>buffer_type</i>=<i>string</i> | <i>buffer</i>];"),
    parameters = listOf(
        DirectiveParameter(
            name = "javascript_function",
            description = "JavaScript function to process and modify response body",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(location, locationIf, limitExcept),
    module = ngx_http_js_module
)

val jsContent = Directive(
    name = "js_content",
    description = "Specifies a JavaScript function to handle request content",
    syntax = listOf("<b>js_content</b> <i>module.function</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "javascript_function",
            description = "JavaScript function to process request content",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(location, locationIf, limitExcept),
    module = ngx_http_js_module
)

val jsContextReuse = Directive(
    name = "js_context_reuse",
    description = "Sets a maximum number of JS context to be reused for QuickJS engine. Each context is used for a single request. The finished context is put into a pool of reusable contexts. If the pool is full, the context is destroyed.",
    syntax = listOf("<b>js_context_reuse</b> <i>number</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "max_contexts",
            description = "Maximum number of reusable JS contexts",
            valueType = ValueType.NUMBER,
            defaultValue = "128",
            required = false
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_js_module
)

val jsEngine = Directive(
    name = "js_engine",
    description = "Specifies the JavaScript engine to use for processing",
    syntax = listOf("<b>js_engine</b> njs | qjs;"),
    parameters = listOf(
        DirectiveParameter(
            name = "engine",
            description = "JavaScript engine type (njs or qjs)",
            valueType = ValueType.STRING,
            defaultValue = "njs",
            allowedValues = listOf("njs", "qjs"),
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_js_module
)

val jsFetchBufferSize = Directive(
    name = "js_fetch_buffer_size",
    description = "Sets the size of the buffer used for reading and writing with Fetch API.",
    syntax = listOf("<b>js_fetch_buffer_size</b> <i>size</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Buffer size for Fetch API operations",
            valueType = ValueType.SIZE,
            defaultValue = "16k",
            required = false
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_js_module
)

val jsFetchCiphers = Directive(
    name = "js_fetch_ciphers",
    description = "Configures SSL/TLS ciphers for JavaScript fetch operations",
    syntax = listOf("<b>js_fetch_ciphers</b> <i>ciphers</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "ciphers",
            description = "OpenSSL-compatible cipher suite configuration",
            valueType = ValueType.STRING,
            defaultValue = "HIGH:!aNULL:!MD5",
            required = false
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_js_module
)

val jsFetchMaxResponseBufferSize = Directive(
    name = "js_fetch_max_response_buffer_size",
    description = "Sets the maximum size of the response received with Fetch API.",
    syntax = listOf("<b>js_fetch_max_response_buffer_size</b> <i>size</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Maximum response buffer size",
            valueType = ValueType.SIZE,
            defaultValue = "1m",
            required = false
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_js_module
)

val jsFetchProtocols = Directive(
    name = "js_fetch_protocols",
    description = "Specifies SSL/TLS protocols for JavaScript fetch operations",
    syntax = listOf("<b>js_fetch_protocols</b> [TLSv1] [TLSv1.1] [TLSv1.2] [TLSv1.3];"),
    parameters = listOf(
        DirectiveParameter(
            name = "protocols",
            description = "List of allowed SSL/TLS protocols",
            valueType = ValueType.STRING,
            required = false
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_js_module
)

val jsFetchTimeout = Directive(
    name = "js_fetch_timeout",
    description = "Defines a timeout for reading and writing for Fetch API. The timeout is set only between two successive read/write operations, not for the whole response. If no data is transmitted within this time, the connection is closed.",
    syntax = listOf("<b>js_fetch_timeout</b> <i>time</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "timeout",
            description = "Connection timeout duration",
            valueType = ValueType.TIME,
            required = false
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_js_module
)

val jsFetchTrustedCertificate = Directive(
    name = "js_fetch_trusted_certificate",
    description = "Specifies a trusted certificate file for SSL/TLS verification in JavaScript fetch operations",
    syntax = listOf("<b>js_fetch_trusted_certificate</b> <i>file</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "certificate_file",
            description = "Path to trusted CA certificate file",
            valueType = ValueType.PATH,
            required = false
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_js_module
)

val jsFetchVerify = ToggleDirective(
    "js_fetch_verify",
    "Enables or disables verification of the HTTPS server certificate with Fetch API.",
    enabled = true,
    context = listOf(http, server, location),
    module = ngx_http_js_module
)

val jsFetchVerifyDepth = Directive(
    name = "js_fetch_verify_depth",
    description = "Sets the maximum depth of SSL certificate verification chain",
    syntax = listOf("<b>js_fetch_verify_depth</b> <i>number</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "depth",
            description = "Maximum depth of certificate chain verification",
            valueType = ValueType.NUMBER,
            defaultValue = "100",
            required = false
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_js_module
)

val jsHeaderFilter = Directive(
    name = "js_header_filter",
    description = "Applies a JavaScript function to modify response headers before sending to the client",
    syntax = listOf("<b>js_header_filter</b> <i>module.function</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "javascript_function",
            description = "JavaScript function to process and modify response headers",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(location, locationIf, limitExcept),
    module = ngx_http_js_module
)

val jsSet = Directive(
    name = "js_set",
    description = "Sets an Nginx variable using a JavaScript function",
    syntax = listOf("<b>js_set</b> <i>\$variable</i> <i>module.function</i> [nocache];"),
    parameters = listOf(
        DirectiveParameter(
            name = "variable",
            description = "Variable name to be set",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "function",
            description = "JavaScript function to generate variable value",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_js_module
)

var jsSharedDictZone = Directive(
    name = "js_shared_dict_zone",
    description = "Defines a shared memory zone for JavaScript objects",
    syntax = listOf("<b>js_shared_dict_zone</b> zone=<i>name</i>:<i>size</i> [timeout=<i>time</i>] [type=string|number] [evict] [state=<i>file</i>];"),
    context = listOf(http),
    module = ngx_http_js_module
)

val jsVar = Directive(
    name = "js_var",
    description = "Sets an Nginx variable using a JavaScript expression",
    syntax = listOf("<b>js_var</b> <i>\$variable</i> [<i>value</i>];"),
    parameters = listOf(
        DirectiveParameter(
            name = "variable",
            description = "Variable name",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "value",
            description = "Optional initial value for the variable",
            valueType = ValueType.STRING,
            required = false
        )
    ),
    context = listOf(http, server, location),
    module = ngx_http_js_module
)

val jsFetchKeepalive = Directive(
    name = "js_fetch_keepalive",
    description = "Activates the cache for connections to destination servers with Fetch API",
    syntax = listOf("<b>js_fetch_keepalive</b> <i>connections</i>;"),
    context = listOf(http, server, location),
    module = ngx_http_js_module
)

val jsFetchKeepaliveRequests = Directive(
    name = "js_fetch_keepalive_requests",
    description = "Sets the maximum number of requests through one keepalive connection with Fetch API",
    syntax = listOf("<b>js_fetch_keepalive_requests</b> <i>number</i>;"),
    context = listOf(http, server, location),
    module = ngx_http_js_module
)

val jsFetchKeepaliveTime = Directive(
    name = "js_fetch_keepalive_time",
    description = "Limits the maximum time for requests through one keepalive connection with Fetch API",
    syntax = listOf("<b>js_fetch_keepalive_time</b> <i>time</i>;"),
    context = listOf(http, server, location),
    module = ngx_http_js_module
)

val jsFetchKeepaliveTimeout = Directive(
    name = "js_fetch_keepalive_timeout",
    description = "Sets a timeout for idle keepalive connections to destination servers with Fetch API",
    syntax = listOf("<b>js_fetch_keepalive_timeout</b> <i>time</i>;"),
    context = listOf(http, server, location),
    module = ngx_http_js_module
)

val jsFetchProxy = Directive(
    name = "js_fetch_proxy",
    description = "Configures a forward proxy URL with Fetch API",
    syntax = listOf("<b>js_fetch_proxy</b> <i>url</i>;"),
    context = listOf(http, server, location),
    module = ngx_http_js_module
)

val jsLoadHttpNativeModule = Directive(
    name = "js_load_http_native_module",
    description = "Loads a native module (shared library) for use in HTTP JavaScript code (QuickJS only)",
    syntax = listOf("<b>js_load_http_native_module</b> <i>path</i> [as <i>name</i>];"),
    context = listOf(main),
    module = ngx_http_js_module
)
