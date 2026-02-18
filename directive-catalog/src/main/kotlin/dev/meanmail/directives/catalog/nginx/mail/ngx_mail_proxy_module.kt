package dev.meanmail.directives.catalog.nginx.mail

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/mail/ngx_mail_proxy_module.html

val ngx_mail_proxy_module = NginxModule(
    name = "ngx_mail_proxy_module",
    description = "Module for proxying mail protocols in NGINX mail server"
)

val proxyBuffer = Directive(
    name = "proxy_buffer",
    description = "Sets the size of the buffer used for proxying. Default size is equal to one memory page (4K or 8K depending on platform)",
    syntax = listOf("<b>proxy_buffer</b> <i>size</i>;"),
    module = ngx_mail_proxy_module,
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Buffer size for proxying",
            valueType = ValueType.SIZE,
            required = true,
            defaultValue = "4k|8k"
        )
    ),
    context = listOf(mail, mailServer)
)

val proxyPassErrorMessage = ToggleDirective(
    "proxy_pass_error_message",
    "Indicates whether to pass the error message obtained during backend authentication to the client. Default is off to prevent exposing internal error details",
    module = ngx_mail_proxy_module,
    enabled = false,
    context = listOf(mail, mailServer)
)

val proxyProtocol = ToggleDirective(
    "proxy_protocol",
    "Enables the PROXY protocol for connections to a backend",
    module = ngx_mail_proxy_module,
    enabled = false,
    context = listOf(mail, mailServer)
)

val proxySmtpAuth = ToggleDirective(
    "proxy_smtp_auth",
    "Enables or disables user authentication on the SMTP backend using the AUTH command. Default is off",
    module = ngx_mail_proxy_module,
    enabled = false,
    context = listOf(mail, mailServer)
)

val proxyTimeout = Directive(
    name = "proxy_timeout",
    description = "Sets the timeout between two successive read or write operations on client or proxied server connections. If no data is transmitted within this time, the connection is closed",
    syntax = listOf("<b>proxy_timeout</b> <i>timeout</i>;"),
    module = ngx_mail_proxy_module,
    parameters = listOf(
        DirectiveParameter(
            name = "time",
            valueType = ValueType.TIME,
            description = "Timeout duration for read/write operations",
            required = true
        )
    ),
    context = listOf(mail, mailServer)
)

val xclient = ToggleDirective(
    "xclient",
    "Enables or disables the passing of the XCLIENT command with client parameters when connecting to the SMTP backend. Default is on",
    module = ngx_mail_proxy_module,
    enabled = true,
    context = listOf(mail, mailServer)
)
