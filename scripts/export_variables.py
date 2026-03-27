#!/usr/bin/env python3
"""Export embedded variable definitions from nginx.org XML documentation.

Walks nginx.org/xml/en/docs/**/*_module.xml, parses <section id="variables">
elements, and outputs a JSON mapping of module -> variables with descriptions
and parameterized flags.

Usage:
    cd nginx.org && python3 ../scripts/export_variables.py | python3 -m json.tool
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


def _get_all_text(elem):
    """Get all text content of an element, ignoring tags."""
    return ''.join(elem.itertext())


_BLOCK_TAGS = {'para', 'example', 'list', 'note'}


def _render_example(elem):
    """Render an <example> element as an HTML code block."""
    text = _get_all_text(elem).strip('\n')
    return f'<pre><code>{text}</code></pre>'


def _render_block(elem):
    """Render an element's text content, collapsing whitespace."""
    if elem.tag == 'example':
        return _render_example(elem)
    text = _get_all_text(elem)
    return re.sub(r'\s+', ' ', text).strip()


def _render_description(tag_desc_elem, value_placeholder=None):
    """Render a <tag-desc> element to plain text.

    When value_placeholder is set, replaces <value> elements whose text
    matches it with {}, so the placeholder can be substituted with actual
    values in quick documentation.
    Preserves paragraph breaks between block-level elements (para, example,
    list, note). Inline elements within the same block are collapsed to a
    single line.
    """
    if value_placeholder:
        for value_elem in tag_desc_elem.iter('value'):
            if value_elem.text and value_elem.text.strip() == value_placeholder:
                value_elem.text = '{}'
                value_elem.tail = value_elem.tail or ''

    paragraphs = []

    # Leading text before the first block-level child
    leading_parts = [tag_desc_elem.text or '']
    for child in tag_desc_elem:
        if child.tag in _BLOCK_TAGS:
            # Flush leading/inter-block text
            leading = re.sub(r'\s+', ' ', ''.join(leading_parts)).strip()
            if leading:
                paragraphs.append(leading)
            leading_parts = []
            # Render the block element itself
            block_text = _render_block(child)
            if block_text:
                paragraphs.append(block_text)
            # Tail text after this block element
            leading_parts.append(child.tail or '')
        else:
            # Inline element — collect its text contribution
            leading_parts.append(_get_all_text(child))
            leading_parts.append(child.tail or '')

    # Flush any remaining text
    trailing = re.sub(r'\s+', ' ', ''.join(leading_parts)).strip()
    if trailing:
        paragraphs.append(trailing)

    return '\n\n'.join(paragraphs)


def _extract_variable_name(tag_name_elem):
    """Extract variable name, parameterized flag, and value placeholder.

    Returns (name, parameterized, value_text) where name has the $ prefix
    stripped, or (None, False, None) if this tag-name is not a variable
    (e.g. a status code literal nested inside a variable's description list).

    Parameterized variables like $arg_name have a <value> child in the
    <tag-name> element; we keep the trailing _ in the name. value_text is
    the text of the <value> child (e.g. "name") for matching in descriptions.
    """
    # Only <tag-name> entries containing a <var> child are actual variables.
    # Others (e.g. <literal>200</literal> status codes) are descriptive
    # sub-entries and should be skipped.
    var_elem = tag_name_elem.find('var')
    if var_elem is None:
        return None, False, None

    value_elem = tag_name_elem.find('value')
    has_value_child = value_elem is not None
    value_text = value_elem.text.strip() if value_elem is not None and value_elem.text else None
    name = _get_all_text(var_elem).strip()

    # Strip $ prefix
    if name.startswith('$'):
        name = name[1:]

    return name, has_value_child, value_text


def process_module_file(filepath):
    """Parse a module XML file and extract variable information.

    Returns (module_key, module_data) or None if no variables found.
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

    module_name = root.get('name', '')
    module_key = filepath.stem

    # Find the variables section
    variables = {}
    for section in root.iter('section'):
        if section.get('id') != 'variables':
            continue

        # Variables are in <tag-name>/<tag-desc> pairs inside <list type="tag">
        for list_elem in section.iter('list'):
            if list_elem.get('type') != 'tag':
                continue

            # Iterate children directly to pair tag-name with tag-desc
            children = list(list_elem)
            i = 0
            while i < len(children):
                child = children[i]
                if child.tag == 'tag-name':
                    name, parameterized, value_text = _extract_variable_name(child)
                    # Look for the next tag-desc sibling
                    description = ''
                    if i + 1 < len(children) and children[i + 1].tag == 'tag-desc':
                        description = _render_description(children[i + 1], value_text)
                        i += 1

                    if name:
                        variables[name] = {
                            'description': description,
                            'parameterized': parameterized,
                        }
                i += 1

    if not variables:
        return None

    # Infer context from module key pattern, falling back to directive contexts
    context = None
    if module_key.startswith('ngx_http_'):
        context = 'http'
    elif module_key.startswith('ngx_stream_'):
        context = 'stream'
    else:
        # Check <context> tags in directive sections
        contexts = {ctx.text for ctx in root.iter('context') if ctx.text}
        # http/server/location/if/limit_except are HTTP contexts
        http_contexts = {'http', 'server', 'location', 'if', 'limit_except'}
        stream_contexts = {'stream'}
        has_http = bool(contexts & http_contexts)
        has_stream = bool(contexts & stream_contexts)
        if has_http and not has_stream:
            context = 'http'
        elif has_stream and not has_http:
            context = 'stream'

    return module_key, {
        'name': module_name,
        'context': context,
        'variables': variables,
    }


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
        entry = process_module_file(xml_path)
        if entry:
            module_key, module_data = entry
            result[module_key] = module_data

    json.dump(result, sys.stdout, indent=2, ensure_ascii=False)
    sys.stdout.write('\n')


if __name__ == '__main__':
    main()
