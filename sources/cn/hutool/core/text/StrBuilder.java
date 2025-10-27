package cn.hutool.core.text;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.PrimitiveArrayUtil;
import java.io.Serializable;
import java.util.Arrays;

/* loaded from: classes.dex */
public class StrBuilder implements CharSequence, Appendable, Serializable {
    public static final int DEFAULT_CAPACITY = 16;
    private static final long serialVersionUID = 6341229705927508451L;
    private int position;
    private char[] value;

    public StrBuilder() {
        this(16);
    }

    public static StrBuilder create() {
        return new StrBuilder();
    }

    private void ensureCapacity(int i2) {
        if (i2 - this.value.length > 0) {
            expandCapacity(i2);
        }
    }

    private void expandCapacity(int i2) {
        char[] cArr = this.value;
        int length = (cArr.length << 1) + 2;
        if (length - i2 >= 0) {
            i2 = length;
        }
        if (i2 < 0) {
            throw new OutOfMemoryError("Capacity is too long and max than Integer.MAX");
        }
        this.value = Arrays.copyOf(cArr, i2);
    }

    private void moveDataAfterIndex(int i2, int i3) {
        ensureCapacity(Math.max(this.position, i2) + i3);
        int i4 = this.position;
        if (i2 < i4) {
            char[] cArr = this.value;
            System.arraycopy(cArr, i2, cArr, i3 + i2, i4 - i2);
        } else if (i2 > i4) {
            Arrays.fill(this.value, i4, i2, ' ');
        }
    }

    private static int totalLength(CharSequence... charSequenceArr) {
        int length = charSequenceArr.length;
        int length2 = 0;
        for (int i2 = 0; i2 < length; i2++) {
            CharSequence charSequence = charSequenceArr[i2];
            length2 += charSequence == null ? 0 : charSequence.length();
        }
        return length2;
    }

    @Override // java.lang.CharSequence
    public char charAt(int i2) {
        if (i2 < 0) {
            i2 += this.position;
        }
        if (i2 < 0 || i2 > this.position) {
            throw new StringIndexOutOfBoundsException(i2);
        }
        return this.value[i2];
    }

    public StrBuilder clear() {
        return reset();
    }

    public StrBuilder del(int i2, int i3) throws StringIndexOutOfBoundsException {
        if (i2 < 0) {
            i2 = 0;
        }
        int i4 = this.position;
        if (i3 >= i4) {
            this.position = i2;
            return this;
        }
        if (i3 < 0) {
            i3 = 0;
        }
        int i5 = i3 - i2;
        if (i5 > 0) {
            char[] cArr = this.value;
            System.arraycopy(cArr, i2 + i5, cArr, i2, i4 - i3);
            this.position -= i5;
        } else if (i5 < 0) {
            throw new StringIndexOutOfBoundsException("Start is greater than End.");
        }
        return this;
    }

    public StrBuilder delTo(int i2) {
        if (i2 < 0) {
            i2 = 0;
        }
        return del(i2, this.position);
    }

    public StrBuilder getChars(int i2, int i3, char[] cArr, int i4) {
        int i5 = 0;
        if (i2 < 0) {
            i2 = 0;
        }
        if (i3 < 0 || i3 > (i5 = this.position)) {
            i3 = i5;
        }
        if (i2 > i3) {
            throw new StringIndexOutOfBoundsException("srcBegin > srcEnd");
        }
        System.arraycopy(this.value, i2, cArr, i4, i3 - i2);
        return this;
    }

    public boolean hasContent() {
        return this.position > 0;
    }

    public StrBuilder insert(int i2, Object obj) {
        return obj instanceof CharSequence ? insert(i2, (CharSequence) obj) : insert(i2, (CharSequence) Convert.toStr(obj));
    }

    public boolean isEmpty() {
        return this.position == 0;
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.position;
    }

    public StrBuilder reset() {
        this.position = 0;
        return this;
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i2, int i3) {
        return subString(i2, i3);
    }

    public String subString(int i2) {
        return subString(i2, this.position);
    }

    public String toString(boolean z2) {
        int i2 = this.position;
        if (i2 <= 0) {
            return "";
        }
        String str = new String(this.value, 0, i2);
        if (z2) {
            reset();
        }
        return str;
    }

    public String toStringAndReset() {
        return toString(true);
    }

    public StrBuilder(int i2) {
        this.value = new char[i2];
    }

    public static StrBuilder create(int i2) {
        return new StrBuilder(i2);
    }

    public String subString(int i2, int i3) {
        return new String(this.value, i2, i3 - i2);
    }

    public static StrBuilder create(CharSequence... charSequenceArr) {
        return new StrBuilder(charSequenceArr);
    }

    public StrBuilder(CharSequence... charSequenceArr) {
        this(ArrayUtil.isEmpty((Object[]) charSequenceArr) ? 16 : 16 + totalLength(charSequenceArr));
        for (CharSequence charSequence : charSequenceArr) {
            append(charSequence);
        }
    }

    public StrBuilder append(Object obj) {
        return insert(this.position, obj);
    }

    public StrBuilder insert(int i2, char c3) {
        if (i2 < 0) {
            i2 += this.position;
        }
        if (i2 >= 0) {
            moveDataAfterIndex(i2, 1);
            this.value[i2] = c3;
            this.position = Math.max(this.position, i2) + 1;
            return this;
        }
        throw new StringIndexOutOfBoundsException(i2);
    }

    @Override // java.lang.CharSequence
    public String toString() {
        return toString(false);
    }

    @Override // java.lang.Appendable
    public StrBuilder append(char c3) {
        return insert(this.position, c3);
    }

    public StrBuilder append(char[] cArr) {
        return PrimitiveArrayUtil.isEmpty(cArr) ? this : append(cArr, 0, cArr.length);
    }

    public StrBuilder append(char[] cArr, int i2, int i3) {
        return insert(this.position, cArr, i2, i3);
    }

    @Override // java.lang.Appendable
    public StrBuilder append(CharSequence charSequence) {
        return insert(this.position, charSequence);
    }

    public StrBuilder insert(int i2, char[] cArr) {
        return PrimitiveArrayUtil.isEmpty(cArr) ? this : insert(i2, cArr, 0, cArr.length);
    }

    @Override // java.lang.Appendable
    public StrBuilder append(CharSequence charSequence, int i2, int i3) {
        return insert(this.position, charSequence, i2, i3);
    }

    public StrBuilder insert(int i2, char[] cArr, int i3, int i4) {
        if (PrimitiveArrayUtil.isEmpty(cArr) || i3 > cArr.length || i4 <= 0) {
            return this;
        }
        if (i2 < 0) {
            i2 += this.position;
        }
        if (i2 >= 0) {
            if (i3 < 0) {
                i3 = 0;
            } else if (i3 + i4 > cArr.length) {
                i4 = cArr.length - i3;
            }
            moveDataAfterIndex(i2, i4);
            System.arraycopy(cArr, i3, this.value, i2, i4);
            this.position = Math.max(this.position, i2) + i4;
            return this;
        }
        throw new StringIndexOutOfBoundsException(i2);
    }

    public StrBuilder insert(int i2, CharSequence charSequence) {
        if (i2 < 0) {
            i2 += this.position;
        }
        if (i2 >= 0) {
            if (charSequence == null) {
                charSequence = "";
            }
            int length = charSequence.length();
            moveDataAfterIndex(i2, charSequence.length());
            int i3 = 0;
            if (charSequence instanceof String) {
                ((String) charSequence).getChars(0, length, this.value, i2);
            } else if (charSequence instanceof StringBuilder) {
                ((StringBuilder) charSequence).getChars(0, length, this.value, i2);
            } else if (charSequence instanceof StringBuffer) {
                ((StringBuffer) charSequence).getChars(0, length, this.value, i2);
            } else if (charSequence instanceof StrBuilder) {
                ((StrBuilder) charSequence).getChars(0, length, this.value, i2);
            } else {
                int i4 = this.position;
                while (i3 < length) {
                    this.value[i4] = charSequence.charAt(i3);
                    i3++;
                    i4++;
                }
            }
            this.position = Math.max(this.position, i2) + length;
            return this;
        }
        throw new StringIndexOutOfBoundsException(i2);
    }

    public StrBuilder insert(int i2, CharSequence charSequence, int i3, int i4) {
        if (charSequence == null) {
            charSequence = "null";
        }
        int length = charSequence.length();
        if (i3 > length) {
            return this;
        }
        if (i3 < 0) {
            i3 = 0;
        }
        if (i4 > length) {
            i4 = length;
        }
        if (i3 >= i4) {
            return this;
        }
        if (i2 < 0) {
            i2 += this.position;
        }
        if (i2 >= 0) {
            int i5 = i4 - i3;
            moveDataAfterIndex(i2, i5);
            int i6 = this.position;
            while (i3 < i4) {
                this.value[i6] = charSequence.charAt(i3);
                i3++;
                i6++;
            }
            this.position = Math.max(this.position, i2) + i5;
            return this;
        }
        throw new StringIndexOutOfBoundsException(i2);
    }
}
