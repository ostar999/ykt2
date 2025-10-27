package org.eclipse.jetty.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.util.StringUtil;

/* loaded from: classes9.dex */
public class ByteArrayBuffer extends AbstractBuffer {
    static final int MAX_WRITE = Integer.getInteger("org.eclipse.jetty.io.ByteArrayBuffer.MAX_WRITE", 131072).intValue();
    protected final byte[] _bytes;

    public static class CaseInsensitive extends ByteArrayBuffer implements Buffer.CaseInsensitve {
        public CaseInsensitive(String str) {
            super(str);
        }

        @Override // org.eclipse.jetty.io.ByteArrayBuffer, org.eclipse.jetty.io.AbstractBuffer
        public boolean equals(Object obj) {
            return (obj instanceof Buffer) && equalsIgnoreCase((Buffer) obj);
        }

        public CaseInsensitive(byte[] bArr, int i2, int i3, int i4) {
            super(bArr, i2, i3, i4);
        }
    }

    public ByteArrayBuffer(int i2, int i3, boolean z2) {
        this(new byte[i2], 0, 0, i3, z2);
    }

    @Override // org.eclipse.jetty.io.Buffer
    public byte[] array() {
        return this._bytes;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int capacity() {
        return this._bytes.length;
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public void compact() {
        if (isReadOnly()) {
            throw new IllegalStateException("READONLY");
        }
        int iMarkIndex = markIndex() >= 0 ? markIndex() : getIndex();
        if (iMarkIndex > 0) {
            int iPutIndex = putIndex() - iMarkIndex;
            if (iPutIndex > 0) {
                byte[] bArr = this._bytes;
                System.arraycopy(bArr, iMarkIndex, bArr, 0, iPutIndex);
            }
            if (markIndex() > 0) {
                setMarkIndex(markIndex() - iMarkIndex);
            }
            setGetIndex(getIndex() - iMarkIndex);
            setPutIndex(putIndex() - iMarkIndex);
        }
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer
    public boolean equals(Object obj) {
        int i2;
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof Buffer)) {
            return false;
        }
        if (obj instanceof Buffer.CaseInsensitve) {
            return equalsIgnoreCase((Buffer) obj);
        }
        Buffer buffer = (Buffer) obj;
        if (buffer.length() != length()) {
            return false;
        }
        int i3 = this._hash;
        if (i3 != 0 && (obj instanceof AbstractBuffer) && (i2 = ((AbstractBuffer) obj)._hash) != 0 && i3 != i2) {
            return false;
        }
        int index = getIndex();
        int iPutIndex = buffer.putIndex();
        int iPutIndex2 = putIndex();
        while (true) {
            int i4 = iPutIndex2 - 1;
            if (iPutIndex2 <= index) {
                return true;
            }
            iPutIndex--;
            if (this._bytes[i4] != buffer.peek(iPutIndex)) {
                return false;
            }
            iPutIndex2 = i4;
        }
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public boolean equalsIgnoreCase(Buffer buffer) {
        int i2;
        if (buffer == this) {
            return true;
        }
        if (buffer == null || buffer.length() != length()) {
            return false;
        }
        int i3 = this._hash;
        if (i3 != 0 && (buffer instanceof AbstractBuffer) && (i2 = ((AbstractBuffer) buffer)._hash) != 0 && i3 != i2) {
            return false;
        }
        int index = getIndex();
        int iPutIndex = buffer.putIndex();
        byte[] bArrArray = buffer.array();
        if (bArrArray != null) {
            int iPutIndex2 = putIndex();
            while (true) {
                int i4 = iPutIndex2 - 1;
                if (iPutIndex2 <= index) {
                    break;
                }
                byte b3 = this._bytes[i4];
                iPutIndex--;
                byte b4 = bArrArray[iPutIndex];
                if (b3 != b4) {
                    if (97 <= b3 && b3 <= 122) {
                        b3 = (byte) ((b3 - 97) + 65);
                    }
                    if (97 <= b4 && b4 <= 122) {
                        b4 = (byte) ((b4 - 97) + 65);
                    }
                    if (b3 != b4) {
                        return false;
                    }
                }
                iPutIndex2 = i4;
            }
        } else {
            int iPutIndex3 = putIndex();
            while (true) {
                int i5 = iPutIndex3 - 1;
                if (iPutIndex3 <= index) {
                    break;
                }
                byte b5 = this._bytes[i5];
                iPutIndex--;
                byte bPeek = buffer.peek(iPutIndex);
                if (b5 != bPeek) {
                    if (97 <= b5 && b5 <= 122) {
                        b5 = (byte) ((b5 - 97) + 65);
                    }
                    if (97 <= bPeek && bPeek <= 122) {
                        bPeek = (byte) ((bPeek - 97) + 65);
                    }
                    if (b5 != bPeek) {
                        return false;
                    }
                }
                iPutIndex3 = i5;
            }
        }
        return true;
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public byte get() {
        byte[] bArr = this._bytes;
        int i2 = this._get;
        this._get = i2 + 1;
        return bArr[i2];
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer
    public int hashCode() {
        if (this._hash == 0 || this._hashGet != this._get || this._hashPut != this._put) {
            int index = getIndex();
            int iPutIndex = putIndex();
            while (true) {
                int i2 = iPutIndex - 1;
                if (iPutIndex <= index) {
                    break;
                }
                byte b3 = this._bytes[i2];
                if (97 <= b3 && b3 <= 122) {
                    b3 = (byte) ((b3 - 97) + 65);
                }
                this._hash = (this._hash * 31) + b3;
                iPutIndex = i2;
            }
            if (this._hash == 0) {
                this._hash = -1;
            }
            this._hashGet = this._get;
            this._hashPut = this._put;
        }
        return this._hash;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public byte peek(int i2) {
        return this._bytes[i2];
    }

    @Override // org.eclipse.jetty.io.Buffer
    public void poke(int i2, byte b3) {
        this._bytes[i2] = b3;
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public int readFrom(InputStream inputStream, int i2) throws IOException {
        if (i2 < 0 || i2 > space()) {
            i2 = space();
        }
        int iPutIndex = putIndex();
        int i3 = 0;
        int i4 = i2;
        int i5 = 0;
        while (i3 < i2) {
            i5 = inputStream.read(this._bytes, iPutIndex, i4);
            if (i5 < 0) {
                break;
            }
            if (i5 > 0) {
                iPutIndex += i5;
                i3 += i5;
                i4 -= i5;
                setPutIndex(iPutIndex);
            }
            if (inputStream.available() <= 0) {
                break;
            }
        }
        if (i5 >= 0 || i3 != 0) {
            return i3;
        }
        return -1;
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public int space() {
        return this._bytes.length - this._put;
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public void writeTo(OutputStream outputStream) throws IOException {
        int length = length();
        int i2 = MAX_WRITE;
        if (i2 <= 0 || length <= i2) {
            outputStream.write(this._bytes, getIndex(), length);
        } else {
            int index = getIndex();
            while (length > 0) {
                int i3 = MAX_WRITE;
                if (length <= i3) {
                    i3 = length;
                }
                outputStream.write(this._bytes, index, i3);
                index += i3;
                length -= i3;
            }
        }
        if (isImmutable()) {
            return;
        }
        clear();
    }

    public ByteArrayBuffer(byte[] bArr) {
        this(bArr, 0, bArr.length, 2);
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int peek(int i2, byte[] bArr, int i3, int i4) {
        if ((i2 + i4 > capacity() && (i4 = capacity() - i2) == 0) || i4 < 0) {
            return -1;
        }
        System.arraycopy(this._bytes, i2, bArr, i3, i4);
        return i4;
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public int poke(int i2, Buffer buffer) {
        int i3 = 0;
        this._hash = 0;
        int length = buffer.length();
        if (i2 + length > capacity()) {
            length = capacity() - i2;
        }
        byte[] bArrArray = buffer.array();
        if (bArrArray != null) {
            System.arraycopy(bArrArray, buffer.getIndex(), this._bytes, i2, length);
        } else {
            int index = buffer.getIndex();
            while (i3 < length) {
                this._bytes[i2] = buffer.peek(index);
                i3++;
                i2++;
                index++;
            }
        }
        return length;
    }

    public ByteArrayBuffer(byte[] bArr, int i2, int i3) {
        this(bArr, i2, i3, 2);
    }

    public ByteArrayBuffer(byte[] bArr, int i2, int i3, int i4) {
        super(2, false);
        this._bytes = bArr;
        setPutIndex(i3 + i2);
        setGetIndex(i2);
        this._access = i4;
    }

    public ByteArrayBuffer(byte[] bArr, int i2, int i3, int i4, boolean z2) {
        super(2, z2);
        this._bytes = bArr;
        setPutIndex(i3 + i2);
        setGetIndex(i2);
        this._access = i4;
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public int poke(int i2, byte[] bArr, int i3, int i4) {
        this._hash = 0;
        if (i2 + i4 > capacity()) {
            i4 = capacity() - i2;
        }
        System.arraycopy(bArr, i3, this._bytes, i2, i4);
        return i4;
    }

    public ByteArrayBuffer(int i2) {
        this(new byte[i2], 0, 0, 2);
        setPutIndex(0);
    }

    public ByteArrayBuffer(String str) {
        super(2, false);
        byte[] bytes = StringUtil.getBytes(str);
        this._bytes = bytes;
        setGetIndex(0);
        setPutIndex(bytes.length);
        this._access = 0;
        this._string = str;
    }

    public ByteArrayBuffer(String str, boolean z2) {
        super(2, false);
        byte[] bytes = StringUtil.getBytes(str);
        this._bytes = bytes;
        setGetIndex(0);
        setPutIndex(bytes.length);
        if (z2) {
            this._access = 0;
            this._string = str;
        }
    }

    public ByteArrayBuffer(String str, String str2) throws UnsupportedEncodingException {
        super(2, false);
        byte[] bytes = str.getBytes(str2);
        this._bytes = bytes;
        setGetIndex(0);
        setPutIndex(bytes.length);
        this._access = 0;
        this._string = str;
    }
}
