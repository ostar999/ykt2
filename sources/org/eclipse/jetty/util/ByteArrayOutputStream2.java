package org.eclipse.jetty.util;

import java.io.ByteArrayOutputStream;

/* loaded from: classes9.dex */
public class ByteArrayOutputStream2 extends ByteArrayOutputStream {
    public ByteArrayOutputStream2() {
    }

    public byte[] getBuf() {
        return ((ByteArrayOutputStream) this).buf;
    }

    public int getCount() {
        return ((ByteArrayOutputStream) this).count;
    }

    public void reset(int i2) {
        reset();
        if (((ByteArrayOutputStream) this).buf.length < i2) {
            ((ByteArrayOutputStream) this).buf = new byte[i2];
        }
    }

    public void setCount(int i2) {
        ((ByteArrayOutputStream) this).count = i2;
    }

    public void writeUnchecked(int i2) {
        byte[] bArr = ((ByteArrayOutputStream) this).buf;
        int i3 = ((ByteArrayOutputStream) this).count;
        ((ByteArrayOutputStream) this).count = i3 + 1;
        bArr[i3] = (byte) i2;
    }

    public ByteArrayOutputStream2(int i2) {
        super(i2);
    }
}
