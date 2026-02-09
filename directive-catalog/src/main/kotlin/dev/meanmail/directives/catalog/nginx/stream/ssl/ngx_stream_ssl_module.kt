package dev.meanmail.directives.catalog.nginx.stream.ssl

import dev.meanmail.directives.catalog.*
import dev.meanmail.directives.catalog.nginx.stream.stream
import dev.meanmail.directives.catalog.nginx.stream.streamServer

// https://nginx.org/en/docs/stream/ngx_stream_ssl_module.html

val ngx_stream_ssl_module = NginxModule(
    name = "ngx_stream_ssl_module",
    description = "Comprehensive stream module for secure TCP/UDP connections, providing advanced SSL/TLS configuration, client authentication, and session management features"
)

val streamSslAlpn = Directive(
    name = "ssl_alpn",
    description = "Configures ALPN (Application-Layer Protocol Negotiation) for SSL/TLS stream connections",
    parameters = listOf(
        DirectiveParameter(
            name = "protocol",
            valueType = ValueType.STRING,
            description = "Supported application-level protocol",
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslCertificate = Directive(
    name = "ssl_certificate",
    description = "Sets the path to the SSL/TLS certificate file for the server",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the SSL/TLS certificate file",
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslCertificateCache = Directive(
    name = "ssl_certificate_cache",
    description = "Enables or disables the SSL certificate cache",
    parameters = listOf(
        DirectiveParameter(
            name = "enabled",
            valueType = ValueType.BOOLEAN,
            description = "Enables or disables the SSL certificate cache",
            defaultValue = "off"
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslCertificateKey = Directive(
    name = "ssl_certificate_key",
    description = "Sets the path to the SSL/TLS certificate private key file",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the SSL/TLS certificate private key file",
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslCiphers = Directive(
    name = "ssl_ciphers",
    description = "Specifies the enabled ciphers for SSL/TLS connections",
    parameters = listOf(
        DirectiveParameter(
            name = "ciphers",
            valueType = ValueType.STRING,
            description = "List of allowed SSL/TLS cipher suites",
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslClientCertificate = Directive(
    name = "ssl_client_certificate",
    description = "Sets the path to the trusted CA certificate for client certificate verification",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the trusted CA certificate file",
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslConfCommand = Directive(
    name = "ssl_conf_command",
    description = "Sets OpenSSL configuration commands for SSL/TLS connections",
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
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslCrl = Directive(
    name = "ssl_crl",
    description = "Sets the path to the certificate revocation list (CRL) for client certificate verification",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the CRL file",
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslDhparam = Directive(
    name = "ssl_dhparam",
    description = "Sets the path to the Diffie-Hellman parameters file for key exchange",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the Diffie-Hellman parameters file",
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslEcdhCurve = Directive(
    name = "ssl_ecdh_curve",
    description = "Specifies the elliptic curve for ECDHE key exchange",
    parameters = listOf(
        DirectiveParameter(
            name = "curve",
            valueType = ValueType.STRING,
            description = "Name of the elliptic curve",
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslHandshakeTimeout = Directive(
    name = "ssl_handshake_timeout",
    description = "Sets the timeout for SSL/TLS handshake completion",
    parameters = listOf(
        DirectiveParameter(
            name = "time",
            valueType = ValueType.TIME,
            description = "Timeout duration for SSL/TLS handshake",
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslPasswordFile = Directive(
    name = "ssl_password_file",
    description = "Sets the path to a file containing SSL certificate passwords",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the password file",
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslPreferServerCiphers = Directive(
    name = "ssl_prefer_server_ciphers",
    description = "Enables server cipher preference over client preference",
    parameters = listOf(
        DirectiveParameter(
            name = "state",
            valueType = ValueType.BOOLEAN,
            description = "Server cipher preference state",
            allowedValues = listOf("on", "off"),
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslProtocols = Directive(
    name = "ssl_protocols",
    description = "Specifies the SSL/TLS protocols to use",
    parameters = listOf(
        DirectiveParameter(
            name = "protocols",
            valueType = ValueType.STRING,
            description = "List of SSL/TLS protocols to enable",
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslSessionCache = Directive(
    name = "ssl_session_cache",
    description = "Configures the SSL/TLS session cache type and size",
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
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslSessionTicketKey = Directive(
    name = "ssl_session_ticket_key",
    description = "Sets the key for encrypting and decrypting TLS session tickets",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the session ticket key file",
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslSessionTickets = Directive(
    name = "ssl_session_tickets",
    description = "Enables or disables TLS session tickets",
    parameters = listOf(
        DirectiveParameter(
            name = "state",
            valueType = ValueType.BOOLEAN,
            description = "TLS session tickets state",
            allowedValues = listOf("on", "off"),
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslSessionTimeout = Directive(
    name = "ssl_session_timeout",
    description = "Sets the timeout for SSL/TLS session reuse",
    parameters = listOf(
        DirectiveParameter(
            name = "time",
            valueType = ValueType.TIME,
            description = "Duration for SSL/TLS session timeout",
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslTrustedCertificate = Directive(
    name = "ssl_trusted_certificate",
    description = "Sets the path to the trusted CA certificate for SSL/TLS verification",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the trusted CA certificate file",
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslVerifyClient = Directive(
    name = "ssl_verify_client",
    description = "Configures client certificate verification",
    parameters = listOf(
        DirectiveParameter(
            name = "mode",
            valueType = ValueType.STRING,
            description = "Verification mode (on, off, optional, optional_no_ca)",
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslVerifyDepth = Directive(
    name = "ssl_verify_depth",
    description = "Sets the maximum depth of CA certificate chain verification",
    parameters = listOf(
        DirectiveParameter(
            name = "depth",
            valueType = ValueType.NUMBER,
            description = "Maximum depth of certificate chain verification",
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslCertificateCompression = ToggleDirective(
    "ssl_certificate_compression",
    "Enables TLSv1.3 certificate compression (RFC 8879). Disabled by default since 1.29.1",
    enabled = false,
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslEchFile = Directive(
    name = "ssl_ech_file",
    description = "Specifies a file with encrypted ClientHello configuration for TLS 1.3 ECH",
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslOcsp = Directive(
    name = "ssl_ocsp",
    description = "Enables OCSP validation of the client certificate chain",
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
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslOcspCache = Directive(
    name = "ssl_ocsp_cache",
    description = "Sets name and size of the cache that stores client certificates status for OCSP validation",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            description = "Path and parameters for OCSP response cache",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslOcspResponder = Directive(
    name = "ssl_ocsp_responder",
    description = "Overrides the URL of the OCSP responder for validation of client certificates",
    parameters = listOf(
        DirectiveParameter(
            name = "url",
            description = "URL of the OCSP responder",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslRejectHandshake = ToggleDirective(
    "ssl_reject_handshake",
    "If enabled, SSL handshakes in the server block will be rejected",
    enabled = false,
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslStapling = ToggleDirective(
    "ssl_stapling",
    "Enables or disables stapling of OCSP responses by the server",
    enabled = false,
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslStaplingFile = Directive(
    name = "ssl_stapling_file",
    description = "Sets the file for stapled OCSP response instead of querying the OCSP responder",
    parameters = listOf(
        DirectiveParameter(
            name = "file",
            description = "Path to the file with OCSP response for stapling",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslStaplingResponder = Directive(
    name = "ssl_stapling_responder",
    description = "Overrides the URL of the OCSP responder for stapling",
    parameters = listOf(
        DirectiveParameter(
            name = "url",
            description = "URL of the OCSP responder for stapling",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)

val streamSslStaplingVerify = ToggleDirective(
    "ssl_stapling_verify",
    "Enables or disables verification of OCSP responses by the server",
    enabled = true,
    context = listOf(stream, streamServer),
    module = ngx_stream_ssl_module
)
