package org.jsoup.parser;

import java.util.Locale;
import org.jsoup.helper.Validate;

/* loaded from: classes9.dex */
class CharacterReader {
    static final char EOF = 65535;
    private final char[] input;
    private final int length;
    private int pos = 0;
    private int mark = 0;

    public CharacterReader(String str) {
        Validate.notNull(str);
        char[] charArray = str.toCharArray();
        this.input = charArray;
        this.length = charArray.length;
    }

    public void advance() {
        this.pos++;
    }

    public char consume() {
        int i2 = this.pos;
        char c3 = i2 >= this.length ? (char) 65535 : this.input[i2];
        this.pos = i2 + 1;
        return c3;
    }

    public String consumeAsString() {
        char[] cArr = this.input;
        int i2 = this.pos;
        this.pos = i2 + 1;
        return new String(cArr, i2, 1);
    }

    public String consumeDigitSequence() {
        int i2;
        char c3;
        int i3 = this.pos;
        while (true) {
            i2 = this.pos;
            if (i2 >= this.length || (c3 = this.input[i2]) < '0' || c3 > '9') {
                break;
            }
            this.pos = i2 + 1;
        }
        return new String(this.input, i3, i2 - i3);
    }

    public String consumeHexSequence() {
        int i2;
        char c3;
        int i3 = this.pos;
        while (true) {
            i2 = this.pos;
            if (i2 >= this.length || (((c3 = this.input[i2]) < '0' || c3 > '9') && ((c3 < 'A' || c3 > 'F') && (c3 < 'a' || c3 > 'f')))) {
                break;
            }
            this.pos = i2 + 1;
        }
        return new String(this.input, i3, i2 - i3);
    }

    public String consumeLetterSequence() {
        int i2;
        char c3;
        int i3 = this.pos;
        while (true) {
            i2 = this.pos;
            if (i2 >= this.length || (((c3 = this.input[i2]) < 'A' || c3 > 'Z') && (c3 < 'a' || c3 > 'z'))) {
                break;
            }
            this.pos = i2 + 1;
        }
        return new String(this.input, i3, i2 - i3);
    }

    public String consumeLetterThenDigitSequence() {
        char c3;
        int i2 = this.pos;
        while (true) {
            int i3 = this.pos;
            if (i3 >= this.length || (((c3 = this.input[i3]) < 'A' || c3 > 'Z') && (c3 < 'a' || c3 > 'z'))) {
                break;
            }
            this.pos = i3 + 1;
        }
        while (!isEmpty()) {
            char[] cArr = this.input;
            int i4 = this.pos;
            char c4 = cArr[i4];
            if (c4 < '0' || c4 > '9') {
                break;
            }
            this.pos = i4 + 1;
        }
        return new String(this.input, i2, this.pos - i2);
    }

    public String consumeTo(char c3) {
        int iNextIndexOf = nextIndexOf(c3);
        if (iNextIndexOf == -1) {
            return consumeToEnd();
        }
        String str = new String(this.input, this.pos, iNextIndexOf);
        this.pos += iNextIndexOf;
        return str;
    }

    public String consumeToAny(char... cArr) {
        int i2 = this.pos;
        loop0: while (this.pos < this.length) {
            for (char c3 : cArr) {
                if (this.input[this.pos] == c3) {
                    break loop0;
                }
            }
            this.pos++;
        }
        int i3 = this.pos;
        return i3 > i2 ? new String(this.input, i2, i3 - i2) : "";
    }

    public String consumeToEnd() {
        char[] cArr = this.input;
        int i2 = this.pos;
        String str = new String(cArr, i2, this.length - i2);
        this.pos = this.length;
        return str;
    }

    public boolean containsIgnoreCase(String str) {
        Locale locale = Locale.ENGLISH;
        return nextIndexOf(str.toLowerCase(locale)) > -1 || nextIndexOf(str.toUpperCase(locale)) > -1;
    }

    public char current() {
        int i2 = this.pos;
        if (i2 >= this.length) {
            return (char) 65535;
        }
        return this.input[i2];
    }

    public boolean isEmpty() {
        return this.pos >= this.length;
    }

    public void mark() {
        this.mark = this.pos;
    }

    public boolean matchConsume(String str) {
        if (!matches(str)) {
            return false;
        }
        this.pos += str.length();
        return true;
    }

    public boolean matchConsumeIgnoreCase(String str) {
        if (!matchesIgnoreCase(str)) {
            return false;
        }
        this.pos += str.length();
        return true;
    }

    public boolean matches(char c3) {
        return !isEmpty() && this.input[this.pos] == c3;
    }

    public boolean matchesAny(char... cArr) {
        if (isEmpty()) {
            return false;
        }
        char c3 = this.input[this.pos];
        for (char c4 : cArr) {
            if (c4 == c3) {
                return true;
            }
        }
        return false;
    }

    public boolean matchesDigit() {
        char c3;
        return !isEmpty() && (c3 = this.input[this.pos]) >= '0' && c3 <= '9';
    }

    public boolean matchesIgnoreCase(String str) {
        int length = str.length();
        if (length > this.length - this.pos) {
            return false;
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (Character.toUpperCase(str.charAt(i2)) != Character.toUpperCase(this.input[this.pos + i2])) {
                return false;
            }
        }
        return true;
    }

    public boolean matchesLetter() {
        if (isEmpty()) {
            return false;
        }
        char c3 = this.input[this.pos];
        return (c3 >= 'A' && c3 <= 'Z') || (c3 >= 'a' && c3 <= 'z');
    }

    public int nextIndexOf(char c3) {
        for (int i2 = this.pos; i2 < this.length; i2++) {
            if (c3 == this.input[i2]) {
                return i2 - this.pos;
            }
        }
        return -1;
    }

    public int pos() {
        return this.pos;
    }

    public void rewindToMark() {
        this.pos = this.mark;
    }

    public String toString() {
        char[] cArr = this.input;
        int i2 = this.pos;
        return new String(cArr, i2, this.length - i2);
    }

    public void unconsume() {
        this.pos--;
    }

    public boolean matches(String str) {
        int length = str.length();
        if (length > this.length - this.pos) {
            return false;
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (str.charAt(i2) != this.input[this.pos + i2]) {
                return false;
            }
        }
        return true;
    }

    public int nextIndexOf(CharSequence charSequence) {
        char cCharAt = charSequence.charAt(0);
        int i2 = this.pos;
        while (i2 < this.length) {
            if (cCharAt != this.input[i2]) {
                do {
                    i2++;
                    if (i2 >= this.length) {
                        break;
                    }
                } while (cCharAt != this.input[i2]);
            }
            int i3 = i2 + 1;
            int length = (charSequence.length() + i3) - 1;
            int i4 = this.length;
            if (i2 < i4 && length <= i4) {
                int i5 = i3;
                for (int i6 = 1; i5 < length && charSequence.charAt(i6) == this.input[i5]; i6++) {
                    i5++;
                }
                if (i5 == length) {
                    return i2 - this.pos;
                }
            }
            i2 = i3;
        }
        return -1;
    }

    public String consumeTo(String str) {
        int iNextIndexOf = nextIndexOf(str);
        if (iNextIndexOf != -1) {
            String str2 = new String(this.input, this.pos, iNextIndexOf);
            this.pos += iNextIndexOf;
            return str2;
        }
        return consumeToEnd();
    }
}
