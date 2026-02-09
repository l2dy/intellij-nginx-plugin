package dev.meanmail.directives.catalog.nginx.mail

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/mail/ngx_mail_ssl_module.html

val ngx_mail_ssl_module = NginxModule(
    name = "ngx_mail_ssl_module",
    description = "The SSL module for NGINX server configuration"
)

val mailSsl = ToggleDirective(
    "ssl",
    "Enables SSL for mail connections",
    enabled = false,
    context = listOf(mail, mailServer),
    module = ngx_mail_ssl_module
)

val mailSslCertificate = Directive(
    name = "ssl_certificate",
    description = "Specifies the path to the SSL certificate file for mail",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the SSL/TLS certificate file",
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_ssl_module
)

val mailSslCertificateKey = Directive(
    name = "ssl_certificate_key",
    description = "Specifies the path to the SSL certificate key file for mail",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the SSL/TLS certificate private key file",
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_ssl_module
)

val mailSslCiphers = Directive(
    name = "ssl_ciphers",
    description = "Specifies the ciphers for SSL connections",
    parameters = listOf(
        DirectiveParameter(
            name = "ciphers",
            valueType = ValueType.STRING,
            description = "List of allowed SSL/TLS cipher suites",
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_ssl_module
)

val mailSslClientCertificate = Directive(
    name = "ssl_client_certificate",
    description = "Specifies the path to the client CA certificate file for $mail",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the trusted CA certificate file",
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_ssl_module
)

val mailSslConfCommand = Directive(
    name = "ssl_conf_command",
    description = "Sets OpenSSL configuration commands",
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
    context = listOf(mail, mailServer),
    module = ngx_mail_ssl_module
)

val mailSslCrl = Directive(
    name = "ssl_crl",
    description = "Specifies the path to the certificate revocation list (CRL)",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the CRL file",
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_ssl_module
)

val mailSslDhparam = Directive(
    name = "ssl_dhparam",
    description = "Specifies the path to the Diffie-Hellman parameters file",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the Diffie-Hellman parameters file",
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_ssl_module
)

val mailSslEcdhCurve = Directive(
    name = "ssl_ecdh_curve",
    description = "Specifies the elliptic curve for ECDHE ciphers",
    parameters = listOf(
        DirectiveParameter(
            name = "curve",
            valueType = ValueType.STRING,
            description = "Name of the elliptic curve",
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_ssl_module
)

val mailSslPasswordFile = Directive(
    name = "ssl_password_file",
    description = "Specifies the path to the file with SSL private key passwords",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the password file",
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_ssl_module
)

val mailSslPreferServerCiphers = Directive(
    name = "ssl_prefer_server_ciphers",
    description = "Enables server cipher preferences over client preferences",
    parameters = listOf(
        DirectiveParameter(
            name = "state",
            valueType = ValueType.BOOLEAN,
            description = "Server cipher preference state",
            allowedValues = listOf("on", "off"),
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_ssl_module
)

val mailSslProtocols = Directive(
    name = "ssl_protocols",
    description = "Specifies the SSL/TLS protocols to use",
    parameters = listOf(
        DirectiveParameter(
            name = "protocols",
            valueType = ValueType.STRING,
            description = "List of SSL/TLS protocols to enable",
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_ssl_module
)

val mailSslSessionCache = Directive(
    name = "ssl_session_cache",
    description = "Configures the SSL session cache",
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
    context = listOf(mail, mailServer),
    module = ngx_mail_ssl_module
)

val mailSslSessionTicketKey = Directive(
    name = "ssl_session_ticket_key",
    description = "Specifies the path to the SSL session ticket key file",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the session ticket key file",
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_ssl_module
)

val mailSslSessionTickets = Directive(
    name = "ssl_session_tickets",
    description = "Enables or disables SSL session tickets",
    parameters = listOf(
        DirectiveParameter(
            name = "state",
            valueType = ValueType.BOOLEAN,
            description = "TLS session tickets state",
            allowedValues = listOf("on", "off"),
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_ssl_module
)

val mailSslSessionTimeout = Directive(
    name = "ssl_session_timeout",
    description = "Sets the timeout for SSL sessions",
    parameters = listOf(
        DirectiveParameter(
            name = "time",
            valueType = ValueType.TIME,
            description = "Duration for SSL/TLS session timeout",
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_ssl_module
)

val mailSslTrustedCertificate = Directive(
    name = "ssl_trusted_certificate",
    description = "Specifies the path to the trusted CA certificates file",
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the trusted CA certificate file",
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_ssl_module
)

val mailSslVerifyClient = Directive(
    name = "ssl_verify_client",
    description = "Configures client certificate verification",
    parameters = listOf(
        DirectiveParameter(
            name = "mode",
            valueType = ValueType.STRING,
            description = "Verification mode (on, off, optional, optional_no_ca)",
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_ssl_module
)

val mailSslVerifyDepth = Directive(
    name = "ssl_verify_depth",
    description = "Sets the maximum depth of CA certificate chain verification",
    parameters = listOf(
        DirectiveParameter(
            name = "depth",
            valueType = ValueType.NUMBER,
            description = "Maximum depth of certificate chain verification",
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_ssl_module
)

val starttls = Directive(
    name = "starttls",
    description = "Configures STARTTLS support",
    parameters = listOf(
        DirectiveParameter(
            name = "mode",
            description = "STARTTLS mode (on, off, or only)",
            valueType = ValueType.STRING,
            allowedValues = listOf("on", "off", "only")
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_ssl_module
)

val mailSslCertificateCompression = ToggleDirective(
    "ssl_certificate_compression",
    "Enables TLSv1.3 certificate compression (RFC 8879). Disabled by default since 1.29.1",
    enabled = false,
    context = listOf(mail, mailServer),
    module = ngx_mail_ssl_module
)
