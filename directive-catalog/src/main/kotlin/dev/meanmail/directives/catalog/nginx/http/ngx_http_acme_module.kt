package dev.meanmail.directives.catalog.nginx.http

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/http/ngx_http_acme_module.html

val ngx_http_acme_module = NginxModule(
    "ngx_http_acme_module",
    description = "Provides automatic SSL/TLS certificate management via the ACME protocol"
)

val acmeIssuer = Directive(
    name = "acme_issuer",
    description = "Defines an ACME issuer configuration block",
    syntax = listOf("<b>acme_issuer</b> <i>name</i> { ... }"),
    context = listOf(http),
    module = ngx_http_acme_module
)

val acmeAcceptTermsOfService = Directive(
    name = "accept_terms_of_service",
    description = "Accepts the ACME server terms of service",
    syntax = listOf("<b>accept_terms_of_service</b>;"),
    context = listOf(acmeIssuer),
    module = ngx_http_acme_module
)

val acmeContact = Directive(
    name = "contact",
    description = "Specifies contact URLs for the ACME server account",
    syntax = listOf("<b>contact</b> <i>URL</i>;"),
    context = listOf(acmeIssuer),
    module = ngx_http_acme_module
)

val acmePreferredChain = Directive(
    name = "preferred_chain",
    description = "Specifies preferred certificate chain",
    syntax = listOf("<b>preferred_chain</b> <i>name</i>;"),
    context = listOf(acmeIssuer),
    module = ngx_http_acme_module
)

val acmeAccountKey = Directive(
    name = "account_key",
    description = "Specifies account private key type (ecdsa or rsa)",
    syntax = listOf("<b>account_key</b> <i>alg</i>[:<i>size</i>] | <i>file</i>;"),
    context = listOf(acmeIssuer),
    module = ngx_http_acme_module
)

val acmeCertificate = Directive(
    name = "acme_certificate",
    description = "Defines a certificate with domain identifiers for automatic management",
    syntax = listOf("<b>acme_certificate</b> <i>issuer</i> [<i>identifier</i> ...] [key=<i>alg</i>[:<i>size</i>]];"),
    context = listOf(server),
    module = ngx_http_acme_module
)

val acmeSharedZone = Directive(
    name = "acme_shared_zone",
    description = "Configures a shared memory zone for ACME certificate data",
    syntax = listOf("<b>acme_shared_zone</b> zone=<i>name</i>:<i>size</i>;"),
    context = listOf(http),
    module = ngx_http_acme_module
)

val acmeSslTrustedCertificate = Directive(
    name = "ssl_trusted_certificate",
    description = "Specifies a file with trusted CA certificates for ACME server verification",
    syntax = listOf("<b>ssl_trusted_certificate</b> <i>file</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "path",
            valueType = ValueType.PATH,
            description = "Path to the trusted CA certificate file",
        )
    ),
    context = listOf(acmeIssuer),
    module = ngx_http_acme_module
)

val acmeSslVerify = Directive(
    name = "ssl_verify",
    description = "Enables or disables ACME server certificate verification",
    syntax = listOf("<b>ssl_verify</b> on | off;"),
    context = listOf(acmeIssuer),
    module = ngx_http_acme_module
)

val acmeStatePath = Directive(
    name = "state_path",
    description = "Sets the directory for ACME module state files",
    syntax = listOf("<b>state_path</b> <i>path</i> | off;"),
    context = listOf(acmeIssuer),
    module = ngx_http_acme_module
)

val acmeUri = Directive(
    name = "uri",
    description = "Sets the ACME server directory URL",
    syntax = listOf("<b>uri</b> <i>uri</i>;"),
    context = listOf(acmeIssuer),
    module = ngx_http_acme_module
)

val acmeChallenge = Directive(
    name = "challenge",
    description = "Sets the ACME challenge type (http-01 or tls-alpn-01)",
    syntax = listOf("<b>challenge</b> <i>type</i>;"),
    context = listOf(acmeIssuer),
    module = ngx_http_acme_module
)

val acmeExternalAccountKey = Directive(
    name = "external_account_key",
    description = "Sets external account authorization parameters",
    syntax = listOf("<b>external_account_key</b> <i>kid</i> <i>file</i>;"),
    context = listOf(acmeIssuer),
    module = ngx_http_acme_module
)

val acmeProfile = Directive(
    name = "profile",
    description = "Specifies a certificate profile for the ACME request",
    syntax = listOf("<b>profile</b> <i>name</i> [require];"),
    context = listOf(acmeIssuer),
    module = ngx_http_acme_module
)
