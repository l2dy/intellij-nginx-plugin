#!/usr/bin/env python3
"""Generate Kotlin variable catalog files from exported variable JSON.

Reads the JSON output of export_variables.py and generates (overwrites) Kotlin
source files under directive-catalog/src/main/kotlin/dev/meanmail/variables/catalog/.

Also generates Common.kt with the Variable class, findVariables(), and
initVariables() aggregating all modules.

Usage:
    cd nginx.org && python3 ../scripts/export_variables.py \
        | python3 ../scripts/generate_variables.py

Or with a file argument:
    python3 scripts/generate_variables.py variables.json
"""

import json
import keyword
import re
import sys
from pathlib import Path


# Map module_key -> relative path (from catalog root) of the directive catalog file.
# Built by scanning the directive catalog directory at runtime.
_directive_catalog_paths: dict[str, Path] = {}


# Context directive import paths, keyed by context string from JSON.
_CONTEXT_IMPORTS = {
    'http': 'dev.meanmail.directives.catalog.nginx.http.http',
    'stream': 'dev.meanmail.directives.catalog.nginx.stream.stream',
}


def _build_directive_catalog_map(directive_catalog_dir: Path):
    """Scan directive catalog .kt files and build module_key -> relative path map."""
    for kt_file in directive_catalog_dir.rglob('*.kt'):
        if kt_file.name == 'Common.kt':
            continue
        module_key = kt_file.stem
        rel = kt_file.relative_to(directive_catalog_dir)
        _directive_catalog_paths[module_key] = rel


def _directive_import_path(module_key: str) -> str | None:
    """Return the Kotlin import path for a directive catalog module val.

    E.g. "ngx_http_core_module" -> "dev.meanmail.directives.catalog.nginx.http.ngx_http_core_module"
    """
    rel = _directive_catalog_paths.get(module_key)
    if rel is None:
        return None
    # Convert path to package: nginx/http/ngx_http_core_module.kt -> nginx.http.ngx_http_core_module
    parts = list(rel.with_suffix('').parts)
    return 'dev.meanmail.directives.catalog.' + '.'.join(parts)


def _variable_package(module_key: str) -> str | None:
    """Return the Kotlin package for the variable catalog file of a module.

    Mirrors the directive catalog subpackage structure.
    E.g. "ngx_http_core_module" (at nginx/http/ngx_http_core_module.kt)
         -> "dev.meanmail.variables.catalog.nginx.http"
    """
    rel = _directive_catalog_paths.get(module_key)
    if rel is None:
        return None
    parent_parts = list(rel.parent.parts)
    if not parent_parts or parent_parts == ['.']:
        return 'dev.meanmail.variables.catalog'
    return 'dev.meanmail.variables.catalog.' + '.'.join(parent_parts)


def _variable_output_path(module_key: str, output_base: Path) -> Path | None:
    """Return the output .kt file path for a module's variable catalog."""
    rel = _directive_catalog_paths.get(module_key)
    if rel is None:
        return None
    # Same relative path but under the variables catalog tree
    return output_base / rel


def _sanitize_val_name(var_name: str) -> str:
    """Convert a variable name to a valid Kotlin val identifier.

    - Replaces non-alphanumeric chars with _
    - Prefixes with _ if it starts with a digit
    - Backtick-wraps Kotlin keywords
    """
    name = re.sub(r'[^a-zA-Z0-9_]', '_', var_name)
    if not name:
        name = '_'
    if name[0].isdigit():
        name = '_' + name
    # Kotlin hard keywords that can't be used as identifiers
    kotlin_keywords = {
        'as', 'break', 'class', 'continue', 'do', 'else', 'false', 'for',
        'fun', 'if', 'in', 'interface', 'is', 'null', 'object', 'package',
        'return', 'super', 'this', 'throw', 'true', 'try', 'typealias',
        'typeof', 'val', 'var', 'when', 'while',
    }
    if name in kotlin_keywords:
        name = f'`{name}`'
    return name


def _escape_kotlin_string(s: str) -> str:
    """Escape a string for use inside a Kotlin string literal."""
    s = s.replace('\\', '\\\\')
    s = s.replace('"', '\\"')
    s = s.replace('$', '\\$')
    return s


def generate_module_file(
    module_key: str,
    module_data: dict,
    output_base: Path,
) -> Path | None:
    """Generate a single module's variable catalog .kt file.

    Returns the output path, or None if the module has no directive catalog entry.
    """
    output_path = _variable_output_path(module_key, output_base)
    if output_path is None:
        return None

    package = _variable_package(module_key)
    directive_import = _directive_import_path(module_key)
    context = module_data.get('context')

    lines = []
    lines.append(f'package {package}')
    lines.append('')
    if context and context in _CONTEXT_IMPORTS:
        lines.append(f'import {_CONTEXT_IMPORTS[context]}')
    else:
        lines.append('import dev.meanmail.directives.catalog.any')
    lines.append(f'import {directive_import}')
    lines.append('import dev.meanmail.variables.catalog.Variable')
    lines.append('')

    variables = module_data.get('variables', {})
    val_names = []
    context_id = context if context else 'any'
    for var_name, var_info in variables.items():
        val_name = _sanitize_val_name(var_name)
        val_names.append(val_name)
        description = var_info['description']
        parameterized = var_info.get('parameterized', False)

        lines.append(f'val {val_name} = Variable(')
        lines.append(f'    "{_escape_kotlin_string(var_name)}",')
        if '\n' in description:
            # Triple-quoted strings: $ must be escaped as ${'$'}
            raw_desc = description.replace('$', "${'$'}")
            lines.append('    """')
            for para in raw_desc.split('\n\n'):
                lines.append(f'    {para}')
                lines.append('')
            # Remove trailing blank line and close the triple-quote
            if lines[-1] == '':
                lines.pop()
            lines.append('    """.trimIndent(),')
        else:
            lines.append(f'    "{_escape_kotlin_string(description)}",')
        lines.append(f'    {module_key},')
        if parameterized:
            lines.append(f'    parameterized = true,')
        lines.append(f'    context = {context_id},')
        lines.append(')')
        lines.append('')

    # Aggregate list for this module
    lines.append(f'val {module_key}_variables: List<Variable> = listOf(')
    for val_name in val_names:
        lines.append(f'    {val_name},')
    lines.append(')')
    lines.append('')

    content = '\n'.join(lines)
    output_path.parent.mkdir(parents=True, exist_ok=True)
    output_path.write_text(content, encoding='utf-8')
    return output_path


def generate_common_kt(
    generated_modules: list[tuple[str, dict]],
    output_base: Path,
):
    """Generate Common.kt with Variable class, findVariables(), and initVariables().

    References per-module <module>_variables lists to keep this file short.
    Imports the per-module _variables vals by name (these are unique across modules).
    Uses wildcard imports grouped by subpackage to keep the import section short.
    """
    # Collect packages that contain _variables vals
    packages = set()
    short_names = []
    for module_key, module_data in sorted(generated_modules, key=lambda x: x[0]):
        package = _variable_package(module_key)
        packages.add(package)
        short_names.append(f'{module_key}_variables')

    lines = []
    lines.append('package dev.meanmail.variables.catalog')
    lines.append('')
    lines.append('import dev.meanmail.directives.catalog.Directive')
    lines.append('import dev.meanmail.directives.catalog.NginxModule')
    lines.append('import dev.meanmail.directives.catalog.any')
    for pkg in sorted(packages):
        lines.append(f'import {pkg}.*')
    lines.append('')
    lines.append('class Variable(')
    lines.append('    val name: String,')
    lines.append('    val description: String,')
    lines.append('    val module: NginxModule,')
    lines.append('    val parameterized: Boolean = false,')
    lines.append('    val context: Directive = any,')
    lines.append(') {')
    lines.append('    companion object {')
    lines.append('        val all: List<Variable> by lazy { initVariables() }')
    lines.append('    }')
    lines.append('}')
    lines.append('')
    lines.append('fun findVariables(name: String): List<Variable> {')
    lines.append('    val exact = Variable.all.filter { it.name == name }')
    lines.append('    if (exact.isNotEmpty()) return exact')
    lines.append('    return Variable.all.filter { it.parameterized && name.startsWith(it.name) }')
    lines.append('}')
    lines.append('')
    lines.append('fun findVariables(name: String, context: Set<Directive>?): List<Variable> {')
    lines.append('    val all = findVariables(name)')
    lines.append('    if (context == null) return all')
    lines.append('    val filtered = all.filter { it.context == any || it.context in context }')
    lines.append('    return if (filtered.isNotEmpty()) filtered else all')
    lines.append('}')
    lines.append('')
    lines.append('private fun initVariables(): List<Variable> =')
    for i, name in enumerate(short_names):
        suffix = ' +' if i < len(short_names) - 1 else ''
        lines.append(f'    {name}{suffix}')
    lines.append('')

    content = '\n'.join(lines)
    output_path = output_base / 'Common.kt'
    output_path.parent.mkdir(parents=True, exist_ok=True)
    output_path.write_text(content, encoding='utf-8')
    print(f'  Generated: Common.kt', file=sys.stderr)


def main():
    # Read JSON input
    if len(sys.argv) > 1:
        with open(sys.argv[1], encoding='utf-8') as f:
            data = json.load(f)
    else:
        data = json.load(sys.stdin)

    # Find catalog directories
    script_dir = Path(__file__).resolve().parent
    repo_root = script_dir.parent
    directive_catalog_dir = (
        repo_root / 'directive-catalog' / 'src' / 'main' / 'kotlin'
        / 'dev' / 'meanmail' / 'directives' / 'catalog'
    )
    variable_catalog_dir = (
        repo_root / 'directive-catalog' / 'src' / 'main' / 'kotlin'
        / 'dev' / 'meanmail' / 'variables' / 'catalog'
    )

    if not directive_catalog_dir.is_dir():
        print(
            f'Error: directive catalog directory not found: {directive_catalog_dir}',
            file=sys.stderr,
        )
        sys.exit(1)

    _build_directive_catalog_map(directive_catalog_dir)

    generated_count = 0
    skipped = []
    generated_modules = []

    for module_key, module_data in sorted(data.items()):
        if module_key not in _directive_catalog_paths:
            skipped.append(module_key)
            continue

        output_path = generate_module_file(module_key, module_data, variable_catalog_dir)
        if output_path:
            generated_count += 1
            generated_modules.append((module_key, module_data))
            print(f'  Generated: {output_path.name}', file=sys.stderr)

    # Generate Common.kt
    generate_common_kt(generated_modules, variable_catalog_dir)

    if skipped:
        print(
            f'\nSkipped {len(skipped)} modules without directive catalog entries:',
            file=sys.stderr,
        )
        for s in skipped:
            print(f'  {s}', file=sys.stderr)

    print(
        f'\nDone. Generated {generated_count} module files + Common.kt.',
        file=sys.stderr,
    )


if __name__ == '__main__':
    main()
