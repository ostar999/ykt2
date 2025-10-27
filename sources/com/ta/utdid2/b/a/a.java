package com.ta.utdid2.b.a;

import com.caverock.androidsvg.SVGParser;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import kotlin.text.Typography;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes6.dex */
class a implements XmlSerializer {

    /* renamed from: a, reason: collision with root package name */
    private static final String[] f17239a = {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "&quot;", null, null, null, "&amp;", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "&lt;", null, "&gt;", null};

    /* renamed from: a, reason: collision with other field name */
    private OutputStream f81a;

    /* renamed from: a, reason: collision with other field name */
    private Writer f82a;

    /* renamed from: a, reason: collision with other field name */
    private CharsetEncoder f84a;

    /* renamed from: i, reason: collision with root package name */
    private boolean f17241i;
    private int mPos;

    /* renamed from: b, reason: collision with root package name */
    private final char[] f17240b = new char[8192];

    /* renamed from: a, reason: collision with other field name */
    private ByteBuffer f83a = ByteBuffer.allocate(8192);

    private void a(String str, int i2, int i3) throws IOException {
        if (i3 > 8192) {
            int i4 = i3 + i2;
            while (i2 < i4) {
                int i5 = i2 + 8192;
                a(str, i2, i5 < i4 ? 8192 : i4 - i2);
                i2 = i5;
            }
            return;
        }
        int i6 = this.mPos;
        if (i6 + i3 > 8192) {
            flush();
            i6 = this.mPos;
        }
        str.getChars(i2, i2 + i3, this.f17240b, i6);
        this.mPos = i6 + i3;
    }

    private void append(char c3) throws IOException {
        int i2 = this.mPos;
        if (i2 >= 8191) {
            flush();
            i2 = this.mPos;
        }
        this.f17240b[i2] = c3;
        this.mPos = i2 + 1;
    }

    private void d(String str) throws IOException {
        String str2;
        int length = str.length();
        String[] strArr = f17239a;
        char length2 = (char) strArr.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            char cCharAt = str.charAt(i2);
            if (cCharAt < length2 && (str2 = strArr[cCharAt]) != null) {
                if (i3 < i2) {
                    a(str, i3, i2 - i3);
                }
                i3 = i2 + 1;
                append(str2);
            }
            i2++;
        }
        if (i3 < i2) {
            a(str, i3, i2 - i3);
        }
    }

    private void g() throws IOException {
        int iPosition = this.f83a.position();
        if (iPosition > 0) {
            this.f83a.flip();
            this.f81a.write(this.f83a.array(), 0, iPosition);
            this.f83a.clear();
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer attribute(String str, String str2, String str3) throws IllegalStateException, IOException, IllegalArgumentException {
        append(' ');
        if (str != null) {
            append(str);
            append(':');
        }
        append(str2);
        append("=\"");
        d(str3);
        append('\"');
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void cdsect(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void comment(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void docdecl(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void endDocument() throws IllegalStateException, IOException, IllegalArgumentException {
        flush();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer endTag(String str, String str2) throws IllegalStateException, IOException, IllegalArgumentException {
        if (this.f17241i) {
            append(" />\n");
        } else {
            append("</");
            if (str != null) {
                append(str);
                append(':');
            }
            append(str2);
            append(">\n");
        }
        this.f17241i = false;
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void entityRef(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void flush() throws IOException {
        int i2 = this.mPos;
        if (i2 > 0) {
            if (this.f81a != null) {
                CharBuffer charBufferWrap = CharBuffer.wrap(this.f17240b, 0, i2);
                CoderResult coderResultEncode = this.f84a.encode(charBufferWrap, this.f83a, true);
                while (!coderResultEncode.isError()) {
                    if (coderResultEncode.isOverflow()) {
                        g();
                        coderResultEncode = this.f84a.encode(charBufferWrap, this.f83a, true);
                    } else {
                        g();
                        this.f81a.flush();
                    }
                }
                throw new IOException(coderResultEncode.toString());
            }
            this.f82a.write(this.f17240b, 0, i2);
            this.f82a.flush();
            this.mPos = 0;
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public int getDepth() {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public boolean getFeature(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getNamespace() {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getPrefix(String str, boolean z2) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public Object getProperty(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void ignorableWhitespace(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void processingInstruction(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setFeature(String str, boolean z2) throws IllegalStateException, IllegalArgumentException {
        if (!str.equals("http://xmlpull.org/v1/doc/features.html#indent-output")) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(OutputStream outputStream, String str) throws IllegalStateException, IOException, IllegalArgumentException {
        if (outputStream == null) {
            throw new IllegalArgumentException();
        }
        try {
            this.f84a = Charset.forName(str).newEncoder();
            this.f81a = outputStream;
        } catch (IllegalCharsetNameException e2) {
            throw ((UnsupportedEncodingException) new UnsupportedEncodingException(str).initCause(e2));
        } catch (UnsupportedCharsetException e3) {
            throw ((UnsupportedEncodingException) new UnsupportedEncodingException(str).initCause(e3));
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setPrefix(String str, String str2) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setProperty(String str, Object obj) throws IllegalStateException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void startDocument(String str, Boolean bool) throws IllegalStateException, IOException, IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version='1.0' encoding='utf-8' standalone='");
        sb.append(bool.booleanValue() ? "yes" : SVGParser.XML_STYLESHEET_ATTR_ALTERNATE_NO);
        sb.append("' ?>\n");
        append(sb.toString());
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer startTag(String str, String str2) throws IllegalStateException, IOException, IllegalArgumentException {
        if (this.f17241i) {
            append(">\n");
        }
        append(Typography.less);
        if (str != null) {
            append(str);
            append(':');
        }
        append(str2);
        this.f17241i = true;
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer text(char[] cArr, int i2, int i3) throws IllegalStateException, IOException, IllegalArgumentException {
        if (this.f17241i) {
            append(">");
            this.f17241i = false;
        }
        a(cArr, i2, i3);
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer text(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        if (this.f17241i) {
            append(">");
            this.f17241i = false;
        }
        d(str);
        return this;
    }

    private void append(char[] cArr, int i2, int i3) throws IOException {
        if (i3 > 8192) {
            int i4 = i3 + i2;
            while (i2 < i4) {
                int i5 = i2 + 8192;
                append(cArr, i2, i5 < i4 ? 8192 : i4 - i2);
                i2 = i5;
            }
            return;
        }
        int i6 = this.mPos;
        if (i6 + i3 > 8192) {
            flush();
            i6 = this.mPos;
        }
        System.arraycopy(cArr, i2, this.f17240b, i6, i3);
        this.mPos = i6 + i3;
    }

    private void a(char[] cArr, int i2, int i3) throws IOException {
        String str;
        String[] strArr = f17239a;
        char length = (char) strArr.length;
        int i4 = i3 + i2;
        int i5 = i2;
        while (i2 < i4) {
            char c3 = cArr[i2];
            if (c3 < length && (str = strArr[c3]) != null) {
                if (i5 < i2) {
                    append(cArr, i5, i2 - i5);
                }
                i5 = i2 + 1;
                append(str);
            }
            i2++;
        }
        if (i5 < i2) {
            append(cArr, i5, i2 - i5);
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(Writer writer) throws IllegalStateException, IOException, IllegalArgumentException {
        this.f82a = writer;
    }

    private void append(String str) throws IOException {
        a(str, 0, str.length());
    }
}
