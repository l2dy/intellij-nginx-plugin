#!/usr/bin/env python3
"""Inject documentation URLs into Kotlin directive catalog files.

Reads a JSON mapping of module_key -> URL (from export_urls.py) and patches
each catalog .kt file to add ``url = "..."`` to the ``NginxModule(`` constructor
call, before the closing parenthesis.

Usage:
    cd nginx.org && python3 ../scripts/export_urls.py \
        | python3 ../scripts/inject_urls.py

Or with a file argument:
    python3 scripts/inject_urls.py urls.json
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


def inject_file(filepath: Path, url: str) -> bool:
    """Inject url = "..." into the NginxModule( call in a .kt file.

    Strategy: find the NginxModule( constructor call, track parens to find
    its closing ), and insert url = "..." as the last argument.

    Returns True if the file was modified.
    """
    text = filepath.read_text(encoding='utf-8')
    lines = text.split('\n')
    result = []
    modified = False
    i = 0

    while i < len(lines):
        line = lines[i]

        # Detect NginxModule( constructor call
        if 'NginxModule(' in line and 'class ' not in line:
            # Check if url is already present in the NginxModule block
            # by scanning ahead to find the closing paren
            block_lines = [line]
            depth = line.count('(') - line.count(')')
            j = i + 1
            while j < len(lines) and depth > 0:
                block_lines.append(lines[j])
                depth += lines[j].count('(') - lines[j].count(')')
                j += 1

            # Check if url already present
            block_text = '\n'.join(block_lines)
            if 'url =' in block_text:
                # Already has url, skip
                for bl in block_lines:
                    result.append(bl)
                i = j
                continue

            # Re-scan to find the closing paren and inject url before it
            result.append(line)
            depth = line.count('(') - line.count(')')
            i += 1
            while i < len(lines) and depth > 0:
                cur = lines[i]
                cur_depth = cur.count('(') - cur.count(')')
                new_depth = depth + cur_depth

                if new_depth == 0:
                    # This line contains the closing paren of NginxModule(
                    # Get indent from last argument line, not the closing paren
                    indent = _get_last_arg_indent(result)
                    # Ensure previous result line has trailing comma
                    _ensure_trailing_comma(result)
                    result.append(
                        f'{indent}url = "{escape_kotlin_string(url)}"'
                    )
                    modified = True
                    result.append(cur)
                    i += 1
                    break

                result.append(cur)
                depth = new_depth
                i += 1
            continue

        result.append(line)
        i += 1

    if modified:
        filepath.write_text('\n'.join(result), encoding='utf-8')
    return modified


def _get_indent(line: str) -> str:
    """Extract leading whitespace from a line."""
    match = re.match(r'^(\s*)', line)
    return match.group(1) if match else ''


def _get_last_arg_indent(lines: list[str]) -> str:
    """Get indent from the last non-empty line in result (an argument line)."""
    for idx in range(len(lines) - 1, -1, -1):
        stripped = lines[idx].strip()
        if stripped:
            return _get_indent(lines[idx])
    return '    '


def _ensure_trailing_comma(lines: list[str]) -> None:
    """Ensure the last non-empty line ends with a comma."""
    for idx in range(len(lines) - 1, -1, -1):
        stripped = lines[idx].rstrip()
        if stripped:
            if not stripped.endswith(','):
                lines[idx] = stripped + ','
            break


def main():
    # Read JSON input
    if len(sys.argv) > 1:
        with open(sys.argv[1], encoding='utf-8') as f:
            data = json.load(f)
    else:
        data = json.load(sys.stdin)

    # Find catalog directory
    script_dir = Path(__file__).resolve().parent
    repo_root = script_dir.parent
    catalog_dir = (
        repo_root / 'directive-catalog' / 'src' / 'main' / 'kotlin'
        / 'dev' / 'meanmail' / 'directives' / 'catalog'
    )

    if not catalog_dir.is_dir():
        print(
            f'Error: catalog directory not found: {catalog_dir}',
            file=sys.stderr,
        )
        sys.exit(1)

    modified_count = 0
    for kt_file in sorted(catalog_dir.rglob('*.kt')):
        if kt_file.name == 'Common.kt':
            continue
        module_key = kt_file.stem
        url = data.get(module_key)
        if url:
            if inject_file(kt_file, url):
                modified_count += 1
                print(f'  Modified: {kt_file.name}')

    print(f'\nDone. Modified {modified_count} files.')


if __name__ == '__main__':
    main()
