package dev.meanmail.directives.catalog.nginx.mail

import dev.meanmail.directives.catalog.*

// https://nginx.org/en/docs/mail/ngx_mail_realip_module.html

val ngx_mail_realip_module = NginxModule(
    "ngx_mail_realip_module",
    description = "Module for changing client address and port sent in the PROXY protocol header"
)

val mailSetRealIpFrom = Directive(
    name = "set_real_ip_from",
    description = "Defines trusted addresses that are known to send correct replacement addresses",
    syntax = listOf("<b>set_real_ip_from</b> <i>address</i> | <i>CIDR</i> | unix:;"),
    module = ngx_mail_realip_module,
    parameters = listOf(
        DirectiveParameter(
            name = "address",
            valueType = ValueType.STRING,
            description = "IP address, network range (CIDR), or 'unix:' to trust all UNIX-domain sockets",
        )
    ),
    context = listOf(mail, mailServer)
)
