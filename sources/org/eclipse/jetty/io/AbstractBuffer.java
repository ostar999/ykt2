package org.eclipse.jetty.io;

import cn.hutool.core.text.StrPool;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public abstract class AbstractBuffer implements Buffer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected static final String __IMMUTABLE = "IMMUTABLE";
    protected static final String __READONLY = "READONLY";
    protected static final String __READWRITE = "READWRITE";
    protected static final String __VOLATILE = "VOLATILE";
    protected int _access;
    protected int _get;
    protected int _hash;
    protected int _hashGet;
    protected int _hashPut;
    protected int _mark;
    protected int _put;
    protected String _string;
    protected View _view;
    protected boolean _volatile;
    private static final Logger LOG = Log.getLogger((Class<?>) AbstractBuffer.class);
    private static final boolean __boundsChecking = Boolean.getBoolean("org.eclipse.jetty.io.AbstractBuffer.boundsChecking");

    public AbstractBuffer(int i2, boolean z2) {
        if (i2 == 0 && z2) {
            throw new IllegalArgumentException("IMMUTABLE && VOLATILE");
        }
        setMarkIndex(-1);
        this._access = i2;
        this._volatile = z2;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public byte[] asArray() {
        int length = length();
        byte[] bArr = new byte[length];
        byte[] bArrArray = array();
        if (bArrArray != null) {
            System.arraycopy(bArrArray, getIndex(), bArr, 0, length);
        } else {
            peek(getIndex(), bArr, 0, length());
        }
        return bArr;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public Buffer asImmutableBuffer() {
        return isImmutable() ? this : duplicate(0);
    }

    @Override // org.eclipse.jetty.io.Buffer
    public Buffer asMutableBuffer() {
        if (!isImmutable()) {
            return this;
        }
        Buffer buffer = buffer();
        return buffer.isReadOnly() ? duplicate(2) : new View(buffer, markIndex(), getIndex(), putIndex(), this._access);
    }

    @Override // org.eclipse.jetty.io.Buffer
    public Buffer asNonVolatileBuffer() {
        return !isVolatile() ? this : duplicate(this._access);
    }

    @Override // org.eclipse.jetty.io.Buffer
    public Buffer asReadOnlyBuffer() {
        return isReadOnly() ? this : new View(this, markIndex(), getIndex(), putIndex(), 1);
    }

    @Override // org.eclipse.jetty.io.Buffer
    public Buffer buffer() {
        return this;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public void clear() {
        setMarkIndex(-1);
        setGetIndex(0);
        setPutIndex(0);
    }

    @Override // org.eclipse.jetty.io.Buffer
    public void compact() {
        if (isReadOnly()) {
            throw new IllegalStateException(__READONLY);
        }
        int iMarkIndex = markIndex() >= 0 ? markIndex() : getIndex();
        if (iMarkIndex > 0) {
            byte[] bArrArray = array();
            int iPutIndex = putIndex() - iMarkIndex;
            if (iPutIndex > 0) {
                if (bArrArray != null) {
                    System.arraycopy(array(), iMarkIndex, array(), 0, iPutIndex);
                } else {
                    poke(0, peek(iMarkIndex, iPutIndex));
                }
            }
            if (markIndex() > 0) {
                setMarkIndex(markIndex() - iMarkIndex);
            }
            setGetIndex(getIndex() - iMarkIndex);
            setPutIndex(putIndex() - iMarkIndex);
        }
    }

    public ByteArrayBuffer duplicate(int i2) {
        return ((this instanceof Buffer.CaseInsensitve) || (buffer() instanceof Buffer.CaseInsensitve)) ? new ByteArrayBuffer.CaseInsensitive(asArray(), 0, length(), i2) : new ByteArrayBuffer(asArray(), 0, length(), i2);
    }

    public boolean equals(Object obj) {
        int i2;
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof Buffer)) {
            return false;
        }
        Buffer buffer = (Buffer) obj;
        if ((this instanceof Buffer.CaseInsensitve) || (buffer instanceof Buffer.CaseInsensitve)) {
            return equalsIgnoreCase(buffer);
        }
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
            if (peek(i4) != buffer.peek(iPutIndex)) {
                return false;
            }
            iPutIndex2 = i4;
        }
    }

    @Override // org.eclipse.jetty.io.Buffer
    public boolean equalsIgnoreCase(Buffer buffer) {
        int i2;
        if (buffer == this) {
            return true;
        }
        if (buffer.length() != length()) {
            return false;
        }
        int i3 = this._hash;
        if (i3 != 0 && (buffer instanceof AbstractBuffer) && (i2 = ((AbstractBuffer) buffer)._hash) != 0 && i3 != i2) {
            return false;
        }
        int index = getIndex();
        int iPutIndex = buffer.putIndex();
        byte[] bArrArray = array();
        byte[] bArrArray2 = buffer.array();
        if (bArrArray != null && bArrArray2 != null) {
            int iPutIndex2 = putIndex();
            while (true) {
                int i4 = iPutIndex2 - 1;
                if (iPutIndex2 <= index) {
                    break;
                }
                byte b3 = bArrArray[i4];
                iPutIndex--;
                byte b4 = bArrArray2[iPutIndex];
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
                byte bPeek = peek(i5);
                iPutIndex--;
                byte bPeek2 = buffer.peek(iPutIndex);
                if (bPeek != bPeek2) {
                    if (97 <= bPeek && bPeek <= 122) {
                        bPeek = (byte) ((bPeek - 97) + 65);
                    }
                    if (97 <= bPeek2 && bPeek2 <= 122) {
                        bPeek2 = (byte) ((bPeek2 - 97) + 65);
                    }
                    if (bPeek != bPeek2) {
                        return false;
                    }
                }
                iPutIndex3 = i5;
            }
        }
        return true;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public byte get() {
        int i2 = this._get;
        this._get = i2 + 1;
        return peek(i2);
    }

    @Override // org.eclipse.jetty.io.Buffer
    public final int getIndex() {
        return this._get;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public boolean hasContent() {
        return this._put > this._get;
    }

    public int hashCode() {
        if (this._hash == 0 || this._hashGet != this._get || this._hashPut != this._put) {
            int index = getIndex();
            byte[] bArrArray = array();
            if (bArrArray != null) {
                int iPutIndex = putIndex();
                while (true) {
                    int i2 = iPutIndex - 1;
                    if (iPutIndex <= index) {
                        break;
                    }
                    byte b3 = bArrArray[i2];
                    if (97 <= b3 && b3 <= 122) {
                        b3 = (byte) ((b3 - 97) + 65);
                    }
                    this._hash = (this._hash * 31) + b3;
                    iPutIndex = i2;
                }
            } else {
                int iPutIndex2 = putIndex();
                while (true) {
                    int i3 = iPutIndex2 - 1;
                    if (iPutIndex2 <= index) {
                        break;
                    }
                    byte bPeek = peek(i3);
                    if (97 <= bPeek && bPeek <= 122) {
                        bPeek = (byte) ((bPeek - 97) + 65);
                    }
                    this._hash = (this._hash * 31) + bPeek;
                    iPutIndex2 = i3;
                }
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
    public boolean isImmutable() {
        return this._access <= 0;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public boolean isReadOnly() {
        return this._access <= 1;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public boolean isVolatile() {
        return this._volatile;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int length() {
        return this._put - this._get;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public void mark() {
        setMarkIndex(this._get - 1);
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int markIndex() {
        return this._mark;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public byte peek() {
        return peek(this._get);
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int poke(int i2, Buffer buffer) {
        int i3 = 0;
        this._hash = 0;
        int length = buffer.length();
        if (i2 + length > capacity()) {
            length = capacity() - i2;
        }
        byte[] bArrArray = buffer.array();
        byte[] bArrArray2 = array();
        if (bArrArray != null && bArrArray2 != null) {
            System.arraycopy(bArrArray, buffer.getIndex(), bArrArray2, i2, length);
        } else if (bArrArray != null) {
            int index = buffer.getIndex();
            while (i3 < length) {
                poke(i2, bArrArray[index]);
                i3++;
                i2++;
                index++;
            }
        } else if (bArrArray2 != null) {
            int index2 = buffer.getIndex();
            while (i3 < length) {
                bArrArray2[i2] = buffer.peek(index2);
                i3++;
                i2++;
                index2++;
            }
        } else {
            int index3 = buffer.getIndex();
            while (i3 < length) {
                poke(i2, buffer.peek(index3));
                i3++;
                i2++;
                index3++;
            }
        }
        return length;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int put(Buffer buffer) {
        int iPutIndex = putIndex();
        int iPoke = poke(iPutIndex, buffer);
        setPutIndex(iPutIndex + iPoke);
        return iPoke;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public final int putIndex() {
        return this._put;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int readFrom(InputStream inputStream, int i2) throws IOException {
        byte[] bArrArray = array();
        int iSpace = space();
        if (iSpace <= i2) {
            i2 = iSpace;
        }
        if (bArrArray != null) {
            int i3 = inputStream.read(bArrArray, this._put, i2);
            if (i3 > 0) {
                this._put += i3;
            }
            return i3;
        }
        int i4 = i2 <= 1024 ? i2 : 1024;
        byte[] bArr = new byte[i4];
        while (i2 > 0) {
            int i5 = inputStream.read(bArr, 0, i4);
            if (i5 < 0) {
                return -1;
            }
            put(bArr, 0, i5);
            i2 -= i5;
        }
        return 0;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public void reset() {
        if (markIndex() >= 0) {
            setGetIndex(markIndex());
        }
    }

    public void rewind() {
        setGetIndex(0);
        setMarkIndex(-1);
    }

    @Override // org.eclipse.jetty.io.Buffer
    public void setGetIndex(int i2) {
        this._get = i2;
        this._hash = 0;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public void setMarkIndex(int i2) {
        this._mark = i2;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public void setPutIndex(int i2) {
        this._put = i2;
        this._hash = 0;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int skip(int i2) {
        if (length() < i2) {
            i2 = length();
        }
        setGetIndex(getIndex() + i2);
        return i2;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public Buffer slice() {
        return peek(getIndex(), length());
    }

    @Override // org.eclipse.jetty.io.Buffer
    public Buffer sliceFromMark() {
        return sliceFromMark((getIndex() - markIndex()) - 1);
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int space() {
        return capacity() - this._put;
    }

    public String toDebugString() {
        return getClass() + "@" + super.hashCode();
    }

    @Override // org.eclipse.jetty.io.Buffer
    public String toDetailString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StrPool.BRACKET_START);
        sb.append(super.hashCode());
        sb.append(",");
        sb.append(buffer().hashCode());
        sb.append(",m=");
        sb.append(markIndex());
        sb.append(",g=");
        sb.append(getIndex());
        sb.append(",p=");
        sb.append(putIndex());
        sb.append(",c=");
        sb.append(capacity());
        sb.append("]={");
        if (markIndex() >= 0) {
            for (int iMarkIndex = markIndex(); iMarkIndex < getIndex(); iMarkIndex++) {
                TypeUtil.toHex(peek(iMarkIndex), (Appendable) sb);
            }
            sb.append("}{");
        }
        int index = getIndex();
        int i2 = 0;
        while (index < putIndex()) {
            TypeUtil.toHex(peek(index), (Appendable) sb);
            int i3 = i2 + 1;
            if (i2 == 50 && putIndex() - index > 20) {
                sb.append(" ... ");
                index = putIndex() - 20;
            }
            index++;
            i2 = i3;
        }
        sb.append('}');
        return sb.toString();
    }

    public String toString() {
        if (!isImmutable()) {
            return new String(asArray(), 0, length());
        }
        if (this._string == null) {
            this._string = new String(asArray(), 0, length());
        }
        return this._string;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public void writeTo(OutputStream outputStream) throws IOException {
        byte[] bArrArray = array();
        if (bArrArray != null) {
            outputStream.write(bArrArray, getIndex(), length());
        } else {
            int length = length();
            int i2 = length <= 1024 ? length : 1024;
            byte[] bArr = new byte[i2];
            int i3 = this._get;
            while (length > 0) {
                int iPeek = peek(i3, bArr, 0, length > i2 ? i2 : length);
                outputStream.write(bArr, 0, iPeek);
                i3 += iPeek;
                length -= iPeek;
            }
        }
        clear();
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int get(byte[] bArr, int i2, int i3) {
        int index = getIndex();
        int length = length();
        if (length == 0) {
            return -1;
        }
        if (i3 > length) {
            i3 = length;
        }
        int iPeek = peek(index, bArr, i2, i3);
        if (iPeek > 0) {
            setGetIndex(index + iPeek);
        }
        return iPeek;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public void mark(int i2) {
        setMarkIndex(this._get + i2);
    }

    @Override // org.eclipse.jetty.io.Buffer
    public Buffer peek(int i2, int i3) {
        View view = this._view;
        if (view == null) {
            this._view = new View(this, -1, i2, i2 + i3, isReadOnly() ? 1 : 2);
        } else {
            view.update(buffer());
            this._view.setMarkIndex(-1);
            this._view.setGetIndex(0);
            this._view.setPutIndex(i3 + i2);
            this._view.setGetIndex(i2);
        }
        return this._view;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public Buffer sliceFromMark(int i2) {
        if (markIndex() < 0) {
            return null;
        }
        Buffer bufferPeek = peek(markIndex(), i2);
        setMarkIndex(-1);
        return bufferPeek;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public void put(byte b3) {
        int iPutIndex = putIndex();
        poke(iPutIndex, b3);
        setPutIndex(iPutIndex + 1);
    }

    @Override // org.eclipse.jetty.io.Buffer
    public Buffer get(int i2) {
        int index = getIndex();
        Buffer bufferPeek = peek(index, i2);
        setGetIndex(index + i2);
        return bufferPeek;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public String toString(String str) {
        try {
            byte[] bArrArray = array();
            if (bArrArray != null) {
                return new String(bArrArray, getIndex(), length(), str);
            }
            return new String(asArray(), 0, length(), str);
        } catch (Exception e2) {
            LOG.warn(e2);
            return new String(asArray(), 0, length());
        }
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int put(byte[] bArr, int i2, int i3) {
        int iPutIndex = putIndex();
        int iPoke = poke(iPutIndex, bArr, i2, i3);
        setPutIndex(iPutIndex + iPoke);
        return iPoke;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int put(byte[] bArr) {
        int iPutIndex = putIndex();
        int iPoke = poke(iPutIndex, bArr, 0, bArr.length);
        setPutIndex(iPutIndex + iPoke);
        return iPoke;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public String toString(Charset charset) {
        try {
            byte[] bArrArray = array();
            if (bArrArray != null) {
                return new String(bArrArray, getIndex(), length(), charset);
            }
            return new String(asArray(), 0, length(), charset);
        } catch (Exception e2) {
            LOG.warn(e2);
            return new String(asArray(), 0, length());
        }
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int poke(int i2, byte[] bArr, int i3, int i4) {
        int i5 = 0;
        this._hash = 0;
        if (i2 + i4 > capacity()) {
            i4 = capacity() - i2;
        }
        byte[] bArrArray = array();
        if (bArrArray != null) {
            System.arraycopy(bArr, i3, bArrArray, i2, i4);
        } else {
            while (i5 < i4) {
                poke(i2, bArr[i3]);
                i5++;
                i2++;
                i3++;
            }
        }
        return i4;
    }
}
