#!/usr/bin/env python3
"""Export module documentation URLs from nginx.org XML documentation.

Walks nginx.org/xml/en/docs/**/*_module.xml, extracts the ``link`` attribute
from each ``<module>`` root element, and outputs a JSON mapping of
module_key -> full URL.

The lua_nginx_module entry is hardcoded since it lives on GitHub, not nginx.org.

Usage:
    cd nginx.org && python3 ../scripts/export_urls.py > urls.json
"""

import json
import re
import sys
import xml.etree.ElementTree as ET
from pathlib import Path


def strip_doctype(xml_text):
    """Remove DOCTYPE declaration so ElementTree can parse without DTD."""
    xml_text = re.sub(
        r'<!DOCTYPE\s+[^>]*>', '', xml_text, count=1
    )
    entity_defs = (
        '<!DOCTYPE module [\n'
        '  <!ENTITY nbsp "&#xA0;">\n'
        '  <!ENTITY mdash "&#xA0;- ">\n'
        ']>\n'
    )
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


def extract_url(filepath):
    """Extract the documentation URL from a module XML file.

    Returns (module_key, url) or None.
    """
    try:
        raw = filepath.read_text(encoding='utf-8')
    except (OSError, UnicodeDecodeError):
        return None

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

    link = root.get('link')
    if not link:
        return None

    module_key = filepath.stem
    url = f'https://nginx.org{link}'
    return module_key, url


def main():
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
        entry = extract_url(xml_path)
        if entry:
            module_key, url = entry
            result[module_key] = url

    # Hardcode OpenResty lua-nginx-module (not in nginx.org XML)
    result['lua_nginx_module'] = (
        'https://github.com/openresty/lua-nginx-module'
        '/blob/master/README.markdown'
    )

    json.dump(result, sys.stdout, indent=2, ensure_ascii=False)
    sys.stdout.write('\n')


if __name__ == '__main__':
    main()
