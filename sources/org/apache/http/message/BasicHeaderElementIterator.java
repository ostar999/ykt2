package org.apache.http.message;

import java.util.NoSuchElementException;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HeaderIterator;
import org.apache.http.ParseException;
import org.apache.http.util.CharArrayBuffer;

/* loaded from: classes9.dex */
public class BasicHeaderElementIterator implements HeaderElementIterator {
    private CharArrayBuffer buffer;
    private HeaderElement currentElement;
    private ParserCursor cursor;
    private final HeaderIterator headerIt;
    private final HeaderValueParser parser;

    public BasicHeaderElementIterator(HeaderIterator headerIterator, HeaderValueParser headerValueParser) {
        this.currentElement = null;
        this.buffer = null;
        this.cursor = null;
        if (headerIterator == null) {
            throw new IllegalArgumentException("Header iterator may not be null");
        }
        if (headerValueParser == null) {
            throw new IllegalArgumentException("Parser may not be null");
        }
        this.headerIt = headerIterator;
        this.parser = headerValueParser;
    }

    private void bufferHeaderValue() {
        this.cursor = null;
        this.buffer = null;
        while (this.headerIt.hasNext()) {
            Header headerNextHeader = this.headerIt.nextHeader();
            if (headerNextHeader instanceof FormattedHeader) {
                FormattedHeader formattedHeader = (FormattedHeader) headerNextHeader;
                CharArrayBuffer buffer = formattedHeader.getBuffer();
                this.buffer = buffer;
                ParserCursor parserCursor = new ParserCursor(0, buffer.length());
                this.cursor = parserCursor;
                parserCursor.updatePos(formattedHeader.getValuePos());
                return;
            }
            String value = headerNextHeader.getValue();
            if (value != null) {
                CharArrayBuffer charArrayBuffer = new CharArrayBuffer(value.length());
                this.buffer = charArrayBuffer;
                charArrayBuffer.append(value);
                this.cursor = new ParserCursor(0, this.buffer.length());
                return;
            }
        }
    }

    private void parseNextElement() throws ParseException {
        HeaderElement headerElement;
        loop0: while (true) {
            if (!this.headerIt.hasNext() && this.cursor == null) {
                return;
            }
            ParserCursor parserCursor = this.cursor;
            if (parserCursor == null || parserCursor.atEnd()) {
                bufferHeaderValue();
            }
            if (this.cursor != null) {
                while (!this.cursor.atEnd()) {
                    headerElement = this.parser.parseHeaderElement(this.buffer, this.cursor);
                    if (headerElement.getName().length() != 0 || headerElement.getValue() != null) {
                        break loop0;
                    }
                }
                if (this.cursor.atEnd()) {
                    this.cursor = null;
                    this.buffer = null;
                }
            }
        }
        this.currentElement = headerElement;
    }

    @Override // org.apache.http.HeaderElementIterator, java.util.Iterator
    public boolean hasNext() throws ParseException {
        if (this.currentElement == null) {
            parseNextElement();
        }
        return this.currentElement != null;
    }

    @Override // java.util.Iterator
    public final Object next() throws NoSuchElementException {
        return nextElement();
    }

    @Override // org.apache.http.HeaderElementIterator
    public HeaderElement nextElement() throws ParseException, NoSuchElementException {
        if (this.currentElement == null) {
            parseNextElement();
        }
        HeaderElement headerElement = this.currentElement;
        if (headerElement == null) {
            throw new NoSuchElementException("No more header elements available");
        }
        this.currentElement = null;
        return headerElement;
    }

    @Override // java.util.Iterator
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Remove not supported");
    }

    public BasicHeaderElementIterator(HeaderIterator headerIterator) {
        this(headerIterator, BasicHeaderValueParser.DEFAULT);
    }
}
