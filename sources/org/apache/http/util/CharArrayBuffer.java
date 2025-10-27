package org.apache.http.util;

import java.io.Serializable;
import org.apache.http.protocol.HTTP;

/* loaded from: classes9.dex */
public final class CharArrayBuffer implements Serializable {
    private static final long serialVersionUID = -6208952725094867135L;
    private char[] buffer;
    private int len;

    public CharArrayBuffer(int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Buffer capacity may not be negative");
        }
        this.buffer = new char[i2];
    }

    private void expand(int i2) {
        char[] cArr = new char[Math.max(this.buffer.length << 1, i2)];
        System.arraycopy(this.buffer, 0, cArr, 0, this.len);
        this.buffer = cArr;
    }

    public void append(char[] cArr, int i2, int i3) {
        int i4;
        if (cArr == null) {
            return;
        }
        if (i2 < 0 || i2 > cArr.length || i3 < 0 || (i4 = i2 + i3) < 0 || i4 > cArr.length) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("off: ");
            stringBuffer.append(i2);
            stringBuffer.append(" len: ");
            stringBuffer.append(i3);
            stringBuffer.append(" b.length: ");
            stringBuffer.append(cArr.length);
            throw new IndexOutOfBoundsException(stringBuffer.toString());
        }
        if (i3 == 0) {
            return;
        }
        int i5 = this.len + i3;
        if (i5 > this.buffer.length) {
            expand(i5);
        }
        System.arraycopy(cArr, i2, this.buffer, this.len, i3);
        this.len = i5;
    }

    public char[] buffer() {
        return this.buffer;
    }

    public int capacity() {
        return this.buffer.length;
    }

    public char charAt(int i2) {
        return this.buffer[i2];
    }

    public void clear() {
        this.len = 0;
    }

    public void ensureCapacity(int i2) {
        if (i2 <= 0) {
            return;
        }
        int length = this.buffer.length;
        int i3 = this.len;
        if (i2 > length - i3) {
            expand(i3 + i2);
        }
    }

    public int indexOf(int i2, int i3, int i4) {
        if (i3 < 0) {
            i3 = 0;
        }
        int i5 = this.len;
        if (i4 > i5) {
            i4 = i5;
        }
        if (i3 > i4) {
            return -1;
        }
        while (i3 < i4) {
            if (this.buffer[i3] == i2) {
                return i3;
            }
            i3++;
        }
        return -1;
    }

    public boolean isEmpty() {
        return this.len == 0;
    }

    public boolean isFull() {
        return this.len == this.buffer.length;
    }

    public int length() {
        return this.len;
    }

    public void setLength(int i2) {
        if (i2 >= 0 && i2 <= this.buffer.length) {
            this.len = i2;
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("len: ");
        stringBuffer.append(i2);
        stringBuffer.append(" < 0 or > buffer len: ");
        stringBuffer.append(this.buffer.length);
        throw new IndexOutOfBoundsException(stringBuffer.toString());
    }

    public String substring(int i2, int i3) {
        return new String(this.buffer, i2, i3 - i2);
    }

    public String substringTrimmed(int i2, int i3) {
        if (i2 < 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Negative beginIndex: ");
            stringBuffer.append(i2);
            throw new IndexOutOfBoundsException(stringBuffer.toString());
        }
        if (i3 > this.len) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("endIndex: ");
            stringBuffer2.append(i3);
            stringBuffer2.append(" > length: ");
            stringBuffer2.append(this.len);
            throw new IndexOutOfBoundsException(stringBuffer2.toString());
        }
        if (i2 > i3) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("beginIndex: ");
            stringBuffer3.append(i2);
            stringBuffer3.append(" > endIndex: ");
            stringBuffer3.append(i3);
            throw new IndexOutOfBoundsException(stringBuffer3.toString());
        }
        while (i2 < i3 && HTTP.isWhitespace(this.buffer[i2])) {
            i2++;
        }
        while (i3 > i2 && HTTP.isWhitespace(this.buffer[i3 - 1])) {
            i3--;
        }
        return new String(this.buffer, i2, i3 - i2);
    }

    public char[] toCharArray() {
        int i2 = this.len;
        char[] cArr = new char[i2];
        if (i2 > 0) {
            System.arraycopy(this.buffer, 0, cArr, 0, i2);
        }
        return cArr;
    }

    public String toString() {
        return new String(this.buffer, 0, this.len);
    }

    public int indexOf(int i2) {
        return indexOf(i2, 0, this.len);
    }

    public void append(String str) {
        if (str == null) {
            str = "null";
        }
        int length = str.length();
        int i2 = this.len + length;
        if (i2 > this.buffer.length) {
            expand(i2);
        }
        str.getChars(0, length, this.buffer, this.len);
        this.len = i2;
    }

    public void append(CharArrayBuffer charArrayBuffer, int i2, int i3) {
        if (charArrayBuffer == null) {
            return;
        }
        append(charArrayBuffer.buffer, i2, i3);
    }

    public void append(CharArrayBuffer charArrayBuffer) {
        if (charArrayBuffer == null) {
            return;
        }
        append(charArrayBuffer.buffer, 0, charArrayBuffer.len);
    }

    public void append(char c3) {
        int i2 = this.len + 1;
        if (i2 > this.buffer.length) {
            expand(i2);
        }
        this.buffer[this.len] = c3;
        this.len = i2;
    }

    public void append(byte[] bArr, int i2, int i3) {
        int i4;
        if (bArr == null) {
            return;
        }
        if (i2 < 0 || i2 > bArr.length || i3 < 0 || (i4 = i2 + i3) < 0 || i4 > bArr.length) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("off: ");
            stringBuffer.append(i2);
            stringBuffer.append(" len: ");
            stringBuffer.append(i3);
            stringBuffer.append(" b.length: ");
            stringBuffer.append(bArr.length);
            throw new IndexOutOfBoundsException(stringBuffer.toString());
        }
        if (i3 == 0) {
            return;
        }
        int i5 = this.len;
        int i6 = i3 + i5;
        if (i6 > this.buffer.length) {
            expand(i6);
        }
        while (i5 < i6) {
            this.buffer[i5] = (char) (bArr[i2] & 255);
            i2++;
            i5++;
        }
        this.len = i6;
    }

    public void append(ByteArrayBuffer byteArrayBuffer, int i2, int i3) {
        if (byteArrayBuffer == null) {
            return;
        }
        append(byteArrayBuffer.buffer(), i2, i3);
    }

    public void append(Object obj) {
        append(String.valueOf(obj));
    }
}
