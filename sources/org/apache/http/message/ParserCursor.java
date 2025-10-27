package org.apache.http.message;

import kotlin.text.Typography;
import org.apache.http.util.CharArrayBuffer;

/* loaded from: classes9.dex */
public class ParserCursor {
    private final int lowerBound;
    private int pos;
    private final int upperBound;

    public ParserCursor(int i2, int i3) {
        if (i2 < 0) {
            throw new IndexOutOfBoundsException("Lower bound cannot be negative");
        }
        if (i2 > i3) {
            throw new IndexOutOfBoundsException("Lower bound cannot be greater then upper bound");
        }
        this.lowerBound = i2;
        this.upperBound = i3;
        this.pos = i2;
    }

    public boolean atEnd() {
        return this.pos >= this.upperBound;
    }

    public int getLowerBound() {
        return this.lowerBound;
    }

    public int getPos() {
        return this.pos;
    }

    public int getUpperBound() {
        return this.upperBound;
    }

    public String toString() {
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(16);
        charArrayBuffer.append('[');
        charArrayBuffer.append(Integer.toString(this.lowerBound));
        charArrayBuffer.append(Typography.greater);
        charArrayBuffer.append(Integer.toString(this.pos));
        charArrayBuffer.append(Typography.greater);
        charArrayBuffer.append(Integer.toString(this.upperBound));
        charArrayBuffer.append(']');
        return charArrayBuffer.toString();
    }

    public void updatePos(int i2) {
        if (i2 < this.lowerBound) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("pos: ");
            stringBuffer.append(i2);
            stringBuffer.append(" < lowerBound: ");
            stringBuffer.append(this.lowerBound);
            throw new IndexOutOfBoundsException(stringBuffer.toString());
        }
        if (i2 <= this.upperBound) {
            this.pos = i2;
            return;
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("pos: ");
        stringBuffer2.append(i2);
        stringBuffer2.append(" > upperBound: ");
        stringBuffer2.append(this.upperBound);
        throw new IndexOutOfBoundsException(stringBuffer2.toString());
    }
}
