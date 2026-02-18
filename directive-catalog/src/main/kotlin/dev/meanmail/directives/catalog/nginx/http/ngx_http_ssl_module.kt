package dev.meanmail.directives.catalog.nginx.http

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/http/ngx_http_ssl_module.html

val ngx_http_ssl_module = NginxModule(
    name = "ngx_http_ssl_module",
    description = "Provides support for HTTPS protocol. Requires OpenSSL library. Not built by default, must be enabled with --with-http_ssl_module configuration parameter"
)

val ssl = ToggleDirective(
    "ssl",
    "Enables HTTPS for the current server block (obsolete in version 1.15.0, use 'ssl' parameter of 'listen' directive instead)",
    enabled = false,
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslBufferSize = Directive(
    name = "ssl_buffer_size",
    description = "Sets the size of the buffer used for sending data. Default size is 16k, which corresponds to minimal overhead when sending big responses",
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Buffer size for sending SSL data",
            valueType = ValueType.SIZE,
            required = true,
            defaultValue = "16k"
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslCertificate = Directive(
    name = "ssl_certificate",
    description = """
        Specifies a file with the certificate in the PEM format for the given virtual server. 
        If intermediate certificates are needed, they should be specified in the same file in order: 
        primary certificate first, then intermediate certificates. 
        A secret key in PEM format may be placed in the same file.

        Can be specified multiple times to load certificates of different types (e.g., RSA and ECDSA).
        Requires OpenSSL 1.0.2+ for separate certificate chains.

        Supports variables in filename since version 1.15.9 (with potential performance implications).
        Can use 'data:${'$'}variable' syntax since 1.15.10 to load certificate from a variable.
    """.trimIndent(),
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the SSL/TLS certificate file",
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslCertificateCache = Directive(
    name = "ssl_certificate_cache",
    description = "Configures the cache for SSL certificates to improve performance by avoiding repeated file reads",
    parameters = listOf(
        DirectiveParameter(
            name = "enabled",
            valueType = ValueType.BOOLEAN,
            description = "Enables or disables the SSL certificate cache",
            defaultValue = "off"
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslCertificateKey = Directive(
    name = "ssl_certificate_key",
    description = "Specifies the path to a file with the secret key in the PEM format for the given virtual server",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the SSL/TLS certificate private key file",
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslCiphers = Directive(
    name = "ssl_ciphers",
    description = "Specifies the enabled ciphers for SSL connections. The ciphers are specified in the OpenSSL library format",
    parameters = listOf(
        DirectiveParameter(
            name = "ciphers",
            valueType = ValueType.STRING,
            description = "List of allowed SSL/TLS cipher suites",
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslClientCertificate = Directive(
    name = "ssl_client_certificate",
    description = "Specifies the trusted CA certificates for client certificate authentication",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the trusted CA certificate file",
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslConfCommand = Directive(
    name = "ssl_conf_command",
    description = "Sets arbitrary OpenSSL configuration commands for the SSL context. Allows fine-tuning of SSL/TLS parameters not directly supported by NGINX",
    parameters = listOf(
        DirectiveParameter(
            name = "command",
            valueType = ValueType.STRING,
            description = "OpenSSL configuration command",
        ),
        DirectiveParameter(
            name = "value",
            valueType = ValueType.STRING,
            description = "Value for the configuration command",
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslCrl = Directive(
    name = "ssl_crl",
    description = "Specifies a file with revoked certificates (Certificate Revocation List) that will be used in the client certificate verification",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the CRL file",
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslDhparam = Directive(
    name = "ssl_dhparam",
    description = "Specifies the path to a file containing Diffie-Hellman parameters for DHE ciphers",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the Diffie-Hellman parameters file",
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslEarlyData = ToggleDirective(
    "ssl_early_data",
    "Enables TLS 1.3 early data (0-RTT) support, which allows sending data in the first TLS handshake message",
    enabled = false,
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslEcdhCurve = Directive(
    name = "ssl_ecdh_curve",
    description = "Specifies a curve for ECDHE ciphers used in SSL connections",
    parameters = listOf(
        DirectiveParameter(
            name = "curve",
            valueType = ValueType.STRING,
            description = "Name of the elliptic curve",
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslOcsp = Directive(
    name = "ssl_ocsp",
    description = "Enables or configures OCSP (Online Certificate Status Protocol) stapling",
    parameters = listOf(
        DirectiveParameter(
            name = "parameter",
            description = "OCSP stapling configuration option",
            valueType = ValueType.STRING,
            required = false,
            allowedValues = listOf(
                "on",
                "off",
                "leaf"
            )
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslOcspCache = Directive(
    name = "ssl_ocsp_cache",
    description = "Configures the storage of OCSP responses for SSL connections",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            description = "Path and parameters for OCSP response cache",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslOcspResponder = Directive(
    name = "ssl_ocsp_responder",
    description = "Specifies the OCSP responder URL for certificate validation",
    parameters = listOf(
        DirectiveParameter(
            name = "url",
            description = "URL of the OCSP responder",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslPasswordFile = Directive(
    name = "ssl_password_file",
    description = "Specifies a file with passwords used to decrypt SSL private keys",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the password file",
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslPreferServerCiphers = ToggleDirective(
    "ssl_prefer_server_ciphers",
    "Determines whether server or client ciphers are preferred during SSL negotiation",
    enabled = false,
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslProtocols = Directive(
    name = "ssl_protocols",
    description = """
        Enables specified SSL/TLS protocols.

        Notes:
        - TLSv1.1 and TLSv1.2 require OpenSSL 1.0.1+
        - TLSv1.3 requires OpenSSL 1.1.1+
        - TLSv1.3 is used by default since version 1.23.4

        If specified at server level, can use default server's value.
    """.trimIndent(),
    parameters = listOf(
        DirectiveParameter(
            name = "protocols",
            valueType = ValueType.STRING,
            description = "List of SSL/TLS protocols to enable",
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslRejectHandshake = ToggleDirective(
    "ssl_reject_handshake",
    "Rejects SSL handshakes when no certificate is configured",
    enabled = false,
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslSessionCache = Directive(
    name = "ssl_session_cache",
    description = "Configures the SSL session cache for reusing SSL sessions",
    parameters = listOf(
        DirectiveParameter(
            name = "type",
            valueType = ValueType.STRING,
            description = "Session cache type (shared, builtin)",
        ),
        DirectiveParameter(
            name = "size",
            valueType = ValueType.SIZE,
            description = "Size of the session cache",
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslSessionTicketKey = Directive(
    name = "ssl_session_ticket_key",
    description = "Specifies a file with the secret key used to encrypt and decrypt TLS session tickets",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the session ticket key file",
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslSessionTickets = ToggleDirective(
    "ssl_session_tickets",
    "Enables or disables session tickets for SSL/TLS connections",
    enabled = true,
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslSessionTimeout = Directive(
    name = "ssl_session_timeout",
    description = "Sets the time for which an SSL session will be valid",
    parameters = listOf(
        DirectiveParameter(
            name = "time",
            valueType = ValueType.TIME,
            description = "Duration for SSL/TLS session timeout",
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslStapling = ToggleDirective(
    "ssl_stapling",
    "Enables OCSP stapling for SSL/TLS connections",
    enabled = false,
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslStaplingFile = Directive(
    name = "ssl_stapling_file",
    description = "Specifies a file with the OCSP response for SSL stapling",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the file with OCSP response for stapling",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslStaplingResponder = Directive(
    name = "ssl_stapling_responder",
    description = "Specifies the OCSP responder URL for SSL stapling",
    parameters = listOf(
        DirectiveParameter(
            name = "url",
            description = "URL of the OCSP responder for stapling",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslStaplingVerify = ToggleDirective(
    "ssl_stapling_verify",
    "Enables verification of the OCSP response during SSL stapling",
    enabled = false,
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslTrustedCertificate = Directive(
    name = "ssl_trusted_certificate",
    description = "Specifies a file with trusted CA certificates for SSL/TLS connections",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the trusted CA certificate file",
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslVerifyClient = Directive(
    name = "ssl_verify_client",
    description = "Configures client certificate verification",
    parameters = listOf(
        DirectiveParameter(
            name = "mode",
            valueType = ValueType.STRING,
            description = "Verification mode (on, off, optional, optional_no_ca)",
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslVerifyDepth = Directive(
    name = "ssl_verify_depth",
    description = "Sets the maximum depth of client certificate verification chain",
    parameters = listOf(
        DirectiveParameter(
            name = "depth",
            valueType = ValueType.NUMBER,
            description = "Maximum depth of certificate chain verification",
        )
    ),
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslCertificateCompression = ToggleDirective(
    "ssl_certificate_compression",
    "Enables TLSv1.3 certificate compression (RFC 8879). Disabled by default since 1.29.1",
    enabled = false,
    context = listOf(http, server),
    module = ngx_http_ssl_module
)

val sslEchFile = Directive(
    name = "ssl_ech_file",
    description = "Specifies a file with encrypted ClientHello configuration for TLS 1.3 ECH",
    context = listOf(http, server),
    module = ngx_http_ssl_module
)
