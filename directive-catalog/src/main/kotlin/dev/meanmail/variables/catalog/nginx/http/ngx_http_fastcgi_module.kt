package dev.meanmail.variables.catalog.nginx.http

import dev.meanmail.directives.catalog.nginx.http.http
import dev.meanmail.directives.catalog.nginx.http.ngx_http_fastcgi_module
import dev.meanmail.variables.catalog.Variable

val fastcgi_script_name = Variable(
    "fastcgi_script_name",
    """
    request URI or, if a URI ends with a slash, request URI with an index file name configured by the directive appended to it. This variable can be used to set the SCRIPT_FILENAME and PATH_TRANSLATED parameters that determine the script name in PHP. For example, for the “/info/” request with the following directives

    <pre><code>fastcgi_index index.php;
fastcgi_param SCRIPT_FILENAME /home/www/scripts/php${'$'}fastcgi_script_name;</code></pre>

    the SCRIPT_FILENAME parameter will be equal to “/home/www/scripts/php/info/index.php”.

    When using the directive, the ${'$'}fastcgi_script_name variable equals the value of the first capture set by the directive.
    """.trimIndent(),
    ngx_http_fastcgi_module,
    context = http,
)

val fastcgi_path_info = Variable(
    "fastcgi_path_info",
    "the value of the second capture set by the directive. This variable can be used to set the PATH_INFO parameter.",
    ngx_http_fastcgi_module,
    context = http,
)

val ngx_http_fastcgi_module_variables: List<Variable> = listOf(
    fastcgi_script_name,
    fastcgi_path_info,
)
