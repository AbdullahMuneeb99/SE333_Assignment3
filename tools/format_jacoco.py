import re
from xml.dom import minidom

src = 'target/site/jacoco/jacoco.xml'
out1 = 'target/site/jacoco/jacoco.xml'
out2 = 'jacoco-formatted.xml'

with open(src, 'r', encoding='utf-8') as f:
    text = f.read()

# Try to extract DOCTYPE (if any)
doctype_match = re.search(r'(!DOCTYPE[^>]*>)', text)
doctype_text = None
if doctype_match:
    # include the <! at the beginning
    start = text.rfind('<!DOCTYPE', 0, doctype_match.start())
    if start != -1:
        # find end of DOCTYPE
        m = re.search(r'<\!DOCTYPE[^>]*>', text[start:])
        if m:
            doctype_text = m.group(0)

# Parse and pretty print
try:
    dom = minidom.parseString(text)
    pretty = dom.toprettyxml(indent='  ', encoding='utf-8')
    # minidom returns bytes when encoding specified
    if isinstance(pretty, bytes):
        pretty = pretty.decode('utf-8')
    # Ensure only one XML declaration
    pretty = re.sub(r'^\s*<\?xml[^>]*>\s*', '<?xml version="1.0" encoding="UTF-8"?>\n', pretty)

    # Insert DOCTYPE right after XML declaration if we saved one
    if doctype_text:
        # Put doctype on its own line after declaration
        pretty = pretty.replace('<?xml version="1.0" encoding="UTF-8"?>\n', '<?xml version="1.0" encoding="UTF-8"?>\n' + doctype_text + '\n', 1)

    # Write both files
    with open(out1, 'w', encoding='utf-8') as f:
        f.write(pretty)
    with open(out2, 'w', encoding='utf-8') as f:
        f.write(pretty)
    print('Formatted and wrote:', out1, 'and', out2)
except Exception as e:
    print('Error formatting XML:', e)
    raise
