package org.eclipse.jetty.util;

import com.google.common.base.Ascii;
import java.io.IOException;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public abstract class Utf8Appendable {
    public static final char REPLACEMENT = 65533;
    private static final int UTF8_ACCEPT = 0;
    private static final int UTF8_REJECT = 12;
    protected final Appendable _appendable;
    private int _codep;
    protected int _state = 0;
    protected static final Logger LOG = Log.getLogger((Class<?>) Utf8Appendable.class);
    private static final byte[] BYTE_TABLE = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 10, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 11, 6, 6, 6, 5, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8};
    private static final byte[] TRANS_TABLE = {0, 12, Ascii.CAN, 36, 60, 96, 84, 12, 12, 12, TarConstants.LF_NORMAL, 72, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0, 12, 12, 12, 12, 12, 0, 12, 0, 12, 12, 12, Ascii.CAN, 12, 12, 12, 12, 12, Ascii.CAN, 12, Ascii.CAN, 12, 12, 12, 12, 12, 12, 12, 12, 12, Ascii.CAN, 12, 12, 12, 12, 12, Ascii.CAN, 12, 12, 12, 12, 12, 12, 12, Ascii.CAN, 12, 12, 12, 12, 12, 12, 12, 12, 12, 36, 12, 36, 12, 12, 12, 36, 12, 12, 12, 12, 12, 36, 12, 36, 12, 12, 12, 36, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};

    public static class NotUtf8Exception extends IllegalArgumentException {
        public NotUtf8Exception(String str) {
            super("Not valid UTF8! " + str);
        }
    }

    public Utf8Appendable(Appendable appendable) {
        this._appendable = appendable;
    }

    public void append(byte b3) {
        try {
            appendByte(b3);
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public void appendByte(byte b3) throws IOException {
        if (b3 > 0 && this._state == 0) {
            this._appendable.append((char) (b3 & 255));
            return;
        }
        int i2 = b3 & 255;
        byte b4 = BYTE_TABLE[i2];
        int i3 = this._state;
        int i4 = i3 == 0 ? (255 >> b4) & i2 : (i2 & 63) | (this._codep << 6);
        this._codep = i4;
        byte b5 = TRANS_TABLE[i3 + b4];
        if (b5 == 0) {
            this._state = b5;
            if (i4 < 55296) {
                this._appendable.append((char) i4);
                return;
            }
            for (char c3 : Character.toChars(i4)) {
                this._appendable.append(c3);
            }
            return;
        }
        if (b5 != 12) {
            this._state = b5;
            return;
        }
        String str = "byte " + TypeUtil.toHexString(b3) + " in state " + (this._state / 12);
        this._codep = 0;
        this._state = 0;
        this._appendable.append((char) 65533);
        throw new NotUtf8Exception(str);
    }

    public void checkState() throws IOException {
        if (isUtf8SequenceComplete()) {
            return;
        }
        this._codep = 0;
        this._state = 0;
        try {
            this._appendable.append((char) 65533);
            throw new NotUtf8Exception("incomplete UTF8 sequence");
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public boolean isUtf8SequenceComplete() {
        return this._state == 0;
    }

    public abstract int length();

    public void reset() {
        this._state = 0;
    }

    public String toReplacedString() throws IOException {
        if (!isUtf8SequenceComplete()) {
            this._codep = 0;
            this._state = 0;
            try {
                this._appendable.append((char) 65533);
                NotUtf8Exception notUtf8Exception = new NotUtf8Exception("incomplete UTF8 sequence");
                Logger logger = LOG;
                logger.warn(notUtf8Exception.toString(), new Object[0]);
                logger.debug(notUtf8Exception);
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        }
        return this._appendable.toString();
    }

    public void append(byte[] bArr, int i2, int i3) {
        int i4 = i3 + i2;
        while (i2 < i4) {
            try {
                appendByte(bArr[i2]);
                i2++;
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    public boolean append(byte[] bArr, int i2, int i3, int i4) {
        int i5 = i3 + i2;
        while (i2 < i5) {
            try {
                if (length() > i4) {
                    return false;
                }
                appendByte(bArr[i2]);
                i2++;
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        }
        return true;
    }
}
