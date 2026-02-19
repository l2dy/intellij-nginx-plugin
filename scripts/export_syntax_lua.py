#!/usr/bin/env python3
"""Export directive syntax from lua-nginx-module README.markdown.

Parses **syntax:** lines from the Directives section and outputs JSON
compatible with inject_syntax.py.

Usage:
    python3 scripts/export_syntax_lua.py lua-nginx-module/README.markdown \
        | python3 scripts/inject_syntax.py
"""

import json
import re
import sys
from pathlib import Path


def extract_directives_section(text: str) -> str:
    """Extract the Directives section, excluding Nginx API for Lua."""
    # Find "Directives\n==========" heading
    start_match = re.search(r'^Directives\n=+', text, re.MULTILINE)
    if not start_match:
        print('Error: could not find Directives section', file=sys.stderr)
        sys.exit(1)

    # Find "Nginx API for Lua\n=================" heading
    end_match = re.search(
        r'^Nginx API for Lua\n=+', text, re.MULTILINE
    )
    if not end_match:
        print('Error: could not find Nginx API for Lua section',
              file=sys.stderr)
        sys.exit(1)

    return text[start_match.start():end_match.start()]


def parse_syntax_lines(section: str) -> dict[str, list[str]]:
    """Parse **syntax:** *<content>* lines grouped by directive heading.

    Returns {directive_name: [syntax_content, ...]}.
    """
    directives: dict[str, list[str]] = {}
    current_directive = None

    for line in section.split('\n'):
        # Detect directive heading: a line of dashes following a name
        # The directive name is on the previous non-blank line.
        # We track the last non-blank, non-heading line as a candidate.
        pass

    # Re-approach: scan for directive headings (name followed by --- line)
    # and **syntax:** lines
    lines = section.split('\n')
    current_directive = None
    i = 0
    while i < len(lines):
        line = lines[i]
        stripped = line.strip()

        # Check if this line is a heading underline (---+)
        if stripped and all(c == '-' for c in stripped) and len(stripped) >= 3:
            # Previous non-blank line is the directive name
            j = i - 1
            while j >= 0 and not lines[j].strip():
                j -= 1
            if j >= 0:
                current_directive = lines[j].strip()

        # Match **syntax:** *<content>*
        m = re.match(r'\*\*syntax:\*\*\s*\*(.+)\*\s*$', stripped)
        if m and current_directive:
            raw = m.group(1)
            html = convert_syntax_to_html(current_directive, raw)
            directives.setdefault(current_directive, []).append(html)

        i += 1

    return directives


def convert_syntax_to_html(directive_name: str, raw: str) -> str:
    """Convert markdown syntax content to HTML format.

    Follows the same conventions as the nginx.org XML export:
    - First word (directive name) -> <b>name</b>
    - <param> angle-bracket placeholders -> <i>param</i>
    - $var references -> <i>$var</i>
    - { lua-script } / { lua-script-str } -> { ... }
    - \\[ / \\] -> [ / ] (unescape markdown)
    - Bare parameter words -> <i>word</i>
    - Block directives (containing { ... }) don't get semicolons
    - Statement directives end with ;
    """
    # Unescape markdown backslash escapes for brackets
    raw = raw.replace('\\[', '[')
    raw = raw.replace('\\]', ']')

    # Replace { lua-script } and { lua-script-str } with { ... }
    is_block = bool(re.search(r'\{\s*lua-script(?:-str)?\s*\}', raw))
    raw = re.sub(r'\{\s*lua-script(?:-str)?\s*\}', '{ ... }', raw)

    # Tokenize
    tokens = tokenize(raw)

    # Build HTML
    parts = []
    first_word = True
    for token in tokens:
        if first_word:
            # The first word is the directive name
            parts.append(f'<b>{escape_html(token)}</b>')
            first_word = False
            continue

        if token in ('|', '...', '{', '}', '(', ')'):
            parts.append(escape_html(token))
        elif token.startswith('<') and token.endswith('>'):
            # Angle-bracket placeholder like <size>
            inner = token[1:-1]
            parts.append(f'<i>{escape_html(inner)}</i>')
        elif token.startswith('&lt;') and token.endswith('&gt;'):
            # Already-escaped angle brackets
            inner = token[4:-4]
            parts.append(f'<i>{escape_html(inner)}</i>')
        elif token.startswith('$'):
            # Variable reference
            parts.append(f'<i>{escape_html(token)}</i>')
        elif token in ('on', 'off') or token.endswith(';'):
            parts.append(escape_html(token))
        elif token.startswith('[') and token.endswith(']'):
            # Bracket group - process contents
            inner = token[1:-1]
            parts.append('[' + process_bracket_contents(inner) + ']')
        else:
            # Bare parameter word -> italicize
            parts.append(f'<i>{escape_html(token)}</i>')

    result = ' '.join(parts)
    # Normalize whitespace
    result = re.sub(r'\s+', ' ', result).strip()

    # Add terminator
    if is_block:
        # Already has { ... } in the text, no semicolon needed
        pass
    else:
        result += ';'

    return result


def tokenize(raw: str) -> list[str]:
    """Split syntax content into tokens."""
    tokens = []
    i = 0
    s = raw

    while i < len(s):
        # Skip whitespace
        if s[i].isspace():
            i += 1
            continue

        # HTML entity &lt; ... &gt;
        if s[i:i+4] == '&lt;':
            end = s.find('&gt;', i)
            if end != -1:
                tokens.append(s[i:end+4])
                i = end + 4
                continue

        # Angle bracket placeholder <...>
        if s[i] == '<':
            end = s.find('>', i)
            if end != -1:
                tokens.append(s[i:end+1])
                i = end + 1
                continue

        # Bracket group [...]
        if s[i] == '[':
            end = s.find(']', i)
            if end != -1:
                tokens.append(s[i:end+1])
                i = end + 1
                continue

        # Special single chars
        if s[i] in ('{', '}', '(', ')'):
            tokens.append(s[i])
            i += 1
            continue

        # | operator
        if s[i] == '|':
            tokens.append('|')
            i += 1
            continue

        # ... ellipsis
        if s[i:i+3] == '...':
            tokens.append('...')
            i += 3
            continue

        # Word (contiguous non-whitespace, non-special)
        j = i
        while j < len(s) and not s[j].isspace() and s[j] not in '<>[]{}|':
            # Stop before &lt;
            if s[j] == '&' and s[j:j+4] == '&lt;':
                break
            j += 1
        if j > i:
            tokens.append(s[i:j])
            i = j
        else:
            # Fallback: skip one char
            tokens.append(s[i])
            i += 1

    return tokens


def process_bracket_contents(inner: str) -> str:
    """Process contents inside [...] brackets."""
    # If it contains $, process variable references
    parts = []
    for word in inner.split():
        if word.startswith('$'):
            parts.append(f'<i>{escape_html(word)}</i>')
        elif word == '...':
            parts.append('...')
        else:
            parts.append(escape_html(word))
    return ' '.join(parts)


def escape_html(text: str) -> str:
    """Escape HTML special characters."""
    return (text
            .replace('&', '&amp;')
            .replace('<', '&lt;')
            .replace('>', '&gt;'))


def main():
    if len(sys.argv) < 2:
        print(
            'Usage: python3 export_syntax_lua.py <README.markdown>',
            file=sys.stderr,
        )
        sys.exit(1)

    readme_path = Path(sys.argv[1])
    if not readme_path.is_file():
        print(f'Error: file not found: {readme_path}', file=sys.stderr)
        sys.exit(1)

    text = readme_path.read_text(encoding='utf-8')
    section = extract_directives_section(text)
    directives = parse_syntax_lines(section)

    if not directives:
        print('Warning: no directives found', file=sys.stderr)
        sys.exit(1)

    # Build output in the format expected by inject_syntax.py
    result = {
        'lua_nginx_module': {
            'name': 'lua_nginx_module',
            'directives': {
                name: {'syntax': syntaxes}
                for name, syntaxes in directives.items()
            },
        }
    }

    json.dump(result, sys.stdout, indent=2, ensure_ascii=False)
    sys.stdout.write('\n')


if __name__ == '__main__':
    main()
