package dev.meanmail.directives.catalog.nginx.mail

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/mail/ngx_mail_imap_module.html

val ngx_mail_imap_module = NginxModule(
    "ngx_mail_imap_module",
    description = "Module for handling IMAP protocol in NGINX mail proxy"
)

val imapAuth = Directive(
    name = "imap_auth",
    description = "Sets permitted methods of authentication for IMAP clients. Supported methods are: plain (LOGIN, AUTH=PLAIN), login (AUTH=LOGIN), cram-md5 (AUTH=CRAM-MD5), and external (AUTH=EXTERNAL)",
    syntax = listOf("<b>imap_auth</b> <i>method</i> ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "method",
            description = "Authentication method for IMAP clients",
            valueType = ValueType.STRING,
            required = true,
            allowedValues = listOf("plain", "login", "cram-md5", "external"),
            defaultValue = "plain"
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_imap_module
)

val imapCapabilities = Directive(
    name = "imap_capabilities",
    description = "Sets the IMAP protocol extensions list passed to the client in response to the CAPABILITY command. Authentication methods and STARTTLS are automatically added based on configuration",
    syntax = listOf("<b>imap_capabilities</b> <i>extension</i> ...;"),
    parameters = listOf(
        DirectiveParameter(
            name = "extension",
            description = "IMAP protocol extension supported by the server",
            valueType = ValueType.STRING,
            required = true,
            defaultValue = "IMAP4 IMAP4rev1 UIDPLUS"
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_imap_module
)

val imapClientBuffer = Directive(
    name = "imap_client_buffer",
    description = "Sets the size of the buffer used for reading IMAP commands. Default size is equal to one memory page (4K or 8K depending on platform)",
    syntax = listOf("<b>imap_client_buffer</b> <i>size</i>;"),
    parameters = listOf(
        DirectiveParameter(
            name = "size",
            description = "Buffer size for reading IMAP commands",
            valueType = ValueType.SIZE,
            required = false,
            defaultValue = "4k|8k"
        )
    ),
    context = listOf(mail, mailServer),
    module = ngx_mail_imap_module
)
