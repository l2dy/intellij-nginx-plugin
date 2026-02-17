package dev.meanmail.directives.catalog.openresty.http.lua

import dev.meanmail.directives.catalog.*
import dev.meanmail.directives.catalog.nginx.http.*
import dev.meanmail.directives.catalog.nginx.http.upstream.upstream

// https://github.com/openresty/lua-nginx-module#readme

val lua_nginx_module = NginxModule(
    name = "lua_nginx_module",
    description = "Lua module for Nginx",
)

val accessByLua = Directive(
    name = "access_by_lua",
    description = "Executes Lua code during the access phase",
    parameters = listOf(
        DirectiveParameter(
            name = "code",
            description = "Lua code to execute",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val accessByLuaBlock = Directive(
    name = "access_by_lua_block",
    description = "Executes Lua code during the access phase",
    parameters = emptyList(),
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val accessByLuaFile = Directive(
    name = "access_by_lua_file",
    description = "Executes Lua code from a file during the access phase",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the Lua file to execute",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val accessByLuaNoPostpone = ToggleDirective(
    name = "access_by_lua_no_postpone",
    description = "Disables postponing of access phase for Lua",
    enabled = false,
    context = listOf(http),
    module = lua_nginx_module
)

val balancerByLuaBlock = Directive(
    name = "balancer_by_lua_block",
    description = "Executes Lua code during the load balancing phase",
    parameters = emptyList(),
    context = listOf(upstream),
    module = lua_nginx_module
)

val balancerByLuaFile = Directive(
    name = "balancer_by_lua_file",
    description = "Executes Lua code from a file during the load balancing phase",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the Lua file to execute",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(upstream),
    module = lua_nginx_module
)

val balancerKeepalive = Directive(
    name = "balancer_keepalive",
    description = "Enables keepalive connections for the balancer",
    parameters = listOf(
        DirectiveParameter(
            name = "total_connections",
            description = "Number of connections to keep alive",
            valueType = ValueType.INTEGER,
            required = true,
            minValue = 1
        )
    ),
    context = listOf(upstream),
    module = lua_nginx_module
)

val bodyFilterByLua = Directive(
    name = "body_filter_by_lua",
    description = "Executes Lua code during the body filter phase",
    parameters = listOf(
        DirectiveParameter(
            name = "code",
            description = "Lua code to execute",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val bodyFilterByLuaBlock = Directive(
    name = "body_filter_by_lua_block",
    description = "Executes Lua code during the body filter phase",
    parameters = emptyList(),
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val bodyFilterByLuaFile = Directive(
    name = "body_filter_by_lua_file",
    description = "Executes Lua code from a file during the body filter phase",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the Lua file to execute",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val contentByLua = Directive(
    name = "content_by_lua",
    description = "Executes Lua code as content handler",
    parameters = listOf(
        DirectiveParameter(
            name = "code",
            description = "Lua code to execute",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(locationIf, location),
    module = lua_nginx_module
)

val contentByLuaBlock = Directive(
    name = "content_by_lua_block",
    description = "Executes Lua code as content handler",
    parameters = emptyList(),
    context = listOf(locationIf, location),
    module = lua_nginx_module
)

val contentByLuaFile = Directive(
    name = "content_by_lua_file",
    description = "Executes Lua code from a file as content handler",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the Lua file to execute",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(locationIf, location),
    module = lua_nginx_module
)

val exitWorkerByLuaBlock = Directive(
    name = "exit_worker_by_lua_block",
    description = "Executes Lua code when a worker process exits",
    parameters = emptyList(),
    context = listOf(http),
    module = lua_nginx_module
)

val exitWorkerByLuaFile = Directive(
    name = "exit_worker_by_lua_file",
    description = "Executes Lua code from a file when a worker process exits",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the Lua file to execute",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http),
    module = lua_nginx_module
)

val headerFilterByLua = Directive(
    name = "header_filter_by_lua",
    description = "Executes Lua code during the header filter phase",
    parameters = listOf(
        DirectiveParameter(
            name = "code",
            description = "Lua code to execute",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val headerFilterByLuaBlock = Directive(
    name = "header_filter_by_lua_block",
    description = "Executes Lua code during the header filter phase",
    parameters = emptyList(),
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val headerFilterByLuaFile = Directive(
    name = "header_filter_by_lua_file",
    description = "Executes Lua code from a file during the header filter phase",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the Lua file to execute",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val initByLua = Directive(
    name = "init_by_lua",
    description = "Executes Lua code when Nginx starts up",
    parameters = listOf(
        DirectiveParameter(
            name = "code",
            description = "Lua code to execute",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http),
    module = lua_nginx_module
)

val initByLuaBlock = Directive(
    name = "init_by_lua_block",
    description = "Executes Lua code when Nginx starts up",
    parameters = emptyList(),
    context = listOf(http),
    module = lua_nginx_module
)

val initByLuaFile = Directive(
    name = "init_by_lua_file",
    description = "Executes Lua code from a file when Nginx starts up",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the Lua file to execute",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http),
    module = lua_nginx_module
)

val initWorkerByLua = Directive(
    name = "init_worker_by_lua",
    description = "Executes Lua code when a worker process starts",
    parameters = listOf(
        DirectiveParameter(
            name = "code",
            description = "Lua code to execute",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http),
    module = lua_nginx_module
)

val initWorkerByLuaBlock = Directive(
    name = "init_worker_by_lua_block",
    description = "Executes Lua code when a worker process starts",
    parameters = emptyList(),
    context = listOf(http),
    module = lua_nginx_module
)

val initWorkerByLuaFile = Directive(
    name = "init_worker_by_lua_file",
    description = "Executes Lua code from a file when a worker process starts",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the Lua file to execute",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http),
    module = lua_nginx_module
)

val logByLua = Directive(
    name = "log_by_lua",
    description = "Executes Lua code during the logging phase",
    parameters = listOf(
        DirectiveParameter(
            name = "code",
            description = "Lua code to execute",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val logByLuaBlock = Directive(
    name = "log_by_lua_block",
    description = "Executes Lua code during the logging phase",
    parameters = emptyList(),
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val logByLuaFile = Directive(
    name = "log_by_lua_file",
    description = "Executes Lua code from a file during the logging phase",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the Lua file to execute",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val luaCaptureErrorLog = Directive(
    name = "lua_capture_error_log",
    description = "Captures Nginx error log messages",
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Size of the error log buffer",
            valueType = ValueType.SIZE,
            required = true
        )
    ),
    context = listOf(http),
    module = lua_nginx_module
)

val luaCheckClientAbort = ToggleDirective(
    name = "lua_check_client_abort",
    description = "Checks for client aborts in Lua",
    enabled = false,
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val luaCodeCache = ToggleDirective(
    name = "lua_code_cache",
    description = "Enables Lua code caching",
    enabled = true,
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val luaHttp10Buffering = ToggleDirective(
    name = "lua_http10_buffering",
    description = "Enables buffering for HTTP/1.0 requests",
    enabled = true,
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val luaLoadRestyCore = ToggleDirective(
    name = "lua_load_resty_core",
    description = "Deprecated; resty.core is loaded automatically during Lua VM initialization",
    enabled = true,
    context = listOf(http),
    module = lua_nginx_module
)

val luaMallocTrim = Directive(
    name = "lua_malloc_trim",
    description = "Trims the memory allocated by Lua",
    parameters = listOf(
        DirectiveParameter(
            name = "request_count",
            description = "Number of requests between trim operations",
            valueType = ValueType.INTEGER,
            required = true,
            defaultValue = "1000",
            minValue = 0
        )
    ),
    context = listOf(http),
    module = lua_nginx_module
)

val luaMaxPendingTimers = Directive(
    name = "lua_max_pending_timers",
    description = "Sets the maximum number of pending timers for Lua",
    parameters = listOf(
        DirectiveParameter(
            name = "count",
            description = "Maximum number of pending timers for Lua",
            valueType = ValueType.INTEGER,
            required = true,
            defaultValue = "1024",
            minValue = 1
        )
    ),
    context = listOf(http),
    module = lua_nginx_module
)

val luaMaxRunningTimers = Directive(
    name = "lua_max_running_timers",
    description = "Sets the maximum number of running timers for Lua",
    parameters = listOf(
        DirectiveParameter(
            name = "count",
            description = "Maximum number of running timers for Lua",
            valueType = ValueType.INTEGER,
            required = true,
            defaultValue = "256",
            minValue = 1
        )
    ),
    context = listOf(http),
    module = lua_nginx_module
)

val luaNeedRequestBody = ToggleDirective(
    name = "lua_need_request_body",
    description = "Forces reading the client request body before Lua handlers run",
    enabled = false,
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val luaPackageCpath = Directive(
    name = "lua_package_cpath",
    description = "Sets the Lua package C path",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            description = "Lua package C path",
            valueType = ValueType.STRING,
            required = true,
            defaultValue = "/usr/local/openresty/lualib/?.so"
        )
    ),
    context = listOf(http),
    module = lua_nginx_module
)

val luaPackagePath = Directive(
    name = "lua_package_path",
    description = "Sets the Lua package path",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            description = "Lua package path",
            valueType = ValueType.STRING,
            required = true,
            defaultValue = "/usr/local/openresty/lualib/?.lua;/usr/local/openresty/lualib/?/init.lua"
        )
    ),
    context = listOf(http),
    module = lua_nginx_module
)

val luaRegexCacheMaxEntries = Directive(
    name = "lua_regex_cache_max_entries",
    description = "Sets the maximum number of entries in the Lua regex cache",
    parameters = listOf(
        DirectiveParameter(
            name = "number",
            description = "Maximum number of entries in the Lua regex cache",
            valueType = ValueType.INTEGER,
            required = true,
            defaultValue = "1024",
            minValue = 1
        )
    ),
    context = listOf(http),
    module = lua_nginx_module
)

val luaRegexMatchLimit = Directive(
    name = "lua_regex_match_limit",
    description = "Sets the maximum number of regex matches in Lua",
    parameters = listOf(
        DirectiveParameter(
            name = "number",
            description = "Maximum number of regex matches in Lua",
            valueType = ValueType.INTEGER,
            required = true,
            defaultValue = "0",
            minValue = 0
        )
    ),
    context = listOf(http),
    module = lua_nginx_module
)

val luaSaRestart = ToggleDirective(
    name = "lua_sa_restart",
    description = "Enables restarting of Lua VMs after a signal",
    enabled = true,
    context = listOf(http),
    module = lua_nginx_module
)

val luaSharedDict = Directive(
    name = "lua_shared_dict",
    description = "Creates a shared memory zone for Lua",
    parameters = listOf(
        DirectiveParameter(
            name = "name",
            description = "Name of the shared memory zone",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "size",
            description = "Size of the shared memory zone",
            valueType = ValueType.SIZE,
            required = true
        )
    ),
    context = listOf(http),
    module = lua_nginx_module
)

val luaSocketBufferSize = Directive(
    name = "lua_socket_buffer_size",
    description = "Sets the buffer size for Lua sockets",
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Buffer size for Lua sockets",
            valueType = ValueType.SIZE,
            required = true,
            defaultValue = "4k|8k"
        )
    ),
    context = listOf(http, location, server),
    module = lua_nginx_module
)

val luaSocketConnectTimeout = Directive(
    name = "lua_socket_connect_timeout",
    description = "Sets the connection timeout for Lua sockets",
    parameters = listOf(
        DirectiveParameter(
            name = "timeout",
            description = "Connection timeout for Lua sockets",
            valueType = ValueType.TIME,
            required = true,
            defaultValue = "60s"
        )
    ),
    context = listOf(http, location, server),
    module = lua_nginx_module
)

val luaSocketKeepaliveTimeout = Directive(
    name = "lua_socket_keepalive_timeout",
    description = "Sets the keepalive timeout for Lua sockets",
    parameters = listOf(
        DirectiveParameter(
            name = "timeout",
            description = "Keepalive timeout for Lua sockets",
            valueType = ValueType.TIME,
            required = true,
            defaultValue = "60s"
        )
    ),
    context = listOf(http, location, server),
    module = lua_nginx_module
)

val luaSocketLogErrors = ToggleDirective(
    name = "lua_socket_log_errors",
    description = "Enables logging of Lua socket errors",
    enabled = true,
    context = listOf(http, location, server),
    module = lua_nginx_module
)

val luaSocketPoolSize = Directive(
    name = "lua_socket_pool_size",
    description = "Sets the pool size for Lua sockets",
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Pool size for Lua sockets",
            valueType = ValueType.INTEGER,
            required = true,
            defaultValue = "30",
            minValue = 1
        )
    ),
    context = listOf(http, location, server),
    module = lua_nginx_module
)

val luaSocketReadTimeout = Directive(
    name = "lua_socket_read_timeout",
    description = "Sets the read timeout for Lua sockets",
    parameters = listOf(
        DirectiveParameter(
            name = "timeout",
            description = "Read timeout for Lua sockets",
            valueType = ValueType.TIME,
            required = true,
            defaultValue = "60s"
        )
    ),
    context = listOf(http, location, server),
    module = lua_nginx_module
)

val luaSocketSendLowat = Directive(
    name = "lua_socket_send_lowat",
    description = "Sets the low water mark for Lua socket sends",
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Low water mark for Lua socket sends",
            valueType = ValueType.SIZE,
            required = true,
            defaultValue = "0"
        )
    ),
    context = listOf(http, location, server),
    module = lua_nginx_module
)

val luaSocketSendTimeout = Directive(
    name = "lua_socket_send_timeout",
    description = "Sets the send timeout for Lua sockets",
    parameters = listOf(
        DirectiveParameter(
            name = "timeout",
            description = "Send timeout for Lua sockets",
            valueType = ValueType.TIME,
            required = true,
            defaultValue = "60s"
        )
    ),
    context = listOf(http, location, server),
    module = lua_nginx_module
)

val luaSslCertificate = Directive(
    name = "lua_ssl_certificate",
    description = "Sets the SSL certificate for Lua",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the SSL certificate",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http, location, server),
    module = lua_nginx_module
)

val luaSslCertificateKey = Directive(
    name = "lua_ssl_certificate_key",
    description = "Sets the SSL certificate key for Lua",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the SSL certificate key",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http, location, server),
    module = lua_nginx_module
)

val luaSslCiphers = Directive(
    name = "lua_ssl_ciphers",
    description = "Sets the SSL ciphers for Lua",
    parameters = listOf(
        DirectiveParameter(
            name = "ciphers",
            description = "SSL ciphers for Lua",
            valueType = ValueType.STRING,
            required = true,
            defaultValue = "DEFAULT"
        )
    ),
    context = listOf(http, location, server),
    module = lua_nginx_module
)

val luaSslConfCommand = Directive(
    name = "lua_ssl_conf_command",
    description = "Sets an SSL configuration command for Lua",
    parameters = listOf(
        DirectiveParameter(
            name = "command",
            description = "SSL configuration command for Lua",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, location, server),
    module = lua_nginx_module
)

val luaSslCrl = Directive(
    name = "lua_ssl_crl",
    description = "Sets the SSL certificate revocation list for Lua",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the SSL certificate revocation list",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http, location, server),
    module = lua_nginx_module
)

val luaSslProtocols = Directive(
    name = "lua_ssl_protocols",
    description = "Sets the SSL protocols for Lua",
    parameters = listOf(
        DirectiveParameter(
            name = "protocols",
            description = "SSL protocols for Lua",
            valueType = ValueType.STRING,
            required = true,
            defaultValue = "TLSv1 TLSv1.1 TLSv1.2 TLSv1.3"
        )
    ),
    context = listOf(http, location, server),
    module = lua_nginx_module
)

val luaSslTrustedCertificate = Directive(
    name = "lua_ssl_trusted_certificate",
    description = "Sets the SSL trusted certificate for Lua",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the SSL trusted certificate",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http, location, server),
    module = lua_nginx_module
)

val luaSslVerifyDepth = Directive(
    name = "lua_ssl_verify_depth",
    description = "Sets the SSL verification depth for Lua",
    parameters = listOf(
        DirectiveParameter(
            name = "depth",
            description = "SSL verification depth for Lua",
            valueType = ValueType.INTEGER,
            required = true,
            defaultValue = "1",
            minValue = 1
        )
    ),
    context = listOf(http, location, server),
    module = lua_nginx_module
)

val luaSslKeyLog = Directive(
    name = "lua_ssl_key_log",
    description = "Logs SSL session keys in SSLKEYLOGFILE format",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the SSL key log file",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http, location, server),
    module = lua_nginx_module
)

val precontentByLuaBlock = Directive(
    name = "precontent_by_lua_block",
    description = "Executes Lua code during the precontent phase",
    parameters = emptyList(),
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val precontentByLuaFile = Directive(
    name = "precontent_by_lua_file",
    description = "Executes Lua code from a file during the precontent phase",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the Lua file to execute",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val precontentByLuaNoPostpone = ToggleDirective(
    name = "precontent_by_lua_no_postpone",
    description = "Disables postponing of precontent phase for Lua",
    enabled = false,
    context = listOf(http),
    module = lua_nginx_module
)

val proxySslCertificateByLuaBlock = Directive(
    name = "proxy_ssl_certificate_by_lua_block",
    description = "Executes Lua code during the proxy SSL certificate phase",
    parameters = emptyList(),
    context = listOf(location),
    module = lua_nginx_module
)

val proxySslCertificateByLuaFile = Directive(
    name = "proxy_ssl_certificate_by_lua_file",
    description = "Executes Lua code from a file during the proxy SSL certificate phase",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the Lua file to execute",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(location),
    module = lua_nginx_module
)

val proxySslVerifyByLuaBlock = Directive(
    name = "proxy_ssl_verify_by_lua_block",
    description = "Executes Lua code when verifying upstream SSL certificates",
    parameters = emptyList(),
    context = listOf(location),
    module = lua_nginx_module
)

val proxySslVerifyByLuaFile = Directive(
    name = "proxy_ssl_verify_by_lua_file",
    description = "Executes Lua code from a file when verifying upstream SSL certificates",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the Lua file to execute during SSL verification",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(location),
    module = lua_nginx_module
)

val luaUpstreamSkipOpensslDefaultVerify = ToggleDirective(
    name = "lua_upstream_skip_openssl_default_verify",
    description = "Controls whether to skip OpenSSL's default upstream certificate verification",
    enabled = false,
    context = listOf(location, locationIf),
    module = lua_nginx_module
)

val luaThreadCacheMaxEntries = Directive(
    name = "lua_thread_cache_max_entries",
    description = "Sets the maximum number of entries in the Lua thread cache",
    parameters = listOf(
        DirectiveParameter(
            name = "number",
            description = "Maximum number of entries in the Lua thread cache",
            valueType = ValueType.INTEGER,
            required = true,
            defaultValue = "1024",
            minValue = 1
        )
    ),
    context = listOf(http),
    module = lua_nginx_module
)

val luaTransformUnderscoresInResponseHeaders = ToggleDirective(
    name = "lua_transform_underscores_in_response_headers",
    description = "Transforms underscores in response headers to hyphens",
    enabled = true,
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val luaUseDefaultType = ToggleDirective(
    name = "lua_use_default_type",
    description = "Sets the default MIME type for Lua scripts",
    enabled = true,
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val luaWorkerThreadVmPoolSize = Directive(
    name = "lua_worker_thread_vm_pool_size",
    description = "Sets the size of the worker thread VM pool for Lua",
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Size of the worker thread VM pool for Lua",
            valueType = ValueType.INTEGER,
            required = true,
            defaultValue = "10",
            minValue = 1
        )
    ),
    context = listOf(http),
    module = lua_nginx_module
)

val rewriteByLua = Directive(
    name = "rewrite_by_lua",
    description = "Executes Lua code during the rewrite phase",
    parameters = listOf(
        DirectiveParameter(
            name = "code",
            description = "Lua code to execute",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val rewriteByLuaBlock = Directive(
    name = "rewrite_by_lua_block",
    description = "Executes Lua code during the rewrite phase",
    parameters = emptyList(),
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val rewriteByLuaFile = Directive(
    name = "rewrite_by_lua_file",
    description = "Executes Lua code from a file during the rewrite phase",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the Lua file to execute",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http, locationIf, location, server),
    module = lua_nginx_module
)

val rewriteByLuaNoPostpone = ToggleDirective(
    name = "rewrite_by_lua_no_postpone",
    description = "Disables postponing of rewrite phase for Lua",
    enabled = false,
    context = listOf(http),
    module = lua_nginx_module
)

val serverRewriteByLuaBlock = Directive(
    name = "server_rewrite_by_lua_block",
    description = "Executes Lua code during the server rewrite phase",
    parameters = emptyList(),
    context = listOf(http, server),
    module = lua_nginx_module
)

val serverRewriteByLuaFile = Directive(
    name = "server_rewrite_by_lua_file",
    description = "Executes Lua code from a file during the server rewrite phase",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the Lua file to execute",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http, server),
    module = lua_nginx_module
)

val setByLua = Directive(
    name = "set_by_lua",
    description = "Executes Lua code and sets a variable",
    parameters = listOf(
        DirectiveParameter(
            name = "variable",
            description = "Variable to set",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "code",
            description = "Lua code to execute",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "args",
            description = "Arguments to pass to the Lua code",
            valueType = ValueType.STRING,
            required = false,
            multiple = true
        )
    ),
    context = listOf(`if`, location, server),
    module = lua_nginx_module
)

val setByLuaBlock = Directive(
    name = "set_by_lua_block",
    description = "Executes Lua code and sets a variable",
    parameters = listOf(
        DirectiveParameter(
            name = "variable",
            description = "Variable to set",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(`if`, location, server),
    module = lua_nginx_module
)

val setByLuaFile = Directive(
    name = "set_by_lua_file",
    description = "Executes Lua code from a file and sets a variable",
    parameters = listOf(
        DirectiveParameter(
            name = "variable",
            description = "Variable to set",
            valueType = ValueType.STRING,
            required = true
        ),
        DirectiveParameter(
            name = "file",
            description = "Path to the Lua file to execute",
            valueType = ValueType.PATH,
            required = true
        ),
        DirectiveParameter(
            name = "args",
            description = "Arguments to pass to the Lua code",
            valueType = ValueType.STRING,
            required = false,
            multiple = true
        )
    ),
    context = listOf(`if`, location, server),
    module = lua_nginx_module
)

val sslCertificateByLuaBlock = Directive(
    name = "ssl_certificate_by_lua_block",
    description = "Executes Lua code during the SSL certificate phase",
    parameters = emptyList(),
    context = listOf(server),
    module = lua_nginx_module
)

val sslCertificateByLuaFile = Directive(
    name = "ssl_certificate_by_lua_file",
    description = "Executes Lua code from a file during the SSL certificate phase",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the Lua file to execute",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(server),
    module = lua_nginx_module
)

val sslClientHelloByLuaBlock = Directive(
    name = "ssl_client_hello_by_lua_block",
    description = "Executes Lua code during the SSL client hello phase",
    parameters = emptyList(),
    context = listOf(http, server),
    module = lua_nginx_module
)

val sslClientHelloByLuaFile = Directive(
    name = "ssl_client_hello_by_lua_file",
    description = "Executes Lua code from a file during the SSL client hello phase",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the Lua file to execute",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http, server),
    module = lua_nginx_module
)

val sslSessionFetchByLuaBlock = Directive(
    name = "ssl_session_fetch_by_lua_block",
    description = "Executes Lua code during the SSL session fetching phase",
    parameters = emptyList(),
    context = listOf(http),
    module = lua_nginx_module
)

val sslSessionFetchByLuaFile = Directive(
    name = "ssl_session_fetch_by_lua_file",
    description = "Executes Lua code from a file during the SSL session fetching phase",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the Lua file to execute",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http),
    module = lua_nginx_module
)

val sslSessionStoreByLuaBlock = Directive(
    name = "ssl_session_store_by_lua_block",
    description = "Executes Lua code during the SSL session storing phase",
    parameters = emptyList(),
    context = listOf(http),
    module = lua_nginx_module
)

val sslSessionStoreByLuaFile = Directive(
    name = "ssl_session_store_by_lua_file",
    description = "Executes Lua code from a file during the SSL session storing phase",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the Lua file to execute",
            valueType = ValueType.PATH,
            required = true
        )
    ),
    context = listOf(http),
    module = lua_nginx_module
)

