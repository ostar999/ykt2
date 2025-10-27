package org.eclipse.jetty.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes9.dex */
public class MultiPartOutputStream extends FilterOutputStream {
    private String boundary;
    private byte[] boundaryBytes;
    private boolean inPart;
    private static final byte[] __CRLF = {13, 10};
    private static final byte[] __DASHDASH = {45, 45};
    public static String MULTIPART_MIXED = "multipart/mixed";
    public static String MULTIPART_X_MIXED_REPLACE = "multipart/x-mixed-replace";

    public MultiPartOutputStream(OutputStream outputStream) throws IOException {
        super(outputStream);
        this.inPart = false;
        String str = "jetty" + System.identityHashCode(this) + Long.toString(System.currentTimeMillis(), 36);
        this.boundary = str;
        this.boundaryBytes = str.getBytes("ISO-8859-1");
        this.inPart = false;
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.inPart) {
            ((FilterOutputStream) this).out.write(__CRLF);
        }
        OutputStream outputStream = ((FilterOutputStream) this).out;
        byte[] bArr = __DASHDASH;
        outputStream.write(bArr);
        ((FilterOutputStream) this).out.write(this.boundaryBytes);
        ((FilterOutputStream) this).out.write(bArr);
        ((FilterOutputStream) this).out.write(__CRLF);
        this.inPart = false;
        super.close();
    }

    public String getBoundary() {
        return this.boundary;
    }

    public OutputStream getOut() {
        return ((FilterOutputStream) this).out;
    }

    public void startPart(String str) throws IOException {
        if (this.inPart) {
            ((FilterOutputStream) this).out.write(__CRLF);
        }
        this.inPart = true;
        ((FilterOutputStream) this).out.write(__DASHDASH);
        ((FilterOutputStream) this).out.write(this.boundaryBytes);
        OutputStream outputStream = ((FilterOutputStream) this).out;
        byte[] bArr = __CRLF;
        outputStream.write(bArr);
        if (str != null) {
            ((FilterOutputStream) this).out.write(("Content-Type: " + str).getBytes("ISO-8859-1"));
        }
        ((FilterOutputStream) this).out.write(bArr);
        ((FilterOutputStream) this).out.write(bArr);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        ((FilterOutputStream) this).out.write(bArr, i2, i3);
    }

    public void startPart(String str, String[] strArr) throws IOException {
        if (this.inPart) {
            ((FilterOutputStream) this).out.write(__CRLF);
        }
        this.inPart = true;
        ((FilterOutputStream) this).out.write(__DASHDASH);
        ((FilterOutputStream) this).out.write(this.boundaryBytes);
        OutputStream outputStream = ((FilterOutputStream) this).out;
        byte[] bArr = __CRLF;
        outputStream.write(bArr);
        if (str != null) {
            ((FilterOutputStream) this).out.write(("Content-Type: " + str).getBytes("ISO-8859-1"));
        }
        ((FilterOutputStream) this).out.write(bArr);
        for (int i2 = 0; strArr != null && i2 < strArr.length; i2++) {
            ((FilterOutputStream) this).out.write(strArr[i2].getBytes("ISO-8859-1"));
            ((FilterOutputStream) this).out.write(__CRLF);
        }
        ((FilterOutputStream) this).out.write(__CRLF);
    }
}
