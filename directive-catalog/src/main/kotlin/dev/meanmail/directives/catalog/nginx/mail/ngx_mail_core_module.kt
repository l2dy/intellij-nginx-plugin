package dev.meanmail.directives.catalog.nginx.mail

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/mail/ngx_mail_core_module.html

val ngx_mail_core_module = NginxModule(
    "ngx_mail_core_module",
    description = "Core module for NGINX mail server configuration"
)


val mail = Directive(
    name = "mail",
    description = "Provides configuration context for mail server directives",
    syntax = listOf("<b>mail</b> { ... }"),

    context = listOf(main),
    module = ngx_mail_core_module
)

val mailServer = Directive(
    name = "server",
    description = "Configures a mail server block",
    syntax = listOf("<b>server</b> { ... }"),

    parameters = listOf(

        DirectiveParameter(

            name = "address",

            valueType = ValueType.STRING,

            description = "IP address or domain name of the server",

            ),

        DirectiveParameter(

            name = "port",

            valueType = ValueType.NUMBER,

            description = "Port of the server",

            required = false,

            ),

        DirectiveParameter(

            name = "weight",

            valueType = ValueType.NUMBER,

            description = "Weight for server in load balancing",

            required = false,

            ),

        DirectiveParameter(

            name = "max_conns",

            valueType = ValueType.NUMBER,

            description = "Maximum number of concurrent connections",

            required = false,

            ),

        DirectiveParameter(

            name = "max_fails",

            valueType = ValueType.NUMBER,

            description = "Number of failed attempts before marking server unavailable",

            required = false,

            ),

        DirectiveParameter(

            name = "fail_timeout",

            valueType = ValueType.TIME,

            description = "Duration to consider server unavailable after max_fails",

            required = false,

            )

    ),

    context = listOf(mail),
    module = ngx_mail_core_module
)

val mailListen = Directive(
    name = "listen",
    description = "Sets the address and port for the mail server socket",
    syntax = listOf("<b>listen</b> <i>address</i>:<i>port</i> [ssl] [proxy_protocol] [backlog=<i>number</i>] [rcvbuf=<i>size</i>] [sndbuf=<i>size</i>] [bind] [ipv6only=on|off] [so_keepalive=on|off|[<i>keepidle</i>]:[<i>keepintvl</i>]:[<i>keepcnt</i>]];"),
    parameters = listOf(
        DirectiveParameter(
            name = "address",
            valueType = ValueType.STRING,
            description = "IP address or UNIX-domain socket path",
        ),
        DirectiveParameter(
            name = "port",
            valueType = ValueType.NUMBER,
            description = "Port number for TCP connections",
        ),
        DirectiveParameter(
            name = "options",
            valueType = ValueType.STRING,
            description = "Additional socket configuration options",
            required = false,
        )
    ),
    context = listOf(mailServer),
    module = ngx_mail_core_module
)

val mailMaxErrors = Directive(
    name = "max_errors",
    description = "Sets the number of protocol errors before closing the connection",
    syntax = listOf("<b>max_errors</b> <i>number</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "number",
            description = "Maximum number of protocol errors",
            valueType = ValueType.INTEGER,
            required = false,
            defaultValue = "5"
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_core_module
)

val mailProtocol = Directive(
    name = "protocol",
    description = "Specifies the mail protocol to use",
    syntax = listOf("<b>protocol</b> imap | pop3 | smtp;"),
    parameters = listOf(
        DirectiveParameter(
            name = "protocol_type",
            description = "Mail protocol type",
            valueType = ValueType.STRING,
            required = true,
            allowedValues = listOf("imap", "pop3", "smtp")
        )
    ),
    context = listOf(mailServer),
    module = ngx_mail_core_module
)

val mailResolver = Directive(
    name = "resolver",
    description = "Defines DNS servers used to resolve hostnames",
    syntax = listOf(
        "<b>resolver</b> <i>address</i> ... [valid=<i>time</i>] [ipv4=on|off] [ipv6=on|off] [status_zone=<i>zone</i>];",
        "<b>resolver</b> off;"
    ),
    parameters = listOf(
        DirectiveParameter(
            name = "address",
            valueType = ValueType.STRING,
            description = "IP address of DNS server",
        ),
        DirectiveParameter(
            name = "valid",
            valueType = ValueType.TIME,
            description = "Caching time for DNS records",
            required = false,
        ),
        DirectiveParameter(
            name = "ipv6",
            valueType = ValueType.BOOLEAN,
            description = "Enable IPv6 resolution",
            required = false,
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_core_module
)

val mailResolverTimeout = Directive(
    name = "resolver_timeout",
    description = "Sets the resolution timeout for DNS queries",
    syntax = listOf("<b>resolver_timeout</b> <i>time</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "time",
            valueType = ValueType.TIME,
            description = "Timeout duration for DNS resolution",
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_core_module
)

val mailServerName = Directive(
    name = "server_name",
    description = "Sets the server name for mail server",
    syntax = listOf("<b>server_name</b> <i>name</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "name",
            description = "Server name",
            valueType = ValueType.STRING,
            required = true
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_core_module
)

val mailTimeout = Directive(
    name = "timeout",
    description = "Sets the timeout for mail server connections",
    syntax = listOf("<b>timeout</b> <i>time</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "time",
            description = "Connection timeout duration",
            valueType = ValueType.TIME,
            required = false,
            defaultValue = "60s"
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_core_module
)
