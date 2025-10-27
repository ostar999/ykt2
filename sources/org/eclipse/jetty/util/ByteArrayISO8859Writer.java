package org.eclipse.jetty.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/* loaded from: classes9.dex */
public class ByteArrayISO8859Writer extends Writer {
    private ByteArrayOutputStream2 _bout;
    private byte[] _buf;
    private boolean _fixed;
    private int _size;
    private OutputStreamWriter _writer;

    public ByteArrayISO8859Writer() {
        this._bout = null;
        this._writer = null;
        this._fixed = false;
        this._buf = new byte[2048];
    }

    private void writeEncoded(char[] cArr, int i2, int i3) throws IOException {
        ByteArrayOutputStream2 byteArrayOutputStream2 = this._bout;
        if (byteArrayOutputStream2 == null) {
            this._bout = new ByteArrayOutputStream2(i3 * 2);
            this._writer = new OutputStreamWriter(this._bout, "ISO-8859-1");
        } else {
            byteArrayOutputStream2.reset();
        }
        this._writer.write(cArr, i2, i3);
        this._writer.flush();
        ensureSpareCapacity(this._bout.getCount());
        System.arraycopy(this._bout.getBuf(), 0, this._buf, this._size, this._bout.getCount());
        this._size += this._bout.getCount();
    }

    public int capacity() {
        return this._buf.length;
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    public void destroy() {
        this._buf = null;
    }

    public void ensureSpareCapacity(int i2) throws IOException {
        int i3 = this._size;
        int i4 = i3 + i2;
        byte[] bArr = this._buf;
        if (i4 > bArr.length) {
            if (this._fixed) {
                throw new IOException("Buffer overflow: " + this._buf.length);
            }
            byte[] bArr2 = new byte[((bArr.length + i2) * 4) / 3];
            System.arraycopy(bArr, 0, bArr2, 0, i3);
            this._buf = bArr2;
        }
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
    }

    public byte[] getBuf() {
        return this._buf;
    }

    public byte[] getByteArray() {
        int i2 = this._size;
        byte[] bArr = new byte[i2];
        System.arraycopy(this._buf, 0, bArr, 0, i2);
        return bArr;
    }

    public Object getLock() {
        return ((Writer) this).lock;
    }

    public void resetWriter() {
        this._size = 0;
    }

    public void setLength(int i2) {
        this._size = i2;
    }

    public int size() {
        return this._size;
    }

    public int spareCapacity() {
        return this._buf.length - this._size;
    }

    public void write(char c3) throws IOException {
        ensureSpareCapacity(1);
        if (c3 < 0 || c3 > 127) {
            writeEncoded(new char[]{c3}, 0, 1);
            return;
        }
        byte[] bArr = this._buf;
        int i2 = this._size;
        this._size = i2 + 1;
        bArr[i2] = (byte) c3;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(this._buf, 0, this._size);
    }

    @Override // java.io.Writer
    public void write(char[] cArr) throws IOException {
        ensureSpareCapacity(cArr.length);
        for (int i2 = 0; i2 < cArr.length; i2++) {
            char c3 = cArr[i2];
            if (c3 >= 0 && c3 <= 127) {
                byte[] bArr = this._buf;
                int i3 = this._size;
                this._size = i3 + 1;
                bArr[i3] = (byte) c3;
            } else {
                writeEncoded(cArr, i2, cArr.length - i2);
                return;
            }
        }
    }

    public ByteArrayISO8859Writer(int i2) {
        this._bout = null;
        this._writer = null;
        this._fixed = false;
        this._buf = new byte[i2];
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i2, int i3) throws IOException {
        ensureSpareCapacity(i3);
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = i2 + i4;
            char c3 = cArr[i5];
            if (c3 >= 0 && c3 <= 127) {
                byte[] bArr = this._buf;
                int i6 = this._size;
                this._size = i6 + 1;
                bArr[i6] = (byte) c3;
            } else {
                writeEncoded(cArr, i5, i3 - i4);
                return;
            }
        }
    }

    public ByteArrayISO8859Writer(byte[] bArr) {
        this._bout = null;
        this._writer = null;
        this._buf = bArr;
        this._fixed = true;
    }

    @Override // java.io.Writer
    public void write(String str) throws IOException {
        if (str == null) {
            write("null", 0, 4);
            return;
        }
        int length = str.length();
        ensureSpareCapacity(length);
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt >= 0 && cCharAt <= 127) {
                byte[] bArr = this._buf;
                int i3 = this._size;
                this._size = i3 + 1;
                bArr[i3] = (byte) cCharAt;
            } else {
                writeEncoded(str.toCharArray(), i2, length - i2);
                return;
            }
        }
    }

    @Override // java.io.Writer
    public void write(String str, int i2, int i3) throws IOException {
        ensureSpareCapacity(i3);
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = i2 + i4;
            char cCharAt = str.charAt(i5);
            if (cCharAt >= 0 && cCharAt <= 127) {
                byte[] bArr = this._buf;
                int i6 = this._size;
                this._size = i6 + 1;
                bArr[i6] = (byte) cCharAt;
            } else {
                writeEncoded(str.toCharArray(), i5, i3 - i4);
                return;
            }
        }
    }
}
