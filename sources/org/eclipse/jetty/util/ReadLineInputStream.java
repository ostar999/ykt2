package org.eclipse.jetty.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes9.dex */
public class ReadLineInputStream extends BufferedInputStream {
    boolean _seenCRLF;
    boolean _skipLF;

    public ReadLineInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override // java.io.BufferedInputStream, java.io.FilterInputStream, java.io.InputStream
    public synchronized int read() throws IOException {
        int i2;
        i2 = super.read();
        if (this._skipLF) {
            this._skipLF = false;
            if (this._seenCRLF && i2 == 10) {
                i2 = super.read();
            }
        }
        return i2;
    }

    public String readLine() throws IOException {
        mark(((BufferedInputStream) this).buf.length);
        while (true) {
            int i2 = super.read();
            if (i2 == -1) {
                int i3 = ((BufferedInputStream) this).markpos;
                ((BufferedInputStream) this).markpos = -1;
                if (((BufferedInputStream) this).pos > i3) {
                    return new String(((BufferedInputStream) this).buf, i3, ((BufferedInputStream) this).pos - i3, StringUtil.__UTF8_CHARSET);
                }
                return null;
            }
            if (i2 == 13) {
                int i4 = ((BufferedInputStream) this).pos;
                if (!this._seenCRLF || i4 >= ((BufferedInputStream) this).count) {
                    this._skipLF = true;
                } else {
                    byte[] bArr = ((BufferedInputStream) this).buf;
                    int i5 = ((BufferedInputStream) this).pos;
                    if (bArr[i5] == 10) {
                        ((BufferedInputStream) this).pos = i5 + 1;
                    }
                }
                int i6 = ((BufferedInputStream) this).markpos;
                ((BufferedInputStream) this).markpos = -1;
                return new String(((BufferedInputStream) this).buf, i6, (i4 - i6) - 1, StringUtil.__UTF8_CHARSET);
            }
            if (i2 == 10) {
                if (!this._skipLF) {
                    int i7 = ((BufferedInputStream) this).markpos;
                    ((BufferedInputStream) this).markpos = -1;
                    return new String(((BufferedInputStream) this).buf, i7, (((BufferedInputStream) this).pos - i7) - 1, StringUtil.__UTF8_CHARSET);
                }
                this._skipLF = false;
                this._seenCRLF = true;
                ((BufferedInputStream) this).markpos++;
            }
        }
    }

    public ReadLineInputStream(InputStream inputStream, int i2) {
        super(inputStream, i2);
    }

    @Override // java.io.BufferedInputStream, java.io.FilterInputStream, java.io.InputStream
    public synchronized int read(byte[] bArr, int i2, int i3) throws IOException {
        if (this._skipLF && i3 > 0) {
            this._skipLF = false;
            if (this._seenCRLF) {
                int i4 = super.read();
                if (i4 == -1) {
                    return -1;
                }
                if (i4 != 10) {
                    bArr[i2] = (byte) (i4 & 255);
                    return super.read(bArr, i2 + 1, i3 - 1) + 1;
                }
            }
        }
        return super.read(bArr, i2, i3);
    }
}
