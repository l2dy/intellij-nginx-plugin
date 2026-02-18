#!/usr/bin/env python3
"""Export directive syntax from nginx.org XML documentation.

Walks nginx.org/xml/en/docs/**/*.xml, parses <directive> elements,
and outputs a JSON mapping of module -> directives with syntax, default,
and context information.

Usage:
    cd nginx.org && python3 ../scripts/export_syntax.py | python3 -m json.tool
"""

import json
import re
import sys
import xml.etree.ElementTree as ET
from pathlib import Path


def strip_doctype(xml_text):
    """Remove DOCTYPE declaration so ElementTree can parse without DTD.

    Also pre-defines &nbsp; and &mdash; entities that the DTD normally provides.
    """
    # Remove DOCTYPE line(s) — may span multiple lines
    xml_text = re.sub(
        r'<!DOCTYPE\s+[^>]*>', '', xml_text, count=1
    )
    # Inject entity definitions right after the XML declaration
    entity_defs = (
        '<!DOCTYPE module [\n'
        '  <!ENTITY nbsp "&#xA0;">\n'
        '  <!ENTITY mdash "&#xA0;- ">\n'
        ']>\n'
    )
    # Insert after <?xml ...?> if present, otherwise prepend
    if xml_text.lstrip().startswith('<?xml'):
        xml_text = re.sub(
            r'(<\?xml[^?]*\?>)',
            r'\1\n' + entity_defs,
            xml_text,
            count=1,
        )
    else:
        xml_text = entity_defs + xml_text
    return xml_text


def _escape_html(text):
    """Escape HTML special characters in plain text."""
    return text.replace('&', '&amp;').replace('<', '&lt;').replace('>', '&gt;')


def render_syntax_element(directive_name, syntax_elem):
    """Render a <syntax> element to an HTML string.

    Follows nginx.org's directive.xslt rendering conventions:
    - Directive name -> <b>name</b>
    - <value>X</value> -> <i>X</i>
    - <literal>X</literal> -> X (plain text)
    - Raw text -> HTML-escaped as-is
    - block="yes" -> append " { ... }" instead of ";"
    """
    is_block = syntax_elem.get('block', 'no') == 'yes'

    parts = []
    _collect_syntax_parts(syntax_elem, parts)

    body = ''.join(parts)
    # Normalize whitespace (but preserve HTML tags)
    body = re.sub(r'\s+', ' ', body).strip()

    if body:
        syntax_str = f'<b>{_escape_html(directive_name)}</b> {body}'
    else:
        syntax_str = f'<b>{_escape_html(directive_name)}</b>'

    if is_block:
        syntax_str += ' { ... }'
    else:
        syntax_str += ';'

    return syntax_str


def _collect_syntax_parts(elem, parts):
    """Recursively collect HTML parts from a <syntax> element."""
    if elem.text:
        parts.append(_escape_html(elem.text))
    for child in elem:
        if child.tag == 'value':
            text = _get_all_text(child)
            parts.append(f'<i>{_escape_html(text)}</i>')
        elif child.tag == 'literal':
            _collect_literal_parts(child, parts)
        else:
            parts.append(_escape_html(_get_all_text(child)))
        if child.tail:
            parts.append(_escape_html(child.tail))


def _collect_literal_parts(elem, parts):
    """Collect HTML from a <literal>, which may contain <value> children."""
    if elem.text:
        parts.append(_escape_html(elem.text))
    for child in elem:
        if child.tag == 'value':
            text = _get_all_text(child)
            parts.append(f'<i>{_escape_html(text)}</i>')
        else:
            parts.append(_escape_html(_get_all_text(child)))
        if child.tail:
            parts.append(_escape_html(child.tail))


def _get_all_text(elem):
    """Get all text content of an element, ignoring tags."""
    return ''.join(elem.itertext())


def render_default(default_elem):
    """Render a <default> element. Returns None for empty <default/>."""
    text = _get_all_text(default_elem).strip()
    return text if text else None


def process_module_file(filepath):
    """Parse a module XML file and extract directive information.

    Returns (module_key, module_data) or None if not a module file.
    """
    try:
        raw = filepath.read_text(encoding='utf-8')
    except (OSError, UnicodeDecodeError):
        return None

    # Skip non-module files early (article, book, etc.)
    if '<!DOCTYPE module' not in raw:
        return None

    xml_text = strip_doctype(raw)

    try:
        root = ET.fromstring(xml_text)
    except ET.ParseError:
        print(f'Warning: failed to parse {filepath}', file=sys.stderr)
        return None

    if root.tag != 'module':
        return None

    module_name = root.get('name', '')
    # Derive module key from the file stem (e.g. ngx_http_rewrite_module)
    module_key = filepath.stem

    directives = {}
    for directive_elem in root.iter('directive'):
        dir_name = directive_elem.get('name', '')

        # Collect syntax lines
        syntaxes = []
        for syntax_elem in directive_elem.findall('syntax'):
            syntaxes.append(render_syntax_element(dir_name, syntax_elem))

        # Collect default
        default_elem = directive_elem.find('default')
        default_val = render_default(default_elem) if default_elem is not None else None

        # Collect contexts
        contexts = []
        for ctx_elem in directive_elem.findall('context'):
            ctx_text = (ctx_elem.text or '').strip()
            contexts.append(ctx_text)

        directives[dir_name] = {
            'syntax': syntaxes,
            'default': default_val,
            'contexts': contexts,
        }

    if not directives:
        return None

    return module_key, {
        'name': module_name,
        'directives': directives,
    }


def main():
    # Determine base directory: expect to run from nginx.org/
    base = Path('xml/en/docs')
    if not base.is_dir():
        print(
            'Error: expected to run from the nginx.org/ directory '
            '(xml/en/docs/ not found)',
            file=sys.stderr,
        )
        sys.exit(1)

    result = {}
    for xml_path in sorted(base.rglob('*_module.xml')):
        entry = process_module_file(xml_path)
        if entry:
            module_key, module_data = entry
            result[module_key] = module_data

    json.dump(result, sys.stdout, indent=2, ensure_ascii=False)
    sys.stdout.write('\n')


if __name__ == '__main__':
    main()
