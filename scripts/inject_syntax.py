#!/usr/bin/env python3
"""Inject syntax lines into Kotlin directive catalog files.

Reads the JSON output of export_syntax.py and patches each catalog .kt file
to add ``syntax = listOf(...)`` after the ``description`` line of each
``Directive(`` call (skips ToggleDirective).

Usage:
    cd nginx.org && python3 ../scripts/export_syntax.py \
        | python3 ../scripts/inject_syntax.py

Or with a file argument:
    python3 scripts/inject_syntax.py syntax.json
"""

import json
import re
import sys
from pathlib import Path


def escape_kotlin_string(s: str) -> str:
    """Escape a string for use inside a Kotlin string literal ("...")."""
    s = s.replace('\\', '\\\\')
    s = s.replace('"', '\\"')
    s = s.replace('$', '\\$')
    return s


def build_syntax_arg(syntaxes: list[str], indent: str) -> str:
    """Build a Kotlin ``syntax = listOf(...)`` line."""
    if not syntaxes:
        return ''
    if len(syntaxes) == 1:
        escaped = escape_kotlin_string(syntaxes[0])
        return f'{indent}syntax = listOf("{escaped}"),'
    lines = [f'{indent}syntax = listOf(']
    for i, syn in enumerate(syntaxes):
        escaped = escape_kotlin_string(syn)
        comma = ',' if i < len(syntaxes) - 1 else ''
        lines.append(f'{indent}    "{escaped}"{comma}')
    lines.append(f'{indent}),')
    return '\n'.join(lines)


def build_lookup(data: dict) -> dict:
    """Build (module_key, directive_name) -> list[syntax_html] lookup."""
    lookup = {}
    for module_key, module_data in data.items():
        for dir_name, dir_data in module_data.get('directives', {}).items():
            syntaxes = dir_data.get('syntax', [])
            if syntaxes:
                lookup[(module_key, dir_name)] = syntaxes
    return lookup


def find_module_key(filepath: Path) -> str | None:
    """Derive the module key from a catalog .kt filename."""
    return filepath.stem


def inject_file(filepath: Path, lookup: dict, module_key: str) -> bool:
    """Inject syntax lines into a single .kt file. Returns True if modified."""
    text = filepath.read_text(encoding='utf-8')
    lines = text.split('\n')
    result = []
    modified = False
    i = 0

    while i < len(lines):
        line = lines[i]
        result.append(line)

        # Detect start of a Directive( call (not ToggleDirective)
        stripped = line.strip()
        if _is_directive_start(stripped):
            i += 1
            directive_name = None
            description_found = False

            while i < len(lines):
                cur = lines[i]
                cur_stripped = cur.strip()

                # Bail out before appending — outer loop will handle this line
                if (cur_stripped.startswith('parameters') or
                        cur_stripped.startswith('context') or
                        cur_stripped.startswith('module') or
                        cur_stripped.startswith('syntax') or
                        cur_stripped == ')'):
                    break

                result.append(cur)

                # Detect directive name: named or positional
                name_detected_this_line = False
                if directive_name is None:
                    name_match = re.match(r'name\s*=\s*"([^"]+)"', cur_stripped)
                    if name_match:
                        directive_name = name_match.group(1)
                        name_detected_this_line = True
                    else:
                        pos_match = re.match(r'^"([^"]+)"\s*,?\s*$', cur_stripped)
                        if pos_match:
                            directive_name = pos_match.group(1)
                            name_detected_this_line = True

                # Detect description: named or positional (2nd bare string)
                is_description = cur_stripped.startswith('description')
                if (not is_description and directive_name
                        and not description_found
                        and not name_detected_this_line):
                    # Positional description: bare string after name
                    is_description = bool(
                        re.match(r'^"', cur_stripped) and
                        not re.match(r'name\s*=', cur_stripped)
                    )

                if is_description:
                    description_found = True
                    desc_line = cur

                    # Detect triple-quoted string (odd number of """ on line)
                    if '"""' in cur_stripped and cur_stripped.count('"""') % 2 == 1:
                        # Scan to closing """
                        i += 1
                        while i < len(lines):
                            cur = lines[i]
                            result.append(cur)
                            desc_line = cur
                            if '"""' in lines[i]:
                                break
                            i += 1
                        # After closing """, there may be .trimIndent(), on the
                        # same line — scan until the trailing comma
                        if not cur.rstrip().endswith(','):
                            i += 1
                            while i < len(lines):
                                cur = lines[i]
                                result.append(cur)
                                desc_line = cur
                                if cur.rstrip().endswith(','):
                                    break
                                i += 1
                    elif not _line_closes_string_arg(cur):
                        i += 1
                        while i < len(lines):
                            cur = lines[i]
                            result.append(cur)
                            desc_line = cur
                            if _line_closes_string_arg(cur):
                                break
                            i += 1

                    # Check if the NEXT line already has syntax =
                    next_idx = i + 1
                    already_has_syntax = (
                        next_idx < len(lines) and
                        lines[next_idx].strip().startswith('syntax')
                    )

                    # Inject syntax after description if applicable
                    if directive_name and not already_has_syntax:
                        key = (module_key, directive_name)
                        syntaxes = lookup.get(key)
                        if syntaxes:
                            indent = _get_indent(desc_line)
                            syntax_line = build_syntax_arg(syntaxes, indent)
                            if syntax_line:
                                result.append(syntax_line)
                                modified = True
                    i += 1
                    break

                i += 1
            continue

        i += 1

    if modified:
        filepath.write_text('\n'.join(result), encoding='utf-8')
    return modified


def _is_directive_start(stripped: str) -> bool:
    """Check if a stripped line starts a Directive( constructor call."""
    # Match patterns like:
    #   val foo = Directive(
    #   Directive(
    # But NOT ToggleDirective(
    if 'ToggleDirective(' in stripped:
        return False
    if 'Directive(' in stripped and 'class ' not in stripped:
        return True
    return False


def _line_closes_string_arg(line: str) -> bool:
    """Check if a line contains the closing of a string argument.

    Looks for a pattern like: ...description...",  or  ...text..."  followed
    by optional comma/whitespace at end of line.
    """
    stripped = line.rstrip()
    # Must end with ", or just "
    return bool(re.search(r'"\s*,?\s*$', stripped))


def _get_indent(line: str) -> str:
    """Extract leading whitespace from a line."""
    match = re.match(r'^(\s*)', line)
    return match.group(1) if match else ''


def main():
    # Read JSON input
    if len(sys.argv) > 1:
        with open(sys.argv[1], encoding='utf-8') as f:
            data = json.load(f)
    else:
        data = json.load(sys.stdin)

    lookup = build_lookup(data)

    # Find catalog directory
    script_dir = Path(__file__).resolve().parent
    repo_root = script_dir.parent
    catalog_dir = repo_root / 'directive-catalog' / 'src' / 'main' / 'kotlin' / 'dev' / 'meanmail' / 'directives' / 'catalog'

    if not catalog_dir.is_dir():
        print(f'Error: catalog directory not found: {catalog_dir}', file=sys.stderr)
        sys.exit(1)

    modified_count = 0
    for kt_file in sorted(catalog_dir.rglob('*.kt')):
        if kt_file.name == 'Common.kt':
            continue
        module_key = find_module_key(kt_file)
        if module_key and any(k[0] == module_key for k in lookup):
            if inject_file(kt_file, lookup, module_key):
                modified_count += 1
                print(f'  Modified: {kt_file.name}')

    print(f'\nDone. Modified {modified_count} files.')


if __name__ == '__main__':
    main()
