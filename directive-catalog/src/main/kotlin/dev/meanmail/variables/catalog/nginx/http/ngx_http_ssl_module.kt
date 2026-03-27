package dev.meanmail.variables.catalog.nginx.http

import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.http.ngx_http_ssl_module
import dev.meanmail.variables.catalog.Variable

val ssl_alpn_protocol = Variable(
    "ssl_alpn_protocol",
    "returns the protocol selected by ALPN during the SSL handshake, or an empty string otherwise (1.21.4);",
    ngx_http_ssl_module,
    context = http,
)

val ssl_cipher = Variable(
    "ssl_cipher",
    "returns the name of the cipher used for an established SSL connection;",
    ngx_http_ssl_module,
    context = http,
)

val ssl_ciphers = Variable(
    "ssl_ciphers",
    """
    returns the list of ciphers supported by the client (1.11.7). Known ciphers are listed by names, unknown are shown in hexadecimal, for example:

    <pre><code>AES128-SHA:AES256-SHA:0x00ff</code></pre>

    The variable is fully supported only when using OpenSSL version 1.0.2 or higher. With older versions, the variable is available only for new sessions and lists only known ciphers.
    """.trimIndent(),
    ngx_http_ssl_module,
    context = http,
)

val ssl_client_escaped_cert = Variable(
    "ssl_client_escaped_cert",
    "returns the client certificate in the PEM format (urlencoded) for an established SSL connection (1.13.5);",
    ngx_http_ssl_module,
    context = http,
)

val ssl_client_cert = Variable(
    "ssl_client_cert",
    """
    returns the client certificate in the PEM format for an established SSL connection, with each line except the first prepended with the tab character; this is intended for the use in the directive;

    The variable is deprecated, the ${'$'}ssl_client_escaped_cert variable should be used instead.
    """.trimIndent(),
    ngx_http_ssl_module,
    context = http,
)

val ssl_client_fingerprint = Variable(
    "ssl_client_fingerprint",
    "returns the SHA1 fingerprint of the client certificate for an established SSL connection (1.7.1);",
    ngx_http_ssl_module,
    context = http,
)

val ssl_client_i_dn = Variable(
    "ssl_client_i_dn",
    "returns the “issuer DN” string of the client certificate for an established SSL connection according to RFC 2253 (1.11.6);",
    ngx_http_ssl_module,
    context = http,
)

val ssl_client_i_dn_legacy = Variable(
    "ssl_client_i_dn_legacy",
    """
    returns the “issuer DN” string of the client certificate for an established SSL connection;

    Prior to version 1.11.6, the variable name was ${'$'}ssl_client_i_dn.
    """.trimIndent(),
    ngx_http_ssl_module,
    context = http,
)

val ssl_client_raw_cert = Variable(
    "ssl_client_raw_cert",
    "returns the client certificate in the PEM format for an established SSL connection;",
    ngx_http_ssl_module,
    context = http,
)

val ssl_client_s_dn = Variable(
    "ssl_client_s_dn",
    "returns the “subject DN” string of the client certificate for an established SSL connection according to RFC 2253 (1.11.6);",
    ngx_http_ssl_module,
    context = http,
)

val ssl_client_s_dn_legacy = Variable(
    "ssl_client_s_dn_legacy",
    """
    returns the “subject DN” string of the client certificate for an established SSL connection;

    Prior to version 1.11.6, the variable name was ${'$'}ssl_client_s_dn.
    """.trimIndent(),
    ngx_http_ssl_module,
    context = http,
)

val ssl_client_serial = Variable(
    "ssl_client_serial",
    "returns the serial number of the client certificate for an established SSL connection;",
    ngx_http_ssl_module,
    context = http,
)

val ssl_client_sigalg = Variable(
    "ssl_client_sigalg",
    """
    returns the signature algorithm for the client certificate for an established SSL connection (1.29.3).

    The variable is supported only when using OpenSSL version 3.5 or higher. With older versions, the variable value will be an empty string.

    The variable is available only for new sessions.
    """.trimIndent(),
    ngx_http_ssl_module,
    context = http,
)

val ssl_client_v_end = Variable(
    "ssl_client_v_end",
    "returns the end date of the client certificate (1.11.7);",
    ngx_http_ssl_module,
    context = http,
)

val ssl_client_v_remain = Variable(
    "ssl_client_v_remain",
    "returns the number of days until the client certificate expires (1.11.7);",
    ngx_http_ssl_module,
    context = http,
)

val ssl_client_v_start = Variable(
    "ssl_client_v_start",
    "returns the start date of the client certificate (1.11.7);",
    ngx_http_ssl_module,
    context = http,
)

val ssl_client_verify = Variable(
    "ssl_client_verify",
    """
    returns the result of client certificate verification: “SUCCESS”, “FAILED:reason”, and “NONE” if a certificate was not present;

    Prior to version 1.11.7, the “FAILED” result did not contain the reason string.
    """.trimIndent(),
    ngx_http_ssl_module,
    context = http,
)

val ssl_curve = Variable(
    "ssl_curve",
    """
    returns the negotiated curve used for SSL handshake key exchange process (1.21.5). Known curves are listed by names, unknown are shown in hexadecimal, for example:

    <pre><code>prime256v1</code></pre>

    The variable is supported only when using OpenSSL version 3.0 or higher. With older versions, the variable value will be an empty string.
    """.trimIndent(),
    ngx_http_ssl_module,
    context = http,
)

val ssl_curves = Variable(
    "ssl_curves",
    """
    returns the list of curves supported by the client (1.11.7). Known curves are listed by names, unknown are shown in hexadecimal, for example:

    <pre><code>0x001d:prime256v1:secp521r1:secp384r1</code></pre>

    The variable is supported only when using OpenSSL version 1.0.2 or higher. With older versions, the variable value will be an empty string.

    The variable is available only for new sessions.
    """.trimIndent(),
    ngx_http_ssl_module,
    context = http,
)

val ssl_early_data = Variable(
    "ssl_early_data",
    "returns “1” if TLS 1.3 early data is used and the handshake is not complete, otherwise “” (1.15.3).",
    ngx_http_ssl_module,
    context = http,
)

val ssl_ech_outer_server_name = Variable(
    "ssl_ech_outer_server_name",
    "returns the public server name requested through SNI if TLS 1.3 ECH was accepted, otherwise “” (1.29.4);",
    ngx_http_ssl_module,
    context = http,
)

val ssl_ech_status = Variable(
    "ssl_ech_status",
    """
    returns the result of TLS 1.3 ECH processing: “FAILED”, “BACKEND”, “GREASE”, “SUCCESS”, or “NOT_TRIED” (1.29.4);

    The variable is currently supported only when using OpenSSL ECH feature branch and is therefore subject to change. The variable value will otherwise be an empty string.
    """.trimIndent(),
    ngx_http_ssl_module,
    context = http,
)

val ssl_protocol = Variable(
    "ssl_protocol",
    "returns the protocol of an established SSL connection;",
    ngx_http_ssl_module,
    context = http,
)

val ssl_server_name = Variable(
    "ssl_server_name",
    "returns the server name requested through SNI (1.7.0);",
    ngx_http_ssl_module,
    context = http,
)

val ssl_session_id = Variable(
    "ssl_session_id",
    "returns the session identifier of an established SSL connection;",
    ngx_http_ssl_module,
    context = http,
)

val ssl_session_reused = Variable(
    "ssl_session_reused",
    "returns “r” if an SSL session was reused, or “.” otherwise (1.5.11).",
    ngx_http_ssl_module,
    context = http,
)

val ssl_sigalg = Variable(
    "ssl_sigalg",
    """
    returns the signature algorithm for the server certificate for an established SSL connection (1.29.3).

    The variable is supported only when using OpenSSL version 3.5 or higher. With older versions, the variable value will be an empty string.

    The variable is available only for new sessions.
    """.trimIndent(),
    ngx_http_ssl_module,
    context = http,
)

val ngx_http_ssl_module_variables: List<Variable> = listOf(
    ssl_alpn_protocol,
    ssl_cipher,
    ssl_ciphers,
    ssl_client_escaped_cert,
    ssl_client_cert,
    ssl_client_fingerprint,
    ssl_client_i_dn,
    ssl_client_i_dn_legacy,
    ssl_client_raw_cert,
    ssl_client_s_dn,
    ssl_client_s_dn_legacy,
    ssl_client_serial,
    ssl_client_sigalg,
    ssl_client_v_end,
    ssl_client_v_remain,
    ssl_client_v_start,
    ssl_client_verify,
    ssl_curve,
    ssl_curves,
    ssl_early_data,
    ssl_ech_outer_server_name,
    ssl_ech_status,
    ssl_protocol,
    ssl_server_name,
    ssl_session_id,
    ssl_session_reused,
    ssl_sigalg,
)
