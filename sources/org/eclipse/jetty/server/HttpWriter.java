package org.eclipse.jetty.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.eclipse.jetty.http.AbstractGenerator;
import org.eclipse.jetty.util.ByteArrayOutputStream2;

/* loaded from: classes9.dex */
public class HttpWriter extends Writer {
    public static final int MAX_OUTPUT_CHARS = 512;
    private static final int WRITE_CONV = 0;
    private static final int WRITE_ISO1 = 1;
    private static final int WRITE_UTF8 = 2;
    final AbstractGenerator _generator;
    final HttpOutput _out;
    int _surrogate = 0;
    int _writeMode;

    public HttpWriter(HttpOutput httpOutput) {
        this._out = httpOutput;
        this._generator = httpOutput._generator;
    }

    private Writer getConverter() throws IOException {
        HttpOutput httpOutput = this._out;
        if (httpOutput._converter == null) {
            HttpOutput httpOutput2 = this._out;
            httpOutput._converter = new OutputStreamWriter(httpOutput2._bytes, httpOutput2._characterEncoding);
        }
        return this._out._converter;
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this._out.close();
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() throws IOException {
        this._out.flush();
    }

    public void setCharacterEncoding(String str) {
        if (str == null || "ISO-8859-1".equalsIgnoreCase(str)) {
            this._writeMode = 1;
        } else if ("UTF-8".equalsIgnoreCase(str)) {
            this._writeMode = 2;
        } else {
            this._writeMode = 0;
            String str2 = this._out._characterEncoding;
            if (str2 == null || !str2.equalsIgnoreCase(str)) {
                this._out._converter = null;
            }
        }
        HttpOutput httpOutput = this._out;
        httpOutput._characterEncoding = str;
        if (httpOutput._bytes == null) {
            httpOutput._bytes = new ByteArrayOutputStream2(512);
        }
    }

    @Override // java.io.Writer
    public void write(String str, int i2, int i3) throws IOException {
        while (i3 > 512) {
            write(str, i2, 512);
            i2 += 512;
            i3 -= 512;
        }
        HttpOutput httpOutput = this._out;
        if (httpOutput._chars == null) {
            httpOutput._chars = new char[512];
        }
        char[] cArr = httpOutput._chars;
        str.getChars(i2, i2 + i3, cArr, 0);
        write(cArr, 0, i3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0178 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0175 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.io.OutputStream, org.eclipse.jetty.server.HttpOutput] */
    @Override // java.io.Writer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void write(char[] r12, int r13, int r14) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 458
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.HttpWriter.write(char[], int, int):void");
    }
}
